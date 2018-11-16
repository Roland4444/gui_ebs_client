package app.GUIModules.Video;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class grab {
    public VideoCapture vc;
    public grab(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        vc = new VideoCapture(0);
    }
    public byte[] getFrame() throws IOException {

        Mat frame = new Mat();
        MatOfByte mem = new MatOfByte();
        if (vc.grab()) {
            vc.retrieve(frame);
            Highgui.imencode(".bmp", frame, mem);
            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
        }
        return mem.toArray();
    }

}
