/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package infolaby;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author 4dunarem
 */
public class LabyrintheProposition extends SatProblem {

    public int tmax;
    public int nbrLig;
    public int nbrCol;
    public int xi;
    public int yi;
    public int xf;
    public int yf;
    public int[] mursX;
    public int[] mursY;

    public LabyrintheProposition(int tmax, int nbrL, int nbrC, int[] mursX, int[] mursY, int xi, int yi, int xf, int yf) {
        super(tmax * nbrL * nbrC);
        this.tmax = tmax;
        this.nbrCol = nbrC;
        this.nbrLig = nbrL;
        this.xi = xi;
        this.xf = xf;
        this.yi = yi;
        this.yf = yf;
        this.mursX = mursX;
        this.mursY = mursY;

        /* appel des clauses*/
        uneCaseParDeplacement();
        casesPossible();
        posInitialeAuTempsZero();
        arriveeAtteinte();
        pasDeRetour();
        presenceMur();
    }

    public int numVar(int x, int y, int t) {
        return t * this.nbrCol * this.nbrLig + x * this.nbrCol + y + 1;
    }/*
    //  public int numVar(int x, int y, int t) {
    // return x * this.tmax * this.tmax + y * this.tmax + t + 1;
    
    /* Position initiale au temps t = 0 */


    public void posInitialeAuTempsZero() {
        int[] clause = new int[1];
        clause[0] = this.numVar(this.xi, this.yi, 0);
        this.addClause(clause);
    }

    /* Position arrivée atteinte */
    public void arriveeAtteinte() {
        int[] clause = new int[this.tmax];
        for (int t = 0; t < this.tmax; t++) {
            clause[t] = this.numVar(this.xf, this.yf, t);
        }
        this.addClause(clause);
    }

    /* pas de retour sur la position précédente */
    public void pasDeRetour() {
        for (int x = 0; x < this.nbrLig; x++) {
            for (int y = 0; y < this.nbrCol; y++) {
                for (int t1 = 0; t1 < this.tmax; t1++) {
                    for (int t2 = t1 + 1; t2 < this.tmax; t2++) {
                        int[] clause = new int[2];
                        clause[0] = -this.numVar(x, y, t1);
                        clause[1] = -this.numVar(x, y, t2);
                        this.addClause(clause);

                    }
                }
            }

        }
    }

    // Une case par déplacement
    public void uneCaseParDeplacement() {
        for (int t = 0; t < this.tmax; t++) {
            for (int x1 = 0; x1 < this.nbrCol; x1++) {
                for (int y1 = 0; y1 < this.nbrCol; y1++) {
                    for (int x2 = 0 ; x2 < this.nbrCol; x2++) {
                        for (int y2 = 0 ; y2 < this.nbrCol; y2++) {
                            if(x1==x2 && y1==y2){}
                            else{
                            int[] clause = new int[2];
                            clause[0] = -this.numVar(x1, y1, t);
                            clause[1] = -this.numVar(x2, y2, t);
                            this.addClause(clause);  
                            }
                        }
                    }
                }
            }
        }
    }

