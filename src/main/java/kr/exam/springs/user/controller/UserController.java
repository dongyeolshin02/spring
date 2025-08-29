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
import kr.exam.springs.user.vo.Users;
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
	
	
	@GetMapping("/join.do")
	public ModelAndView joinView() {
		ModelAndView view = new ModelAndView("user/register");
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
	

	
	@GetMapping("/list.do")
	public ModelAndView userListView(
			@RequestParam(name="currentPage", defaultValue = "0") int currentPage) {
		
		ModelAndView view = new ModelAndView();
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			map.put("currentPage", currentPage);
			map = service.getUserList(map);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		view.addObject("data", map);
		view.setViewName("user/userList");
		
		return view;
	}
	
	
	@GetMapping("/search.do")
	@ResponseBody  // ajax 호출에 대한 데이터 전송 
	public Map<String, Object> searchUser( @RequestParam(name="type") String type,
									@RequestParam(name="schText")String searchText) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			//검색 후에는 무조건 첫페이지 
			map.put("currentPage", 0);
			map.put("type", type);
			map.put("searchText", searchText);
			map = service.getUserList(map);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	@PostMapping("/add.do")
	@ResponseBody
	public Map<String, Object> addUser(Users.UserInfo user) {
		 Map<String, Object> resultMap = new HashMap<>();
		 resultMap.put("resultCode", 200);
		 
		 try {
			 
			 int result = service.addUser(user);
			 
			 if(result < 0) {
				 throw new Exception();
			 }
			 			
		 }catch (Exception e) {
			resultMap.put("resultCode", 500);
			 e.printStackTrace();
		}
		 
		 
		 
		 return resultMap;
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
