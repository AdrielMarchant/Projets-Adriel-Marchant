package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.CategorieOperation;
import application.tools.EditionMode;
import application.tools.StageManagement;
import application.view.PrelevementEditorpaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.CompteCourant;
import model.data.Operation;
import model.data.PrelevementAutomatique;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;

public class PrelevementEditorPane {
    private Stage primaryStage;
    private PrelevementEditorpaneController pec;

    /**
     * Pane d'édition des Prelevements Automatiques
     * @param _parentStage IN : Stage parent à hériter
     * @param _dbstate IN :
     */
    public PrelevementEditorPane(Stage _parentStage, DailyBankState _dbstate) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    PrelevementEditorpaneController.class.getResource("prelevementeditorpane.fxml"));
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
            this.pec = loader.getController();
            this.pec.initContext(this.primaryStage, _dbstate);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Ouvre le dialogue d'éditeur de Prelevements Automatiques
     * @return PrelevementAutomatique modifié
     */
    public PrelevementAutomatique doPrelevementEditorDialog(PrelevementAutomatique prelevement) {
        if (prelevement == null) {
            return this.pec.getPrelevement(prelevement, EditionMode.CREATION);
        } else {
            return this.pec.getPrelevement(prelevement, EditionMode.MODIFICATION);
        }
    }
}
