
public class ConnectFour {
    private final int[][] gameBoard;
    private int[] piecesInColumn; // Each index keeps track of how many game pieces are currently in the corresponding column
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int RED = 1;
    private static final int YELLOW = 2;

    public ConnectFour(Player playerOne, Player playerTwo) {
        this.gameBoard = new int[ROWS][COLUMNS];
        this.piecesInColumn = new int[COLUMNS];
        //Initialize each position in the game board to empty
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gameBoard[i][j] = -1;
            }
        }

    }

    public boolean makeMove(Player player, int column) {
        /* Since row position is determined by how many pieces are currently in a given column,
         we only need to choose a column position and the row position will be determined as a result. */

        //Decrement the column value by 1, as our array is zero indexed.
        column--;

        //Check if the column chosen is valid
        if (column < 0 || column >= COLUMNS) {
            System.out.println("Column choice must be between 1 and 7 !");
            return false;
        }

        //Check if the column chosen is already full
        if (isColumnFull(column)) {
            System.out.println("That column is already full!");
            return false;
        }
        /*Otherwise, start from the bottom of the column and change the value in
        the first open row to the player's number*/
        else {
//            for (int i = ROWS - 1; i >= 0; i--) {
//                if (gameBoard[i][column] == -1) {
//                    gameBoard[i][column] = player.getPlayerNumber();
//                    break;
//                }
//            }
//            return true;
            int nextOpenRow = ROWS - 1 - piecesInColumn[column];
            gameBoard[nextOpenRow][column] = player.getPlayerNumber();
            piecesInColumn[column]++;
            return true;
        }

    }


    public int checkForWinner(int latestMove){

        //If the gameboard is full, the game is a TIE
        if(isBoardFull()){
            // 0 indicates a TIE
            return 0;
        }

        int currentCol = latestMove - 1; //Remember, the user sees the columns numbered 1 -> 7 but our array is 0 indexed
        int currentRow = ROWS - piecesInColumn[currentCol]; // Determine which row the last piece was placed in

       int rows =  checkRows(currentRow);
       int columns =  checkColumns(currentCol);
       int diagonals =  checkDiagonals(currentRow,currentCol);


       if(rows == RED || columns == RED || diagonals == RED){
           return RED;
       }

       if(rows == YELLOW || columns == YELLOW || diagonals == YELLOW){
           return YELLOW;
       }

       //Otherwise we don't have a winner yer

        return -1;
    }

    private int checkRows(int currentRow){
        //Obtain the row and column of the latest move

        for (int i = 0; i < COLUMNS - 3; i++) {

            if (gameBoard[currentRow][i] == RED &&
                    gameBoard[currentRow][i + 1] == RED &&
                    gameBoard[currentRow][i + 2] == RED &&
                    gameBoard[currentRow][i + 3] == RED) {

                return RED;
            }

            if (gameBoard[currentRow][i] == YELLOW &&
                    gameBoard[currentRow][i + 1] == YELLOW &&
                    gameBoard[currentRow][i + 2] == YELLOW &&
                    gameBoard[currentRow][i + 3] == YELLOW) {

                return YELLOW;
            }
        }
        //Otherwise, we have no winner yet
        return -1;
    }

    private int checkColumns( int currentCol){
        //Check the column

        //Commented out becuase we are not returning -1 unluess we seperate column checking into its own funciton
//        if(piecesInColumn[currentCol] < 4){
//            //If there are less than 4 game pieces in the column, there cannot be a winner here by column
//            return -1;
//        }

        for (int i = ROWS - 1; i >= ROWS - 3; i--) {
            if (gameBoard[i][currentCol] == RED &&
                    gameBoard[i - 1][currentCol] == RED &&
                    gameBoard[i - 2][currentCol] == RED &&
                    gameBoard[i - 3][currentCol] == RED) {

                return RED;
            }

            if (gameBoard[i][currentCol] == YELLOW &&
                    gameBoard[i - 1][currentCol] == YELLOW &&
                    gameBoard[i - 2][currentCol] == YELLOW &&
                    gameBoard[i - 3][currentCol] == YELLOW) {

                return YELLOW;
            }

        }

        //Otherwise we have no winner
        return -1;

    }

    private int checkDiagonals(int currentRow, int currentCol){
        //Check the left-to-right upward diagonal containing the latest move

        int startingRow = currentRow + 3;
        int startingCol = currentCol - 3;

        //get to a starting position that is valid on the board
        while (startingRow > ROWS - 1 || startingCol < 0) {
            startingRow--;
            startingCol++;
        }

        while (startingRow - 3 >= 0 && startingCol + 3 <  COLUMNS ) {
            if (gameBoard[startingRow][startingCol] == RED &&
                    gameBoard[startingRow - 1][startingCol + 1] == RED &&
                    gameBoard[startingRow - 2][startingCol + 2] == RED &&
                    gameBoard[startingRow - 3][startingCol + 3] == RED) {

                return RED;
            }

            if (gameBoard[startingRow][startingCol] == YELLOW &&
                    gameBoard[startingRow - 1][startingCol + 1] == YELLOW &&
                    gameBoard[startingRow - 2][startingCol + 2] == YELLOW &&
                    gameBoard[startingRow - 3][startingCol + 3] == YELLOW) {

                return YELLOW;
            }
            startingRow--;
            startingCol++;



        }

        //Check the left to right downward diagonal containing the latest placed game piece

        //Reset the starting row and column variables
        startingRow = currentRow - 3;
        startingCol = currentCol - 3;

        while(startingRow < 0 || startingCol < 0 ){
            startingCol++;
            startingRow++;
        }

        while(startingRow + 3 < ROWS  && startingCol + 3 < COLUMNS ){
            if(gameBoard[startingRow][startingCol] == RED &&
                    gameBoard[startingRow + 1][startingCol + 1] == RED &&
                    gameBoard[startingRow + 2][startingCol + 2] == RED &&
                    gameBoard[startingRow + 3][startingCol + 3] == RED){

                return RED;
            }

            if(gameBoard[startingRow][startingCol] == YELLOW &&
                    gameBoard[startingRow + 1][startingCol + 1] == YELLOW &&
                    gameBoard[startingRow + 2][startingCol + 2] == YELLOW &&
                    gameBoard[startingRow + 3][startingCol + 3] == YELLOW){

                return YELLOW;
            }

            startingRow++;
            startingCol++;

        }

        //Otherwise, we have no winner yet
        return -1;

    }

    private boolean isColumnFull(int columnNumber) {
        /*Based on the way pieces are placed in a game of connect four, if the very first row of a column has
         a piece in it, the column must be full.*/
        if (gameBoard[0][columnNumber] == -1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isBoardFull() {
        //If any value in our board is -1, the board is not full
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (gameBoard[i][j] == -1) {
                    return false;
                }
            }
        }
        //Otherwise the board is full
        return true;
    }

    public void printGameBoard() {
        System.out.println("==============================");
        //Display the number for each column
        System.out.println("1 2 3 4 5 6 7");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (gameBoard[i][j] == RED) {
                    System.out.print("R ");
                } else if (gameBoard[i][j] == YELLOW) {
                    System.out.print("Y ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println("==============================");
    }

    public void clearBoard() {
        //Reset all board positions to -1
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gameBoard[i][j] = -1;
            }
        }
    }
}
