package infolaby;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Classe gérant l'affichage du labyrinthe
 * @author Olivier
 */
public class Panneau extends JPanel {
     
        /**
         * Taille cellule
         */
	private int c=32;
        
        /**
         * Variable de l'individu à afficher
         */
        private Individu individu_afficher=new Individu();
	
        // Creation d'un tableau de pheromones
        private double[][] coeffPheromone = new double[15][15];

        public void init_matrice() {
            for (int i = 0; i <= 14; i++) {

                for (int j = 0; j <= 14; j++) {
                    coeffPheromone[i][j] = 0.0;
                }
            }
        }

        public double get_pheromone(int x, int y) {
            return coeffPheromone[y][x];
        }

        public void set_pheromone(int x, int y, double coeff){
            coeffPheromone[y][x]=coeff;
        }

	
        /**
         * Dessin du labyrinthe
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
                                //if(coeffPheromone[y][x]!=0.0){
                                    Color z=new Color((float)0.0,(float)0.0,(float)1.0,(float)coeffPheromone[y][x]);
                                    g.setColor(z);
				    g.fillRect(x*c, y*c, c, c); 
                               //}
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
    
        /**
         * Mutateur
         * @param ind
         *      L individu a afficher
         */
         public void setIndividu_afficher(Individu ind) {
		this.individu_afficher = ind;
	}
         
         /**
         * Accesseurs
         * @return L Individu qui est affiche
         */
        public Individu getIndividu_afficher(){
            return this.individu_afficher;
        }
        

}
