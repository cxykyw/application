package com.imooc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.imooc.mapper.SysUserMapper;
import com.imooc.mapper.SysUserMapperCustom;
import com.imooc.pojo.SysUser;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysUserMapperCustom userMapperCustom;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(SysUser user) throws Exception {
			
		userMapper.insert(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(SysUser user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(String userId) {
		userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public SysUser queryUserById(String userId) {
		
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SysUser> queryUserList(SysUser user) {
		Example example = new Example(SysUser.class);
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmptyOrWhitespace(user.getNickname())) {
			criteria.andLike("nickname", "%"+user.getNickname()+"%");
		}
		if(!StringUtils.isEmptyOrWhitespace(user.getUsername())) {
			criteria.andLike("username", "%"+user.getUsername()+"%");
		}
		
		List<SysUser> userList = userMapper.selectByExample(example);
		return userList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SysUser> queryUserListPaged(SysUser user, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		Example example = new Example(SysUser.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isEmptyOrWhitespace(user.getNickname())) {
			criteria.andLike("nickname", "%"+user.getNickname()+"%");
		}
		example.orderBy("registTime").desc();
		List<SysUser> userList = userMapper.selectByExample(example);
		
		return userList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public SysUser queryUserByIdCustom(String userId) {
		List<SysUser> userList = userMapperCustom.queryUserSimplyInfoById(userId);
		
		if (userList != null && !userList.isEmpty()) {
			return (SysUser)userList.get(0);
		}
		
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUserTransactional(SysUser user) {
		userMapper.insert(user);
		
		int a = 1 / 0;
		
		user.setIsDelete(1);
		userMapper.updateByPrimaryKeySelective(user);
		
	}

}
