package by.bsuir.publisher.service;

import org.springframework.stereotype.Component;

@Component
public class LogWaitSomeTime {

    // Метод для тестирования отображения на экран работы Redis
    public void WaitSomeTime() {
        System.out.println("Redis caching works begin");
        try {
            System.out.println("Redis caching works...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Redis caching works end");
    }
}
