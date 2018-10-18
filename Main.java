import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
      //Create two players and get their names from user input
        System.out.println("Welcome to Connect Four!");
        System.out.println("Player 1 please enter your name: ");
        String player1_name = scanner.nextLine();
        System.out.println("Player 2 Enter your name: ");
        String player2_name = scanner.nextLine();
        Player playerOne = new Player(player1_name);
        Player playerTwo = new Player(player2_name);
        System.out.println(playerOne.getName() + " will be red (R on the board)");
        System.out.println(playerTwo.getName() + " will be yellow (Y on the board)\n");
        ConnectFour connectFour = new ConnectFour(playerOne,playerTwo);
        connectFour.printGameBoard();
        System.out.println("\n");
        boolean hasWon = false;
        while(hasWon == false){
            System.out.println(playerOne.getName() + ", Please enter a column to make your move");
            int move =  scanner.nextInt();
            while(connectFour.makeMove(playerOne, move) == false){
                System.out.println("Please try again: ");
                move =scanner.nextInt();
           }
            connectFour.printGameBoard();
            int winner = connectFour.validateGameBoard();
            whoWon(winner);
            if(winner != -1){
                hasWon = false;
                break;
            }
            System.out.println(playerTwo.getName() + ", Please enter a column to make your move");
            move =  scanner.nextInt();
            while(connectFour.makeMove(playerTwo, move) == false){
                System.out.println("Please try again: ");
                move =scanner.nextInt();
            }
            connectFour.printGameBoard();
            winner = connectFour.validateGameBoard();
            whoWon(winner);
            if(winner != -1){
                hasWon = false;
                break;
            }
        }
    }

   private static void whoWon(int winner){
        if(winner == 0){
        System.out.println("It's a tie!");
        }

       else if(winner == 1){
           System.out.println("Player One wins!");
       }

       else if(winner == 2){
           System.out.println("Player Two wins!");
       }

       else{
           System.out.println("No winner yet!\n");
       }

   }

}


