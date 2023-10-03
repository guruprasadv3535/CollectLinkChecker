package apitesting;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

//
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import org.testng.annotations.Test;
//
public class SchedulerForNetBanking {
//	
//	@Test
//	public void schedulerForAllBanks() {
//		
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
//
//        // Schedule your Selenium code to run daily at a specific time
//        scheduler.scheduleAtFixedRate(() -> {
//            // Your Selenium code goes here
//        	System.out.println("Hi guru");
//            // This code will run at the specified interval
//        }, 0, 2, TimeUnit.MINUTES); // Change 1 to the desired number of days
//
////        // Add a shutdown hook to stop the scheduler gracefully
////        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
////            scheduler.shutdown();
////        }));
//		
//	}
//

	public void test() {
        System.out.println("Hi guru");
	}
	public void test2() {
		System.out.println("bye guru");
	}

	@Test
	public void schedular() throws Exception {

		int count=1;
		while (count<=6) {
			if (count == 1) {
				test();
				count++;
			}
			if (count == 2 || count == 3 || count == 4 || count == 5) {
                TimeUnit.MINUTES.sleep(1);
                test2();
                count++;
			}
			if(count==6) {
				TimeUnit.MINUTES.sleep(2);
				count=1;
			}
		}
	}
}
