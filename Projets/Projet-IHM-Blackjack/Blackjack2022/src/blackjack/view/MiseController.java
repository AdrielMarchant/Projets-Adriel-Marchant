package blackjack.view;

import java.net.URL;
import java.util.ResourceBundle;

import blackjack.BlackJackMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MiseController implements Initializable {
	private BlackJackMain main;
	@FXML
	private Button butMoinsUn;
	@FXML
	private Button butMoinsDix;
	@FXML
	private Button butPlusUn;
	@FXML
	private Button butPlusDix;
	
	@FXML
	private Label mise;
	private MainController mc;
	private int quelBouton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void setDialogStage(Stage primaryStage) {
	}
	
	public void setMain(BlackJackMain main) {
		this.main = main;
	}
	public void assignMainController(MainController pMc) {
		this.mc = pMc;
	}
	
	@FXML
	private void actionMiser() {
		//MainController ctrlMain = new MainController() ;
		//
		
		System.out.println("ici " + getMise()+ " bouton : "+quelBouton);
		switch(quelBouton) {
		case 1:
			this.mc.updateMiseUn(getMise());
			break;
		case 2:
			this.mc.updateMiseDeux(getMise());
			break;
		case 3: 
			this.mc.updateMiseTrois(getMise());
			break;
		}
		
		this.main.afficherMain();
	}
	
	private void actionAjouter(int valeur) {
		int nb = Integer.parseInt( this.mise.getText() );
		this.mise.setText(""+(nb+valeur));
		
	}
	
	@FXML
	private void actionPlusUn() {
		actionAjouter(1);
	}
	
	@FXML
	private void actionPlusDix() {
		actionAjouter(10);
	}
	
	@FXML
	private void actionMoinsUn() {
		actionAjouter(-1);
	}
	
	@FXML
	private void actionMoinsDix() {
		actionAjouter(-10);
	}
	
	public String getMise() {
		return this.mise.getText();
	}

	public void setQuelBouton(int quelBouton) {
		this.quelBouton = quelBouton;
		
	}


}
