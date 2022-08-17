package application.elements.figures;

import java.util.Vector;
import javafx.scene.paint.Color;

public abstract class Figure {

	public static int instances = 0;
	
	protected Color color;
	protected int currentPosition;
	protected int numberOfDiamonds;
	protected int serialNumber;
	protected String id;
	protected long startTime;
	protected long endTime;
	protected boolean reachedTheGoal;
	protected boolean playing = false;
	protected Vector<Integer> path = new Vector<>();
	
	public Figure(Color color) {
		
		this.color = color;
		serialNumber = ++instances;
	}
	
	public void setStartTime(long time) {
		startTime = time;
	}
	
	public void setCurrentPosition(int position) {
		currentPosition = position;
	}
	
	public void addToPath(int field) {
		path.add(field);
	}
	
	public void setNumberOfDiamonds(int diamonds) {
		numberOfDiamonds = diamonds;
	}
	
	public void setEndTime(long time) {
		endTime = time;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void playing(boolean true_or_false) {
		playing = true_or_false;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public Vector<Integer> getPath(){
		return path;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public int getNumberOfDiamonds() {
		return numberOfDiamonds;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public void reachedTheGoal(int lastField) {
		
		if(currentPosition == lastField) {
			
			reachedTheGoal = true;
		}
		
		reachedTheGoal = false;
	}
	
	public String print() {
		
		String ret = "Figura (" + id +  ", " + color + ")";
		
		for(var element : path) {
			
			ret += "-";
			ret += element;
		}
		
		ret += "stigla do cilja ";
		
		if(reachedTheGoal) {
			ret += "da";
		}
		else {
			ret += "ne";
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		return "Figura " + serialNumber;
	}
}
