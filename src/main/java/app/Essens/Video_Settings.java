package app.Essens;

import java.io.*;

public class Video_Settings implements Serializable {
    public int width;
    public int heigth;
    public boolean CheckFaces;

    public Video_Settings(int x, int y, boolean checkFaces){
        this.CheckFaces=checkFaces;
        this.width=x;
        this.heigth=y;

    }

    public static byte[] saveSetiingsToBytes(Video_Settings input) {
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

    public static Video_Settings restoreBytesToSetiings(byte[] input){
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
        return (Video_Settings) o;
    }
}
