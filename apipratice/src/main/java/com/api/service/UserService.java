package com.api.service;

import com.api.domain.UserVO;

public interface UserService {
	public void register(UserVO userVO);

	public int idcheck(String userId);

	public int GetKey(String userId, String userKey);

	public int alter_userKey(String userId, String userKey);
}
