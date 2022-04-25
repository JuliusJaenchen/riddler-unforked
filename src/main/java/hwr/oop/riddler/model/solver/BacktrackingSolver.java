package hwr.oop.riddler.model.solver;

import hwr.oop.riddler.logic.SudokuValidator;
import hwr.oop.riddler.model.Sudoku;
import hwr.oop.riddler.model.component.Cell;

import java.util.ArrayDeque;
import java.util.Deque;

public class BacktrackingSolver implements IterativeSudokuSolver {

    private final Deque<Cell> backtrackingStack = new ArrayDeque<>();

    @Override
    public boolean doSolvingStep(Sudoku sudoku) {
        Cell cell;
        while ((cell = getNextUnsolved(backtrackingStack.size(), sudoku)) != null) {
            cell.setAssumedValue(cell.getAssumedPossibles().get(0));

            if (new SudokuValidator().isValid(sudoku)) {
                backtrackingStack.push(cell);
            } else {
                // System.out.println("tried " + cell.getPossibles().get(0) + " but lead to invalid sudoku");

                cell.removeAssumedPossible(cell.getAssumedValue());

                while (cell.getAssumedPossibles().isEmpty() && !backtrackingStack.isEmpty()) {
                    cell.resetAssumptions();
                    cell = backtrackingStack.pop();
                    cell.removeAssumedPossible(cell.getAssumedValue());
                }

                if (backtrackingStack.isEmpty()) {
                    cell.removePossible(cell.getAssumedValue());
                    cell.resetAssumptions();
                    //System.out.println("back: removed possible");
                    return true;
                }
                cell.resetAssumedValue();
            }
        }
        return false;
    }

    private Cell getNextUnsolved(int index, Sudoku sudoku) {
        var cells = sudoku.getAllCells();
        for (int i = index; i < cells.size(); i++) {
            var cell = cells.get(i);
            if (!cell.isSet()) return cell;
        }
        return null;
    }
}
