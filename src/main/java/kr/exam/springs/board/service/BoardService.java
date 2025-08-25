package kr.exam.springs.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.exam.springs.board.mapper.BoardMapper;
import kr.exam.springs.board.vo.Board;
import kr.exam.springs.board.vo.Board.BoardFiles;
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
	
	@Transactional(
			rollbackFor = Exception.class,
			propagation = Propagation.REQUIRED, // 기존트랙션이 있음 사용, 없으면 생성 
			isolation = Isolation.READ_COMMITTED //적용된것만 읽기 > 데이터를 가져올때 같은 시간에 수정 중이거나 처리 중인건 제외 
			)
	public int writeBoard(Board.Request request) throws Exception {
		int result = 0;
		
		try {
			
			//1.글등록
			request.setWriter("admin");
			result = mapper.insertBoard(request);
			
			if(result < 0) {
				throw new Exception();
			}
			
			//2. 파일업로드
			Board.BoardFiles files = this.uploadFiles(request.getBrdId(), request.getFile());
			
			if(files != null) {
				//3. 파일 등록 
				result = mapper.insertBoardFiles(files);
				
				if(result < 0) {
					throw new Exception();
				}
			}
		
		}catch (Exception e) {
			 throw new RuntimeException("글 등록 중 오류");
		}
		return result;
	}
	
	
	public Board.BoardFiles  uploadFiles(int brdId, MultipartFile multiFile)  throws Exception{
		
		Board.BoardFiles  files = new BoardFiles();
		//내용이 없음 > 선택 안하면 비어 있음 
		if(multiFile.isEmpty()) {
			return null;
		}
		
		String fileName = multiFile.getOriginalFilename();
		String extention = fileName.substring( fileName.lastIndexOf(".") + 1 ) ;
		//중복없는 이름 만들기
		//UUID ?   네트워크 전송 시 생성하는 식별자 중복방지를 위함
		String uuId = UUID.randomUUID().toString().replaceAll("-", "");
		//길이를 줄이기
		uuId  = uuId.substring(0, 16);
		String storedName = uuId + "." + extention;
		
		// 파일경로
		String filePath ="C:\\files\\down\\board\\";
		//저장할 전체 경로 
		String fullPath = filePath + storedName;
		
		//빈파일(=아직 없음) 객체 만들기
		File newFile = new  File(fullPath);
		
		//파일  경로 없을 시 만들자
		if( !newFile.getParentFile().exists() ) {
			//상위...상위 경로들이 없을 때 다 만들어주기 
			newFile.getParentFile().mkdirs();
		}
		
		//백지 파일 만들기
		newFile.createNewFile();
		//복사
		multiFile.transferTo(newFile);
		
		
		//files.setBrdId(brdId);
		files.setFileName(fileName);
		files.setStoredName(storedName);
		files.setFilePath(filePath);
		files.setFileSize(multiFile.getSize());
	
		
		return files;
		
	}
	
}
