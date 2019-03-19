public interface ConnectFourModelInterface {

    //This interface will allow the Controller to manipulate the Models's state
    // and also allow the Controller and View to obtain the Model's state

    boolean makeMove(Player player, int columnNumber);

    int[][] getGameBoard();
    int[] getPiecesInColumns();
    Player getPlayerOne();
    Player getPlayerTwo();
    int getTotalNumTurns();
}
