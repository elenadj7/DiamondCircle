package application;

import java.io.IOException;
import java.util.Vector;

import application.elements.figures.Figure;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FigureWindowController {

	void display(int dimension, Figure figure, Vector<Integer> path) {
		
        
		try {
			
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("FigureWindow.fxml"));
			Scene scene = new Scene(root,500,400);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle(figure.toString());
			stage.getIcons().add(new Image(System.getProperty("user.dir") + "\\images\\logo.png"));
			stage.setScene(scene);
            stage.show();
            
            VBox vboxBoard = (VBox)scene.lookup("#vboxFigureWindow");
            
            int position = 1;
			for(int i = 0; i < dimension; ++i) {
				
				HBox hbox = new HBox();
				
				for(int j = 0; j < dimension; ++j) {
					
					Label label = new Label();
					label.setFont(Font.font(16));
					label.setAlignment(Pos.CENTER);
					if(figure.getPath().contains(position)){
						label.setBackground(new Background(new BackgroundFill(Color.rgb(243, 213, 22), new CornerRadii(3), Insets.EMPTY)));
					}
					else if(path.contains(position)) {
						label.setBackground(new Background(new BackgroundFill(Color.rgb(215, 247, 245), new CornerRadii(3), Insets.EMPTY)));
					}
					else {
						label.setBackground(new Background(new BackgroundFill(Color.rgb(117, 202, 195), new CornerRadii(3), Insets.EMPTY)));
					}
					label.setBorder(new Border(new BorderStroke(Paint.valueOf("#5357a6"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
					label.setPrefSize(35, 30);
					label.setText(position + "");
					label.setTextFill(Color.rgb(83, 87, 166));
					HBox.setMargin(label, new Insets(2.5,2.5,2.5,2.5));
					hbox.getChildren().add(label);
					
					position++;
				}
				VBox.setMargin(hbox, new Insets(2.5,1.25,1.25,2.5));
				
				switch(dimension) {
				
				case 10:
					hbox.setPadding(new Insets(0,0,0,50));
					break;
					
				case 9:
					vboxBoard.setPadding(new Insets(20,0,0,0));
					hbox.setPadding(new Insets(0,0,0,70));
					break;
					
				case 8:
					vboxBoard.setPadding(new Insets(40,0,0,0));
					hbox.setPadding(new Insets(0,0,0,90));
					break;
					
				case 7:
					vboxBoard.setPadding(new Insets(60,0,0,0));
					hbox.setPadding(new Insets(0,0,0,110));
					break;
				}
				vboxBoard.getChildren().add(hbox);
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
