package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.EditionMode;
import application.tools.StageManagement;
import application.view.EmployesManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Employe;
import model.orm.AccessEmploye;
import model.orm.exception.ApplicationException;
import model.orm.exception.DatabaseConnexionException;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployesManagement {

	private Stage primaryStage;
	private DailyBankState dbs;
	private EmployesManagementController emc;

	/**
	 * Charge l'FXML lors de l'appel de  emc.displayDialog();
	 * @param _parentStage
	 * @param _dbstate
	 */
	public EmployesManagement(Stage _parentStage, DailyBankState _dbstate) {
		this.dbs = _dbstate;
		try {
			FXMLLoader loader = new FXMLLoader(EmployesManagementController.class.getResource("employesmanagement.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth()+50, root.getPrefHeight()+10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Gestion des clients");
			this.primaryStage.setResizable(false);

			this.emc = loader.getController();
			this.emc.initContext(this.primaryStage, this, _dbstate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ouvre le dialogue de gestion de employes
	 */
	public void doEmployesManagementDialog() {
		this.emc.displayDialog();
	}

	/**
	 * ouvre un dialogue EmployeEditorPane avec le employe actuel en rentrée. Aprés modification par l'utilisateur l'employe modifié est retourné
	 * @param em Employe à modifier
	 * @return Employe modifié
	 */
	public Employe modifierEmploye(Employe em) {
		EmployeEditorPane cep = new EmployeEditorPane(this.primaryStage, this.dbs);
		Employe result = cep.doEmployeEditorDialog(em, EditionMode.MODIFICATION);
		if (result != null) {
			try {
				AccessEmploye ac = new AccessEmploye();
				ac.updateEmploye(result);
			} catch (DatabaseConnexionException e) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
				ed.doExceptionDialog();
				result = null;
				this.primaryStage.close();
			} catch (ApplicationException | SQLException ae) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, (ApplicationException) ae);
				ed.doExceptionDialog();
				result = null;
			}
		}
		return result;
	}

	/**
	 * ouvre un dialogue EmployeEditorPane en mode création et rien en rentrée. Aprés remplire les coordonnées l'employé est retourné
	 * @return employé retourné
	 */
	public Employe nouveauEmploye() {
		Employe employe;
		EmployeEditorPane eep = new EmployeEditorPane(this.primaryStage, this.dbs);
		employe = eep.doEmployeEditorDialog(null, EditionMode.CREATION);
		if (employe != null) {
			try {
				AccessEmploye ae = new AccessEmploye();

				ae.insertEmploye(employe);
			} catch (ApplicationException ae) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
				ed.doExceptionDialog();
				employe = null;
			}
		}
		return employe;
	}


	/**
	 * recherche des Employes dans la BD qui valident certains critères
	 * @param _numCompte IN : numéro de compte du client
	 * @param _debutNom IN : début du nom du client
	 * @param _debutPrenom IN : début du prénom du client
	 * @return Arraylist IN : des clients retrouvés avec les paramètres
	 */
	public ArrayList<Employe> getlisteEmployes(int _numCompte, String _debutNom, String _debutPrenom) {
		ArrayList<Employe> listeCli = new ArrayList<>();
		try {
			// Recherche des clients en BD. cf. AccessClient > getClients(.)
			// numCompte != -1 => recherche sur numCompte
			// numCompte != -1 et debutNom non vide => recherche nom/prenom
			// numCompte != -1 et debutNom vide => recherche tous les clients

			AccessEmploye em = new AccessEmploye();
			listeCli = em.getEmployes(this.dbs.getEmpAct().idAg, _numCompte, _debutNom, _debutPrenom);

		} catch (DatabaseConnexionException e) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
			ed.doExceptionDialog();
			this.primaryStage.close();
			listeCli = new ArrayList<>();
		} catch (ApplicationException ae) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();
			listeCli = new ArrayList<>();
		}
		return listeCli;
	}

	/**
	 * Méthode pour effacer un employé de la BD
	 * @param emplDel IN : employé à effacer
	 */
	public void doEffacerEmploye(Employe emplDel) {
		try {
			AccessEmploye ae = new AccessEmploye();
			ae.deleteEmploye(emplDel);
		} catch (ApplicationException ae) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();
		}
	}
}
