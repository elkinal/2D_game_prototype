package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by alxye on 30-Jun-18.
 */
public class Content extends JPanel implements ActionListener {
    private static Timer t;
    public static Level currentLevel = Levels.level1;

    public static boolean displayTrue = true;

    Level levelsList[] = {Levels.level1, Levels.level2, Levels.level3, Levels.level4, Levels.level5, Levels.level6, Levels.level7, Levels.level8, Levels.level9};

    public static int tileSize = Program.screenWidth/18; //50
    private static int XGridSize = tileSize * currentLevel.getContent()[0].length;
    private static int YGridSize = tileSize * currentLevel.getContent().length;
    public static int YOffset = (Program.screenHeight + 8 * tileSize) / 2 - YGridSize + tileSize;
    public static int XOffset = Program.screenWidth / 2 - XGridSize / 2;

    private static Random random = new Random();
    //centering the player on the screen
    public static int XPlayerOffset = Program.screenWidth / 2 - tileSize / 2;
    public static int YPlayerOffset = Program.screenHeight / 2 - tileSize / 2;
    private static int maxSpeed = 10;

    public static boolean showHint = false;
    private static boolean showControls = false;

    private static BufferedImage tile100; //coin rock tiles
    private static BufferedImage tile99; //battery rock tiles
    private static BufferedImage tile0; //grass
    private static BufferedImage tile1; //sand
    private static BufferedImage tile2; //rock
    private static BufferedImage tile3; //mud
    private static BufferedImage tile4; //wall
    private static BufferedImage tile5; //ice
    private static BufferedImage tile6; //snow
    private static BufferedImage tile7; //small grass snow
    private static BufferedImage tile8; //large grass snow
    private static BufferedImage tile9; //planks
    private static BufferedImage tile11; //large rocks in sand
    private static BufferedImage tile12; //small rocks in sand
    private static BufferedImage tile13; //broken tiles
    private static BufferedImage tile14; //medium rocks in sand
    private static BufferedImage tile19; //fake sand with jump-scare event
    private static BufferedImage tile29; //bloody stone tile with jump-scare event
    private static BufferedImage credits1; /*the first image that appears after the game has been completed,
                                           you need to change this as currently the credits are bad
                                           there is not enough information in them */
    private static boolean torchStatus = true;
    private static float battery = 100f;


    private static BufferedImage player;
    private static BufferedImage player_right;
    private static BufferedImage player_left;
    private static BufferedImage demon;

    public static double demonYMod = 0;
    /*public static double demonX = tileSize * 3 + XOffset;
    public static double demonY = tileSize * 3 + YOffset + demonYMod;*/
    public static double demonX = tileSize * currentLevel.getDemonX() + XOffset;
    public static double demonY = tileSize * currentLevel.getDemonY() + YOffset + demonYMod;

    public static boolean demonProximity = false;

    public static boolean jumpScareOverlay = false;
    public static boolean gameState = true;

    public static BufferedImage jumpscare1;
    public static BufferedImage jumpscare2;
    public static BufferedImage jumpscare3;
    public static BufferedImage jumpscare4;

    private static int collectedCoins = 0;
    public static boolean controlDemonTriggerSound = true;

    public static BufferedImage[] nextJumpScare = new BufferedImage[2];

    private static int velX = 0;
    private static int velY = 0;

    private Font font = new Font("Sans-Serif", Font.PLAIN, 40);
    private Font fontSmall = new Font("Roboto", Font.PLAIN, 30); //sort out the fonts and make them compatible with most computers
    private Font fontLarge = new Font("Sans-Serif", Font.PLAIN, 100);
    //lighting
    private float lightX = 100f;
    private float lightY = 100f;
    public static float radius = 300; //leave at 300
    private static float minRadius = 100;
    private static float maxRadius = 400;

    private static Thread torchThread = new Thread(new Torch(-300/99)); //remove this later - this is the default
    private static Thread jumpScareThread = new Thread(new ImageJumpScare(1000, 1000)); //remove this later - this is the default
    private static Thread jumpScareThreadInstant = new Thread(new ImageJumpScare(0, 1000)); //remove this later - this is the default
    public static Thread demonMovementThread = new Thread(new DemonMovement());

