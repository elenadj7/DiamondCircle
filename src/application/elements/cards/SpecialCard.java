package application.elements.cards;

import javafx.scene.image.ImageView;

public class SpecialCard extends Card{

	public SpecialCard(ImageView image) {
		super(image);
	}
	
	@Override
	public String toString() {
		
		return "Izvučena je specijalna karta. Kreiraju se rupe na poljima. ";
	}
}
