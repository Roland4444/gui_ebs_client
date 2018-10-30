package app.Sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class Sound {
    boolean checkAudioConfig(AudioFormat format) {

        var info = new DataLine.Info(TargetDataLine.class, format);
        return AudioSystem.isLineSupported(info);
    }
    boolean checkAudioConfig( AudioFormat.Encoding encoding, float sampleRate, int sampleSizeInBits,
                                   int channels, int frameSize, float frameRate, boolean bigEndian){
        var format = new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        var info = new DataLine.Info(TargetDataLine.class, format);
        return AudioSystem.isLineSupported(info);
    }
}
