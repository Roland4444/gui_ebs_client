package app.GUIModules.Interface.GetBio.Video;


import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static org.opencv.core.Core.ellipse;


public class window {
    public window(int x, int y){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        processor my_processor=new processor();
        STReam my_panel = new STReam();
        frame.setContentPane(my_panel);
        frame.setVisible(true);
        //-- 2. Read the video stream
        Mat webcam_image=new Mat();
        VideoCapture capture =new VideoCapture(-1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        capture.set(3, x); //1280);
        capture.set(4, y);//720);
        if( capture.isOpened())
        {
            while( true )
            {
                capture.read(webcam_image);
                if( !webcam_image.empty() )
                {
                    frame.setSize(webcam_image.width()+40,webcam_image.height()+60);
                    //-- 3. Apply the classifier to the captured image
                    //     webcam_image=my_processor.detect(webcam_image);

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

    public static void main(String arg[]){
        // Load the native library.
        new window(640, 480);
    }
}

class STReam extends JPanel{
    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    // Create a constructor method


    public STReam(){
        super();
    }
    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public boolean MatToBufferedImage(Mat matBGR){
/*
        frame = new Mat();
        mem = new MatOfByte();
        if (vc.grab()) {
            vc.retrieve(frame);
            Highgui.imencode(".png", frame, mem);
            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
            var fos = new FileOutputStream(saveTo);
            fos.write(mem.toArray());
            fos.close();
        }
        System.out.println("Length file =>"+ new File(saveTo).length());
        vc.release();
        return mem.toArray();   */

        long startTime = System.nanoTime();
        int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;
        byte[] sourcePixels = new byte[width * height * channels];
        matBGR.get(0, 0, sourcePixels);
        // create new image and get reference to backing data
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        long endTime = System.nanoTime();
        System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));
        return true;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (this.image==null) return;
        g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);
        //g.drawString("This is my custom Panel!",10,20);
    }
}


class processor {
    private CascadeClassifier face_cascade;
    // Create a constructor method
    public processor(){
        face_cascade=new CascadeClassifier("cascade.xml");
        if(face_cascade.empty())
        {
            System.out.println("--(!)Error loading A\n");
            return;
        }
        else
        {
            System.out.println("Face classifier loooaaaaaded up");
        }
    }
    public Mat detect(Mat inputframe){
        long startTime = System.nanoTime();
        Mat mRgba=new Mat();
        Mat mGrey=new Mat();
        MatOfRect faces = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist( mGrey, mGrey );
        face_cascade.detectMultiScale(mGrey, faces);
        long endTime = System.nanoTime();
        System.out.println(String.format("Detect time: %.2f ms", (float)(endTime - startTime)/1000000));
        System.out.println(String.format("Detected %s faces", faces.toArray().length));
        for(Rect rect:faces.toArray())
        {
            Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );
            ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );
        }
        return mRgba;
    }
}