package application.view;

import application.DailyBankState;
import application.control.EmployesManagement;
import application.tools.AlertUtilities;
import application.tools.ConstantesIHM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Employe;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployesManagementController implements Initializable {

	// Etat application
	private DailyBankState dbs;
	private EmployesManagement em;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre
	private ObservableList<Employe> olc;

	// Manipulation de la fenêtre
	public void initContext(Stage _primaryStage, EmployesManagement _em, DailyBankState _dbstate) {
		this.em = _em;
		this.primaryStage = _primaryStage;
		this.dbs = _dbstate;
		this.configure();
	}
	
	/**
	 * fonction qui renvoi vers la fonction closeWindow quand l'utilisateur souhaite fermer la fenêtre et qui valide l'état des composants 
	 */
	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

		this.olc = FXCollections.observableArrayList();
		this.lvEmployes.setItems(this.olc);
		this.lvEmployes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		this.lvEmployes.getFocusModel().focus(-1);
		this.lvEmployes.getSelectionModel().selectedItemProperty().addListener(e -> this.validateComponentState());
		this.validateComponentState();
	}

	/**
	 * fonction qui affiche la scène principale est se met en attente
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
	private TextField txtNum;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private ListView<Employe> lvEmployes;
	@FXML
	private Button btnDelEmploye;
	@FXML
	private Button btnModifEmploye;

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
	 * fonction FXML qui ferme la scène principale
	 */
	@FXML
	private void doCancel() {
		this.primaryStage.close();
	}

	/**
	 * fonction FXML qui cherche qui enregistre tout les employes trouves qui suivent le pattern donné
	 */
	@FXML
	private void doRechercher() {
		int numEmploye;
		try {
			String nc = this.txtNum.getText();
			if (nc.equals("")) {
				numEmploye = -1;
			} else {
				numEmploye = Integer.parseInt(nc);
				if (numEmploye < 0) {
					this.txtNum.setText("");
					numEmploye = -1;
				}
			}
		} catch (NumberFormatException nfe) {
			this.txtNum.setText("");
			numEmploye = -1;
		}

		String debutNom = this.txtNom.getText();
		String debutPrenom = this.txtPrenom.getText();

		if (numEmploye != -1) {
			this.txtNom.setText("");
			this.txtPrenom.setText("");
		} else {
			if (debutNom.equals("") && !debutPrenom.equals("")) {
				this.txtPrenom.setText("");
			}
		}

		ArrayList<Employe> listeEmpl;
		listeEmpl = this.em.getlisteEmployes(numEmploye, debutNom, debutPrenom);

		this.olc.clear();
		for (Employe cli : listeEmpl) {
			this.olc.add(cli);
		}

		this.validateComponentState();
	}


	/**
	 * fonction FXML qui modifie les information d'un client en fonction du client selectionné
	 */
	@FXML
	private void doModifierEmploye() {

		int selectedIndice = this.lvEmployes.getSelectionModel().getSelectedIndex();
		if (selectedIndice >= 0) {
			Employe cliMod = this.olc.get(selectedIndice);
			Employe result = this.em.modifierEmploye(cliMod);
			if (result != null) {
				this.olc.set(selectedIndice, result);
			}
		}
	}

	/**
	 * fonction FXML qui desactive un employe selectionné
	 */
	@FXML
	private void doEffacerEmploye() {
		int selectedIndice = this.lvEmployes.getSelectionModel().getSelectedIndex();
		if (selectedIndice >= 0) {
			Employe cliMod = this.olc.get(selectedIndice);
			if (!ConstantesIHM.isAdmin(cliMod)) {
				this.em.doEffacerEmploye(cliMod);
				this.olc.remove(selectedIndice);
			} else {
				AlertUtilities.showAlert(this.primaryStage, "On ne peut pas effacer le chef d'agence", null, "Choisit un autre employe" ,
						Alert.AlertType.WARNING);
			}

		}
	}

	/**
	 * fonction FXML qui crée un nouvel employe
	 */
	@FXML
	private void doNouveauEmploye() {
		Employe client;
		client = this.em.nouveauEmploye();
		if (client != null) {
			this.olc.add(client);
		}
	}

	/**
	 * fonction qui valide l'état des composants
	 */
	private void validateComponentState() {
		// Non implémenté => désactivé

		int selectedIndice = this.lvEmployes.getSelectionModel().getSelectedIndex();
		if (selectedIndice >= 0) {
			this.btnModifEmploye.setDisable(false);
			this.btnDelEmploye.setDisable(false);
		} else {
			this.btnModifEmploye.setDisable(true);
			this.btnDelEmploye.setDisable(true);
		}
	}
}
