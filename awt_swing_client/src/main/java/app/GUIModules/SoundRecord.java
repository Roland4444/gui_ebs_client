package app.GUIModules;
import app.Essens.SoundView;
import app.abstractions.ModuleGUI;

import javax.swing.*;
import java.awt.*;

public class SoundRecord extends ModuleGUI {
    public JFrame frame;
    public JPanel panel;
    public JPanel controlsPanel;
    public JPanel startPanel;
    public JPanel stopPPanel;
    public JButton sets;
    public JButton check;
    public JButton save;
    public JButton start;
    public JButton stop;
    public JLabel startLabel;
    public JLabel stopLabel;
    public SoundView sv;
    public SoundRecord() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        preperaGUI();
    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("Меню записи звука");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        panel = new JPanel(new BorderLayout());

        frame.getContentPane().add(panel, BorderLayout.PAGE_END);

        sets = new JButton("Настройки звука");
        check = new JButton("Проверить записанный фрагмент");
        save = new JButton("Сохранить фрагмент в WAV...");
        panel.add(sets, BorderLayout.WEST);
        panel.add(check, BorderLayout.CENTER);
        panel.add(save, BorderLayout.EAST);

        controlsPanel = new JPanel(new FlowLayout());
        startPanel = new JPanel(new GridLayout(2,1));
        stopPPanel = new JPanel(new GridLayout(2,1));

        start = new JButton("START");
        stop = new JButton("STOP");
        startLabel = new JLabel("Start");
        stopLabel=new JLabel("Stop");

        startPanel.add(start);
        startPanel.add(startLabel);

        stopPPanel.add(stop);
        stopPPanel.add(stopLabel);

        controlsPanel.add(startPanel, FlowLayout.LEFT);
        controlsPanel.add(stopPPanel, FlowLayout.RIGHT);

        frame.getContentPane().add(controlsPanel, BorderLayout.PAGE_START);




        frame.setVisible(true);


    }

    @Override
    public void initListeners() {

    }


    public static void  main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        SoundRecord sr = new SoundRecord();
    }

}
