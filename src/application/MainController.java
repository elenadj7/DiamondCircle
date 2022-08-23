package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import application.elements.BoardField;
import application.elements.Game;
import application.elements.Player;
import application.elements.TimeCounter;
import application.elements.cards.Card;
import application.elements.cards.RegularCard;
import application.elements.cards.SpecialCard;
import application.elements.figures.Figure;
import application.elements.figures.Ghost;
import application.interfaces.CanFloat;
import application.validations.DataValidation;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController extends Application{
	
	public static Logger logger;
	public static FileHandler handler;
	public static boolean endOfSimulation = false;
	public static AtomicBoolean run = new AtomicBoolean(true);
	public static HashMap<Integer,BoardField> board = new HashMap<>();
	public static Label time;
	public static Label cardLabel;
	public static Label labelResults;
	public static Label numberOfGames;
	public static int numberOfPlayers;
	public static int numberOfHoles;
	public static int matrixDimension;
	
	
	public Vector<Integer> matrixPath = new Vector<>();
	private String[] config;
	private Vector<Player> players = new Vector<>();
	private Vector<Card> cards = new Vector<>();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			
			initialize();
			DataValidation validation = new DataValidation();
			validation.validate(config);
			Collections.shuffle(players);
			
			
			
			//postavljanje scene
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			root.setStyle("-fx-background-color:white");
			Scene scene = new Scene(root,1300,780);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Paint.valueOf("e7eaf6"));
			primaryStage.setScene(scene);
			primaryStage.setTitle("Diamond circle");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(System.getProperty("user.dir") + "\\images\\logo.png"));
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
				
			});
			primaryStage.show();
			
			
			
			
			time = (Label)scene.lookup("#time");
			cardLabel = (Label)scene.lookup("#cardLabel");
			labelResults = (Label)scene.lookup("#labelResults");
			
			
			
			
			Button filesList = (Button)scene.lookup("#filesList");
			filesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					new FilesListController().display();
				}
				
			});
			
	
			Button start_stop = (Button)scene.lookup("#start_stop");
			start_stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					
					if(!run.get()) {
						
						synchronized(Ghost.toLock) {
							Ghost.toLock.notify();
						}
						synchronized(TimeCounter.toLock) {
							TimeCounter.toLock.notify();
							
						}
						synchronized(Game.toLock) {
							Game.toLock.notify();
						}
						run.set(true);
						
					}
					else {
						
						run.set(false);
					}
				}
			});
			
			
			
			
			
			numberOfGames = (Label)scene.lookup("#numberOfGames");
			numberOfGames.setText("Trenutan broj odigranih igara: " + new File(System.getProperty("user.dir") + "\\list of games").list().length);
			
			VBox vboxBoard = (VBox)scene.lookup("#vboxBoard");
			//u zavisnosti od dimenzije matrice se postavljaju margine
			switch(matrixDimension) {
			
			case 7:
				BorderPane.setMargin(vboxBoard, new Insets(65,0,0,200));
				break;
				
			case 8:
				BorderPane.setMargin(vboxBoard, new Insets(40,0,0,170));
				break;
				
			case 9:
				BorderPane.setMargin(vboxBoard, new Insets(10,0,0,135));
				break;
				
			case 10:
				BorderPane.setMargin(vboxBoard, new Insets(0,0,0,100));
				break;
			}
			
			int position = 1;
			
			//iscrtavanje matrice
			for(int i = 0; i < matrixDimension; ++i) {
				
				HBox hbox = new HBox();
				
				for(int j = 0; j < matrixDimension; ++j) {
					
					Label label = new Label();
					label.setAlignment(Pos.CENTER);
					label.setFont(Font.font(16));
					label.setBorder(new Border(new BorderStroke(Paint.valueOf("#2a6171"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
					label.setTextFill(Color.rgb(42, 97, 113));
					if(matrixPath.contains(position)) {
						label.setBackground(new Background(new BackgroundFill(Color.rgb(215, 247, 245), new CornerRadii(3), Insets.EMPTY)));
					}
					else {
						label.setBackground(new Background(new BackgroundFill(Color.rgb(117, 202, 195), new CornerRadii(3), Insets.EMPTY)));
					}
					label.setPrefSize(60, 48);
					label.setText(position + "");
					board.put(position, new BoardField(label));
					HBox.setMargin(label, new Insets(5,2.5,2.5,5));
					hbox.getChildren().add(label);
					
					position++;
				}
				VBox.setMargin(hbox, new Insets(2.5,1.25,1.25,2.5));
				
				vboxBoard.getChildren().add(hbox);
			}
			
			
			
			//postavljanje figura na listview i korisnickih imena na gui 
			VBox figureList = (VBox)scene.lookup("#figureList");
			VBox listOfPlayers = (VBox)scene.lookup("#listOfPlayers");
			
			for(var player : players) {
				
				for(var figure : player.getFigures()) {
					
					Label labelFigure = new Label();
					labelFigure.setText(figure.toString() + " - " + figure.getId());
					labelFigure.setPrefWidth(150);
					labelFigure.setPrefHeight(35);
					labelFigure.setFont(Font.font("Arial", 15));
					labelFigure.setAlignment(Pos.CENTER);
					labelFigure.setTextFill(figure.getColor());
					
					labelFigure.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							
							new FigureWindowController().display(matrixDimension, figure, matrixPath);
							
						}
						
					});
					
					figureList.getChildren().add(labelFigure);
				}
				
				Label onePlayer = new Label();
				
				onePlayer.setText(player.getUsername());
				onePlayer.setFont(Font.font("Arial", 15));
				onePlayer.setPrefSize(205, 35);
				onePlayer.setAlignment(Pos.CENTER);
				onePlayer.setTextFill(player.getColor());
				
				listOfPlayers.getChildren().add(onePlayer);
			}
			
			//pokretanje threadova
			new TimeCounter().start();
			new Ghost(matrixDimension, matrixPath).start();
			new Game(players, cards, matrixPath).start();
			
		} catch(Exception e) {
			
			logger.log(Level.SEVERE, null, e);
		}
		finally {
			handler.close();
		}
	}
	
	public void initialize() throws IOException {
		
		try {
			handler =  new FileHandler("MyLogFile.log",true);
			logger = Logger.getGlobal();
			logger.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();  
			handler.setFormatter(formatter);  
			logger.setUseParentHandlers(false);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		//ucitavanje konfiguracionog fajla i inicijalizacija dimenzije, igraca i broja rupa
		config = Files.lines(new File(System.getProperty("user.dir"), "config.txt").toPath()).toArray(String[]::new);
		matrixDimension = Integer.parseInt(config[0]);
		numberOfPlayers = Integer.parseInt(config[1]);
		String[] tmpPlayers = config[2].split(",");
		numberOfHoles = Integer.parseInt(config[3]);
		
		
		//ucitavanje putanje i inicijalizacija
		String[] path = Files.lines(new File(System.getProperty("user.dir"), "path" + matrixDimension + ".txt").toPath()).toArray(String[]::new);
		String[] fields = path[0].split("-");
		for(var element : fields) {
			matrixPath.add(Integer.parseInt(element));
		}
		
		
		//inicijalizacija  igraca
		Color[] colors = new Color[4];
		colors[0] = Color.rgb(204,204,0);
		colors[1] = Color.GREEN;
		colors[2] = Color.RED;
		colors[3] = Color.BLUE;
		
		for(int i = 0; i < tmpPlayers.length; ++i) {
			
			players.add(new Player(tmpPlayers[i], colors[i], matrixPath.lastElement()));
	
		}
		
		
		//12 specijalnih karti
		for(int i = 0; i < 12; ++i) {
			
			cards.add(new SpecialCard(new ImageView(System.getProperty("user.dir") + "\\images\\special.png")));
		}
		
		//po 10 regularnih
		for(int i = 0; i < 10; ++i) {
			
			cards.add(new RegularCard(new ImageView(System.getProperty("user.dir") + "\\images\\regular1.png"), 1));
			cards.add(new RegularCard(new ImageView(System.getProperty("user.dir") + "\\images\\regular2.png"), 2));
			cards.add(new RegularCard(new ImageView(System.getProperty("user.dir") + "\\images\\regular3.png"), 3));
			cards.add(new RegularCard(new ImageView(System.getProperty("user.dir") + "\\images\\regular4.png"), 4));
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void updateDiamonds(Vector<Integer> diamonds, Vector<Integer> oldDiamonds) {
		
		synchronized(board) {
			
			for(var id : oldDiamonds) {
				
				BoardField field = board.get(id);
				Label label = field.getLabel();
				label.setGraphic(null);
				if(!field.hasFigure()) {
					
					label.setText(id + "");
				}
				field.hasDiamond(false);
			}
			
			
			for(var id : diamonds) {
				
				BoardField field = board.get(id);
				Label label = field.getLabel();
				ImageView image = new ImageView(System.getProperty("user.dir") + "\\images\\logo.png");
				image.setFitWidth(20);
				image.setFitHeight(20);
				label.setGraphic(image);
				field.hasDiamond(true);
			}
		}
		

	}
	
	
	public static void updateTime(long currentTime) {
		
		time.setText("Vrijeme trajanja simulacije: " + currentTime + "s");
		
	}
	
	public static void updateCard(Card card) {
		
		ImageView image = card.getImage();
		image.setFitWidth(205);
		image.setFitHeight(280);
		
		cardLabel.setGraphic(image);
		labelResults.setText(card.toString());
	}
	
	public static void updateFigure(int oldPosition, Figure figure, Player player, Vector<Integer> matrixPath) {
		
		String text = "Igrač " + player.getUsername() + " je na potezu. ";
			
		BoardField oldPositionField = board.get(matrixPath.elementAt(oldPosition));
		Label oldPositionLabel = oldPositionField.getLabel();
		oldPositionLabel.setText(matrixPath.elementAt(oldPosition) + "");
		oldPositionLabel.setTextFill(Color.rgb(42, 97, 113));
		oldPositionLabel.setBackground(new Background(new BackgroundFill(Color.rgb(215, 247, 245), new CornerRadii(3), Insets.EMPTY)));
			
				
		BoardField currentPositionField = board.get(matrixPath.elementAt(figure.getCurrentPosition()));
		Label currentPositionLabel = currentPositionField.getLabel();
		currentPositionLabel.setText(figure.getId());
		currentPositionLabel.setTextFill(Color.BLACK);
		currentPositionLabel.setBackground(new Background(new BackgroundFill(figure.getColor(), new CornerRadii(3), Insets.EMPTY)));
			
				
		int transition = figure.getCurrentPosition() - oldPosition;
		text += figure.toString() + " prelazi " + transition + " polja i pomjera se sa pozicije " + matrixPath.elementAt(oldPosition) + " na poziciju " + matrixPath.elementAt(figure.getCurrentPosition()) + ".";
				
		
		labelResults.setText(text);
	}
		
	public static void removeOldFigure(Figure figure, int oldPosition, Vector<Integer> matrixPath) {
		
		BoardField oldPositionField = board.get(matrixPath.elementAt(oldPosition));
		Label oldPositionLabel = oldPositionField.getLabel();
		oldPositionLabel.setText(matrixPath.elementAt(oldPosition) + "");
		oldPositionLabel.setTextFill(Color.rgb(42, 97, 113));
		oldPositionLabel.setBackground(new Background(new BackgroundFill(Color.rgb(215, 247, 245), new CornerRadii(3), Insets.EMPTY)));
		
		labelResults.setText(figure.toString() + "  je završila igru.");
		
	}
	
	public static void updateHoles(Vector<Integer> holes, Vector<Figure> figures, Vector<Integer> matrixPath) {
		
		String text = "";
		
		for(var hole : holes) {
			
			BoardField field = board.get(matrixPath.elementAt(hole));
			Label label = field.getLabel();
			label.setText(matrixPath.elementAt(hole) + "");
			label.setTextFill(Color.WHITE);
			label.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(3), Insets.EMPTY)));
				
		}
		
		for(var figure : figures) {
			
			
			if(figure instanceof CanFloat) {
					
				text += figure.toString() + " nije pojedena jer je lebdeća.\n";
			}
			else {
				text += figure.toString() + " je pojedena.\n";
			}
		}
		if(figures.isEmpty()) {
			text += "Nijedna figura nije pojedena.";
		}
		
		labelResults.setText(text);
	}
	
	public static void deleteHoles(Vector<Integer> holes, Vector<Figure> figures, Vector<Integer> matrixPath) {
		
		for(var hole : holes) {
				
			BoardField field = board.get(matrixPath.elementAt(hole));
			Label label = field.getLabel();
			label.setTextFill(Color.rgb(42, 97, 113));
			label.setBackground(new Background(new BackgroundFill(Color.rgb(215, 247, 245), new CornerRadii(3), Insets.EMPTY)));
		}
			
		for(var figure : figures) {
				
			if(figure instanceof CanFloat) {
				
				BoardField field = board.get(matrixPath.elementAt(figure.getCurrentPosition()));
				Label label = field.getLabel();
				label.setText(figure.getId());
				label.setTextFill(Color.BLACK);
				label.setBackground(new Background(new BackgroundFill(figure.getColor(), new CornerRadii(3), Insets.EMPTY)));
			}
		}
	}
	
	public static void endOfSimulation() {
		
		labelResults.setText("Igra je završila!");
		numberOfGames.setText("Trenutan broj odigranih igara: " + new File(System.getProperty("user.dir") + "\\list of games").list().length);

	}
	
	public static void setStartFigure(Figure figure, Vector<Integer> matrixPath, int position) {
		
		BoardField field = board.get(matrixPath.elementAt(position));
		Label label = field.getLabel();
		label.setText(figure.getId());
		label.setTextFill(Color.BLACK);
		label.setBackground(new Background(new BackgroundFill(figure.getColor(), new CornerRadii(3), Insets.EMPTY)));
		
		labelResults.setText(figure.toString() + " kreće sa igrom. ");
	}
}
