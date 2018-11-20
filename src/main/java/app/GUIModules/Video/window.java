package app.GUIModules.Video;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class window {
    public static void main(String arg[]){
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);

        CamPanel my_panel = new CamPanel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);
        //-- 2. Read the video stream
        Mat webcam_image=new Mat();
        VideoCapture capture =new VideoCapture(-1);
        if( capture.isOpened())
        {
            while( true )
            {
                capture.read(webcam_image);
                if( !webcam_image.empty() )
                {
                   // frame.setSize(2*webcam_image.width()+40,2*webcam_image.height()+60);
                    //-- 3. Apply the classifier to the captured image


                    //-- 4. Display the image
                    my_panel.MatToBufferedImage(webcam_image); // We could look at the error...
                    my_panel.repaint();
                }
                else
                {
                    System.out.println(" --(!) No captured frame -- Break!");
                    break;
                }
            }
        }
        return;
    }
}


