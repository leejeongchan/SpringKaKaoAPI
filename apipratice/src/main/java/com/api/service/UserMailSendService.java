package com.api.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.api.mapper.UserMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UserMailSendService {
	@Setter(onMethod_ = @Autowired)
	private JavaMailSender mailSender;

	@Setter(onMethod_ = @Autowired)
	private UserMapper userMapper;

	// 이메일 난수 만드는 메서드
	private String init() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		int num = 0;

		do {
			num = ran.nextInt(75) + 48;
			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char) num);
			} else {
				continue;
			}

		} while (sb.length() < size);
		if (lowerCheck) {
			return sb.toString().toLowerCase();
		}
		return sb.toString();
	}

	// 난수를 이용한 키 생성
	private boolean lowerCheck;
	private int size;

	public String getKey(boolean lowerCheck, int size) {
		this.lowerCheck = lowerCheck;
		this.size = size;
		return init();
	}

	// 회원가입 발송 이메일(인증키 발송)
	public void mailSendWithUserKey(String userEmail, String userId, HttpServletRequest request) {

			String userKey = getKey(false, 20);
			// userDao = sqlSession.getMapper(UserDaoInterface.class);
			userMapper.GetKey(userId, userKey);
			log.info("1"); 
			MimeMessage mail = mailSender.createMimeMessage();
			log.info("mail: " + mail);
			String htmlStr = "<h2>안녕하세요 MS :p 이정찬 MOVIE 입니다!</h2><br><br>" + "<h3>" + userId + "님</h3>"
					+ "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다 : " + "<a href='http://localhost:8080" + request.getContextPath()
					+ "/user/key_alter?userId=" + userId + "&userKey=" + userKey + "'>인증하기</a></p>"
					+ "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
			try {
				mail.setSubject("[본인인증] MS :p 이정찬님의 인증메일입니다", "utf-8");
				mail.setText(htmlStr, "utf-8", "html");
				mail.addRecipient(RecipientType.TO, new InternetAddress(userEmail));
				log.info("mail: " + mail);
				mailSender.send(mail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	}
	//이메일 받고 링크를 누르면 아래 작동.
	public int alter_userKey_service(String userId, String userKey) {
		int resultCnt = 0;
		resultCnt = userMapper.alter_userKey(userId, userKey);
		return resultCnt;
	}
}
