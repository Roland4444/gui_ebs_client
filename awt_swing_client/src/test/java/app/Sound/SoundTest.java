package app.Sound;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    void startRecordviabinarysets() throws IOException, LineUnavailableException, InterruptedException {
        Sound_Settings ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
        Sound binarySound = new Sound(ss);
        binarySound.startRecord("out.wav");
        Thread.sleep( 4000);
        binarySound.stopRecord();
    }

    @Test
    void findProperLine() throws LineUnavailableException {
        var l = mixer.getTargetLineInfo();
        var targetLine = mixer.getTargetLines();
        assertNotEquals(null, targetLine);
        System.out.println(targetLine.length);

        assertNotEquals(null, l);
        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(l[0]);
        assertNotEquals(null, line);
    }
}