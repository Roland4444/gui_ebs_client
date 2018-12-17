package app.abstractions;

import java.io.*;


public class Model implements Serializable {
    public String SNILS;
    public String RA;
    public String FIO;
    public String Pass;

    public Model(String SNILS, String RA, String FIO, String Pass){
        this.FIO=FIO;
        this.Pass=Pass;
        this.RA=RA;
        this.SNILS=SNILS;

    }

    public static byte[] saveModel(Model input){
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
                // ignore close exception
            }
        }
        return Result;
    }

    public static Model restoredModel(byte[] input){
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
                // ignore close exception
            }
        }
        return (Model) o;
    }
}

