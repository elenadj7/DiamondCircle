package application.elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;

import application.MainController;
import application.elements.cards.Card;
import application.elements.cards.RegularCard;
import application.elements.cards.SpecialCard;
import application.elements.figures.Figure;
import application.interfaces.CanFloat;
import javafx.application.Platform;

public class Game extends Thread{

	private Vector<Player> players;
	private Vector<Card> cards = new Vector<>();
	private Vector<Figure> currentFigures = new Vector<>();
	private Figure currentFigure;
	private Vector<Integer> holes = new Vector<>();
	private Vector<Figure> eatenFigures = new Vector<>();
	private Vector<Integer> matrixPath = new Vector<>();
	
	public static Object toLock = new Object();
	
	public Game(Vector<Player> players, Vector<Card> cards, Vector<Integer> matrixPath) {
		this.players = players;
		this.cards.addAll(cards);
		
		for(var player : players) {
			currentFigures.add(player.getPlayingFigure());
		}
		
		this.matrixPath.addAll(matrixPath);
	}
	
	public Player getPlayer(Figure figure) {
		
		for(var element : players) {
			if(element.getFigures().contains(figure)) {
				return element;
			}
		}
		return null;
	}
	
	public void run() {
		
		long counter = 0;
		Collections.shuffle(cards);
	
		while(!MainController.endOfSimulation) {
			
			if(!MainController.run.get()) {
				
				synchronized(toLock) {
					try {
						toLock.wait();
					} catch (InterruptedException e) {
						
						MainController.logger.log(Level.SEVERE, null, e);
						
					}
				}
			}
			
			if(!currentFigures.isEmpty()) {
				
				int position = (int) (counter % currentFigures.size());
				
				currentFigure = currentFigures.elementAt(position);
				
				Card card = cards.elementAt(0);
				cards.removeElementAt(0);
				cards.add(card);
				
				if(!MainController.run.get()) {
					
					synchronized(toLock) {
						try {
							toLock.wait();
						} catch (InterruptedException e) {
							
							MainController.logger.log(Level.SEVERE, null, e);
							
						}
					}
				}
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						
						MainController.updateCard(card);
						
					}
					
				});
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					
					MainController.logger.log(Level.SEVERE, null, e);
				}
				
				if(card instanceof RegularCard) {
					
					int cardFields = ((RegularCard) card).getId();
					int oldPosition = currentFigure.getCurrentPosition();
				
					if(oldPosition == 0) {
						
						currentFigure.setStartTime(TimeCounter.time);
						currentFigure.addToPath(matrixPath.elementAt(0));
						
						if(!MainController.run.get()) {
							
							synchronized(toLock) {
								try {
									toLock.wait();
								} catch (InterruptedException e) {
									
									MainController.logger.log(Level.SEVERE, null, e);
									
								}
							}
						}
						
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
									
								MainController.setStartFigure(currentFigure, matrixPath, 0);
								
							}
								
						});
						
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							
							MainController.logger.log(Level.SEVERE, null, e);
						}
					}
					
					
					int fieldsToMove = currentFigure.move(cardFields);
					BoardField field = null;
					
					int i = 0;
					int diamonds = 0;
					boolean old = false;
					
					while(i < fieldsToMove) {
					
						currentFigure.setCurrentPosition(currentFigure.getCurrentPosition() + 1);
						int nextFieldIndex = currentFigure.getCurrentPosition();
				
						if(nextFieldIndex >= matrixPath.size() - 1) {
							
							int positionOfFigure = currentFigures.indexOf(currentFigure);
							currentFigure.setEndTime(TimeCounter.time);
							currentFigures.removeElement(currentFigure);
							Player player = getPlayer(currentFigure);
							if(player.nextFigure()) {
								
								currentFigures.insertElementAt(player.getPlayingFigure(), positionOfFigure);

							}
							
							old = true;
							currentFigure.addToPath(matrixPath.elementAt(nextFieldIndex));
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									MainController.removeOldFigure(currentFigure, oldPosition, matrixPath);
								}
								
							});
							
							break;
						}
						
						field = MainController.board.get(matrixPath.elementAt(nextFieldIndex));
							
						if(!field.hasFigure()) {
								
							if(field.hasDiamond()) {
									
								field.hasDiamond(false);
								diamonds++;
							}
								
							i++;
							
						}
						
						currentFigure.addToPath(matrixPath.elementAt(nextFieldIndex));
					}
					
					currentFigure.setNumberOfDiamonds(diamonds);
					
					MainController.board.get(matrixPath.elementAt(oldPosition)).hasFigure(false);
					if(!old) {
						
						MainController.board.get(matrixPath.elementAt(currentFigure.getCurrentPosition())).hasFigure(true);
					}
					
					if(!MainController.run.get()) {
						
						synchronized(toLock) {
							try {
								toLock.wait();
							} catch (InterruptedException e) {
								
								MainController.logger.log(Level.SEVERE, null, e);
								
							}
						}
					}
					
					if(!old) {
						
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								
								MainController.updateFigure(oldPosition, currentFigure, getPlayer(currentFigure), matrixPath);
							}
							
						});
					}
				}
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					MainController.logger.log(Level.SEVERE, null, e);
				}
				
				if(card instanceof SpecialCard) {
					
					Vector<Integer> holes = new Vector<>();
					Vector<Figure> figures = new Vector<>();
					Random rand = new Random();
					
					while(holes.size() != MainController.numberOfHoles) {
						
						int number = rand.nextInt(matrixPath.size() - 1);
							
						BoardField field = MainController.board.get(matrixPath.elementAt(number));
						if(!field.hasDiamond() && !holes.contains(number)) {
							holes.add(number);
								
							if(field.hasFigure()) {
									
								for(var current : currentFigures) {
										
									if(current.getCurrentPosition() == number) {
											
										if(!(current instanceof CanFloat)) {
											
											Player player = getPlayer(current);
											int positionOfFigure = currentFigures.indexOf(current);
											currentFigures.removeElement(current);
											current.setEndTime(TimeCounter.time);
												
											if(player.nextFigure()) {
													
												currentFigures.insertElementAt(player.getPlayingFigure(), positionOfFigure);
											}
										}
										else {
											field.hasFigure(false);
										}
											
										figures.add(current);
										break;
									}
								}
							}
						}
					}
					
					if(!MainController.run.get()) {
						
						synchronized(toLock) {
							try {
								toLock.wait();
							} catch (InterruptedException e) {
								
								MainController.logger.log(Level.SEVERE, null, e);
								
							}
						}
					}
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
								
							MainController.updateHoles(holes, figures, matrixPath);
						}
							
					});
					
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						
						MainController.logger.log(Level.SEVERE, null, e);
					}
					
					if(!MainController.run.get()) {
						
						synchronized(toLock) {
							try {
								toLock.wait();
							} catch (InterruptedException e) {
								
								MainController.logger.log(Level.SEVERE, null, e);
								
							}
						}
					}
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							
							MainController.deleteHoles(holes, figures, matrixPath);
						}
						
					});
				
				
					try {
						sleep(1000);
					} catch (InterruptedException e) {

						MainController.logger.log(Level.SEVERE, null, e);
					}
				}
				
				counter++;
			}
			else {
				
				MainController.endOfSimulation = true;
			}
			
		}
		try {
			File file = new File(System.getProperty("user.dir") + "\\list of games\\" + "IGRA_" + new Date().getTime() + ".txt");
			
			FileWriter writer = new FileWriter(file);
			
			for(var player : players) {
				
				writer.write(player.toString());
			}
			
			writer.write("Vrijeme trajanja simulacije: " + TimeCounter.time + "s");
			
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					
					MainController.endOfSimulation();
				}
				
			});
			
			writer.close();
		}catch(IOException e) {
			
			MainController.logger.log(Level.WARNING, null, e);
		}
	}
	
}