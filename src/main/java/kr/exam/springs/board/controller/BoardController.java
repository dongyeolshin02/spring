package kr.exam.springs.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.exam.springs.board.service.BoardService;
import kr.exam.springs.service.TestService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;

	// 화면 + 데이터를 분리해서  리턴 
//	@RequestMapping("/list.do")
//	public String boardListView(Model m) {
//		m.addAttribute("key",  "값");
//		return "board/boardList";
//	}
	
	
	@RequestMapping("/list.do")
	public ModelAndView boardListView(@RequestParam(name="currentPage", defaultValue = "0") int currentPage) {
		ModelAndView view = new ModelAndView();
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			map.put("currentPage", currentPage);
			map = service.getBoardList(map);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		view.addObject("data", map);
		view.setViewName("board/boardList");
		
		return view;
	}
	
}
