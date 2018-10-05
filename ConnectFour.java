import java.util.HashSet;
import java.util.Set;

public class ConnectFour {

    private final int[][] gameBoard;
    private Player playerOne;
    private Player playerTwo;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
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


    public void makeMove(int column){
        //Since row position is determined by how many pieces are currently in a given column,
        //We only need to choose a column position and the row position will be determined as a result.
        if(column < 0 || column >= COLUMNS ){
            throw new ArrayIndexOutOfBoundsException("Column choice must be between positive and be no greater than 6");
        }

        int corespondingRow = (COLUMNS - 1) - piecesInColumns[column];


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
    private boolean validateRows(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS - 3; j++){
            //Use a hashset to check if every four numbers in a row are the same
                Set<Integer> pieceSet = new HashSet<Integer>();
                pieceSet.add(gameBoard[i][j]);
                pieceSet.add(gameBoard[i][j+1]);
                pieceSet.add(gameBoard[i][j+2]);
                pieceSet.add(gameBoard[i][j+3]);
                if(pieceSet.size() == 1 && pieceSet.contains(-1) == false){
                    //We have a winner
                    return true;
                    //Consider returning which player(or player piece constant value) won
                }
            }
        }
        return false;
    }

    private boolean validateColumns(){
        for(int j = 0; j < COLUMNS; j++){
            for(int i = ROWS - 1; i >= 3; i++){
                if(piecesInColumns[j] < 4){
                    continue;
                    //If the current column has less than 4 pieces in it we don't need to check it
                }

                Set<Integer> pieceSet = new HashSet<Integer>();
                pieceSet.add(gameBoard[i][j]);
                pieceSet.add(gameBoard[i+1][j]);
                pieceSet.add(gameBoard[i+2][j]);
                pieceSet.add(gameBoard[i+3][j]);
                if(pieceSet.size() == 1 && pieceSet.contains(-1) == false){
                    //We have a winner
                    return true;
                }


            }
        }

        return false;
    }

    private boolean validateDiagonals(){


        //Temporary, for testing main purposes only
        return false;

    }

    private boolean isColumnFull(int columnNumber){

        if(piecesInColumns[columnNumber] == 6){
            return true;
        }

        else{
            return false;
        }


    }

    private boolean isFull(){
        for(int numPieces: piecesInColumns){
            if(numPieces < ROWS){
                return false;
            }
        }

        return true;
    }

    public void printGameBoard(){
        for(int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                System.out.print(gameBoard[i][j] +" ");
            }
            System.out.println();
        }
    }

    public void clearBoard(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; i < COLUMNS; i++){
                gameBoard[i][j] = -1;
            }
        }
    }

}
