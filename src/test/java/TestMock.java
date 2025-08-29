import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.exam.springs.board.controller.BoardController;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
	    "file:src/main/webapp/WEB-INF/spring/root-context.xml", 
	    "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"
	})
@WebAppConfiguration
public class TestMock {

	@Autowired
    private WebApplicationContext webContext;  // 추가

	private MockMvc mockMvc;	
	@Autowired
	BoardController controller;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	    }
	 
	 
	 
	 
	@Test
	public void testBoardListView2() throws Exception {
		mockMvc.perform(get("/board/list.do"))
	               .andExpect(status().isOk())
	               .andExpect(view().name("board/boardList"))
	               .andDo(print());
	}
	
}
