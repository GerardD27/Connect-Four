import java.util.ArrayList;
import java.util.List;

public class ConnectFourModel implements ConnectFourModelInterface{

    private int[][] gameBoard;
    private int[] piecesInColumn; // Each index keeps track of how many game pieces are currently in the corresponding column
    private Player playerOne;
    private Player playerTwo;
    private int totalNumTurns;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int RED = 1;
    private static final int YELLOW = 2;
    private List<ConnectFourObserver> connectFourObserverList;

    public ConnectFourModel() {
        this.gameBoard = new int[ROWS][COLUMNS];
        this.piecesInColumn = new int[COLUMNS];
        this.totalNumTurns = 0;
        this.connectFourObserverList = new ArrayList<>();

        //Initialize each game board position to empty
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gameBoard[i][j] = -1;
            }
        }

    }

    @Override
    public boolean makeMove(Player player, int columnNumber) {
            /* Since row position is determined by how many pieces are currently in a given column,
         we only need to choose a column position and the row position will be determined as a result. */

        //Decrement the column value by 1, as our array is zero indexed.
        columnNumber--;

        //Check if the column chosen is valid
        if (columnNumber < 0 || columnNumber >= COLUMNS) {
            System.out.println("Column choice must be between 1 and 7 !");
            return false;
        }

        //Check if the column chosen is already full
        if (isColumnFull(columnNumber)) {
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
            int nextOpenRow = ROWS - 1 - piecesInColumn[columnNumber];
            gameBoard[nextOpenRow][columnNumber] = player.getPlayerNumber();
            piecesInColumn[columnNumber]++;

            //Note we're not incrementing total num turns here. Maybe we should THINK ABOUT IT
            //Our model should notify our observers that our state has changed

            //Follow up note: adding incrementation to totalNumTurns here
            totalNumTurns++;
            notifyObservers();
            return true;
        }
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

    @Override
    public int[][] getGameBoard() {
        return gameBoard;
    }

    @Override
    public int[] getPiecesInColumns() {
        return piecesInColumn;
    }

    @Override
    public Player getPlayerOne() {
        return playerOne;
    }

    @Override
    public Player getPlayerTwo() {
        return playerTwo;
    }

    @Override
    public int getTotalNumTurns() {
        return totalNumTurns;
    }

    @Override
    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    @Override
    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    @Override
    public void registerObserver(ConnectFourObserver observer) {
        connectFourObserverList.add(observer);
    }

    @Override
    public void removeObserver(ConnectFourObserver observer) {
        connectFourObserverList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // Add code that updates all observers on necessary model state here
        for(ConnectFourObserver observer: connectFourObserverList){
            observer.update();
        }
    }
}
