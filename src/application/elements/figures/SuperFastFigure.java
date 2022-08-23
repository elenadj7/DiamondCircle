package application.elements.figures;

import application.interfaces.IsSuperFast;
import javafx.scene.paint.Color;

public class SuperFastFigure extends Figure implements IsSuperFast{

	public SuperFastFigure(Color color, int lastElement) {
		
		super(color, lastElement);
		super.id = "SFF";
	}
	
	public int move(int cardNumber) {
		
		int move = 0;
		int res = 2*(numberOfDiamonds + cardNumber);
		move += res;
		numberOfDiamonds = 0;
		return move;
	}
}
