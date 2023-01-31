package application.view;

import application.DailyBankState;
import application.control.PrelevementManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Client;
import model.data.CompteCourant;
import model.data.PrelevementAutomatique;
import model.orm.AccessPrelevementAutomatique;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class PrelevementsManagementController implements Initializable {

    // Etat application
    private DailyBankState dbs;
    private PrelevementManagement pm;

    // Fenêtre physique
    private Stage primaryStage;

    // Données de la fenêtre
    private Client clientDuCompte;
    private CompteCourant compteConcerne;
    private ObservableList<PrelevementAutomatique> olPrelevements;

    // Manipulation de la fenêtre
    public void initContext(Stage _primaryStage, PrelevementManagement _pm, DailyBankState _dbstate, Client client, CompteCourant compte) {
        this.primaryStage = _primaryStage;
        this.dbs = _dbstate;
        this.pm = _pm;
        this.clientDuCompte = client;
        this.compteConcerne = compte;

        this.configure();
    }

    /**
     * Méthode appelée lors de l'ouverture qui va initialiser les composants de la fenêtre et faire en sorte que lors de la fermeture de la fenêtre, la méthode closeWindow() soit appelée.
     */
    private void configure() {
        this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

        this.olPrelevements = FXCollections.observableArrayList();
        this.lvPrelevements.setItems(this.olPrelevements);
        this.lvPrelevements.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.updateInfoPrelevementsCompte();
        this.lvPrelevements.getFocusModel().focus(-1);
        this.lvPrelevements.getSelectionModel().selectedItemProperty().addListener(e -> this.validateComponentState());
        this.validateComponentState();
    }

    /**
     * méthode appelée pour ouvrir la fenêtre
     */
    public void displayDialog() {
        this.primaryStage.showAndWait();
    }

    // Gestion du stage
    private Object closeWindow(WindowEvent e) {
        this.doCancel();
        e.consume();
        return null;
    }

    // Attributs de la scene + actions
    @FXML
    private Label lblInfosClient;
    @FXML
    private Label lblInfosCompte;
    @FXML
    private ListView<PrelevementAutomatique> lvPrelevements;
    @FXML
    private Button btnDelPrelevement;
    @FXML
    private Button btnModifPrelevement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void doCancel() {
        this.primaryStage.close();
    }

    /**
     * Méthode appelée pour effacer un prélèvement
     * @throws DatabaseConnexionException
     * @throws DataAccessException
     */
    @FXML
    private void doDelPrelevement() throws DatabaseConnexionException, DataAccessException {
        PrelevementAutomatique prelevement = this.lvPrelevements.getSelectionModel().getSelectedItem();
        AccessPrelevementAutomatique aca = new AccessPrelevementAutomatique();
        if (prelevement != null) {
            aca.deletePrelevementAutomatique(prelevement.idPrelevementAutomatique);
            this.updateInfoPrelevementsCompte();
        }
    }


    /**
     * Méthode appelée pour modifier un prélèvement
     */
    @FXML
    private void doModifPrelevement() {
        this.pm.modifierPrelevement(this.lvPrelevements.getSelectionModel().getSelectedItem(), this.lvPrelevements.getSelectionModel().getSelectedItem().idNumCompte);
        this.updateInfoPrelevementsCompte();
    }

    /**
     * Méthode appelée pour créer un nouveau prélèvement
     */
    @FXML
    private void doCreatePrelevement() {


        this.pm.modifierPrelevement(null, this.clientDuCompte.idNumCli);
        this.updateInfoPrelevementsCompte();

    }

    /**
     * Méthode appelée pour valider lo'état des composants de la fenêtre
     */
    private void validateComponentState() {
        int selectedIndice = this.lvPrelevements.getSelectionModel().getSelectedIndex();
        if (selectedIndice >= 0) {
            this.btnDelPrelevement.setDisable(false);
            this.btnModifPrelevement.setDisable(false);
        } else {
            this.btnDelPrelevement.setDisable(true);
            this.btnModifPrelevement.setDisable(true);
        }
    }

    /**
     * Méthode appelée pour mettre à jour les informations du compte concerné
     */
    private void updateInfoPrelevementsCompte() {

        ArrayList<PrelevementAutomatique> listePrelevements;

        listePrelevements = this.pm.prelevementsCompte();



        String info;
        info = this.clientDuCompte.nom + "  " + this.clientDuCompte.prenom + "  (id : " + this.clientDuCompte.idNumCli
                + ")";
        this.lblInfosClient.setText(info);

        info = "Cpt. : " + this.compteConcerne.idNumCompte + "  "
                + String.format(Locale.ENGLISH, "%12.02f", this.compteConcerne.solde) + "  /  "
                + String.format(Locale.ENGLISH, "%8d", this.compteConcerne.debitAutorise);
        this.lblInfosCompte.setText(info);

        this.olPrelevements.clear();
        if (listePrelevements != null) {
            for (PrelevementAutomatique op : listePrelevements) {
                this.olPrelevements.add(op);
            }
        }
        this.validateComponentState();
    }
}
