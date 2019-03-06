package app.Sound;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WavTest {

    @Test
    void toBigEndian() {
        var arr = new byte[]{(byte)0xaa,(byte)0xbb};
        var swapped = Wav.toBigEndian(arr);
        assertEquals("bbaa", Wav.BytetoHexRepresent(swapped));

    }

    @Test
    void getLengthTrack() throws IOException {
        var arr = Files.readAllBytes(new File("out5.wav").toPath());
        assertEquals(4,(int)Wav.getLengthTrack(arr));
    }

    @Test
    void getwavSpectreTest() throws IOException {
        var arr = Files.readAllBytes(new File("Silent.wav").toPath());
        var rawdata  = Wav.getdata(arr);
        PrintWriter pw = new PrintWriter("flow");
        for (int i=0; i<rawdata.length;i++){
            int uint8 = rawdata[i] & 0xFF;
            pw.write(String.valueOf(uint8));
            pw.write("\n");

        }
        pw.close();
    }

    @Test
    void updateWAV() throws IOException {
        var arr = Files.readAllBytes(new File("Silent.wav").toPath());
        var header = Wav.getSubBytes(arr, 0, 43);
        var data = new byte[arr.length-43];
        for (int i=44; i<arr.length-1; i++){
            data[i-44] = (byte) (i % 5000);
            
        };
        var fos = new FileOutputStream("mod.wav");
        fos.write(header);
        fos.write(data);
        fos.close();
    }


    @Test
    void getdata() throws IOException {
        var arr = Files.readAllBytes(new File("init.wav").toPath());
        assertNotEquals(null, arr);
        System.out.println(Wav.getdata(arr).length);
        System.out.println(Wav.BytetoHexRepresent(Wav.toBigEndian(Wav.getdata(arr))));
        var dump = Wav.toBigEndian(Wav.getdata(arr));
        var list = new ArrayList<Double>();
        PrintWriter pw = new PrintWriter("out.samples");
        for (int i=0; i<dump.length; i++) {
            Double dbl = ByteBuffer.wrap(new byte[]{dump[i], dump[i++]}).getDouble();
            System.out.println(dbl);
            pw.write(String.valueOf(new Double(String.valueOf(new byte[]{dump[i], dump[i++]}))));
            pw.write("\n");
        }
        pw.close();
        var desc = Wav. getSubBytes(arr, 0,43);
        var data = Wav.getdata(arr);
        var f = new FileOutputStream("resulted.wav");
        f.write(desc);
        f.write(Wav.toBigEndian((data)));
        f.close();
    }

    @Test
    void bigInt(){
        var chunck = new byte[]{0x01, (byte) 0xff};
        var num = new BigInteger( 1,  chunck );
        System.out.println(String.valueOf(num));
        Double dbl = ByteBuffer.wrap(chunck).getDouble();

        System.out.println(dbl);



    }
}