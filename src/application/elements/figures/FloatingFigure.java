package application.elements.figures;

import application.interfaces.CanFloat;
import javafx.scene.paint.Color;

public class FloatingFigure extends Figure implements CanFloat{

	public FloatingFigure(Color color) {
		
		super(color);
		super.id = "FF";
	}
}
