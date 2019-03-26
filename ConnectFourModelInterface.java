public interface ConnectFourModelInterface {

    //This interface will allow the Controller to manipulate the Models's state
    // and also allow the Controller and View to obtain the Model's state

    boolean makeMove(Player player, int columnNumber);

    int[][] getGameBoard();
    int[] getPiecesInColumns();
    Player getPlayerOne();
    Player getPlayerTwo();
    void setPlayerOne(Player playerOne);
    void setPlayerTwo(Player playerTwo);
    int getTotalNumTurns();
    void registerObserver(ConnectFourObserver observer);
    void removeObserver(ConnectFourObserver observer);
    void notifyObservers();
}
