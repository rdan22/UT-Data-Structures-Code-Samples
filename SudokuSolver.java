/*
Use recursive backtracking to solve a Sudoku problem.
*/
import java.util.Arrays;

public class SudokuSolver {
    
    private static final int BOARD_SIZE = 9;
    private static final int MINI_SIZE = (int) Math.sqrt(BOARD_SIZE);
    
    /** 
     * Find a solution to a sudoku puzzle. 
     * <br>pre: board != null, board is 9 by 9. 
     * <br>post: return a board that is the solved puzzle 
     * or a copy of the original board if there is no solution
     * @param startBoard The starting board. 
     * Empty values = 0, given values = 1 through 9 may not be changed
     */
    public static int[][] getSudokuSolution(int[][] startBoard) {
        if (startBoard == null || startBoard.length != BOARD_SIZE 
                || startBoard[0].length != BOARD_SIZE)
            throw new IllegalArgumentException("Violation of precondition in getSudo");

        // store solution in board so we don't change startBoard
        int[][] board = copyBoard(startBoard); 
        // calls is used for diagnostics to see how many times we
        // call solveSudoku when trying to solve
        int[] calls = {0};
        solveSudoku(0, 0, board, calls);
        System.out.println();
        System.out.println("Number of calls to solveSudoku for this board: " + calls[0]);
        return board;
    }

    // recursive backtracking method to solve sudoku
    // We start at the cell at row 0, column 0 and move
    // in row major order through the cells
    private static boolean solveSudoku(int row, int col, int[][] board, int[] calls) {
        calls[0]++;
        if (row == board.length) {
            // We have placed digits 1-9 in all cells
            // with no conflicts. 
            return true;
        }
        // determine location of the next cell
        int nextCol = (col == board.length - 1)  ? 0 : col + 1;
        int nextRow = (nextCol == 0) ? row + 1 : row;
        if (board[row][col] != 0) {
            // bummer, no options, go on to the next cell
            return solveSudoku(nextRow, nextCol, board, calls);
        }
        // We have OPTIONS!!!!!!
        for (int choice = 1; choice <= board.length; choice++) {
            // make the current choice
            board[row][col] = choice; // go through the door
            if (digitsOkay(row, col, board)) {
                // return solvesudoku(nextRow, nextCol, board); // Exam 2, -7 
                boolean solved = solveSudoku(nextRow, nextCol, board, calls);
                if (solved) {
                    // what does that mean, choice was the RIGHT CHOICE
                    // HOORAY!!!!, pop, pop
                    return true;
                }                
            } // end of if(digitsOkay)
            // either bad choice now or turned out to be bad later
            // undo choice (the loop will overwrite old choice with new choice
        } // end of for loop
        // none of our choices worked
        board[row][col] = 0;
        return false;
    }

    // helper method to make a copy of a sudoku board.
    private static int[][] copyBoard(int[][] startBoard) {
        int[][] result = new int[startBoard.length][];
        for (int r = 0; r < result.length; r++) {
            result[r] = Arrays.copyOf(startBoard[r], startBoard[r].length);
        
        }
        return result;
    }



    // Helper method check to ensure no digit other than zero
    // is repeated in a given row, column, or mini matrix.
    private static boolean digitsOkay(int row, int col, int[][] board) {
        return portionOkay(row, 0, 0, 1, board) &&
        portionOkay(0, col, 1, 0, board) &&
        miniMatrixOkay(row, col, board);

    }



    // helper method to see if no repeat digits (other than 0) in a row or column
    private static boolean portionOkay(int rowStart, int colStart, int rowChange, int colChange, int[][] board) {
        // check digits in row or column.
        // for all non zero digits update array of booleans. if digit appears twice then
        // used[digit] is set to true first time and we return false second time
        boolean[] used = new boolean[BOARD_SIZE + 1]; // don't use zero element in array
        for (int i = 0, row = rowStart, col = colStart; i < BOARD_SIZE; i++, row += rowChange, col += colChange) {
            int digit = board[row][col];
            if( digit != 0) {
                if (used[digit]) {
                    return false; // duplicate!!
                }
                used[digit] = true; // mark as used
            }
        }
        return true; // no repeats found!
    }

