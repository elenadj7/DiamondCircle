package application.elements.figures;

import application.interfaces.CanFloat;
import javafx.scene.paint.Color;

public class FloatingFigure extends Figure implements CanFloat{

	public FloatingFigure(Color color, int lastElement) {
		
		super(color, lastElement);
		super.id = "FF";
	}
	
	public int move(int cardNumber) {
		
		int move = 0;
		move += numberOfDiamonds;
		move += cardNumber;
		numberOfDiamonds = 0;
		
		return move;
	}
}
