package Main;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alxye on 01-Jul-18.
 */
public class Sound {
    public static void playSound(File file) {
        InputStream in;
        try {
            in = new FileInputStream(file);
            AudioStream audios = new AudioStream(in);
            AudioPlayer.player.start(audios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
