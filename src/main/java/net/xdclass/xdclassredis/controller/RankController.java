package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.util.JsonData;
import net.xdclass.xdclassredis.vo.UserPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/rank")
public class RankController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String DAILY_RANK_KEY = "video:rank:daily";

    @RequestMapping("daily_rank")
    public JsonData videoDailyRank() {
        List<VideoDO> list = redisTemplate.opsForList().range(DAILY_RANK_KEY, 0, -1);
        return JsonData.buildSuccess(list);
    }

    /**
     * return all rank from large to small
     * @return
     */
    @RequestMapping("real_rank1")
    public JsonData realRank1() {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        Set<UserPointVO> set = operations.reverseRange(0, -1);
        return JsonData.buildSuccess(set);
    }

    /**
     * return all rank from small to large
     * @return
     */
    @RequestMapping("real_rank2")
    public JsonData realRank2() {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        Set<UserPointVO> set = operations.range(0, -1);
        return JsonData.buildSuccess(set);
    }

    /**
     * return top 3
     * @return
     */
    @RequestMapping("real_rank3")
    public JsonData realRank3() {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        Set<UserPointVO> set = operations.reverseRange(0, 3);
        return JsonData.buildSuccess(set);
    }


    /**
     * find my rank
     * @param phone
     * @param name
     * @return
     */
    @RequestMapping("find_myrank")
    public JsonData realMyRank(String phone, String name) {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        UserPointVO userPointVO = new UserPointVO(name, phone);
        long rank = operations.reverseRank(userPointVO);

        return JsonData.buildSuccess(++rank);
    }

    /**
     * uprank
     * @param phone
     * @param name
     * @param point
     * @return
     */
    @RequestMapping("uprank")
    public JsonData uprank(String phone, String name, int point) {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        UserPointVO userPointVO = new UserPointVO(name, phone);
        operations.incrementScore(userPointVO, point);

        Set<UserPointVO> set = operations.reverseRange(0, -1);
        return JsonData.buildSuccess(set);
    }

    /**
     * my point
     * @param phone
     * @param name
     * @return
     */
    @RequestMapping("mypoint")
    public JsonData mypoint(String phone, String name) {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        UserPointVO userPointVO = new UserPointVO(name, phone);

        double score = operations.score(userPointVO);
        return JsonData.buildSuccess(score);
    }
}
