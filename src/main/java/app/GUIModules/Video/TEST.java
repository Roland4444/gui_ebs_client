package app.GUIModules.Video;

import app.abstractions.ModuleGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TEST extends ModuleGUI {
    public final String LImgStatic = "pics/static.jpg";

    BufferedImage Img1, Img2;
    JLabel Limg1;
    JLabel Limg2;
    JPanel MainPanel;

    public TEST() throws IOException {
        frame = new JFrame("tested");
        frame.setSize(640, 480);
        MainPanel = new JPanel(new BorderLayout());
        Img1 = ImageIO.read(new File(LImgStatic));
        ImageIcon icon =  new ImageIcon(
                Img1.getScaledInstance(
                        Img1.getWidth(null)/4,
                        Img1.getHeight(null)/4,
                        Image.SCALE_SMOOTH )
        );

        Limg1 = new JLabel(icon);
    }
    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.getContentPane().add(MainPanel);
        MainPanel.add(Limg1);

        frame.setVisible(true);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initActions() {

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        TEST test = new TEST();
        test.preperaGUI();
    }
}
