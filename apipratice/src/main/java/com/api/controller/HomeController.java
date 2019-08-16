package com.api.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.service.KaKaoAPI;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Log4j
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Setter(onMethod_=@Autowired)
	private KaKaoAPI kakaoApi;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	@GetMapping("/index")
	public void loginGet() {
		
	}
//	/**
//	 * GET /oauth/authorize?client_id={app_key}&redirect_uri={redirect_uri}&response_type=code HTTP/1.1
//	 * Host: kauth.kakao.com
//	 * HTTP/1.1 302 Found
//	 * Content-Length: 0
//     * Location: {redirect_uri}?code={authorize_code}
//	 */
//	@RequestMapping(value="/login")
//	public String login(@RequestParam("code") String code,Model model,HttpSession session) {
//		log.info("code: "+code);
//		//요청해서 받은 code를 이용해서 POST로 토큰을 요청하면 받아온다.
//		String access_Token = kakaoApi.getAccessToken(code);
//		log.info("access_Token: "+access_Token);
//		//받아온 토큰을 이용해서 사용자 정보를 가져온다.
//		HashMap<String,Object> userInfo = kakaoApi.getUserInfo(access_Token);
//		
//		//만약 클라이언트 이메일이 존재한다면 세션에 이메일과 토큰 등록
//		if(userInfo.get("email")!=null) {
//			session.setAttribute("userId", userInfo.get("email"));
//	        session.setAttribute("access_Token", access_Token);
//		}
//		model.addAttribute("email",userInfo.get("email"));
//		model.addAttribute("nickname",userInfo.get("nickname"));
//		return "index";
//	}
//	
//	@RequestMapping(value="/logout")
//	public String logout(Model model,HttpSession session) {
//		kakaoApi.kakaoLogout((String)session.getAttribute("access_Token"));
//		session.removeAttribute("access_Token");
//	    session.removeAttribute("userId");
//	    model.addAttribute("message","로그아웃 되었습니다.");
//	    model.addAttribute("flag",1);
//		return "index";
//	}
}
