package infolaby;

/**
 *
 * @author ivan
 */
public class Echantillon_genetique {

    private Individu_genetique individus_genetiques[]; // Tableau des individus de l'echantillon
    private int nbIndividu; // Nombre d'individu dans l'echantillon
        
    //Constructeur
    public Echantillon_genetique(int nombre_individus, double alpha, int type_selection){

        nbIndividu=nombre_individus;
        individus_genetiques= new Individu_genetique[nbIndividu];
        
        for(int i=0;i<nbIndividu;i++){
            individus_genetiques[i] = new Individu_genetique(alpha,type_selection);	
        }		
    }
	
    //Accesseurs
    public Individu_genetique getIndividu(int i){
        return individus_genetiques[i];
    }
   
    public void setIndividu(int i, Individu_genetique indiv){
        this.individus_genetiques[i] = indiv;
    }  
    
    public boolean afficher_meilleurIndividu(int type_selection){
        double score = 0.0;
        int position = 0;
        boolean fin=false;
        
        // Les scores n'ont jamais été calculés dans le 3e type de sélection
        if(type_selection==3){
            for(int i=0;i<nbIndividu;i++){
                individus_genetiques[i].fonction_score();
            }
        }
        
        // On recherche le meilleur individu (meilleur score )
        for(int i=0;i<nbIndividu;i++){
            if(individus_genetiques[i].score>score){
                score = individus_genetiques[i].score;
                position = i;
            }  
        }
        Individu_genetique individu_genetique = individus_genetiques[position];
        
        // Affichage des informations
        System.out.println("\n******* Meilleur individu de la population *******");
        if(individu_genetique.solution){
            if(individu_genetique.score>80){
                System.out.println("- Est LA solution");
                individu_genetique.afficher();
                fin=true;
            }else{
                System.out.println("- Est une solution");
            }
        }else{
            System.out.println("- N'est pas une solution");
        }
        System.out.println("- Score : "+score);
        System.out.println("- Nombre bonnes cases : "+individu_genetique.nb_cases);
        System.out.println("- Chemin :");
        
        return fin;
    }
    
    public Individu_genetique afficher_meilleurIndividuDansEchantillon(int type_selection){
        double score = 0.0;
        int position = 0;
        boolean fin=false;
        
        // Les scores n'ont jamais été calculés dans le 3e type de sélection
        if(type_selection==3){
            for(int i=0;i<nbIndividu;i++){
                individus_genetiques[i].fonction_score();
            }
        }
        
        // On recherche le meilleur individu (meilleur score )
        for(int i=0;i<nbIndividu;i++){
            if(individus_genetiques[i].score>score){
                score = individus_genetiques[i].score;
                position = i;
            }  
        }
        return individus_genetiques[position];
    }
}
