package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.EditionMode;
import application.tools.StageManagement;
import application.view.CompteEditorPaneController;
import application.view.EmployeEditorPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Employe;
import model.data.CompteCourant;

public class EmployeEditorPane {

	private Stage primaryStage;
	private EmployeEditorPaneController eepc;

	public EmployeEditorPane(Stage _parentStage, DailyBankState _dbstate) {

		try {
			FXMLLoader loader = new FXMLLoader(EmployeEditorPaneController.class.getResource("employeeditorpane.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth()+20, root.getPrefHeight()+10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Gestion d'un compte");
			this.primaryStage.setResizable(false);

			this.eepc = loader.getController();
			this.eepc.initContext(this.primaryStage, _dbstate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre le dialogue d'édition des employés
	 * @param employe IN : Employé à modifier
	 * @param em IN : Mode d'édition
	 * @return Employé modifié
	 */
	public Employe doEmployeEditorDialog(Employe employe, EditionMode em) {
		return this.eepc.displayDialog(employe, em);
	}
}