    /* Gestion des murs */
    public void presenceMur() {

        for (int t = 0; t < this.tmax; t++) { // quel que soit t
            for (int k = 0; k < this.mursX.length; k++) {
                int[] clause = new int[1];
                // v vecteur coordonnée X murs, w coordonnée Y mur
                int vx = this.mursX[k];
                int vy = this.mursY[k];
                clause[0] = -this.numVar(vx, vy, t);
                this.addClause(clause);
            }

        }

    }


public void casesPossible() {

        for (int t = 1; t < this.tmax; t++) { // quel que soit t
            for (int x = 1; x < (this.nbrLig-1); x++) {
            for (int y = 1; y < (this.nbrCol-1); y++) {
                int[] clause = new int[5];
                clause[0] = this.numVar(x, y-1, t-1);
                clause[1] = this.numVar(x, y+1, t-1);
                clause[2] = this.numVar(x+1, y, t-1);
                clause[3] = this.numVar(x-1, y, t-1);
                clause[4] = -this.numVar(x,y,t);
                this.addClause(clause);
            
            }

        }

    }
}
//    public void pasDiagonales() {
//
//        for (int t = 1; t < this.tmax; t++) { // quel que soit t
//            int[] clause = new int[4];
//            clause[0] = -this.numVar(x - 1, y - 1, t - 1);
//            clause[1] = -this.numVar(x - 1, y + 1, t - 1);
//            clause[2] = -this.numVar(x + 1, y - 1, t - 1);
//            clause[3] = -this.numVar(x + 1, y + 1, t - 1);
//            this.addClause(clause);
//        }
//
//    }
    //=========================================================================
//  public int numVar(int x, int y, int t) {
//        return t * this.nbrCol * this.nbrLig + x * this.nbrCol + y + 1;
    public int[] decomposeVar(int numVar) {
        int[] decode = new int[3];
        numVar = numVar - 1;
        /*decode[0] = numVar % this.nbrCol; // y 
        decode[1] = ((numVar - decode[0]) / this.nbrCol) % this.nbrLig; // x
        decode[2] = (numVar - decode[1] - decode[0]*this.nbrCol)/(this.nbrCol*this.nbrLig); //t
         */
        decode[2] = numVar / (this.nbrCol * this.nbrLig); //t
        decode[1] = (numVar / this.nbrCol) % this.nbrLig; // x 
        decode[0] = numVar % this.nbrCol; // y

        return decode;
    }

    //@Override
    @Override
    public String formatVar(int numVar) {
        int[] decode = this.decomposeVar(numVar);
        return "V[" + decode[1] + "," + decode[0] + "," + decode[2] + "]";
    }
    

    public static LabyrintheProposition laby1() {
//        int[] mursX = new int[1];
//        int[] mursY = new int[1];
        int[] mursX = {0, 1, 2, 3, 4, 0, 4, 0, 1, 3, 4, 0, 4, 0, 1, 2, 3, 4};
        int[] mursY = {0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4};
//        mursX[0] = 0;
//        mursY[0] = 1;
        LabyrintheProposition lab = new LabyrintheProposition(6, 5, 5, mursX, mursY, 1, 1, 3, 3);
        return lab;
    }

    public static LabyrintheProposition laby2() {
//        int[] mursX = new int[1];
//        int[] mursY = new int[1];
        int[] mursX = {0};
        int[] mursY = {1};
//        mursX[0] = 0;
//        mursY[0] = 1;
        LabyrintheProposition lab = new LabyrintheProposition(3, 2, 2, mursX, mursY, 0, 0, 1, 1);
        return lab;
    }

    public static LabyrintheProposition laby3() {
//        int[] mursX = new int[1];
//        int[] mursY = new int[1];
        int[] mursX = {0,1,2,3,0,2,3,0,3,0,1,2,3};
        int[] mursY = {0,0,0,0,1,1,1,2,2,3,3,3,3};
//        mursX[0] = 0;
//        mursY[0] = 1;
        LabyrintheProposition lab = new LabyrintheProposition(5, 4, 4, mursX, mursY, 1, 1, 2, 2);
        return lab;
    }
    
        public static LabyrintheProposition laby4() {
//        int[] mursX = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,6,6,6,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,11,11,11,11,11,12,12,12,12,12,12,12,12,12,13,13,13,13,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14};
//        int[] mursY = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,14,0,2,3,4,6,7,8,9,10,11,12,14,0,6,10,14,0,2,4,6,8,10,12,13,14,0,2,4,8,10,14,0,2,4,5,6,8,10,11,12,13,14,0,6,8,12,14,0,1,2,3,4,6,7,8,9,10,12,14,0,6,12,14,0,2,3,4,5,6,8,10,11,12,14,0,2,8,10,14,0,2,4,5,6,8,10,12,14,0,8,12,14,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
          Labyrinthe labyrinthe=new Labyrinthe();
          labyrinthe.doListMurs();
          int[][] list = labyrinthe.getListeMurs();
          int[] mursX = new int[list.length];
          int[] mursY = new int[list.length];
          
          for(int i=0; i<list.length;i++){
              mursX[i]=list[i][0];
              mursY[i]=list[i][1];
          }
        LabyrintheProposition lab = new LabyrintheProposition(40, 15, 15, mursX, mursY, 1, 1, 13, 13);
        return lab;
    }
}
