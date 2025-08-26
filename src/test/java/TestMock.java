import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.exam.springs.board.controller.BoardController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	    "file:src/main/webapp/WEB-INF/spring/root-context.xml", 
	    "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"
	})
@WebAppConfiguration
public class TestMock {

	

	private MockMvc mockMvc;
	
	@InjectMocks
	BoardController controller;
	
	 
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	    }
	 
	 
	 
	 
	@Test
	   public void testBoardListView2() throws Exception {
	        mockMvc.perform(get("/board/list2.do"))
	               .andExpect(status().isOk())
	               .andExpect(view().name("board/boardList2"))
	               .andDo(print());
	    }
	
}
