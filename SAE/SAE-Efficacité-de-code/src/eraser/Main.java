package eraser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	Scanner clavier = new Scanner(System.in);
    	System.out.println("veuillez rentrer une chaine de caractère :");
    	String texte = clavier.nextLine();
    	System.out.println("voici la version traitée :");
        System.out.println(Eraser.erase(texte));
    }
}
