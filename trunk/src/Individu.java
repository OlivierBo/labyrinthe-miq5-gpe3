

public class Individu{

	
	private Chemin chemin = new Chemin();	// Chemin de l'individu
	
        private Labyrinthe labyrinthe=new Labyrinthe(); //Matrice qui sera visualisée
        
        private boolean solution=false; // labyrinthe résolu sur l'individu

   
	
	//Constructeur
	public Individu(){ 
		System.out.println("creation indi");
                int[][]  deplacement={{1,1}};
                chemin.setDeplacement(deplacement);
	}
	
	
        public int distance(int x, int y){
            int distance=0;
            distance=Math.abs(x-13)+Math.abs(y-13); // en considérant que l'arrivée est en 13 , 13       
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
        
}
