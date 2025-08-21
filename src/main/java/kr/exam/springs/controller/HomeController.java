package kr.exam.springs.controller;

import java.util.HashMap;
import java.util.Map;

import kr.exam.springs.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class HomeController {

	private final TestService testService;

	
	
	//@RequestMapping(name="/hello.do")  //get , post 모두 가능 
	//@RequestMapping(name="/hello.do", method = RequestMethod.GET) // get방식 만 접근 가능 
	//@RequestMapping(name="/hello.do", method = RequestMethod.POST) // post 방식만 접근 가능 
	// Method 방식 + Mapping  이라고 하면 해당 method 전용 링크 가 된다.
	@GetMapping("/hello.do")
	public Map<String, Object> hello(){
		Map<String, Object> resultMap = new HashMap<>();
		int a = testService.test();
		resultMap.put("OK", a);
		return resultMap;
	}
	
}
