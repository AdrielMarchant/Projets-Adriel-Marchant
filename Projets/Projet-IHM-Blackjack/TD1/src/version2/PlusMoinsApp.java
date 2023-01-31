package version2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PlusMoinsApp extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(PlusMoinsApp.class.getResource("PlusMoins.fxml"));
		
		BorderPane borderPane = loader.load();
		
		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("PlusMoinsApp");
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
