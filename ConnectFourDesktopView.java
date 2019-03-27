import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourDesktopView implements ConnectFourObserver {

    ConnectFourModelInterface model;
    ConnectFourControllerInterface controller;
    //Main Menu GUI Components We Need To Access
    private JFrame mainMenuFrame;
    private JTextField nameOne;
    private JTextField nameTwo;

    public ConnectFourDesktopView(ConnectFourModelInterface model, ConnectFourControllerInterface controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver(this);
    }

    public void buildMainMenu(){
        // Construct a main menu that welcomes players and takes their names
        mainMenuFrame = new JFrame();
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome to Connect Four!");
        Font titleFont = new Font(Font.SERIF, Font.BOLD, 20);
        title.setFont(titleFont);
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);

        nameOne = new JTextField(10);
        JLabel nameOneLabel = new JLabel("Player one's name:");
        nameTwo = new JTextField(10);
        JLabel nameTwoLabel = new JLabel("Player two's name");

        JPanel namesPanel = new JPanel();
        namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
        namesPanel.add(nameOneLabel);
        namesPanel.add(nameOne);
        namesPanel.add(nameTwoLabel);
        namesPanel.add(nameTwo);

        JButton startGameButton = new JButton("Start Game!");
        startGameButton.addActionListener(new StartGame());

        //We now use an inner class as opposed to the anonymous inner class to implement onAction functionality for the start button

//                new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //Once we press start game, create player objects based on the names, close the window and open a new window with the game GUI
//                playerOne = new Player(nameOne.getText());
//                playerTwo = new Player(nameTwo.getText());
//                System.out.println("Welcome player one: " + playerOne.getName());
//                System.out.println("Welcome player two: " + playerTwo.getName());
//                mainMenuFrame.dispose();
//                buildGameGUI();
//            }
//        });

        JPanel startGamePanel = new JPanel();
        startGamePanel.add(startGameButton);

        JPanel centerPanel = new JPanel();
        centerPanel.add(namesPanel);

        mainMenuFrame.getContentPane().add(BorderLayout.NORTH,titlePanel);
        mainMenuFrame.getContentPane().add(BorderLayout.SOUTH, startGamePanel);
        mainMenuFrame.getContentPane().add(BorderLayout.CENTER,centerPanel);
        mainMenuFrame.setSize(400,200);
        mainMenuFrame.setVisible(true);
    }

    private class StartGame implements ActionListener{

        JTextField nameOne;
        @Override
        public void actionPerformed(ActionEvent e) {
            //1.)Read player one and player two's names from their respective text fields
                String playerOneName = nameOne.getText();
                String playerTwoName = nameTwo.getText();

            //2.)Create two new player objects(player one and player two) from using these name OR
                //Note that this part should be the controllers responsibility, the view shouldn't ask to change
                // the model's state directly.
                controller.setPlayerOne(new Player(playerOneName));
                controller.setPlayerTwo(new Player(playerTwoName));

            //3.)Close the main menu frame as we no longer need it
                mainMenuFrame.dispose();

            //4.)Call the method which creates the Game GUI

        }
    }
    
    @Override
    public void update() {

    }
}
