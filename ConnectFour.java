import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour {
    private final int[][] gameBoard;
    private int[] piecesInColumn; // Each index keeps track of how many game pieces are currently in the corresponding column
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int RED = 1;
    private static final int YELLOW = 2;
    private Player playerOne;
    private Player playerTwo;
    private int totalNumTurns;

    public ConnectFour() {
        this.gameBoard = new int[ROWS][COLUMNS];
        this.piecesInColumn = new int[COLUMNS];
        //Initialize each position in the game board to empty
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gameBoard[i][j] = -1;
            }
        }

        this.totalNumTurns = 0;

    }

    public static void main(String[] args) {
        new ConnectFour().buildMainMenu();

    }

    public void buildMainMenu(){
        // Construct a main menu that welcomes players and takes their names

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome to Connect Four!");
        Font titleFont = new Font(Font.SERIF, Font.BOLD, 20);
        title.setFont(titleFont);
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);

        JTextField nameOne = new JTextField(10);
        JLabel nameOneLabel = new JLabel("Player one's name:");
        JTextField nameTwo = new JTextField(10);
        JLabel nameTwoLabel = new JLabel("Player two's name");
        JPanel namesPanel = new JPanel();
        namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
        namesPanel.add(nameOneLabel);
        namesPanel.add(nameOne);
        namesPanel.add(nameTwoLabel);
        namesPanel.add(nameTwo);

        JButton startGameButton = new JButton("Start Game!");

        startGameButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                playerOne = new Player(nameOne.getText());
                playerTwo = new Player(nameTwo.getText());
                System.out.println("Welcome player one: " + playerOne.getName());
                System.out.println("Welcome player two: " + playerTwo.getName());
                mainFrame.dispose();
                buildGameGUI();
            }
        });

        JPanel startGamePanel = new JPanel();
        startGamePanel.add(startGameButton);

        JPanel centerPanel = new JPanel();
        centerPanel.add(namesPanel);

        mainFrame.getContentPane().add(BorderLayout.NORTH,titlePanel);
        mainFrame.getContentPane().add(BorderLayout.SOUTH, startGamePanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER,centerPanel);
        mainFrame.setSize(400,200);
        mainFrame.setVisible(true);

    }

    public void buildGameGUI(){

        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.setSize(500,500);
        gameFrame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem loadGame = new JMenuItem("Load Game");
        //Add an action listener here for loading the game

        JMenuItem saveGame = new JMenuItem("Save Game");
        //Add an action listener here for saving the game

        JMenuItem newGame = new JMenuItem("New Game");
        //Add an action listener here for starting a new game
        // Start a new game by calling the clear board function to wipe the underlying data structure
        // Then, repaint the board using the connect four visualizer to the original(default) state
        // Then reset the total number of turns and show the correct(player one's) name on the label

        fileMenu.add(newGame);
        fileMenu.add(saveGame);
        fileMenu.add(loadGame);
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        gameFrame.getContentPane().add(BorderLayout.NORTH,menuBar);


        //Now that we've set up the menu bar, let's work on drawing the initial game board

        ConnectFourVisualizer gameVisualizer = new ConnectFourVisualizer();

        gameFrame.add(BorderLayout.CENTER, gameVisualizer);

        //Add the functionality to choose a column to place a game piece in

        JPanel chooseMovePanel = new JPanel();
        JLabel chooseMoveLabel = new JLabel( playerOne.getName() + ", Choose a column from 1 to 7: ");
        JTextField moveChoiceField = new JTextField(5);
        JButton chooseMoveButton = new JButton("Choose");

        chooseMovePanel.add(chooseMoveLabel);
        chooseMovePanel.add(moveChoiceField);
        chooseMovePanel.add(chooseMoveButton);

        gameFrame.getContentPane().add(BorderLayout.SOUTH, chooseMovePanel);

         //Store the number of turns the game takes to complete

        chooseMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Make sure you add some input validation here to ensure what the user
                //Enters is valid, and to ensure they need to pick again if the move is invalid.

                int move = Integer.parseInt(moveChoiceField.getText());
                if(totalNumTurns % 2 == 0){
                    //It's player one's turn, so make their move and after change the text to ask for player two
                    makeMove(playerOne, move);
                    chooseMoveLabel.setText(playerTwo.getName() + ", choose a column from 1 to 7:");
                    moveChoiceField.setText("");
                }

                else{
                    //Otherwise it's player two's turn, so make their move and change the text to ask for player one
                    makeMove(playerTwo, move);
                    chooseMoveLabel.setText(playerOne.getName() + ", choose a column from 1 to 7:");
                    moveChoiceField.setText("");
                }

                printGameBoard();
                totalNumTurns++;
                gameVisualizer.repaint();

                int hasWinner = checkForWinner(move);
                if(hasWinner != -1){
                    moveChoiceField.setEnabled(false);
                    chooseMoveButton.setEnabled(false);
                    if(hasWinner == RED){
                        chooseMoveLabel.setText(playerOne.getName() + " wins!");

                    }

                    else if(hasWinner == YELLOW){
                        chooseMoveLabel.setText(playerTwo.getName() + " wins!");
                    }
                }


            }


        });


    }

    public class ConnectFourVisualizer extends JPanel{

        private int xBackground;
        private int yBackground;
        private int widthBackground;
        private int heightBackGround;

        private int xGameSlot;
        private int yGameSlot;
        private int widthGameSlot;
        private int heightGameSlot;



        public ConnectFourVisualizer(){
            this.widthGameSlot = 20;
            this.heightGameSlot = 20;

            this.heightBackGround = 300;
            this.widthBackground = 350;

            this.xBackground = 75;
            this.yBackground = 100;
        }

        public void paintComponent(Graphics g){
            g.setColor(Color.YELLOW);
            g.fillRect(xBackground, yBackground, widthBackground, heightBackGround);
            int currentX = xBackground + 40;
            int currentY = yBackground + 40;

            for(int i = 0; i < 6; i++) {


                for (int j = 0; j < 7; j++) {
                    if(gameBoard[i][j] == RED){
                        g.setColor(Color.RED);
                    }
                    else if(gameBoard[i][j] == YELLOW){
                        g.setColor(Color.BLACK);
                    }
                    else{
                        g.setColor(Color.WHITE);
                    }

                    g.fillOval(currentX, currentY, widthGameSlot, heightGameSlot);
                    currentX += 40;

                }
                currentX = xBackground + 40;
                currentY += 40;

            }
        }
    }


    public boolean makeMove(Player player, int column) {
        /* Since row position is determined by how many pieces are currently in a given column,
         we only need to choose a column position and the row position will be determined as a result. */

        //Decrement the column value by 1, as our array is zero indexed.
        column--;

        //Check if the column chosen is valid
        if (column < 0 || column >= COLUMNS) {
            System.out.println("Column choice must be between 1 and 7 !");
            return false;
        }

        //Check if the column chosen is already full
        if (isColumnFull(column)) {
            System.out.println("That column is already full!");
            return false;
        }
        /*Otherwise, start from the bottom of the column and change the value in
        the first open row to the player's number*/
        else {
//            for (int i = ROWS - 1; i >= 0; i--) {
//                if (gameBoard[i][column] == -1) {
//                    gameBoard[i][column] = player.getPlayerNumber();
//                    break;
//                }
//            }
//            return true;
            int nextOpenRow = ROWS - 1 - piecesInColumn[column];
            gameBoard[nextOpenRow][column] = player.getPlayerNumber();
            piecesInColumn[column]++;
            return true;
        }

    }


    public int checkForWinner(int latestMove){

        //If the gameboard is full, the game is a TIE
        if(isBoardFull()){
            // 0 indicates a TIE
            return 0;
        }

        int currentCol = latestMove - 1; //Remember, the user sees the columns numbered 1 -> 7 but our array is 0 indexed
        int currentRow = ROWS - piecesInColumn[currentCol]; // Determine which row the last piece was placed in

       int rows =  checkRows(currentRow);
       int columns =  checkColumns(currentCol);
       int diagonals =  checkDiagonals(currentRow,currentCol);


       if(rows == RED || columns == RED || diagonals == RED){
           return RED;
       }

       if(rows == YELLOW || columns == YELLOW || diagonals == YELLOW){
           return YELLOW;
       }

       //Otherwise we don't have a winner yer

        return -1;
    }

    private int checkRows(int currentRow){
        //Obtain the row and column of the latest move

        for (int i = 0; i < COLUMNS - 3; i++) {

            if (gameBoard[currentRow][i] == RED &&
                    gameBoard[currentRow][i + 1] == RED &&
                    gameBoard[currentRow][i + 2] == RED &&
                    gameBoard[currentRow][i + 3] == RED) {

                return RED;
            }

            if (gameBoard[currentRow][i] == YELLOW &&
                    gameBoard[currentRow][i + 1] == YELLOW &&
                    gameBoard[currentRow][i + 2] == YELLOW &&
                    gameBoard[currentRow][i + 3] == YELLOW) {

                return YELLOW;
            }
        }
        //Otherwise, we have no winner yet
        return -1;
    }

    private int checkColumns( int currentCol){
        //Check the column

        //Commented out becuase we are not returning -1 unluess we seperate column checking into its own funciton
//        if(piecesInColumn[currentCol] < 4){
//            //If there are less than 4 game pieces in the column, there cannot be a winner here by column
//            return -1;
//        }

        for (int i = ROWS - 1; i >= ROWS - 3; i--) {
            if (gameBoard[i][currentCol] == RED &&
                    gameBoard[i - 1][currentCol] == RED &&
                    gameBoard[i - 2][currentCol] == RED &&
                    gameBoard[i - 3][currentCol] == RED) {

                return RED;
            }

            if (gameBoard[i][currentCol] == YELLOW &&
                    gameBoard[i - 1][currentCol] == YELLOW &&
                    gameBoard[i - 2][currentCol] == YELLOW &&
                    gameBoard[i - 3][currentCol] == YELLOW) {

                return YELLOW;
            }

        }

        //Otherwise we have no winner
        return -1;

    }

    private int checkDiagonals(int currentRow, int currentCol){
        //Check the left-to-right upward diagonal containing the latest move

        int startingRow = currentRow + 3;
        int startingCol = currentCol - 3;

        //get to a starting position that is valid on the board
        while (startingRow > ROWS - 1 || startingCol < 0) {
            startingRow--;
            startingCol++;
        }

        while (startingRow - 3 >= 0 && startingCol + 3 <  COLUMNS ) {
            if (gameBoard[startingRow][startingCol] == RED &&
                    gameBoard[startingRow - 1][startingCol + 1] == RED &&
                    gameBoard[startingRow - 2][startingCol + 2] == RED &&
                    gameBoard[startingRow - 3][startingCol + 3] == RED) {

                return RED;
            }

            if (gameBoard[startingRow][startingCol] == YELLOW &&
                    gameBoard[startingRow - 1][startingCol + 1] == YELLOW &&
                    gameBoard[startingRow - 2][startingCol + 2] == YELLOW &&
                    gameBoard[startingRow - 3][startingCol + 3] == YELLOW) {

                return YELLOW;
            }
            startingRow--;
            startingCol++;



        }

        //Check the left to right downward diagonal containing the latest placed game piece

        //Reset the starting row and column variables
        startingRow = currentRow - 3;
        startingCol = currentCol - 3;

        while(startingRow < 0 || startingCol < 0 ){
            startingCol++;
            startingRow++;
        }

        while(startingRow + 3 < ROWS  && startingCol + 3 < COLUMNS ){
            if(gameBoard[startingRow][startingCol] == RED &&
                    gameBoard[startingRow + 1][startingCol + 1] == RED &&
                    gameBoard[startingRow + 2][startingCol + 2] == RED &&
                    gameBoard[startingRow + 3][startingCol + 3] == RED){

                return RED;
            }

            if(gameBoard[startingRow][startingCol] == YELLOW &&
                    gameBoard[startingRow + 1][startingCol + 1] == YELLOW &&
                    gameBoard[startingRow + 2][startingCol + 2] == YELLOW &&
                    gameBoard[startingRow + 3][startingCol + 3] == YELLOW){

                return YELLOW;
            }

            startingRow++;
            startingCol++;

        }

        //Otherwise, we have no winner yet
        return -1;

    }

    private boolean isColumnFull(int columnNumber) {
        /*Based on the way pieces are placed in a game of connect four, if the very first row of a column has
         a piece in it, the column must be full.*/
        if (gameBoard[0][columnNumber] == -1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isBoardFull() {
        //If any value in our board is -1, the board is not full
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (gameBoard[i][j] == -1) {
                    return false;
                }
            }
        }
        //Otherwise the board is full
        return true;
    }

    public void printGameBoard() {
        System.out.println("==============================");
        //Display the number for each column
        System.out.println("1 2 3 4 5 6 7");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (gameBoard[i][j] == RED) {
                    System.out.print("R ");
                } else if (gameBoard[i][j] == YELLOW) {
                    System.out.print("Y ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println("==============================");
    }

    public void clearBoard() {
        //Reset all board positions to -1
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gameBoard[i][j] = -1;
            }
        }
    }
}
