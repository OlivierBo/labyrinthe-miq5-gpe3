/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package infolaby;

/**
 *
 * @author rd08
 */
public class Proposition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Labyrinthe p = Labyrinthe.laby4();
        //System.out.println(p);
        //System.out.println(p.formatClauses());
        int[] sol = p.premiereSol();
        int nb = 0;
        int[] sol1 = new int[0];
        for(int i=0;i<sol.length;i++){
            if(sol[i]>0){
                nb++;
                int[] soltemp=new int[nb];
                soltemp[nb-1]=sol[i];
                
                for(int k=0;k<sol1.length;k++){
                    soltemp[k]=sol1[k];
                }  
             sol1=new int[nb]; 
                for(int k=0;k<sol1.length;k++){
                    sol1[k]=soltemp[k];
                }  
            }
        }


//        for(int i=0;i<sol.length;i++){
//            System.out.println(sol[i]);
//        }

        for(int i=0;i<sol1.length;i++){
            System.out.println(sol1[i] + "  decomposition : "+p.formatVar(sol1[i]));
        }
        //System.out.println(p.formatSol(sol));

        //System.out.println(p.affichePremiedecodeSolutions(3));
    }
}
