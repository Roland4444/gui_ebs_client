package app;
import app.Sound.Sound_Settings;
import essens.InputMessage;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;

public class SSettings {
    JFrame frame;
    JPanel panelControlButtons;

    JButton loadSets;
    JButton saveSets;

    JPanel SetsPane ;
    JPanel sampleRatePane;

    JLabel sampleRateLabel;
    JTextField sampleRateInput ;

    JPanel sampleSizeInBitsPane;


    JLabel sampleSizeInBitsLabel ;
    JTextField sampleSizeInBitsInput;

    JPanel channelsPane ;


    JLabel channelsLabel;
    JTextField channelsInput;

    JPanel frameSizePane ;

    JLabel frameSizeLabel;
    JTextField frameSizeInput;

    JPanel frameRatePane ;

    JLabel frameRateLabel;
    JTextField frameRateInput;
    JPanel bigEndianPane;

    JPanel checkBoxPanel ;

    JLabel bigEndianLabel;
    CheckboxGroup check ;
    Checkbox true_, false_;

    JPanel indexmixerPane ;

    JLabel indexmixerLabel;

    Choice mixerchooser;



    public void createAndShowGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        frame = new JFrame("Меню настроек звука");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelControlButtons = new JPanel(new BorderLayout());
        frame.getContentPane().add(panelControlButtons, BorderLayout.PAGE_END);

        saveSets = new JButton("Сохранить настройки");
        loadSets = new JButton("Загрузить настройки");
        panelControlButtons.add(loadSets, BorderLayout.WEST);
        panelControlButtons.add(saveSets, BorderLayout.EAST);

        var gr_layout= new GridLayout(4,                1);
        gr_layout.setVgap(20);

        SetsPane = new JPanel(gr_layout);

        frame.getContentPane().add(SetsPane, BorderLayout.PAGE_START);

        sampleRatePane = new JPanel(new GridLayout());
        SetsPane.add(sampleRatePane);

        sampleRateLabel = new JLabel("Частота дискретизации");
        sampleRateInput = new JTextField("",3);


        sampleRatePane.add(sampleRateLabel);
        sampleRatePane.add(sampleRateInput);

        sampleSizeInBitsPane = new JPanel(new GridLayout());
        SetsPane.add(sampleSizeInBitsPane);

        sampleSizeInBitsLabel = new JLabel("Разрядность звука");
        sampleSizeInBitsInput = new JTextField();

        sampleSizeInBitsPane.add(sampleSizeInBitsLabel);
        sampleSizeInBitsPane.add(sampleSizeInBitsInput);



        channelsPane = new JPanel(new GridLayout());
        SetsPane.add(channelsPane);

        channelsLabel = new JLabel("Число каналов звука");
        channelsInput = new JTextField("",3);

        channelsPane.add(channelsLabel);
        channelsPane.add(channelsInput);



        frameSizePane = new JPanel(new GridLayout());
        SetsPane.add(frameSizePane);

        frameSizeLabel = new JLabel("Число каналов звука");
        frameSizeInput = new JTextField("",3);

        frameSizePane.add(frameSizeLabel);
        frameSizePane.add(frameSizeInput);



        frameRatePane = new JPanel(new GridLayout());
        SetsPane.add(frameRatePane);

        frameRateLabel = new JLabel("Число кадров в секунду звука");
        frameRateInput = new JTextField("",3);

        frameRatePane.add(frameRateLabel);
        frameRatePane.add(frameRateInput);


        bigEndianPane = new JPanel(new GridLayout());
        SetsPane.add(bigEndianPane);

        checkBoxPanel = new JPanel(new GridLayout());

        bigEndianLabel = new JLabel("BigEndian?");
        check = new CheckboxGroup();
        true_=new Checkbox ("True");
        false_=new Checkbox ("False");
        true_.setCheckboxGroup(check);
        false_.setCheckboxGroup(check);

        bigEndianPane.add(bigEndianLabel);
        checkBoxPanel.add(true_);
        checkBoxPanel.add(false_);
        bigEndianPane.add(checkBoxPanel);

        indexmixerPane = new JPanel(new GridLayout());
        SetsPane.add(indexmixerPane);

        indexmixerLabel = new JLabel("Номер миксера");

        mixerchooser = new Choice();

        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        var counter=0;
        for (var s: mixInfos){
            mixerchooser.add((counter++)+"=>"+s.getName());
        }

        indexmixerPane.add(indexmixerLabel);
        indexmixerPane.add(mixerchooser);
        frame.pack();

        frame.setVisible(true);
    }

    public void loadSets(String FileName) throws IOException {
        var arr_sets = Files.readAllBytes(new File(FileName).toPath());
        Sound_Settings ss = Sound_Settings.restoreBytesToSetiings(arr_sets);
        sampleRateInput.setText(String.valueOf(ss.sampleRate));
        sampleSizeInBitsInput.setText(String.valueOf(ss.sampleSizeInBits));
        channelsInput.setText(String.valueOf(ss.channels));
        frameSizeInput.setText(String.valueOf(ss.frameSize));
        frameRateInput.setText(String.valueOf(ss.frameRate));
        if (ss.bigEndian)
            check.setSelectedCheckbox(true_);
        else check.setSelectedCheckbox(false_);
        mixerchooser.select(ss.indexmixer);
    }
//float sampleRate, int sampleSizeInBits,
//                          int channels, int frameSize, float frameRate, boolean bigEndian, int indexmixer
    private void initListeners() {
        saveSets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                boolean Endian;
                if (check.getSelectedCheckbox()==true_)
                    Endian=true;
                else
                    Endian=false;
                Sound_Settings ss = new Sound_Settings(Float.parseFloat(sampleRateInput.getText()),
                        Integer.parseInt(sampleSizeInBitsInput.getText()),  Integer.parseInt(channelsInput.getText()),
                        Integer.parseInt(frameSizeInput.getText()),
                        Float.parseFloat(frameRateInput.getText()), Endian, mixerchooser.getSelectedIndex());
                try {
                    new FileOutputStream("./sound_settings.bin").write(Sound_Settings.saveSetiingsToBytes(ss));
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

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                System.out.println("==============================>CLOSING!!!!");
                Runtime.getRuntime().exit(0);
            }
        });
    }


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        SSettings ss = new SSettings();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ss.createAndShowGUI();
                    ss.initListeners();
                    ss.loadSets("./sound_settings.bin");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("DEfault file setting not found");
                }
            }
        });
    }

}
