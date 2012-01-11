package infolaby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe contenant la totalite des algorithmes de resolution ainsi que la gestion de l'interface
 * @author Olivier
 */
public class Fenetre extends JFrame {
    
       
        /**
         * Panneau affichage labyrinthe
         */
	private Panneau pan = new Panneau();
        
        /**
         * JComboBox : listes déroulantes
         */
	private JComboBox<String> comboBoxAlpha = new JComboBox<>();
	private JComboBox<String> comboBoxNbI = new JComboBox<>();
	private JComboBox<String> comboBoxTypeResolution = new JComboBox<>();
        
        
        /**
         * JButton : Bouton
         */
	private JButton boutonLancer = new JButton("Lancer");   
	private JButton boutonArreter = new JButton("Arreter");
	private JButton boutonContinuer = new JButton("Continuer");
        
        
        /**
         * JPanel : Layout pour placer les boutons et informations
         */
	private JPanel container = new JPanel();    
	private JPanel south = new JPanel();
	private JPanel north2 = new JPanel();
	private JPanel north = new JPanel();
        private JPanel west = new JPanel();
        private JPanel west2 = new JPanel();
        
        /**
         * Jlabel : Ecriture de chaîne de caractères sur l'interface
         */
        private JLabel label1 = new JLabel(""); 
	private JLabel labelE = new JLabel("0");
	private JLabel labelI = new JLabel("0");
	private JLabel labelD = new JLabel("0");
	private JLabel labelC = new JLabel("0");
	private JLabel labelM = new JLabel("0");
	private JLabel labelP = new JLabel("0");
	private JLabel labelC1 = new JLabel("0.0");
	private JLabel labelC2 = new JLabel("0.0"); 

        /**
         * Boolean en cours d'animation ? selon l'etat, met en pause la résolution
         */
        private boolean animated = true;
        
        /**
         * Solution trouvée par l'algorithme
         */
        private boolean solution=false;
        
        /**
         * Thread cree lors d une execution d un algorithme
         */
	private Thread t;
	
        /**
         * Variable algorithme DFS
         */
        private Individu id_DFS=new Individu();
         /**
         * Variable algorithme BFS
         */
        private Individu id_BFS=new Individu();
         /**
         * Variable algorithme greedy
         */
        private Individu id_greedy=new Individu();
         /**
         * Variable algorithme A
         */
        private Individu id_A=new Individu();
         /**
         * Variable algorithme escalade
         */
        private Individu id_escalade=new Individu();
        /**
         * Variable algorithme tabous
         */
        private Individu id_tabous=new Individu();
        /**
         * Variable algorithme recuit
         */
        private Echantillon echantillon_recuit=new Echantillon(1);
        /**
         * Variable algorithme genetique
         */
        private Echantillon_genetique echantillon_genetique;
        /**
         * Variable algorithme fourmis
         */
	private N_echantillon n_echantillon_fourmis=new N_echantillon(1,1);
        
        /**
         * Nombre d individus a traiter
         */
        private int nbIndividu=1;
        /**
         * Nombre d echantillons a traiter
         */
      	private int nbEchantillon=1;
        /**
         * Iteration d un algorithme de resolution
         */
        private int iteration=0;
        /**
         * numero de l individu en cours
         */
        private int numeroIndividuEncours=1;
         /**
         * numero de l echantillon en cours
         */
	private int numeroEchantillonEncours=1;
         /**
         * Pondération de la fonction score
         */
	private double alpha=1.0;
        
      
        /**
         * Variables de sorties d'algorithme pour comparaison
         */
	private float moyenneTempsResolution;
        /**
         * Variables de sorties d'algorithme pour comparaison
         */
	private float pourcentageReussite;
        /**
         * Variables de sorties d'algorithme pour comparaison
         */
	private float critere1;
        /**
         * Variables de sorties d'algorithme pour comparaison
         */
	private float critere2;
        
        
        
