package infolaby;


/**
 * Classe definissant les déplacements de l'individu
 * @author Olivier
 */
public class Chemin {
         /**
         * Deplacement par coordonnres 1er element : ligne ; 2nd element : colonne
         */
	private int[][] deplacement;
	/**
         * Nombre de cases explorees
         */
	private int nbcases;
        
        
	/**
         * Constructeur
         */
	public Chemin(){
		
		deplacement=new int[1][2];
		
		deplacement[0][0]=1;
		deplacement[0][1]=1;
		this.updateNbcase();	
	}
	
         /**
         * Mise a jour du nombre de cases explorees
         */
	public void updateNbcase(){
                /*int i=0;
		while(i<this.getDeplacement().length){
			i++;
		}
		this.nbcases=i;*/
            this.nbcases = this.getDeplacement().length;
	}
	
         /**
         * Test si l'on passe par un chemin deja emprunte
         * @param x 
         *      Abscisse
         * @param y
         *      Ordonnée
         * 
         * @return Vrai si l'individu est deja passe par cette case
         */
	public boolean existeDeja(int x, int y){
		boolean existe=false;
		for(int i=0; i<nbcases;i++){
			if(deplacement[i][0]==x && deplacement[i][1]==y){
				existe=true;
			}
		}
		return existe;
	}
	
        /**
         * Accesseurs
         * @return Nombre de cases
         */
	public int getNbcases(){
		return nbcases;
	}

	
	

        /**
         * Accesseurs
         * @return Tableau suite de coordonnees [x,y]
         */
	public int[][] getDeplacement() {
		return deplacement;
	}

        /**
         * Mutateur
         * @param deplacement 
         */
	public void setDeplacement(int deplacement[][]) {
		this.deplacement = deplacement;
                updateNbcase();
	}
        
         /**
         * Rajouter une coordonnee dans la suite de deplacement de l'individu
         * @param x 
         *      Abscisse
         * @param y
         *      Ordonnée
         */
        public void AddDeplacement(int x, int y){
            this.updateNbcase();
            int[][] dep=new int[this.nbcases+1][2];
            for(int i=0;i<deplacement.length;i++){
             dep[i][0]= deplacement[i][0];
             dep[i][1]= deplacement[i][1];
            }
            dep[this.nbcases][0]=x;
            dep[this.nbcases][1]=y;
            this.setDeplacement(dep);
            this.updateNbcase();
        }

       /**
         * Couper le deplacement d un individu
         * @param longueur 
         *      Longueur a couper
         */
        public void couper(int longueur){
            int nouveau[][] = new int [longueur][2];
            for (int i=0;i<longueur;i++){
                nouveau[i][0]=deplacement[i][0];
                nouveau[i][1]=deplacement[i][1];
            }
            deplacement=nouveau;
            this.updateNbcase();
        }
}
