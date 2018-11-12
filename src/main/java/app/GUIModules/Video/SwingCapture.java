package app.GUIModules.Video;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SwingCapture {

    public void capture(String out) throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File(out));
    }

}
