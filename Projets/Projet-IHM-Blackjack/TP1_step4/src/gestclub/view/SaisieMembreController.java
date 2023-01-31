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
	private Stage dialogStage;
	private EtatMembre etats;
	private Membre membre;
	
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
		txtNom.setText(txtNom.getText().trim());
		txtPrenom.setText(txtPrenom.getText().trim());
		if (txtNom.getLength()>2 && txtNom != null && txtPrenom != null && txtPrenom.getLength()> 2 && datePickInscription.getValue() != null && etats != null){
		membre = new Membre(txtNom.getText(),txtPrenom.getText(),etats,txtVille.getText(),datePickInscription.getValue(),txtNotes.getText());
		this.dialogStage.close();
		}else {
			System.out.println("réessayez avec des valeurs correctes");
		}
		
		
		
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
