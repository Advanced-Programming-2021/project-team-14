package graphic;

import sample.MainGraphic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public enum Medias {
    BACKGROUND("background.wav"),
    COIN_FLIP("coinflip.wav"),
    ATTACK("attack.wav"),
    BUY_ITEM("buyitem.wav"),
    CHANGE_PHASE("hover.wav"),
    PUT_CARD("putcard.wav"),
    SNACKBAR_ERROR("snackbarError.wav"),
    START_DUEL_CLICK("startDuelClick.wav"),
    GAMEPLAY_BACKGROUND("gamePlayBackground.wav"),
    USUAL_CLICK("usualClick.wav");


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
            System.out.println(ignored.toString());
        }
    }


    public void play(int count) {
        if (!mute)
            clip.loop(count);

    }

    public void reduceVolume() {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-25.0f); // Reduce volume by 10 decibels.
    }

    public void pause() {
        clip.stop();
    }

    public void loop() {
        if (!mute)
            clip.loop(-1);
    }

}
