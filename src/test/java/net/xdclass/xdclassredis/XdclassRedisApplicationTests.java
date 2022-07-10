package net.xdclass.xdclassredis;

import net.xdclass.xdclassredis.model.UserDO;
import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.vo.UserPointVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;

@SpringBootTest
class XdclassRedisApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void testStringSet() {
//		ValueOperations valueOperations = redisTemplate.opsForValue();
		redisTemplate.opsForValue().set("name", "xdclass.net");

		stringRedisTemplate.opsForValue().set("str_name", "xdclass.net2");
	}

	@Test
	void testStringGet() {

		String str1 = (String) redisTemplate.opsForValue().get("name");
		System.out.println(str1); // xdclass.net

		String str2 = stringRedisTemplate.opsForValue().get("str_name");
		System.out.println(str2); // xdclass.net2
	}

	@Test
	public void testSeria() {

		UserDO userDO = new UserDO();
		userDO.setId(1);
		userDO.setName("bob");
		userDO.setPwd("123456");

		redisTemplate.opsForValue().set("user-service:user:2", userDO);
	}

	@Test
	public void saveRank() {
		String DAILY_RANK_KEY = "video:rank:daily";

		VideoDO video1 = new VideoDO(3,"PaaS工业 级微服务大课","xdclass.net",1099);
		VideoDO video2 = new VideoDO(5,"AlibabaCloud全家桶实战","xdclass.net",59);
		VideoDO video3 = new VideoDO(53,"SpringBoot2.X+Vue3综合实 战","xdclass.net",49);
		VideoDO video4 = new VideoDO(15,"玩转23 种设计模式+最近实战","xdclass.net",49);
		VideoDO video5 = new VideoDO(45,"Nginx 网关+LVS+KeepAlive","xdclass.net",89);
		redisTemplate.opsForList().leftPushAll(DAILY_RANK_KEY,video4,video5,video3,video2,video1);
	}

	@Test
	public void replaceRank() {
		String DAILY_RANK_KEY = "video:rank:daily";

		VideoDO video = new VideoDO(5432,"xdclass interview","xdclass.net",300);
		redisTemplate.opsForList().set(DAILY_RANK_KEY, 1, video);
	}

	@Test
	public void userProfile() {

		BoundSetOperations operations = redisTemplate.boundSetOps("user:tag:1");
		operations.add("car", "student", "rich", "dog", "guangdong", "rich");

		Set<String> set = operations.members();
		System.out.println(set);
	}

	@Test
	public void testSocial() {
		BoundSetOperations operations = redisTemplate.boundSetOps("user:lin");
		operations.add("A", "B", "C", "D", "E");
		System.out.println("fans:" + operations.members());

		BoundSetOperations operations2 = redisTemplate.boundSetOps("user:bob");
		operations2.add("A", "B", "F", "G", "H");
		System.out.println("fans:" + operations2.members());

		Set linDiff = operations.diff("user:bob");
		System.out.println("lin diff:" + linDiff);

		Set bobDiff = operations2.diff("user:lin");
		System.out.println("lin diff:" + bobDiff);

		Set interSet = operations.intersect("user:bob");
		System.out.println("intercept:" + interSet);

		Set union = operations.union("user:bob");
		System.out.println("union:" + union);

		boolean flag = operations.isMember("A");
		System.out.println("A is in set1: " + flag);
	}


	@Test
	void testData() {
		UserPointVO p1 = new UserPointVO("老 王","13113");
		UserPointVO p2 = new UserPointVO("老 A","324");
		UserPointVO p3 = new UserPointVO("老 B","242");
		UserPointVO p4 = new UserPointVO("老 C","542345");
		UserPointVO p5 = new UserPointVO("老 D","235");
		UserPointVO p6 = new UserPointVO("老 E","1245");

		UserPointVO p7 = new UserPointVO("老 F","2356432");
		UserPointVO p8 = new UserPointVO("老 G","532332");
		BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

		operations.add(p1, 888);
		operations.add(p2, 8882);
		operations.add(p3, 8388);
		operations.add(p4, 8588);
		operations.add(p5, 8885);
		operations.add(p6, 8881);
		operations.add(p7, 8);
		operations.add(p8, 88);
	}
}
