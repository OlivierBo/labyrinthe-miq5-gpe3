
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Labyrinthe {
        //Définition du labyrinthe
	public char[][] murs = { 
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
    boolean estMur(int x, int y) {
	return murs[y][x]=='+';
    }
	
	// est-ce que (x,y) est un départ ?
    boolean estDepart(int x, int y) {
	return murs[y][x]=='D';
    }
    
	// est-ce que (x,y) est une arrivée ?
    boolean estArrivee(int x, int y) {
	return murs[y][x]=='A';
    }
    
 // la taille du labyrinthe, a la fois nb lignes et nb colonnes
    int n() { 
	return murs.length; 
    }
    
}
