import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.exam.springs.board.mapper.BoardMapper;
import kr.exam.springs.board.service.BoardService;
import kr.exam.springs.common.page.PageVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	    "file:src/main/webapp/WEB-INF/spring/root-context.xml", 
	    "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"
})
public class TestBoardService  {
	
	@Autowired
	 private BoardService service;
	
	@Autowired
	private BoardMapper mapper;

	@Test
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
		assertNotNull("결과가 null이면 안됩니다", res);
		assertNotNull("dataList는 null이면 안됩니다", res.get("dataList"));
		assertNotNull("pageHTML은 null이면 안됩니다", res.get("pageHTML"));
	}

}
