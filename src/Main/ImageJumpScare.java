package Main;

import java.awt.image.BufferedImage;

/**
 * Created by alxye on 01-Jul-18.
 */
public class ImageJumpScare extends Thread {
    private int time;
    private int duration;
    public ImageJumpScare(int delay, int d) {
        time = delay;
        duration = d;
    }
    public void run() {
        try {
            sleep(time);
            Content.jumpScareOverlay = true;
            Content.playJumpScareSound1();
            sleep(duration);
            Content.jumpScareOverlay = false;
            Content.resetPos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
