package gestclub.view;

import java.net.URL;
import java.util.ResourceBundle;

import gestclub.GestClubApp;
import gestclub.model.Membre;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ListeMembreController implements Initializable{
	@FXML
	private ListView<Membre> liste;
	private GestClubApp app;
	
	@FXML
	private Button butEditer;
	
	@FXML
	private Button butSupprimer;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		liste.getSelectionModel().selectedItemProperty().addListener(e -> mouseClickedOnListe(e));
		
		
		
	}
	
	private void mouseClickedOnListe(Observable e) {
		boolean empty = liste.getSelectionModel().isEmpty();
		butEditer.setDisable(empty);
		butSupprimer.setDisable(empty);
	}

	public void setApp(GestClubApp app) {
		this.app = app;
	}
	
	@FXML
	private void actionButNM() {
		Membre m = this.app.showSaisieMembre(null);
		if (m != null) {
		liste.getItems().add(m);
		}
	}
	
	@FXML
	private void actionEditer() {
		Membre membre = liste.getSelectionModel().getSelectedItem();
		System.out.println("édition du membre "+membre.getNom()+" "+membre.getPrenom());
		membre = this.app.showSaisieMembre(membre);
		 
	
		
	}
	
	@FXML
	private void actionSupprimer() {
		
	}

	public void loadListe(ObservableList<Membre> listeMembres) {
		liste.itemsProperty().set(listeMembres);
		
	}
	
	

}
