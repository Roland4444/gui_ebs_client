package app;
import app.Sound.Sound_Settings;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
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

public class SSettings {
    public JFrame frame;
    public JPanel panelControlButtons;

    public JButton loadSets;
    public JButton saveSets;
    public JButton saveAsSets;

    public JPanel SetsPane ;
    public JPanel sampleRatePane;

    public JLabel sampleRateLabel;
    public JTextField sampleRateInput ;

    public JPanel sampleSizeInBitsPane;


    public JLabel sampleSizeInBitsLabel ;
    public JTextField sampleSizeInBitsInput;

    public JPanel channelsPane ;


    public JLabel channelsLabel;
    public JTextField channelsInput;

    public JPanel frameSizePane ;

    public JLabel frameSizeLabel;
    public JTextField frameSizeInput;

    public JPanel frameRatePane ;

    public JLabel frameRateLabel;
    public JTextField frameRateInput;
    public JPanel bigEndianPane;

    public JPanel checkBoxPanel ;

    public  JLabel bigEndianLabel;
    public CheckboxGroup check ;
    public  Checkbox true_, false_;

    public JPanel indexmixerPane ;

    public JLabel indexmixerLabel;

    public Choice mixerchooser;


    public SSettings(){

    }


    public void createAndShowGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        frame = new JFrame("Меню настроек звука");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelControlButtons = new JPanel(new BorderLayout());
        frame.getContentPane().add(panelControlButtons, BorderLayout.PAGE_END);

        saveSets = new JButton("Сохранить настройки");
        loadSets = new JButton("Загрузить настройки");
        saveAsSets = new JButton("Сохранить..");
        panelControlButtons.add(loadSets, BorderLayout.WEST);
        panelControlButtons.add(saveSets, BorderLayout.EAST);
        panelControlButtons.add(saveAsSets, BorderLayout.CENTER);

        var gr_layout= new GridLayout(4,                1);
        gr_layout.setVgap(20);

        SetsPane = new JPanel(gr_layout);

        frame.getContentPane().add(SetsPane, BorderLayout.PAGE_START);

        sampleRatePane = new JPanel(new GridLayout());
        SetsPane.add(sampleRatePane);

        sampleRateLabel = new JLabel("Частота дискретизации(sampleRate)");
        sampleRateInput = new JTextField("",3);


        sampleRatePane.add(sampleRateLabel);
        sampleRatePane.add(sampleRateInput);

        sampleSizeInBitsPane = new JPanel(new GridLayout());
        SetsPane.add(sampleSizeInBitsPane);

        sampleSizeInBitsLabel = new JLabel("Разрядность звука(sampleSizeInBits)");
        sampleSizeInBitsInput = new JTextField();

        sampleSizeInBitsPane.add(sampleSizeInBitsLabel);
        sampleSizeInBitsPane.add(sampleSizeInBitsInput);



        channelsPane = new JPanel(new GridLayout());
        SetsPane.add(channelsPane);

        channelsLabel = new JLabel("Число каналов звука(channels)");
        channelsInput = new JTextField("",3);

        channelsPane.add(channelsLabel);
        channelsPane.add(channelsInput);



        frameSizePane = new JPanel(new GridLayout());
        SetsPane.add(frameSizePane);

        frameSizeLabel = new JLabel("Размер кадра звука(frameSize)");
        frameSizeInput = new JTextField("",3);

        frameSizePane.add(frameSizeLabel);
        frameSizePane.add(frameSizeInput);



        frameRatePane = new JPanel(new GridLayout());
        SetsPane.add(frameRatePane);

        frameRateLabel = new JLabel("Число кадров в секунду звука(frameRate)");
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

        indexmixerLabel = new JLabel("Номер миксера(mixer#)");

        mixerchooser = new Choice();

        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        var counter=0;
        for (var s: mixInfos){
            mixerchooser.add((counter++)+"=>"+s.getName());
        }

        indexmixerPane.add(indexmixerLabel);
        indexmixerPane.add(mixerchooser);
        frame.pack();


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

    void initListeners() {
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

        saveAsSets.addActionListener(new ActionListener() {
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
                    new FileOutputStream(fullpathtoCheckFile).write(Sound_Settings.saveSetiingsToBytes(ss));
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
            System.out.println("Higing window!!!!");
            frame.setVisible(false);
        }
    });

   }


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        SSettings ss = new SSettings();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ss.createAndShowGUI();
        ss.initListeners();
        ss.frame.setVisible(true);
        ss.loadSets("./sound_settings.bin");
    };


}
