import java.io.Serializable;

public class Player implements Serializable {

    private final String name;
    private static int counter = 0;
    private int playerNumber;


    public Player(String name) {
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
