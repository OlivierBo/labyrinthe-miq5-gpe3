
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Fenetre fenetre = new Fenetre();
		//fenetre.getPan().BFS();
                
                /*
		//----------------TESTS DES FONCTIONS ------------------//
		
		//////// Test du fonctionnement des classes////////
		
		System.out.println("test deplacement");
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getNbcases());
		System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getDeplacement()[0][0]);
		
		int[][] dep={{1,1},{1,2},{1,3},{1,4},{1,5},{2,5},{3,5},{4,5},{5,5},{5,6},{5,7},{4,7},{3,7}};
		
		fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().setDeplacement(dep);
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getNbcases());
		System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getDeplacement()[1][1]);
		try {
			Thread.sleep(2000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                System.out.println("Test detection des murs voisins :");
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getLabyrinthe().estMursVoisins(1, 1));              
		int[][] dep2={{1,1},{2,1},{3,1},{3,2},{3,3},{4,3},{5,3},{6,3},{7,3},{7,4},{7,5},{8,5},{9,5},{9,4}};
		fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().setDeplacement(dep2);
		fenetre.getPan().repaint();

                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getNbcases());
                
		boolean a1=fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().existeDeja(3, 3);
		boolean a2=fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().existeDeja(9, 4);
		
		System.out.println("Test case existante :"+a1+a2);
                
                try {
			Thread.sleep(1000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                
                
                
                System.out.println("Test ajout de case dans le déplacement :");
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getNbcases());
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(9, 3);
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getDeplacement()[14][1]);
		System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getNbcases());
                System.out.println("----------");
		fenetre.getPan().repaint();
                
                // Animation basique
                try {
			Thread.sleep(1000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(9, 2);
                fenetre.getPan().repaint();
                try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(9, 1);
                fenetre.getPan().repaint();
                try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(10, 1);
                fenetre.getPan().repaint();
                try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(11, 1);
                fenetre.getPan().repaint();
                try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(12, 1);
                fenetre.getPan().repaint();
                try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(13, 1);
                fenetre.getPan().repaint();
                try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().AddDeplacement(13, 2);
                fenetre.getPan().repaint();
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getChemin().getNbcases());
	
                System.out.println("Test distance à l'arrivée");
                System.out.println(fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).distance(13, 2));
                
                System.out.println("Test liste des coordonnées des murs");
                fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getLabyrinthe().doListMurs();
                int[][] lmurs=fenetre.getPan().getN_Echantillon().getEchantillon(1).getIndividu(1).getLabyrinthe().getListeMurs();
                for(int i=0;i<lmurs.length;i++){
                    System.out.println(lmurs[i][0]);
                    System.out.println(lmurs[i][1]);
                    System.out.println("------");
                }
        //*/
        }

}
