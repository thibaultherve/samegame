import java.awt.*;
import java.awt.image.*;
import javax.swing.JComponent;
import java.awt.event.*;
import java.util.Random;

public class Grille extends JComponent implements MouseListener, MouseMotionListener{


	Bloc[] tabBlocs = new Bloc[150];
	public Grille() {
		super();
		initTableBlocs();

    //Ajout de JComponent en tant qu'observateur
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}

	public void initTableBlocs() {
		int k=0;
		Random r = new Random();
		for(int i=0; i<10; i++) {
			for (int j=0; j<15; j++) {
				int valeur = r.nextInt(3);
				if (valeur==0)
					tabBlocs[k] = new BlocBleu(j,i,/*LARGEUR_FENETRE/NB_COLONNES*/50,/*HAUTEUR_FENETRE/NB_LIGNES*/57);
				else if (valeur==1)
					tabBlocs[k] = new BlocVert(j,i,/*LARGEUR_FENETRE/NB_COLONNES*/50,/*HAUTEUR_FENETRE/NB_LIGNES*/57);
				else if (valeur==2)
					tabBlocs[k] = new BlocRouge(j,i,/*LARGEUR_FENETRE/NB_COLONNES*/50,/*HAUTEUR_FENETRE/NB_LIGNES*/57);
				k++;
			}
		}
    //assigne tous les blocs périphériques de chaque bloc
		assigneTousBlocsPeripheriques();


	}

	public void assigneTousBlocsPeripheriques() {
		for (int i=0; i<150; i++) {
			if (tabBlocs[i]!=null && tabBlocs[i].actif==true)
				assigneBlocPeripheriques(tabBlocs[i]);
		}
	}

	public void assigneBlocPeripheriques(Bloc P_bloc) {
		P_bloc.nord=getBlocNord(P_bloc);
		P_bloc.sud=getBlocSud(P_bloc);
		P_bloc.est=getBlocEst(P_bloc);
		P_bloc.ouest=getBlocOuest(P_bloc);
	}

	public Bloc getBlocNord(Bloc P_bloc) {
		return getBloc(P_bloc.posx, P_bloc.posy-1);
	}

	public Bloc getBlocSud(Bloc P_bloc) {
		return getBloc(P_bloc.posx, P_bloc.posy+1);
	}

	public Bloc getBlocEst(Bloc P_bloc) {
		return getBloc(P_bloc.posx+1, P_bloc.posy);
	}

	public Bloc getBlocOuest(Bloc P_bloc) {
		return getBloc(P_bloc.posx-1, P_bloc.posy);
	}

	public Bloc getBloc(int x, int y) {
		
		for (int i=0; i<150; i++) {
			if (tabBlocs[i].posx==x && tabBlocs[i].posy==y && tabBlocs[i].actif==true) {
				return tabBlocs[i];
			}
		}
		return null;
	}

