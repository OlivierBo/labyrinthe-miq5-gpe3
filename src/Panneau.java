

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

	public class Panneau extends JPanel {
     
            //Autres variables
	private int c=32; // Taille cellule

            // Variable de l'individu à afficher
        private Individu individu_afficher=new Individu(); // id_DFS par défaut
	

	
	
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

}
