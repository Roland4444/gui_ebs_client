package app.Essens;

import java.io.*;

public class SoundBundle implements Serializable {
    public byte[] bigWavContent;
    public float begin09;
    public float end09;
    public float begin90;
    public float end90;
    public float begin090;
    public float end090;

    public static byte[] saveToByte(SoundBundle input){
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

    };

    public static SoundBundle restorefromBytes(byte[] input){
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
        return (SoundBundle) o;

    };

}
