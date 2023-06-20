import javax.swing.*;

public class SnakeGame extends JFrame {
    Board Board;
    SnakeGame()
    {
        Board=new Board();
        add(Board);
        pack();
        setResizable(false);
        setVisible(true);

    }


    public static void main(String[] args) {

        SnakeGame snkeGame=new SnakeGame();


    }
}