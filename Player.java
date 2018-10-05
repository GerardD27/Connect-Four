import java.util.Scanner;

public class Player {

    private final String name;
    private static int counter = 0;
    private int playerNumber;
    private final int playerGamePieceColor;
    private static final int RED = 1;
    private static final int YELLOW = 2;
    //private Scanner scanner = new Scanner(System.in);

    public Player(String name) {
        //Initialize player number to increment based on how many instances there have been of the class

        this.name = name;
        this.counter++;
        this.playerNumber = counter;
        if(playerNumber == 1){
            playerGamePieceColor = RED;
        }

        else{
            playerGamePieceColor = YELLOW;
        }
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int placeAPiece(){
        //Returns the column number the player wishes to place a piece in.

        //Tempoarary and only for testing purposes
        return 0;
    }
}
