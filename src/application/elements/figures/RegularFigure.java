package application.elements.figures;

import javafx.scene.paint.Color;

public class RegularFigure extends Figure {

	public RegularFigure(Color color, int lastElement) {
		
		super(color, lastElement);
		super.id = "RF";
	}
	
	public int move(int cardNumber) {
		
		int move = 0;
		move += numberOfDiamonds;
		move += cardNumber;
		numberOfDiamonds = 0;
		return move;
		
	}
}
