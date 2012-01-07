package infolaby;

/**
 *
 * @author ivan
 */
public class Individu_genetique {
    
    int individu[]; // individus avec orientations
    Chemin chemin = new Chemin(); // Chemin de l'individu en coordonnées (pour bouclage, collision, arrivée)
    private Labyrinthe labyrinthe= new Labyrinthe(); // pour vérifier la collision avec les murs
    double alpha; // pondération de la fonction de score
    
    int longueur = 169-1; // 169longueur d'un individu (nombre de cases présentes dans le labyrinthe) moins la première imposée
    int nb_cases = longueur+1; // nombre de cases justes (avant collision, passage sur une même case ou sur l'arrivée)
    double score; // résultat de la fonction de score
    boolean solution; // circuit sans collision ou retour sur soi-même, passant par la case finale
        
    // Constructeur
    public Individu_genetique(double coeff_score,int type_selection){
        
        alpha = coeff_score;
        
        individu = new int[longueur];
        for (int i=0;i<longueur;i++){
            individu[i]=(int) Math.round(Math.random()*3);
        }
        
        int deplacement[][]={{1,1}};
        chemin.setDeplacement(deplacement);
        this.traduction_combinaison(0);
        
        this.collision();
        this.existence();
        if(type_selection!=3){
            this.fonction_score();
        }
    }
    
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
                    if(i<nb_cases){
                    nb_cases = i;    
                    }
                    existe = true;
                }
            }
            if(labyrinthe.estArrivee(deplacement[i][0],deplacement[i][1])){
                solution = true; // remettre à false, quand ?!
            }
            i++;
        }while(!existe && i<nb_cases);  
    }
    
    // On évalue la qualité de chaque individu grâce à la fonction de score.
    void fonction_score(){
        int x = chemin.getDeplacement()[nb_cases-1][0];
        int y = chemin.getDeplacement()[nb_cases-1][1];
        score = 100 - 100*alpha*(Math.sqrt(Math.pow(13-x,2)+Math.pow(13-y,2))/17)+(1-alpha)*(100.0*nb_cases/169);
    }
    
    // On affiche les coordonnées successives du chemin retenu
    void afficher(){
        int deplacement[][] = chemin.getDeplacement();
        for(int i=0;i<longueur+1;i++){
            System.out.println(deplacement[i][0]+","+deplacement[i][1]+" | ");
        }
    }
        
}