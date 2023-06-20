import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    int B_HEIGHT=400;
    int B_WIDTH=400;

    int MAX_DOTS=1600;
    int DOT_SIZE=10;
    int DOTS;
    int x[]=new int[MAX_DOTS];
    int y[]=new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    Image body, head, apple;
    Timer timer;
    int DELAY=150;
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDiretion=false;
    boolean inGame=true;
    Board()
    {
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }
    public void initGame(){
        DOTS=3;
        x[0]=200;
        y[0]=200;
        for(int i=1; i<DOTS; i++)
        {
            x[i]=x[0]+DOT_SIZE*i;
            y[i]=y[0];
        }
        locateApple();
        timer =new Timer(DELAY, this);
        timer.start();
    }
    //load images from resources to image object
    public void loadImages(){
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body=bodyIcon.getImage();

        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head=headIcon.getImage();


        ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();

    }
    //draw images apple and snake position
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);

    }
    public void doDrawing(Graphics g)
    {
        if(inGame)
        {

            g.drawImage(apple, apple_x, apple_y, this);
            for(int i=0; i<DOTS; i++)
            {
                if(i==0)
                {
                    g.drawImage(head, x[0], y[0], this);
                }
                else{
                    g.drawImage(body, x[i],y[i], this);
                }
            }
        }
        else {
            gameOver(g);
            timer.stop();
        }
    }
    //Randomize apple's position
    public void locateApple()
    {
        apple_x=((int)(Math.random()*39))*DOT_SIZE;
        apple_y=((int)(Math.random()*39))*DOT_SIZE;
    }

    //check collisions with border and body
    public void checkCollisions()
    {
        for(int i=1; i<DOTS;i++)
        {
            //check for body
            if(i>3&&x[0]==x[i]&&y[0]==y[i])
            {
                inGame=false;
            }
        }
        //check for border
        if(x[0]<0)
        {
            inGame=false;
        }
        if(x[0]>B_WIDTH)
        {
            inGame=false;
        }
        if(y[0]<0)
        {
            inGame=false;
        }
        if(y[0]>B_HEIGHT)
        {
            inGame=false;
        }

    }

    ///Display  game ver msg
    public void  gameOver(Graphics g)
    {
        String msg="Game Over";
        int score=(DOTS-3)*100;
        String scoremsg="Score:"+Integer.toString(score);
        Font small=new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics=getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,B_HEIGHT/4);
        g.drawString(scoremsg,(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2,3*(B_HEIGHT/4));

    }

    @Override
    public void  actionPerformed(ActionEvent actionEvent){
        if(inGame)
        {
            checkApple();;
            move();
            checkCollisions();
        }
        repaint();
    }
    //function make snake move
    public void move(){
        for(int i=DOTS-1; i>0; i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0]-=DOT_SIZE;
        }
        if(rightDirection)
        {
            x[0]+=DOT_SIZE;
        }
        if(upDirection)
        {
            y[0]-=DOT_SIZE;
        }
        if(downDiretion)
        {
            y[0]+=DOT_SIZE;
        }

    }
    //make snake eat  food
    public void checkApple(){
        if(apple_x==x[0]&&apple_y==y[0])
        {
            DOTS++;
            locateApple();
        }
    }

    //impliments control
    private  class TAdapter extends KeyAdapter{
        @Override
        public void  keyPressed(KeyEvent KeyEvent){
            int key=KeyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&!rightDirection)
            {
                leftDirection=true;
                upDirection=false;
                downDiretion=false;
            }
            if(key==KeyEvent.VK_RIGHT&&!leftDirection)
            {
                rightDirection=true;
                upDirection=false;
                downDiretion=false;
            }
            if(key==KeyEvent.VK_UP&&!downDiretion)
            {
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&!upDirection)
            {
                leftDirection=false;
                downDiretion=true;
                rightDirection=false;
            }
        }

    }


}
