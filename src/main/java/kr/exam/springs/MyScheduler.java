package kr.exam.springs;

import org.springframework.scheduling.annotation.Scheduled;

public class MyScheduler {

    // 매 5초마다 실행
    @Scheduled(fixedRate = 5000)
    public void runTask() {
        System.out.println("5초마다 실행되는 작업: " + System.currentTimeMillis());
    }

    // 매일 새벽 1시에 실행 (Cron 표현식 사용)
    @Scheduled(cron = "0 0 1 * * *")
    public void cronTask() {
        System.out.println("매일 새벽 1시에 실행되는 작업");
    }
}
