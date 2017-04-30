import java.awt.*;
import java.awt.image.*;
import javax.swing.JComponent;
import java.awt.event.*;
import java.util.Random;
import java.lang.Thread;
import javax.swing.*;
import java.io.*;

public class Grille extends JComponent implements MouseListener, MouseMotionListener, ActionListener {
	double score=0;
	double add;
	String scoreTXT="0";
	String addTXT="0";
	Bloc[] tabBlocs = new Bloc[150];
	int choix=0;
	File file=null;

	JButton jb_fichier = new JButton(new ImageIcon("./img/fic.png"));
	JButton jb_aleatoire = new JButton(new ImageIcon("./img/ale.png"));
	JButton centre = new JButton(new ImageIcon("./img/SameGame.png"));
	
	public Grille() {
		super();    	
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		jb_aleatoire.addActionListener(this);
		jb_fichier.addActionListener(this); 
		centre.addActionListener(this);

		BorderLayout g1 = new BorderLayout();
		this.setLayout(g1);
		
		jb_aleatoire.setPreferredSize(new Dimension(100,100));
		jb_fichier.setPreferredSize(new Dimension(100,100));
		this.add(jb_fichier,BorderLayout.NORTH); 
		this.add(jb_aleatoire,BorderLayout.SOUTH);
		this.add(centre,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent evenement) {

		if (jb_aleatoire == evenement.getSource()) {
			
			initTableBlocs();
			this.remove(jb_fichier);
			this.remove(jb_aleatoire);
			this.remove(centre);
			
		}

		else if (jb_fichier== evenement.getSource()) {			
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			//permet d'ouvrir directement dans le répertoire où se trouve le jeu
			chooser.setCurrentDirectory(new File("."));

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				String cheminFichier = file.getAbsolutePath();			
				initTableBlocs(cheminFichier);
				this.remove(jb_fichier);
				this.remove(jb_aleatoire);
				this.remove(centre);
			}
		}

		else {
			JFrame regles = new JFrame();
			regles.setSize(500, 300);
			regles.setLayout(new GridLayout(9, 1));

			JLabel menu1 = new JLabel("Regles du jeu");
			JLabel menu2 = new JLabel("Dans ce jeu, une grille est remplie de blocs de trois types");
			JLabel menu3 = new JLabel("votre but est de la vider.");
			JLabel menu4 = new JLabel("Un groupe de blocs adjacents et de même type");
			JLabel menu5 = new JLabel("il peut être retiré en cliquant dessus.");
			JLabel menu6 = new JLabel("votre but est de la vider.");
			JLabel menu7 = new JLabel("Les blocs restants se réarrangent afin de boucher les trous");
			JLabel menu8 = new JLabel("et on recommence jusqu'à avoir vidé la grille");
			JLabel menu9 = new JLabel("ou n'avoir plus que des blocs isolés.");
			regles.add(menu1);
			regles.add(menu2);
			regles.add(menu3);
			regles.add(menu4);
			regles.add(menu5);
			regles.add(menu6);
			regles.add(menu7);
			regles.add(menu8);
			regles.add(menu9);





			regles.setVisible(true);
			

		}

	}

	//permet de définir le type et les blocs périphériques de chaque bloc aléatoirement
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

