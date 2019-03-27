public class ConnectFourController implements ConnectFourControllerInterface {

    ConnectFourModelInterface model;
    ConnectFourDesktopView desktopView;


    public ConnectFourController(ConnectFourModelInterface model) {
        this.model = model;
        this.desktopView = new ConnectFourDesktopView(model, this);
        desktopView.buildMainMenu();
    }

    @Override
    public boolean makeMove(Player player, int column) {
      return  model.makeMove(player,column);
    }

    @Override
    public void setPlayerOne(Player playerOne) {
        model.setPlayerOne(playerOne);
    }

    @Override
    public void setPlayerTwo(Player playerTwo) {
        model.setPlayerTwo(playerTwo);
    }
}
