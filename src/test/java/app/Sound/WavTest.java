package app.Sound;

import app.GUIModules.Video.grab;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class WavTest {

    @Test
    void toBigEndian() {
        var arr = new byte[]{(byte)0xaa,(byte)0xbb};
        var swapped = Wav.toBigEndian(arr);
        assertEquals("bbaa", Wav.BytetoHexRepresent(swapped));

    }

    @Test
    void getLengthTrack() throws IOException {
        var arr = Files.readAllBytes(new File("out5.wav").toPath());
        assertEquals(4,(int)Wav.getLengthTrack(arr));
    }

    @Test
    void getwavSpectreTest() throws IOException {
        var arr = Files.readAllBytes(new File("Silent.wav").toPath());
        var rawdata  = Wav.getdata(arr);
        PrintWriter pw = new PrintWriter("flow");
        for (int i=0; i<rawdata.length;i++){
            int uint8 = rawdata[i] & 0xFF;
            pw.write(String.valueOf(uint8));
            pw.write("\n");

        }
        pw.close();
    }

    @Test
    void updateWAV() throws IOException {
        var arr = Files.readAllBytes(new File("Silent.wav").toPath());
        var header = Wav.getSubBytes(arr, 0, 43);
        var data = new byte[arr.length-43];
        for (int i=44; i<arr.length-1; i++){
            data[i-44] = (byte) (i % 5000);
            
        };
        var fos = new FileOutputStream("mod.wav");
        fos.write(header);
        fos.write(data);
        fos.close();
    }

    @Test
    void strewam() throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture capture = new VideoCapture(0);

        // Reading the next video frame from the camera
        Mat matrix = new Mat();

        //Instantiate JFrame
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //When we find the user close our window.
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("Frame closing...");
                frame.setVisible(false);
                capture.release();
            }
        });

        ImageIcon icon = new ImageIcon("tested.png"); // Inserts the image icon
        JLabel label = new JLabel(icon); //Label of ImageIcon

        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
        // Instantiating the imgcodecs class

        // Where we will save the image.

        // If camera is opened
        if (capture.isOpened()) {


            // While there is next video frame
            while (capture.read(matrix)) {
                System.out.println("Retrieving Frames...");
                var gr = new grab(640, 480);
                byte[] arrImg=null;
                try {
                    arrImg= gr.getFrame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }



                // Creating BuffredImage from the matrix
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(arrImg));

                //Saving the mat to an image.

                ImageIcon icon2 =  new ImageIcon(image);



                label.setIcon(icon2);
                label.updateUI();

            }
        }
    }
}