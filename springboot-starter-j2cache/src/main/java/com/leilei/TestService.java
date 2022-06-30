package com.leilei;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lei
 * @create 2022-05-10 18:37
 * @desc
 **/
@Service
public class TestService {

	private final AtomicInteger num = new AtomicInteger(0);
	private final RedisTemplate<String, Object> redisTemplate;

	public TestService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Cacheable(cacheNames="testBean",key = "#id")
	public TestBean testBean(Integer id) {
		TestBean bean = new TestBean();
		int i = num.incrementAndGet();
		bean.setName("testBean"+i);
		bean.setNum(i);
		return bean;
	}

	/**
	 * 测试自己的redisTemplate模板不受框架干扰
	 * @return
	 */
	public Object testMyTemplate() {
		redisTemplate.opsForValue().set("天王盖地虎","宝塔镇河妖");
		return redisTemplate.opsForValue().get("天王盖地虎");
	}
	
	@CacheEvict(cacheNames={"testBean"},allEntries = true)
	public void evict() {
		
	}

	@CacheEvict(cacheNames={"testBean"},key = "#id")
	public void evict(Integer id) {

	}
	
	public void reset() {
		num.set(0);
	}
	
}
