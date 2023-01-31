package blackjack.view;

import java.net.URL;
import java.util.ResourceBundle;

import blackjack.BlackJackMain;
import blackjack.om.BlackBot;
import blackjack.om.EtatBlackBot;
import blackjack.om.MainBlackjack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private Stage dialogStage;
	private BlackJackMain main;
	private BlackBot bot = new BlackBot(3);


	@FXML
	private Label miseUn;
	private int mise1;
	@FXML
	private Label miseDeux;
	private int mise2;
	@FXML
	private Label miseTrois;
	private int mise3;
	@FXML
	private Label soldeUn;
	private int solde1;
	@FXML
	private Label soldeDeux;
	private int solde2;
	@FXML
	private Label soldeTrois;
	private int solde3;
	@FXML
	private Label etatBot;
	@FXML
	private Button butMise1;
	@FXML
	private Button butMise2;
	@FXML
	private Button butMise3;
	@FXML
	private Button butHit1;
	@FXML
	private Button butHit2;
	@FXML
	private Button butHit3;
	@FXML
	private Button butStand1;
	@FXML
	private Button butStand2;
	@FXML
	private Button butStand3;
	@FXML
	private Button butStart;
	@FXML
	private Button butMises;
	@FXML
	private TextArea mainJ1;
	@FXML
	private TextArea mainJ2;
	@FXML
	private TextArea mainJ3;
	@FXML
	private TextArea mainC;
	@FXML
	private Button butContinuer;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize");
		this.solde1 = 100;
		this.solde2 = 100;
		this.solde3 = 100;

	}

	public void updateMiseUn(String mise) {
		System.out.println("updateMiseUn");
		int pfMise = Integer.parseInt( mise);
		System.out.println(pfMise);
		if (pfMise <= this.solde1 && pfMise > 0) {
			this.mise1 =pfMise;
			System.out.println("test");
			this.miseUn.setText("mise : "+mise);
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("erreur de mise ");
			alert.setHeaderText("le montant de la mise doit être positif et inférieur ou égal au solde ");
			alert.showAndWait();			
		}
	}
	public void updateMiseDeux(String mise) {
		int pfMise = Integer.parseInt( mise);
		System.out.println(pfMise);
		if (pfMise <= this.solde2 && pfMise > 0) {
			this.mise2 =pfMise;
			System.out.println("test");
			this.miseDeux.setText("mise : "+mise);
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("erreur de mise ");
			alert.setHeaderText("le montant de la mise doit être positif et inférieur ou égal au solde ");
			alert.showAndWait();			
		}
	}
	public void updateMiseTrois(String mise) {
		int pfMise = Integer.parseInt( mise);
		System.out.println(pfMise);
		if (pfMise <= this.solde3 && pfMise > 0) {
			this.mise3 =pfMise;
			System.out.println("test");
			this.miseTrois.setText("mise : "+mise);
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("erreur de mise ");
			alert.setHeaderText("le montant de la mise doit être positif et inférieur ou égal au solde ");
			alert.showAndWait();			
		}
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

	@FXML
	private void actionMiseUn() {
		this.main.loadBlackJackMise(1);
	}
	@FXML
	private void actionMiseDeux() {
		this.main.loadBlackJackMise(2);
	}
	@FXML
	private void actionMiseTrois() {
		this.main.loadBlackJackMise(3);
	}

	@FXML
	private void actionRetour() {
		this.main.loadBlackJackAccueil();
	}

	@FXML
	private void actionMises() {
		System.out.println(this.bot.getEtat());
		System.out.println("solde 1 "+this.solde1);
		System.out.println("mise1 "+ this.mise1);
		if (this.mise1 <= this.solde1 && this.mise2 <= this.solde2 && this.mise3 <= this.solde3){
			if (this.mise1 != 0 || this.mise2 != 0 || this.mise3 != 0) {

				this.butMises.setOpacity(0);
				this.butMises.setDisable(true);
				this.butMise1.setDisable(true);
				this.butMise2.setDisable(true);
				this.butMise3.setDisable(true);
				if ( mise1 != 0) {
					this.butHit1.setDisable(false);
					this.butStand1.setDisable(false);
					this.bot.miser(0, mise1);
				}			
				if (mise2 != 0) {
					this.butHit2.setDisable(false);
					this.butStand2.setDisable(false);
					this.bot.miser(1, mise2);
				}
				if (mise3 != 0) {
					this.butHit3.setDisable(false);
					this.butStand3.setDisable(false);
					this.bot.miser(2, mise3);
				}
				this.bot.distribuer();
				this.mainC.setText(this.bot.getMainBanque().toString());
				this.mainJ1.setText(this.bot.getMainJoueurs(0).toString());
				this.mainJ2.setText(this.bot.getMainJoueurs(1).toString());
				this.mainJ3.setText(this.bot.getMainJoueurs(2).toString());
				this.etatBot.setText("Etat de la partie : En jeu ");


			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Erreur mise");
				alert.initOwner(this.dialogStage);
				alert.setHeaderText("Veuillez renseigner une valeur de mise pour au moins un des joueurs");

				alert.showAndWait();
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Erreur mise");
			alert.initOwner(this.dialogStage);
			alert.setHeaderText("Veuillez renseigner une valeur de mise inférieure ou égale à votre solde");
			alert.showAndWait();
			this.mise1 = 0;
			this.mise2= 0;
			this.mise3 = 0;
			this.miseUn.setText("mise : 0");
			this.miseDeux.setText("mise : 0");
			this.miseTrois.setText("mise : 0");

		}
	}

	@FXML
	private void actionStart() {
		this.butMise1.setDisable(false);
		this.butMise2.setDisable(false);
		this.butMise3.setDisable(false);
		this.butStart.setOpacity(0);
		this.butStart.setDisable(true);
		this.butMises.setOpacity(1);
		this.butMises.setDisable(false);
		this.etatBot.setText("Etat de la partie : Mise");

	}

	public void setDialogStage(Stage primaryScene) {
		this.dialogStage = primaryScene;
	}

	public void setMain(BlackJackMain main) {
		this.main = main;
	}

	@FXML
	private void actionHit1() {
		System.out.println("marche");
		this.bot.tirer(0);
		this.mainJ1.setText(this.bot.getMainJoueurs(0).toString());
		
		if (this.bot.getMainJoueurs(0).getScore() > 21) {
			System.out.println("marche pas");
			this.butHit1.setDisable(true);
			this.butStand1.setDisable(true);
		}

		isFini();
	}

	@FXML
	private void actionHit2() {
		this.bot.tirer(1);
		this.mainJ2.setText(this.bot.getMainJoueurs(1).toString());
		
		if (this.bot.getMainJoueurs(1).getScore() > 21) {
			System.out.println("marche pas");
			this.butHit2.setDisable(true);
			this.butStand2.setDisable(true);
		}
		isFini();
	}

	@FXML
	private void actionHit3() {
		this.bot.tirer(2);
		this.mainJ3.setText(this.bot.getMainJoueurs(2).toString());
	
		if (this.bot.getMainJoueurs(2).getScore() > 21) {
			System.out.println("marche pas");
			this.butHit3.setDisable(true);
			this.butStand3.setDisable(true);
		}
		isFini();
	}

	@FXML
	private void actionStand1() {
		this.bot.terminer(0);
		this.butHit1.setDisable(true);
		this.butStand1.setDisable(true);
		System.out.println(this.bot.getEtat());
		isFini();
	}
	@FXML
	private void actionStand2() {
		this.bot.terminer(1);
		this.butHit2.setDisable(true);
		this.butStand2.setDisable(true);
		System.out.println(this.bot.getEtat());
		isFini();
	}
	@FXML
	private void actionStand3() {
		this.bot.terminer(2);
		this.butHit3.setDisable(true);
		this.butStand3.setDisable(true);
		System.out.println(this.bot.getEtat());
		isFini();

	}

	private void isFini() {
		if ( this.bot.getEtat() == EtatBlackBot.GAIN) {
			System.out.println("ici");
			this.mainC.setText(this.bot.getMainBanque().toString());
			if ( this.bot.getGainJoueurs(0) == 0 ) {
				if(this.bot.getMainJoueurs(0).getScore() == this.bot.getMainBanque().getScore()) {

				}else {
					this.solde1 -= this.mise1;
				}

			}else {
				this.solde1 += this.bot.getGainJoueurs(0);
			}

			if ( this.bot.getGainJoueurs(1) == 0 ) {
				if(this.bot.getMainJoueurs(1).getScore() == this.bot.getMainBanque().getScore()) {

				}else {
					this.solde2 -= this.mise2;
				}

			}else {
				this.solde2 += this.bot.getGainJoueurs(1);
			}

			if ( this.bot.getGainJoueurs(2) == 0 ) {
				if(this.bot.getMainJoueurs(2).getScore() == this.bot.getMainBanque().getScore()) {

				}else {
					this.solde3 -= this.mise3;
				}

			}else {
				this.solde3 += this.bot.getGainJoueurs(2);
			}

			setSoldes();
			this.etatBot.setText("Etat de la partie : Gain");
			this.butContinuer.setOpacity(1);
			this.butContinuer.setDisable(false);
			this.butHit1.setDisable(true);
			this.butStand1.setDisable(true);
			this.butHit2.setDisable(true);
			this.butStand2.setDisable(true);
			this.butHit3.setDisable(true);
			this.butStand3.setDisable(true);


		}
	}

	private void setSoldes() {
		this.soldeUn.setText("solde : "+this.solde1);
		this.soldeDeux.setText("solde : "+this.solde2);
		this.soldeTrois.setText("solde : "+this.solde3);
	}

	@FXML
	private void continuer() {
		this.bot.relancerPartie();
		this.butStart.opacityProperty().set(1);
		this.butStart.setDisable(false);
		this.butContinuer.setOpacity(0);
		this.butContinuer.setDisable(true);
		this.mainJ1.setText("");
		this.mainJ2.setText("");
		this.mainJ3.setText("");
		this.mainC.setText("");
	}








}
