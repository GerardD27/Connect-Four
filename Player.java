import java.io.Serializable;
import java.util.Scanner;

public class Player implements Serializable {

    private final String name;
    private static int counter = 0;
    private int playerNumber;

    //private Scanner scanner = new Scanner(System.in);

    public Player(String name) {
        //Initialize player number to increment based on how many instances there have been of the class

        this.name = name;
        this.counter++;
        this.playerNumber = counter;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

}
