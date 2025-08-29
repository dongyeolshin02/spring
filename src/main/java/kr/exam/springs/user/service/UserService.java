package kr.exam.springs.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.stereotype.Service;

import kr.exam.springs.common.page.PageVO;
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
	
	public Map<String, Object> getUserList(Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		int currentPage = (int)param.get("currentPage");
		//전체 회원수
		int total = mapper.getAllUserCount(param);
		//페이지객체
		PageVO page = new PageVO();
		page.setData(currentPage, total);
		
		List<Users.UserInfo> userList = new ArrayList<>();
		
		if(total > 0 ) {
			//페이지 처리 범위를 파라메터 맵에 넣는다.
			param.put("offSet", page.getOffSet());
			param.put("count", page.getCount());
			//페이지에 보여줄 리스트 가져오기 
			userList = mapper.getUserList(param);
		}
		
		//클라이언트에 넘겨줄 데이터 셋 저장 
		resultMap.put("currentPage", currentPage);
		resultMap.put("total", total);
		resultMap.put("dataList", userList);
		resultMap.put("pageHTML", page.pageHTML());
		
		
		return resultMap;
	}
	
	
	
	public int addUser(Users.UserInfo  user) throws Exception  {
		
		int existsId = mapper.checkId(user.getUserId());
		
		if(existsId > 0) {
			 throw new DuplicateMemberException("아이디가 이미 사용중입니다");
		}
		
		int result = mapper.addUser(user);
		
		return result;
	}
	
	
	
	
	
	
}
