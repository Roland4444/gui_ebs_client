package app.Sound;

import app.Essens.Sound_Settings;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Sound {
    public Sound(Mixer mixer) {
        this.mixer = mixer;
    }

    public Sound(Sound_Settings sets) {
        printSetts(sets);
        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        this.mixer = AudioSystem.getMixer(mixInfos[sets.indexmixer]);
        this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sets.sampleRate, sets.sampleSizeInBits, sets.channels, sets.frameSize, sets.frameRate, sets.bigEndian);
    }

    public void printSetts(Sound_Settings sets) {
        System.out.println("CHANNELS =>" + sets.channels);
        System.out.println("frame rate =>" + sets.frameRate);
        System.out.println("frameSize =>" + sets.frameSize);
        System.out.println("sampleRate =>" + sets.sampleRate);
        System.out.println("sampleSizeInBits =>" + sets.sampleSizeInBits);
    }

    public Mixer mixer = null;
    public Thread ThreadRecord;
    public String currentFile;
    public TargetDataLine td;
    public AudioFormat format;

    public void setFormat(AudioFormat f) {
        this.format = f;
    }

    boolean checkAudioConfig() {
        var info = new DataLine.Info(TargetDataLine.class, format);
        return AudioSystem.isLineSupported(info);
    }

    public void startRecord(String FileName) throws LineUnavailableException, IOException {
        if (!checkAudioConfig()) {
            System.out.println("FORMAt Not suppoerted");
            return;
        }
        var info = new DataLine.Info(TargetDataLine.class, format);
        td = (TargetDataLine) mixer.getLine(info);
        td.open();
        td.start();
        ThreadRecord = new Thread() {
            @Override
            public void run() {
                var audioStream = new AudioInputStream(td);
                currentFile = FileName;
                var file = new File(currentFile);
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        };
        ThreadRecord.start();
    }

    public void stopRecord() {
        td.stop();
        td.close();
    }

    public void getFullInfo() {
        var mixInfos = AudioSystem.getMixerInfo();
        var counter = 0;
        for (var info : mixInfos) {
            System.out.println("\n\n\nIndex==>" + counter);
            System.out.println("Name =>" + info.getName() + "\nDescription=>" + info.getDescription());
            mixer = AudioSystem.getMixer(mixInfos[counter]);


            System.out.println("Describing subsystem");
            System.out.println("\ndata Written to::");
            var sources = mixer.getSourceLineInfo();
            for (var s : sources)
                System.out.println(s.toString());

            System.out.println("\ndata CAPTURED from::");
            var targets = mixer.getTargetLineInfo();
            for (var s : targets)
                System.out.println(s.toString());

            counter++;
        }
    }


    private final int BUFFER_SIZE = 800472;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;

    /**
     * @param filename the name of the file that is going to be played
     */
    public void playSound(String filename) {
        String strFilename = filename;
        try {
            soundFile = new File(strFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }

    public void playSound(String filename, int offset) {
        String strFilename = filename;
        try {
            soundFile = new File(strFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, offset, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }


    public void playSound(String filename, int offsetread, int offsetwrite) {
        String strFilename = filename;
        try {
            soundFile = new File(strFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, offsetread, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, offsetwrite, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }

    public void margeWav(String wav1, String wav2, String outputResult) {
        try {
            AudioInputStream clip1 = AudioSystem.getAudioInputStream(new File(wav1));
            AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(wav2));

            AudioInputStream appendedFiles =
                    new AudioInputStream(
                            new SequenceInputStream(clip1, clip2),
                            clip1.getFormat(),
                            clip1.getFrameLength() + clip2.getFrameLength());

            AudioSystem.write(appendedFiles,
                    AudioFileFormat.Type.WAVE,
                    new File(outputResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
