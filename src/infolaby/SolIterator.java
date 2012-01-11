package infolaby;
/*
 *     Copyright 2000-2011 Francois de Bertrand de Beuvron
 * 
 *     This file is part of CoursBeuvron.
 * 
 *     CoursBeuvron is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     CoursBeuvron is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.util.Iterator;
import java.util.NoSuchElementException;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

/**
 *
 * @author francois
 */
public class SolIterator implements Iterator<int[]> {
         boolean plusDeSol = false;
        ModelIterator iter;

        public SolIterator(SatProblem p) {
            try {
                ISolver solver = SolverFactory.newDefault();

// prepare the solver to accept MAXVAR variables. MANDATORY
                solver.newVar(p.getNbrVar());
// not mandatory for SAT solving. MANDATORY for MAXSAT solving
                solver.setExpectedNumberOfClauses(p.getClauses().size());
// Feed the solver using Dimacs format, using arrays of int
// (best option to avoid dependencies on SAT4J IVecInt)
                for (int i = 0; i < p.getClauses().size(); i++) {
                    // the clause should not contain a 0, only integer (positive or negative)
                    // with absolute values less or equal to MAXVAR
                    // e.g. int [] clause = {1, -3, 7}; is fine
                    // adapt Array to IVecInt
                    solver.addClause(new VecInt(p.getClauses().get(i))); // adapt Array to IVecInt
                }
                this.iter = new ModelIterator(solver);
            } catch (ContradictionException ex) {
                this.plusDeSol = true;
            }

        }

        @Override
        public boolean hasNext() {
            try {
                if (!this.plusDeSol) {
                    this.plusDeSol = !this.iter.isSatisfiable();
                }
                return !this.plusDeSol;
            } catch (TimeoutException ex) {
                throw new MiniSatError(ex);
            }
        }

        @Override
        public int[] next() {
            if (this.plusDeSol) {
                throw new NoSuchElementException();
            } else {
                return this.iter.model();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }  
}