    public Content() {
        t = new Timer(5, this);
        t.start();
        super.setDoubleBuffered(true);
        //starting the background music for the game
        Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\house_of_leaves.wav"));
        setBackground(Color.BLACK);
        demonMovementThread.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
//        setDemonLocationTile(10,25);
        //loading different tiles into buffered images

        try {
            tile0 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\grass_texture.jpg"));
            tile1 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\sand_texture.jpg"));
            tile19 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\sand_texture.jpg"));
            tile2 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\rock_texture.jpg"));
            tile3 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\mud_texture.jpg"));
            tile4 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\brick_texture.png"));
            tile5 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\ice_texture.jpg"));
            tile6 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\snow_texture.jpg"));
            tile7 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\small_grass_snow.jpg"));
            tile8 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\large_grass_snow.jpg"));
            tile9 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\plank_texture.jpg"));
            tile11 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\sand_pebbles_large_texture.jpg"));
            tile13 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\broken_tile_texture.jpg"));
            tile12 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\sand_pebbles_small_texture.jpg"));
            tile14 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\sand_pebbles_medium_texture.jpg"));
            tile29 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\blood_tile.png"));
            tile99 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\battery_rock.png"));
            tile100 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\coin_rock.png"));
            credits1 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\credits1.png")); //fix this plz
            player_left = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\player.png"));
            player_right = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\player_flipped.png"));
            jumpscare1 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\jumpscare_image_1.jpg"));
            jumpscare2 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\jumpscare_image_2.jpg"));
            jumpscare3 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\jumpscare_image_3.jpg"));
            jumpscare4 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\jumpscare_image_4.jpg"));
            demon = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\demon.png"));
            nextJumpScare[0] = jumpscare1;
            nextJumpScare[1] = jumpscare4;

        } catch (IOException e) {
            e.printStackTrace();
        }
        //make sure that the brightness of the beam of light can be changed with the mouse wheel
        this.addMouseWheelListener(e -> {
            radius += e.getUnitsToScroll()*2;
        });
        //this toggles the torch ON/OFF
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.isMetaDown()) {
                    torchStatus = !torchStatus;
                    if(battery <= 0) {
                        jumpScareThread.start();
                        gameOver();
                    }
                    Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\torch_sound.wav"));
                }
            }
        });
        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_W) {
                    velY += 1;
                    if(velY < 0)
                        velY = 0;
                    repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_S) {
                    velY -= 1;
                    if(velY > 0)
                        velY = 0;
                    repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_A) {
                    velX -= 1;
                    if(velX > 0)
                        velX = 0;
                    repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_D) {
                    velX += 1;
                    if(velX < 0)
                        velX = 0;
                    repaint();
                }
                //show control panel when the ESC key is pressed
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    showControls = !showControls;
                }
            }
            public void keyReleased(KeyEvent e) {

            }
        });

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
/*        graphics2D.setColor(new Color(179, 143, 0));
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawLine(500, 0, 500, 1000);
        graphics2D.drawLine(0, 500, 1000, 500);*/
        if(displayTrue) {
            graphics2D.setColor(Color.BLACK);
            for (int i = 0; i < currentLevel.getContent().length; i++) {
                for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
//                System.out.print(currentLevel.getContent()[i][j]);
                    if (currentLevel.getContent()[i][j] == 0)
                        graphics2D.drawImage(tile0, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 1 || currentLevel.getContent()[i][j] == 19)
                        graphics2D.drawImage(tile1, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 29)
                        graphics2D.drawImage(tile29, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 100)
                        graphics2D.drawImage(tile100, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 99)
                        graphics2D.drawImage(tile99, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 2)
                        graphics2D.drawImage(tile2, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 3)
                        graphics2D.drawImage(tile3, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 4)
                        graphics2D.drawImage(tile4, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 5)
                        graphics2D.drawImage(tile5, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 6)
                        graphics2D.drawImage(tile6, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 7)
                        graphics2D.drawImage(tile7, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 8)
                        graphics2D.drawImage(tile8, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 9)
                        graphics2D.drawImage(tile9, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 11)
                        graphics2D.drawImage(tile11, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 12)
                        graphics2D.drawImage(tile12, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 14)
                        graphics2D.drawImage(tile14, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 13)
                        graphics2D.drawImage(tile13, tileSize * j + XOffset, tileSize * i + YOffset, tileSize, tileSize, null);


                }
            }
            graphics2D.drawImage(player, XPlayerOffset, YPlayerOffset, tileSize, tileSize, null);
            Point2D center = new Point2D.Float(XPlayerOffset + tileSize / 2, YPlayerOffset + tileSize / 2);
            float[] distance = {0.0f, 1.0f};
            Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.black};
            RadialGradientPaint p = new RadialGradientPaint(center, radius, distance, colors);
            //torch status regulated here
            if (torchStatus && battery > 0) {
                graphics2D.setPaint(p);
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .98f)); // set this to .95f
                battery -= (radius * 0.0001); //reduces the battery at a rate that is directly proportional to the radius of the light
            }


            //fix a glitch where you seem to get faster when the light is turned off

            graphics2D.drawImage(demon, (int) demonX, (int) demonY, tileSize, tileSize, null);


            graphics2D.fillRect(0, 0, Program.screenWidth, Program.screenHeight);

            //checking if any of the jump-scares have been activated
            if (jumpScareOverlay) {
            /*if(randInt(1, 2) == 1)
                 graphics2D.drawImage(jumpscare1, 0, 0, Program.screenWidth, Program.screenHeight, null);
            else
                 graphics2D.drawImage(jumpscare4, 0, 0, Program.screenWidth, Program.screenHeight, null);*/
                torchStatus = false;
                if (randInt(0, 1) == 1)
                    graphics2D.drawImage(nextJumpScare[0], 0, 0, Program.screenWidth, Program.screenHeight, null);
                if (randInt(0, 1) == 0)
                    graphics2D.drawImage(nextJumpScare[1], 0, 0, Program.screenWidth, Program.screenHeight, null);

            }
            if (demonProximity) {
                //this creates the static effect to make the dark more interesting - add random colors as well
                //make static amount proportional to the distance from the demon
                //set the static amount to 350 - it is better
                for (int i = 0; i < 300 - DemonMovement.distance; i++) {
                    if (randInt(0, 2) == 0)
                        graphics2D.setColor(new Color(0, randInt(0, 150), 0));
                    if (randInt(0, 2) == 1)
                        graphics2D.setColor(new Color(randInt(0, 150), 0, 0));
                    if (randInt(0, 2) == 2)
                        graphics2D.setColor(new Color(0, 0, randInt(0, 150)));
                    int yAxis = randInt(0, Program.screenHeight);
                    int xAxis = randInt(0, Program.screenWidth);
                    graphics2D.drawLine(xAxis, yAxis, xAxis + 7, yAxis);
                    if (controlDemonTriggerSound) {
                        torchThread = new Thread(new Torch(1000)); //accidentaly created a storm "lightning effect" - it is useful
                        torchThread.start();
                        //should play a "thunder" sound here...
                        Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\trigger_sound.wav"));
                        controlDemonTriggerSound = false;
                    }
                }
            }


            //shows the charge that the battery has
            graphics2D.setColor(Color.GREEN);
