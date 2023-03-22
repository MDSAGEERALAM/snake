import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT = 400;
    int B_WIDTH = 400;
    int MAX_DOTS = 1600; //maxdots in board
    int DOT_SIZE = 10;  // size of one dots
    int DOTS; // head , body , tail in initial
    int[] x = new int[MAX_DOTS];
    int[] y = new int[MAX_DOTS];

    int apple_x;
    int apple_y;
    int s=0;
    Image head, body, apple;
    Timer timer;
    int Delay = 180;  // it means that timer will be increases in 0.3 second
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;  // it means that game is going on

    Board() {
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setBackground(Color.black); // set background color of board
        initGame();
        loadImages();
    }

    // Initilize game
    public void initGame() {
        DOTS = 3;
        // Initilize snake position on board
        x[0] = 250;
        y[0] = 250;
        for (int i = 1; i < DOTS; i++) {
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];    // if snake move in x direction then only change in x and y will remain same
        }
        // apple position initilize
        //  apple_x = 150;
        // apple_y = 150;
        locateApple();
        timer = new Timer(Delay, this); // board class is listner of this
        timer.start();
    }

    // load images from resources folder to Image object
    public void loadImages() {
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

    //draw image at snake and apple position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else g.drawImage(body, x[i], y[i], this);
            }
        } else {
            gameOver(g);
            timer.stop();
        }
    }

    public void locateApple() {
        apple_x = ((int) (Math.random() * 39)) * DOT_SIZE; // this will give random value 0 to 39
        apple_y = ((int) (Math.random() * 39)) * DOT_SIZE;
    }

    // check collosion with border and body
    public void checkCollision() {
        // collosion with body check
        for (int i = 1; i < DOTS; i++) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i])    // it means that head and body collide
            {
                inGame = false;
            }
        }
        if (x[0] < 0 || x[0] >= B_WIDTH || y[0] < 0 || y[0] >= B_HEIGHT)  // check with boarder
        {
            inGame = false;
        }
    }

    // display game and score
    public void gameOver(Graphics g) {
        String msg = "Game Over Dear";
        int score = (DOTS - 3) * s;
        String scoremsg = "Score:" + Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fontMetrics.stringWidth(msg)) / 2, (B_HEIGHT /2)-50);
        g.drawString(scoremsg, (B_WIDTH - fontMetrics.stringWidth(scoremsg)) / 2,   (B_HEIGHT /2)+50);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    // make snake move
    public void move() {
        for (int i = DOTS - 1; i >= 1; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (downDirection) {
            y[0] = y[0] + DOT_SIZE;  // it means that move downdirection
        }
    }

    // make snake eat food(apple)
    public void checkApple() {
        if (apple_x == x[0] && apple_y == y[0])  // it means that collision with head to the apple
        {
            DOTS++;
            s=s+5;
            if(Delay > 90)
            {
                Delay=Delay-30;
            }
            locateApple();
        }
    }

    //Implement control of snake game
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                leftDirection = false;
                rightDirection = false;
                upDirection = true;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}

