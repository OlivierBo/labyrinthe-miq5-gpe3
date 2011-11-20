
public class Individu{

	
	public Chemin chemin = new Chemin();	// Chemin de l'individu
	
        public Labyrinthe labyrinthe=new Labyrinthe(); //Matrice qui sera visualisée
	
	//Constructeur
	public Individu(){ 
		System.out.println("creation indi");
                int[][]  deplacement={{1,1}};
                chemin.setDeplacement(deplacement);
	}
	
	// Accesseurs
	public Chemin getChemin(){
		return chemin;
		
	}

	
}
