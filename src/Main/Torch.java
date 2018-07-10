package Main;

/**
 * Created by alxye on 01-Jul-18.
 */
public class Torch extends Thread {
    private float change = 0.0f;
    public Torch(float c) {
        change = c;
    }
    public void run() {
        for (int i = 0; i < 100; i++) {
            Content.radius += change;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
