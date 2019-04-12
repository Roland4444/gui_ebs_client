package app.GUIModules.Interface.GetBio.Video;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.opencv.highgui.Highgui.imencode;


public class grab {
    public final String saveTo = "tested.png";
    public final static String saveTo_ = "tested.png";
    public VideoCapture vc;
    Mat frame;
    MatOfByte mem;
    public grab(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        vc = new VideoCapture(0);
        vc.set(3, 1024); //1280);
        vc.set(4, 1024);//720);
    }
    public grab(int width, int heigth){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        vc = new VideoCapture(0);
        vc.set(3, width); //1280);
        vc.set(4, heigth);//720);
    }
    public byte[] getFrame() throws IOException {
        frame = new Mat();
        mem = new MatOfByte();
        if (vc.grab()) {
            vc.retrieve(frame);
            imencode(".png", frame, mem);
            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
            var fos = new FileOutputStream(saveTo);
            fos.write(mem.toArray());
            fos.close();
        }
        System.out.println("Length file =>"+ new File(saveTo).length());
        vc.release();
        return mem.toArray();
    }



}
