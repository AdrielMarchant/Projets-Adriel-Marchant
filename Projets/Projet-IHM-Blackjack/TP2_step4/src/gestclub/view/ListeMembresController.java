package gestclub.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gestclub.GestClubApp;
import gestclub.model.Membre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ListeMembresController implements Initializable {
	
	@FXML
	private ListView<Membre> listViewMembres;
	@FXML
	private Button butEditer;
	@FXML
	private Button butSupprimer;
	
	private GestClubApp getClubApp;

	public void setGetClubApp(GestClubApp getClubApp) {
		this.getClubApp = getClubApp;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	public void setListeMembre( List<Membre> liste ) {
		for (Membre membre : liste) {
			this.listViewMembres.getItems().add(membre);
		}
	}

	@FXML
	private void actionNouveau() {
		Membre m = this.getClubApp.showSaisieMembre();
		if (m!=null) {
			this.listViewMembres.getItems().add(m);
		}
	}
	@FXML
	private void actionEditer() {
		System.out.println("Editer");
	}
	@FXML
	private void actionSupprimer() {
		System.out.println("Supprimer");
	}
	

}
