package application.view;

import application.DailyBankState;
import application.tools.AlertUtilities;
import application.tools.EditionMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.PrelevementAutomatique;

import java.net.URL;
import java.util.ResourceBundle;

public class PrelevementEditorpaneController implements Initializable {


    private PrelevementAutomatique prelevementEdite;

    private EditionMode em;

    private PrelevementAutomatique prelevementResult;

    private Stage primaryStage;

    private DailyBankState dbs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button btnValider;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextField txtMontant;

    @FXML
    private TextField txtIdPrelevement;

    @FXML
    private TextField txtDateRecurrente;

    @FXML
    private TextField txtBeneficiaire;

    /**
     * Méthode qui va permettre de valider les champs du prelevement et l'effectuer si tout va bien
     */
    @FXML
    private void doValider() {
        if (this.txtBeneficiaire.getText().isEmpty() ||this.txtBeneficiaire.getText() == "") {
            AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le bénéficiaire ne doit pas être vide",
                    Alert.AlertType.WARNING);
            this.txtBeneficiaire.requestFocus();
            return;
        }
        this.prelevementEdite.beneficiaire = this.txtBeneficiaire.getText();
        if (this.txtMontant.getText().isEmpty() ) {
            AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le montant ne doit pas être vide",
                    Alert.AlertType.WARNING);
            this.txtMontant.requestFocus();
            return;
        }
        try {
            this.prelevementEdite.montant = Double.parseDouble(this.txtMontant.getText());
            if (this.prelevementEdite.montant <= 0) {
                AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le montant doit être positif",
                        Alert.AlertType.WARNING);
                this.txtMontant.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le montant doit être un nombre",
                    Alert.AlertType.WARNING);
            this.txtMontant.requestFocus();
            return;
        }
        if (this.txtDateRecurrente.getText().isEmpty()) {
            AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "La date ne doit pas être vide",
                    Alert.AlertType.WARNING);
            this.txtDateRecurrente.requestFocus();
            return;
        }
        try {
            this.prelevementEdite.datePrelevement = Integer.parseInt(this.txtDateRecurrente.getText());
            if (this.prelevementEdite.datePrelevement <= 0) {
                AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "La date recurrente doit être positive",
                        Alert.AlertType.WARNING);
                this.txtDateRecurrente.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "La date doit être un nombre",
                    Alert.AlertType.WARNING);
            this.txtDateRecurrente.requestFocus();
            return;
        }
        this.prelevementResult = this.prelevementEdite;
        this.primaryStage.close();
    }

    /**
     * Méthode qui va ouvrir la fenêtre d'édition/création d'un prelevement
     * @param prelevement le prelevement à éditer
     * @param mode le mode d'édition
     * @return le prelevement résultant de l'édition/création
     */
    public PrelevementAutomatique getPrelevement(PrelevementAutomatique prelevement, EditionMode mode) {
        if (prelevement == null) {
            this.prelevementEdite = new PrelevementAutomatique();
            this.txtIdPrelevement.setText(String.valueOf(prelevementEdite.idPrelevementAutomatique));
        }else {
            this.prelevementEdite = new PrelevementAutomatique(prelevement);
        }
        this.em = mode;
        this.prelevementResult = null;
        switch (mode) {
            case CREATION:
                this.primaryStage.setTitle("Création d'un prélèvement");
                break;
            case MODIFICATION:
                this.primaryStage.setTitle("Modification d'un prélèvement");
                this.txtIdPrelevement.setText(String.valueOf(this.prelevementEdite.idPrelevementAutomatique));
                this.txtBeneficiaire.setText(this.prelevementEdite.beneficiaire);
                this.txtMontant.setText(String.valueOf(this.prelevementEdite.montant));
                this.txtDateRecurrente.setText(String.valueOf(this.prelevementEdite.datePrelevement));
                break;
        }


        this.primaryStage.showAndWait();
        return prelevementResult;
    }



    public void initContext(Stage _primaryStage, DailyBankState _dbstate) {
        this.primaryStage = _primaryStage;
        this.dbs = _dbstate;
        this.configure();
    }

    /**
     * fonction qui renvoi vers la fonction closeWindow quand l'utilisateur souhaite fermer la fenêtre
     */
    private void configure() {
        this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

    }

    // Gestion du stage
    private Object closeWindow(WindowEvent e) {
        this.doCancel();
        e.consume();
        return null;
    }

    /**
     * Méthode qui va permettre de fermer la fenêtre et rerourner null
     *
     */
    @FXML
    private void doCancel() {
        this.prelevementResult = null;
        this.primaryStage.close();
    }





}
