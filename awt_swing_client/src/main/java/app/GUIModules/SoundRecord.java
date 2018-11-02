package app.GUIModules;
import app.Sound.Sound;
import app.Essens.Sound_Settings;
import app.abstractions.ModuleGUI;
import app.utils.timeBasedUUID;
import essens.InputMessage;
import essens.ResponceMessage;
import essens.TablesEBSCheck;
import impl.JAktor;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class SoundRecord extends ModuleGUI {
    timeBasedUUID uuid = new timeBasedUUID();
    public final String tempfile = "temp.wav";


    public Map<String, Integer> tableRequest = new HashMap<>();
    JMenuBar menuBar;

    JMenu fileMenu ;
    JMenu EditMenu;
    JMenu helpMenu ;
    JMenu settsMenu;
    JMenuItem exitItem ;

    JMenuItem nsItem = new JMenuItem("Настройки сервиса");
    JMenuItem ssItem = new JMenuItem("Настройки звука");
    JMenuItem aboutItem = new JMenuItem("О программе");
    public SSettings ss;
    public NetworkSettings ns;
    public About about;
    public boolean recording = false;
    public Sound binarySound = null;
    public JFrame frame;
    public JPanel panel;
    public JPanel controlsPanel;
    public JPanel startPanel;
    public JPanel stopPPanel;
    public JButton check;
    public JButton save;
    public JButton start;
    public JButton stop;
    public JLabel startLabel;
    public JLabel stopLabel;
    public boolean checked = false;
    public interop exchange;
    AppAktor akt;

    public Color StartBackgroundColor;
    public SoundRecord() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        preperaGUI();
        initNetworkSettinFrame();
        initSoundSettingFrame();
        initListeners();
        initinterop();
        initAboutFrame();
        prepareAktor();

    }

    public void initinterop(){
        exchange=new interop();
        exchange.checked=false;
        exchange.errorcode=-2;
        exchange.resultcheck=-2;
        exchange.resultloadso=-2;
    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("EBS GUI Client 1.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        menuBar = new JMenuBar();

        fileMenu = new JMenu("Файл");
        EditMenu = new JMenu("Правка");
        helpMenu = new JMenu("Помощь");
        settsMenu = new JMenu("Настройкм");
        exitItem = new JMenuItem("Выйти");

        nsItem = new JMenuItem("Настройки сервиса");
        ssItem = new JMenuItem("Настройки звука");
        aboutItem = new JMenuItem("О программе");
        helpMenu.add(aboutItem);
        settsMenu.add(nsItem);
        settsMenu.add(ssItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        menuBar.add(EditMenu);
        menuBar.add(helpMenu);
        EditMenu.add(settsMenu);
        frame.setJMenuBar(menuBar);

        panel = new JPanel(new BorderLayout());

        frame.getContentPane().add(panel, BorderLayout.PAGE_END);


        check = new JButton("Проверить записанный фрагмент");
        save = new JButton("Сохранить фрагмент в WAV...");
        panel.add(check, BorderLayout.WEST);
        panel.add(save, BorderLayout.EAST);

        var controlPanelLayout = new FlowLayout();
        controlPanelLayout.setHgap(100);
        controlPanelLayout.setVgap(40);

        controlsPanel = new JPanel(controlPanelLayout);
        startPanel = new JPanel(new GridLayout(2,1));
        stopPPanel = new JPanel(new GridLayout(2,1));

        start = new JButton("START");
        stop = new JButton("STOP");
        startLabel = new JLabel("Начать запись звукового фрагмента");
        stopLabel=new JLabel("Остановить запись");

        startPanel.add(start);
        startPanel.add(startLabel);

        stopPPanel.add(stop);
        stopPPanel.add(stopLabel);

        controlsPanel.add(startPanel, BorderLayout.WEST);
        controlsPanel.add(stopPPanel, BorderLayout.EAST);

        frame.getContentPane().add(controlsPanel, BorderLayout.CENTER);
        save.setEnabled(false);
        check.setEnabled(false);

        frame.setVisible(true);

    }

    private void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        about= new About();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    about.preperaGUI();
                    about.initListeners();
                    about.frame.setLocationRelativeTo(null);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initNetworkSettinFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        ns=new NetworkSettings();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ns.preperaGUI();
                    ns.initListeners();
                    ns.tryReadData();
                    ns.frame.setLocationRelativeTo(null);
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    private void initSoundSettingFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        ss = new SSettings();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ss.preperaGUI();
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


    @Override
    public void initListeners() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                System.out.println("Pressed");
                save.setEnabled(false);
                checked=false;
                StartBackgroundColor = start.getBackground();
                start.setBackground(Color.RED);
                if (recording)
                    return;
                start.setBackground(Color.RED);
                Sound_Settings ss = null;
                try {
                    ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binarySound = new Sound(ss);
                try {
                    binarySound.startRecord("temp.wav");
                } catch (LineUnavailableException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА => ПРОВЕРЬТЕ ПРАВИЛЬНОСТЬ ЗВУКОВЫХ НАСТРОЕК");
                    start.setBackground(StartBackgroundColor);
                    recording=false;
                    return;
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА => ПРОВЕРЬТЕ ПРАВИЛЬНОСТЬ ЗВУКОВЫХ НАСТРОЕК");
                    start.setBackground(StartBackgroundColor);
                    recording=false;
                    return;
                } catch (java.lang.IllegalArgumentException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА => ПРОВЕРЬТЕ ПРАВИЛЬНОСТЬ ЗВУКОВЫХ НАСТРОЕК");
                    start.setBackground(StartBackgroundColor);
                    recording=false;
                    return;
                }
                recording=true;
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                if (!recording)
                    return;
                start.setBackground(StartBackgroundColor);
                binarySound.stopRecord();
                recording = false;
                check.setEnabled(true);
            }
        });



        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                byte[] fileContent = null;
                var checkfile = new File(tempfile);
                try {
                    fileContent = Files.readAllBytes(checkfile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var uuid_ = uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    InputMessage inp = new  InputMessage(checkfile.getName(), fileContent,  akt.tebs.voice, akt.getURL_thisAktor(), uuid_);
                    System.out.println("\n\n\n\nSTARTING SENDING...");
                    System.out.println("AKTOR ADRESS="+akt.getURL_thisAktor());
                    System.out.println("SENDING =>> "+ns.sets.address);
                    akt.send(InputMessage.saveMessageToBytes(inp), ns.sets.address);
                    System.out.println("\n\n\n\nSENDING FINISHED!!!...");
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");

                }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
                }
            }
        });

        nsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                ns.frame.setVisible(true);
            }
        });

        ssItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                ss.frame.setVisible(true);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                about.frame.setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });


        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.SAVE);
                FilenameFilter wavFilter = new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        String lowercaseName = name.toLowerCase();
                        if (lowercaseName.endsWith(".wav")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
                fd.setFilenameFilter(wavFilter);
                fd.setVisible(true);

                var savingFile = fd.getDirectory()+fd.getFile();
                System.out.print(savingFile);
                try {
                    var content = Files.readAllBytes(new File(tempfile).toPath());
                    var fos = new FileOutputStream(savingFile);
                    fos.write(content);
                    fos.close();
                    showMessageDialog(null, "Файл"+savingFile+" сохранен!");
                    new File(tempfile).delete();
                    save.setEnabled(false);
                    check.setEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    private void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.checkedViaForm=exchange;
        akt.save=save;
        akt.setAddress("http://127.0.0.1:14444/");
        akt.spawn();
        showMessageDialog(null, "AKtor spawned");
    }

    public static void  main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        SoundRecord sr = new SoundRecord();
    }

    public class AppAktor extends JAktor {
        public interop checkedViaForm;
        public JButton save;
        public TablesEBSCheck tebs = new TablesEBSCheck();
        Boolean justSpawned=false;
        @Override
        public void receive(byte[] message) throws IOException {
            System.out.println("Received!!!! via console");
            // showMessageDialog(null, "JUST RECEIVED");
            var resp = ResponceMessage.restoreBytesToResponceMessage(message);
            System.out.println("\n\n\nRECEIVED");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tableRequest.get(resp.ID)!=null){
                tableRequest.remove(resp.ID);
                tableRequest.put(resp.ID, resp.checkResult);
                if ((resp.checkResult==0) && (resp.lastErrorInSession==0) && (resp.ResultLoadingSoSymbols==0))
                { save.setEnabled(true);}
        //        else
          //          this.label_resultCheck.setText("проверка не пройдена");

            }
        }
    }

    public class interop{
        public boolean checked;
        public int resultloadso;
        public int resultcheck;
        public int errorcode;
    }

}
