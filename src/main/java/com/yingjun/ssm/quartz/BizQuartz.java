package com.yingjun.ssm.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yingjun.ssm.cache.RedisUtil;
import com.yingjun.ssm.dao.UserDao;

@Component
public class BizQuartz {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisUtil cache;
	/**
	 * 用户自动加积分
	 * 每天9点到17点每过1分钟所有用户加一次积分
	 */
	@Scheduled(cron = "0 0/1 9-17 * * ? ")
	public void addUserScore() {
		LOG.info("@Scheduled--------addUserScore()");
		userDao.addScore(10);
	}
	/**
	 * 每隔5分钟定时清理缓存
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void cacheClear() {
		LOG.info("@Scheduled-------cacheClear()");
		//cache.clearCache();
	}
	
}
