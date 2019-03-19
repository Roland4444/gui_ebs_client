package app.GUIModules.Interface.GetBio.Video;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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