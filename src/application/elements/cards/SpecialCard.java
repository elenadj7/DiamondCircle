package application.elements.cards;

import javafx.scene.image.Image;

public class SpecialCard extends Card{

	public SpecialCard(Image image) {
		super(image);
	}
	
	@Override
	public String toString() {
		
		return "Izvucena je specijalna karta. Kreiraju se rupe na poljima. ";
	}
}
