

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

	public class Panneau extends JPanel implements Resolution{

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
	private int c=32; // Taille cellule
        private int nbIndividu;
      	private int nbEchantillon;

   
        private int numeroIndividuEncours=1;
	private int numeroEchantillonEncours=1;
        
        
            // Variables de sorties d'algorithme pour comparaison
	private float moyenneTempsResolution;
	private float pourcentageReussite;
	private float critere1;
	private float critere2;
        
            // Variable de l'individu à afficher
        private Individu individu_afficher=id_DFS; // id_DFS par défaut
	

	
	
        /*
         Dessin du labyrinthe
         */
	public void paintComponent(Graphics g){
		//On décide d'une couleur de fond pour notre rectangle
		g.setColor(Color.white);
		//On dessine celui-ci afin qu'il prenne tout la surface
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
                // 2 boucles for imbriquées pour parcourir et dessiner le labyrinthe
		for (int x=0; x<individu_afficher.getLabyrinthe().n(); x++){
			for (int y=0; y<individu_afficher.getLabyrinthe().n(); y++) {
				if (individu_afficher.getLabyrinthe().estMur(x,y)) {
				    g.setColor(Color.gray);
				    g.fillRect(x*c, y*c, c, c);
				}
				if (individu_afficher.getLabyrinthe().estDepart(x,y)) {
				    g.setColor(Color.green);
				    g.fillRect(x*c, y*c, c, c);
				}
				if (individu_afficher.getLabyrinthe().estArrivee(x,y)) {
				    g.setColor(Color.red);
				    g.fillRect(x*c, y*c, c, c);
				 
				}
			}
		}
                // On prend le déplacement de l'individu et on le dessine
		int[][] dep=individu_afficher.getChemin().getDeplacement();
		individu_afficher.getChemin().updateNbcase();
		int ndep=individu_afficher.getChemin().getNbcases();
		int xd;
		int yd;
		g.setColor(Color.blue);
		for(int i=0; i< (ndep-1); i++){
			xd=dep[i][0];
			yd=dep[i][1];
			g.fillRect(xd*c+c/4, yd*c+c/4, c/2, c/2);
		}
		g.setColor(Color.green);
		xd=dep[ndep-1][0];
		yd=dep[ndep-1][1];
		g.fillRect(xd*c+c/4, yd*c+c/4, c/2, c/2);
		    
	}
        
       


        
      

        /*
         * Définition des méthodes de résolution
         */

	@Override
	public void BFS() {
         id_BFS.getChemin().AddDeplacement(2, 1);
         setIndividu_afficher(id_BFS);
         repaint();
         
	}



	@Override
	public void DFS() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void AlgorithmeGreedy() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void AlgorithmeA() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void Escalade() {
            // Initialisation
		id_escalade=new Individu();
                boolean solution=false;
                boolean bloque=false;
                int x=1;
                int y=1;
                int distance=id_escalade.distance(x, y);
               
            // Boucle pour le déplacement    
                while((solution==false)&&(bloque==false)){
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
                    
                    
                }
                
            // Affichage du résultat
                setIndividu_afficher(id_escalade);
                repaint();
                System.out.println(" bloqué = "+ bloque + " ; solution = "+ solution);
                
		
	}



	@Override
	public void RechercheAvecTabous() {
                //Initialisation
		echantillon_tabous=new Echantillon(nbIndividu);
                boolean solution=false;
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
                
		
	



	@Override
	public void RecuitSimule() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void AlgorithmeGenetique() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void AlgorithmeColonieFourmis() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void LogiqueDeProposition() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void LogiqueDePredicats() {
		// TODO Auto-generated method stub
		
	}
	
        
         /*
         * Accesseurs
         */
        
         public void setIndividu_afficher(Individu ind) {
		this.individu_afficher = ind;
	}
        
        public Individu getIndividu_afficher(){
            return this.individu_afficher;
        }
        
        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public Echantillon getEchantillon_recuit() {
            return echantillon_recuit;
        }

        public void setEchantillon_recuit(Echantillon echantillon_recuit) {
            this.echantillon_recuit = echantillon_recuit;
        }

        public Individu getId_A() {
            return id_A;
        }

        public void setId_A(Individu id_A) {
            this.id_A = id_A;
        }

        public Individu getId_BFS() {
            return id_BFS;
        }

        public void setId_BFS(Individu id_BFS) {
            this.id_BFS = id_BFS;
        }

        public Individu getId_DFS() {
            return id_DFS;
        }

        public void setId_DFS(Individu id_DFS) {
            this.id_DFS = id_DFS;
        }

        public Individu getId_escalade() {
            return id_escalade;
        }

        public void setId_escalade(Individu id_escalade) {
            this.id_escalade = id_escalade;
        }

        public Individu getId_greedy() {
            return id_greedy;
        }

        public void setId_greedy(Individu id_greedy) {
            this.id_greedy = id_greedy;
        }

        public Echantillon getId_tabous() {
            return echantillon_tabous;
        }

        public void setId_tabous(Echantillon e_tabous) {
            this.echantillon_tabous = e_tabous;
        }

        public N_echantillon getN_echantillon_fourmis() {
            return n_echantillon_fourmis;
        }

        public void setN_echantillon_fourmis(N_echantillon n_echantillon_fourmis) {
            this.n_echantillon_fourmis = n_echantillon_fourmis;
        }

        public N_echantillon getN_echantillon_genetique() {
            return n_echantillon_genetique;
        }

        public void setN_echantillon_genetique(N_echantillon n_echantillon_genetique) {
            this.n_echantillon_genetique = n_echantillon_genetique;
        }

        public int getNbEchantillon() {
            return nbEchantillon;
        }

        public void setNbEchantillon(int nbEchantillon) {
            this.nbEchantillon = nbEchantillon;
        }

        public int getNbIndividu() {
            return nbIndividu;
        }

        public void setNbIndividu(int nbIndividu) {
            this.nbIndividu = nbIndividu;
        }

        public int getNumeroEchantillonEncours() {
            return numeroEchantillonEncours;
        }

        public void setNumeroEchantillonEncours(int numeroEchantillonEncours) {
            this.numeroEchantillonEncours = numeroEchantillonEncours;
        }

        public int getNumeroIndividuEncours() {
            return numeroIndividuEncours;
        }

        public void setNumeroIndividuEncours(int numeroIndividuEncours) {
            this.numeroIndividuEncours = numeroIndividuEncours;
        }


	public float getMoyenneTempsResolution() {
		return moyenneTempsResolution;
	}


	public void setMoyenneTempsResolution(float moyenneTempsResolution) {
		this.moyenneTempsResolution = moyenneTempsResolution;
	}


	public float getPourcentageReussite() {
		return pourcentageReussite;
	}


	public void setPourcentageReussite(float pourcentageReussite) {
		this.pourcentageReussite = pourcentageReussite;
	}


	public float getCritere1() {
		return critere1;
	}


	public void setCritere1(float critere1) {
		this.critere1 = critere1;
	}


	public float getCritere2() {
		return critere2;
	}


	public void setCritere2(float critere2) {
		this.critere2 = critere2;
	}

        public double round(double f, int i){
            return (double)((int)(f*Math.pow(10,i)+0.5))/Math.pow(10, i);       
                    }

	
}
