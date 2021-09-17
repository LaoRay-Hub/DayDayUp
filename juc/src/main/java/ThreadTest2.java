import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadTest2 {
    public static void main(String[] args) {

        new Thread(()->{

            while (true){
                log.info("111111111");
            }
        },"t1").start();

        new Thread(()->{

            while (true){
                log.info("22222222");
            }
        },"t2").start();
    }
}
