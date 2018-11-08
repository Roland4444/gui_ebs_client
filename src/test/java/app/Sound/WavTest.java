package app.Sound;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WavTest {

    @Test
    void toBigEndian() {
        var arr = new byte[]{(byte)0xaa,(byte)0xbb};
        var swapped = Wav.toBigEndian(arr);
        assertEquals("bbaa", Wav.printByte(swapped));

    }
}