import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
 * La classe SameGame permet d'afficher la fenêtre de jeu, de paramétrer cette fenêtre
 * de créer une grille et de la rendre visible
 * @author Thibault HERVE - Flavien RIZOULIERES
*/
public class SameGame {
  public static int LARGEUR_FENETRE = 15*50+300;
  public static int HAUTEUR_FENETRE = 10*57;
  public static int NB_LIGNES = 10;
  public static int NB_COLONNES = 15;

  public static void main(String[] args) {

    JFrame fenetre = new JFrame("SameGame");
    fenetre.setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE);
    fenetre.setLocationRelativeTo(null);
    
    //impossible de redimensionner la fenêtre
    fenetre.setResizable(false);

    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Grille maGrille = new Grille();
    fenetre.add(maGrille);
    fenetre.setVisible(true);
  }
}