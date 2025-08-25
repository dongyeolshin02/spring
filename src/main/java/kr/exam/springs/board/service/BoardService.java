package kr.exam.springs.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import kr.exam.springs.board.controller.BoardController;
import kr.exam.springs.board.mapper.BoardMapper;
import kr.exam.springs.board.vo.Board;
import kr.exam.springs.common.page.PageVO;
import lombok.RequiredArgsConstructor;

@Service
//@NoArgsConstructor // 매개변수가 없는 기본생성자 
//@AllArgsConstructor // 모든 맴버변수를 매개변수로 갖는 생성자
@RequiredArgsConstructor // 맴버변수 중에 final 처리가 된 것들만 매개변수  가지는 생성자 
public class BoardService {

	private final BoardMapper mapper;

	
	public Map<String, Object> getBoardList(Map<String, Object> param) throws Exception{
		Map<String, Object> map = new HashMap<>();
		
		try {
		int currentPage = (int)param.get("currentPage");
		
		// 전체 리스트 개수
		int total = mapper.getBoardTotal();
		
		//페이징 객체를 호출 
		PageVO pageVO = new PageVO();
		//페이징 처리를 위한 값을 입력 
		pageVO.setData(currentPage, total);
		
		List<Board.Response> boardList = new ArrayList<>();
		
		if(total > 0) {
			param.put("offSet", pageVO.getOffSet());
			param.put("count", pageVO.getCount());
			
			boardList = mapper.getBoardList(param);
		}
		
		
		map.put("currentPage", currentPage);
		map.put("dataList", boardList);
		map.put("total", total);
		map.put("pageHTML", pageVO.pageHTML());
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return map;
	}
	
}
