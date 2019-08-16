package com.api.mapper;

import org.apache.ibatis.annotations.Param;

import com.api.domain.UserVO;

public interface UserMapper {
	// 회원가입
	public void register(UserVO userVO);

	// 중복확인
	public int idcheck(String userId);

	// 인증키 업데이트
	public int GetKey(@Param("userId") String userId, @Param("userKey") String userKey);

	// 인증완료시 키 Y로 바꾸기
	public int alter_userKey(@Param("userId") String userId, @Param("userKey") String userKey);
}
