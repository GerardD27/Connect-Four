import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
	// write your code here
      //Creating players and assigning their names and player numbers currently works
        System.out.println("Welcome to Connect Four!");
        System.out.println("Main Menu: ");
        System.out.println("0: Quit");
        System.out.println("1: Player vs Player(Local Play only!)");
        System.out.println("2: Player vs Computer(WIP)");
        System.out.println("Player 1 please enter your name: ");
        int choice;
        switch(choice){
            case 1:
                String player1_name = scanner.nextLine();
                System.out.println("Player 2 Enter your name: ");
                String player2_name = scanner.nextLine();

                Player playerOne = new Player(player1_name);
                Player playerTwo = new Player(player2_name);

                System.out.println("Player one's name is: " + playerOne.getName());
                System.out.println("Player two's name is: " + playerTwo.getName());

                System.out.println("Player one's player number is: " + playerOne.getPlayerNumber());
                System.out.println("Player two's player number is: " + playerTwo.getPlayerNumber());

                ConnectFour connectFour = new ConnectFour(playerOne,playerTwo);
                connectFour.printGameBoard();
                do{

                }while()


        }

    }

    private static int playerMove(String columnChoice){
        //Make sure to validate this and check if the column is full before

        return Integer.parseInt(columnChoice);
    }
}


