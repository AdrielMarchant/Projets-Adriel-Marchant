package application;

import application.tools.ConstantesIHM;
import model.data.AgenceBancaire;
import model.data.Employe;

public class DailyBankState {
	
	//Données de l'état 
	private Employe empAct;
	private AgenceBancaire agAct;
	private boolean isChefDAgence;

	/*
	 * fonction qui retourne l'employé actuel 
	 * 
	 * @return : la variable de type Employe
	 */
	public Employe getEmpAct() {
		return this.empAct;
	}

	/*
	 * fonction qui initialise un employé avec 1 employé en entrée 
	 * 
	 * @param employeActif IN : un employé
	 */
	public void setEmpAct(Employe employeActif) {
		this.empAct = employeActif;
	}

	/*
	 * fonction qui retourne l'agence bancaire 
	 * 
	 * @return : l'agence bancaire de la claisse
	 */
	public AgenceBancaire getAgAct() {
		return this.agAct;
	}

	/*
	 * fonction qui initialise une agence bancaire avec une agence bancaire en entrée 
	 * 
	 * @param agenceActive IN : une agence bancaire 
	 */
	public void setAgAct(AgenceBancaire agenceActive) {
		this.agAct = agenceActive;
	}

	/*
	 * fonction qui retourne si l'utilisateur est chef d'agence 
	 * 
	 * @return : booléen true si l'utilisateur est chef d'agence , faux si il ne l'est pas 
	 */
	public boolean isChefDAgence() {
		return this.isChefDAgence;
	}
	
	/*
	 * fonction qui initialise si l'utilisateur est chef d'agence 
	 * 
	 * @param IN : booléen True si l'utilisateur est chef d'agence, faux si il ne l'est pas 
	 */
	public void setChefDAgence(boolean isChefDAgence) {
		this.isChefDAgence = isChefDAgence;
	}
	
	/*
	 * fonction qui initaile si l'utilisateur est chef d'agence 
	 * 
	 * @param droitsAccess IN : chaine de caractère si la chaine de caractère vaut "chefAgence" l'utilisateur devient chef d'agence sinon il ne l'est pas 
	 */
	public void setChefDAgence(String droitsAccess) {
		this.isChefDAgence = false;

		if (droitsAccess.equals(ConstantesIHM.AGENCE_CHEF)) {
			this.isChefDAgence = true;
		}
	}
}
