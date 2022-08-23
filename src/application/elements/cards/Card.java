package application.elements.cards;

import javafx.scene.image.ImageView;

public abstract class Card {

	protected ImageView image;
	
	public Card(ImageView image) {
		this.image = image;
	}
	
	public ImageView getImage() {
		return image;
	}
}
