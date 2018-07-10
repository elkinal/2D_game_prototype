package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by alxye on 30-Jun-18.
 */
public class Program {

    public static int screenHeight;
    public static int screenWidth;
    public static double scale = 1;
    public static JFrame frame = new JFrame();

    public static void main(String[] args) {
//        JFrame frame = new JFrame();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        ImageIcon gameIcon = new ImageIcon("C:\\Users\\alxye\\Desktop\\tiles\\player.png");

        screenHeight = bounds.height;
        screenWidth = bounds.width;

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Scary Walrus 1");
        frame.add(new Content());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(screenWidth,screenHeight);
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        frame.getContentPane().setCursor(blankCursor);

        frame.setIconImage(gameIcon.getImage());
        frame.setVisible(true);

    }
}
