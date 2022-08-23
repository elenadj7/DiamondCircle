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
	protected int lastElement;
	protected Vector<Integer> path = new Vector<>();
	
	public Figure(Color color, int lastElement) {
		
		this.color = color;
		serialNumber = ++instances;
		currentPosition = 0;
		this.lastElement = lastElement;
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
	
	public String getId() {
		return id;
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
	
	public String print() {
		
		String colorString = "";
		
		switch(color.toString()) {
		
		case "0x0000ffff":
			colorString += "plava";
			break;
			
		case "0xff0000ff":
			colorString += "crvena";
			break;
			
		case "0xcccc00ff":
			colorString += "zuta";
			break;
			
		case "0x008000ff":
			colorString += "zelena";
			break;
		}
		long time = endTime - startTime;
		String ret = "Figura " + serialNumber + " (" + id +  ", " + colorString + ", " + time + "s)";
		
		for(var element : path) {
			
			ret += "-";
			ret += element;
		}
		
		ret += " stigla do cilja ";
		
		if(path.lastElement() == lastElement) {
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
	
	public abstract int move(int cardNumber);
}
