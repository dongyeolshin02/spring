package kr.exam.springs.schedule;

import org.springframework.scheduling.annotation.Scheduled;

public class MySchedulerTest {

	
	@Scheduled(fixedRate = 5000)
	public void test() {
		System.out.println(5000);
		
	}
}
