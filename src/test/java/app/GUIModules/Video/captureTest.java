package app.GUIModules.Video;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class captureTest {

    @Test
    void  capture() throws IOException {
        String save="out.png";
        SwingCapture cp =new SwingCapture();
        cp.capture(save);
        assertEquals(true, new File(save).exists());
    }
}