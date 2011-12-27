
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Labyrinthe {
        //Définition du labyrinthe
	private char[][] murs = { 
			{'+','+','+','+','+','+','+','+','+','+','+','+','+','+','+'},
			{'+','D',' ',' ',' ',' ',' ',' ','+',' ',' ',' ',' ',' ','+'},
			{'+',' ','+',' ','+','+','+',' ','+',' ','+','+','+',' ','+'},
			{'+',' ','+',' ',' ',' ',' ',' ','+',' ','+',' ',' ',' ','+'},
			{'+',' ','+',' ','+','+','+',' ','+',' ','+',' ','+',' ','+'},
			{'+',' ',' ',' ',' ',' ','+',' ',' ',' ','+',' ','+',' ','+'},
			{'+',' ','+','+','+',' ','+','+','+','+','+',' ','+',' ','+'},
			{'+',' ','+',' ',' ',' ',' ',' ','+',' ',' ',' ',' ',' ','+'},
			{'+',' ','+',' ','+','+','+','+','+',' ','+','+','+','+','+'},
			{'+',' ','+',' ',' ',' ',' ',' ','+',' ',' ',' ',' ',' ','+'},
			{'+',' ','+','+','+','+','+',' ','+',' ','+','+','+',' ','+'},
			{'+',' ','+',' ',' ',' ','+',' ',' ',' ','+',' ',' ',' ','+'},
			{'+',' ','+',' ','+',' ','+','+','+','+','+',' ','+','+','+'},
			{'+',' ',' ',' ','+',' ','+',' ',' ',' ',' ',' ',' ','A','+'},
			{'+','+','+','+','+','+','+','+','+','+','+','+','+','+','+'}};
        
        private int[][] listeMurs= new int[0][0];
        
        
         public Labyrinthe(){
             String[] chaine = new String[15];
		String fichier ="coucou.txt";
        int l=0;
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
            try (BufferedReader br = new BufferedReader(ipsr)) {
                String ligne;
                while ((ligne=br.readLine())!=null && l<16){
                        System.out.println(ligne);
                        chaine[l]=ligne;
                        l++;
                }
            }
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
       for(int i=0; i<=14;i++){       
        for(int j=0; j<=14;j++){
        murs[i][j]=chaine[i].charAt(j);
        }
       }
      }
   
	
	// est-ce que (x,y) est un mur ?
    public boolean estMur(int x, int y) {
	return murs[y][x]=='+';
    }
	
	// est-ce que (x,y) est un départ ?
    public boolean estDepart(int x, int y) {
	return murs[y][x]=='D';
    }
    
	// est-ce que (x,y) est une arrivée ?
    public boolean estArrivee(int x, int y) {
	return murs[y][x]=='A';
    }
    
 // la taille du labyrinthe, a la fois nb lignes et nb colonnes
    public int n() { 
	return murs.length; 
    }
    
    public int[][] getListeMurs(){
        return listeMurs;
    }
    
    public int estVoisins(int x, int y){
        int resultat=0;
        //Test à l'Est
            if(!estMur(x+1,y)){
                resultat=resultat+1;
            }
        
         //Test au Sud
            if(!estMur(x,y+1)){
                resultat=resultat+10;
            }
        
          //Test à l'Ouest
            if(!estMur(x-1,y)){
                resultat=resultat+100;
            }
        
          //Test au Nord
            if(!estMur(x,y-1)){
                resultat=resultat+1000;
            }
        
        return resultat;
    }
    
    public void doListMurs(){
        
        int t=0;
        for(int i=0;i<n();i++){
            for(int j=0;j<n();j++){
                if(estMur(i,j)){
                    t++;
                }
            }
        }
        int[][] l=new int[t][2];
        int t2=0;
        for(int i=0;i<n();i++){
            for(int j=0;j<n();j++){
                if(estMur(i,j)){
                    l[t2][0]=i;
                    l[t2][1]=j;
                    t2++;
                }
            }
        }
        listeMurs=l;
    }
}
