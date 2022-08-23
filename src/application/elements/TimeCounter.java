package application.elements;

import java.util.logging.Level;

import application.MainController;
import javafx.application.Platform;

public class TimeCounter extends Thread{

	public static long time = 0;
	public static Object toLock = new Object();
	
	public TimeCounter() {
		
		setPriority(10);
	}
	
	public void run() {

		while(!MainController.endOfSimulation) {
			
			if(!MainController.run.get()) {
				
				synchronized(toLock) {
					try {
						toLock.wait();
					} catch (InterruptedException e) {
						
						MainController.logger.log(Level.WARNING, null, e);
					}
				}
			}
			
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					
					long current = ++time;
					MainController.updateTime(current);
					
				}
				
			});
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				
				MainController.logger.log(Level.WARNING, null, e);
			}
		}
	}
}