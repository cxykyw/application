package com.imooc.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.pojo.IMoocJSONResult;
import com.imooc.pojo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/getUser")
	public User getUser() {
		
		User user = new User();
		user.setName("kyw11");
		user.setPassword("12311");
		user.setAge(18);
		user.setBirthday(new Date());
		user.setDesc("helo-boot");
		
		
		return user;
	}
	
	
	@RequestMapping("/getUserJson")
	public IMoocJSONResult getUserJson() {
		
		User user = new User();
		user.setName("kyw");
		user.setPassword("1231");
		user.setAge(18);
		user.setBirthday(new Date());
		user.setDesc("helo-boot");
		
		
		return IMoocJSONResult.ok(user);
	}
}
