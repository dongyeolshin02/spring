package kr.exam.springs.user.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.exam.springs.user.vo.Users;

@Mapper
public interface UserMapper {

	/**
	 * 로그인 사용자 가져오기 
	 * @param param
	 * @return
	 */
	Users.LoginUser getLoginUser(Map<String, Object> param);
}
