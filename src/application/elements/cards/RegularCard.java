package application.elements.cards;

import javafx.scene.image.ImageView;

public class RegularCard extends Card{

	private int id;
	
	public RegularCard(ImageView image, int id) {
		super(image);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		
		return "Izvučena je regularna karta sa brojem " + id + ".";
	}
}
