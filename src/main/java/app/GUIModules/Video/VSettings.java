package app.GUIModules.Video;

import app.Essens.OnClosed;
import app.Essens.Video_Settings;
import app.abstractions.ModuleGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class VSettings extends ModuleGUI {

    public OnClosed onclosed;
    public Video_Settings default_;
    public final String defaultFileName = "./video_settings.bin";
    public final static String defaultFileName_static = "./video_settings.bin";
    public JFrame frame;
    public JPanel panelControlButtons;

    public JButton loadSets;
    public JButton saveSets;
    public JButton saveAsSets;

    public JPanel SetsPane ;

    public JPanel XSettsPane;
    public JLabel XLabel;
    public JTextField XInput;

    public JPanel YSettsPane;
    public JLabel YLabel;
    public JTextField YInput;

    public JPanel CheckPanel;
    public JLabel CheckLabel;
    public JCheckBox CheckBox;



    public VSettings() throws IOException {
        System.out.println("Create default");
        default_= new Video_Settings(640,480, false);
        frame = new JFrame("Меню настроек Видео");
        panelControlButtons = new JPanel(new BorderLayout());
        saveSets = new JButton("Сохранить настройки");
        loadSets = new JButton("Загрузить настройки");
        saveAsSets = new JButton("Сохранить..");
        var gr_layout= new GridLayout(4,                1);
        gr_layout.setVgap(20);
        SetsPane = new JPanel(gr_layout);
        XSettsPane = new JPanel(new GridLayout());
        XLabel = new JLabel("Ширина изображения");
        XInput = new JTextField("",3);
        YSettsPane = new JPanel(new GridLayout());
        YLabel = new JLabel("Высота изображения");
        YInput = new JTextField();

        CheckPanel = new JPanel(new GridLayout());
        CheckBox = new JCheckBox();
        CheckLabel = new JLabel("Выделять лицо?");

        if (!new File(VSettings.defaultFileName_static).exists()){
            System.out.println("CREATING NEW FILE!!!");
            var fos = new FileOutputStream(VSettings.defaultFileName_static);
            fos.write(Video_Settings.saveSetiingsToBytes(this.default_));
            fos.close();
        }

    }

    public Video_Settings buildVS(){
        Video_Settings vs = new Video_Settings(Integer.parseInt(XInput.getText()), Integer.parseInt(YInput.getText()), CheckBox.isSelected());
        return vs;
    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panelControlButtons, BorderLayout.PAGE_END);
        panelControlButtons.add(loadSets, BorderLayout.WEST);
        panelControlButtons.add(saveSets, BorderLayout.EAST);
        panelControlButtons.add(saveAsSets, BorderLayout.CENTER);
        var gr_layout= new GridLayout(4,                1);
        gr_layout.setVgap(20);

        frame.getContentPane().add(SetsPane, BorderLayout.PAGE_START);
        SetsPane.add(XSettsPane);

        XSettsPane.add(XLabel);
        XSettsPane.add(XInput);

        SetsPane.add(YSettsPane);

        YSettsPane.add(YLabel);
        YSettsPane.add(YInput);

        SetsPane.add(CheckPanel);
        CheckPanel.add(CheckLabel);
        CheckPanel.add(CheckBox);



        frame.pack();
    }

    public void loadSets(String FileName) throws IOException {
        var arr_sets = Files.readAllBytes(new File(FileName).toPath());
        Video_Settings vs = Video_Settings.restoreBytesToSetiings(arr_sets);
        XInput.setText(String.valueOf(vs.width));
        YInput.setText(String.valueOf(vs.heigth));
        CheckBox.setSelected(vs.CheckFaces);

    }

    @Override
    public void initListeners() {
        saveSets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {

                try {
                    new FileOutputStream(defaultFileName).write(Video_Settings.saveSetiingsToBytes(buildVS()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        saveAsSets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.SAVE);
                fd.setDirectory("./");
                fd.setVisible(true);
                var fullpathtoCheckFile = fd.getDirectory()+fd.getFile();
                System.out.print(fullpathtoCheckFile);
                File file = new File(fullpathtoCheckFile);
                if (file != null) {
                    try {
                        loadSets(fullpathtoCheckFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    new FileOutputStream(fullpathtoCheckFile).write(Video_Settings.saveSetiingsToBytes(buildVS()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        loadSets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.LOAD);
                fd.setDirectory("./");
                fd.setVisible(true);
                var fullpathtoCheckFile = fd.getDirectory()+fd.getFile();
                System.out.print(fullpathtoCheckFile);
                File file = new File(fullpathtoCheckFile);
                if (file != null) {
                    try {
                        loadSets(fullpathtoCheckFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    onclosed.onClosed();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Higing window!!!!");
                frame.setVisible(false);
            }
        });

    }


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        VSettings ss = new VSettings();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ss.preperaGUI();
        ss.initListeners();
        ss.frame.setVisible(true);
        ss.loadSets(defaultFileName_static);
    };

    @Override
    public void initActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
