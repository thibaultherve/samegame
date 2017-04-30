import java.awt.*;
import java.awt.image.*;

/**
 * La classe Bloc permet de créer des objets Bloc
 * @author Thibault HERVE - Flavien RIZOULIERES
*/
public class Bloc {
  /**
   * coordonnée x du bloc
   */
  protected int posx;

  /**
   * coordonnée y du bloc
   */
  protected int posy;

  /**
   * largeur du bloc
   */
  protected int largeur_case;

  /**
   * hauteur du bloc
   */
  protected int hauteur_case;

  /**
   * image x du bloc
   */
  protected Image imageBloc;

  /**
   * bloc nord du bloc
   */
  Bloc nord;

  /**
   * bloc sud du bloc
   */
  Bloc sud;

  /**
   * bloc est du bloc
   */
  Bloc est;

  /**
   * bloc ouest du bloc
   */
  Bloc ouest;

  /**
   * type du bloc
   * V = vert, R = rouge, B = bleu
   */
  char typeBloc;

  /**
   * détermine si le bloc a le focus
   */
  boolean bFocus = false;

  /**
   * détermine si le bloc est encore actif
   */
  boolean actif = true;

  /**
   * constructeur du Bloc
   * @param P_posx coordonnée x du bloc
   * @param P_posy coordonnée y du bloc
   * @param P_largeur largeur du bloc
   * @param P_hauteyr hauteur du bloc
   */
  public Bloc(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
    posx=P_posx;
    posy=P_posy;
    largeur_case=P_largeur;
    hauteur_case=P_hauteur;
  }
}
