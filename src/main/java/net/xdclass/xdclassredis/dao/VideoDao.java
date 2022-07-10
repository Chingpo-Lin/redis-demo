package net.xdclass.xdclassredis.dao;

import net.xdclass.xdclassredis.model.VideoDO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VideoDao {


    private static Map<Integer, VideoDO> map = new HashMap<>();

    static {
        map.put(1, new VideoDO(1, "SpringCloudAlibaba", "class", 1099));
        map.put(2, new VideoDO(2, "RabbitMQ", "class2", 79));
        map.put(3, new VideoDO(3, "MybatisPlus+SwaggerUI3.X+Lombok", "class3", 49));
        map.put(4, new VideoDO(4, "Nginx", "class4", 49));
        map.put(5, new VideoDO(5, "SpringBoot2.3/spring5/mybatis3", "class5", 49));
        map.put(6, new VideoDO(6, "AlibabaCloud+SpringCloud", "class6", 59));
    }

    /**
     * mimic find from db
     * @param videoId
     * @return
     */
    public VideoDO findDetailById(int videoId) {
        return map.get(videoId);
    }
}

