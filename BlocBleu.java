import java.awt.*;
import java.awt.image.*;

public class BlocBleu extends Bloc {


  public BlocBleu(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
    super(P_posx,P_posy,P_largeur,P_hauteur);
    this.imageBloc = Toolkit.getDefaultToolkit().getImage("img/bleu.png");
    typeBloc='B';
  }





}
