package infolaby;

/**
 * Classe definissant une population de plusieurs individus
 * @author Olivier
 */
public class Echantillon {
	
        /**
         * Tableau des individus de l'echantillon
         */
	private Individu individus[];
        
	/**
         * Nombre d'individu dans l'echantillon
         */
	private int nbIndividu;
        
        /**
         * labyrinthe resolu sur l'échantillon
         */
        private boolean resolu=false;  
        
	/**
         * Constructeur
         * @param n
         *      Nombre d'individu
         */
	public Echantillon(int n){
		//System.out.println("Creation echantillon");
		individus= new Individu [n];
		for(int i=0;i<n;i++){
			individus[i] = new Individu();	
			}
		nbIndividu=n;
	}
	
	
	
	/**
         * Accesseur
         * @return Nombre d'individu
         */
	public int getNbIndividu(){
		return nbIndividu;
	}
	
        /**
         * Accesseur
         * @return L'individu demande
         */
	public Individu getIndividu(int n){
		return individus[n];
	}
	
        /**
         * Mutateur
         * @param individu
         *  L'individu a placer dans l'echantillon
         */
        public void setIndividu(Individu individu[]){
		this.individus=individu;
                nbIndividu=individu.length;
	}

        /**
         * Accesseur
         * @return Vrai si resolu
         */
        public boolean getresolu() {
		return resolu;
	}


        /**
         * Mutateur
         * @param type
         *      Ecrire qu'il y a une solution dans l'echantillon
         */
	public void setresolu(boolean type) {
		this.resolu=type;
	}

        
}
