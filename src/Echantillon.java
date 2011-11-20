
public class Echantillon {
	
	private Individu individus[]; // Tableau des individus de l'echantillon
	
	private int nbIndividu; // Nombre d'individu dans l'echantillon
	
	private int tempsResolution; //Image du temps de résolution

        private boolean resolu=false; // labyrinthe résolu sur l'échantillon
        
	//Constructeur
	public Echantillon(int n){
		System.out.println("Creation echantillon");
		individus= new Individu [n];
		for(int i=0;i<n;i++){
			individus[i] = new Individu();	
			}
		nbIndividu=n;
		tempsResolution=0;
	}
	
	
	
	//Accesseurs
	public int getNbIndividu(){
		return nbIndividu;
	}
	
	public Individu getIndividu(int n){
		return individus[n-1];
	}
	
	public void setNbIndividu(int n){
		this.nbIndividu=n;
	}
        
        public void setIndividu(Individu individu[]){
		this.individus=individu;
                nbIndividu=individu.length;
	}



	public int getTempsResolution() {
		return tempsResolution;
	}



	public void setTempsResolution(int tempsResolution) {
		this.tempsResolution = tempsResolution;
	}

        public boolean getresolu() {
		return resolu;
	}



	public void setresolu(boolean type) {
		this.resolu=type;
	}

        
}
