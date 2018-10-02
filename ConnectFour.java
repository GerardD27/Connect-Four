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

}
