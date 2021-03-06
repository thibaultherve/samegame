import java.awt.*;
import java.awt.image.*;

/**
 * La classe BlocBleu permet de créer des objets Bloc de type bleu
 * @author Thibault HERVE - Flavien RIZOULIERES
*/
public class BlocBleu extends Bloc {
	/**
	 * constructeur du BlocBleu
	 * @param P_posx coordonnée x du bloc
	 * @param P_posy coordonnée y du bloc
	 * @param P_largeur largeur du bloc
	 * @param P_hauteyr hauteur du bloc
	 */
	public BlocBleu(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
    	super(P_posx,P_posy,P_largeur,P_hauteur);
    	this.imageBloc = Toolkit.getDefaultToolkit().getImage("img/bleu.png");
    	typeBloc='B';
  	}
}