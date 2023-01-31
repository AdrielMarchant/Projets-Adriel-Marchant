package version2;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlusMoinsController implements Initializable {
	private Stage fenetre;
	

	@FXML
	private TextField zoneTexte;
	
	
	public PlusMoinsController() {
		System.out.println("Constuction du contrôleur");
	}
	
	public void setFenetre(Stage fenetre) {
		System.out.println("Recuperation de la fenetre ");
		this.fenetre = fenetre;
		this.fenetre.setOnCloseRequest(event -> actionQuitter());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialisation");
	}
	
	@FXML
	private void actionPlus() {
		actionAjout(1);
	}
	
	@FXML
	private void actionMoins() {
		actionAjout(-1);
	}

	@FXML
	private void actionRaz() {
		zoneTexte.setText("10");
	}
	
	private void actionAjout(int val) {
		int valLue =Integer.parseInt( zoneTexte.getText());
		valLue += val;
		zoneTexte.setText(""+valLue);
	}
	
	public void actionQuitter() {
		System.out.println("Fermeture demandée ...");
		System.out.println("Le compteur vaut : "+zoneTexte.getText());
	}

	

}
