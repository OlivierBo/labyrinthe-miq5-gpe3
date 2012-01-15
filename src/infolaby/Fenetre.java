package infolaby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe contenant la totalite des algorithmes de resolution ainsi que la gestion de l'interface
 * @author Olivier
 */
public class Fenetre extends JFrame {

    /**
     * Panneau affichage labyrinthe
     */
    private Panneau pan = new Panneau();
    /**
     * JComboBox : listes déroulantes
     */
    private JComboBox<String> comboBoxAlpha = new JComboBox<>();
    private JComboBox<String> comboBoxNbI = new JComboBox<>();
    private JComboBox<String> comboBoxTypeResolution = new JComboBox<>();
    /**
     * JButton : Bouton
     */
    private JButton boutonLancer = new JButton("Lancer");
    private JButton boutonArreter = new JButton("Arreter");
    private JButton boutonContinuer = new JButton("Continuer");
    /**
     * JPanel : Layout pour placer les boutons et informations
     */
    private JPanel container = new JPanel();
    private JPanel south = new JPanel();
    private JPanel north2 = new JPanel();
    private JPanel north = new JPanel();
    private JPanel west = new JPanel();
    private JPanel west2 = new JPanel();
    /**
     * Jlabel : Ecriture de chaîne de caractères sur l'interface
     */
    private JLabel label1 = new JLabel("");
    private JLabel labelE = new JLabel("");
    /**
     * Boolean en cours d'animation ? selon l'etat, met en pause la résolution
     */
    private boolean animated = true;
    /**
     * Solution trouvée par l'algorithme
     */
    private boolean solution = false;
    /**
     * Thread cree lors d une execution d un algorithme
     */
    private Thread t;
    /**
     * Variable algorithme DFS
     */
    private Individu id_DFS = new Individu();
    /**
     * Variable algorithme BFS
     */
    private Individu id_BFS = new Individu();
    /**
     * Variable algorithme greedy
     */
    private Individu id_greedy = new Individu();
    /**
     * Variable algorithme A
     */
    private Individu id_A = new Individu();
    /**
     * Variable algorithme escalade
     */
    private Individu id_escalade = new Individu();
    /**
     * Variable algorithme tabous
     */
    private Individu id_tabous = new Individu();
    /**
     * Variable algorithme recuit
     */
    private Echantillon echantillon_recuit = new Echantillon(1);
    /**
     * Variable algorithme genetique
     */
    private Echantillon_genetique echantillon_genetique;
    /**
     * Variable algorithme genetique - meilleur individu
     */
    private Individu_genetique individu_meilleur_genetique;
    /**
     * Variable algorithme fourmis
     */
    private Echantillon echantillon_fourmis = new Echantillon(1);
    /**
     * Variable algorithme genetique
     */
    private Individu ind_propo = new Individu();
    /**
     * Nombre d individus a traiter
     */
    private int nbIndividu = 1;
    /**
     * Nombre d echantillons a traiter
     */
    private int nbEchantillon = 1;
    /**
     * Iteration d un algorithme de resolution
     */
    private int iteration = 0;
    /**
     * Pondération de la fonction score
     */
    private double alpha = 0.6;
    /**
     * Variables de sorties d'algorithme pour comparaison
     */
    private float moyenneTempsResolution;
    /**
     * Allocation memoire de l'algorithme
     */
    private long memoire;
    /**
     * Temps de la résolution
     */
    private long temps;
    long tempspausedeb;
    long tempspausefin;

    /**
     * Constructeur
     */
    public Fenetre() {
        /*
        Définition de la fenetre
         */
        this.setTitle("Resolution Labyrinthe - Projet Informatique");
        this.setSize(1000, 525);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Définition des listes déroulantes
        String[] listeA = {"0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1"};
        String[] listeI = {"20", "40", "60", "80", "100", "120", "140", "160", "180", "200"};
        String[] listeResol = {"DFS", "BFS", "Algorithme greedy", "Algorithme A*", "Escalade", "Recherche avec tabous", "Recuit simulé", "Algorithme génétiques", "Algorithme de colonies de fourmis", "Logique de propositions", "Logique de prédicats"};

        comboBoxAlpha = new JComboBox<>(listeA);
        comboBoxAlpha.setSelectedIndex(6);
        comboBoxNbI = new JComboBox<>(listeI);
        comboBoxTypeResolution = new JComboBox<>(listeResol);




        //Définition de l'écoute des commandes
        comboBoxAlpha.addActionListener(new ItemActionAlpha());
        comboBoxAlpha.setPreferredSize(new Dimension(60, 30));
        comboBoxAlpha.setForeground(Color.blue);

        comboBoxNbI.addActionListener(new ItemActionNbI());
        comboBoxNbI.setPreferredSize(new Dimension(60, 30));
        comboBoxNbI.setForeground(Color.blue);

        comboBoxTypeResolution.addActionListener(new ItemActionResol());
        comboBoxTypeResolution.setPreferredSize(new Dimension(150, 30));
        comboBoxTypeResolution.setForeground(Color.blue);

        boutonLancer.addActionListener(new BoutonLancerListener());
        boutonArreter.addActionListener(new BoutonArreterListener());
        boutonContinuer.addActionListener(new BoutonContinuerListener());


        // Placement des commandes du haut
        north.setPreferredSize(new Dimension(500, 160));
        north.setLayout(new GridLayout(3, 3, 5, 5));
        north.add(new JLabel("Alpha"));
        north.add(new JLabel("Nombre d'individus"));
        north.add(new JLabel("Type de résolution"));
        north.add(comboBoxAlpha);
        north.add(comboBoxNbI);
        north.add(comboBoxTypeResolution);
        north.add(boutonLancer);
        north.add(boutonArreter);
        boutonArreter.setEnabled(false);
        boutonContinuer.setEnabled(false);
        north.add(boutonContinuer);

        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setPreferredSize(new Dimension(500, 20));
        north2.setPreferredSize(new Dimension(500, 180));
        north2.setLayout(new BorderLayout());
        north2.add(label1, BorderLayout.NORTH);
        north2.add(north, BorderLayout.SOUTH);

        //Placement des info du bas
        south.setPreferredSize(new Dimension(500, 345));
        south.add(new JLabel(""));
        south.add(labelE);

        west.setPreferredSize(new Dimension(500, 100));
        west.setLayout(new BorderLayout());
        west.add(north2, BorderLayout.NORTH);
        west.add(south, BorderLayout.WEST);

        //Placement du labyrinthe au milieu et layout final
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        container.add(west, BorderLayout.WEST);
        container.add(pan, BorderLayout.CENTER);

        // Activer l'interface
        this.setContentPane(container);
        this.setVisible(true);
    }

