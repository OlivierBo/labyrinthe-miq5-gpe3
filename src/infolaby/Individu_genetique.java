package infolaby;

/**
 *
 * @author ivan
 */

public class Individu_genetique implements Cloneable {
    
    int individu[]; // individus comprenant les orientations suivantes (01321002...)
    Chemin chemin = new Chemin(); // Chemin de l'individu en coordonnées (pour bouclage, collision, arrivée) : longueur individu + 1
    private Labyrinthe labyrinthe= new Labyrinthe(); // pour vérifier la collision avec les murs
    double alpha; // pondération de la fonction de score  : 0 = priorité sur nb-cases | 1 = priorité sur la distance
    
    int longueur = 169-1; // longueur d'un individu (nombre de cases présentes dans le labyrinthe) moins la première imposée
    int nb_cases = longueur+1; // nombre de cases justes (avant collision, passage sur une même case ou sur l'arrivée)
    double score; // résultat de la fonction de score
    boolean solution; // circuit sans collision ni retour sur soi-même, passant par la case finale
        
    // Constructeur
    public Individu_genetique(double coeff_score){
        
        alpha = coeff_score;
        
        individu = new int[longueur];
        for (int i=0;i<longueur;i++){
            individu[i]=(int)(Math.random()*4);
        }
        
        // Création de la première case [1,1] puis écriture du chemin avec les direction précédemment générées
        int deplacement[][]={{1,1}};
        chemin.setDeplacement(deplacement);
        this.traduction_combinaison(0);
        
        this.collision();
        this.existence();
        this.fonction_score();
    }
    
    // Nécessaire dans la copie des éléments (cf. Fenêtre)
    public Object clone(){
        Individu_genetique individu_to_return = null;
        try{
            individu_to_return = (Individu_genetique) super.clone();
        }catch(CloneNotSupportedException e){}
        
        individu_to_return.individu = individu.clone();
        individu_to_return.chemin = (Chemin) chemin.clone();
        return individu_to_return;
    }
              
    // Ecriture d'un chemin à partir de la dernière case présente et prenant les déplacements en compte à partir le case 'position' jusqu'à la fin
    void traduction_combinaison(int position){
        
        int taille = chemin.getDeplacement().length;
        int derniere_coord[][]={{chemin.getDeplacement()[taille-1][0],chemin.getDeplacement()[taille-1][1]}};
        
        for (int i=position;i<this.longueur;i++){
            switch(this.individu[i]){
                case 0 :
                    derniere_coord[0][1] -= 1;
                    chemin.AddDeplacement(derniere_coord[0][0],derniere_coord[0][1]); // y-1
                    break;
                case 1 :
                    derniere_coord[0][0] += 1;
                    chemin.AddDeplacement(derniere_coord[0][0],derniere_coord[0][1]); // x+1
                    break;
                case 2 :
                    derniere_coord[0][1] += 1;
                    chemin.AddDeplacement(derniere_coord[0][0],derniere_coord[0][1]); // y+1
                    break;
                case 3 :
                    derniere_coord[0][0] -= 1;
                    chemin.AddDeplacement(derniere_coord[0][0],derniere_coord[0][1]); // x-1
                    break;    
            }
        }     
    }
    
    void collision(){ // Détection d'une collision avec un mur --> change la valeur de nb_cases
        int i=1;
        boolean collision = false;
        int deplacement[][] = chemin.getDeplacement();
        do{
            if(labyrinthe.estMur(deplacement[i][0],deplacement[i][1])){
                nb_cases = i;
                collision = true;
            }
            i++;
        }while(!collision && i<longueur+1);
    }
    
    void existence(){ // Détection d'un rebouclage avant la collision --> si nécessaire, change la valeur de nb_cases
        int i=1;
        boolean existe = false;
        int deplacement[][] = chemin.getDeplacement();
        solution = false;
        
        do{
            for(int j=0;j<i;j++){
                if(deplacement[j][0]==deplacement[i][0] && deplacement[j][1]==deplacement[i][1]){
                    nb_cases = i;    
                    existe = true;
                }
            }
            if(labyrinthe.estArrivee(deplacement[i][0],deplacement[i][1])){
                solution = true;
            }
            i++;
        }while(!existe && i<nb_cases);  
    }
    
    void fonction_score(){ // On évalue la qualité de chaque individu grâce à la fonction de score.
        int x = chemin.getDeplacement()[nb_cases-1][0];
        int y = chemin.getDeplacement()[nb_cases-1][1];
        score =   alpha*(18.38-Math.sqrt(Math.pow(13-x,2)+Math.pow(13-y,2)))+(1-alpha)*(nb_cases);
        //score = 100 - 100*alpha*(Math.sqrt(Math.pow(13-x,2)+Math.pow(13-y,2))/18.38)-(1-alpha)*(100.0*(169-nb_cases)/169);
    }
	
	
    // On affiche les coordonnées successives du chemin retenu
    void afficher(){
        int deplacement[][] = chemin.getDeplacement();
        for(int i=0;i<longueur+1;i++){
            System.out.println(deplacement[i][0]+","+deplacement[i][1]+" | ");
        }
    }
     
        /**
         * Accesseur
         * @return chemin
         */
	public Chemin getChemin(){
		return chemin;
	}
        
        
        /**
         * Accesseur
         * @return nb_cases
         */
	public int getNbcases(){
		return nb_cases;
	}
}