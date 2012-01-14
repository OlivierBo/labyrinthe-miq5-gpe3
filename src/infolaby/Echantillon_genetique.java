package infolaby;

/**
 *
 * @author ivan
 */

public class Echantillon_genetique {

    private Individu_genetique individus_genetiques[]; // Tableau des individus de l'echantillon
    private int nbIndividu; // Nombre d'individu dans l'echantillon
        
    //Constructeur
    public Echantillon_genetique(int nombre_individus, double alpha){

        nbIndividu=nombre_individus;
        individus_genetiques= new Individu_genetique[nbIndividu];
        
        for(int i=0;i<nbIndividu;i++){
            individus_genetiques[i] = new Individu_genetique(alpha);	
        }		
    }
	
    //Accesseurs
    public Individu_genetique getIndividu(int i){
        return individus_genetiques[i];
    }
   
    public void setIndividu(int i, Individu_genetique indiv){
        this.individus_genetiques[i] = indiv;
    }  
    
    // Recherche du meilleur individu de la population
    public Individu_genetique afficher_meilleurIndividu(){
        double score = 0.0;
        int position = 0;
        
        for(int i=0;i<nbIndividu;i++){
            if(individus_genetiques[i].score>score){
                score = individus_genetiques[i].score;
                position = i;
            }  
        }
        return individus_genetiques[position];
    }
	
}
