package app.Sound;

import app.Essens.Sound_Settings;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SoundTest {
    public Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
    public Mixer mixer = AudioSystem.getMixer(mixInfos[6]);
    public final String tempfile = "temp.wav";
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
        binarySound.startRecord("out1.wav");
        Thread.sleep( 1000);
        binarySound.stopRecord();

        binarySound.startRecord("out2.wav");
        Thread.sleep( 3000);
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

    @Test
    void playWav() throws IOException {
        Sound_Settings ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
        Sound binarySound = new Sound(ss);
        binarySound.playSound("out.wav");
    }

    @Test
    void joinFiles() throws IOException {
        Sound_Settings ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
        Sound binarySound = new Sound(ss);
        String out = "result.wav";
        binarySound.joinWav("slot1.wav", "slot2.wav", out);
        var res = new File(out);
        assertEquals(true, res.exists());
    }

    @Test
    void playoffset() throws IOException {
        Sound_Settings ss = null;

        try {
            ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Sound binarySound = new Sound(ss);
        binarySound.playSound(tempfile, 65000, 0);
    }

    @Test
    void getframerate() throws IOException, UnsupportedAudioFileException {
        File file = new File("temp.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames+0.0) / format.getFrameRate();
        System.out.println("out.wav DURATION =>"+durationInSeconds);
        System.out.println("FRame length =>"+frames);
        System.out.println("Frame rate=>"+format.getFrameRate());
    }

    @Test
    void playoffset2() throws IOException, UnsupportedAudioFileException {
        File file = new File("out.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames+0.0) / format.getFrameRate();
        System.out.println("out.wav DURATION =>"+durationInSeconds);
        System.out.println("FRame length =>"+frames);
        System.out.println("Frame rate=>"+format.getFrameRate());
        file = new File("out5.wav");
        audioInputStream = AudioSystem.getAudioInputStream(file);
        format = audioInputStream.getFormat();
        frames = audioInputStream.getFrameLength();
        durationInSeconds = (frames+0.0) / format.getFrameRate();
        System.out.println("out5.wav DURATION =>"+durationInSeconds);
        System.out.println("FRame length =>"+frames);


        Sound_Settings ss = null;

        try {
            ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Sound binarySound = new Sound(ss);
        binarySound.playSound("out.wav", 0000, 195000);

    }


    @Test
    void printByte() throws IOException {

        byte [] d=new byte[] { (byte)0x0a, (byte)0x00,(byte)0x01,(byte) 0x02, (byte) 0xff};
        System.out.println("\n\nOUT=>");
        System.out.println(Wav.printByte(d));
    }

    @Test
    void equal() throws IOException {

        byte [] arr1 = Files.readAllBytes(new File("out1.wav").toPath());
        assertEquals("52494646", Wav.printByte(Wav.getSubBytes(arr1, 0,3)));
        byte [] arr2 = Files.readAllBytes(new File("out2.wav").toPath());
        //assertEquals(Sound.printByte(arr1), Sound.printByte(arr2));
    }

    @Test
    void getSubBytes() {
        byte[] arr = new byte[] { (byte)0xfa, (byte)0x00,(byte)0x01,(byte) 0x02, (byte) 0xff};
        byte[] sub = Wav.getSubBytes(arr, 0,1);
        assertEquals("fa00", Wav.printByte(sub));
        sub = Wav.getSubBytes(arr, 0,2);
        assertEquals("fa0001", Wav.printByte(sub));

    }

    @Test
    void getdata() {
        byte [] arr = new byte[50];
        for (int i=0; i<arr.length;i++){
            arr[i]= (byte) i;
        }
        System.out.println(Wav.printByte(arr));
        byte[] data = Wav.getdata(arr);
        System.out.println(Wav.printByte(data));
        assertNotEquals(null, data);
    }


    @Test
    void getNumberFromBytes() {
        byte[] arr = new byte[]{(byte) 0x01 };
        byte[] arr2 = new byte[]{(byte) 0x0f };
        assertEquals(1, Wav.getNumberFromBytes(arr));
        assertEquals(15, Wav.getNumberFromBytes(arr2));
    }

    @Test
    void getInfo() throws IOException {
        byte [] arr1 = Files.readAllBytes(new File("out1.wav").toPath());
        byte [] arr2 = Files.readAllBytes(new File("out2.wav").toPath());

        System.out.println(Wav.getFullInfo(arr1));
    }
}