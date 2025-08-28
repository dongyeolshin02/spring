package kr.exam.springs.user.vo;

import lombok.Data;

public class Users {

	@Data
	public static class LoginUser {
		private String userId;
		private String userName;
		private String birth;
	}
	
	
	@Data
	public static class UserInfo {
		
		private String userId;
		private String passwd;
		private String userName;
		private String birth;
		private String phone;
		private String email;
	}
}
