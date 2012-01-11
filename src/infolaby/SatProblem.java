package infolaby;
/*
Copyright 2000-2011 Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

/**
 * description d'un problème de logique des proposition sous forme d'un
 * ensemble (liste) de clauses
 * @author François de Beuvron (INSA Strasbourg)
 */
public class SatProblem {

    /**
     * liste des clauses. chaque clause est un tableau d'entier. chaque litéral
     * dans la clause est soit une variable (un entier de 1 à N) soit la négation
     * d'une variable représentée par l'opposé du numéro de la variable (en entier
     * négatif entre -N et -1)
     */
    private int nbrVar;
    protected List<int[]> clauses;

    /**
     * donne une representation textuelle de la solution adaptée au probleme
     * Cette methode sera souvent redéfinie dans les sous-classes, car l'affichage
     * direct des literaux est en général peut compréhensible
     * @param uneSol
     * @return 
     */
    public String formatSol(int[] uneSol) {
        StringBuilder res = new StringBuilder();
        res.append("{sol : ");
        for (int i = 0 ; i < uneSol.length ; i ++) {
            res.append(this.formatLit(uneSol[i]));
            if (i != uneSol.length) {
                res.append(" , ");
            }
        }
        res.append(" }");
        return res.toString();
    }
    
  

    /**
     * donne une representation textuelle d'une variable adaptée au probleme
     * Cette methode sera souvent redéfinie dans les sous-classes, car l'affichage
     * direct des variable est en général peut compréhensible
     */
    public String formatVar(int numVar) {
        return "" + numVar;
    }
    
    public String formatLit(int numLit) {
        if (numLit > 0) {
            return this.formatVar(numLit);
        } else {
            return "-" + this.formatVar(-numLit);
        }
    }

    protected SatProblem() {
    }

    public SatProblem(int nbrVar) {
        this(nbrVar, new ArrayList<int[]>());
    }

    public SatProblem(int nbrVar, List<int[]> clauses) {
        this.nbrVar = nbrVar;
        this.clauses = clauses;
    }

    public String toString() {
        return "[" + this.getClass().getSimpleName() + " : " + this.nbrVar
                + " variables ; " + this.clauses.size() + " clauses]";
    }

    /**
     * tente de resoudre un probleme sous forme de clauses de logique des proposition
     * @return une solution, ou null si le probleme n'a pas de solution
     */
    public int[] premiereSol() {
        List<int[]> sols = this.premieresSols(1);
        if (sols.isEmpty()) {
            return null;
        } else {
            return sols.get(0);
        }

    }

    /**
     * tente de resoudre un probleme sous forme de clauses de logique des proposition
     * @return la liste de toutes les solutions (!! potentiellement très grand
     * utilisez plutot premiereSol ou premieresSols ou reconstruisez vous-même la boucle si vous n'êtes
     * pas interressés par chacune des solutions possibles)
     */
    public List<int[]> allSols() {
        return this.premieresSols(-1);

    }

    /**
     * tente de resoudre un probleme sous forme de clauses de logique des proposition
     * @param nbrMax : nombre maximum de solutions souhaitées (-1 ==> pas de limite)
     * @return la liste des nbrMax premieres solutions (ou moins evidemment si le 
     * probleme à moins de nbrMax sols)
     */
    public List<int[]> premieresSols(int nbrMax) {

        try {
            ISolver solver = SolverFactory.newDefault();

// prepare the solver to accept MAXVAR variables. MANDATORY
            solver.newVar(this.getNbrVar());
// not mandatory for SAT solving. MANDATORY for MAXSAT solving
            solver.setExpectedNumberOfClauses(this.getClauses().size());
// Feed the solver using Dimacs format, using arrays of int
// (best option to avoid dependencies on SAT4J IVecInt)
            for (int i = 0; i < this.getClauses().size(); i++) {
                // the clause should not contain a 0, only integer (positive or negative)
                // with absolute values less or equal to MAXVAR
                // e.g. int [] clause = {1, -3, 7}; is fine
                // adapt Array to IVecInt
                solver.addClause(new VecInt(this.getClauses().get(i))); // adapt Array to IVecInt
            }
            List<int[]> res = new ArrayList<int[]>();
            ModelIterator mi = new ModelIterator(solver);
            int nbrSol = 0;
            while ((nbrMax == -1 || nbrSol < nbrMax) && mi.isSatisfiable()) {
                int[] model = mi.model();
                res.add(model);
                nbrSol++;
            }
            return res;
        } catch (TimeoutException ex) {
            throw new MiniSatError(ex);
        } catch (ContradictionException ex) {
            return new ArrayList<int[]>();
        }
    }

