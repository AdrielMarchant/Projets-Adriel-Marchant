package gestclub.view;

import java.net.URL;
import java.util.ResourceBundle;

import gestclub.model.EtatMembre;
import gestclub.model.Membre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaisieMembreController implements Initializable {
	
	private Stage  dialogStage;
	private Membre membre = null;
	
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtVille;
	
	@FXML
	private RadioButton radioButProspect;
	@FXML
	private RadioButton radioButMembre;
	@FXML
	private RadioButton radioButAncien;
	
	@FXML
	private DatePicker datePickInscription;
	
	@FXML
	private TextArea  txtNotes;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public Membre getMembre() {
		return this.membre;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	@FXML
	private void actionOk() {
		String erreurs = this.trouverErreursSaisie();
		if (erreurs.length()>0) {
			System.out.println("Saisie incorrecte :");
			System.out.println(erreurs);
		} else {
			Membre newM = this.creerMembre();
			this.membre = newM;
			System.out.println("OK on va ajouter un membre : ");
			System.out.println( newM );
			this.dialogStage.close();
		}
	}
	@FXML
	private void actionAnnuler() {
		this.membre = null;
		this.dialogStage.close();
	}
	
	private String trouverErreursSaisie() {
		String erreurs = "";
		txtNom.setText(txtNom.getText().trim());
		txtPrenom.setText(txtPrenom.getText().trim());
		
		if (txtNom.getText().trim().length()<3) {
			erreurs += "Le nom est obligatoire et doit avoir plus de 2 lettres\n";
			txtNom.setStyle("-fx-background-color:red");
		}
		if (txtPrenom.getText().trim().length()<3) {
			erreurs += "Le prénom est obligatoire et doit avoir plus de 2 lettres\n";
			txtPrenom.setStyle("-fx-background-color:red");
		}
		if ( datePickInscription.getValue() == null ) {
			erreurs += "La date d'inscription est obligatoire\n";
			datePickInscription.setStyle("-fx-background-color:red");
		}
		if ( !radioButAncien.isSelected() && !radioButMembre.isSelected() && !radioButProspect.isSelected() ) {
			erreurs += "Il faut préciser un état pour ce membre\n";
			
		}
		return erreurs;
	}
	/**
	 * Créer un Membre en partant des valeurs saisies.
	 * 
	 * cette méthode ne doit être appellée que si trouverErreursSaisie() retourne une chaine vide
	 * 
	 * @return le nouveau Membre
	 */
	private Membre creerMembre() {
		// 
		Membre newMembre = new Membre(
				 txtNom.getText().trim(),
				 txtPrenom.getText().trim(),
				 EtatMembre.Prospect,
				 txtVille.getText().trim(),
				 datePickInscription.getValue(),
				 txtNotes.getText().trim()
				);
		return newMembre;
	}

}
