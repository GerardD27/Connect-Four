public interface ConnectFourControllerInterface {

    boolean makeMove(Player player, int column);
    void setPlayerOne(Player playerOne);
    void setPlayerTwo(Player playerTwo);
    //Not that there is not a method for setting the total number of turns
    //Currently I don't think this should be able to be changed by the view,
    //And rather should only be updated by the model's make move function once a player has successfully
    // Completed their turn

    //ALSO: Consider if the controller should be disabling the views buttons or if that should be the view's
    // responsibility once a game has been completed(won).
}
