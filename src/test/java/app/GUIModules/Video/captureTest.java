package app.GUIModules.Video;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class captureTest {

    @org.junit.Test
    public void  capture() throws IOException {
        String save="out.png";

        assertEquals(true, new File(save).exists());
    }
}