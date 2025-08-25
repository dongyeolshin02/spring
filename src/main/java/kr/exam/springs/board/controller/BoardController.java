package kr.exam.springs.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.exam.springs.board.service.BoardService;
import kr.exam.springs.board.vo.Board;
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
	
	
	
	@RequestMapping("/list2.do")
	public ModelAndView boardListView2() {
		ModelAndView view = new ModelAndView("board/boardList2");
		return view;
	}
	
	// get방식으로만 호출해야함
	// view가 아니라 ajax 로 인한 data를 return 해야함 
	@ResponseBody
	@GetMapping("/list/data.do")
	public Map<String, Object> getListData(@RequestParam(name="currentPage", defaultValue = "0")int currentPage){
		Map<String, Object> dataMap = new HashMap<>();
		
		try {
			
			dataMap.put("currentPage", currentPage);
			dataMap = service.getBoardList(dataMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	
	@GetMapping("/form.do")
	public ModelAndView writeView( ) {
		return new ModelAndView("board/writeForm");
	}
	
	
	@PostMapping("/write.do")
	public ModelAndView  writeBoard(@ModelAttribute Board.Request request)  {
	
		int result = 0;
		String msg = "";
		
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/board/list2.do");
		try {
			
			result = service.writeBoard(request);
			msg =  result > 0 ? "새글이 등록되었습니다." : "글등록이 실패했습니다.";
					
		}catch (Exception e) {
			msg = "글등록 중에 오류가 발생했습니다.";
			e.printStackTrace();
		}finally {
			view.addObject("msg", msg);
		}
		
		
		return view;
	}
	
}















