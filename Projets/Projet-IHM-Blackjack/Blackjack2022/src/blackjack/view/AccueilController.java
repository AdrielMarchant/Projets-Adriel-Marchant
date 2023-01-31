package blackjack.view;

import java.net.URL;
import java.util.ResourceBundle;

import blackjack.BlackJackMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccueilController implements Initializable {
	private Stage dialogStage;
	private BlackJackMain main;


	@FXML
	private Button butJouer;

	@FXML
	private Button butRegles;

	@FXML
	private Button butQuitter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	private void actionJouer() {
		this.main.loadBlackJackMain();
	}

	@FXML
	private void actionRegles() {
		Alert regles = new Alert(AlertType.INFORMATION);
		regles.setTitle("Règles du BlackJack :");
		regles.setHeaderText("Voici les regles du BlackJack :");
		String regle="La partie oppose individuellement chaque joueur contre la banque. Le but est de battre le croupier sans dépasser le score de 21. Dès qu'un joueur fait plus que 21, on dit qu'il « brûle » et il perd sa mise initiale. La valeur des cartes sont les suivantes6 :\r\n"
				+ "\r\n"
				+ "de 2 à 9 : valeur nominale de la carte ;\r\n"
				+ "de 10 au roi (surnommées « bûche »)  : 10 points ;\r\n"
				+ "As : 1 ou 11 points (au choix du joueur).\r\n"
				+ "Un Blackjack est la situation où le joueur reçoit, dès le début du jeu, un As et une buche. Si le joueur atteint 21 points avec plus de deux cartes, on compte 21 points et non Blackjack";

		regles.setContentText(regle);
		regles.initOwner(dialogStage);
		regles.show();
	}

	@FXML
	private void actionQuitter() {
		this.dialogStage.close();
	}

	public void setDialogStage(Stage primaryScene) {
		this.dialogStage = primaryScene;
	}

	public void setMain(BlackJackMain main) {
		this.main = main;
	}





}
