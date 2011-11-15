
public class Labyrinthe {

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
