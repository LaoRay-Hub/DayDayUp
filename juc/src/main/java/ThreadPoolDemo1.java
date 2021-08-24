import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo1 {


    public static void main(String[] args) {

        //一池多线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //一池一线程
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        //一池可扩容线程
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        try {
            for (int i = 1; i <= 10; i++) {

                executorService2.execute(()->{

                    System.out.println(Thread.currentThread().getName()+"办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService2.shutdown();
        }




    }
}
