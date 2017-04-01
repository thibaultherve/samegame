import java.awt.*;
import java.awt.image.*;

public class Bloc {
  protected int posx;
  protected int posy;
  protected int largeur_case;
  protected int hauteur_case;
  protected Image imageBloc;

  Bloc nord;
  Bloc sud;
  Bloc est;
  Bloc ouest;

  char typeBloc;

  boolean bFocus = false;

  public Bloc(int P_posx, int P_posy, int P_largeur, int P_hauteur) {
    posx=P_posx;
    posy=P_posy;
    largeur_case=P_largeur;
    hauteur_case=P_hauteur;
  }




}
