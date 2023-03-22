import javax.swing.*;

public class Snake extends JFrame {
    Board board;

    Snake()
    {
        board=new Board();
        add(board);
        pack();  // inherit the board dimention from board class
        setLocationRelativeTo(null);  // display in centre board on screen
        setTitle("Snake Game");
        setResizable(false);
         setVisible(true);
    }
    public static void main(String[] args) {
        Snake obj= new Snake();

    }
}