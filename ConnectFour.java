public class ConnectFour {

    private final int[][] gameBoard;
    private Player playerOne;
    private Player playerTwo;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;


    public ConnectFour() {
        this.gameBoard = new int[ROWS][COLUMNS];
        //Initialize each position in the game board to empty
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                gameBoard[i][j] = -1;
            }
        }
    }


    public void makeMove(int row, int column){

    }

    public int validateGameBoard(){

        //Fill this with private method calls to valideate three things
        //1.) Check each row for four sequential pieces of the same color
        //2.) Check each column for four sequential pieces of the same color
        //3.) check each diagonal(with more than four spaces long it) for four sequential pieces of the same color

        //Return -1 if no current winner
        //Return 1 if player one wins
        //Return 2 if player 2 wins
    }

    //Should the validations return the color they find or the number of the player that won instead of
    private boolean validateRows(){

    }

    private boolean validateColumns(){

    }

    private boolean validateDiagonals(){

    }

    public void printGameBoard(){

    }

    public void clearBoard(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; i < COLUMNS; i++){
                gameBoard[i][j] = -1;
            }
        }
    }

}
