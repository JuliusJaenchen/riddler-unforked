package hwr.oop.riddler;

import hwr.oop.riddler.io.FileConverter;
import hwr.oop.riddler.logic.SudokuValidator;
import hwr.oop.riddler.model.Sudoku;
import hwr.oop.riddler.model.solver.BacktrackingSolver;
import hwr.oop.riddler.model.solver.IterativeSudokuSolver;
import hwr.oop.riddler.model.solver.ObviousSolver;
import hwr.oop.riddler.model.solver.SimpleReducePossiblesSolver;

import java.util.List;

public class Riddler {
    public static void main(String[] args) throws IllegalArgumentException {
        var converter = new FileConverter();

        if (args.length != 1) {
            throw new IllegalArgumentException("USAGE: java Riddler [filepath]");
        }
        String filepath = args[args.length - 1];

        long start = System.currentTimeMillis();

        for (int i = 1; i < 20; i++) {
            var sudoku = new Sudoku(converter.parseInputFile("txt/sudoku." + i + ".txt"));
            List<IterativeSudokuSolver> solverList = List.of(new SimpleReducePossiblesSolver(), new ObviousSolver(), new BacktrackingSolver());
            solve(sudoku, solverList);
            if (!new SudokuValidator().isValid(sudoku)) {
                throw new IllegalArgumentException("Awgoksegise");
            }
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    private static void solve(Sudoku sudoku, List<IterativeSudokuSolver> solverList) {
        int solvingIndex = 0;
        while (solvingIndex < solverList.size()) {
            //System.out.println("step " + solvingIndex);
            try {
                while (solverList.get(solvingIndex).doSolvingStep(sudoku)) {
                    //    System.out.println("step succeed");
                    solvingIndex = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error ");
                sudoku.print();
                System.exit(0);
            }

            solvingIndex++;

        }
        //sudoku.print();
    }
}
