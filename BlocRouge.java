import java.awt.*;
import java.awt.image.*;

/**
 * La classe BlocBleu permet de créer des objets Bloc de type rouge
 * @author Thibault HERVE - Flavien RIZOULIERES
*/
public class BlocRouge extends Bloc {
	/**
	 * constructeur du BlocRouge
	 * @param P_posx coordonnée x du bloc
	 * @param P_posy coordonnée y du bloc
	 * @param P_largeur largeur du bloc
	 * @param P_hauteyr hauteur du bloc
	 */
	public BlocRouge(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
		super(P_posx,P_posy,P_largeur,P_hauteur);
		this.imageBloc = Toolkit.getDefaultToolkit().getImage("img/rouge.png");
		typeBloc='R';
	}
}