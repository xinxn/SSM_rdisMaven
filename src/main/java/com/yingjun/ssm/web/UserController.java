package com.yingjun.ssm.web;

import com.yingjun.ssm.cache.RedisUtil;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.sql.DataSource;

@Controller
@RequestMapping("/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, Integer offset, Integer limit) {
		LOG.info("invoke----------/user/list");
		offset = offset == null ? 0 : offset;//默认便宜0
		limit = limit == null ? 50 : limit;//默认展示50条
		List<User> list = userService.getUserList(offset, limit);
		model.addAttribute("userlist", list);
		return "userlist";
	}
	
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping(value = "/forword")
	public String Forword() {
		System.out.println("1111:");
		redisUtil.set("word", "123456");
		return "MyJsp";
	}
	@RequestMapping(value = "/userlist")
	public String userlist() {
		
		Object object = redisUtil.get("word");
		System.out.println("222:"+object);
		
		return "userlist";
	}
	@RequestMapping(value = "/goodslist")
	public String goodslist() {
		
		System.out.println("333:");
		
		return "goodslist";
	}

}
