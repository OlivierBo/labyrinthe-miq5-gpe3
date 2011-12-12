

public class Chemin {
	
	private int[][] deplacement; // Deplacement par coordonn�es 1er �l�ment : ligne ; 2nd �l�ment : colonne
	
	private int nbcases; // Nombre de cases explor�es
        
        
	
	// Constructeur
	public Chemin(){
		
		deplacement=new int[200][2];
		
		deplacement[0][0]=1;
		deplacement[0][1]=1;
		this.updateNbcase();	
	}
	
	public void updateNbcase(){
		int i=0;
		while(i<this.getDeplacement().length && (this.getDeplacement()[i][0]!=0 && this.getDeplacement()[i][1]!=0) ){
			i++;
		}
		this.nbcases=i;
	}
	
	public boolean existeDeja(int x, int y){
		boolean existe=false;
		for(int i=0; i<=nbcases-1;i++){
			if(deplacement[i][0]==x && deplacement[i][1]==y){
				existe=true;
			}
		}
		return existe;
	}
	
	// Accesseurs
	public int getNbcases(){
		return nbcases;
	}

	
	


	public int[][] getDeplacement() {
		return deplacement;
	}


	public void setDeplacement(int deplacement[][]) {
		this.deplacement = deplacement;
                updateNbcase();
	}
        
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

}
