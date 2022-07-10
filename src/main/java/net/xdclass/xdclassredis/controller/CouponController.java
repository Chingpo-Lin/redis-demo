package net.xdclass.xdclassredis.controller;


import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("add")
    public JsonData saveCoupon(@RequestParam(value="coupon_id", required = true) int couponId) {

        String uuid = UUID.randomUUID().toString();

        String lockKey = "lock:coupon:" + couponId;
        lock(couponId, uuid, lockKey);
        return JsonData.buildSuccess();
    }

    private void lock(int couponId, String uuid, String lockKey) {

        // lua script
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] " +
                "then return redis.call('del',KEYS[1]) else return 0 end";

        Boolean nativaLock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, Duration.ofSeconds(30));
        System.out.println(uuid + "lock status: " + nativaLock);

        if (nativaLock) {
            // success
            try {
                TimeUnit.SECONDS.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                        Arrays.asList(lockKey),uuid);
                System.out.println("unlock status:" + result);

            }
        } else {
            try {
                System.out.println("fail to lock, enter loop");
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock(couponId, uuid, lockKey);
        }
    }
}
