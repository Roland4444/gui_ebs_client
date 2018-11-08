package app.Sound;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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
    void wavTest(){

    }
}