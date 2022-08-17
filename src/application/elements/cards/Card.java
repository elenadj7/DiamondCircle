package application.elements.cards;

import javafx.scene.image.Image;

public abstract class Card {

	protected Image image;
	
	public Card(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
}
