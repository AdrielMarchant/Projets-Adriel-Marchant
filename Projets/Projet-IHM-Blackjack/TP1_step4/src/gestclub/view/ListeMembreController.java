package gestclub.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gestclub.GestClubApp;
import gestclub.model.Membre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ListeMembreController implements Initializable{
	@FXML
	private ListView<Membre> liste;
	private GestClubApp app;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void setApp(GestClubApp app) {
		this.app = app;
	}
	
	@FXML
	private void actionButNM() {
		Membre m = this.app.showSaisieMembre();
		if (m != null) {
		liste.getItems().add(m);
		}
	}
	
	@FXML
	private void actionEditer() {
		
	}
	
	@FXML
	private void actionSupprimer() {
		
	}

	public void loadListe(List<Membre> listeMembres) {
		for (Membre m:listeMembres) {
			liste.getItems().add(m);
		}
		
	}
	
	

}
