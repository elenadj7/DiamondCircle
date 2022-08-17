package application.elements.cards;

import javafx.scene.image.Image;

public class RegularCard extends Card{

	private int id;
	
	public RegularCard(Image image, int id) {
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
		
		return "Izvucena je regularna karta sa brojem " + id;
	}
}
