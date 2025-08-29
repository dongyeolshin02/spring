import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import kr.exam.springs.board.mapper.BoardMapper;
import kr.exam.springs.board.service.BoardService;
import kr.exam.springs.board.vo.Board;
import kr.exam.springs.common.page.PageVO;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
	    "file:src/main/webapp/WEB-INF/spring/root-context.xml", 
	    "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"
})
@Transactional
@Rollback
public class TestBoardService  {
	
	@Autowired
	 private BoardService service;
	
	@Autowired
	private BoardMapper mapper;

	@Test
	@DisplayName("게시판 리스트가져오기")
	public void testGetBoardList()  throws Exception{
		PageVO page = new PageVO();
		//given
		int total = mapper.getBoardTotal();
		page.setData(0, total);

		Map<String, Object> param = new HashMap<>();
		param.put("currentPage", 0);
		param.put("offSet", page.getOffSet());
		param.put("count", page.getCount());
		
		//when
		Map<String, Object> res = service.getBoardList(param);
		
		 // Then - 페이징 핵심 로직 검증
		assertNotEquals("결과가 null이면 안됩니다", res);
		assertNotEquals("dataList는 null이면 안됩니다", res.get("dataList"));
		assertNotEquals("pageHTML은 null이면 안됩니다", res.get("pageHTML"));
		
	}
	
	
	@Test
	public void insertBoard()  throws Exception {
		//given
		Board.Request request = new  Board.Request();
	    MockMultipartFile testFile = new MockMultipartFile(
	        "file", "test-file.txt", "text/plain", "테스트 파일 내용".getBytes()
	    );
		
		request.setTitle("테스트111");
		request.setWriter("admin");
		request.setContents("테스트");
		request.setFile(testFile);
		
		//when
		int result = service.writeBoard(request);
		//then
		assertEquals(result, 1);	
		Board.Detail savedBoard = mapper.getBoard(request.getBrdId());
	    assertNotNull(savedBoard, "등록된 게시글을 조회할 수 있어야 합니다");
	    assertEquals(savedBoard.getTitle(), "테스트111", "제목이 일치해야 합니다");
	} 
	
	

}
