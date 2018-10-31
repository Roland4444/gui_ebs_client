package app;

import app.Essens.SoundView;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;

public class SoundRecord {
    public JFrame frame;
    public JButton play;
    public JButton stop;
    public SoundView sv;
    public SoundRecord() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        createAndShowGUI();
    }

    public void createAndShowGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("Меню записи звука");
        frame.setSize(600, 400);
        frame.setVisible(true);

    }

    public static void  main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        SoundRecord sr = new SoundRecord();
    }

}
