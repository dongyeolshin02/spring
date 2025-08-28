package kr.exam.springs.user.mapper;

import java.util.List;
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
	//사용자 전체 수
	int getAllUserCount(Map<String, Object> param);
	//페이지에 보여줄 사용자리스트 
	List<Users.UserInfo> getUserList(Map<String, Object> param);
}
