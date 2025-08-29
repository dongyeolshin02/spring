package kr.exam.springs.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean isTrue = true;
		
		HttpSession session = request.getSession();
		//로그인 상태가 아니라면 
		if(session.getAttribute("userInfo") == null) {
			
			if(this.isAjaxRequest(request)) {
				//ajax 처리
				//너 오류났다고 return  문서 만들어줘야함 타입 json 문서
			
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("application/json");
				
				PrintWriter out = response.getWriter();
				
				JSONObject jsonDoc = new JSONObject();
				jsonDoc.append("msg", "로그인이 필요한 기능입니다. 로그인 후 사용하세요");
				
				out.write(jsonDoc.toString());
				isTrue = false;
				
			}else {
				isTrue = false;
				response.sendRedirect("/user/login.do");
			}
			
		}
		
		return isTrue;
	}
	
	//ajax 도 로그인이 아니면 작동하면 아니된다.
	private boolean isAjaxRequest (HttpServletRequest request) {	
		
		//ajax 통신일 때 X-Requested-With 값이 XMLHttpRequest 로 나온다.
		if(request.getHeader("X-Requested-With") != null &&
				 "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			return true;
		}
		
		return false;
	}

	
}
