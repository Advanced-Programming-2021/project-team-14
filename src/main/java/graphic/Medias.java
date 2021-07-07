package graphic;

import sample.MainGraphic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public enum Medias {
    BACKGROUND("background.wav"),
    CHOMP("chomp.wav"),
    INTRO("intro.wav");


    private static boolean mute = false;
    private String name;
    private Clip clip;

    Medias(String url) {
        this.name = url;
        setMedia();
    }

    public static void mute() {
        mute = !mute;
    }

    private void setMedia() {
        URL url = MainGraphic.class.getResource("audio/" + name);
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            this.clip = clip;
        } catch (Exception ignored) {
        }
    }


    public void play() {
        if (!mute)
            clip.loop(-1);
    }

    public void pause() {
        clip.stop();
    }

    public void loop() {
        if (!mute)
            clip.loop(-1);
    }

}
