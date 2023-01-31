package application.view;

import application.DailyBankState;
import application.tools.CategorieOperation;
import application.tools.ConstantesIHM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.CompteCourant;
import model.data.Operation;
import model.orm.AccessCompteCourant;
import model.orm.AccessOperation;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;
import model.orm.exception.ManagementRuleViolation;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class OperationVirementPaneController implements Initializable {

	// Etat application
	private DailyBankState dbs;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre
	private CategorieOperation categorieOperation;
	private CompteCourant compteEdite;
	private Operation operationResultat;
	private CompteCourant compteRecepteur;

	// Manipulation de la fenêtre
	public void initContext(Stage _primaryStage, DailyBankState _dbstate) {
		this.primaryStage = _primaryStage;
		this.dbs = _dbstate;
		this.configure();
	}

	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
	}

	/**
	 * Méthode qui ouvre la fenêtre de OperationVirementPane et qui initialise les champs
	 * @param cpte IN : un CompteCourant qui définit le compte sur lequel on va créer l'opération
	 * @param mode IN : un EditionMode qui définit ce que va faire la fonction
	 * @return un Operation qui définit l'opération créée
	 * @throws DatabaseConnexionException
	 * @throws DataAccessException
	 */
	public Operation displayDialog(CompteCourant cpte, CategorieOperation mode) throws DatabaseConnexionException, DataAccessException {
		this.categorieOperation = mode;
		this.compteEdite = cpte;


			String info = "Cpt. : " + this.compteEdite.idNumCompte + "  "
					+ String.format(Locale.ENGLISH, "%12.02f", this.compteEdite.solde) + "  /  "
					+ String.format(Locale.ENGLISH, "%8d", this.compteEdite.debitAutorise);
			this.lblMessage.setText(info);

			this.btnOk.setText("Effectuer Virement");
			this.btnCancel.setText("Annuler Virement");

			ObservableList<String> list = FXCollections.observableArrayList();
			AccessCompteCourant ac = new AccessCompteCourant();
			for (CompteCourant cpt : ac.getCompteCourants(this.compteEdite.idNumCli)) {
				if (cpt.estCloture.equals("N") && cpt.idNumCompte != this.compteEdite.idNumCompte) {
					this.cbCompteVirement.getItems().add(cpt);
				}

			}


			this.cbCompteVirement.getSelectionModel().select(0);

		// Paramétrages spécifiques pour les chefs d'agences
		if (ConstantesIHM.isAdmin(this.dbs.getEmpAct())) {
			// rien pour l'instant
		}

		this.operationResultat = null;
		this.cbCompteVirement.requestFocus();

		this.primaryStage.showAndWait();
		return this.operationResultat;
	}

	// Gestion du stage
	private Object closeWindow(WindowEvent e) {
		this.doCancel();
		e.consume();
		return null;
	}

	// Attributs de la scene + actions
	@FXML
	private Label lblMessage;
	@FXML
	private Label lblMontant;
	@FXML
	private ComboBox<CompteCourant> cbCompteVirement;
	@FXML
	private TextField txtMontant;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancel;

	@FXML
	private Label listMSG;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Méthode qui permet d'anuler l'opération et de fermer la fenêtre
	 */
	@FXML
	private void doCancel() {
		this.operationResultat = null;
		this.primaryStage.close();
	}

	/**
	 * Méthode qui va valider le virement indiqué par l'utilisateur et si les conditions sont remplies il va créer l'opération
	 */
	@FXML
	private void doAjouter() {
		// règles de validation d'un transfert :
		// - le montant doit être un nombre valide
		double montant;
		String info;

		this.txtMontant.getStyleClass().remove("borderred");
		this.lblMontant.getStyleClass().remove("borderred");
		this.lblMessage.getStyleClass().remove("borderred");
		this.listMSG.getStyleClass().remove("borderred");
		info = "Cpt. : " + this.compteEdite.idNumCompte + "  " + String.format(Locale.ENGLISH, "%12.02f", this.compteEdite.solde) ;
		this.lblMessage.setText(info);
		try {
			montant = Double.parseDouble(this.txtMontant.getText().trim());
			if (montant <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException nfe) {
			this.txtMontant.getStyleClass().add("borderred");
			this.lblMontant.getStyleClass().add("borderred");
			this.txtMontant.requestFocus();
			return;
		}
		if(this.cbCompteVirement.getValue() == null) {
			this.listMSG.getStyleClass().add("borderred");
			return;
		}
		compteRecepteur = this.cbCompteVirement.getValue();
		this.operationResultat = new Operation(-1, montant, null, null, this.compteEdite.idNumCli, "TRANSFERT");
		AccessOperation ao = new AccessOperation();
		try {
			ao.doVirement(this.compteEdite.idNumCompte,compteRecepteur.idNumCompte, montant);
			this.primaryStage.close();
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		} catch (DatabaseConnexionException e) {
			throw new RuntimeException(e);
		} catch (ManagementRuleViolation e) {
			throw new RuntimeException(e);
		}

	}
}
