//import java.util.Scanner;
//
//public class Main {
//
//    private static Scanner scanner = new Scanner(System.in);
//    public static void main(String[] args) {
//
//        play();
//    }
//
//   private static void whoWon(int winner){
//        if(winner == 0){
//        System.out.println("It's a tie!");
//        }
//
//       else if(winner == 1){
//           System.out.println("Player one wins!");
//       }
//
//       else if(winner == 2){
//           System.out.println("Player two wins!");
//       }
//
//       else{
//           System.out.println("No winner yet!\n");
//       }
//
//   }
//
//   private static void play(){
//       System.out.println("Welcome to Connect Four!");
//       System.out.println("Player one, please enter your name: ");
//       String name = scanner.nextLine();
//       Player playerOne = new Player(name);
//       System.out.println("Player one's name is: " + playerOne.getName());
//
//       System.out.println("Player two, please enter your name: ");
//       name = scanner.nextLine();
//       Player playerTwo = new Player(name);
//       System.out.println("Player two's name is: " + playerTwo.getName());
//
//       ConnectFour connectFour = new ConnectFour(playerOne, playerTwo);
//       System.out.println("Here is the starting board!");
//       connectFour.printGameBoard();
//
//
//       while(true){
//           int playerMove = getPlayerMove(playerOne, connectFour);
//           connectFour.printGameBoard();
//           int winner = connectFour.checkForWinner(playerMove);
//           whoWon(winner);
//           if(winner != -1){
//               break;
//           }
//
//           playerMove = getPlayerMove(playerTwo, connectFour);
//           connectFour.printGameBoard();
//           winner = connectFour.checkForWinner(playerMove);
//           whoWon(winner);
//           if(winner != -1){
//               break;
//           }
//       }
//   }
//
//   private static int getPlayerMove(Player player , ConnectFour currentGame){
//        System.out.println(player.getName() + ", please choose a column from 1 - 7 to make your move: ");
//        int move = scanner.nextInt();
//        while(currentGame.makeMove(player, move) == false){
//            System.out.println("Invalid move choice! Please pick again: ");
//            move = scanner.nextInt();
//        }
//
//        return move;
//
//   }
//
//}


