package com.yingjun.ssm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yingjun.ssm.cache.RedisUtil;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:spring/spring-dao.xml","classpath:spring/spring-web.xml","classpath:spring/spring-redis.xml"})
public class RedisText {

	@Autowired
	private RedisUtil redisUtil;
	
	@Test
	public void textRes() {
		
		boolean set = redisUtil.set("key", "com.yingjun.ssm.cache.RedisUtil@733ec58b");
		System.out.println(set);
		Object object = redisUtil.get("key");
		System.out.println(object);
	}
	
	
	public  void red() {
		//连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
	}
	
}
