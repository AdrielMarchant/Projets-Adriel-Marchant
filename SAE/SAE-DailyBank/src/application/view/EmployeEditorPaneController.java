package application.view;

import application.DailyBankState;
import application.control.ExceptionDialog;
import application.tools.AlertUtilities;
import application.tools.ConstantesIHM;
import application.tools.EditionMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Employe;
import model.orm.AccessEmploye;
import model.orm.exception.ApplicationException;
import model.orm.exception.DataAccessException;
import model.orm.exception.Order;
import model.orm.exception.Table;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeEditorPaneController implements Initializable {

	// Etat application
	private DailyBankState dbs;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre
	private Employe employeEdite;
	private EditionMode em;
	private Employe employeResult;

	// Manipulation de la fenêtre
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

	/**
	 * fonction qui affiche initialise les chemps en fonction des informations d'un employe et du mode d'edition
	 * 
	 *  @param employe IN : un Employe
	 *  
	 *  @param mode IN : un EditionMode qui définit ce que va faire la fonction 
	 *  
	 *  @return : return un Employe
	 */
	public Employe displayDialog(Employe employe, EditionMode mode) {

		this.em = mode;
		if (employe == null) {
			this.employeEdite = new Employe(0, "", "", "", "", "",this.dbs.getEmpAct().idAg);
		} else {
			this.employeEdite = new Employe(employe);
		}
		this.employeResult = null;
		switch (mode) {
		case CREATION:
			this.txtIdEmpl.setDisable(true);
			this.txtNom.setDisable(false);
			this.txtPrenom.setDisable(false);
			this.txtLogin.setDisable(false);
			this.txtMdp.setDisable(false);
			this.txtDroitsAcces.setDisable(true);
			this.butMDP.setDisable(true);
			this.lblMessage.setText("Informations sur le nouveau employé");
			this.butOk.setText("Ajouter");
			this.butCancel.setText("Annuler");
			this.txtDroitsAcces.setText("guichetier");
			break;
		case MODIFICATION:
			this.txtIdEmpl.setDisable(true);
			this.txtNom.setDisable(false);
			this.txtPrenom.setDisable(false);
			this.txtLogin.setDisable(false);
			this.txtDroitsAcces.setDisable(true);
			this.txtMdp.setDisable(true);
			if (ConstantesIHM.isAdmin(this.dbs.getEmpAct()) || this.dbs.getEmpAct().idEmploye == employeEdite.idEmploye) {
				this.butMDP.setDisable(false);
			} else {
				this.butMDP.setDisable(true);
			}
			this.txtDroitsAcces.setText(this.employeEdite.droitsAccess);
			this.lblMessage.setText("Informations employé");
			this.butOk.setText("Modifier");
			this.butCancel.setText("Annuler");
			break;
		case SUPPRESSION:
			// ce mode n'est pas utilisé pour les Clients :
			// la suppression d'un client n'existe pas il faut que le chef d'agence
			// bascule son état "Actif" à "Inactif"
			ApplicationException ae = new ApplicationException(Table.NONE, Order.OTHER, "SUPPRESSION CLIENT NON PREVUE",
					null);
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();

			break;
		}

		// initialisation du contenu des champs
		this.txtIdEmpl.setText("" + this.employeEdite.idEmploye);
		this.txtNom.setText(this.employeEdite.nom);
		this.txtPrenom.setText(this.employeEdite.prenom);

		this.txtLogin.setText(this.employeEdite.login);


		this.employeResult = null;

		this.primaryStage.showAndWait();
		return this.employeResult;
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
	private TextField txtIdEmpl;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtDroitsAcces;
	@FXML
	private TextField txtLogin;
	@FXML
	private TextField txtMdp;
	@FXML
	private Button butOk;
	@FXML
	private Button butCancel;
	@FXML
	private CheckBox butMDP;

	/**
	 * fonction qui s'active au debut de programme pour initialiser des données 
	 * 
	 * @param location IN : une URL de vue 
	 * 
	 * @param resources IN : un paquet de ressource 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * fonction FXML qui ferme la scène principale et qui renvoi un Employe null
	 */
	@FXML
	private void doCancel() {
		this.employeResult = null;
		this.primaryStage.close();
	}

	/**
	 * fonction FXML qui active/desactive le champ mot de passe
	 */
	@FXML
	private void doMDP() {
		if (this.butMDP.isSelected()) {
			this.txtMdp.setDisable(false);
		} else {
			this.txtMdp.setDisable(true);
		}
	}

	/**
	 * fonction FXML qui en fonction de l'état d'édition fera soit une creation soit une modification soit une suppression 
	 */
	@FXML
	private void doAjouter() {
		switch (this.em) {
		case CREATION:
			if (this.isSaisieValide()) {
				this.employeResult = this.employeEdite;
				this.primaryStage.close();
			}
			break;
		case MODIFICATION:
			if (this.isSaisieValide()) {
				this.employeResult = this.employeEdite;
				this.primaryStage.close();
			}
			break;
		case SUPPRESSION:
			this.employeResult = this.employeEdite;
			this.primaryStage.close();
			break;
		}

	}

	/**
	 * fonction qui retourne si une saisie est valide ou non et qui met à jour les données de l'employé edité
	 */
	private boolean isSaisieValide() {
		this.employeEdite.nom = this.txtNom.getText().trim();
		this.employeEdite.prenom = this.txtPrenom.getText().trim();
		this.employeEdite.droitsAccess = this.txtDroitsAcces.getText().trim();
		this.employeEdite.login = this.txtLogin.getText().trim();
		this.employeEdite.idAg = this.dbs.getAgAct().idAg;


		switch (this.em) {
			case CREATION:
				this.employeEdite.motPasse = this.txtMdp.getText().trim();
				if (this.employeEdite.motPasse.isEmpty()) {
					AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le mot de passe ne doit pas être vide",
							AlertType.WARNING);
					this.txtMdp.requestFocus();
					return false;
				}
				break;
			case MODIFICATION:
				if (this.butMDP.isSelected()) {
					this.employeEdite.motPasse = this.txtMdp.getText().trim();
				}
				break;


		}

		if (this.employeEdite.nom.isEmpty()) {
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le nom ne doit pas être vide",
					AlertType.WARNING);
			this.txtNom.requestFocus();
			return false;
		}
		if (this.employeEdite.prenom.isEmpty()) {
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le prénom ne doit pas être vide",
					AlertType.WARNING);
			this.txtPrenom.requestFocus();
			return false;
		}
		if (this.employeEdite.login.isEmpty()) {
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le login ne doit pas être vide",
					AlertType.WARNING);
			this.txtLogin.requestFocus();
			return false;
		}
		AccessEmploye acces = new AccessEmploye();
		try {
			if (acces.isUsernameTaken(this.employeEdite.login,this.employeEdite.idEmploye)){
				AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le login est déjà utilisé",
						AlertType.WARNING);
				this.txtLogin.requestFocus();
				return false;
			}
		} catch (DataAccessException e) {
		}


		return true;
	}
}