	public void mouseMoved(MouseEvent evenement) {
		for (int i=0; i<150; i++) {
			this.tabBlocs[i].bFocus=false;
		}

		for (int i=0; i<150; i++) {
			if (evenement.getX()>=this.tabBlocs[i].posx*this.tabBlocs[i].largeur_case &&
				evenement.getY()>=  this.tabBlocs[i].posy*this.tabBlocs[i].hauteur_case &&
				evenement.getX()<this.tabBlocs[i].posx*this.tabBlocs[i].largeur_case + this.tabBlocs[i].largeur_case  &&
				evenement.getY()<this.tabBlocs[i].posy*this.tabBlocs[i].hauteur_case + this.tabBlocs[i].hauteur_case) {
				this.tabBlocs[i].bFocus=true;
			focusBlocsPeripheriques(this.tabBlocs[i]);

		}
	}
	this.repaint();
}

public void focusBlocsPeripheriques(Bloc P_bloc) {
	int k=0;
	int save_i=0;


	if (P_bloc.nord!=null) {
		if (P_bloc.typeBloc==P_bloc.nord.typeBloc && P_bloc.nord.bFocus==false) {
			P_bloc.nord.bFocus=true;
			focusBlocsPeripheriques(P_bloc.nord);
		}
	}
	if (P_bloc.sud!=null) {
		if (P_bloc.typeBloc==P_bloc.sud.typeBloc && P_bloc.sud.bFocus==false) {
			P_bloc.sud.bFocus=true;
			focusBlocsPeripheriques(P_bloc.sud);
		}
	}
	if (P_bloc.est!=null) {
		if (P_bloc.typeBloc==P_bloc.est.typeBloc && P_bloc.est.bFocus==false) {
			P_bloc.est.bFocus=true;
			focusBlocsPeripheriques(P_bloc.est);
		}
	}
	if (P_bloc.ouest!=null) {
		if (P_bloc.typeBloc==P_bloc.ouest.typeBloc && P_bloc.ouest.bFocus==false) {
			P_bloc.ouest.bFocus=true;
			focusBlocsPeripheriques(P_bloc.ouest);
		}
	}

  //boucle qui détermine si un seul bloc a le focus
	for (int i=0; i<150; i++) {
    // si un bloc a le focus 
		if (tabBlocs[i].bFocus==true) {
      k++; // on ajoute 1 à k
      save_i=i; // on sauvegarde l'index du dernier bloc qui a le focus
  }
}
  // si il y a qu'un seul bloc qui a le focus
if (k==1) {
    tabBlocs[save_i].bFocus=false; // on lui enlève
}
}




public void mouseDragged(MouseEvent evenement) {



}

public void mouseClicked(MouseEvent evenement) {

	for (int i=0; i<150; i++) {
		if (this.tabBlocs[i].bFocus==true) {
			this.tabBlocs[i].actif=false;
			this.tabBlocs[i].bFocus=false;
			
		}      
	}
	do {
		this.repaint();
	} while (reorgaColonnes());
	
}

public boolean reorgaColonnes() {	
	boolean action=false;
	for (int i=0; i<150; i++) {
		//si mon bloc est actif
		if (tabBlocs[i].actif==true) {
			//si le bloc sud est null
			if (tabBlocs[i].sud == null) {
				//si le bloc a une position en y<9
				if (tabBlocs[i].posy<9) {

					//notre bloc va de 1 case vers le sud
					tabBlocs[i].posy += 1;

					//une action a été faite
					action=true;

					//on assigne les nouveaux périphériques à notre bloc
					assigneBlocPeripheriques(tabBlocs[i]);
				}				

			}
			//mon bloc sud n'est pas null
			else {
				// si le bloc au sud n'est pas actif
				if (tabBlocs[i].sud.actif==false) {

					//notre bloc va vers la position y sud
					tabBlocs[i].posy=tabBlocs[i].sud.posy;

					//une action a été faite
					action=true;

					//on assigne les nouveaux périphériques à notre bloc
					assigneBlocPeripheriques(tabBlocs[i]);
				}
			}

		}
	}
	// on assigne les nouveaux périphériques à tous nos blocs
	assigneTousBlocsPeripheriques();

	//indique si une action a été faite
	return action;
}

public void mouseEntered(MouseEvent evenement) {

}
public void mouseExited(MouseEvent evenement) {

}
public void mousePressed(MouseEvent evenement) {

}
public void mouseReleased(MouseEvent evenement){
}

@Override
public void paintComponent(Graphics pinceau) {
	Graphics secondPinceau = pinceau.create();
	Color jaune = new Color(255,255,0,125);
	secondPinceau.setColor(jaune);
	for (int i=0; i<150; i++) {
		if (tabBlocs[i]!=null && tabBlocs[i].actif==true) {

			secondPinceau.drawImage(
				this.tabBlocs[i].imageBloc,
				this.tabBlocs[i].posx*this.tabBlocs[i].largeur_case,
				this.tabBlocs[i].posy*this.tabBlocs[i].hauteur_case,
				this);


			if (this.tabBlocs[i].bFocus==true) {
				secondPinceau.fillRect(
					this.tabBlocs[i].posx*this.tabBlocs[i].largeur_case,
					this.tabBlocs[i].posy*this.tabBlocs[i].hauteur_case,
					this.tabBlocs[i].largeur_case,
					this.tabBlocs[i].hauteur_case);
			}
		}
	}

}
}
