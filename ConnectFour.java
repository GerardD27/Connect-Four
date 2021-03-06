import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ConnectFour implements Serializable {
    private int[][] gameBoard;
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
                //Once we press start game, create player objects based on the names, close the window and open a new window with the game GUI
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

        ConnectFourVisualizer gameVisualizer = new ConnectFourVisualizer();

        gameFrame.add(BorderLayout.CENTER, gameVisualizer);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");

        JPanel chooseMovePanel = new JPanel();
        JLabel chooseMoveLabel = new JLabel( playerOne.getName() + ", Choose a column from 1 to 7: ");
        JTextField moveChoiceField = new JTextField(5);
        JButton chooseMoveButton = new JButton("Choose");

        exit.addActionListener(new ActionListener() {
            //Close the window and stop the program if the user clicks exit
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem loadGame = new JMenuItem("Load Game");
        //Add an action listener here for loading the game
        //1.) Restore the state of connect four object from the connect_four.ser file
        //2.) Repaint the game board gui to display the previous state's game board and allow the game to continue

        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem newGame = new JMenuItem("New Game");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reset the board and redraw the visual representation based on the empty board
                clearBoard();
                gameVisualizer.repaint();
                totalNumTurns = 0;
                piecesInColumn = new int[COLUMNS];
                chooseMoveLabel.setText(playerOne.getName() + ", Choose a column from 1 to 7: ");
                //Re-enable the buttons and text field in case the previous game had a winner
                moveChoiceField.setEnabled(true);
                chooseMoveButton.setEnabled(true);

            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    FileOutputStream fileOutputStream = new FileOutputStream("connect_four.ser");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(ConnectFour.this);
                    objectOutputStream.close();
                    fileOutputStream.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    //Set the current Connect four object references and values equal to the previously saved games values
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("connect_four.ser"));
                    ConnectFour previousGame = (ConnectFour) objectInputStream.readObject();
                    ConnectFour.this.gameBoard = previousGame.gameBoard;
                    ConnectFour.this.piecesInColumn = previousGame.piecesInColumn;
                    ConnectFour.this.playerOne = previousGame.playerOne;
                    ConnectFour.this.playerTwo = previousGame.playerTwo;
                    ConnectFour.this.totalNumTurns = previousGame.totalNumTurns;

                    System.out.println("The number of turns is: " + totalNumTurns);

                    //Determine whose turn it was when the game was saved, and display the appropriate label
                    if(totalNumTurns % 2 == 0){
                        chooseMoveLabel.setText(playerOne.getName() + ", choose a column from 1 to 7:");
                    }

                    else{
                        chooseMoveLabel.setText(playerTwo.getName() + ", choose a column from 1 to 7");
                    }

                }catch(Exception ex){
                    ex.printStackTrace();
                }
                //Redraw the game gui no correctly show the loaded game state
                gameVisualizer.repaint();
                printGameBoard();
            }
        });

        //Add the menu items to the menua bar
        fileMenu.add(newGame);
        fileMenu.add(saveGame);
        fileMenu.add(loadGame);
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        gameFrame.getContentPane().add(BorderLayout.NORTH,menuBar);


        chooseMovePanel.add(chooseMoveLabel);
        chooseMovePanel.add(moveChoiceField);
        chooseMovePanel.add(chooseMoveButton);

        gameFrame.getContentPane().add(BorderLayout.SOUTH, chooseMovePanel);


        chooseMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Notice here we're displaying whose turn in is after the current player makes their move
                int move = Integer.parseInt(moveChoiceField.getText());
                if(totalNumTurns % 2 == 0){
                    //It's player one's turn, so make their move and after change the text to ask for player two
                   boolean attempt =  makeMove(playerOne, move);
                   if(attempt == false){
                       chooseMoveLabel.setText("That was an invalid move, please try again " + playerOne.getName()+ ":");
                       moveChoiceField.setText("");
                       return;
                   }
                    chooseMoveLabel.setText(playerTwo.getName() + ", choose a column from 1 to 7:");
                    moveChoiceField.setText("");
                }

                else{
                    //Otherwise it's player two's turn, so make their move and change the text to ask for player one
                     boolean attempt = makeMove(playerTwo, move);
                     if(attempt == false){
                         chooseMoveLabel.setText("That was an invalid move, please try again " + playerTwo.getName() + ":");
                         moveChoiceField.setText("");
                         return;
                     }
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
                    saveGame.setEnabled(false);
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
