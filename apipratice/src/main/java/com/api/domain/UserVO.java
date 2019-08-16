package com.api.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private String userId;
	private String userPw;
	private String userEmail;
	private Date userJoinDate;
	private Date userLoginDate;
	private String userKey;
}
