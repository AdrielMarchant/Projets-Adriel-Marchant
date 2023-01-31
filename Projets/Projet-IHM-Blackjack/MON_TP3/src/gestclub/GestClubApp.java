package gestclub;
	
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gestclub.model.EtatMembre;
import gestclub.model.Membre;
import gestclub.view.ListeMembreController;
import gestclub.view.SaisieMembreController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class GestClubApp extends Application {
	
	private BorderPane rootPane;
	private Stage      primaryStage;
	private final ObservableList<Membre> listeMembres = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) {
		
		this.listeMembres.add(new Membre ("Tare","Guy",EtatMembre.Membre,"Grenoble",LocalDate.of(1942, 5,12),"Collègue de Michel"));
		this.listeMembres.add(new Membre ("Deblouze","Agathe",EtatMembre.Ancien,"Portes",LocalDate.of(1991, 2, 17),"Apparu dans le sud des états-unis"));
		this.listeMembres.add(new Membre ("Sansfrapper","André",EtatMembre.Prospect,"Grenoble",LocalDate.of(2002, 04, 11),"C'est tout vert"));
		
		this.primaryStage = primaryStage;
		this.rootPane     = new BorderPane();
		
		Scene scene = new Scene(rootPane);
		scene.getStylesheets().add(GestClubApp.class.getResource("resource/style.css").toExternalForm());
		primaryStage.setTitle("GestClub App");
		primaryStage.setScene(scene);

		loadListeMembre();
		//showSaisieMembre(); // affichage temporaire pour validation

		primaryStage.show();		
		
	}
	
	public void loadListeMembre() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( GestClubApp.class.getResource("view/ListeMembres.fxml"));
			
			BorderPane vueListe = loader.load();
			
			Scene scene = new Scene(vueListe);
			scene.getStylesheets().setAll( primaryStage.getScene().getStylesheets() );
			
			Stage dialogStage =new Stage();
			dialogStage.setTitle("Edition membre");
			dialogStage.initOwner(this.primaryStage);
			dialogStage.setScene(scene);
			
			ListeMembreController ctrl = loader.getController();
			ctrl.setApp(this);
			ctrl.loadListe(listeMembres);
			
			this.rootPane.setCenter( vueListe );
			
						
		} catch (IOException e) {
			System.out.println("Ressource FXML non disponible : ListeMembres");
			System.exit(1);
		}	
	}
	
	public Membre showSaisieMembre(Membre membre) {
		Membre pfMembre = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( GestClubApp.class.getResource("view/SaisieMembre.fxml"));
			
			BorderPane vueSaisie = loader.load();
				
			Scene scene = new Scene(vueSaisie);
			scene.getStylesheets().setAll( primaryStage.getScene().getStylesheets() );
			
			Stage dialogStage =new Stage();
			dialogStage.setTitle("Edition membre");
			dialogStage.initOwner(this.primaryStage);
			dialogStage.setScene(scene);
			
			SaisieMembreController controleur = loader.getController();
			controleur.setDiaglogStage(dialogStage);
			
			if (membre != null) {
				controleur.setMembre(membre);
			}
		
			dialogStage.showAndWait();
			
			
			pfMembre = controleur.getMembre();
						
		} catch (IOException e) {
			System.out.println("Ressource FXML non disponible : SaisieMembres");
			System.exit(1);
		}
		return pfMembre ;
	}
	
	public List<Membre> getMembres(){
		return this.listeMembres;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void stop() throws Exception{
		System.out.println("Fin de l'application, voici la liste des membres : ");
		for ( Membre m:listeMembres) {
			System.out.println(m);
		}
	}
	
}
