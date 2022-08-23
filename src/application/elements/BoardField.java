package application.elements;

import javafx.scene.control.Label;

public class BoardField {

	private boolean hasFigure = false;
	private boolean hasDiamond = false;
	private boolean hasHole = false;
	private Label label;
	
	
	public BoardField(Label label) {
		
		this.label = label;
	}

	public synchronized boolean hasFigure() {
		return hasFigure;
	}
	
	public synchronized boolean hasDiamond() {
		return hasDiamond;
	}
	
	public synchronized boolean hasHole() {
		return hasHole;
	}
	
	public synchronized void hasFigure(boolean true_or_false) {
		hasFigure = true_or_false;
	}
	
	public synchronized void hasDiamond(boolean true_or_false) {
		hasDiamond = true_or_false;
	}
	
	public synchronized void hasHole(boolean true_or_false) {
		hasHole = true_or_false;
	}
	
	public synchronized Label getLabel() {
		return label;
	}
}
