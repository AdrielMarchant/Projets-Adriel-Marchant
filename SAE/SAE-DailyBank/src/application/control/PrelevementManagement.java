package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.StageManagement;
import application.view.PrelevementsManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Client;
import model.data.CompteCourant;
import model.data.PrelevementAutomatique;
import model.orm.AccessPrelevementAutomatique;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;

import java.util.ArrayList;

public class PrelevementManagement {

    private Stage primaryStage;
    private DailyBankState dbs;
    private PrelevementsManagementController pmc;
    private Client clientDuCompte;
    private CompteCourant compteConcerne;

    /**
     * Établit le fxml pour la gestion des prélèvements
     * @param _parentStage Stage parent
     * @param _dbstate DailyBankState
     * @param client Client
     * @param compte CompteCourant
     */
    public PrelevementManagement(Stage _parentStage, DailyBankState _dbstate, Client client, CompteCourant compte) {

        this.clientDuCompte = client;
        this.compteConcerne = compte;
        this.dbs = _dbstate;
        try {
            FXMLLoader loader = new FXMLLoader(
                    PrelevementsManagementController.class.getResource("prelevementsmanagement.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root, 900 + 20, 350 + 10);
            scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

            this.primaryStage = new Stage();
            this.primaryStage.initModality(Modality.WINDOW_MODAL);
            this.primaryStage.initOwner(_parentStage);
            StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("Gestion des prélèvements");
            this.primaryStage.setResizable(false);

            this.pmc = loader.getController();
            this.pmc.initContext(this.primaryStage, this, _dbstate, client, this.compteConcerne);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doOperationsManagementDialog() {
        this.pmc.displayDialog();
    }

    /**
     * Va chercher les prélèvements automatiques associés au compte concerné
     * @return un ArrayList de prélèvements automatiques
     */
    public ArrayList<PrelevementAutomatique> prelevementsCompte() {
        AccessPrelevementAutomatique apa = new AccessPrelevementAutomatique();
        try {
            return apa.getPrelevementAutomatiques(this.compteConcerne.idNumCompte);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        } catch (DatabaseConnexionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Va modifier un prélèvement automatique
     * @param selectedItem le prélèvement automatique à modifier
     * @param idNumCompte le numéro de compte concerné
     */
    public void modifierPrelevement(PrelevementAutomatique selectedItem, int idNumCompte) {
        PrelevementEditorPane pep = new PrelevementEditorPane(this.primaryStage, this.dbs);
        PrelevementAutomatique result = pep.doPrelevementEditorDialog(selectedItem);

        if (result == null) {
            return;
        }
        AccessPrelevementAutomatique apa = new AccessPrelevementAutomatique();
        if (selectedItem != null) {

            try {
                apa.updatePrelevementAutomatique(result.idPrelevementAutomatique,result.montant, result.datePrelevement, result.beneficiaire ,result.idNumCompte);
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            } catch (DatabaseConnexionException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                apa.addPrelevementAutomatique(result.montant, result.datePrelevement, result.beneficiaire, idNumCompte);
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            } catch (DatabaseConnexionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
