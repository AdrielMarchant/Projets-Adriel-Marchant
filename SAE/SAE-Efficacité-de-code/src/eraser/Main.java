package eraser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	Scanner clavier = new Scanner(System.in);
    	System.out.println("veuillez rentrer une chaine de caract�re :");
    	String texte = clavier.nextLine();
    	System.out.println("voici la version trait�e :");
        System.out.println(Eraser.erase(texte));
    }
}
