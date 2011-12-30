import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Fenetre extends JFrame {
    
        // Panneau affichage labyrinthe
	private Panneau pan = new Panneau();
        
        // JComboBox : listes déroulantes
	private JComboBox<String> comboBoxNbE = new JComboBox<>(); 
	private JComboBox<String> comboBoxNbI = new JComboBox<>();
	private JComboBox<String> comboBoxTypeResolution = new JComboBox<>();
        
        //JButton : Bouton
	private JButton boutonLancer = new JButton("Lancer");   
	private JButton boutonArreter = new JButton("Arreter");
	private JButton boutonContinuer = new JButton("Continuer");
        
        // JPanel : Layout pour placer les boutons et informations
	private JPanel container = new JPanel();    
	private JPanel south = new JPanel();
	private JPanel north2 = new JPanel();
	private JPanel north = new JPanel();
        private JPanel west = new JPanel();
        private JPanel west2 = new JPanel();
        
        //Jlabel : Ecriture de chaîne de caractères sur l'interface
        private JLabel label1 = new JLabel(""); 
	private JLabel labelE = new JLabel("0");
	private JLabel labelI = new JLabel("0");
	private JLabel labelD = new JLabel("0");
	private JLabel labelC = new JLabel("0");
	private JLabel labelM = new JLabel("0");
	private JLabel labelP = new JLabel("0");
	private JLabel labelC1 = new JLabel("0.0");
	private JLabel labelC2 = new JLabel("0.0"); 
	
	
        // Boolean en cours d'animation ?
        private boolean animated = true;
        // Solution trouvée par l'algorithme
        private boolean solution=false;
        
        // Thread 
	private Thread t;
	
         // Variables pour chaques algorithmes
        private Individu id_DFS=new Individu();
        private Individu id_BFS=new Individu();
        private Individu id_greedy=new Individu();
        private Individu id_A=new Individu();
        private Individu id_escalade=new Individu();
        private Echantillon echantillon_tabous=new Echantillon(1);
        private Echantillon echantillon_recuit=new Echantillon(1);
        private N_echantillon n_echantillon_genetique=new N_echantillon(1,1);    
	private N_echantillon n_echantillon_fourmis=new N_echantillon(1,1);
        
        
        
        
            //Autres variables
        private int nbIndividu=1;
      	private int nbEchantillon=1;
        private int iteration=0;

   
        private int numeroIndividuEncours=1;
	private int numeroEchantillonEncours=1;
        
        
            // Variables de sorties d'algorithme pour comparaison
	private float moyenneTempsResolution;
	private float pourcentageReussite;
	private float critere1;
	private float critere2;
        
        
        
        /**
         Constructeur
         */
	public Fenetre(){
		
            /*
             Définition de la fenetre
             */
		this.setTitle("Resolution Labyrinthe - Projet Informatique");
		this.setSize(1000, 525);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
                // Définition des listes déroulantes
		String[] listeE={"1","2","3","4","5","6","7","8","9","10"};
		String[] listeI={"10","20","30","40","50","60","70","80","90","100","110","120","130","140","150","160","170","180","190","200"};
		String[] listeResol ={"DFS","BFS","Algorithme greedy", "Algorithme A*", "Escalade", "Recherche avec tabous", "Recuit simulé", "Algorithme génétiques","Algorithme de colonies de fourmis", "Logique de prpositions","Logique de prédicats"};
		
		comboBoxNbE = new JComboBox<>(listeE);
		comboBoxNbI = new JComboBox<>(listeI);
		comboBoxTypeResolution = new JComboBox<>(listeResol);
                
                
                
                
                //Définition de l'écoute des commandes
		comboBoxNbE.addActionListener(new ItemActionNbE());
		comboBoxNbE.setPreferredSize(new Dimension(60,30));
		comboBoxNbE.setForeground (Color.blue);
		
		comboBoxNbI.addActionListener(new ItemActionNbI());
		comboBoxNbI.setPreferredSize(new Dimension(60,30));
		comboBoxNbI.setForeground (Color.blue);
		
		comboBoxTypeResolution.addActionListener(new ItemActionResol());
		comboBoxTypeResolution.setPreferredSize(new Dimension(150,30));
		comboBoxTypeResolution.setForeground (Color.blue);
		
		boutonLancer.addActionListener(new BoutonLancerListener());
		boutonArreter.addActionListener(new BoutonArreterListener());
		boutonContinuer.addActionListener(new BoutonContinuerListener());
		
		
		// Placement des commandes du haut
		north.setPreferredSize(new Dimension(400, 160));
		north.setLayout(new GridLayout(3, 3 , 5 , 5));
		north.add(new JLabel("Nombre d'échantillon"));
		north.add(new JLabel("Nombre d'individus"));
		north.add(new JLabel("Type de résolution"));
		north.add(comboBoxNbE);
		north.add(comboBoxNbI);
		north.add(comboBoxTypeResolution);
		north.add(boutonLancer );
		north.add(boutonArreter);
		boutonArreter.setEnabled (false);
		boutonContinuer.setEnabled (false);
		north.add(boutonContinuer);
		
		label1.setHorizontalAlignment(JLabel.CENTER );
		label1.setPreferredSize(new Dimension(500,20));
		north2.setPreferredSize(new Dimension(500, 180));
		north2.setLayout(new BorderLayout());
		north2.add(label1 ,BorderLayout.NORTH);
		north2.add(north, BorderLayout.SOUTH);
		
		//Placement des info du bas
		south.setPreferredSize(new Dimension(500,250));
		south.setLayout(new GridLayout(8, 2 , 5 , 5));
		south.add (new JLabel("Numéro Echantillon :"));
		south.add(labelE);
		south.add (new JLabel("Numéro Individu :"));
		south.add(labelI);
		south.add (new JLabel("Distance :"));
		south.add(labelD);
		south.add (new JLabel("Cases explorées :"));
		south.add(labelC);
		south.add (new JLabel("Moyenne temps de résolution réussite échantillon :"));
		south.add(labelM);
		south.add (new JLabel("Pourcentage de réussite :"));
		south.add(labelP);
		south.add (new JLabel("Critère 1 :"));
		south.add(labelC1);
		south.add (new JLabel("Critère 2 :"));
		south.add(labelC2);
	
                west.setPreferredSize(new Dimension(500, 100));
                west2.setPreferredSize(new Dimension(100, 100));
		west.setLayout(new BorderLayout());
		west.add(north2 ,BorderLayout.NORTH);
                west.add(west2 ,BorderLayout.WEST);
                west.add(south ,BorderLayout.CENTER);
		
                //Placement du labyrinthe au milieu et layout final
		container.setBackground (Color.white);
		container.setLayout(new BorderLayout());
		container.add (west, BorderLayout.WEST);
		container.add (pan, BorderLayout.CENTER );
		//container.add (south, BorderLayout.SOUTH);

                // Activer l'interface
		this.setContentPane(container);
		this.setVisible(true);
	}

        /**
         Dessiner toutes les n itération le meilleur résultat
         */
	public void meilleurIndividu(Panneau panneau){
            // Ecrit et dessinne le score du meilleur individu
        }
        
        
        
	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}



	public Thread getT() {
		return t;
	}



	public void setT(Thread t) {
		this.t = t;
	}


        /**
        Renvoie le panneau
        */
	public Panneau getPan() {
		return pan;
	}

	public void setPan(Panneau pan) {
		this.pan = pan;
	}



	/**
	* Classes internes implémentant l'interface (méthode active suivant
         l'appui d'un bouton
	*/
	class ItemActionNbE implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			
		label1.setText("action sur "+ comboBoxNbE.getSelectedItem() +" dans Nbe Echantillon") ;
		
		}
		}
	
	class ItemActionNbI implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			
		label1.setText("action sur "+ comboBoxNbI.getSelectedItem() +" dans nombre individu " ) ;
		}
		}
	
	class ItemActionResol implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			
		label1.setText("action sur "+ comboBoxTypeResolution.getSelectedItem() +" dans type de r�solution "+comboBoxTypeResolution.getSelectedIndex() )  ;
		}
		}
	
        //Appui bouton Lancer
	class BoutonLancerListener implements ActionListener{
		public void actionPerformed (ActionEvent arg0) {
                        animated=true;
			boutonLancer.setEnabled (false);
			boutonArreter.setEnabled (true);
			comboBoxNbE.setEnabled (false);
			comboBoxNbI.setEnabled (false);
			comboBoxTypeResolution.setEnabled (false);
			
                        //Selection nombre d'échantillon
			switch(comboBoxNbE.getSelectedIndex()){
			case 0:
				nbEchantillon=1;
				break;
			case 1:
				nbEchantillon=2;
				break;
			case 2:
				nbEchantillon=3;
				break;
			case 3:
				nbEchantillon=4;
				break;
			case 4:
				nbEchantillon=5;
				break;
			case 5:
				nbEchantillon=6;
				break;
			case 6:
				nbEchantillon=7;
				break;
			case 7:
				nbEchantillon=8;
				break;
			case 8:
				nbEchantillon=9;
				break;
			case 9:
				nbEchantillon=10;
				break;
			}
			
                        //Selection nombre d'individus
			switch(comboBoxNbI.getSelectedIndex()){
			case 0:
				nbIndividu=10;
				break;
			case 1:
				nbIndividu=20;
				break;
			case 2:
				nbIndividu=30;
				break;
			case 3:
				nbIndividu=40;
				break;
			case 4:
				nbIndividu=50;
				break;
			case 5:
				nbIndividu=60;
				break;
			case 6:
				nbIndividu=70;
				break;
			case 7:
				nbIndividu=80;
				break;
			case 8:
				nbIndividu=90;
				break;
			case 9:
				nbIndividu=100;
				break;
			case 10:
				nbIndividu=110;
				break;
			case 11:
				nbIndividu=120;
				break;
			case 12:
				nbIndividu=130;
				break;
			case 13:
				nbIndividu=140;
				break;
			case 14:
				nbIndividu=150;
				break;
			case 15:
				nbIndividu=160;
				break;
			case 16:
				nbIndividu=170;
				break;
			case 17:
				nbIndividu=180;
				break;
			case 18:
				nbIndividu=190;
				break;
			case 19:
				nbIndividu=200;
				break;
			}
                        	

                    // Selection type de résolution
                    switch(comboBoxTypeResolution.getSelectedIndex()){
                    case 0:
                            nbEchantillon=1;
                            nbIndividu=1;
                            t=new Thread(new DFS_Runnable());
                            break;
                    case 1:
                            nbEchantillon=1;
                            nbIndividu=1;
                            t=new Thread(new BFS_Runnable());
                            break;

                    case 2:
                            nbEchantillon=1;
                            nbIndividu=1;
                            t=new Thread(new Greedy_Runnable());
                            break;

                    case 3:
                            nbEchantillon=1;
                            nbIndividu=1;
                            t=new Thread(new A_Runnable());
                            break;

                    case 4:
                            nbEchantillon=1;
                            nbIndividu=1;
                            t=new Thread(new Escalade_Runnable());
                            break;

                    case 5:
                            nbEchantillon=1;
                            nbIndividu=1;
                            t=new Thread(new Tabous_Runnable());
                            break;

                    case 6:
                            nbEchantillon=1;
                            t=new Thread(new Recuit_Runnable());
                            break;

                    case 7:
                            t=new Thread(new Genetique_Runnable());
                            break;

                    case 8:
                            t=new Thread(new Fourmis_Runnable());
                            break;

                    case 9:
                            t=new Thread(new Proposition_Runnable());
                            break;

                    case 10:
                            t=new Thread(new Predicat_Runnable());
                            break;
                    }
                    System.out.println("Parametres d'execution :   Algo " +comboBoxTypeResolution.getSelectedItem()+ " ; NbI =  " + nbIndividu + " ; NbE = " + nbEchantillon);
                    t.start();
		}
	}
	
        
        //Appui bouton arreter
	class BoutonArreterListener implements ActionListener{
		public void actionPerformed (ActionEvent arg0) {
			boutonLancer.setEnabled (true);
			boutonArreter.setEnabled (false);
			comboBoxNbE.setEnabled (true);
			comboBoxNbI.setEnabled (true);
			comboBoxTypeResolution.setEnabled (true);
                        boutonContinuer.setEnabled(false);
                        solution=true;
		label1.setText("Vous avez cliqué sur le bouton Arreter");
		
		}
	}
	
        //Appui bouton Continuer
	class BoutonContinuerListener implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) {
                        animated=true;
			boutonContinuer.setEnabled (false);
		}
	}
        
        
        
        // Classes implétentant l'interface Runnable
        
        class BFS_Runnable implements Runnable{
        @Override
            public void run() {
            BFS();
            }
            }
        
        class DFS_Runnable implements Runnable{
        @Override
            public void run() {
            DFS();
            }
            }
        
        class Greedy_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeGreedy();
            }
            }
        
        class A_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeA();
            }
            }
        
        class Escalade_Runnable implements Runnable{
        @Override
            public void run() {
            Escalade();
            }
            }
        
        class Tabous_Runnable implements Runnable{
        @Override
            public void run() {
            RechercheAvecTabous();
            }
            }
        
        class Recuit_Runnable implements Runnable{
        @Override
            public void run() {
            RecuitSimule();
            }
            }
        
        class Genetique_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeGenetique();
            }
            }
        
        class Fourmis_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeColonieFourmis();
            }
            }
        
        class Proposition_Runnable implements Runnable{
        @Override
            public void run() {
            LogiqueDeProposition();
            }
            }
        
        class Predicat_Runnable implements Runnable{
        @Override
            public void run() {
            LogiqueDePredicats();
            }
            }
        
        
        /*
         * Autres méthodes
         */
         public double round(double f, int i){
            return (double)((int)(f*Math.pow(10,i)+0.5))/Math.pow(10, i);       
                    }
        
         public void sendIteration(){
             label1.setText("iteration =  "+iteration);
         }
        
        
        
        /*
         * Définition des méthodes de résolution
         */

	
	public void BFS() {
          id_BFS=new Individu();
	
            //Matrice permettant de mémoriser les positions
            int[][] mem_positionX = new int[40][40];
            int[][] mem_positionY = new int[40][40];
            //Entier permettant de sélectionner les cases de mem-position
            int i; //Sélectionne le niveau dans le diagramme des possibilités
            int j; //Sélectionne le numéro dans les niveaux
            int k;  //Permet de remplir les cases mémoires  
            
            solution=false; // boolean permettant d'arreter le programme quand l'individu est arrivé

            ///Position de l'individu dans le labyrinthe en pixels
            int x=id_DFS.getChemin().getDeplacement()[0][0]; //Abscisse
            int y=id_DFS.getChemin().getDeplacement()[0][1]; //Ordonnée
            

            //Matrice de boolean pour vérifier si une position dans le labyrinthe a déjà été mémorisée
            boolean[][] DejaMem= new boolean[15][15];

            //Initialisation de la matrice DejaMem
            for(int a=0; a<15; a++) {
                for(int b=0; b<15; b++){ 
                    DejaMem[a][b]=false;
                }
            }

            DejaMem[1][1]=true; //Permet de démarrer.
            mem_positionX[0][0]=x;
            mem_positionY[0][0]=y;

            for (i=0;i<39;i++){
                k=0;
                for (j=0;j<40;j++) {
                    if (mem_positionX[i][j]!=0) { //Nous vérifions si la case i,j de mem_position a déjà enregistré une position
                        if (solution==false) {
                        
            x=mem_positionX[i][j];
            y=mem_positionY[i][j];
            
            // Déplacer l'individu à une possibilité de case
            id_BFS.getChemin().AddDeplacement(x,y);
            getPan().setIndividu_afficher(id_BFS);
            getPan().repaint();
            
             //Arrête le programme si l'individu est arrivé
               solution=id_BFS.getLabyrinthe().estArrivee(x,y); 
            

            //Analyse des possibilités de chemin et mise en mémoire

            //Est
            if(id_BFS.getChemin().existeDeja(x+1,y)==false && id_BFS.getLabyrinthe().estMur(x+1,y)==false) {
                if(DejaMem[x+1][y]==false) {
                    mem_positionX[i+1][k]=x+1;
                    mem_positionY[i+1][k]=y;
                    k=k+1;
                    DejaMem[x+1][y]=true;
                }
            }

            //Sud
            if(id_BFS.getChemin().existeDeja(x,y+1)==false && id_BFS.getLabyrinthe().estMur(x,y+1)==false) {
                if(DejaMem[x][y+1]==false) {
                    mem_positionX[i+1][k]=x;
                    mem_positionY[i+1][k]=y+1;
                    k=k+1;
                    DejaMem[x][y+1]=true;
                }
            }

            //Ouest
            if(id_BFS.getChemin().existeDeja(x-1,y)==false && id_BFS.getLabyrinthe().estMur(x-1,y)==false) {
                if(DejaMem[x-1][y]==false) {
                    mem_positionX[i+1][k]=x-1;
                    mem_positionY[i+1][k]=y;
                    k=k+1;
                    DejaMem[x-1][y]=true;
                }
            }
            //Nord
            if(id_BFS.getChemin().existeDeja(x,y-1)==false && id_BFS.getLabyrinthe().estMur(x,y-1)==false) {
                if(DejaMem[x][y-1]==false) {
                    mem_positionX[i+1][k]=x;
                    mem_positionY[i+1][k]=y-1;
                    k=k+1;
                    DejaMem[x][y-1]=true;
                }
            }

            }
            
            }}}
            for (i=0;i<id_BFS.getChemin().getNbcases();i++) {
                System.out.println(id_BFS.getChemin().getDeplacement()[i][0]+" "+id_BFS.getChemin().getDeplacement()[i][1]);
            }
            System.out.println("distance parcourue= "+id_BFS.getChemin().getNbcases());
            boutonLancer.setEnabled (true);
            boutonArreter.setEnabled (false);
            comboBoxNbE.setEnabled (true);
            comboBoxNbI.setEnabled (true);
            comboBoxTypeResolution.setEnabled (true);
	}



	
	public void DFS() {

             id_DFS=new Individu();
             iteration=0;
            //Matrice permettant de mémoriser les positions
            //int[][] mem_positionX = new int[40][40];
            //int[][] mem_positionY = new int[40][40];
            //Entier permettant de sélectionner les cases de mem-position
            int i; //Sélectionne le niveau dans le diagramme des possibilités
            int j; //Sélectionne le numéro dans les niveaux
            //int k;  //Permet de remplir les cases mémoires
                    
            //Matrices permettant de mémoriser la case précédente d'une autre
            int[][] pos_precX = new int[40][40];
            int[][] pos_precY = new int[40][40];
            
            int tamponX; //Mémoire tampon permettant à l'individu de revenir en arrière en utilisant pos_prec
            int tamponY; 
            
            //boolean individu_arrivee=false; // boolean permettant d'arreter le programme quand l'individu est arrivé

            //Permet de noter une case ne précédent aucun débouché de solution
            boolean[][] aucune_solution=new boolean[15][15];
            
            //Position de l'individu dans le labyrinthe en pixels
            int x=id_DFS.getChemin().getDeplacement()[0][0]; //Abscisse
            int y=id_DFS.getChemin().getDeplacement()[0][1]; //Ordonnée
            

            
            //mem_positionX[0][0]=x;
            //mem_positionY[0][0]=y;
            
            solution=id_DFS.getLabyrinthe().estArrivee(x,y);
            
            while (solution==false) {
               if(animated){
            //Si possible, se déplacer à l'Est par rapport à la position mémorisée par i 
                if ( aucune_solution[x+1][y]==false && id_DFS.getChemin().existeDeja(x+1,y)==false && id_DFS.getLabyrinthe().estMur(x+1,y)==false) {
                    //Se déplacer et enregistrer position en i+1
                    id_DFS.getChemin().AddDeplacement(x+1,y);
                    pos_precX[x+1][y]=x;
                    pos_precY[x+1][y]=y;
                    x=x+1;
                    iteration++;
                }
                // sinon se déplacer au Sud ou au Nord ou à l'Ouest...
                else{ 
                    if (aucune_solution[x][y+1]==false && id_DFS.getChemin().existeDeja(x,y+1)==false && id_DFS.getLabyrinthe().estMur(x,y+1)==false) {
                        id_DFS.getChemin().AddDeplacement(x,y+1);
                        pos_precX[x][y+1]=x;
                        pos_precY[x][y+1]=y;
                        y=y+1;
                        iteration++;
                    }
                    else { //deplacement à l'Ouest
                        if (aucune_solution[x-1][y]==false && id_DFS.getChemin().existeDeja(x-1,y)==false && id_DFS.getLabyrinthe().estMur(x-1,y)==false) {
                            id_DFS.getChemin().AddDeplacement(x-1,y);                            
                            getPan().setIndividu_afficher(id_DFS);
                            getPan().repaint();
                            pos_precX[x-1][y]=x;
                            pos_precY[x-1][y]=y;
                            x=x-1;
                            iteration++;
                        }
                        else { //deplacement au Nord
                            if (aucune_solution[x][y-1]==false && id_DFS.getChemin().existeDeja(x,y-1)==false && id_DFS.getLabyrinthe().estMur(x,y-1)==false) {
                                id_DFS.getChemin().AddDeplacement(x,y-1);
                                getPan().setIndividu_afficher(id_DFS);
                                getPan().repaint();
                                pos_precX[x][y-1]=x;
                                pos_precY[x][y-1]=y;
                                y=y-1;
                                iteration++;
                            }
                            else { 
                //Si aucune position possible alors
                //Enregistrer la position comme ayant aucun débouché de solution
                                aucune_solution[x][y]=true;
                                //Remonter à l'étape i-1 
                                tamponX=pos_precX[x][y];
                                tamponY=pos_precY[x][y];
                                x=tamponX;
                                y=tamponY;
                                id_DFS.getChemin().AddDeplacement(x,y);
                                iteration++;
                            }
                        }
                    }	
                }
               }
            solution=id_DFS.getLabyrinthe().estArrivee(x,y);
            if(iteration%10==0){
                    if(animated){
                     animated=false;
                     sendIteration();
                     boutonContinuer.setEnabled (true);
                     getPan().setIndividu_afficher(id_DFS);
                     getPan().repaint();
                     if(solution){
                        iteration++;
                        }
                    }
                        System.out.println(" attente "+iteration);
                        try {
                    Thread .sleep(500);
                    } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }

                    }
            }
            for (i=0;i<id_DFS.getChemin().getNbcases();i++) {
                System.out.println(id_DFS.getChemin().getDeplacement()[i][0]+" "+id_DFS.getChemin().getDeplacement()[i][1]);
            }
            System.out.println("distance parcourue= "+id_DFS.getChemin().getNbcases());
            getPan().setIndividu_afficher(id_DFS);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled (true);
            boutonArreter.setEnabled (false);
            comboBoxNbE.setEnabled (true);
            comboBoxNbI.setEnabled (true);
            comboBoxTypeResolution.setEnabled (true);
		
	}



	
	public void AlgorithmeGreedy() {
		// TODO Auto-generated method stub
		
	}



	
	public void AlgorithmeA() {
		// TODO Auto-generated method stub
		
	}



	
	public void Escalade() {
            // Initialisation
                iteration=0;
		id_escalade=new Individu();
                solution=false;
                boolean bloque=false;
                int x=1;
                int y=1;
                int distance=id_escalade.distance(x, y);
               
                
            // Boucle pour le déplacement    
                while((solution==false)&&(bloque==false)){
                    if(animated){
                        // Recherche du meilleur voisin
                        if(!id_escalade.getLabyrinthe().estMur(x+1, y)&&(distance>id_escalade.distance(x+1, y))){ // Test à l'Est
                            x=x+1; 
                            id_escalade.getChemin().AddDeplacement(x, y);
                        }
                            else if(!id_escalade.getLabyrinthe().estMur(x, y+1)&&(distance>id_escalade.distance(x, y+1))){
                                y=y+1; 
                                id_escalade.getChemin().AddDeplacement(x, y);
                                }
                                else if(!id_escalade.getLabyrinthe().estMur(x-1, y)&&(distance>id_escalade.distance(x-1, y))){
                                    x=x-1; 
                                    id_escalade.getChemin().AddDeplacement(x, y);
                                    }
                                    else if(!id_escalade.getLabyrinthe().estMur(x, y-1)&&(distance>id_escalade.distance(x, y-1))){
                                       y=y-1;   
                                       id_escalade.getChemin().AddDeplacement(x, y);
                                    }

                        // Test bloque
                        if(distance==id_escalade.distance(x, y)){
                            bloque=true;
                        }
                        else{
                            // Calcul de la distance
                            distance=id_escalade.distance(x, y);   
                        }
                         //Test solution
                        solution=id_escalade.getLabyrinthe().estArrivee(x, y);
                        if(!solution){
                            iteration++;
                        }
                    }
                    if(iteration%10==0){
                    if(animated){
                     animated=false;
                     sendIteration();
                     boutonContinuer.setEnabled (true);
                     getPan().setIndividu_afficher(id_escalade);
                     getPan().repaint();
                     if(solution){
                        iteration++;
                        }
                    }
                        System.out.println(" attente "+iteration);
                        try {
                    Thread .sleep(500);
                    } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }

                    }
                }
                
            // Affichage du résultat
                getPan().setIndividu_afficher(id_escalade);
                getPan().repaint();
                System.out.println(" bloqué = "+ bloque + " ; solution = "+ solution);
                sendIteration();
                boutonLancer.setEnabled (true);
                boutonArreter.setEnabled (false);
		comboBoxNbE.setEnabled (true);
                comboBoxNbI.setEnabled (true);
		comboBoxTypeResolution.setEnabled (true);
	}



	
	public void RechercheAvecTabous() {
                //Initialisation
		echantillon_tabous=new Echantillon(nbIndividu);
                solution=false;
                boolean bloque=false;
                int x=1;
                int y=1;
                int x_dest=x;
                int y_dest=y;
                int distance_Est=0;
                int distance_Sud=0;
                int distance_Ouest=0;
                int distance_Nord=0;
                int distance=0;
                
                // Boucle d'execution de l'algorithme pour chaque individu
            for(int i=0; i<echantillon_tabous.getNbIndividu();i++){
                // Boucle de résolution
                while((solution==false)&&(bloque==false)){
                    if(!(echantillon_tabous.getIndividu(i+1).getLabyrinthe().estMur(x+1, y))&&!(echantillon_tabous.getIndividu(i+1).getChemin().existeDeja(x+1, y))){
                        distance_Est=echantillon_tabous.getIndividu(i+1).distance(x+1, y);
                        x_dest=x+1;
                        y_dest=y;
                        distance=distance_Est;
                    }
                    else if(!(echantillon_tabous.getIndividu(i+1).getLabyrinthe().estMur(x, y+1))&&!(echantillon_tabous.getIndividu(i+1).getChemin().existeDeja(x, y+1))){
                        distance_Sud=echantillon_tabous.getIndividu(i+1).distance(x, y+1);
                            if(distance_Sud<distance){
                                x_dest=x;
                                y_dest=y+1;
                                distance=distance_Sud;
                            }
                            if(distance_Sud==distance){
                                int a=(int)(round(Math.random(),0));
                                if(a==0){
                                   x_dest=x;
                                   y_dest=y+1;
                                   distance=distance_Sud; 
                                }
                            }  
                        }
                        else if(!(echantillon_tabous.getIndividu(i+1).getLabyrinthe().estMur(x-1, y))&&!(echantillon_tabous.getIndividu(i+1).getChemin().existeDeja(x-1, y))){
                        distance_Ouest=echantillon_tabous.getIndividu(i+1).distance(x-1, y);
                            if(distance_Ouest<distance){
                                x_dest=x-1;
                                y_dest=y;
                                distance=distance_Ouest;
                            }
                            if(distance_Ouest==distance){
                                int a=(int)(round(Math.random(),0));
                                if(a==0){
                                   x_dest=x-1;
                                   y_dest=y;
                                   distance=distance_Ouest; 
                                }
                            }   
                        }
                        else if(!(echantillon_tabous.getIndividu(i+1).getLabyrinthe().estMur(x, y-1))&&!(echantillon_tabous.getIndividu(i+1).getChemin().existeDeja(x, y-1))){
                        distance_Nord=echantillon_tabous.getIndividu(i+1).distance(x, y-1);
                            if(distance_Nord<distance){
                                x_dest=x;
                                y_dest=y-1;
                                distance=distance_Nord;
                            }
                            if(distance_Nord==distance){
                                int a=(int)(round(Math.random(),0));
                                if(a==0){
                                   x_dest=x;
                                   y_dest=y-1;
                                   distance=distance_Nord; 
                                }
                            }   
                        }
                
                    // Test bloque
                    if(distance==echantillon_tabous.getIndividu(i+1).distance(x, y)){
                        bloque=true;
                    }
                    
                     //Test solution
                    solution=echantillon_tabous.getIndividu(i+1).getLabyrinthe().estArrivee(x, y);
                     
                }
            }
               // int distance=id_escalade.distance(x, y);
            
        }
                
		
	



	
	public void RecuitSimule() {
		// TODO Auto-generated method stub
		
	}



	
	public void AlgorithmeGenetique() {
		// TODO Auto-generated method stub
		
	}



	
	public void AlgorithmeColonieFourmis() {
		// TODO Auto-generated method stub
		
	}



	
	public void LogiqueDeProposition() {
		// TODO Auto-generated method stub
		
	}



	
	public void LogiqueDePredicats() {
		// TODO Auto-generated method stub
		
	}
	
}
