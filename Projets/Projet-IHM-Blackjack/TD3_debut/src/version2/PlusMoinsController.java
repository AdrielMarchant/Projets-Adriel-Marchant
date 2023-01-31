package version2;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class PlusMoinsController implements Initializable {
	@FXML
	private Slider slider;
	
	@FXML
	private TextField zoneTexte;
	
	private IntegerProperty texteProperty;
	
	private Stage     fenetrePrincipale = null;
	
	public void setFenetrePrincipale(Stage fenetrePrincipale) {
		this.fenetrePrincipale = fenetrePrincipale;
		this.fenetrePrincipale.setOnCloseRequest(event -> actionQuitter());
	}
	
	private void actionAjouter(int valeur) {
		int nb = Integer.parseInt( this.zoneTexte.getText() );
		this.zoneTexte.setText(""+(nb+valeur));
	}
	@FXML
	private void actionRAZ() {
		this.zoneTexte.setText("10");
	}
	@FXML
	private void actionPlus() {
		actionAjouter(1);
	}
	@FXML
	private void actionMoins() {
		actionAjouter(-1);
	}
	
	@FXML
	private void actionQuitter() {
		String valTexte = this.zoneTexte.getText();
		System.out.println("La dernière valeur du compteur était : "+valTexte);
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Fermeture de l'application");
		alert.setHeaderText("Voulez-vous réellement quitter ? ");
		alert.initOwner(fenetrePrincipale);
		
		ButtonType plustard = new ButtonType("Plus tard...");
		alert.getButtonTypes().setAll(plustard, ButtonType.YES,ButtonType.NO);
		
		Optional<ButtonType> reponse = alert.showAndWait();
		if( reponse.orElse(null) == ButtonType.YES) {
			this.fenetrePrincipale.close();
		}else if (reponse.orElse(null) == plustard) {
			System.out.println("On reste encore un peu ...");
		}
	
		
	}
	
	@FXML
	private void actionAPropos() {
		Alert about = new Alert(AlertType.INFORMATION);
		about.setTitle("A propos...");
		about.setHeaderText("Crédits");
		about.initOwner(fenetrePrincipale);
		
		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
				
		webView.setPrefSize(640, 480);	
		webEngine.load("https://www.google.fr/");
		
		about.getDialogPane().setContent(webView);
		
		
		about.showAndWait();
		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public TextField getZoneTexte() {
		return this.zoneTexte;
	}
	
	public Integer getTexteInt() {
		return Integer.parseInt( this.zoneTexte.getText() );
	}
	
	
	public void setPropertiersCompteur(IntegerProperty intProp) {
		Bindings.bindBidirectional(zoneTexte.textProperty(),intProp,new NumberStringConverter() );
		slider.valueProperty().bindBidirectional(intProp) ;
	}
}
