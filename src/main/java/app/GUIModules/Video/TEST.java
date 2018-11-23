package app.GUIModules.Video;

import app.Essens.Sound_Settings;
import app.abstractions.ModuleGUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class TEST extends ModuleGUI {
    public final String LImgStatic = "pics/static.jpg";
    public final String[] arr = {"pics/1.jpg", "pics/två.jpg"};
    BufferedImage Img1, Img2;
    JLabel Limg1;
    JLabel Limgtvå;
    JPanel MainPanel;
    JButton testButton;
    JLabel  TopLabel;

    public TEST() throws IOException {
        frame = new JFrame("tested");
        frame.setSize(640, 480);
        MainPanel = new JPanel(new BorderLayout());
        Img1 = ImageIO.read(new File(LImgStatic));
        Img2 = ImageIO.read(new File(arr[0]));
        ImageIcon icon =  new ImageIcon(
                Img1.getScaledInstance(
                        Img1.getWidth(null)/4,
                        Img1.getHeight(null)/4,
                        Image.SCALE_SMOOTH )
        );

        Limg1 = new JLabel(icon);

        ImageIcon icon2 =  new ImageIcon(
                Img2.getScaledInstance(
                        Img2.getWidth(null)/4,
                        Img2.getHeight(null)/4,
                        Image.SCALE_SMOOTH )
        );
        Limgtvå = new JLabel(icon2);
        testButton = new JButton("test");
        TopLabel = new JLabel("this is top label");
    }
    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.getContentPane().add(MainPanel);

        MainPanel.add(Limg1, BorderLayout.EAST);

        MainPanel.add(Limgtvå, BorderLayout.WEST);

        frame.setVisible(true);

        MainPanel.add(testButton, BorderLayout.SOUTH);

        MainPanel.add(TopLabel, BorderLayout.NORTH);

    }

    @Override
    public void initListeners() {
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                showMessageDialog(null, "LOOK at blinker");
            }
        });

    }


    @Override
    public void initActions() {

    }


    public void prepareandrunThread() throws InterruptedException {
        var thr = new modifier();
        thr.mounted=Limgtvå;
        thr.arr=arr;
        thr.start();

        var thr2=new camera();
        thr2.mounted=TopLabel;
        thr2.start();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, InterruptedException {
        TEST test = new TEST();
        test.preperaGUI();
        test.initListeners();
        test.prepareandrunThread();


    }

    class camera extends Thread {
        public JLabel mounted;
        Image im;
        public void run() {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            var vc = new VideoCapture(0);
            vc.set(3, 640); //1280);
            vc.set(4, 480);//720);
            var mat = new Mat();
            if (vc.isOpened()) {
                while (true) {
                    vc.read(mat);
                    var mem = new MatOfByte();
                    Highgui.imencode(".png", mat, mem);
                    try {
                        im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    var icon2 = new ImageIcon(im.getScaledInstance(
                            im.getWidth(null) / 4,
                            im.getHeight(null) / 4,
                            Image.SCALE_SMOOTH));

                    mounted.setIcon(icon2);
                    mounted.updateUI();


                }
            }
        }
    }

    class modifier extends Thread{
        public String[] arr;
        public JLabel mounted;
        ImageIcon icon2;
        int counter=0;

        public void run(){
            while (true){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter==0)
                    counter=1;
                else counter=0;
                try {
                    var Image_ = ImageIO.read(new File(arr[counter]));
                    icon2 =  new ImageIcon(Image_.getScaledInstance(
                            Image_.getWidth(null)/4,
                            Image_.getHeight(null)/4,
                            Image.SCALE_SMOOTH));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mounted.setIcon(icon2);
                mounted.updateUI();



            }


        }

    }
}
