package version1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlusMoinsApp extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		//System.out.println("start");
		
		TextField zoneTexte = new TextField("zone de texte");
		zoneTexte.setMaxHeight(Double.MAX_VALUE);
		
		Button butPlus  = new Button("+");
		Button butMoins = new Button("-");
		Button butRaz   = new Button("RAZ");
		
		butPlus.setPrefWidth(  300 );
		butMoins.setPrefWidth( 300 );
		butRaz.setPrefWidth(   300 );
		butPlus.setStyle("-fx-font-size:18");
		butMoins.setStyle("-fx-font-size:18");
		butRaz.setStyle("-fx-font-size:18");
		zoneTexte.setAlignment(Pos.CENTER);
		zoneTexte.setStyle("-fx-font-size:28");
		zoneTexte.setEditable(false);
		zoneTexte.setText("10");

		GridPane gridPane = new GridPane();
		gridPane.add(butPlus, 0, 0);
		gridPane.add(butRaz, 1, 0);
		gridPane.add(butMoins, 2, 0);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(zoneTexte);
		borderPane.setBottom(gridPane);
		
		Scene scene = new Scene(borderPane, 320, 200);
		primaryStage.setScene(scene);
		
		
		primaryStage.setTitle("PlusMoinsApp");
		primaryStage.show();
		
	}
	@Override
	public void init() throws Exception {
		System.out.println("Init");
	}
	@Override
	public void stop() throws Exception {
		System.out.println("Stop");
	}

}
