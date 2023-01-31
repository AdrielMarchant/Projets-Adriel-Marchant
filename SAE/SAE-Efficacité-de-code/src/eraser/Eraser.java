package eraser;

public class Eraser {
	/**
	 * méthode qui prend en paramètre une chaine de caractères et qui renvoie une une chaine de caractère 
	 * qui ne garde pas les espaces simple mais garde les espaces multiples
	 * @param str IN : chaine de caractère qui est le message à modifier
	 * 
	 * @return : la chaine de caractère sans les espaces simples 
	 */
	public static String erase(String str) {
		int taille = str.length(); // taille de la chaine de caractères
		int cpt = 0; // compteur d'espaces 
		String texte= ""; // texte à reconstituer caractère par caractère 
		for (int i = 0 ; i<taille; i++) { // boucle qui parcours toute la chaine de caractère
			char caractere = str.charAt(i); // caractère à l'emplacement i dans str 
			if (caractere == ' ') { // vérification de si le caractère à l'emplacement est un espace et incrément de 1 si l'espace est présent
				cpt += 1; 
			}else { // si le caractère n'est pas un espace 
				if ( cpt >1) { // si deux espace ou plus sont enregistré dans le compteur ils sont ajoutés à la suite du texte
					for ( int j =0 ; j<cpt ; j++) {
						texte += ' ';
					}
				}
				cpt = 0; 
				texte += caractere; // ajout du caractère normal si il n'est pas un espace
			}
		}
		str = texte;
		return str;
	}
}
