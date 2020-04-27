import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author draper_hxy
 */
public class Test {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        System.out.println("启动了");

        executorService.schedule(new Runnable() {
            public void run() {
                System.out.println("hello");
            }
        }, 3, TimeUnit.SECONDS) ;
    }

}
