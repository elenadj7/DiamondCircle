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

	public boolean hasFigure() {
		return hasFigure;
	}
	
	public boolean hasDiamond() {
		return hasDiamond;
	}
	
	public boolean hasHole() {
		return hasHole;
	}
	
	public void hasFigure(boolean true_or_false) {
		hasFigure = true_or_false;
	}
	
	public void hasDiamond(boolean true_or_false) {
		hasDiamond = true_or_false;
	}
	
	public void hasHole(boolean true_or_false) {
		hasHole = true_or_false;
	}
	
	public Label getLabel() {
		return label;
	}
}