    // helper to check that no digits other than 0 are
    // repeated in the mini matric cell row, col
    // is a part of.
    private static boolean miniMatrixOkay(int row,
            int col, int[][] board) {

        boolean[] used = new boolean[BOARD_SIZE + 1];

        // figure out upper left indices for mini matrix
        // we need to check

        // row 0,1,2 -> 0, row 3, 4, 5 -> 3, row 6, 7, 8 -> 6
        // same logic for column
        row = (row / MINI_SIZE) * MINI_SIZE;
        col = (col / MINI_SIZE) * MINI_SIZE;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++)  {
                int digit = board[row + r][col + c];
                if (digit != 0) {
                    if (used[digit]) {
                        return false; // duplicate!!
                    }
                    used[digit] = true; // mark as used
                }
            }
        }
        return true;
    }
    
    public static void tests() {
        String puzzle1 = "530070000600195000098000060800060003400803001700020006060000280000419005000080079";
        int[][] board = stringToBoard(puzzle1);
        int[][] result = getSudokuSolution(board);
        String actualBoard = boardToString(result);
        System.out.println("Initial board");
        printBoard(board);
        System.out.println();
        System.out.println("Solution");
        printBoard(result);
        System.out.println();
        String expectedBoard = "534678912672195348198342567859761423426853791713924856961537284287419635345286179";
        if(actualBoard.equals(expectedBoard))
            System.out.println( "Test 22 passed. sudoku solveRecursive.");
        else {
            System.out.println("Test 22 failed. sudoku solver:");
            System.out.println("Expected board:");
            printBoard(stringToBoard(expectedBoard));
            System.out.println();
            System.out.println("Actual board:");
            printBoard(result);
        }


        String puzzle2 = "370002060000000050008073900147000000009301200000000891005120600010000000080600043";
        board = stringToBoard(puzzle2);
        result = getSudokuSolution(board);
        actualBoard = boardToString(result);
        expectedBoard = "371592468294816357568473912147289536859361274623745891735124689416938725982657143";
        if(actualBoard.equals(expectedBoard))
            System.out.println( "Test 23 passed. sudoku solveRecursive.");
        else {
            System.out.println("Test 23 failed. sudoku solver:");
            System.out.println("Expected board:");
            printBoard(stringToBoard(expectedBoard));
            System.out.println();
            System.out.println("Actual board:");
            printBoard(result);
        }


        String puzzle3 = "000010300008003500500002001003000007106070403400000200200300004004900600007080000";
        assert puzzle3.length() == 81;
        board = stringToBoard(puzzle3);
        result = getSudokuSolution(board);
        actualBoard = boardToString(result);
        System.out.println("Initial Board");
        printBoard(board);
        expectedBoard = "642715389718493526539862741823546917196278453475139268261357894384921675957684132";
        if(actualBoard.equals(expectedBoard))
            System.out.println( "Test 24 passed. sudoku solveRecursive.");
        else {
            System.out.println("Test 24 failed. sudoku solver:");
            System.out.println("Expected board:");
            printBoard(stringToBoard(expectedBoard));
            System.out.println();
            System.out.println("Actual board:");
            printBoard(result);
        }


        String puzzle4 = "080700500700008021000002009800100206690824015105009008500400000430200007009007080";
        assert puzzle4.length() == 81;
        board = stringToBoard(puzzle4);
        result = getSudokuSolution(board);
        actualBoard = boardToString(result);
        expectedBoard = "982716534754938621361542879843175296697824315125369748578493162436281957219657483";
        if(actualBoard.equals(expectedBoard))
            System.out.println( "Test 25 passed. sudoku solveRecursive.");
        else {
            System.out.println("Test 25 failed. sudoku solver:");
            System.out.println("Expected board:");
            printBoard(stringToBoard(expectedBoard));
            System.out.println();
            System.out.println("Actual board:");
            printBoard(result);
        }


        String puzzle5 = "000050400080102350400307086050000670100809002038000040590203004047906020003070000";
        assert puzzle5.length() == 81;
        board = stringToBoard(puzzle5);
        result = getSudokuSolution(board);
        actualBoard = boardToString(result);
        expectedBoard = "000050400080102350400307086050000670100809002038000040590203004047906020003070000";
        if (actualBoard.equals(expectedBoard)) {
            System.out.println( "Test 26 passed. sudoku solveRecursive.");
        } else {
            System.out.println("Test 26 failed. sudoku solver:");
            System.out.println("Expected board:");
            printBoard(stringToBoard(expectedBoard));
            System.out.println();
            System.out.println("Actual board:");
            printBoard(result);
        }
    }
    
    private static String boardToString(int[][] board) {
        final int NUM_DIGITS_SUDOKU_BOARD = BOARD_SIZE * BOARD_SIZE;
        StringBuilder result = new StringBuilder(NUM_DIGITS_SUDOKU_BOARD);
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                result.append(board[r][c]);
            }
        }
        return result.toString();
    }

    private static int[][] stringToBoard(String puzzle) {
        int[][] result = new int[BOARD_SIZE][BOARD_SIZE];
        int index = 0;
        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result.length; c++, index++) {
                result[r][c] = puzzle.charAt(index) - '0';
            }
        }
        return result;
    }

    private static void printBoard(int[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                System.out.print(board[r][c]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        tests();
    }

}

