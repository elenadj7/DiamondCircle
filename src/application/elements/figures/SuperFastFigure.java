package application.elements.figures;

import application.interfaces.IsSuperFast;
import javafx.scene.paint.Color;

public class SuperFastFigure extends Figure implements IsSuperFast{

	public SuperFastFigure(Color color) {
		
		super(color);
		super.id = "SFF";
	}
}
