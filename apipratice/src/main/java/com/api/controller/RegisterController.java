package com.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.service.UserMailSendService;
import com.api.service.UserService;
import com.api.domain.UserVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user/*")
public class RegisterController {

	@Setter(onMethod_ = @Autowired)
	private UserMailSendService mailService;

	@Setter(onMethod_ = @Autowired)
	private UserService userService;

	@GetMapping(value = "/register")
	public void register() {

	}

	@PostMapping(value = "/register")
	public String registerPost(UserVO userVO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// 비밀번호 암호화
		String hashedPw = BCrypt.hashpw(userVO.getUserPw(), BCrypt.gensalt());
		userVO.setUserPw(hashedPw);

		userService.register(userVO);
		mailService.mailSendWithUserKey(userVO.getUserEmail(), userVO.getUserId(), request);
		redirectAttributes.addFlashAttribute("msg", "REGISTERED");

		return "redirect:/user/login";
	}


	@GetMapping("/key_alter")
	public String key_alterConfirm(@RequestParam("userId") String userId,@RequestParam("userKey") String userKey) {
		mailService.alter_userKey_service(userId,userKey);
		return "/user/userRegSuccess";
	}
	
	
	// 중복 체크 컨트롤러 AJax
	// /user/idcheck
	@RequestMapping("/idcheck")
	@ResponseBody
	public String idcheck(@RequestBody String userid) {
		String checkRst;
		int count = 0;

		count = userService.idcheck(userid);
		log.info("check Id: " + userid);
		log.info("count: " + count);
		if (count > 0) {
			// 중복 발생
			checkRst = "F";
		} else {
			// 중복 아님
			checkRst = "S";
		}
		return checkRst;
	}
}
