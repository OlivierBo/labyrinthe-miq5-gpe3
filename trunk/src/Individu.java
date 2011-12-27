

public class Individu{

	
	private Chemin chemin = new Chemin();	// Chemin de l'individu
	
        private Labyrinthe labyrinthe=new Labyrinthe(); //Matrice qui sera visualisée
        
        private boolean solution=false; // labyrinthe résolu sur l'individu
        
        private int x_dest=13; // Coordonnées de la case arrivée
        private int y_dest=13;

   
	
	//Constructeur
	public Individu(){ 
		System.out.println("creation indi");
            //Positionnement de départ
          
            for (int i=0;i<this.getLabyrinthe().n();i++) {
                for (int j=0;j<this.getLabyrinthe().n();j++) {
                    if (this.getLabyrinthe().estDepart(i, j)) {
                     int[][]  deplacement={{i,j}};
                     chemin.setDeplacement(deplacement);
                    }
                }
            }
                
	}
	
	
        public int distance(int x, int y){
            int distance=0;
            caseArrivee();
            distance=Math.abs(x-x_dest)+Math.abs(y-y_dest); // en considérant que l'arrivée est en 13 , 13       
            return distance;
        }

	// Accesseurs
	public Chemin getChemin(){
		return chemin;
	}
        
        public Labyrinthe getLabyrinthe(){
            return labyrinthe;
        }
        
         public boolean isSolution() {
        return solution;
         }

        public void setSolution(boolean solution) {
            this.solution = solution;
        }
        
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
