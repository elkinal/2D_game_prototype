package Main;

/**
 * Created by alxye on 02-Jul-18.
 */
public class HideHint extends Thread {
    public void run() {
        try {
            Thread.sleep(1700);
            Content.showHint = false;
            Content.controlDemonTriggerSound = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
