
public class Individu{

	public char[][] map; //Matrice qui sera visualisée
	
	public Chemin chemin = new Chemin();	// Chemin de l'individu
	
	public Labyrinthe labyrinthe=new Labyrinthe();
	
	//Constructeur
	public Individu(){
		System.out.println("creation indi");
		this.map=labyrinthe.murs;
                int[][]  deplacement={{1,1}};
                chemin.setDeplacement(deplacement);
	}
	
	// Accesseurs
	public Chemin getChemin(){
		return chemin;
		
	}

	
}
