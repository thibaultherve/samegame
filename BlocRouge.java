import java.awt.*;
import java.awt.image.*;

public class BlocRouge extends Bloc {


  public BlocRouge(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
    super(P_posx,P_posy,P_largeur,P_hauteur);
    this.imageBloc = Toolkit.getDefaultToolkit().getImage("img/rouge.png");
    typeBloc='R';
  }




}
