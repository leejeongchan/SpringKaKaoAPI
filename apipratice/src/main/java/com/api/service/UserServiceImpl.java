package com.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.domain.UserVO;
import com.api.mapper.UserMapper;

import lombok.Setter;

@Service
public class UserServiceImpl implements UserService{

	@Setter(onMethod_=@Autowired)
	private UserMapper userMapper;
	
	@Override
	public void register(UserVO userVO) {
		// TODO Auto-generated method stub
		userMapper.register(userVO);
	}

	@Override
	public int idcheck(String userId) {
		// TODO Auto-generated method stub
		return userMapper.idcheck(userId);
	}

	@Override
	public int GetKey(String userId, String userKey) {
		// TODO Auto-generated method stub
		return userMapper.GetKey(userId, userKey);
	}

	@Override
	public int alter_userKey(String userId, String userKey) {
		// TODO Auto-generated method stub
		return userMapper.alter_userKey(userId, userKey);
	}

}
