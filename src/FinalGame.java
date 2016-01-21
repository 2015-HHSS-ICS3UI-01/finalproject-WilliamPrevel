/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author lamon
 */
public class FinalGame extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // player position variables
    int x = 100;
    int y = 500;
    //mouse variables
    int mouseX = 0;
    int mouseY = 0;
    boolean buttonPressed = false;
    // block
    ArrayList<Rectangle> blocks = new ArrayList<>();
    ArrayList<Rectangle> blocks2 = new ArrayList<>();
    ArrayList<Rectangle> blocks3 = new ArrayList<>();
    // another player
    Rectangle player = new Rectangle(0, 600, 25, 25);
    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    int gravity = 1;
    int frameCount = 0;
    //keyboard variables
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    float angle = 0;
    int camx = 0;
    BufferedImage colorWheel = loadImage("color_wheel_730.png");

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error Loading" + filename);
        }
        return img;

    }
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        g.setColor(Color.BLACK);

        // go through each block
        for (Rectangle block : blocks) {
            // draw the block
            g.fillRect(block.x, block.y, block.width, block.height);
        }
        g.setColor(Color.LIGHT_GRAY);
        for (Rectangle block : blocks2) {
            // draw the block2
            g.fillRect(block.x, block.y, block.width, block.height);
        }

        g.setColor(Color.WHITE);
        for (Rectangle block : blocks3) {
            // draw the block2
            g.fillRect(block.x, block.y, block.width, block.height);
        }

        g.setColor(Color.WHITE);
        for (Rectangle block : blocks2) {
            // draw the block2
            g.fillRect(block.x, block.y, block.width, block.height);
        }
        g2d.translate(player.x + player.width / 2, player.y + player.height / 2);
        g2d.rotate(Math.toRadians(angle));
        g.drawImage(colorWheel, -player.width / 2, -player.height / 2, player.width, player.height, null);
        g2d.rotate(Math.toRadians(-angle));
        g2d.translate(-player.x - player.width / 2, -player.y - player.height / 2);

        if (buttonPressed) {
            g.setColor(Color.GREEN);
            g.fillRect(75, 550, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(200, 575, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(225, 525, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(0, 75, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(0, 450, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(100, 425, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(150, 425, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(200, 425, 25, 25);
            g.setColor(Color.GREEN);
            g.fillRect(225, 400, 25, 25);
        }
        if (!buttonPressed) {
            g.setColor(Color.RED);
            g.fillRect(150, 525, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(200, 550, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(225, 550, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(225, 500, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(200, 500, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(25, 425, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(125, 425, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(175, 425, 25, 25);
            g.setColor(Color.RED);
            g.fillRect(225, 425, 25, 25);
        }

        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // initial things to do before game starts
        //add blocks
        blocks.add(new Rectangle(50, 575, 25, 25));
        blocks.add(new Rectangle(75, 575, 25, 25));
        blocks.add(new Rectangle(100, 550, 50, 50));
        blocks.add(new Rectangle(100, 525, 25, 25));
        blocks.add(new Rectangle(125, 525, 25, 25));
        blocks.add(new Rectangle(0, 475, 175, 25));
        blocks.add(new Rectangle(175, 475, 25, 100));
        blocks.add(new Rectangle(250, 50, 25, HEIGHT));
        blocks.add(new Rectangle(-25, 0, 25, HEIGHT));
        blocks.add(new Rectangle(0, -25, WIDTH, 0));
        blocks.add(new Rectangle(25, 50, 250, 25));
        blocks.add(new Rectangle(0, 100, 150, 25));
        blocks.add(new Rectangle(50, 425, 50, 25));
        blocks.add(new Rectangle(50, 425, 50, 25));
        blocks.add(new Rectangle(0, 375, 200, 25));
        blocks2.add(new Rectangle(75, 550, 25, 25));
        blocks2.add(new Rectangle(200, 575, 25, 25));
        blocks2.add(new Rectangle(225, 525, 25, 25));
        blocks2.add(new Rectangle(0, 75, 25, 25));
        blocks2.add(new Rectangle(0, 450, 25, 25));
        blocks2.add(new Rectangle(100, 425, 25, 25));
        blocks2.add(new Rectangle(150, 425, 25, 25));
        blocks2.add(new Rectangle(200, 425, 25, 25));
        blocks2.add(new Rectangle(225, 425, 25, 25));
        blocks2.add(new Rectangle(225, 400, 25, 25));
        blocks3.add(new Rectangle(150, 525, 25, 25));
        blocks3.add(new Rectangle(200, 550, 50, 25));
        blocks3.add(new Rectangle(200, 500, 50, 25));
        blocks3.add(new Rectangle(25, 425, 25, 25));
        blocks3.add(new Rectangle(125, 425, 25, 25));
        blocks3.add(new Rectangle(175, 425, 25, 25));
        blocks3.add(new Rectangle(225, 425, 25, 25));

        // END INITIAL THINGS TO DO


        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            if (left) {
                moveX = -2;
                angle--;
            } else if (right) {
                moveX = 2;
                angle++;
            } else {
                moveX = 0;
            }
            frameCount++;

            if (frameCount >= 1) {
                // gravity pulling player down
                moveY = moveY + gravity;
                frameCount = 0;
            }

            //jumping
            // jump being pressed and not in the air
            if (jump && !prevJump && !inAir) {
                // make a big change in y direction
                moveY = -7;
                inAir = true;
            }
            // keeps track of jump key changes
            prevJump = jump;

            // move the player
            player.x = player.x + moveX;
            player.y = player.y + moveY;


            // if feet of player become lower than the screen   
            if (player.y + player.height > HEIGHT) {
                // stops the falling
                player.y = HEIGHT - player.height;
                moveY = 0;
                inAir = false;
            }


            if (angle >= 360) {
                angle = angle - 360;
            } else if (angle < 0) {
                angle = 360 + angle;
            }

            // go through all blocks
            for (Rectangle block : blocks) {

                // is the player hitting a block
                if (player.intersects(block)) {
                    // get the collision rectangle
                    Rectangle intersection = player.intersection(block);

                    // fix the x movement
                    if (intersection.width < intersection.height) {
                        // player on the left
                        if (player.x < block.x) {
                            // move the player the overlap
                            player.x = player.x - intersection.width;
                        } else {
                            player.x = player.x + intersection.width;
                        }
                    } else { // fix the y
                        // hit the block with my head
                        if (player.y > block.y) {
                            player.y = player.y + intersection.height;
                            moveY = 0;
                        } else {
                            player.y = player.y - intersection.height;
                            moveY = 0;
                            inAir = false;
                        }
                    }
                }
            }

            if (buttonPressed) {
                for (Rectangle block : blocks2) {
                    // is the player hitting a block
                    if (player.intersects(block)) {
                        // get the collision rectangle
                        Rectangle intersection = player.intersection(block);

                        // fix the x movement
                        if (intersection.width < intersection.height) {
                            // player on the left
                            if (player.x < block.x) {
                                // move the player the overlap
                                player.x = player.x - intersection.width;
                            } else {
                                player.x = player.x + intersection.width;
                            }
                        } else { // fix the y
                            // hit the block with my head
                            if (player.y > block.y) {
                                player.y = player.y + intersection.height;
                                moveY = 0;
                            } else {
                                player.y = player.y - intersection.height;
                                moveY = 0;
                                inAir = false;
                            }
                        }
                    }
                }
            }

            if (!buttonPressed) {
                for (Rectangle block : blocks3) {
                    // is the player hitting a block
                    if (player.intersects(block)) {
                        // get the collision rectangle
                        Rectangle intersection = player.intersection(block);

                        // fix the x movement
                        if (intersection.width < intersection.height) {
                            // player on the left
                            if (player.x < block.x) {
                                // move the player the overlap
                                player.x = player.x - intersection.width;
                            } else {
                                player.x = player.x + intersection.width;
                            }
                        } else { // fix the y
                            // hit the block with my head
                            if (player.y > block.y) {
                                player.y = player.y + intersection.height;
                                moveY = 0;
                            } else {
                                player.y = player.y - intersection.height;
                                moveY = 0;
                                inAir = false;
                            }
                        }
                    }
                }
            }


            if (player.x > WIDTH / 2) {
                camx = player.x - WIDTH / 2;
            } else {
                camx = 0;
            }
            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        FinalGame game = new FinalGame();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        //add the listeners
        frame.addKeyListener(game); //keyboard
        game.addMouseListener(game); // mouse
        game.addMouseMotionListener(game); // mouse

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = true;
        } else if (key == KeyEvent.VK_D) {
            right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = false;
        } else if (key == KeyEvent.VK_D) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // if holding down the left button
        if (me.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // if released a button
        if (me.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}