//        graphics2D.drawLine(demonX, demonY, (Content.XOffset - Content.getCurrentLocationX()), (Content.YOffset - Content.getCurrentLocationY()- Program.screenHeight/2));
//        graphics2D.drawLine(demonX+tileSize/2, demonY+tileSize/2, XPlayerOffset+tileSize/2, YPlayerOffset+tileSize/2);
            graphics2D.setFont(font);
            graphics2D.drawString(String.valueOf((int) battery + "%"), 10, 40);
            if (showHint)
                graphics2D.drawString(currentLevel.getName(), Program.screenWidth / 2 - 190, Program.screenHeight / 2 - 20);
            graphics2D.drawString(String.valueOf(collectedCoins), Program.screenWidth - 150, 40);


            //this shows the user the controls when he pressed the ESC key
            //the controls should contain the MOVEMENT, POWER-UP INFO, and LEVEL HINT

            if (showControls) {
                graphics2D.setColor(Color.RED);
                graphics2D.drawString("Controls:", 20, 150);
                graphics2D.setFont(fontSmall);
                graphics2D.drawString("W - move upwards", 20, 200);
                graphics2D.drawString("S - move downwards", 20, 250);
                graphics2D.drawString("A - move left", 20, 300);
                graphics2D.drawString("D - move right", 20, 350);
                graphics2D.drawString("Mouse Wheel - Change torch size", 20, 450);
                graphics2D.drawString("Right Click - turn torch ON/OFF", 20, 500);
                graphics2D.drawString("A larger beam of light wastes more battery", 20, 550);

            }
