package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.CategorieOperation;
import application.tools.StageManagement;
import application.view.OperationEditorPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.CompteCourant;
import model.data.Operation;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;

public class OperationEditorPane {

	private Stage primaryStage;
	private OperationEditorPaneController oepc;

	/**
	 * Pane d'édition des opérations
	 * @param _parentStage IN : Stage parent à hériter
	 * @param _dbstate IN :
	 */
	public OperationEditorPane(Stage _parentStage, DailyBankState _dbstate) {

		try {
			FXMLLoader loader = new FXMLLoader(
					OperationEditorPaneController.class.getResource("operationeditorpane.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, 500 + 20, 250 + 10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Enregistrement d'une opération");
			this.primaryStage.setResizable(false);

			this.oepc = loader.getController();
			this.oepc.initContext(this.primaryStage, _dbstate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre le dialogue d'éditeur d'opérations
	 * @param cpte CompteCourrant IN : Le compte associé à l'opération
	 * @param cm CategorieOperation IN : Catégorie des opérations
	 * @return L'ópération modifiée
	 */
	public Operation doOperationEditorDialog(CompteCourant cpte, CategorieOperation cm) {
		try {
			return this.oepc.displayDialog(cpte, cm);
		} catch (DatabaseConnexionException e) {
			throw new RuntimeException(e);
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
