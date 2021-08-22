package ballGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play=false;
    private int score=0;

    private int totalBricks=50;

    private Timer timer;
    private int delay=0;

    private int playerX=380;

    private int ballposX=425;
    private int ballposY=500;
    private int ballXdir=-1;
    private int ballYdir=-2;

    private MapGenerator map;

    public Gameplay() {
        map=new MapGenerator(5,10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer= new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,892,792);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,792);
        g.fillRect(0,0,892,3);
        g.fillRect(883,0,3,792);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("Score:"+score,750,30);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX,752,100,8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks<=0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won",330,400);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",330,450);
        }

        if(ballposY>770){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, You Scored: "+score,290,400);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",330,450);
        }

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20 ,20).intersects(new Rectangle(playerX,750,110,8))){
                ballYdir=-ballYdir;
            }
            A: for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX= j* map.brickWidth+80;
                        int brickY= i* map.brickHeight+50;
                        int brickWidth= map.brickWidth;
                        int brickHeight=map.brickHeight;

                        Rectangle rect= new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect= new Rectangle(ballposX,ballposY,20,20);
                       // Rectangle brickRect=rect;

                        if(ballRect.intersects(rect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballposX+19<=rect.x || ballposX+1>=rect.x + rect.width){
                                ballXdir=-ballXdir;
                            }
                            else{
                                ballYdir=-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if(ballposX<0){
                ballXdir= -ballXdir;
            }
            if(ballposY<0){
                ballYdir= -ballYdir;
            }
            if(ballposX>870){
                ballXdir= -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX >=800){
                playerX = 800;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX <=10){
                playerX = 10;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX=425;
                ballposY=500;
                ballXdir=-1;
                ballYdir=-2;
                playerX=380;
                score=0;
                totalBricks=50;
                map= new MapGenerator(5,10);

            }
        }
    }

    public void moveRight(){
        play=true;
        playerX+=20;
    }
    public void moveLeft(){
        play=true;
        playerX-=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
