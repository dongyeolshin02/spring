package kr.exam.springs.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	//글등록
	int insertBoard(Board.Request request);
	//파일 등록
	int  insertBoardFiles(Board.BoardFiles files);
	//게시글 상세
	Board.Detail getBoard(@Param("brdId") int brdId);
	//첨부파일 정보 
	Board.BoardFiles getBoardFiles(@Param("bfId") int bfId);
	//좋아요 올리기
	int updateLikeCount(Map<String, Object> param);
	//게시글 조회수 올리기
	int updateReadCount(@Param("brdId")int brdId, @Param("readCount")int readCount);
	
	//게시글 수정
	int updateBoard(Board.Detail detail);
	
	//게시글 지우기
	int deleteBoard(@Param("brdId") int brdId);
	//파일하나 지우기
	int deleteBoardFile(@Param("bfId") int bfId);
	//모든 파일 지우기
	int deleteAllBoardFiles(@Param("brdId") int brdId);
	
}
