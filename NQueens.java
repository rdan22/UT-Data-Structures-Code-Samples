import java.util.ArrayList;
import java.util.Scanner;

public class NQueens {

    private static Scanner keyboard = new Scanner(System.in);
    private static int count = 0;

    /**
     * @param args
     */
    public static void main(String[] args) {
        //        solveNQueens(8);
        //        System.out.println("checked " + count + " boards");
//        for(int size = 1; size < 30; size++) {
//            solveNQueens(size);
//            System.out.println();
//            System.out.println("boards checked: " + count);
//        }

        ArrayList<ChessBoard> sols = getAllNQueens(8);

        for(ChessBoard b : sols){
            System.out.println(b);
            System.out.println();
        }
        System.out.println(sols.size() + " solutions found"); 
        System.out.println(count + " boards checked."); 
    }



    public static ArrayList<ChessBoard> getAllNQueens(int size){
        count = 0;
        ArrayList<ChessBoard> result = new ArrayList<>();
        ChessBoard board = new ChessBoard(size);
        findAllSolutionsToNQueensProblem(board, 0, result);
        return result;
    }

    public static void findAllSolutionsToNQueensProblem(
            ChessBoard board, 
            final int col, 
            ArrayList<ChessBoard> solutions) {

        if (col == board.size()) {
            solutions.add(board.getCopy());
        } else {
            for (int r = 0; r < board.size(); r++) {
                board.placeQueen(r, col);
                if (board.queensAreSafe()) {
                    findAllSolutionsToNQueensProblem(board, col + 1, solutions);
                }
                board.pickUpQueen(r, col);
            }
        }


    }

    public static void solveNQueens(int n){
        count = 0;
        ChessBoard board = new ChessBoard(n);
        Stopwatch st = new Stopwatch();
        st.start();
        boolean solved = canSolve(board, 0); // start at column 0, first column
        // boolean solved = canSolveWithDebugging(board, 0); // start at column 0, first column
        st.stop();
        System.out.println("Time to solve " + n + " queens: " + st);
        if(solved) {
            System.out.println("solution to " + n + " queens");
            System.out.println(board);
        } else { 
            System.out.println("no solution to the " + n + " queens problemns");
        }
    } 

    // No debugging
    private static boolean canSolve(ChessBoard board, final int COLUMN) {
        count++;
        if (COLUMN == board.size()) {
            // based on our approach, there are queens safely
            // placed in columns 0 through size() - 1. 
            return true;
        }
        // haven't solved
        for (int row = 0; row < board.size(); row++) {
            board.placeQueen(row, COLUMN);
            if (board.queensAreSafe()) {
                // okay so far, let's go on to the next column
                boolean solved = canSolve(board, COLUMN + 1); 
                if (solved) {
                    return true;
                }
            }
            // either queens was safe NOW or turned out to cause
            // problems later
            board.pickUpQueen(row, COLUMN);
        }
        // none of the choices worked, tell the version of the 
        // problem previous to me, they need to try a different
        return false;
    }

    private static boolean canSolveWithDebugging(ChessBoard board,
            int column) {
        count++;
        if(column == board.size()) {
            //There must be queens in columns 0 through size() - 1
            System.out.println("queen safe in each column, returning true");
            keyboard.nextLine(); // pause
            return true;
        }

        // else, not done. Choices are rows of current column
        for(int row = 0; row < board.size(); row++) {
            board.placeQueen(row, column);
            if(board.queensAreSafe()) {
                System.out.println("placed queen at " + row + " " + column + " going on to next column");
                System.out.println(board);
                System.out.println();
                int nextColumn = column + 1;
                boolean solved = canSolveWithDebugging(board, nextColumn);
                if (solved) {
                    System.out.println("We got back true!!! for " + row + " " + column + " return true as well");
                    return true;
                }
                System.out.println("We got back false. for " + row + " " + column + ", pick up queen and try next row in this column");
                keyboard.nextLine(); // pause
            }
            // I know either queens weren't safe or I got back false, in either case pick up this queen
            board.pickUpQueen(row, column);      
        }

        System.out.println("We tried all rows in column " + column + ", none worked, returning false.");
        keyboard.nextLine(); // pause

        return false;
    }

}