//        demonX = tileSize * 10 + XOffset;
            //have no idea what this does, but it seems to help... find out!
            graphics2D.dispose();
        }
        else {
            graphics2D.drawImage(credits1, 0, 0, Program.screenWidth, Program.screenHeight, null);
            graphics2D.dispose();
        }

    }
    public static int getCurrentLocationY() {
        //YOffset - YPlayerOffset - literal cartesian displacement
        return (YOffset - YPlayerOffset-tileSize/2)/tileSize; //Y
    }
    public static int getCurrentLocationX() {
        //XOffset - XPlayerOffset - literal cartesian displacement
        return (XOffset - XPlayerOffset-tileSize/2)/tileSize; //X
    }
    public static void setDemonLocationTile(int i, int j) {
        demonX = tileSize * i + XOffset;
        demonY = tileSize * j + YOffset;

    }
    //fix this shit
    public static int getTileType(int x, int y) {
        return currentLevel.getContent()[Math.abs(y)][Math.abs(x)];

        /*catch (ArrayIndexOutOfBoundsException e) {
            gameOver();
            resetPos();
            return 0;
        }*/
    }
    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(getCurrentLocationX() + " " + getCurrentLocationY());
//        System.out.println("Tile Type" + getTileType(getCurrentLocationX(), getCurrentLocationY()));
//        System.out.println("Actual coordinates: " + Math.abs(getCurrentLocationX()) + " " + Math.abs(getCurrentLocationY()));
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 19 && gameState) {
            //This jump-scare will only run once
            torchThread.start();
            jumpScareThread.start();
            gameOver();
        }



        //        setDemonLocationTile(10,25);
        demonY = tileSize * currentLevel.getDemonY() + YOffset + (int)demonYMod;
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 29 && gameState) {
            //This jump-scare will only run once
//            torchThread.start();
            jumpScareThreadInstant.start();
            gameOver();
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 100 && gameState) {
            collectedCoins++;
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 2;
            Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\coin_pickup.wav"));
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 99 && gameState) {
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 2;
            Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\recharge_sound.wav"));
            battery += 20;
        }
        //makes sure that the radius of the torch light is within a certain range
        if(radius < minRadius)
            radius = minRadius;
        if(radius > maxRadius)
            radius = maxRadius;
        //checks if the player has collected all the coins, and has therefore completed the level
        if(currentLevel.getCoinAmount() - collectedCoins == 0) {
            System.out.println("You have collected all the coins");
            collectedCoins = 0;
            try {
                currentLevel = levelsList[currentLevel.getId() + 1];
                gameOver();
                resetPos();
            }
            catch (ArrayIndexOutOfBoundsException el) {
                System.out.println("The User has completed the game");
                displayTrue = false;
                //At this point the credits need to start rolling as the game is completed
            }
        }

        //collision detection
        if((YOffset - YPlayerOffset-tileSize)/tileSize == -4) //upper corners (center) coordinates
            velY=-2; //this should allow the player to transition to a new level
         else  if((YOffset - YPlayerOffset)/tileSize <= -currentLevel.getContent().length+5) //lower corner (center) coordinates [+1]
            velY=2;
        else if((XOffset - XPlayerOffset-tileSize)/tileSize >= 0) //left corner (center) coordinates
            velX=2;
        else if((XOffset - XPlayerOffset)/tileSize <= -currentLevel.getContent()[0].length+1) //right corner(center) coordinates
            velX=-2;
        //tjot
        else { //collisions slightly broken as there is an issue when the player becomes trapper in the upper right corner of walls
            //this issue has apparently been fixed by adding if/else statements. More testing is required to conclude this analysis
            if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset)/tileSize) == 4 ||
                    getTileType((XOffset - XPlayerOffset-tileSize)/tileSize,(YOffset - YPlayerOffset)/tileSize) == 4) {
                velY = -1;
            }
            else if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset-tileSize)/tileSize) == 4 ||
                    getTileType((XOffset - XPlayerOffset-tileSize)/tileSize,(YOffset - YPlayerOffset-tileSize)/tileSize) == 4) {
                velY = 1;
            }
            if(getTileType((XOffset - XPlayerOffset-tileSize)/tileSize, (YOffset - YPlayerOffset)/tileSize) == 4 ||
                    getTileType((XOffset - XPlayerOffset-tileSize)/tileSize, (YOffset - YPlayerOffset-tileSize)/tileSize) == 4) {
                velX = -1;
            }
            else if(getTileType((XOffset - XPlayerOffset)/tileSize, (YOffset - YPlayerOffset)/tileSize) == 4 ||
                    getTileType((XOffset - XPlayerOffset)/tileSize, (YOffset - YPlayerOffset-tileSize)/tileSize) == 4) {
                velX = 1;
            }

        }
        //checking if the player is stuck in the upper-right corner between two brick tiles

        /*if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset)/tileSize) == 4 ||
                getTileType((XOffset - XPlayerOffset-tileSize)/tileSize,(YOffset - YPlayerOffset)/tileSize) == 4) {
            System.out.println("The collision system has been triggered");
        }*/

        //collision detection with the wall tile
        //making sure that the player does not exceed the maximum speed allowed
        /*if(velX > maxSpeed) {
            velX = maxSpeed;
        }
        if(velY > maxSpeed) {
            velY = maxSpeed;
        }
        if(velX < -maxSpeed) {
            velX = -maxSpeed;
        }
        if(velY < -maxSpeed) {
            velY = -maxSpeed;
        }*/


        //movement for the demon

        //movement
        XPlayerOffset += velX;
        YOffset += velY;

        //making sure that the player skin changes according to the direction that the player is moving in
        if(velX > 1)
            player = player_right;
        else
            player = player_left;

        repaint();
    }

    public static void gameOver() {
        System.out.println("you lost, please try again matey!");
        gameState = false;
        //setting variables back to the default
        jumpScareOverlay = false;
    }
    public static void resetPos() {
        //returning the player back to their original location
//        YOffset = Program.screenHeight / 2 - YGridSize + tileSize;
        //resetting the player's spawn location
        YGridSize = tileSize * currentLevel.getContent().length;
        YOffset = (Program.screenHeight + 8 * tileSize) / 2 - YGridSize + tileSize;

        XOffset = Program.screenWidth / 2 - XGridSize / 2;
        XPlayerOffset = Program.screenWidth / 2 - tileSize / 2;
        YPlayerOffset = Program.screenHeight / 2 - tileSize / 2;
        //resetting event threads
        torchThread = new Thread(new Torch(-2.98f)); //remove this later - this is the default
        jumpScareThread = new Thread(new ImageJumpScare(1000, 1000)); //remove this later - this is the default
        jumpScareThreadInstant = new Thread(new ImageJumpScare(0, 1000)); //remove this later - this is the default

        radius = 300;
        velX = 0;
        velY = 0;
        battery = 100;
        //resetting the jumpscares
        if(randInt(0,1) == 1) {
            nextJumpScare[0] = jumpscare1;
            nextJumpScare[1] = jumpscare4;
        }
        else {
            nextJumpScare[0] = jumpscare2;
            nextJumpScare[1] = jumpscare3;
        }
        gameState = true;
        //resetting the demon location
        demonX = tileSize * currentLevel.getDemonX() + XOffset;
        demonYMod = 0;
        showHint = true;
        Thread hideHintThread = new Thread(new HideHint());
        hideHintThread.start();



    }
    public static void playJumpScareSound1() {
        Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\walrusGameData\\jumpscare_1.wav"));
    }
}
