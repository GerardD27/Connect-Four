import jdk.internal.joptsimple.internal.Rows;

import java.util.HashSet;
import java.util.Set;

public class ConnectFour {

    private final int[][] gameBoard;
    private Player playerOne;
    private Player playerTwo;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int RED = 1;
    private static final int YELLOW = 2;
    private final int[] piecesInColumns;


    public ConnectFour(Player playerOne, Player playerTwo) {
        this.gameBoard = new int[ROWS][COLUMNS];
        //Initialize each position in the game board to empty
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                gameBoard[i][j] = -1;
            }
        }
        //An array to represent how many pieces are currently in each column
        this.piecesInColumns = new int[COLUMNS];
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

    }


    public void makeMove(Player player, int column){
        //Since row position is determined by how many pieces are currently in a given column,
        //We only need to choose a column position and the row position will be determined as a result.
        if(column < 0 || column >= COLUMNS ){
            throw new ArrayIndexOutOfBoundsException("Column choice must be between positive and be no greater than 6");
        }

        if(isColumnFull(column)){
            System.out.println("Error, that column is already full!");
        }

        else{
            // place the piece in the available row closest to the bottom
            for(int i = ROWS - 1; i >= 0; i--){
                if(gameBoard[i][column] == -1){
                    gameBoard[i][column] = player.getPlayerNumber(); //Temporary and probably should be changed
                    break;
                }
            }
        }

    }

    public int validateGameBoard(){

        //Fill this with private method calls to valideate three things
        //1.) Check each row for four sequential pieces of the same color
        //2.) Check each column for four sequential pieces of the same color
        //3.) check each diagonal(with more than four spaces long it) for four sequential pieces of the same color

        //Return -1 if no current winner
        //Return 1 if player one wins
        //Return 2 if player 2 wins
        //Also check for and include a return for a draw(full board but no winner)

        //Again just for testing purposes
        return 0;
    }

    //Should the validations return the color they find or the number of the player that won instead of
    private int validateRows(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS - 3; j++){
            //Use a hashset to check if every four numbers in a row are the same
                Set<Integer> pieceSet = new HashSet<Integer>();
                pieceSet.add(gameBoard[i][j]);
                pieceSet.add(gameBoard[i][j+1]);
                pieceSet.add(gameBoard[i][j+2]);
                pieceSet.add(gameBoard[i][j+3]);
                if(pieceSet.size() == 1){

                   if(pieceSet.contains(RED)){
                       //Player One Wins
                       return RED;
                   }
                   else if(pieceSet.contains(YELLOW)){
                       //Player Two Wins
                       return YELLOW;
                   }
                }
            }
        }

        return -1;
    }

    private int validateColumns(){
        for(int j = 0; j < COLUMNS; j++){
            for(int i = ROWS - 1; i >= 3; i--){

                Set<Integer> pieceSet = new HashSet<Integer>();
                pieceSet.add(gameBoard[i][j]);
                pieceSet.add(gameBoard[i-1][j]);
                pieceSet.add(gameBoard[i-2][j]);
                pieceSet.add(gameBoard[i-3][j]);
                if(pieceSet.size() == 1){
                    //We have a winner
                     if(pieceSet.contains(RED)){
                         //Player 1 Wins
                         return RED;
                     }

                     else if(pieceSet.contains(YELLOW)){
                         //Player 2 Wins
                         return YELLOW;
                     }
                }
            }
        }

        //No winner found here
        return -1;
    }

    private int validateDiagonals(){
        //Start by moving across the first row(left to right), and check all diagonals that can fit more than 4 pieces.
        for(int i = 3; i <COLUMNS;i++){
            int j = 0; // Check each left diagonal in the first row
            int k = i;
            while(k - 3 >= 0 && j+3 < ROWS){
                Set<Integer> pieces = new HashSet<>();
                pieces.add(gameBoard[j][k]);
                pieces.add(gameBoard[j+1][k-1]);
                pieces.add(gameBoard[j+2][k-2]);
                pieces.add(gameBoard[j+3][k-3]);
                if(pieces.size() == 1){
                    if(pieces.contains(RED)){
                        return RED;
                    }

                    else if(pieces.contains(YELLOW)){
                        return YELLOW;
                    }
                }
                j++;
                k--;

            }

        }

        //Then move down the rightmost column, checking each diagonal(starting with the second row, as the previous loop
        //will have covered the first row's rightmost diagonal
        for(int i = 1; i< 3;/*number of diagonals in the row with 4 or more slots*/i++ ){
            //validate each diagonal here
            int j = i; // set the row number to change with i
            int k = COLUMNS - 1;// only traverse the last column

            while(j + 3 < ROWS && k - 3 >= 0){
                Set<Integer> pieces = new HashSet<>();
                pieces.add(gameBoard[j][k]);
                pieces.add(gameBoard[j+1][k-1]);
                pieces.add(gameBoard[j+2][k-2]);
                pieces.add(gameBoard[j+3][k-3]);

                if(pieces.size() == 1){
                    if(pieces.contains(RED)){
                        return RED;
                    }
                    else if(pieces.contains(YELLOW)){
                        return YELLOW;
                    }
                }
                j++;
                k--;
            }
        }

        //Now we repeat the above process, but starting from the top right instead of the top left of the board;
        for(int i = COLUMNS - 4; i >=0; i--){
            //Moving across the top row from right to left, validate each diagonal
            int j = 0; //Move across the first row
            int k = i;// set the column number to change with i

            while(j + 3 < ROWS && k + 3 < COLUMNS){
                Set<Integer> pieces = new HashSet<>();
                pieces.add(gameBoard[j][k]);
                pieces.add(gameBoard[j+1][k+1]);
                pieces.add(gameBoard[j+2][k+2]);
                pieces.add(gameBoard[j+3][k+3]);

                if(pieces.size() == 1){
                   if(pieces.contains(RED)){
                       return RED;
                   }

                   else if(pieces.contains(YELLOW)){
                       return YELLOW;
                   }
                }
                j++;
                k++;

            }
        }

        //Lastly, move down the leftmost column and check each diagonal
        for(int i = 1; i < 3; i++){
            //validate each diagonal here
            int j = i;// set the row number to change with i;
            int k = 0;// before entering the while loop, begin at the first column(column 0);
            while(j + 3 < ROWS && k + 3 < COLUMNS){
                Set<Integer> pieces = new HashSet<>();
                pieces.add(gameBoard[j][k]);
                pieces.add(gameBoard[j+1][k+1]);
                pieces.add(gameBoard[j+2][k+2]);
                pieces.add(gameBoard[j+3][k+3]);

                if(pieces.size() == 1){
                   if(pieces.contains(RED)){
                       return RED;
                   }
                   else if(pieces.contains(YELLOW)){
                       return YELLOW;
                   }
                }
            }
            j++;
            k++;
        }

        return -1;

    }

    private boolean isColumnFull(int columnNumber){
        //Based on the way pieces are placed in a game of connect four, if the very first row of a column has
        // a piece in it, the column must be full.
        if(gameBoard[0][columnNumber] == -1){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean isBoardFull(){
        for(int i = 0; i < ROWS;i++){
            for(int j = 0; j < COLUMNS; j++){
                if(gameBoard[i][j] == -1){
                    return false;
                }
            }
        }

        return true;
    }

    public void printGameBoard(){
        //Display the current position of the board
        for(int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                //System.out.print(gameBoard[i][j] +" ");
                if(gameBoard[i][j] == RED){
                    System.out.print('R');
                }
                else if(gameBoard[i][j] == YELLOW){
                    System.out.print('Y');
                }
                else{
                    System.out.print('-');
                }
            }
            System.out.println();
        }
    }

    public void clearBoard(){
        //Reset all board positions to -1
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; i < COLUMNS; i++){
                gameBoard[i][j] = -1;
            }
        }
    }

}
