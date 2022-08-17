package application.elements;

import java.util.Random;
import java.util.Vector;
import javafx.scene.paint.Color;

import application.elements.figures.Figure;
import application.elements.figures.FloatingFigure;
import application.elements.figures.RegularFigure;
import application.elements.figures.SuperFastFigure;

public class Player {

	private String username;
	private Vector<Figure>figures = new Vector<>(4);
	private int playingFigure = 0;
	
	public Player(String username, Color color) {
		
		this.username = username;
		
		Random rand = new Random();
		int counter = 4;
		while(counter > 0) {
			
			int number = rand.nextInt(3) + 1;
			
			switch(number) {
			
			case 1:
				figures.add(new RegularFigure(color));
				break;
			case 2:
				figures.add(new FloatingFigure(color));
				break;
			default:
				figures.add(new SuperFastFigure(color));
				break;
				
			}
			
		counter--;
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public Vector<Figure> getFigures(){
		return figures;
	}
	
	public void setPlayingFigure(int position) {
		playingFigure = position;
	}
	
	public Figure getPlayingFigure() {
		
		return figures.elementAt(playingFigure);
	}
	
	public Color getColor() {
		return figures.elementAt(0).getColor();
	}
	
	@Override
	public String toString() {

		String ret = "Igrac " + username + ":\n";
		for(var element : figures) {
			
			ret += element.print() + "\n";
		}
		
		return ret;
	}
}
