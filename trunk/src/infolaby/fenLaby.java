package infolaby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class fenLaby extends JFrame implements ActionListener{
	
	private JPanel laby = new JPanel(); // Panneau contenant les cases du Labyrinthe
	private JPanel cont = new JPanel(); // panneau principal contenant tous les autres
	private JPanel south = new JPanel();
	private JPanel controle = new JPanel();
	private JPanel north = new JPanel();
    
    //Jlabel : Ecriture de chaîne de caractères sur l'interface
    private JLabel label1 = new JLabel("Panneau de contr�le"); 
    private JLabel labelE = new JLabel("0");
    private JLabel labelD = new JLabel("0");
    private JLabel labelM = new JLabel("0");
    private JLabel labelP = new JLabel("0");
    private JLabel labelT = new JLabel("0");
	
    // JComboBox : listes déroulantes
	private JComboBox comboBoxNbEch = new JComboBox(); 
	private JComboBox comboBoxNbIt = new JComboBox();
	private JComboBox comboBoxTypeResol = new JComboBox();
	private JComboBox comboBoxT0 = new JComboBox();
	
	//JButton : Boutons
	private JButton boutonArreter = new JButton("Arreter");
	private JButton boutonLancer = new JButton("Lancer"); // Boutons Start et Next
	private JButton boutonContinuer = new JButton("Continuer");
	
    //Autres variables
	
  	private int nbEchantillon=2;
    private int iteration=100;
	
    
	private int sizeW, sizeH; // Taile de la Frame
	private int win, lose, T0 = 100;
	
	
	private Case[][] C = new Case[15][15]; // ensemble des cases du labyrinthe
	private Case Depart = new Case(); // Case de d�part
	private Case Arrivee = new Case(); // Case d'arriv�e	
	private boolean next_pressed, start_pressed; // Bool�en d'interaction avec les boutons
	
	public fenLaby(){
		
		Toolkit tk = Toolkit.getDefaultToolkit(); // D�finition du Toolkit pour avoir la taille de l'�cran
		Dimension d = tk.getScreenSize(); // r�cup�re les dimensions de l'�cran
		// d�finition des dimentions de la JFrame
		this.sizeH = 3*d.height/4;
		this.sizeW = 2*d.height/3;
		// size of the frame
		this.setSize(this.sizeW, this.sizeH);
		this.setResizable(true);
		// frame's title
		this.setTitle("Labyrinthe");
		// Position to center
		this.setLocationRelativeTo(null); 
		// how to stop the program
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init(); // initialize the labyrinthe
	}

	private void init() {
		// TODO Auto-generated method stub
		String[] listeE={"2","5","10","20","30","50"};
		String[] listeI={"100","500","1000","5000","10000","20000"};
		String[] listeResol ={"Recuit simul�"};
		String[] listeT0={"10","50","100","200","300","1000"};
		
		comboBoxNbEch = new JComboBox(listeE);
		comboBoxNbEch.addActionListener(this);
		comboBoxNbIt = new JComboBox(listeI);
		comboBoxNbIt.addActionListener(this);
		comboBoxTypeResol = new JComboBox(listeResol);
		comboBoxTypeResol.addActionListener(this);
		comboBoxT0 = new JComboBox(listeT0);
		comboBoxT0.addActionListener(this);
		
		// Placement des commandes du haut
		
		north.setLayout(new GridLayout(4, 3 , 10 , 10));
		north.add(new JLabel("Nombre d'�chantillon"));
		north.add(new JLabel("Nombre d'it�rations"));
		north.add(new JLabel("Type de r�solution"));
		north.add(comboBoxNbEch);
		north.add(comboBoxNbIt);
		north.add(comboBoxTypeResol);
		north.add(new JLabel("Temp�rature initiale"));
		north.add(comboBoxT0);
		north.add(new JLabel(""));
		north.add(boutonLancer );
		north.add(boutonArreter);
		north.add(boutonContinuer);
		
		label1.setHorizontalAlignment(JLabel.CENTER );
		label1.setPreferredSize(new Dimension(500,20));
		controle.setLayout(new BorderLayout());
		controle.add(label1 ,BorderLayout.NORTH);
		controle.add(north, BorderLayout.SOUTH);
		
		
		//Placement des info du bas
		south.setPreferredSize(new Dimension(this.sizeW,this.sizeH/5));
		south.setBorder(BorderFactory.createLineBorder(Color.black));
		south.setLayout(new GridLayout(7, 3 , 7 , 7));
		south.add (new JLabel("  "));
		south.add (new JLabel("  "));
		south.add (new JLabel("  "));
		
		south.add (new JLabel("Num�ro Echantillon :"));
		south.add(labelE);
		south.add (new JLabel("  "));
		south.add (new JLabel("Energie (distance � case finale) :"));
		south.add(labelD);
		south.add (new JLabel("  "));
		south.add (new JLabel("Nombre d'it�rations pour l'�chantillon :"));
		south.add(labelM);
		south.add (new JLabel("  "));
		south.add (new JLabel("Pourcentage de r�ussite :"));
		south.add(labelP);
		south.add (new JLabel("  "));
		south.add (new JLabel("Temp�rature finale"));
		south.add(labelT);
		south.add (new JLabel("  "));
		
		south.add (new JLabel("  "));
		south.add (new JLabel("  "));
		south.add (new JLabel("  "));
		
		cont.setLayout(new BorderLayout());
		laby.setBackground(Color.BLACK);
		//controle.setBorder(BorderFactory.createLineBorder(Color.black)); // cr�e des bordures pour les cases
		laby.setBorder(BorderFactory.createLineBorder(Color.black));

		dessineLaby(); // draw the Labyrinthe
		
		Depart = C[1][1];	// Case de d�part
		Arrivee = C[13][13]; // case d'arriv�e
				
		cont.add(laby, BorderLayout.CENTER); // Positionnement du Labyrinthe
		cont.add(controle, BorderLayout.NORTH); // Positionement du panel de contr�le
		cont.add(south, BorderLayout.SOUTH);
		

		boutonArreter.setEnabled (false);
		boutonContinuer.setEnabled (false);
		boutonLancer.addActionListener(this);
		boutonContinuer.addActionListener(this);
		boutonArreter.addActionListener(this);
		// Set a new Panel as our ContentPane
		this.setContentPane(cont);
		
		// make the Frame visible
		this.setVisible(true);
		
		generate(); // calcule le chemin
	}

	private void generate() {
		// TODO Auto-generated method stub
		// methode qui calcul le chemin
		
		// code du recuit simul�
		this.next_pressed = false;
		this.start_pressed = false;
		
		do {
			
			do {
				
				if (next_pressed) {

					// eV = energie du voisin, ebest = e = energie actuelle, emin = enegie minimale, k = nbr d'it�raions, Kmax = maximum d'it�rations
					int eV, e, ebest = 25, emin = 0, k = iteration, kmin = 0;
					// T = Temp�rature
					double T = 0;
					// posi = position actuelle, posiV = position du voisin
					Case posi = new Case(), posiV;
					posi = Depart;
					Depart.setDone(1);
					Depart.setColor("gris");
					Arrivee.setColor("gris");
					
					T = this.T0;

					e = posi.getNrj();

					effaceLaby();

					while (k > kmin && e > emin) {

						if (start_pressed) {
							posiV = stepToVoisin(posi); // On g�n�re le voisin
							eV = posiV.getNrj(); // On r�cup�re l'�nergie du voisin
							Depart.setColor("gris");
							if (eV <= ebest) { // Si l'energie du voisin est <= � ebest//posibest = posiV;
								ebest = eV;
								posi = posiV;
								e = eV;
								posi.setDone(posi.getDone() + 1);

							} else if (random() < Math.exp(-(eV - e) / T)) {
								posi = posiV;
								e = eV;
								posi.setDone(posi.getDone() + 1);

							} else {
								posi.setDone(posi.getDone() + 1);
							}

							k--;
							T = diminuer(T); // on diminue la temp�rature
						}
					}

					mostVisited();

					labelD.setText(e + ""); // actualise la distance entre la derni�re case visit�e et la case arriv�e

					if (e == 0)
						win++;
					else
						lose++;

					labelE.setText((this.win + this.lose) + ""); // actualise le num�ro d'�chantillon
					labelP.setText((100 * this.win / (this.win + this.lose)) + "%");
					labelM.setText((iteration - k) + "");
					labelT.setText(T + "");

					this.next_pressed = false;

					this.repaint(); // actualise l'affichage

					// Informations
					System.out.println("Test " + (win + lose)
							+ "_____________________________________\n"
							+ "                                          \n"
							+ "Temp�rature finale : " + T + "\n"
							+ "Nombre d'it�rations : " + (iteration - k) + "\n"
							+ "energie finale : " + e + "\n"
							+ "Pourcentage de r�ussite depuis le d�but: "
							+ (100 * win / (win + lose)) + "%\n"
							+ "____________________________________________");
				}
				
				this.repaint(); // actualise l'affichage
				
			} while (win + lose < nbEchantillon);
			
			comboBoxTypeResol.setEnabled(true);
			comboBoxNbIt.setEnabled(true);
			comboBoxNbEch.setEnabled(true);
			boutonLancer.setText("Relancer");
			boutonLancer.setEnabled(true);
			boutonContinuer.setEnabled(false);
			//boutonArreter.setEnabled(false);
			
			this.repaint(); // actualise l'affichage
			
		} while (true);
		

	}

	private void dessineLaby(){
		
		laby.setLayout(new GridLayout(15, 15, laby.getWidth()/15,laby.getHeight()/15)); // D�finition des grilles
		
		for(int i = 0; i <= 14; i++){
			for (int j = 0; j <= 14; j++) {
				
				C[i][j] = new Case();
				C[i][j].setNrj(26-i-j);
				switch(i){
					case 1:
						if(j==1 || j==2 || j==3 || j==4 || j==5 || j==6 || j==7 || j==9 || j==10 || j==11 || j==12 || j==13)
							C[i][j].setState(true);
						break;
					case 2:
						if(j==1 || j==3 || j==7 || j==9 || j==13)
							C[i][j].setState(true);
					break;
					case 3:
						if(j==1 || j==3 || j==4 || j==5 || j==6 || j==7 || j==9 || j==11 || j==12 || j==13)
							C[i][j].setState(true);
					break;
					case 4:
						if(j==1 || j==3 || j==7 || j==9 || j==11 || j==13)
							C[i][j].setState(true);
					break;
					case 5:
						if(j==1 || j==2 || j==3 || j==4 || j==5 || j==7 || j==9 || j==8 || j==11 || j==13)
							C[i][j].setState(true);
					break;
					case 6:
						if(j==1 || j==5 || j==11 || j==13)
							C[i][j].setState(true);
					break;
					case 7:
						if(j==1 || j==3 || j==4 || j==5 || j==6 || j==7 || j==9 || j==10 || j==11 || j==12 || j==13)
							C[i][j].setState(true);
					break;
					case 8:
						if(j==1 || j==3 || j==9)
							C[i][j].setState(true);
					break;
					case 9:
						if(j==1 || j==3 || j==4 || j==5 || j==6 || j==7 || j==9 || j==10 || j==11 || j==12 || j==13)
							C[i][j].setState(true);
					break;
					case 10:
						if(j==1 || j==7 || j==9 || j==13)
							C[i][j].setState(true);
					break;
					case 11:
						if(j==1 || j==3 || j==4 || j==5 || j==7 || j==9 || j==8 || j==11 || j==12 || j==13)
							C[i][j].setState(true);
					break;
					case 12:
						if(j==1 ||j==3 || j==5 || j==11)
							C[i][j].setState(true);
					break;
					case 13:
						if(j==1 || j==2 || j==3 || j==5 || j==7 || j==8 || j==9 || j==10 || j==11 || j==12 || j==13)
							C[i][j].setState(true);
					break;
					default :
						C[i][j].setState(false);
					break;
				}
				
				laby.add(C[i][j]);
				
			}
		}
		
		// d�finition des voisins
		for(int i = 0; i <= 14; i++){
			for (int j = 0; j <= 14; j++) {
				
					if (C[i][j].getState()) {
						//
						Case[] Voisin = new Case[4];
						Voisin[0] = C[i - 1][j];
						Voisin[1] = C[i][j + 1];
						Voisin[2] = C[i + 1][j];
						Voisin[3] = C[i][j - 1];
						C[i][j].setVoisins(Voisin);
					}//*/
			}
		}
	}

	private double diminuer(double t) { // M�thode qui diminue � chaque fois qu'on l'appelle, la valeur de la temp�rature
		// TODO Auto-generated method stub
		return t=0.9991*t;
	}

	private double random() { // Methode qui retourne une valeur au hasard
		// TODO Auto-generated method stub
		return Math.random();
	}
		
	private Case stepToVoisin(Case posi) { // M�thode qui g�n�re un voisin de fa�on al�atoire
		// TODO Auto-generated method stub
		Case V = new Case();
		Case[] v = new Case[4];
		int i=1;
		double r=0;
		boolean b = false;
		
		v = posi.getVoisins();
		
			do {
				r = Math.random(); // On g�n�re une valeur de fa�on al�atoire 
				if (r>=0 && r <= 0.25)
					i = 0;
				else if (r > 0.25 && r <= 0.5)
					i = 1;
				else if (r > 0.5 && r <= 0.75)
					i = 2;
				else if (r > 0.75 && r <= 1)
					i = 3;
				
				// En fonction de la valeur de r on d�cide quel voisin on prend
				
				try {	// On teste si le voisin existe ou pas et on affecte � V la valeur du voisin i
					b = v[i].getState();
					V = v[i];
					//System.out.println("r= "+r+" i= "+i);
					//System.out.println(""+b);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			} while (b==false);
			
		return V;
	}

	public void actionPerformed(ActionEvent arg0) {
        
		if(arg0.getSource() == boutonLancer){
			
			this.iteration = Integer.parseInt((String)comboBoxNbIt.getSelectedItem());
			this.nbEchantillon = Integer.parseInt((String)comboBoxNbEch.getSelectedItem());
			this.T0 = Integer.parseInt((String)comboBoxT0.getSelectedItem());
			
			this.win = 0;
			this.lose = 0;
			
			start_pressed = true;
			next_pressed = true;
			
			//comboBoxTypeResol.setEnabled(false);
			//comboBoxNbIt.setEnabled(false);
			comboBoxNbEch.setEnabled(false);
			boutonContinuer.setEnabled(true);
			boutonArreter.setEnabled(true);
			boutonLancer.setEnabled(false);
		}
		
		if(arg0.getSource() == boutonContinuer){
			this.iteration = Integer.parseInt((String)comboBoxNbIt.getSelectedItem());
			this.nbEchantillon = Integer.parseInt((String)comboBoxNbEch.getSelectedItem());
			this.T0 = Integer.parseInt((String)comboBoxT0.getSelectedItem());
			
			next_pressed = true;
			
			boutonContinuer.setEnabled(true);
			boutonArreter.setEnabled(true);
		}
		
		if(arg0.getSource() == boutonArreter){
			next_pressed = false;
			start_pressed = false;
			
			this.win = 0;
			this.lose = 0;
			
			labelE.setText("0");
			labelP.setText("0%");
			labelM.setText("0");
			labelD.setText("0");
			labelT.setText("0");
			
			comboBoxTypeResol.setEnabled(true);
			comboBoxNbIt.setEnabled(true);
			comboBoxNbEch.setEnabled(true);
			boutonContinuer.setEnabled(false);
			boutonArreter.setEnabled(false);
			boutonLancer.setEnabled(true);
			
			effaceLaby(); // On efface le labyrinthe
		}

    }

	public void mostVisited(){

		
		// on r�cup�re le nombre de fois que la case la plus visit�e l'a �t�
		int imax = 1, jmax = 1;
		for (int i = 0; i <= 14; i++) {
			for (int j = 0; j <= 14; j++) {
				if (C[i][j].getState()) {
					if (C[imax][jmax].getDone() <= C[i][j]
							.getDone()) {
						imax = i;
						jmax = j;
					}
				}
			}
		}
		// On informe toutes les cases de la valeur de passage la plus �lev�e
		for (int i = 0; i <= 14; i++) {
			for (int j = 0; j <= 14; j++) {
				if (C[i][j].getState()) {
					C[i][j].setDoneMax(C[imax][jmax].getDone());
				}
			}
		}
		
	}
	
	public void effaceLaby(){
		// Remet les cases dans un �tat neutre
		for (int i = 0; i <= 14; i++) {
			for (int j = 0; j <= 14; j++) {

				C[i][j].setDone(0);
			}
		}
	}
}
