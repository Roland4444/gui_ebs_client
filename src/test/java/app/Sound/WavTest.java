package app.Sound;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

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
}