		assigneTousBlocsPeripheriques();
	}

	//permet de définir le type et les blocs périphériques de chaque bloc à l'aide d'un fichier
	public void initTableBlocs(String cheminFichier) {
		int k=0;
		char cara = ' ';
		
		Random r = new Random();

		try {
			FileReader fichier = new FileReader(cheminFichier);
			BufferedReader flux = new BufferedReader(fichier);

			for(int i=0; i<10; i++) {
				for (int j=0; j<15; j++) {			

					do {
						cara = (char) flux.read();
					} while (cara=='\n');

					if (cara=='B')
						tabBlocs[k] = new BlocBleu(j,i,/*LARGEUR_FENETRE/NB_COLONNES*/50,/*HAUTEUR_FENETRE/NB_LIGNES*/57);
					else if (cara=='V')
						tabBlocs[k] = new BlocVert(j,i,/*LARGEUR_FENETRE/NB_COLONNES*/50,/*HAUTEUR_FENETRE/NB_LIGNES*/57);
					else if (cara=='R')
						tabBlocs[k] = new BlocRouge(j,i,/*LARGEUR_FENETRE/NB_COLONNES*/50,/*HAUTEUR_FENETRE/NB_LIGNES*/57);
					k++; 
				}
			}
			fichier.close();
		} catch (FileNotFoundException e) {
			System.err.println("Erreur : fichier non trouvé !");
		} catch (IOException e) {
			System.err.println("Erreur de lecture du fichier !");
		}

		assigneTousBlocsPeripheriques();
	}

	//assigne pour chaque Bloc, ses 4 Blocs périphériques
	public void assigneTousBlocsPeripheriques() {
		for (int i=0; i<150; i++) {			
			assigneBlocPeripheriques(tabBlocs[i]);
		}
	}

	//assigne au Bloc passé en argument, ses Blocs périphériques
	public void assigneBlocPeripheriques(Bloc P_bloc) {
		if (P_bloc.actif==false) {
			P_bloc.nord=null;
			P_bloc.sud=null;
			P_bloc.est=null;
			P_bloc.ouest=null;
		}

		else{
			P_bloc.nord=getBlocNord(P_bloc);
			P_bloc.sud=getBlocSud(P_bloc);
			P_bloc.est=getBlocEst(P_bloc);
			P_bloc.ouest=getBlocOuest(P_bloc);
		}

	}

	//retourne le Bloc situé au nord du Bloc passé en argument, retourne un objet null s'il n'existe pas
	public Bloc getBlocNord(Bloc P_bloc) {
		return getBloc(P_bloc.posx, P_bloc.posy-1);
	}
	//retourne le Bloc situé au sud du Bloc passé en argument, retourne un objet null s'il n'existe pas
	public Bloc getBlocSud(Bloc P_bloc) {
		return getBloc(P_bloc.posx, P_bloc.posy+1);
	}
	//retourne le Bloc situé à l'est du Bloc passé en argument, retourne un objet null s'il n'existe pas
	public Bloc getBlocEst(Bloc P_bloc) {
		return getBloc(P_bloc.posx+1, P_bloc.posy);
	}
	//retourne le Bloc situé à l'ouest du Bloc passé en argument, retourne un objet null s'il n'existe pas
	public Bloc getBlocOuest(Bloc P_bloc) {
		return getBloc(P_bloc.posx-1, P_bloc.posy);
	}

	//retourne le Bloc se situant au coordonnées x et y, retourne un objet null s'il n'existe pas
	public Bloc getBloc(int x, int y) {
		
		for (int i=0; i<150; i++) {
			if (tabBlocs[i].posx==x && tabBlocs[i].posy==y && tabBlocs[i].actif==true) {
				return tabBlocs[i];
			}
		}
		return null;
	}

	//Lorsque la souris est en mouvement, on enlève le focus à tous les Blocs puis on identifie sur quel Bloc
	//passe la souris. Si la souris est sur un Bloc, on lui met le focus ainsi qu'à ses blocs périphériques
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

	//Prend en argument un Bloc, met le focus sur tous ses Blocs périphériques
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
	if (k==1)
	    tabBlocs[save_i].bFocus=false; // on lui enlève le focus

	}



	//Lorsque on clique et que des Blocs ont le focus, on les supprime et ils deviennent inactifs
	public void mouseClicked(MouseEvent evenement) {
	
		boolean testFocus=false;

		for (int i=0; i<150; i++) {
			if (this.tabBlocs[i].bFocus==true) {
			testFocus=true;
			}
		}

		if (testFocus) {
			actualiseScore();
		}

		for (int i=0; i<150; i++) {
			if (this.tabBlocs[i].bFocus==true) {
				
				this.tabBlocs[i].actif=false;
				this.tabBlocs[i].bFocus=false;

			}      
		}	

		do {
			this.repaint();
			
		} while (reorgaColonnes());

		int decalage=0;

		do {
			assigneTousBlocsPeripheriques();
			decalage++;
			this.repaint();

		} while (reorgaLignes() && decalage<15);

	}

	//re-calcul du score
	public void actualiseScore() {
		int n=0;
	

		for (int i=0; i<150; i++) {
			if (this.tabBlocs[i].bFocus==true) {
				n++;
			}
		}
		
		// (n - 2)^2
		add = Math.pow(n-2,2);
		score = score + add ;
		scoreTXT=Integer.toString((int)score);
		addTXT=Integer.toString((int)add);
	}


	public boolean reorgaLignes() {
		boolean action=false;
		for (int i=0; i<15; i++) {
			if (checkColonneVide(i)) {
				decalageGauche(i+1);
				action=true;
			}
		}

		return action;
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
						tabBlocs[i].posy++;

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



	public boolean checkColonneVide(int numColonne) {		
		for (int j=0; j<10; j++) {
			Bloc monBloc=getBloc(numColonne,j);
			if (monBloc!=null) {
				if (monBloc.actif=true) {
					return false;				
				}				
			}
		}
		return true;	
	}

	public void decalageGauche(int numColonne) {	
		for (int i=0; i<10; i++) {
			Bloc monBloc = getBloc(numColonne,i);	

			if (monBloc!=null) {
				if (monBloc.actif==true) {
					monBloc.posx--;
					assigneBlocPeripheriques(monBloc);
				}
			}		
		}
	}

	//Test la fin de la partie, retourne un booléen, 0 si la parties est en cours, 1 si elle est terminée
	public boolean testFin() {
		boolean fin=true;

		for (int i=0; i<150; i++) {
			if(tabBlocs[i]!=null && tabBlocs[i].actif==true) {
				if (tabBlocs[i].nord!=null && tabBlocs[i].nord.actif==true) {
					if (tabBlocs[i].typeBloc==tabBlocs[i].nord.typeBloc) {
						fin=false;
					}
				}

				if (tabBlocs[i].sud!=null && tabBlocs[i].sud.actif==true) {
					if (tabBlocs[i].typeBloc==tabBlocs[i].sud.typeBloc) {
						fin=false;
					}
				}

				if (tabBlocs[i].est!=null && tabBlocs[i].est.actif==true) {
					if (tabBlocs[i].typeBloc==tabBlocs[i].est.typeBloc) {
						fin=false;
					}
				}

				if (tabBlocs[i].ouest!=null && tabBlocs[i].ouest.actif==true) {
					if (tabBlocs[i].typeBloc==tabBlocs[i].ouest.typeBloc) {
						fin=false;
					}
				}
			}
			
		}
		return fin;

	}



	@Override
	public void paintComponent(Graphics pinceau) {
		Graphics secondPinceau = pinceau.create();
		Color jaune = new Color(255,255,0,125);


		//texte + score
		secondPinceau.setFont(new Font("Verdana", Font.BOLD, 20));

		secondPinceau.setColor(jaune);

		for (int i=0; i<150; i++) {
			if (tabBlocs[i]!=null && tabBlocs[i].actif==true) {
				//on dessine l'image du bloc
				secondPinceau.drawImage(
					this.tabBlocs[i].imageBloc,
					this.tabBlocs[i].posx*this.tabBlocs[i].largeur_case,
					this.tabBlocs[i].posy*this.tabBlocs[i].hauteur_case,
					this);

				// si le bloc a le focus alors on lui ajoute un carré jaune
				if (this.tabBlocs[i].bFocus==true) {

					secondPinceau.fillRect(
						this.tabBlocs[i].posx*this.tabBlocs[i].largeur_case,
						this.tabBlocs[i].posy*this.tabBlocs[i].hauteur_case,
						this.tabBlocs[i].largeur_case,
						this.tabBlocs[i].hauteur_case);


				}
			}
		}
		if (!testFin()) {
			secondPinceau.setFont(new Font("Monospace",Font.PLAIN,30));
			secondPinceau.setColor(Color.BLACK);
			secondPinceau.drawString("SameGame", 820,35);

			secondPinceau.setFont(new Font("Monospace",Font.PLAIN,25));
			secondPinceau.drawString(scoreTXT, 820,200);


			secondPinceau.setColor(Color.RED);
			secondPinceau.setFont(new Font("Monospace",Font.BOLD,30));
			secondPinceau.drawString("SCORE", 820,170);

			secondPinceau.setFont(new Font("Monospace",Font.BOLD,25));



			if (add!=0) {
				secondPinceau.setColor(Color.GREEN);
				if (add>=250) {
					secondPinceau.setColor(Color.BLUE);
				}
				if (add>=500) {
					secondPinceau.setColor(new Color(230,223,0));
				}


				secondPinceau.drawString("(+"+addTXT+")", 920,200);
			}
		}

		if(testFin() && score!=0) {
			secondPinceau.setColor(Color.RED);
			secondPinceau.setFont(new Font("Monospace",Font.BOLD,35));    
			secondPinceau.drawString("Fin de la partie", 60,200);
			secondPinceau.setColor(Color.BLACK);
			secondPinceau.drawString("Votre score est de ", 60,235);
			secondPinceau.setColor(Color.RED);
			secondPinceau.drawString(scoreTXT, 455,235);
		}


	}


	public void mouseEntered(MouseEvent evenement) {
	}
	public void mouseExited(MouseEvent evenement) {
	}
	public void mousePressed(MouseEvent evenement) {
	}
	public void mouseReleased(MouseEvent evenement){
	}
	public void mouseDragged(MouseEvent evenement) {
	}

}
