import java.awt.*;
import java.awt.image.*;

public class BlocVert extends Bloc {


  public BlocVert(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
    super(P_posx,P_posy,P_largeur,P_hauteur);
    this.imageBloc = Toolkit.getDefaultToolkit().getImage("img/vert.png");
    typeBloc='V';
  }





}

