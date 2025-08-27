package kr.exam.springs.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.exam.springs.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService service;
	
	
	@GetMapping("/login.do")
	public ModelAndView loginView() {
		ModelAndView view = new ModelAndView("user/loginForm");
		return view;
	}
	
	@PostMapping("/login.do")
	@ResponseBody //controller 에서 ajax 콜 응답을 위해 처리 
	public Map<String, Object> login(@RequestParam("userId") String userId,
			                         @RequestParam("passwd") String passwd,
			                         HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("userId", userId);
		map.put("passwd", passwd);
	    
		int result = 0;
		
		try {		
			result = service.userLogin(map, req);
			
			if(result < 0) {
				throw new Exception("로그인 실패");
			}
			//성공 시 200
			map.put("resultCode", 200);
			
		}catch (Exception e) {
			map.put("resultCode", 500);
			e.printStackTrace();
		}
	
		return map;
	
	
	
	
	
	
	
	}
}
