package kr.exam.springs.board.vo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

public class Board {

	
	@Data
	public static class Request{
		private int brdId;
		private String title;
		private String contents;
		private String writer;
	}
	
	
	
	@Data
	public static class Response{
		private int brdId;
		private String title;
		private String writer;
		private int readCount;
		private int likeCount;
	
		private String createDate;
		
	}
}
