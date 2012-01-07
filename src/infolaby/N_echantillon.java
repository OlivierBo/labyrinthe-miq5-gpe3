package infolaby;

/**
* Ensemble de population d'individus, ensemble d'echantillons
*/
public class N_echantillon {
        /**
         * Tableau des echantillons
         */
	private Echantillon n_echantillon[];
	/**
         * Nombre d'echantillon
         */
	private int nbEchantillon;


	
	/**
         * Constructeur
         * @param n 
         *      Nombre d echantillons
         * @param nbI
         *      Nombre d individus
         */
	public N_echantillon(int n, int nbI){
		//System.out.println("Création n_echantillon");
		n_echantillon=new Echantillon[n];
		for(int i=0;i<n;i++){
		n_echantillon[i]= new Echantillon(nbI);	
		}
		
	}
	
	
	
	/**
         * Accesseur
         * @return Nombre d'echantillons
         */
	public int getNbEchantillon(){
		return nbEchantillon;
	}
	
        /**
         * Accesseur
         * @param n
         *      numero de l echantillon
         * @return Echantillon souhaite
         */
	public Echantillon getEchantillon(int n){
		return n_echantillon[n-1];
	}
	
        /**
         * Mutateur
         * @param n_Echantillon
         *      Echantillon a placer
         */
        public void setEchantillon(Echantillon n_Echantillon[]){
		n_echantillon=n_Echantillon;
                nbEchantillon=n_Echantillon.length;
	}
}
