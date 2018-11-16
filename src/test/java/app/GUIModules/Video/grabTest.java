package app.GUIModules.Video;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class grabTest {

    @Test
    void getFrame() throws IOException {
        var grab = new grab();
        assertNotEquals(null, grab.vc);
        assertNotEquals(null, grab.getFrame());
    }
}