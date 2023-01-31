package gestclub.view;

import java.net.URL;
import java.util.ResourceBundle;

import gestclub.model.EtatMembre;
import gestclub.model.Membre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SaisieMembreController implements Initializable {
	private Stage dialogStage;
	private EtatMembre etats;
	private Membre membre;
	
	@FXML
	private HBox hBox;
	
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
	private TextArea txtNotes;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void actionOk() {
		String erreurs = "";
		txtNom.setText(txtNom.getText().trim());
		txtPrenom.setText(txtPrenom.getText().trim());
		
		if (txtNom.getText().trim().length()<3) {
			erreurs += "Le nom est obligatoire et doit avoir plus de 2 lettres\n";
			txtNom.setStyle("-fx-background-color:red");
		} else txtNom.setStyle(null);
		if (txtPrenom.getText().trim().length()<3) {
			erreurs += "Le prénom est obligatoire et doit avoir plus de 2 lettres\n";
			txtPrenom.setStyle("-fx-background-color:red");
		}else txtPrenom.setStyle(null);
		if ( datePickInscription.getValue() == null ) {
			erreurs += "La date d'inscription est obligatoire\n";
			datePickInscription.setStyle("-fx-background-color:red");
		}else datePickInscription.setStyle(null);
		if ( !radioButAncien.isSelected() && !radioButMembre.isSelected() && !radioButProspect.isSelected() ) {
			erreurs += "Il faut préciser un état pour ce membre\n";
			hBox.setStyle("-fx-background-color:red");
		}else hBox.setStyle(null);
		

			if (erreurs.isEmpty() && membre == null){
				membre = new Membre(txtNom.getText(),txtPrenom.getText(),etats,txtVille.getText(),datePickInscription.getValue(),txtNotes.getText());
				this.dialogStage.close();
			}

			if (membre != null && erreurs.isEmpty()) {
				setMembre(membre);
				this.dialogStage.close();
				setMembre(membre);
				
		}
	

		
		
	}
	
	public void setMembre(Membre membre) {
		this.membre = membre;
		
		txtNom.setText(membre.getNom());
		txtPrenom.setText(membre.getPrenom());
		txtNotes.setText(membre.getNotes());
		txtVille.setText(membre.getVille());
		if ( membre.getEtat() == EtatMembre.Ancien) {
			this.etats = EtatMembre.Ancien;
			radioButAncien.fire();
		}
		if ( membre.getEtat() == EtatMembre.Membre) {
			this.etats = EtatMembre.Membre;
			radioButMembre.fire();
		}
		if (membre.getEtat() == EtatMembre.Prospect) {
			this.etats = EtatMembre.Prospect;
			radioButProspect.fire();
		}
		datePickInscription.setValue(membre.getDateInscription());
		
		
	}
	
	public void fillMembre(Membre m) {
		txtNom.setText(m.getNom());
	}
		
	@FXML
	private void actionAnnuler() {
		 this.dialogStage.close();
	}
	
	
	public void setDiaglogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void actionButProspect() {
		this.etats = EtatMembre.Prospect;
		
	}
	@FXML
	private void actionButMembre() {
		this.etats = EtatMembre.Membre;
		
	}
	@FXML
	private void actionButAncien() {
		this.etats = EtatMembre.Ancien;
		
	}
	
	public Membre getMembre() {
		return this.membre;
	}

}