    /**
     * Renvoie le panneau
     * @return Panneau
     */
    public Panneau getPan() {
        return pan;
    }

    /**
     * Classe interne implémentant l'interface de l'appui du bouton selection NbE
     */
    class ItemActionAlpha implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            label1.setText("action sur " + comboBoxAlpha.getSelectedItem() + " dans Alpha");

        }
    }

    /**
     * Classe interne implémentant l'interface de l'appui du bouton selection NbI
     */
    class ItemActionNbI implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            label1.setText("action sur " + comboBoxNbI.getSelectedItem() + " dans nombre individu ");
        }
    }

    /**
     * Classe interne implémentant l'interface de l'appui du bouton selection Resolution
     */
    class ItemActionResol implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            label1.setText("action sur " + comboBoxTypeResolution.getSelectedItem() + " dans type de r�solution " + comboBoxTypeResolution.getSelectedIndex());
        }
    }

    /**
     * Classe interne implémentant l'interface de l'appui du bouton Lancer
     */
    class BoutonLancerListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            animated = true;
            boutonLancer.setEnabled(false);
            boutonArreter.setEnabled(true);
            comboBoxAlpha.setEnabled(false);
            comboBoxNbI.setEnabled(false);
            comboBoxTypeResolution.setEnabled(false);
            getPan().init_matrice();
            getPan().setIndividu_afficher(new Individu());
            repaint();

            //Selection nombre d'échantillon
            switch (comboBoxAlpha.getSelectedIndex()) {
                case 0:
                    alpha = 0.0;
                    break;
                case 1:
                    alpha = 0.1;
                    break;
                case 2:
                    alpha = 0.2;
                    break;
                case 3:
                    alpha = 0.3;
                    break;
                case 4:
                    alpha = 0.4;
                    break;
                case 5:
                    alpha = 0.5;
                    break;
                case 6:
                    alpha = 0.6;
                    break;
                case 7:
                    alpha = 0.7;
                    break;
                case 8:
                    alpha = 0.8;
                    break;
                case 9:
                    alpha = 0.9;
                    break;
                case 10:
                    alpha = 1.0;
                    break;
            }

            //Selection nombre d'individus
            switch (comboBoxNbI.getSelectedIndex()) {
                case 0:
                    nbIndividu = 20;
                    break;
                case 1:
                    nbIndividu = 40;
                    break;
                case 2:
                    nbIndividu = 60;
                    break;
                case 3:
                    nbIndividu = 80;
                    break;
                case 4:
                    nbIndividu = 100;
                    break;
                case 5:
                    nbIndividu = 120;
                    break;
                case 6:
                    nbIndividu = 140;
                    break;
                case 7:
                    nbIndividu = 160;
                    break;
                case 8:
                    nbIndividu = 180;
                    break;
                case 9:
                    nbIndividu = 200;
                    break;
            }


            // Selection type de résolution
            switch (comboBoxTypeResolution.getSelectedIndex()) {
                case 0:
                    nbIndividu = 1;
                    t = new Thread(new DFS_Runnable());
                    break;
                case 1:
                    nbIndividu = 1;
                    t = new Thread(new BFS_Runnable());
                    break;

                case 2:
                    nbIndividu = 1;
                    t = new Thread(new Greedy_Runnable());
                    break;

                case 3:
                    nbIndividu = 1;
                    t = new Thread(new A_Runnable());
                    break;

                case 4:
                    nbIndividu = 1;
                    t = new Thread(new Escalade_Runnable());
                    break;

                case 5:
                    nbIndividu = 1;
                    t = new Thread(new Tabous_Runnable());
                    break;

                case 6:
                    t = new Thread(new Recuit_Runnable());
                    break;

                case 7:
                    t = new Thread(new Genetique_Runnable());
                    break;

                case 8:
                    t = new Thread(new Fourmis_Runnable());
                    break;

                case 9:
                    t = new Thread(new Proposition_Runnable());
                    break;

                case 10:
                    t = new Thread(new Predicat_Runnable());
                    break;
            }
            System.out.println("Parametres d'execution :   Algo " + comboBoxTypeResolution.getSelectedItem() + " ; NbI =  " + nbIndividu + " ; Alpha = " + alpha);
            t.start();
        }
    }

    /**
     * Classe interne implémentant l'interface de l'appui du bouton arreter
     */
    class BoutonArreterListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            boutonContinuer.setEnabled(false);
            solution = true;
            label1.setText("Vous avez cliqué sur le bouton Arreter");
            t.stop();
        }
    }

    /**
     * Classe interne implémentant l'interface de l'appui du bouton continuer
     */
    class BoutonContinuerListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            animated = true;
            boutonContinuer.setEnabled(false);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class BFS_Runnable implements Runnable {

        @Override
        public void run() {
            id_BFS = new Individu();
            iteration = 1;
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            BFS();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(id_BFS);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(id_BFS);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class DFS_Runnable implements Runnable {

        @Override
        public void run() {
            id_DFS = new Individu();
            iteration = 1;
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            DFS();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(id_DFS);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(id_DFS);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Greedy_Runnable implements Runnable {

        @Override
        public void run() {
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            AlgorithmeGreedy();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(id_greedy);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(id_greedy);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class A_Runnable implements Runnable {

        @Override
        public void run() {
            tempspausefin = 0;
            long debT = System.currentTimeMillis();
            long deb = Runtime.getRuntime().freeMemory();
            AlgorithmeA();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(id_A);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(id_A);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Escalade_Runnable implements Runnable {

        @Override
        public void run() {
            iteration = 1;
            id_escalade = new Individu();
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            Escalade();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(id_escalade);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(id_escalade);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Tabous_Runnable implements Runnable {

        @Override
        public void run() {
            id_tabous = new Individu();
            iteration = 1;
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            RechercheAvecTabous();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(id_tabous);
            getPan().repaint();
            sendIteration();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(id_tabous);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Recuit_Runnable implements Runnable {

        @Override
        public void run() {
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            RecuitSimule();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Genetique_Runnable implements Runnable {

        @Override
        public void run() {
            iteration=0;
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            AlgorithmeGenetique();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            individu_meilleur_genetique = echantillon_genetique.afficher_meilleurIndividu();
            individu_meilleur_genetique.collision();
            individu_meilleur_genetique.existence();
            int nbcases = individu_meilleur_genetique.getNbcases();
            individu_meilleur_genetique.getChemin().couper(nbcases);
            getPan().getIndividu_afficher().getChemin().setDeplacement(individu_meilleur_genetique.getChemin().getDeplacement());
            repaint();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            Individu ind = new Individu();
            ind.getChemin().setDeplacement(individu_meilleur_genetique.getChemin().getDeplacement());
            ScoreExploration(ind);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Fourmis_Runnable implements Runnable {

        @Override
        public void run() {
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            AlgorithmeColonieFourmis();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            // Choix de la meilleure fourmis en cours
            int position = 0;
            for (int l = 0; l < nbIndividu; l++) {
                double score = 0.0;
                if (doScore(echantillon_fourmis.getIndividu(l)) > score) {
                    score = doScore(echantillon_fourmis.getIndividu(l));
                    position = l;
                }
            }
            // Affichage de la fourmis
            getPan().setIndividu_afficher(echantillon_fourmis.getIndividu(position));
            repaint();
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            ScoreExploration(echantillon_fourmis.getIndividu(position));
            }
            
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Proposition_Runnable implements Runnable {

        @Override
        public void run() {
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            LogiqueDeProposition();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
            getPan().setIndividu_afficher(ind_propo);
            repaint();            
            boutonLancer.setEnabled(true);
            boutonArreter.setEnabled(false);
            comboBoxAlpha.setEnabled(true);
            comboBoxNbI.setEnabled(true);
            comboBoxTypeResolution.setEnabled(true);
            solution=true;
            ScoreExploration(ind_propo);
        }
    }

    /**
     * Classe interne implémentant l'interface de lancement de l'algorithme souhaite
     */
    class Predicat_Runnable implements Runnable {

        @Override
        public void run() {
            tempspausefin = 0;
            long deb = Runtime.getRuntime().freeMemory();
            long debT = System.currentTimeMillis();
            LogiqueDePredicats();
            memoire = deb - Runtime.getRuntime().freeMemory();
            temps = System.currentTimeMillis() - debT - tempspausefin;
        }
    }

    /**
     * Arrondi d une valeur float
     * @param f
     *  float a arrondir
     * @param i
     *  a mettre a zero
     * @return float arrondi
     */
    public double round(double f, int i) {
        return (double) ((int) (f * Math.pow(10, i) + 0.5)) / Math.pow(10, i);
    }

    /**
     * Ecrire l'iteration en pause
     */
    public void sendIteration() {
        label1.setText("iteration =  " + iteration);
    }

    /**
     * Ecrire les informations au bas gauche de la fenetre
     * @param str
     *      Chaîne à écrire
     */
    public void writeScore(String str) {
        labelE.setText(str);
    }

     /**
     * Calcul et affichage du score
     * @param ind
     *      Individu sur lequel le calcul est fait
     */
    public void ScoreExploration(Individu ind) {
        double score = 0.0;
        int x = ind.getChemin().get_last_x();
        int y = ind.getChemin().get_last_y();
        int nbcases = ind.getChemin().getNbcases();
        score = alpha*(18.38-Math.sqrt(Math.pow(13-x,2)+Math.pow(13-y,2)))+(1-alpha)*(nbcases);
        writeScore("<html> Résultats de l'individu <br> solution : " + solution + "<br> Nombre de cases explorées : " + nbcases + "<br> Distance : " + (Math.sqrt(Math.pow(13 - x, 2) + Math.pow(13 - y, 2))) + "<br> Score : " + score + "<br> Memoire : " + (double) ((double) (memoire) / (1048576.0)) + "<br> Temps : " + (double) (temps) / 1000.0 + "</html>");
    }

    /**
     * Calcul du score
     * @param ind
     *      Individu sur lequel le calcul est fait
     */
    public double doScore(Individu ind) {
        double score = 0.0;
        int x = ind.getChemin().get_last_x();
        int y = ind.getChemin().get_last_y();
        int nbcases = ind.getChemin().getNbcases();
        score =  alpha*(18.38-Math.sqrt(Math.pow(13-x,2)+Math.pow(13-y,2)))+(1-alpha)*(nbcases);
        return score;
    }

    /**
     * Mise en pause d'un algorithme utilisant la variable globale iteration
     * @param id
     *      Individu sur lequel l'affichage est fait
     * @param iter
     *      Nombre d'itérations pour faire la pause
     */
    public void pause(Individu id, int iter) {
        if (iteration % iter == 0) {
            tempspausedeb = System.currentTimeMillis();
            animated = false;
            sendIteration();
            boutonContinuer.setEnabled(true);
            getPan().setIndividu_afficher(id);
            getPan().repaint();
            if (solution) {
                iteration++;
            }
            while (!animated) {
                System.out.println(" attente " + iteration);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            iteration++;
            tempspausefin = tempspausefin + System.currentTimeMillis() - tempspausedeb;
        } else {
            iteration++;
        }
    }

    /**
     * Resolution BFS
     */
    public void BFS() {

        //Matrice permettant de mémoriser les positions
        int[][] mem_positionX = new int[40][40];
        int[][] mem_positionY = new int[40][40];
        //Entier permettant de sélectionner les cases de mem-position
        int i; //Sélectionne le niveau dans le diagramme des possibilités
        int j; //Sélectionne le numéro dans les niveaux
        int k;  //Permet de remplir les cases mémoires  

        solution = false; // boolean permettant d'arreter le programme quand l'individu est arrivé

        ///Position de l'individu dans le labyrinthe en pixels
        int x = id_BFS.getChemin().getDeplacement()[0][0]; //Abscisse
        int y = id_BFS.getChemin().getDeplacement()[0][1]; //Ordonnée


        //Matrice de boolean pour vérifier si une position dans le labyrinthe a déjà été mémorisée
        boolean[][] DejaMem = new boolean[15][15];

        //Initialisation de la matrice DejaMem
        for (int a = 0; a < 15; a++) {
            for (int b = 0; b < 15; b++) {
                DejaMem[a][b] = false;
            }
        }

        DejaMem[1][1] = true; //Permet de démarrer.
        mem_positionX[0][0] = x;
        mem_positionY[0][0] = y;

        for (i = 0; i < 39; i++) {
            k = 0;
            for (j = 0; j < 40; j++) {
                if (mem_positionX[i][j] != 0) { //Nous vérifions si la case i,j de mem_position a déjà enregistré une position
                    if (solution == false) {

                        x = mem_positionX[i][j];
                        y = mem_positionY[i][j];

                        // Déplacer l'individu à une possibilité de case
                        id_BFS.getChemin().AddDeplacement(x, y);
                        getPan().setIndividu_afficher(id_BFS);
                        getPan().repaint();

                        //Arrête le programme si l'individu est arrivé
                        solution = id_BFS.getLabyrinthe().estArrivee(x, y);


                        //Analyse des possibilités de chemin et mise en mémoire

                        //Est
                        if (id_BFS.getChemin().existeDeja(x + 1, y) == false && id_BFS.getLabyrinthe().estMur(x + 1, y) == false) {
                            if (DejaMem[x + 1][y] == false) {
                                mem_positionX[i + 1][k] = x + 1;
                                mem_positionY[i + 1][k] = y;
                                k = k + 1;
                                DejaMem[x + 1][y] = true;
                            }
                        }

                        //Sud
                        if (id_BFS.getChemin().existeDeja(x, y + 1) == false && id_BFS.getLabyrinthe().estMur(x, y + 1) == false) {
                            if (DejaMem[x][y + 1] == false) {
                                mem_positionX[i + 1][k] = x;
                                mem_positionY[i + 1][k] = y + 1;
                                k = k + 1;
                                DejaMem[x][y + 1] = true;
                            }
                        }

                        //Ouest
                        if (id_BFS.getChemin().existeDeja(x - 1, y) == false && id_BFS.getLabyrinthe().estMur(x - 1, y) == false) {
                            if (DejaMem[x - 1][y] == false) {
                                mem_positionX[i + 1][k] = x - 1;
                                mem_positionY[i + 1][k] = y;
                                k = k + 1;
                                DejaMem[x - 1][y] = true;
                            }
                        }
                        //Nord
                        if (id_BFS.getChemin().existeDeja(x, y - 1) == false && id_BFS.getLabyrinthe().estMur(x, y - 1) == false) {
                            if (DejaMem[x][y - 1] == false) {
                                mem_positionX[i + 1][k] = x;
                                mem_positionY[i + 1][k] = y - 1;
                                k = k + 1;
                                DejaMem[x][y - 1] = true;
                            }
                        }

                    }

                }
            }
            pause(id_BFS, 10);

        }
        for (i = 0; i < id_BFS.getChemin().getNbcases(); i++) {
            System.out.println(id_BFS.getChemin().getDeplacement()[i][0] + " " + id_BFS.getChemin().getDeplacement()[i][1]);
        }
        System.out.println("distance parcourue= " + id_BFS.getChemin().getNbcases());
    }

    /**
     * Resolution DFS
     */
    public void DFS() {


        //Matrice permettant de mémoriser les positions
        //int[][] mem_positionX = new int[40][40];
        //int[][] mem_positionY = new int[40][40];
        //Entier permettant de sélectionner les cases de mem-position
        int i; //Sélectionne le niveau dans le diagramme des possibilités
        int j; //Sélectionne le numéro dans les niveaux
        //int k;  //Permet de remplir les cases mémoires

        //Matrices permettant de mémoriser la case précédente d'une autre
        int[][] pos_precX = new int[40][40];
        int[][] pos_precY = new int[40][40];

        int tamponX; //Mémoire tampon permettant à l'individu de revenir en arrière en utilisant pos_prec
        int tamponY;

        //boolean individu_arrivee=false; // boolean permettant d'arreter le programme quand l'individu est arrivé

        //Permet de noter une case ne précédent aucun débouché de solution
        boolean[][] aucune_solution = new boolean[15][15];

        //Position de l'individu dans le labyrinthe en pixels
        int x = id_DFS.getChemin().getDeplacement()[0][0]; //Abscisse
        int y = id_DFS.getChemin().getDeplacement()[0][1]; //Ordonnée



        //mem_positionX[0][0]=x;
        //mem_positionY[0][0]=y;

        solution = id_DFS.getLabyrinthe().estArrivee(x, y);

        while (solution == false) {

            //Si possible, se déplacer à l'Est par rapport à la position mémorisée par i 
            if (aucune_solution[x + 1][y] == false && id_DFS.getChemin().existeDeja(x + 1, y) == false && id_DFS.getLabyrinthe().estMur(x + 1, y) == false) {
                //Se déplacer et enregistrer position en i+1
                id_DFS.getChemin().AddDeplacement(x + 1, y);
                pos_precX[x + 1][y] = x;
                pos_precY[x + 1][y] = y;
                x = x + 1;

            } // sinon se déplacer au Sud ou au Nord ou à l'Ouest...
            else {
                if (aucune_solution[x][y + 1] == false && id_DFS.getChemin().existeDeja(x, y + 1) == false && id_DFS.getLabyrinthe().estMur(x, y + 1) == false) {
                    id_DFS.getChemin().AddDeplacement(x, y + 1);
                    pos_precX[x][y + 1] = x;
                    pos_precY[x][y + 1] = y;
                    y = y + 1;

                } else { //deplacement à l'Ouest
                    if (aucune_solution[x - 1][y] == false && id_DFS.getChemin().existeDeja(x - 1, y) == false && id_DFS.getLabyrinthe().estMur(x - 1, y) == false) {
                        id_DFS.getChemin().AddDeplacement(x - 1, y);
                        getPan().setIndividu_afficher(id_DFS);
                        getPan().repaint();
                        pos_precX[x - 1][y] = x;
                        pos_precY[x - 1][y] = y;
                        x = x - 1;

                    } else { //deplacement au Nord
                        if (aucune_solution[x][y - 1] == false && id_DFS.getChemin().existeDeja(x, y - 1) == false && id_DFS.getLabyrinthe().estMur(x, y - 1) == false) {
                            id_DFS.getChemin().AddDeplacement(x, y - 1);
                            getPan().setIndividu_afficher(id_DFS);
                            getPan().repaint();
                            pos_precX[x][y - 1] = x;
                            pos_precY[x][y - 1] = y;
                            y = y - 1;

                        } else {
                            //Si aucune position possible alors
                            //Enregistrer la position comme ayant aucun débouché de solution
                            aucune_solution[x][y] = true;
                            //Remonter à l'étape i-1 
                            tamponX = pos_precX[x][y];
                            tamponY = pos_precY[x][y];
                            x = tamponX;
                            y = tamponY;
                            id_DFS.getChemin().AddDeplacement(x, y);
                        }
                    }
                }
            }

            solution = id_DFS.getLabyrinthe().estArrivee(x, y);

            pause(id_DFS, 10);
        }
        for (i = 0; i < id_DFS.getChemin().getNbcases(); i++) {
            System.out.println(id_DFS.getChemin().getDeplacement()[i][0] + " " + id_DFS.getChemin().getDeplacement()[i][1]);
        }
        System.out.println("distance parcourue= " + id_DFS.getChemin().getNbcases());
        getPan().setIndividu_afficher(id_DFS);
        getPan().repaint();
        sendIteration();
        boutonLancer.setEnabled(true);
        boutonArreter.setEnabled(false);
        comboBoxAlpha.setEnabled(true);
        comboBoxNbI.setEnabled(true);
        comboBoxTypeResolution.setEnabled(true);
        ScoreExploration(id_DFS);
    }

    /**
     * Resolution Greedy
     */
    public void AlgorithmeGreedy() {
        // TODO Auto-generated method stub
    }

    /**
     * Resolution A
     */
    public void AlgorithmeA() {
        // TODO Auto-generated method stub
    }

    /**
     * Resolution escalade
     */
    public void Escalade() {
        // Initialisation
        solution = false; // définir une variable booléenne pour tester la solution
        boolean bloque = false; // définir une variable bouléenne pour tester la blocage
        int x = 1; // initialisation de distance en x
        int y = 1; // initialisation de distance en hy
        int distance = id_escalade.distance(x, y); // initialisation la distance entre pion et point d'arrivée


         // Boucle pour le d¨¦placement, la boucle s'arrete lorsque le programme est bloque ou qu'il y a une solution    
        while ((solution == false) && (bloque == false)) {

            // Recherche du meilleur voisin
            // test de mur et calculer la distance de case de l'est et point d'arrivée
            if (!id_escalade.getLabyrinthe().estMur(x + 1, y) && (distance > id_escalade.distance(x + 1, y))) { // Test à l'Est
                x = x + 1;
                id_escalade.getChemin().AddDeplacement(x, y);
            // test de mur et calculer la distance de case du sud et point d'arrivée
            } else if (!id_escalade.getLabyrinthe().estMur(x, y + 1) && (distance > id_escalade.distance(x, y + 1))) {
                y = y + 1;
                id_escalade.getChemin().AddDeplacement(x, y);
            // test de mur et calculer la distance de case de l'Ouest et point d'arrivée
            } else if (!id_escalade.getLabyrinthe().estMur(x - 1, y) && (distance > id_escalade.distance(x - 1, y))) {
                x = x - 1;
                id_escalade.getChemin().AddDeplacement(x, y);
            // test de mur et calculer la distance de case du Nord et point d'arrivée
            } else if (!id_escalade.getLabyrinthe().estMur(x, y - 1) && (distance > id_escalade.distance(x, y - 1))) {
                y = y - 1;
                id_escalade.getChemin().AddDeplacement(x, y);
            }

            // Test bloqué, si la distance ne change pas, le pion est bloqué
            if (distance == id_escalade.distance(x, y)) {
                bloque = true;
            } else {
                // Calcul de la distance
                distance = id_escalade.distance(x, y);
            }
            //Test solution
            solution = id_escalade.getLabyrinthe().estArrivee(x, y);

            // Pause toutes les 10 itérations
            pause(id_escalade, 10);
        }

        // Résultat
        System.out.println(" bloqué = " + bloque + " ; solution = " + solution);
    }

    /**
     * Resolution tabous
     */
    public void RechercheAvecTabous() {
        //Initialisation

        solution = false;
        boolean bloque = false;
        int x = 1; // initialisation la distance en axe x
        int y = 1; // initialisation la distance en axe y
        int x_dest = x; // une variable intermédiaire pour la distance en x
        int y_dest = y; // une variable interm¨¦diaire pour la distance en y
        // variable pour la distance de chaque direction.
        int distance_Est;
        int distance_Sud;
        int distance_Ouest;
        int distance_Nord;
        int distance = 100;


        // Boucle d'execution de l'algorithme pour chaque individu

        // Boucle de résolution, sortir de la boucle en cas de bloquage ou trvouer une solution
        while ((solution == false) && (bloque == false)) {
            x_dest = x;
            y_dest = y;
            distance = 100;
            if (!(id_tabous.getLabyrinthe().estMur(x + 1, y)) && !(id_tabous.getChemin().existeDeja(x + 1, y))) {
                distance_Est = id_tabous.distance(x + 1, y);
                if (distance_Est < distance) {
                    x_dest = x + 1;
                    y_dest = y;
                    distance = distance_Est;
                }
            } else if (!(id_tabous.getLabyrinthe().estMur(x, y + 1)) && !(id_tabous.getChemin().existeDeja(x, y + 1))) {
                distance_Sud = id_tabous.distance(x, y + 1);
                if (distance_Sud < distance) {
                    x_dest = x;
                    y_dest = y + 1;
                    distance = distance_Sud;
                }
                // si les distances sont les m¨ºme, nous choisions une direction aléatoirement, pareil pour les autres directions suivantes
                if (distance_Sud == distance) {
                    int a = (int) (round(Math.random(), 0));
                    if (a == 0) {
                        x_dest = x;
                        y_dest = y + 1;
                        distance = distance_Sud;
                    }
                }
            } else if (!(id_tabous.getLabyrinthe().estMur(x - 1, y)) && !(id_tabous.getChemin().existeDeja(x - 1, y))) {
                distance_Ouest = id_tabous.distance(x - 1, y);
                if (distance_Ouest < distance) {
                    x_dest = x - 1;
                    y_dest = y;
                    distance = distance_Ouest;
                }
                if (distance_Ouest == distance) {
                    int a = (int) (round(Math.random(), 0));
                    if (a == 0) {
                        x_dest = x - 1;
                        y_dest = y;
                        distance = distance_Ouest;
                    }
                }
            } else if (!(id_tabous.getLabyrinthe().estMur(x, y - 1)) && !(id_tabous.getChemin().existeDeja(x, y - 1))) {
                distance_Nord = id_tabous.distance(x, y - 1);
                if (distance_Nord < distance) {
                    x_dest = x;
                    y_dest = y - 1;
                    distance = distance_Nord;
                }
                if (distance_Nord == distance) {
                    int a = (int) (round(Math.random(), 0));
                    if (a == 0) {
                        x_dest = x;
                        y_dest = y - 1;
                        distance = distance_Nord;
                    }
                }
            }

            // Test bloque si la distance n a pas change
            if (distance == id_tabous.distance(x, y)) {
                bloque = true;
            }
            x = x_dest; // on mis met à jour la distance entre pion et point d'arrivée en axe x
            y = y_dest; // on mis met à jour la distance entre pion et point d'arrivée en axe y
            id_tabous.getChemin().AddDeplacement(x, y);

            //Test solution
            solution = id_tabous.getLabyrinthe().estArrivee(x, y);

            pause(id_tabous, 10); // Pause toutes les 10 itérations

        }
        // Résultat
        System.out.println(" bloque = " + bloque + " ; solution = " + solution);
    }

    /**
     * Resolution recuit simule
     */
    public void RecuitSimule() {
        fenLaby recuit_simule = new fenLaby();
    }

    /**
     * Resolution genetique
     */
    public void AlgorithmeGenetique() {
        /*******************************************************************
         *********************** DEFINITION DES VARIABLES *******************
         *******************************************************************/
        
        /******** Paramètres fenêtre ******** 
        - Nombre individu - divisible par 4 (nbIndividu)
        - Pondération de la fonction de score (alpha) : 0 = priorité sur nb-cases | 1 = priorité sur la distance
         ********* Algorithme ********
        - Méthode de sélection (selection)
        - Probabilité de mutation (prob_mutation)
        - Nombre max d'itérations (max_iterations)
        - Nombre d'itérations déjà effectuées (nb_iteration)
        - Fin de l'algorithme (passage)
        - Nombre d'individus virtuels dans le cas de la probabilité proportionnelle = selection 2 (etendue)
        - Individu_genetique temporaire pour le tri à bulle ou des changements de place (individu_temp)
        - Entier pour les probabilités (proba,proba2)
         */
        
        int selection = 1;
        double prob_mutation = 0.2;
        int max_iterations = 200;

        int nb_iteration = 0;
        boolean passage = false;
        int etendue;
        Individu_genetique individu_temp;
        int proba;
        int proba2;
        
        /*******************************************************************
         ************************** BOUCLES DE CALCUL ***********************
         *******************************************************************/
        
        // 1) Initialisation : on engendre aléatoirement une population Po comprenant N individus
        echantillon_genetique = new Echantillon_genetique(nbIndividu, alpha);
        
        // 2) Boucles d'itérations et traitement des données jusqu'à convergence ou arrêt
        do {
            
            // 2.1) Sélection : on sélectionne un sous-ensemble de la population de taille p tel que p = N/2
            switch (selection) {

                case 1: // Rang = tri sur la moitié de la pop (on ne retient que la moitié supérieur)
                    for (int i=0;i<nbIndividu/2;i++) {
                        for (int j=i+1;j<nbIndividu;j++) {
                            if (echantillon_genetique.getIndividu(j).score > echantillon_genetique.getIndividu(i).score) {
                                individu_temp = echantillon_genetique.getIndividu(j);
                                echantillon_genetique.setIndividu(j, echantillon_genetique.getIndividu(i));
                                echantillon_genetique.setIndividu(i, individu_temp);
                            }
                        }
                    }
                    break;

                case 2: // Tournoi = tri sur la pop. + proba pondérée + meilleur des deux         
                    // Tri par odre de score
                    for (int i=0;i<nbIndividu;i++) {
                        for (int j=i+1;j<nbIndividu;j++) {
                            if (echantillon_genetique.getIndividu(j).score > echantillon_genetique.getIndividu(i).score) {
                                individu_temp = echantillon_genetique.getIndividu(j);
                                echantillon_genetique.setIndividu(j, echantillon_genetique.getIndividu(i));
                                echantillon_genetique.setIndividu(i, individu_temp);
                            }
                        }
                    }

                    // Sélection (meilleur des deux probas pondérées)
                    for (int i=0;i<nbIndividu/2;i++) {
                        int candidats = nbIndividu-i;
                        etendue = ((candidats)*(candidats+1))/2;
                        proba = i+(int)(Math.random()*etendue);
                        proba2 = i+(int)(Math.random()*etendue);

                        // On retient le meilleur individu
                        if (proba2 < proba) {
                            proba = proba2;
                        }

                        int cellule = 0;
                        double total = 0;

                        // Recherde de l'individu concerné
                        do {
                            total = total + (candidats - cellule);
                            cellule++;
                        } while (total < proba);
                        
                        // Décalage pour ne pas reprendre deux fois le même
                        individu_temp = echantillon_genetique.getIndividu(cellule-1);
                        for (int j=cellule-1;j>i;j--) {
                            echantillon_genetique.setIndividu(j,echantillon_genetique.getIndividu(j-1));
                        }
                        // Enregistrement de l'individu à la position en cours (dans la première moitié)
                        echantillon_genetique.setIndividu(i,individu_temp);
                    }
                    break;
            }
            
            /* 2.2) Croisement : parmi ces p individus, on constitue p/2 couples qui seront alors croisés en un point du parcours déjà fait
             *  Individu : 
             *  - - - -( - - - - - -)
             *  | |      |
             *  0 1      croisement
             */
            for (int i=nbIndividu/2;i<nbIndividu;i+=2) {

                // Sélection des deux individus parents
                Individu_genetique individu1 = (Individu_genetique) echantillon_genetique.getIndividu((i-(nbIndividu/2))/2).clone();
                Individu_genetique individu2 = (Individu_genetique) echantillon_genetique.getIndividu(nbIndividu/4+(i-(nbIndividu/2))/2).clone();
                
                // Définition du point de croisement : position maximale sans retour sur soi ni butée sur un mur parmi les deux parents
                int croisement = Math.max(individu1.nb_cases, individu2.nb_cases);
                // Autre possibilité : int croisement = (int) (Math.random()*Math.max(individu1.nb_cases, individu2.nb_cases))+1;             

                // Croisement des directions
                for (int j=croisement-1;j<individu1.longueur;j++) {
                    int temp = individu1.individu[j];
                    individu1.individu[j] = individu2.individu[j];
                    individu2.individu[j] = temp;
                }           

                // 2.3) Mutation des enfants sur la partie du chemin valide
                if ((float)(Math.random())<prob_mutation) {
                    individu1.individu[(int)(Math.random()*(croisement))] = (int)(Math.random()*4);
                }
                if ((float)(Math.random())<prob_mutation) {
                    individu2.individu[(int)(Math.random()*(croisement))] = (int)(Math.random()*4);
                }
                
                // Traduction des nouveaux enfants en termes de coordonnées + evaluation
                individu1.chemin.couper(croisement);
                individu2.chemin.couper(croisement);
                individu1.traduction_combinaison(croisement-1);
                individu2.traduction_combinaison(croisement-1);
                individu1.collision();
                individu2.collision();
                individu1.existence();
                individu2.existence();
                individu1.fonction_score();
                individu2.fonction_score();

                // Enregistrement des enfants (deuxième moitié de la population)
                echantillon_genetique.setIndividu(i,individu1);
                echantillon_genetique.setIndividu(i+1,individu2);
            }
            
            // Informations
            individu_temp = (Individu_genetique) echantillon_genetique.afficher_meilleurIndividu().clone();
            System.out.println("\n******* Meilleur individu de la population *******");
            System.out.println("- Score : "+individu_temp.score);
            System.out.println("- Nombre bonnes cases : "+individu_temp.nb_cases);
            System.out.println("itération : "+nb_iteration);
            nb_iteration++;
            sendIteration();
            iteration++;
            
            // Affichage du meilleur individu sur le labyrinthe
            Chemin meilleur_chemin = individu_temp.chemin;
            meilleur_chemin.couper(individu_temp.nb_cases);
            getPan().getIndividu_afficher().getChemin().setDeplacement(meilleur_chemin.getDeplacement());
            repaint();
            
            // Vérification des conditions de fin
            if (nb_iteration == max_iterations) {
                passage = true;
            }else{
                if(individu_temp.solution){
                    passage = true;
                    System.out.println("Solution trouvée !!!");
                }
            }    
            
        } while (passage != true);       
    }
        
    

    /**
     * Resolution colonie de fourmis
     */
    public void AlgorithmeColonieFourmis() {
        echantillon_fourmis = new Echantillon(nbIndividu);      //initialisation du nombre d'individus
        iteration = 0;
        getPan().init_matrice();       //initialisation de la matrice de pheromones
        double add_ph = 0.2;
        int iteration_max = 100;       // nb d'itérations maxi
        double coeff = 0.0;            
        double evap = 0.99;            // initialisation du coefficient d'evaporation
        int x = 0;
        int y = 0;
        int[] dir;
        int[][] mem;

        while (iteration != iteration_max) {        // on fait tourner l'algorithme tant que le nb d'itération max n'est pas atteinte
            System.out.println(iteration);

            // Mouvement de la colonie et choix de la prochaine direction
            for (int i = 0; i < echantillon_fourmis.getNbIndividu(); i++) {        // l'algorithme tourne pour chaque fourmi
                x = echantillon_fourmis.getIndividu(i).getChemin().get_last_x();   // recuperation de la coordonnée x de la fourmi i 
                y = echantillon_fourmis.getIndividu(i).getChemin().get_last_y();   // recuperation de la coordonnée y de la fourmi i
                dir = fourmisDeplacer(x, y, i);
                if (dir[0] != x || dir[1] != y) {                                 // On teste si l'individu n'est pas dans un cul_de_sac
                                                                                  // (cul de sac si l'individu n'a pas bougé à la dernière itération)
                    
                    echantillon_fourmis.getIndividu(i).getChemin().AddDeplacement(dir[0], dir[1]); // si l'individu n'est pas dans un endroit considéré 
                                                                                                   // comme un cul-de-sac, on procède à son déplacement
                } else {
                    Individu ind = new Individu();                      // On réinitialise l'individu à la case départ si
                                                                        // il se retrouve dans un cul de sac
                    echantillon_fourmis.setInd(ind, i);
                }
                if (echantillon_fourmis.getIndividu(i).getLabyrinthe().estArrivee(dir[0], dir[1])) {  // si l'individu a trouvé la solution, on stocke le chemin qu'il a
                                                                                                      // parcouru et on le reinitialise à la case départ                              
                    mem = echantillon_fourmis.getIndividu(i).getChemin().getDeplacement();
                    for (int k = 0; k < mem.length; k++) {                                      // On remplit la matrice de phéromone avec le chemin en mémoire
                        coeff = getPan().get_pheromone(mem[k][0], mem[k][1]);                   
                        if ((coeff + add_ph) < 1.0) {
                            getPan().set_pheromone(mem[k][0], mem[k][1], coeff + add_ph);
                        }

                    }

                    Individu ind = new Individu();  //réinitialisation de l'individu à la case départ
                    echantillon_fourmis.setInd(ind, i);
                }
                for (int n = 0; n <= 14; n++) {             
                    for (int o = 0; o <= 14; o++) {
                        getPan().set_pheromone(n, o, getPan().get_pheromone(n, o) * evap);  // Evaporation de la matrice de phéromone suivant le coefficient d'évaporation "evap"
                    }
                }
                getPan().setIndividu_afficher(echantillon_fourmis.getIndividu(i));
                repaint();
                // Attente pour la visualisation
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            sendIteration();
            iteration++;
        }

        // Choix de la meilleure fourmis en cours
        int position = 0;
        for (int l = 0; l < nbIndividu; l++) {
            double score = 0.0;
            if (doScore(echantillon_fourmis.getIndividu(l)) > score) {
                score = doScore(echantillon_fourmis.getIndividu(l));
                position = l;
            }
        }
        // Affichage de la fourmis
        getPan().setIndividu_afficher(echantillon_fourmis.getIndividu(position));
        repaint();
    }

    public int[] fourmisDeplacer(int x, int y, int i) {  //méthode permettant de faire le choix de la case de destination suivante
        int[] dir = new int[2];
        int x_dest = x;
        int y_dest = y;
        int a = 0;
        double pheromone = -1.0;

        if (!(echantillon_fourmis.getIndividu(i).getLabyrinthe().estMur(x + 1, y)) && !(echantillon_fourmis.getIndividu(i).getChemin().existeDeja(x + 1, y))) { 
            // On teste la case à droite de la position actuelle pour savoir si c'est un mur et si l'individu y est deja passé
            // si le test est négatif, on regarde le niveau de phéromone en présence
            
            //x_dest = x + 1;
            //y_dest = y;  
            //pheromone = getPan().get_pheromone(x + 1, y); 
        if (getPan().get_pheromone(x+1, y) > pheromone) {
                x_dest = x+1;
                y_dest = y;  //si le niveau de pheromone est supérieur à celui de la case actuelle, 
                             //on stocke ses coordonnées comme destination suivante
                pheromone = getPan().get_pheromone(x+1,y); // on utilise le niveau de phéromone de destination comme nouvelle référence
        }
            if (getPan().get_pheromone(x+1, y) == pheromone) { // si le niveau de phéromone est égal a celui de la position actuelle, on a 
                                                                 // une chance sur deux de choisir la case supérieure comme destination suivante
                a = (int) (round(Math.random(), 0));
                if (a == 0) {
                    x_dest = x+1;
                    y_dest = y;

                }
            }    
        }
        if (!(echantillon_fourmis.getIndividu(i).getLabyrinthe().estMur(x, y + 1)) && !(echantillon_fourmis.getIndividu(i).getChemin().existeDeja(x, y + 1))) {
            // On teste la case supérieure à la position actuelle pour savoir si c'est un mur et si l'individu y est deja passé
            // si le test est négatif, on regarde le niveau de phéromone en présence
            if (getPan().get_pheromone(x, y + 1) > pheromone) {
                x_dest = x;
                y_dest = y + 1;  //si le niveau de pheromone est supérieur à celui de la case actuelle, 
                                 //on stocke ses coordonnées comme destination suivante
                pheromone = getPan().get_pheromone(x, y + 1); // on utilise le niveau de phéromone de destination comme nouvelle référence

            }
            if (getPan().get_pheromone(x, y + 1) == pheromone) { // si le niveau de phéromone est égal a celui de la position actuelle, on a 
                                                                 // une chance sur deux de choisir la case supérieure comme destination suivante
                a = (int) (round(Math.random(), 0));
                if (a == 0) {
                    x_dest = x;
                    y_dest = y + 1;

                }
            }
        }
        if (!(echantillon_fourmis.getIndividu(i).getLabyrinthe().estMur(x - 1, y)) && !(echantillon_fourmis.getIndividu(i).getChemin().existeDeja(x - 1, y))) {
            // On teste la case à droite à la position actuelle pour savoir si c'est un mur et si l'individu y est deja passé
            // si le test est négatif, on regarde le niveau de phéromone en présence
            if (getPan().get_pheromone(x - 1, y) > pheromone) {
                x_dest = x - 1;
                y_dest = y;//si le niveau de pheromone est supérieur à celui de la case actuelle, 
                             //on stocke ses coordonnées comme destination suivante
                pheromone = getPan().get_pheromone(x - 1, y);// on utilise le niveau de phéromone de destination comme nouvelle référence

            }
            if (getPan().get_pheromone(x - 1, y) == pheromone) {// si le niveau de phéromone est égal a celui de la position actuelle, on a 
                                                                 // une chance sur deux de choisir la case supérieure comme destination suivante
                a = (int) (round(Math.random(), 0));
                if (a == 0) {
                    x_dest = x - 1;
                    y_dest = y;

                }
            }
        }
        if (!(echantillon_fourmis.getIndividu(i).getLabyrinthe().estMur(x, y - 1)) && !(echantillon_fourmis.getIndividu(i).getChemin().existeDeja(x, y - 1))) {
             // On teste la case inférieure à la position actuelle pour savoir si c'est un mur et si l'individu y est deja passé
            if (getPan().get_pheromone(x, y - 1) > pheromone) {
                x_dest = x;
                y_dest = y - 1;  //si le niveau de pheromone est supérieur à celui de la case actuelle, 
                             //on stocke ses coordonnées comme destination suivante
                pheromone = getPan().get_pheromone(x, y - 1);// on utilise le niveau de phéromone de destination comme nouvelle référence

            }
            if (getPan().get_pheromone(x, y - 1) == pheromone) {// si le niveau de phéromone est égal a celui de la position actuelle, on a 
                                                                 // une chance sur deux de choisir la case supérieure comme destination suivante
                a = (int) (round(Math.random(), 0));
                if (a == 0) {
                    x_dest = x;
                    y_dest = y - 1;

                }
            }
        }

        dir[0] = x_dest;
        dir[1] = y_dest;
        return dir;
    }

    /**
     * Resolution logique de proposition
     */
    public void LogiqueDeProposition() {
        LabyrintheProposition p = LabyrintheProposition.laby4();
        //System.out.println(p);
        //System.out.println(p.formatClauses());
        int[] sol = p.premiereSol();
        int nb = 0;
        int[] sol1 = new int[0];
        for (int i = 0; i < sol.length; i++) {
            if (sol[i] > 0) {
                nb++;
                int[] soltemp = new int[nb];
                soltemp[nb - 1] = sol[i];

                for (int k = 0; k < sol1.length; k++) {
                    soltemp[k] = sol1[k];
                }
                sol1 = new int[nb];
                for (int k = 0; k < sol1.length; k++) {
                    sol1[k] = soltemp[k];
                }
            }
        }


//        for(int i=0;i<sol.length;i++){
//            System.out.println(sol[i]);
//        }

        for (int i = 0; i < sol1.length; i++) {
            System.out.println(sol1[i] + "  decomposition : " + p.formatVar(sol1[i]));
        }
        //System.out.println(p.formatSol(sol));

        //System.out.println(p.affichePremiedecodeSolutions(3));

        int[][] dep = new int[0][0];
        ind_propo.getChemin().setDeplacement(dep);
        for (int i = 0; i < sol1.length; i++) {
            int[] decode = p.decomposeVar(sol1[i]);
            ind_propo.getChemin().AddDeplacement(decode[1], decode[0]);
        }
    }

    /**
     * Resolution logique de predicats
     */
    public void LogiqueDePredicats() {
        // TODO Auto-generated method stub
    }
}
