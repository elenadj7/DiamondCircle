package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FilesListController {

	public void display() {

		try {
			
			Pane root;
			root = (Pane)FXMLLoader.load(getClass().getResource("FilesList.fxml"));
			Scene scene = new Scene(root,400,200);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle(" ");
			stage.getIcons().add(new Image(System.getProperty("user.dir") + "\\images\\logo.png"));
			stage.setScene(scene);
			stage.show();
			
			ListView<String> listView = (ListView)scene.lookup("#listView");
			File files = new File(System.getProperty("user.dir") + "\\list of games");
			listView.getItems().addAll(files.list());
			
			listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					
					try {
						
						String item = listView.getSelectionModel().getSelectedItem();
						if(item != null) {
							Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "\\list of games\\" + listView.getSelectionModel().getSelectedItem()));
						}
					} catch (IOException e) {
		
						e.printStackTrace();
					}
				}
				
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
}
