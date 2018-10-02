import java.util.Scanner;

public class Player {

    private final String name;
    private static int playerNumber;
    //private Scanner scanner = new Scanner(System.in);

    public Player(String name) {
        //Initialize player number to increment based on how many instances there have been of the class


        //Should we keep keyboard input restricted to the main/client program only?

       /* System.out.println("Please enter your name Player " + this.playerNumber +": ");
        this.name = scanner.nextLine();*/

       this.name = name;

    }
}
