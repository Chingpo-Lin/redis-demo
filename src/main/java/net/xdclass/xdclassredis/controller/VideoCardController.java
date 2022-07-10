package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.service.VideoCardService;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/api/v1/card")
@RestController
public class VideoCardController {

    @Autowired
    private VideoCardService videoCardService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String VIDEO_CARD_CACHE_KEY = "video:card:key";

    /**
     * get data from cache
     * @return
     */
    @GetMapping("list_cache")
    public JsonData listCardCache() {
        Object cacheObj = redisTemplate.opsForValue().get(VIDEO_CARD_CACHE_KEY);

        List<VideoCardDO> list;
        if (cacheObj != null) {
            list = (List<VideoCardDO>) cacheObj;
        } else {
            list = videoCardService.list();
            redisTemplate.opsForValue().set(VIDEO_CARD_CACHE_KEY, list, 5, TimeUnit.MINUTES);
        }
        return JsonData.buildSuccess(list);
    }

    /**
     * get data from db
     * @return
     */
    @GetMapping("list_nocache")
    public JsonData listCardNoCache() {
        List<VideoCardDO> list = videoCardService.list();
        return JsonData.buildSuccess(list);
    }
}