    public static String matAffich(int[][] mat) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                res.append(mat[i][j]);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }

    public String formatClauses() {
        StringBuilder res = new StringBuilder();
        res.append("{");
        res.append(this.clauses.size());
        res.append(" clauses\n");
        for (int i = 0; i < this.clauses.size(); i++) {
            res.append("{");
            int[] clause = this.clauses.get(i);
            for (int j = 0; j < clause.length; j++) {
                res.append(this.formatLit(clause[j]));
                if (j != clause.length - 1) {
                    res.append(" , ");
                }
            }
            res.append("}\n");
        }
        res.append("}\n");
        return res.toString();
    }

    public String affichePremieresSolutions(int nbrMax) {
        List<int[]> sols = this.premieresSols(nbrMax);
        StringBuilder res = new StringBuilder();
        res.append("========== liste des ");
        res.append(nbrMax);
        res.append(" premieres solutions ==========\n");
        if (sols.isEmpty()) {
            res.append("!!! AUCUNE SOLUTION !!!");
        } else {
            if (nbrMax > sols.size()) {
                res.append("!!! seulement ");
                res.append(sols.size());
                res.append(" solutions possibles\n");
            }
            for (int i = 0; i < sols.size(); i++) {
                res.append("---------- sol N° ");
                res.append(i + 1);
                res.append("\n");
                res.append(this.formatSol(sols.get(i)));
                res.append("\n");
            }
        }
        return res.toString();
    }

    public void initWithDimacs(Reader rin) {
        BufferedReader bin = new BufferedReader(rin);
        int numline = 0;
        try {
            this.clauses = new ArrayList<int[]>();
            String line = bin.readLine();
            numline++;
            while (line != null) {
                line = line.trim();
                if (line.length() > 0) {
                    line = line.replaceAll(" +", " ");
                    /*DEBUG*///System.out.println("line : " + line);
                    String[] decomp = line.split(" ");
                    if (decomp[0].toUpperCase().equals("C")) {
                        // comment, do nothing
                    } else if (decomp[0].equals("P")) {
                        if (!decomp[1].toUpperCase().equals("CNF")) {
                            throw new SATParseError("only cnf allowed (line " + numline + ")");
                        }
                        this.setNbrVar(Integer.parseInt(decomp[2]));
                    } else {
                        boolean isNum = false;
                        try {
                            int numeric = Integer.parseInt(decomp[0]);
                            isNum = true;
                        } catch (NumberFormatException ex) {
                        }
                        if (isNum) {
                            int zeroLast = Integer.parseInt(decomp[decomp.length - 1]);
                            if (zeroLast != 0) {
                                throw new SATParseError("clause should be zero ended (line " + numline + ")");
                            }
                            if (decomp.length == 1) {
                                break;
                            }
                            int[] c = new int[decomp.length - 1];
                            for (int i = 0; i < decomp.length - 1; i++) {
                                c[i] = Integer.parseInt(decomp[i]);
                            }
                            this.clauses.add(c);

                        } else {
                            throw new SATParseError("invalid line (line " + numline + ") : " + line);
                        }

                    }
                }
                line = bin.readLine();
                numline++;
            }
        } catch (Exception ex) {
            throw new SATParseError("in line " + numline, ex);
        }
    }

    public void writeDimacs(Writer w) {
        try {
            w.write("p cnf " + this.getNbrVar() + " " + this.clauses.size() + "\n");
            for (int i = 0; i < this.clauses.size(); i++) {
                int[] clause = this.clauses.get(i);
                for (int j = 0; j < clause.length; j++) {
                    w.write("" + clause[j]);
                    w.write(" ");
                }
                w.write("\n");
            }
        } catch (Exception ex) {
            throw new SATWriteError(ex);
        }
    }

    /**
     * @return the nbrVar
     */
    public int getNbrVar() {
        return nbrVar;
    }

    /**
     * @param nbrVar the nbrVar to set
     */
    public void setNbrVar(int nbrVar) {
        this.nbrVar = nbrVar;
    }

    public boolean addClause(int[] clause) {
        return this.clauses.add(clause);
    }

    /**
     * @return the clauses
     */
    public List<int[]> getClauses() {
        return clauses;
    }

    /**
     * @param clauses the clauses to set
     */
    public void setClauses(List<int[]> clauses) {
        this.clauses = clauses;
    }
}
