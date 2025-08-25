package kr.exam.springs.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.exam.springs.board.vo.Board;

@Mapper
public interface BoardMapper {

	/**
	 * 게시글 전체 개수
	 * @return
	 */
	int getBoardTotal();

	/**
	 * 게시글 리스트 가져오기 
	 * @param param
	 * @return
	 */
	List<Board.Response> getBoardList(Map<String, Object> param);
	
}
