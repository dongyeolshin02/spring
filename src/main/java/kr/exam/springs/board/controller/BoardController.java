package kr.exam.springs.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.exam.springs.board.service.BoardService;
import kr.exam.springs.board.vo.Board;
import kr.exam.springs.user.vo.Users;
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
	public ModelAndView boardListView2(@RequestParam(name="currentPage", defaultValue = "0") int currentPage) {
		ModelAndView view = new ModelAndView("board/boardList2");
		view.addObject("currentPage", currentPage);
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
	public ModelAndView  writeBoard(@ModelAttribute Board.Request request,
			                        @SessionAttribute("userInfo") Users.LoginUser user)  {
	
		int result = 0;
		String msg = "";
		
		ModelAndView view = new ModelAndView();
		view.setViewName("board/result");
		try {
			//글쓴이 등록 
			request.setWriter(user.getUserId());
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
	
	
	
	@PostMapping("/update.do")
	public ModelAndView  updateBoard(@ModelAttribute Board.Request request)  {
	
		int result = 0;
		String msg = "";
		
		ModelAndView view = new ModelAndView();
		view.setViewName("board/result");
		try {
			
			result = service.updateBoard(request);
			msg =  result > 0 ? "글이 수정되었습니다." : "글수정이 실패했습니다.";
					
		}catch (Exception e) {
			msg = "글수정 중에 오류가 발생했습니다.";
			e.printStackTrace();
		}finally {
			view.addObject("msg", msg);
		}
		
		
		return view;
	}
	
	
	@GetMapping("/delete.do")
	public ModelAndView  deleteBoard(@RequestParam("brdId") int brdId)  {
		
		int result = 0;
		String msg = "";
		
		ModelAndView view = new ModelAndView();
		view.setViewName("board/result");
		try {
			
			result = service.deleteBoard(brdId);
			msg =  result > 0 ? "글이 삭제되었습니다." : "글삭제가 실패했습니다.";
					
		}catch (Exception e) {
			msg = "글삭제 중에 오류가 발생했습니다.";
			e.printStackTrace();
		}finally {
			view.addObject("msg", msg);
		}
				
		return view;
	}
	
	
	
	
	
	@GetMapping("/detail.do")
	public ModelAndView getBoardDetailView(@RequestParam("brdId") int brdId,
											@RequestParam("currentPage") int currentPage,
											@CookieValue(name="board", defaultValue = "")String cookiValue,
											HttpServletResponse resp) {
		ModelAndView view = new ModelAndView();
		view.setViewName("board/boardDetail");
		Board.Detail detail = null;
		try {
			
			detail = service.getBoard(brdId, cookiValue, resp);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		view.addObject("vo", detail);
		view.addObject("currentPage", currentPage);
		
		return view;
	}
	
	
	@GetMapping("/write/view.do")
	public ModelAndView getWriteView(@RequestParam("brdId") int brdId) {
		ModelAndView view = new ModelAndView();
		view.setViewName("board/updateForm");
		Board.Detail detail = null;
		try {
			
			detail = service.getBoard(brdId);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		view.addObject("vo", detail);
		
		return view;
	}
	
	
	
	@GetMapping("/down.do")
	public void getFileDown(@RequestParam("bfId") int bfId,
							HttpServletResponse resp)  throws Exception{
		
		try {
			
			Board.BoardFiles boardFile = service.getBoardFiles(bfId);
			if(boardFile == null) {
				throw new Exception("DB Error");
			}
			
			String fullPath = boardFile.getFilePath() + boardFile.getStoredName();
			
			File file = new File(fullPath);
			
			if(!file.exists()) {
				throw new Exception("File not found");
			}
			
			//한글깨짐방지를 위한 인코딩
			String encodedName = URLEncoder.encode(boardFile.getFileName(), "UTF-8").replaceAll("\\+", "%20");
			//파일길이
			resp.setContentLength( (int)file.length());
			//브라우저가 다운로드 창을 출력하게 만들자.
			//바이너리 파일 다운로드 
			resp.setContentType("application/octet-stream");
			//첨부파일 등록
			resp.setHeader("Content-Disposition", "attachment;filename=\"" + encodedName + "\"");
			
			//밖에 try-catch가 있어서 안해도 되지만...연습삼아 해보자.
			try(
				   FileInputStream in = new FileInputStream(file);
				   BufferedInputStream bf = new BufferedInputStream(in);
				   OutputStream out = resp.getOutputStream();
				   BufferedOutputStream os = new BufferedOutputStream(out);
				){
				
				
				byte[] buffer = new byte[1024];
				int length = 0;
				
				while( (length = bf.read(buffer))!= -1 )  {
					os.write(buffer, 0, length);
				}
								
			}catch (Exception e) {
				throw new Exception();
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			
		}
		
	}
	
	@ResponseBody
	@PatchMapping("/like.do")
	//@RequestBody  json 문서는 요청객체 바디에서 직접 꺼내야한다....`
	public Map<String, Object> updateLike(@RequestBody Map<String, Object> map){
		
		String msg = "";
		map.put("msg", "");
		try {
			
			int result = service.updateLikeCount(map);
			
			if(result < 0) {
				throw new Exception("업데이트 실패");
			}
			
		}catch (Exception e) {
			msg = e.getMessage() == null ? "업데이트 실패" : e.getMessage();
			map.put("msg", msg);
		}
		
		return map;	
	}
}















