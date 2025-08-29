import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import kr.exam.springs.board.mapper.BoardMapper;
import kr.exam.springs.board.service.BoardService;
import kr.exam.springs.board.vo.Board;

@ExtendWith(MockitoExtension.class)
public class TestMockBoardService {
	
	  
	@Autowired
	private MockMvc mockMvc;
    @Mock
    BoardMapper mapper;
    @Mock
    BoardService service;
    
    @BeforeEach
    void setUp() {
        service = new BoardService(mapper);
    }
    
    @Test
    void totalZero_returnsEmpty_withoutCallingList() throws Exception {
        // given
        when(mapper.getBoardTotal()).thenReturn(0);
        Map<String, Object> in = new HashMap<>();
        in.put("currentPage", 1);

        
        
        // when
        Map<String, Object> out = service.getBoardList(in);

        // then
        assertEquals(0, out.get("total"));
        assertTrue(out.containsKey("currentPage"));
        assertTrue(out.containsKey("dataList"));
        assertTrue(out.containsKey("pageHTML"));
        assertEquals(Collections.emptyList(), out.get("dataList"));

        // list 조회는 호출되면 안 됨
        verify(mapper, never()).getBoardList(any());
    }
    
    
	@Test
	public void insertBoard()  throws Exception {
		// given
        Board.Request request = new Board.Request();
        MockMultipartFile testFile =
                new MockMultipartFile("file","test-file.txt","text/plain","테스트 파일 내용".getBytes());
          
      
        request.setBrdId(123); 
        request.setTitle("테스트111");
        request.setWriter("admin");
        request.setContents("테스트");
        request.setFile(testFile);

        when(mapper.insertBoard(any())).thenReturn(1);   
        when(mapper.insertBoardFiles(any())).thenReturn(1);
       
        int result = service.writeBoard(request);

        // then
        assertEquals(1, result);
        assertEquals(123L, request.getBrdId());
        //동작 검증
        verify(mapper).insertBoard(request);
        verify(mapper).insertBoardFiles(any());  
	} 

	
	
	@Test
    public void boardListView() throws Exception {
        // given
        Map<String, Object> param = new HashMap<>();
        param.put("currentPage", 0);
        
        List<Board.Response> mockBoardList =new ArrayList<>();
        
       when(mapper.getBoardTotal()).thenReturn(10);   
        when(mapper.getBoardList(any())).thenReturn(mockBoardList);          
        // 서비스 Mock 설정
        Map<String, Object> result = service.getBoardList(param);
      
        assertEquals(0, result.get("currentPage"));
        assertEquals(10, result.get("total"));
        assertEquals(mockBoardList, result.get("dataList"));
      
    }
}
