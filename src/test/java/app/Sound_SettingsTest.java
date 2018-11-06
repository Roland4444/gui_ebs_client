package app;

import app.Essens.Sound_Settings;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Sound_SettingsTest {
    public int X = 0;

    @Test
    void saveSetiingsToBytes() throws IOException {
        Sound_Settings ss = new Sound_Settings(44100, 8, 1, 1, 44100, false, 6);
        Sound_Settings restored = Sound_Settings.restoreBytesToSetiings(Sound_Settings.saveSetiingsToBytes(ss));
        assertEquals(ss.channels, restored.channels);
        assertEquals(ss.frameRate, restored.frameRate);
        assertEquals(ss.frameSize, restored.frameSize);
        assertEquals(ss.sampleRate, restored.sampleRate);
        assertEquals(ss.sampleSizeInBits, restored.sampleSizeInBits);
        assertEquals(ss.bigEndian, restored.bigEndian);
        assertEquals(ss.indexmixer, restored.indexmixer);
        printSets(ss, restored);
        FileOutputStream fos = new FileOutputStream("sound_settings.bin");
        fos.write(Sound_Settings.saveSetiingsToBytes(ss));
        fos.close();
    }

    public void printSets(Sound_Settings ss1, Sound_Settings ss2 ){
        System.out.println(ss1.bigEndian+"<>" + ss2.bigEndian);
        System.out.println(ss1.channels+"<>" + ss2.channels);
        System.out.println(ss1.frameRate+"<>" + ss2.frameRate);
        System.out.println(ss1.frameSize+"<>" + ss2.frameSize);
        System.out.println(ss1.indexmixer+"<>" + ss2.indexmixer);
        System.out.println(ss1.sampleRate+"<>" + ss2.sampleRate);
        System.out.println(ss1.sampleSizeInBits+"<>" + ss2.sampleSizeInBits);
    }


    @Test
    public void testLambda(){

        lambda L = new lambda();
        L.C=new callable() {
            @Override
            public void call() {
                X=5;
            }
        };
        L.C.call();
        assertEquals(5, X);


    }


    class lambda{
        public callable C;
    }

    interface callable{
        public void call();
    }

}