package version1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlusMoinsApp extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		System.out.println("start");
		TextField zoneTexte = new TextField("zone de texte");
		zoneTexte.setText("10");
		zoneTexte.setEditable(false);
		zoneTexte.setAlignment(Pos.CENTER);
		
		
		zoneTexte.setMaxHeight(Double.MAX_VALUE);
		
		
		Button butPlus = new Button("+");
		Button butMoins = new Button("-");
		Button butRAZ = new Button("RAZ");		
		butPlus.setPrefWidth(300);
		butRAZ.setPrefWidth(300);
		butMoins.setPrefWidth(300);
		
		butPlus.setStyle("-fx-font-size:18; -fx-base:blue");
		butRAZ.setStyle("-fx-font-size:18; -fx-base:white");
		butMoins.setStyle("-fx-font-size:18;-fx-base:red");
		
		GridPane gridPane = new GridPane();
		gridPane.add(butPlus, 0, 0);
		gridPane.add(butRAZ, 1, 0);
		gridPane.add(butMoins, 2, 0);
		
		
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(zoneTexte);
		borderPane.setBottom(gridPane);
		Scene scene = new Scene(borderPane);
		
		primaryStage.setScene(scene);
		
		
		primaryStage.setTitle("PlusMoins");
		primaryStage.show();
	}
	
	@Override
	public void init() throws Exception{
		System.out.println("itit");
	}
	
	@Override
	public void stop() throws Exception{
		System.out.println("stop");
	}
}
