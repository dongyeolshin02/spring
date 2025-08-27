package kr.exam.springs.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import kr.exam.springs.user.mapper.UserMapper;
import kr.exam.springs.user.vo.Users;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //생성자를 통한 의존 주입
public class UserService {
	
	private final UserMapper mapper;

	public int userLogin(Map<String, Object> param, 
			               HttpServletRequest req) throws Exception{
		int result = 0;
		
		//사용자 정보 가져오기
		Users.LoginUser user = mapper.getLoginUser(param);
		
		if(user != null) {
			result = 1;
			//세션에 저장
			HttpSession session = req.getSession();
			session.setAttribute("userInfo", user);
		}
		
		return result;
	}
}
