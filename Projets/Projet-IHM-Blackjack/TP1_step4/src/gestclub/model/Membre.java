package gestclub.model;

import java.time.LocalDate;

public class Membre {

	private String nom;
	private String prenom;
	private EtatMembre etat;
	private String ville;
	private LocalDate dateInscription;
	private String notes;
	
	public Membre(String nom,String prenom,EtatMembre etat,String ville,LocalDate dateInscription,String notes) {
		this.nom = nom;
		this.prenom = prenom;
		this.etat = etat;
		this.ville = ville;
		this.dateInscription = dateInscription;
		this.notes = notes;
	}
	
	public Membre() {
		this("Anonymous", "", EtatMembre.Prospect, "Toulouse", LocalDate.now(),"");
	}
	
	@Override
	public String toString() {
		switch(this.etat) {
		case Prospect:
			return nom+" "+prenom+" est un prospect depuis "+dateInscription.toString();
		case Membre:
			return nom+" "+prenom+" est un membre depuis "+dateInscription.toString();
		case Ancien:
			return nom+" "+prenom+" n'est plus membre depuis "+dateInscription.toString();
		default:
			return nom+" "+prenom+" a un statut inconny !";
		}
	}
	
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public EtatMembre getEtat() {
		return etat;
	}

	public void setEtat(EtatMembre etat) {
		this.etat = etat;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
