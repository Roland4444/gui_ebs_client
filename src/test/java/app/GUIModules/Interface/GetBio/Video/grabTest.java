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

    @Test
    void getImage() throws IOException, InterruptedException {
        String image = "tested.png";
        String wav = "temp.wav";
        BufferedImage bf = ImageIO.read(new File(image));
        assertNotEquals(null, bf);
        Graphics g = bf.getGraphics();
        JFrame jf = new JFrame("");
        Canvas cns = new Canvas();
        cns.paint(g);

        jf.setSize(400, 300);
        jf.getContentPane().add(cns);
        jf.setVisible(true);
        Thread.sleep(2000);

    }
}