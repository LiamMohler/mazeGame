/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;
//Liam Mohler

import java.io.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Maze extends JFrame implements Runnable {

    static final int numRows = 21;
    static final int numColumns = 21;
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + numColumns*30;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + numRows*30;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;
    int numCoins = 10;
    int points[] = new int [numCoins];
    Coin coins[] = new Coin[numCoins];
    static final int PATH = 0;
    static final int  WALL = 1;
    static int board[][] = {
        {WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL},
        {WALL,WALL,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL},
        {WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,PATH,PATH,PATH,WALL},
        {WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL},
        {WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,PATH,PATH,WALL,PATH,WALL},
        {WALL,PATH,PATH,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,WALL,WALL,PATH,PATH,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,PATH,WALL,WALL,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,WALL},
        {WALL,WALL,PATH,WALL,WALL,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL},
        {WALL,WALL,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL},
        {WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL}
                                                                                                                                                        
    };
//    int columnDir;
//    int rowDir;
    boolean gameOver;
    
    Character player;
    
    int timeCount;
    static Maze frame;
    public static void main(String[] args) {
        frame = new Maze();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Maze() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button

// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();

                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                
//Change the direction of the snake.
                if (e.VK_UP == e.getKeyCode()) {
                    if (board[player.currentRow - 1][player.currentColumn] == PATH)
                    player.moveUp();
                } else if (e.VK_DOWN == e.getKeyCode()) {
                    if (board[player.currentRow + 1][player.currentColumn] == PATH)
                    player.moveDown();
                } else if (e.VK_LEFT == e.getKeyCode()) {
                    if (board[player.currentRow][player.currentColumn -1] == PATH)
                    player.moveLeft();
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                    if (board[player.currentRow][player.currentColumn + 1] == PATH)
                    player.moveRight();
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        
        g.setColor(Color.red);
//horizontal lines
        for (int zi=1;zi<numRows;zi++)
        {
            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
            getX(getWidth2()) ,getY(0)+zi*getHeight2()/numRows );
        }
//vertical lines
        for (int zi=1;zi<numColumns;zi++)
        {
            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
            getX(0)+zi*getWidth2()/numColumns,getY(getHeight2())  );
        }
        
//Display the objects of the board
        for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                if (board[zrow][zcolumn] == WALL)
                {
                    g.setColor(Color.gray);
                    g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                    getY(0)+zrow*getHeight2()/numRows,
                    getWidth2()/numColumns,
                    getHeight2()/numRows);

                }
            }
        }
        for(int i = 0; i <numCoins;i++)
        {
            g.setColor(Color.yellow);
            g.fillRect(getX(0)+coins[i].currentColumn*getWidth2()/numColumns,
            getY(0)+coins[i].currentRow*getHeight2()/numRows,
            getWidth2()/numColumns,
            getHeight2()/numRows);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("" + points[i],getX(0)+coins[i].currentColumn*getWidth2()/numColumns+11,
            getY(0)+coins[i].currentRow*getHeight2()/numRows+23);
        }
        {
            g.setColor(Color.red);
            g.fillRect(getX(0)+player.currentColumn*getWidth2()/numColumns,
            getY(0)+player.currentRow*getHeight2()/numRows,
            getWidth2()/numColumns,
            getHeight2()/numRows);
        }


        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .2;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        player = new Character(); 
        player.currentRow = (int)(Math.random()*numRows);
        player.currentColumn = (int)(Math.random()*numColumns);
        
        for (int zrow = 0;zrow < numRows;zrow++)
        {

            for (int zcolumn = 0;zcolumn < numColumns ; zcolumn++)
            {

                for(;board[player.currentRow][player.currentColumn] == WALL;)
                {
                    player.currentRow =  (int)(Math.random()*numRows + 0);
                    player.currentColumn =  (int)(Math.random()*numColumns + 0);      
                }
            }
        }
        for (int i = 0;i < numCoins;i++)
        {
            coins[i] = new Coin();
            coins[i].currentRow = (int)(Math.random()*numRows + 0);
            coins[i].currentColumn = (int)(Math.random()*numColumns + 0);
            points[i] = (int)(Math.random()*6 + 1);
            for(;board[coins[i].currentRow][coins[i].currentColumn] == WALL;)
            {
                    coins[i].currentRow =  (int)(Math.random()*numRows + 0);
                    coins[i].currentColumn =  (int)(Math.random()*numColumns + 0);      
            }
        }
        timeCount = 0;
//        columnDir = 0;
//        rowDir = 0;
        gameOver = false;

    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
         
            reset();
        }
//
//        player.currentRow += rowDir;
//        player.currentColumn += columnDir;
//        
//        rowDir = 0;
//        columnDir = 0;
        
          
        timeCount++;
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }


/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}
class Character{
    int currentRow;
    int currentColumn;
    public void moveRight()
    {
        currentColumn++;
    }
    public void moveLeft()
    {
        currentColumn--;
    }
    public void moveUp()
    {
        currentRow--;
    }
    public void moveDown()
    {
        currentRow++;
    }
    
}
class Coin
{
    int currentRow;
    int currentColumn;
}
