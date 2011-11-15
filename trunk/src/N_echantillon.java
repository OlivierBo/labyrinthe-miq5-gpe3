
public class N_echantillon {

	private Echantillon n_echantillon[];
	
	int nbEchantillon;


	
	// Constructeur
	public N_echantillon(int n, int nbI){
		System.out.println("Création n_echantillon");
		n_echantillon=new Echantillon[n];
		for(int i=0;i<n;i++){
		n_echantillon[i]= new Echantillon(nbI);	
		}
		
	}
	
	
	
	// Accesseurs
	
	public int getNbEchantillon(){
		return nbEchantillon;
	}
	
	public Echantillon getEchantillon(int n){
		return n_echantillon[n-1];
	}
	
	public void setNbEchantillon(int n){
		this.nbEchantillon=n;
	}
}
