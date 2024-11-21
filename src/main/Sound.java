package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURLs = new URL[30];

    public Sound() {
         soundURLs[0] = getClass().getResource("/resources/sound/Theme.wav");
         soundURLs[1] = getClass().getResource("/resources/sound/key.wav");
         soundURLs[2] = getClass().getResource("/resources/sound/dooropen.wav");
         soundURLs[3] = getClass().getResource("/resources/sound/chest.wav");
         soundURLs[4] = getClass().getResource("/resources/sound/powerup.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURLs[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