        /**
         * Constructeur
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
		String[] listeA={"0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1"};
		String[] listeI={"20","40","60","80","100","120","140","160","180","200"};
		String[] listeResol ={"DFS","BFS","Algorithme greedy", "Algorithme A*", "Escalade", "Recherche avec tabous", "Recuit simulé", "Algorithme génétiques","Algorithme de colonies de fourmis", "Logique de prpositions","Logique de prédicats"};
		
		comboBoxAlpha = new JComboBox<>(listeA);
		comboBoxNbI = new JComboBox<>(listeI);
		comboBoxTypeResolution = new JComboBox<>(listeResol);
                
                
                
                
                //Définition de l'écoute des commandes
		comboBoxAlpha.addActionListener(new ItemActionAlpha());
		comboBoxAlpha.setPreferredSize(new Dimension(60,30));
		comboBoxAlpha.setForeground (Color.blue);
		
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
		north.add(new JLabel("Alpha"));
		north.add(new JLabel("Nombre d'individus"));
		north.add(new JLabel("Type de résolution"));
		north.add(comboBoxAlpha);
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
        * Renvoie le panneau
        * @return Panneau
        */
	public Panneau getPan() {
		return pan;
	}


	/**
	* Classe interne implémentant l'interface de l'appui du bouton selection NbE
	*/
	class ItemActionAlpha implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			
		label1.setText("action sur "+ comboBoxAlpha.getSelectedItem() +" dans Alpha") ;
		
		}
		}
	/**
	* Classe interne implémentant l'interface de l'appui du bouton selection NbI
	*/
	class ItemActionNbI implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			
		label1.setText("action sur "+ comboBoxNbI.getSelectedItem() +" dans nombre individu " ) ;
		}
		}
	/**
	* Classe interne implémentant l'interface de l'appui du bouton selection Resolution
	*/
	class ItemActionResol implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			
		label1.setText("action sur "+ comboBoxTypeResolution.getSelectedItem() +" dans type de r�solution "+comboBoxTypeResolution.getSelectedIndex() )  ;
		}
		}
	
        /**
	* Classe interne implémentant l'interface de l'appui du bouton Lancer
	*/
	class BoutonLancerListener implements ActionListener{
		public void actionPerformed (ActionEvent arg0) {
                        animated=true;
			boutonLancer.setEnabled (false);
			boutonArreter.setEnabled (true);
			comboBoxAlpha.setEnabled (false);
			comboBoxNbI.setEnabled (false);
			comboBoxTypeResolution.setEnabled (false);
			
                        //Selection nombre d'échantillon
			switch(comboBoxAlpha.getSelectedIndex()){
			case 0:
				alpha=0.0;
				break;
			case 1:
				alpha=0.1;
				break;
			case 2:
				alpha=0.2;
				break;
			case 3:
				alpha=0.3;
				break;
			case 4:
				alpha=0.4;
				break;
			case 5:
				alpha=0.5;
				break;
			case 6:
				alpha=0.6;
				break;
			case 7:
				alpha=0.7;
				break;
			case 8:
				alpha=0.8;
				break;
			case 9:
				alpha=0.9;
				break;
                        case 10:
				alpha=1.0;
				break;
			}
			
                        //Selection nombre d'individus
			switch(comboBoxNbI.getSelectedIndex()){
			case 0:
				nbIndividu=20;
				break;
			case 1:
				nbIndividu=40;
				break;
			case 2:
				nbIndividu=60;
				break;
			case 3:
				nbIndividu=80;
				break;
			case 4:
				nbIndividu=100;
				break;
			case 5:
				nbIndividu=120;
				break;
			case 6:
				nbIndividu=140;
				break;
			case 7:
				nbIndividu=160;
				break;
			case 8:
				nbIndividu=180;
				break;
			case 9:
				nbIndividu=200;
				break;
			}
                        	

                    // Selection type de résolution
                    switch(comboBoxTypeResolution.getSelectedIndex()){
                    case 0:
                            nbIndividu=1;
                            t=new Thread(new DFS_Runnable());
                            break;
                    case 1:
                            nbIndividu=1;
                            t=new Thread(new BFS_Runnable());
                            break;

                    case 2:
                            nbIndividu=1;
                            t=new Thread(new Greedy_Runnable());
                            break;

                    case 3:
                            nbIndividu=1;
                            t=new Thread(new A_Runnable());
                            break;

                    case 4:
                            nbIndividu=1;
                            t=new Thread(new Escalade_Runnable());
                            break;

                    case 5:
                            nbIndividu=1;
                            t=new Thread(new Tabous_Runnable());
                            break;

                    case 6:
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
                    System.out.println("Parametres d'execution :   Algo " +comboBoxTypeResolution.getSelectedItem()+ " ; NbI =  " + nbIndividu + " ; Alpha = " + alpha);
                    t.start();
		}
	}
	
        
        /**
	* Classe interne implémentant l'interface de l'appui du bouton arreter
	*/
	class BoutonArreterListener implements ActionListener{
		public void actionPerformed (ActionEvent arg0) {
			boutonLancer.setEnabled (true);
			boutonArreter.setEnabled (false);
			comboBoxAlpha.setEnabled (true);
			comboBoxNbI.setEnabled (true);
			comboBoxTypeResolution.setEnabled (true);
                        boutonContinuer.setEnabled(false);
                        solution=true;
		label1.setText("Vous avez cliqué sur le bouton Arreter");
		
		}
	}
	
        /**
	* Classe interne implémentant l'interface de l'appui du bouton continuer
	*/
	class BoutonContinuerListener implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) {
                        animated=true;
			boutonContinuer.setEnabled (false);
		}
	}
        
        
        
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class BFS_Runnable implements Runnable{
        @Override
            public void run() {
            BFS();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class DFS_Runnable implements Runnable{
        @Override
            public void run() {
            DFS();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Greedy_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeGreedy();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class A_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeA();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Escalade_Runnable implements Runnable{
        @Override
            public void run() {
            Escalade();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Tabous_Runnable implements Runnable{
        @Override
            public void run() {
            RechercheAvecTabous();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Recuit_Runnable implements Runnable{
        @Override
            public void run() {
            RecuitSimule();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Genetique_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeGenetique();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Fourmis_Runnable implements Runnable{
        @Override
            public void run() {
            AlgorithmeColonieFourmis();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Proposition_Runnable implements Runnable{
        @Override
            public void run() {
            LogiqueDeProposition();
            }
            }
        /**
	* Classe interne implémentant l'interface de lancement de l'algorithme souhaite
	*/
        class Predicat_Runnable implements Runnable{
        @Override
            public void run() {
            LogiqueDePredicats();
            }
            }
        
        
        /**
         * Arrondi d une valeur float
         * @param f
         *  float a arrondir
         * @param i
         *  a mettre a zero
         * @return float arrondi
         */
         public double round(double f, int i){
            return (double)((int)(f*Math.pow(10,i)+0.5))/Math.pow(10, i);       
                    }
        /**
         * Ecrire l'iteration en pause
         */
         public void sendIteration(){
             label1.setText("iteration =  "+iteration);
         }
        

	/**
         * Resolution BFS
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
            comboBoxAlpha.setEnabled (true);
            comboBoxNbI.setEnabled (true);
            comboBoxTypeResolution.setEnabled (true);
	}



	/**
         * Resolution DFS
         */
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
            comboBoxAlpha.setEnabled (true);
            comboBoxNbI.setEnabled (true);
            comboBoxTypeResolution.setEnabled (true);
		
	}



	/**
         * Resolution Greedy
         */
	public void AlgorithmeGreedy() {
		// TODO Auto-generated method stub
		
	}



	/**
         * Resolution A
         */
	public void AlgorithmeA() {
		// TODO Auto-generated method stub
		
	}



	/**
         * Resolution escalade
         */
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
		comboBoxAlpha.setEnabled (true);
                comboBoxNbI.setEnabled (true);
		comboBoxTypeResolution.setEnabled (true);
	}



	/**
         * Resolution tabous
         */
	public void RechercheAvecTabous() {
                //Initialisation
		id_tabous=new Individu();
                boolean solution=false;
                boolean bloque=false;
                int x=1;
                int y=1;
                int x_dest=x;
                int y_dest=y;
                int distance_Est;
                int distance_Sud;
                int distance_Ouest;
                int distance_Nord;
                int distance=100;
                iteration=0;
                animated=true;
                
                // Boucle d'execution de l'algorithme pour chaque individu
            
                // Boucle de résolution
                while((solution==false)&&(bloque==false)){
                    if(animated){
                    x_dest=x;
                    y_dest=y;
                    distance=100;
                    if(!(id_tabous.getLabyrinthe().estMur(x+1, y))&&!(id_tabous.getChemin().existeDeja(x+1, y))){
                        distance_Est=id_tabous.distance(x+1, y);
                        if(distance_Est<distance){
                        x_dest=x+1;
                        y_dest=y;
                        distance=distance_Est;
                        }
                    }
                    else if(!(id_tabous.getLabyrinthe().estMur(x, y+1))&&!(id_tabous.getChemin().existeDeja(x, y+1))){
                        distance_Sud=id_tabous.distance(x, y+1);
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
                        else if(!(id_tabous.getLabyrinthe().estMur(x-1, y))&&!(id_tabous.getChemin().existeDeja(x-1, y))){
                        distance_Ouest=id_tabous.distance(x-1, y);
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
                        else if(!(id_tabous.getLabyrinthe().estMur(x, y-1))&&!(id_tabous.getChemin().existeDeja(x, y-1))){
                        distance_Nord=id_tabous.distance(x, y-1);
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
                    System.out.println("distance " + distance);
                    // Test bloque si la distance n a pas change
                    if(distance==id_tabous.distance(x, y)){
                        bloque=true;
                    }
                    x=x_dest;
                    y=y_dest;
                    id_tabous.getChemin().AddDeplacement(x, y);
                    
                     //Test solution
                    solution=id_tabous.getLabyrinthe().estArrivee(x, y);
                    if(!solution){
                            iteration++;
                        }
                    }
                    if(iteration%10==0){
                    if(animated){
                     animated=false;
                     sendIteration();
                     boutonContinuer.setEnabled (true);
                     getPan().setIndividu_afficher(id_tabous);
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
                
            
            getPan().setIndividu_afficher(id_tabous);
            repaint();
            System.out.println(" bloque = "+ bloque + " ; solution = "+ solution);
        }
                
		
	



	/**
         * Resolution recuit simule
         */
	public void RecuitSimule() {
		// TODO Auto-generated method stub
		
	}



	/**
         * Resolution genetique
         */
	public void AlgorithmeGenetique() {
            
            /*******************************************************************
            *********************** DEFINITION DES VARIABLES *******************
            *******************************************************************/
            
            /******** Paramètres ******** 
            - Nombre individu - divisible par 4 (nbIndividu)
            - Pondération de la fonction de score (alpha)
            - Méthode de sélection (selection)
            - Probabilité de mutation (prob_mutation)
            - Nombre max d'itérations (max_iterations)
            ********* Algorithme ********
            - Nombre d'itérations déjà effectuées (nb_iterations)
            - Fin de l'algorithme (passage)
            - Nombre d'individus virtuels dans le cas de la probabilité proportionnelle = selection 2 (etendue)
            - Individu_genetique temporaire pour le tri à bulle ou des changements de place (individu_temp)
            - Tableau d'individus_génétiques = nouvel echantillon_genetique pour la sélection, combinaison (echantillon_genetique_nouveau)
            - Entier pour les probabilités (proba,proba2)
            */
            //double alpha = 1; // 0 : nb-cases | 1 : distance
            int selection = 2;
            double prob_mutation= 0.01;
            int max_iterations = 300;
            
            int nb_iteration=0;
            boolean passage = false;
            int etendue;
            Individu_genetique individu_temp;
            Individu_genetique echantillon_genetique_nouveau[] = new Individu_genetique[nbIndividu/2];
            int proba; int proba2;
            
            /*******************************************************************
            ************************** BOUCLES DE CALCUL ***********************
            *******************************************************************/
            
            // 1) Initialisation : on engendre aléatoirement une population Po comprenant N individus
            echantillon_genetique = new Echantillon_genetique(nbIndividu,alpha,selection);
            // 2) Boucles d'itérations et traitement des données jusqu'à convergence ou arrêt
            do{
                // 2.1) Sélection : on sélectionne un sous-ensemble de la population de taille p tel que p = N/2
                switch(selection){
                    
                    case 1 : // Rang = tri sur la moitié de la pop (on ne retient que la moitié supérieur)
                        for (int i=0;i<nbIndividu/2;i++){
                            individu_temp = echantillon_genetique.getIndividu(i);
                            for(int j=i+1;j<nbIndividu;j++){
                                if(echantillon_genetique.getIndividu(j).score > echantillon_genetique.getIndividu(i).score){
                                    individu_temp = echantillon_genetique.getIndividu(j);
                                    echantillon_genetique.setIndividu(j,echantillon_genetique.getIndividu(i));
                                    echantillon_genetique.setIndividu(i,individu_temp);
                                }
                            }
                            echantillon_genetique_nouveau[i]=individu_temp;
                        }
                        break;
                    
                    case 2 : // Tournoi = tri sur la pop. + proba pondérée + meilleur des deux         
                        // Tri par odre de score
                        for (int i=0;i<nbIndividu;i++){
                            for(int j=i+1;j<nbIndividu;j++){
                                if(echantillon_genetique.getIndividu(j).score > echantillon_genetique.getIndividu(i).score){
                                    individu_temp = echantillon_genetique.getIndividu(j);
                                    echantillon_genetique.setIndividu(j,echantillon_genetique.getIndividu(i));
                                    echantillon_genetique.setIndividu(i,individu_temp);
                                }
                            }
                        }
                        
                        // Sélection (meilleur des deux probas pondérées)
                        for(int i=0;i<nbIndividu/2;i++){
                            int candidats = nbIndividu-i;
                            etendue = ((candidats)*(candidats+1))/2;
                            proba = (int) Math.ceil(Math.random()*etendue);
                            proba2 = (int) Math.ceil(Math.random()*etendue);
                            
                            if(proba2<proba){
                                proba = proba2;
                            }
                            
                            int cellule = 0;
                            double total = 0;
                        
                            do{
                                total = total + (candidats-cellule);
                                cellule++;
                            }while(total<proba);
                            
                            individu_temp = echantillon_genetique.getIndividu(cellule-1);
                            echantillon_genetique_nouveau[i]=individu_temp;
                            
                            // Décalage pour ne pas reprendre deux fois le même
                            for(int j=cellule-1;j<candidats-1;j++){
                                echantillon_genetique.setIndividu(j, echantillon_genetique.getIndividu(j+1));
                            }
                            
                            echantillon_genetique.setIndividu(candidats-1,individu_temp);
                        }
                        break;    
                                          
                    case 3 : // Probabilité        
                        for(int i=0;i<nbIndividu/2;i++){
                            int cellule = (int)(Math.random()*(nbIndividu-i));
                            echantillon_genetique_nouveau[i]=echantillon_genetique.getIndividu(cellule);
                            // décalage pour ne pas reprendre deux fois le même
                            if(cellule!=nbIndividu-i-1){
                                individu_temp = echantillon_genetique.getIndividu(cellule);
                                echantillon_genetique.setIndividu(cellule,echantillon_genetique.getIndividu(nbIndividu-i-1));
                                echantillon_genetique.setIndividu(nbIndividu-i-1,individu_temp);
                            }
                        }
                        break;
                        
                }
                
                /* 2.2) Croisement : parmi ces p individus, on constitue p/2 couples qui seront alors croisés au point de collision le plus petit
                *  Individu : 
                *  - - - -( - - - - - -)
                *  | |      |
                *  0 1      croisement
                */
                for (int i=nbIndividu/2;i<nbIndividu;i+=2){
                    
                    proba = (int)(Math.random()*(nbIndividu-i));
                    proba2 = (int)(Math.random()*(nbIndividu-i));
                    
                    while(proba==proba2){
                        proba2 = (int)(Math.random()*(nbIndividu-i));
                    }
                    
                    // Repérage des deux individus parents
                    Individu_genetique individu1 = echantillon_genetique_nouveau[proba];
                    Individu_genetique individu2 = echantillon_genetique_nouveau[proba2];
                    int croisement = Math.max(individu1.nb_cases,individu2.nb_cases);
                    
                    // décalage pour ne pas reprendre deux fois le même
                    if(proba!=nbIndividu-i-1){
                        individu_temp = echantillon_genetique_nouveau[proba];
                        echantillon_genetique_nouveau[proba] = echantillon_genetique_nouveau[nbIndividu-i-1];
                        echantillon_genetique_nouveau[nbIndividu-i-1] = individu_temp;
                    }
                    if(proba2!=nbIndividu-i-2){
                        individu_temp = echantillon_genetique_nouveau[proba2];
                        echantillon_genetique_nouveau[proba2] = echantillon_genetique_nouveau[nbIndividu-i-2];
                        echantillon_genetique_nouveau[nbIndividu-i-2] = individu_temp;
                    }
                    
                    // Création des enfants 
                    // Croisement des directions
                    for (int j=(croisement-1);j<individu1.longueur;j++){
                        int temp = individu1.individu[j];
                        individu1.individu[j]=individu2.individu[j];
                        individu2.individu[j]=temp;
                    }
                    // 2.3) Mutation //(int)(Math.random()*(individu1.longueur-croisement))+croisement
                    if(new Random().nextInt((int)(1/prob_mutation))==0){
                        individu1.individu[croisement-1]=(int)(Math.random()*4);
                    }
                    if(new Random().nextInt((int)(1/prob_mutation))==0){
                        individu2.individu[croisement-1]=(int)(Math.random()*4);
                    }
 
                    // Traduction des nouveaux enfants en termes de coordonnées + evaluation
                    individu1.chemin.couper(croisement);
                    individu2.chemin.couper(croisement);
                    individu1.traduction_combinaison(croisement-1);
                    individu2.traduction_combinaison(croisement-1);
                    individu1.collision();
                    individu2.collision();
                    individu1.existence();
                    individu2.existence();
                    if(selection!=3){
                        individu1.fonction_score();
                        individu2.fonction_score();
                    }     
                    
                    // Enregistrement de la nouvelle population
                    echantillon_genetique.setIndividu(i,individu1);
                    echantillon_genetique.setIndividu(i+1,individu2);
                }
                // Complément de la population avec les parents
                for (int i=0;i<nbIndividu/2;i++){
                    echantillon_genetique.setIndividu(i,echantillon_genetique_nouveau[i]);
                }
                nb_iteration++;
                System.out.println("iteration : "+nb_iteration);

                // 2.4) Vérification des conditions de fin
                if(nb_iteration==max_iterations){System.out.println("it max");
                    passage = echantillon_genetique.afficher_meilleurIndividu(selection);
                    passage=true;
                }else{
                    passage = echantillon_genetique.afficher_meilleurIndividu(selection);
                }
                
            }while(passage!=true);
            Individu_genetique ind=echantillon_genetique.afficher_meilleurIndividuDansEchantillon(selection);
            ind.collision();
            ind.existence();
            int nbcases=ind.getNbcases();
            ind.getChemin().couper(nbcases);
            getPan().getIndividu_afficher().getChemin().setDeplacement(ind.getChemin().getDeplacement());
            repaint();
        }
		
	



	/**
         * Resolution colonie de fourmis
         */
	public void AlgorithmeColonieFourmis() {
		// TODO Auto-generated method stub
		
	}



	/**
         * Resolution logique de proposition
         */
	public void LogiqueDeProposition() {
		LabyrintheP p = Labyrinthe.laby4();
        //System.out.println(p);
        //System.out.println(p.formatClauses());
        int[] sol = p.premiereSol();
        int nb = 0;
        int[] sol1 = new int[0];
        for(int i=0;i<sol.length;i++){
            if(sol[i]>0){
                nb++;
                int[] soltemp=new int[nb];
                soltemp[nb-1]=sol[i];
                
                for(int k=0;k<sol1.length;k++){
                    soltemp[k]=sol1[k];
                }  
             sol1=new int[nb]; 
                for(int k=0;k<sol1.length;k++){
                    sol1[k]=soltemp[k];
                }  
            }
        }


//        for(int i=0;i<sol.length;i++){
//            System.out.println(sol[i]);
//        }

        for(int i=0;i<sol1.length;i++){
            System.out.println(sol1[i] + "  decomposition : "+p.formatVar(sol1[i]));
        }
        //System.out.println(p.formatSol(sol));

        //System.out.println(p.affichePremiedecodeSolutions(3));
		
	}



	/**
         * Resolution logique de predicats
         */
	public void LogiqueDePredicats() {
		// TODO Auto-generated method stub
		
	}
	
}
