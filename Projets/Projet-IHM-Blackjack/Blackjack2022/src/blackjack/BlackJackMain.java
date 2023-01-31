package blackjack;

import java.io.IOException;

import blackjack.view.AccueilController;
import blackjack.view.MainController;
import blackjack.view.MiseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BlackJackMain extends Application {

	private Stage primaryStage;
	private BorderPane rootPane;
	private MainController mc;
	private BorderPane vueMain;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.rootPane = new BorderPane();

		Scene scene = new Scene(rootPane);

		primaryStage.setTitle("BlackJack AM");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		loadBlackJackAccueil();
		primaryStage.show();

	}

	public void loadBlackJackAccueil() {
		try {
			System.out.println("affichage de l'accueil");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BlackJackMain.class.getResource("view/BlackJack-Accueil.fxml"));

			BorderPane vueAccueil = loader.load();

			AccueilController ctrl =loader.getController();

			ctrl.setDialogStage(primaryStage);
			ctrl.setMain(this);

			this.rootPane.setCenter(vueAccueil);
			this.primaryStage.sizeToScene();





		}catch ( IOException e) {
			e.printStackTrace();
		}
	}

	public void loadBlackJackMain() {
		try {
			System.out.println("affichage du main");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BlackJackMain.class.getResource("view/BlackJack-Main.fxml"));

			this.vueMain = loader.load();

			this.mc = loader.getController();
			//MainController ctrl =
			this.mc.setMain(this);

			this.mc.setDialogStage(primaryStage);
			
			
			this.rootPane.setCenter(this.vueMain);
			this.primaryStage.sizeToScene();
			

		}catch ( IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadBlackJackMise(int quelBouton) {
		try {
			System.out.println("affichage de la mise");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BlackJackMain.class.getResource("view/BlackJack-Mise.fxml"));

			BorderPane vueMise = loader.load();

			MiseController ctrl =loader.getController();
			ctrl.setQuelBouton(quelBouton);
			

			ctrl.setMain(this);
			ctrl.setDialogStage(primaryStage);
			ctrl.assignMainController(this.mc);
			
			
			this.rootPane.setCenter(vueMise);
			this.primaryStage.sizeToScene();
			
			
			
			

		}catch ( IOException e) {
			e.printStackTrace();
		}

	}
	
	public void afficherMain() {
		this.rootPane.setCenter(vueMain);
		this.primaryStage.sizeToScene();
	}
	
	
	

	public static void main(String[] args) {
		launch(args);
	}

}
