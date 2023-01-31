package eraser;

public class Eraser {
	/**
	 * m�thode qui prend en param�tre une chaine de caract�res et qui renvoie une une chaine de caract�re 
	 * qui ne garde pas les espaces simple mais garde les espaces multiples
	 * @param str IN : chaine de caract�re qui est le message � modifier
	 * 
	 * @return : la chaine de caract�re sans les espaces simples 
	 */
	public static String erase(String str) {
		int taille = str.length(); // taille de la chaine de caract�res
		int cpt = 0; // compteur d'espaces 
		String texte= ""; // texte � reconstituer caract�re par caract�re 
		for (int i = 0 ; i<taille; i++) { // boucle qui parcours toute la chaine de caract�re
			char caractere = str.charAt(i); // caract�re � l'emplacement i dans str 
			if (caractere == ' ') { // v�rification de si le caract�re � l'emplacement est un espace et incr�ment de 1 si l'espace est pr�sent
				cpt += 1; 
			}else { // si le caract�re n'est pas un espace 
				if ( cpt >1) { // si deux espace ou plus sont enregistr� dans le compteur ils sont ajout�s � la suite du texte
					for ( int j =0 ; j<cpt ; j++) {
						texte += ' ';
					}
				}
				cpt = 0; 
				texte += caractere; // ajout du caract�re normal si il n'est pas un espace
			}
		}
		str = texte;
		return str;
	}
}
