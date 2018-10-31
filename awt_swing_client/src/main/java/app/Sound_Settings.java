package app;

import essens.ResponceMessage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Mixer;
import java.io.*;

public class Sound_Settings implements Serializable {
    public float sampleRate;
    public int sampleSizeInBits;
    public int channels;
    public int frameSize;
    public float frameRate;
    public boolean bigEndian;
    public int indexmixer;
    public Sound_Settings(){

    }

    public Sound_Settings(AudioFormat.Encoding encoding, float sampleRate, int sampleSizeInBits,
                          int channels, int frameSize, float frameRate, boolean bigEndian, int indexmixer){
        this.sampleRate=sampleRate;
        this.sampleSizeInBits=sampleSizeInBits;
        this.channels=channels;
        this.frameSize=frameSize;
        this.frameRate=frameRate;
        this.bigEndian=bigEndian;
        this.indexmixer =indexmixer;

    }

    public static byte[] saveSetiingsToBytes(Sound_Settings input) {
        byte[] Result=null ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(input);
            out.flush();
            Result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {

            }
        }
        return Result;
    }

    public static Sound_Settings restoreBytesToSetiings(byte[] input){
        Object o=null;
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return (Sound_Settings) o;
    }


}
