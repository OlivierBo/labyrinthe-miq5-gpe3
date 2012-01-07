package infolaby;

/**
 * Individu parcourant le labyrinthe
 */
public class Individu{

	/**
         * Chemin de l'individu
         */
	private Chemin chemin = new Chemin();
	
        /**
         * Matrice du labyrinthe 
         */
        private Labyrinthe labyrinthe=new Labyrinthe(); 
        
        /**
         * vrai si Individu solution 
         */
        private boolean solution=false; 
        
        /**
         * Coordonnées de la case arrivée en x
         */
        private int x_dest=13;
        /**
         * Coordonnées de la case arrivée en y
         */
        private int y_dest=13;


        /**
         * Constructeur
         */
	public Individu(){ 
		//System.out.println("creation indi");
            //Positionnement de départ
          
            for (int i=0;i<this.getLabyrinthe().n();i++) {
                for (int j=0;j<this.getLabyrinthe().n();j++) {
                    if (this.getLabyrinthe().estDepart(i, j)) {
                     int[][]  deplacement={{i,j}};
                     chemin.setDeplacement(deplacement);
                    }
                }
            }
            this.caseArrivee();
                
	}
	
	/**
         * Renvoi la distance entre la position actuelle et la position d'arrivee
         * @param x
         *      abscisse
         * @param y 
         *      Ordonnee
         * @return distance en nombre de cases
         */
        public int distance(int x, int y){
            int distance=0;
            caseArrivee();
            distance=Math.abs(x-x_dest)+Math.abs(y-y_dest); // en considérant que l'arrivée est en 13 , 13       
            return distance;
        }

	/**
         * Accesseur
         * @return chemin
         */
	public Chemin getChemin(){
		return chemin;
	}
        
        /**
         * Accesseur
         * @return Labyrinthe
         */
        public Labyrinthe getLabyrinthe(){
            return labyrinthe;
        }
        
        /**
         * Accesseur
         * @return Vrai si l individu a trouve une solution
         */
        public boolean isSolution() {
        return solution;
         }

        /**
         * Mutateur
         * 
         * @param solution 
         *      Definir si la solution est trouvee
         */
        public void setSolution(boolean solution) {
            this.solution = solution;
        }
        
         /**
         * Enregistre les coordonnees de la case d arrive
         */
        public void caseArrivee(){
            // Recherche case arrivée
            for (int i=0;i<this.getLabyrinthe().n();i++) {
                for (int j=0;j<this.getLabyrinthe().n();j++) {
                    if (this.getLabyrinthe().estArrivee(i, j)) {
                            x_dest=i;
                            y_dest=j;
                    }
                }
            }
        }
        
}
