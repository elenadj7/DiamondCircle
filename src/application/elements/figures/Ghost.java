package application.elements.figures;

import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;

import application.MainController;
import application.elements.BoardField;
import javafx.application.Platform;

public class Ghost extends Thread{

	private int dimension;
	private Vector<Integer> path = new Vector<>();
	public static Object toLock = new Object();
	
	
	public Ghost(int dimension, Vector<Integer> path) {
		this.dimension = dimension;
		this.path = path;
	}
	
	public void run() {
		
		Vector<Integer> diamonds = new Vector<>();
		
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
				
			Random rand = new Random();
				
			int size = rand.nextInt(dimension) + 2;
				
			Vector<Integer> oldDiamonds = new Vector<>();
			oldDiamonds.addAll(diamonds);
				
			diamonds.removeAllElements();
				
			synchronized(MainController.board) {
				while(diamonds.size() != size) {
					int fieldPosition = rand.nextInt(path.size());
					int element = path.elementAt(fieldPosition);
					BoardField field = MainController.board.get(element);
					
					if(!diamonds.contains(element) && !field.hasFigure() && !field.hasHole()) {
						
						diamonds.add(element);
					}
				}
				
			}
				
			Platform.runLater(new Runnable(){
					
				@Override
				public void run() {
						
					synchronized(MainController.board) {
						
						MainController.updateDiamonds(diamonds, oldDiamonds);
					}
						
				}
					
			});
				
			try {
				sleep(5000);
			} catch (InterruptedException e) {
					
				MainController.logger.log(Level.WARNING, null, e);
			}	
		}
	}
}

