package kr.exam.springs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
	    "file:src/main/webapp/WEB-INF/spring/root-context.xml", 
	    "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"
})
class BoardServiceTest {
	
	@Autowired
	BoardService service;
	
	@Autowired
	BoardMapper mapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("게시글 리스트 가져오기")
	void testGetBoardList() throws Exception {
		//given
		Map<String, Object> param = new HashMap<>();
		param.put("currentPage", 0);
		
		//when
		 Map<String, Object> result = service.getBoardList(param);
		 List<Board.Response> list = (List<Board.Response>)result.get("dataList");
		 
		 //then
		 assertNotNull(result);
		 assertNotNull(list);
		 assertEquals(10, list.size());
		 
	}
	
	//실행 후 취소처리 를 위해 적용 
	@Transactional
	@Rollback
	@Test
	void insertBoardWithoutFile() throws Exception{
		//given
		Board.Request req = new Board.Request();
		req.setTitle("테스트모드1");
		req.setContents("테스트모드");
		req.setWriter("admin");
		
		//when
		int result = service.writeBoard(req);
		
		//then
		assertEquals(1, result);
		//게시글 출력
		Board.Detail de = service.getBoard(req.getBrdId());
		assertEquals("테스트모드1", de.getTitle(), "타이틀이 같다");
		assertNotNull(de);
		
	}
	
	
	@Transactional
	@Rollback
	@Test
	void insertBoardWithFile() throws Exception{
		//given
		Board.Request req = new Board.Request();
		req.setTitle("테스트모드1");
		req.setContents("테스트모드");
		req.setWriter("admin");
		//가짜 데이터 만들기
		MockMultipartFile mockFile = 
				new MockMultipartFile("file", 
						"test-file.txt",
						"text/plain", 
						"테스트내용".getBytes());
		
		req.setFile(mockFile);
		
		//when
		int result = service.writeBoard(req);
		
	
		//then
		assertEquals(1, result);
		//게시글 출력
		Board.Detail de = service.getBoard(req.getBrdId());
		//저장되었다면 select 되겠지
		assertNotNull(de);
		//파일저장했으니 리스트가 있겠지
		assertNotNull(de.getFiles());
		
		 
		
	}

}
