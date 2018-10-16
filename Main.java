import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
	// write your code here
      //Creating players and assigning their names and player numbers currently works
        System.out.println("Welcome to Connect Four!");
 /*       System.out.println("Main Menu: ");
        System.out.println("0: Quit");
        System.out.println("1: Player vs Player(Local Play only!)");
        System.out.println("2: Player vs Computer(WIP)");*/

        System.out.println("Player 1 please enter your name: ");
        String player1_name = scanner.nextLine();
        System.out.println("Player 2 Enter your name: ");
        String player2_name = scanner.nextLine();


        Player playerOne = new Player(player1_name);
        Player playerTwo = new Player(player2_name);

        System.out.println(playerOne.getName() + " will be red (R on the board)");
        System.out.println(playerTwo.getName() + " will be yellow (Y on the board)\n");

     /*   System.out.println("Player one's name is: " + playerOne.getName());
        System.out.println("Player two's name is: " + playerTwo.getName());

        System.out.println("Player one's player number is: " + playerOne.getPlayerNumber());
        System.out.println("Player two's player number is: " + playerTwo.getPlayerNumber() +"\n");*/

        ConnectFour connectFour = new ConnectFour(playerOne,playerTwo);
        connectFour.printGameBoard();

       /*Testing the Basic board functions */
        boolean hasWon = false;
        while(hasWon == false){
            System.out.println(playerOne.getName() + ", Please enter a column to make your move");
            /* BE SURE TO HANDLE THE POSSIBLE ARRAY OUT OF BOUNDS EXCEPTION HERE */
            //Maybe include the fullColumn validtiaon component in the finally block?

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

   /* private static int playerMove(Player player){
        //If the player is a computer player, handle how to pick a position
        if(player.getClass().getSimpleName().equals("ComputerPlayer")){
            //add code to randomly choose a position or call method to allow computer to choose
         // int move =  player.getComputerMove();
        }

        else{
                System.out.println("Please enter the column where you would like to place your piece");
               int move = scanner.nextInt();
               if()


        }
        //Make sure to validate this and check if the column is full before


    }*/

   private static void whoWon(int winner){
       if(winner == 1){
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


