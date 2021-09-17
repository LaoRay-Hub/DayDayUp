import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread t = new Thread(() -> log.info("running"));

        t.setName("t1");
        t.start();

        log.info("running");


        Runnable running = () -> log.info("running");

        Thread t2 = new Thread(running, "t2");

        t2.start();


        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.info("running...");
            Thread.sleep(1000);
            return 1000;
        });

        Thread thread = new Thread(task,"callable");

        thread.start();

        log.info("{}",task.get());
    }
}
