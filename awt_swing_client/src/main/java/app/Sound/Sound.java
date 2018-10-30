package app.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public Sound(Mixer mixer){
        this.mixer=mixer;
    }
    public Mixer mixer = null;
    public Thread ThreadRecord;
    public String currentFile;
    public TargetDataLine td;
    public AudioFormat format;
    public void setFormat(AudioFormat  f){
        this.format=f;
    }
    boolean checkAudioConfig() {
        var info = new DataLine.Info(TargetDataLine.class, format);
        return AudioSystem.isLineSupported(info);
    }
    public void startRecord(String FileName) throws LineUnavailableException, IOException {
        var dataline = new DataLine.Info(TargetDataLine.class, format);
        if (!checkAudioConfig()) {
            System.out.println("FORMAt Not suppoerted");
            return ;
        }
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        td = (TargetDataLine) AudioSystem.getLine(info);;
        td.open();
        td.start();
        ThreadRecord = new Thread(){
            @Override
            public void run(){
                var audioStream = new AudioInputStream(td);
                currentFile=FileName;
                var file = new File(currentFile);
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, file);
                } catch (IOException e) {
                    e.printStackTrace();
                };
            }
        };
        ThreadRecord.start();
    }
    public void stopRecord(){
        td.stop();
        td.close();
    }

    public void getFullInfo(){
        var mixInfos = AudioSystem.getMixerInfo();
        var counter = 0;
        for (var info : mixInfos){
            System.out.println("\n\n\nIndex==>"+counter);
            System.out.println("Name =>"+info.getName()+"\nDescription=>"+info.getDescription());
            mixer = AudioSystem.getMixer(mixInfos[counter]);

            System.out.println("Describing subsystem");
            System.out.println("\nSOURCES::");
            var sources = mixer.getSourceLineInfo();
            for (var s : sources )
                System.out.println(s.toString());

            System.out.println("\nOUTPUTS::");
            var targets = mixer.getTargetLineInfo();
            for (var s : targets )
                System.out.println(s.toString());

            counter++;
        }
    }
}
