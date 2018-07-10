package Main;

import java.io.File;

/**
 * Created by alxye on 01-Jul-18.
 */
public class DemonSound extends Thread {
    private boolean play = false;
    public DemonSound(boolean status) {
        play = status;
    }
    //this does not work and is currently useless. Consider deleting this
    public void run() {
        if(play) {
            Sound.playSound(new File("C:\\Users\\alxye\\Desktop\\tiles\\420.wav"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        play = false;
    }
}
