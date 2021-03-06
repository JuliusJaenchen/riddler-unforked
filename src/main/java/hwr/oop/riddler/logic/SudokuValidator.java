package hwr.oop.riddler.logic;

import hwr.oop.riddler.model.Sudoku;
import hwr.oop.riddler.model.component.Cell;

import java.util.HashSet;
import java.util.Set;

public class SudokuValidator {
    private Sudoku sudoku;

    public boolean isValid(Sudoku sudoku) {
        this.sudoku = sudoku;
        return rowsAreValid() && columnsAreValid() && boxesAreValid();
    }

    private boolean rowsAreValid(){
        for (Cell[] row : sudoku.getRows()) {
            if (hasDuplicate(row)) {
                return false;
            }
        }
        return true;
    }

    private boolean columnsAreValid() {
        for (Cell[] column : sudoku.getColumns()) {
            if (hasDuplicate(column)) {
                return false;
            }
        }
        return true;
    }

    private boolean boxesAreValid() {
        for (Cell[] box : sudoku.getBoxes()) {
            if (hasDuplicate(box)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDuplicate(Cell[] testSubject) {
        Set<Integer> encounteredValues = new HashSet<>();
        for (Cell cell : testSubject) {
            if (encounteredValues.contains(cell.value)) {
                return true;
            }
            encounteredValues.add(cell.value);
        }
        return false;
    }
}
