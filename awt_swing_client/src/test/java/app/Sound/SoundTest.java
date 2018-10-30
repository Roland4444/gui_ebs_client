package app.Sound;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SoundTest {
    public Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
    public Mixer mixer = AudioSystem.getMixer(mixInfos[6]);
    Sound sound = new Sound(mixer);
    @Test
    void checkAudioConfig() {
    }

    @Test
    void checkAudioConfig1() {
        var newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 24, 1, 4, 44100, true  );
        sound.setFormat(newFormat);
        assertEquals(true, sound.checkAudioConfig());
        var terribleFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 777000, 196, 2, 40, 5000000, true  );
        sound.setFormat(terribleFormat);
        assertEquals(false, sound.checkAudioConfig());
    }

    @Test
    void startRecord() throws IOException, LineUnavailableException, InterruptedException {
        var newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 24, 1, 4, 44100, false  );
        sound.setFormat(newFormat);
        sound.startRecord("out.wav");
        Thread.sleep(10000);
        sound.stopRecord();

    }
}