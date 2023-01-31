package application.control;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.AlertUtilities;
import application.tools.EditionMode;
import application.tools.StageManagement;
import application.view.ComptesManagementController;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Client;
import model.data.CompteCourant;
import model.data.Operation;
import model.orm.AccessCompteCourant;
import model.orm.AccessOperation;
import model.orm.exception.*;
import model.orm.exception.Table;

public class ComptesManagement {

	private Stage primaryStage;
	private ComptesManagementController cmc;
	private DailyBankState dbs;
	private Client clientDesComptes;

	public ComptesManagement(Stage _parentStage, DailyBankState _dbstate, Client client) {

		this.clientDesComptes = client;
		this.dbs = _dbstate;
		try {
			FXMLLoader loader = new FXMLLoader(ComptesManagementController.class.getResource("comptesmanagement.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth()+50, root.getPrefHeight()+10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Gestion des comptes");
			this.primaryStage.setResizable(false);

			this.cmc = loader.getController();
			this.cmc.initContext(this.primaryStage, this, _dbstate, client);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes the controllers displayDialog method.
	 */
	public void doComptesManagementDialog() {
		this.cmc.displayDialog();
	}

	/**
	 * Ouvre le dialogue de gestion d'opérations
	 * @param cpt
	 */
	public void gererOperations(CompteCourant cpt) {
		OperationsManagement om = new OperationsManagement(this.primaryStage, this.dbs, this.clientDesComptes, cpt);
		om.doOperationsManagementDialog();
	}

	/**
	 * Ouvre le dialogue de création de comptes et rend un compte courant
	 */
	public CompteCourant creerCompte() {
		CompteCourant compte;
		AccessCompteCourant acc = new AccessCompteCourant();

		CompteEditorPane cep = new CompteEditorPane(this.primaryStage, this.dbs);
		compte = cep.doCompteEditorDialog(this.clientDesComptes, null, EditionMode.CREATION);
		if (compte != null) {
			try {
				acc.setNextID(compte);
				acc.creerCompteCourant(compte);
				if (Math.random() < -1) {
					throw new ApplicationException(Table.CompteCourant, Order.INSERT, "todo : test exceptions", null);
				}
			} catch (DatabaseConnexionException e) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
				ed.doExceptionDialog();
				this.primaryStage.close();
			} catch (ApplicationException ae) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
				ed.doExceptionDialog();
			}
		}
		return compte;
	}



	/**
	 * Cloturer un compte
	 * @param cpt compte à cloturer
	 */
	public void supprimerCompte(CompteCourant cpt) {
		AccessCompteCourant acc = new AccessCompteCourant();
		try {
			acc.supprimerCompteCourant(cpt);
		} catch (ApplicationException ae) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();
		}
	}

	/**
	 * Lecture des comptes
	 * @return une liste de comptes du client
	 */
	public ArrayList<CompteCourant> getComptesDunClient() {
		ArrayList<CompteCourant> listeCpt = new ArrayList<>();

		try {
			AccessCompteCourant acc = new AccessCompteCourant();
			listeCpt = acc.getCompteCourants(this.clientDesComptes.idNumCli);
		} catch (DatabaseConnexionException e) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
			ed.doExceptionDialog();
			this.primaryStage.close();
			listeCpt = new ArrayList<>();
		} catch (ApplicationException ae) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();
			listeCpt = new ArrayList<>();
		}
		return listeCpt;
	}

	/**
	 * Création d'un PDF
	 * @param cpt compte pour lequel générer le PDF
	 */
	public void genererReleve(CompteCourant cpt) {
		//Show alertbox asking for month and year
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Relevé de compte");
		alert.setHeaderText("Choisissez le mois et l'année");
		alert.setContentText("Mois :");
		alert.getDialogPane().setPrefSize(300, 200);
		TextField month = new TextField();
		month.setText("Mois");
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(month);
		alert.getDialogPane().setContent(dialogVbox);
		alert.showAndWait();
		//Get the month and year
		String monthString = month.getText();
		if ((monthString.length() != 7) && (monthString.split("\\/")).length != 2 && (monthString.split("\\/")[0]).length() != 2) {
			//Interrupt if the month is not valid
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Erreur");
			alert2.setHeaderText("Mois invalide");
			alert2.setContentText("Le mois doit être au format MM/YYYY");
			alert2.showAndWait();
			return;
		}
		else {
			AccessOperation ao = new AccessOperation();


			try {
				ArrayList<Operation> listeOp = ao.getOperationsLike(cpt.idNumCompte, monthString);
				if (listeOp.size() == 0){
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle("Erreur");
					alert2.setHeaderText("Aucune opération");
					alert2.setContentText("Aucune opération n'a été effectuée ce mois");
					alert2.showAndWait();
					return;
				}
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Image");
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("PDF", "*.pdf"));
				File file = fileChooser.showSaveDialog(this.primaryStage);
				if (file == null) {
					return;
				}
				Document document = new Document(PageSize.A4, 10f, 10f, 10f, 10f);
				PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
				document.open();
				HeaderFooter footer = new HeaderFooter(new Phrase(" page"), true);
				footer.setAlignment(Element.ALIGN_CENTER);
				footer.setBorderWidthBottom(0);
				document.setFooter(footer);


				Paragraph p = new Paragraph("Relevé de compte");
				p.setAlignment(Element.ALIGN_CENTER);
				p.setFont(FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD));
				document.add(p);
				Paragraph p2 = new Paragraph("Compte : " + cpt.idNumCompte);
				p2.setAlignment(Element.ALIGN_CENTER);
				document.add(p2);
				Paragraph p3 = new Paragraph("Client : " + this.clientDesComptes.nom + " " + this.clientDesComptes.prenom);
				p3.setAlignment(Element.ALIGN_CENTER);
				document.add(p3);
				Paragraph p4 = new Paragraph("Mois : " + monthString);
				p4.setAlignment(Element.ALIGN_RIGHT);
				document.add(p4);

				for (Operation op : listeOp) {
					document.add(new Paragraph(op.toString()));
				}
				document.close();
			} catch (DataAccessException e) {
				throw new RuntimeException(e);
			} catch (DatabaseConnexionException e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			} catch (ApplicationException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Ouvre l'interface CRUD des prélèvements
	 * @param cpt compte pour lequel ouvrir l'interface
	 */
    public void prelevement(CompteCourant cpt) {
		PrelevementManagement pm = new PrelevementManagement(this.primaryStage, this.dbs, this.clientDesComptes, cpt);
		pm.doOperationsManagementDialog();
    }
}
