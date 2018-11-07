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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;


public class SoundRecord extends ModuleGUI {
    public final String slot1 = "slot1.wav";
    public final String slot2 = "slot2.wav";
    public final String slot3 = "slot3.wav";



    timeBasedUUID uuid = new timeBasedUUID();
    public final String tempfile = "temp.wav";


    public Map<String, Integer> tableRequest = new HashMap<>();
    JMenuBar menuBar;

    JMenu fileMenu ;
    JMenu EditMenu;
    JMenu helpMenu ;
    JMenu settsMenu;
    JMenu workMenu;

    JMenu playItem ;
    JMenu saveItem ;
    JMenuItem checkItem ;
    JMenuItem exitItem ;

    JMenuItem nsItem;
    JMenuItem ssItem;
    JMenuItem aboutItem;

    JMenuItem playslot1 ;
    JMenuItem playslot2 ;
    JMenuItem playslot3 ;
    JMenuItem playcurrent;
    JMenuItem saveslot1 ;
    JMenuItem saveslot2;
    JMenuItem saveslot3;
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


    }

    public void initinterop(){
        exchange=new interop();
        exchange.checked=false;
        exchange.errorcode=-2;
        exchange.resultcheck=-2;
        exchange.resultloadso=-2;
    }

    public void disableSave(){
        save.setEnabled(false);
        saveItem.setEnabled(false);
    }

    public void enableSave(){
        System.out.println("\n\n\nControl Passsed!!!\n\n\n");
        save.setEnabled(true);
        saveItem.setEnabled(true);
    }

    public void enableCheck(){
        check.setEnabled(false);
        checkItem.setEnabled(false);
    }

    public void disableCheck(){
        check.setEnabled(false);
        checkItem.setEnabled(false);
    }

    public void enablePlay(){
        playItem.setEnabled(true);
    };


    public void disablePlay(){
      playItem.setEnabled(false);

    };


    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("EBS GUI Client 1.5");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        menuBar = new JMenuBar();

        fileMenu = new JMenu("Файл");
        exitItem = new JMenuItem("Выйти");
        fileMenu.add(exitItem);

        EditMenu = new JMenu("Правка");
        nsItem = new JMenuItem("Настройки сервиса");
        ssItem = new JMenuItem("Настройки звука");
        settsMenu = new JMenu("Настройкм");
        settsMenu.add(nsItem);
        settsMenu.add(ssItem);
        EditMenu.add(settsMenu);

        workMenu = new JMenu("Сервис");
        playItem = new JMenu("Воспроизвести");
        checkItem = new JMenuItem("Проверить записанный фрагмент");
        saveItem = new JMenu("Сохранить фрагмент в слот...");
        playcurrent = new JMenuItem("Текущий фрагмент");
        playslot1 = new JMenuItem("Слот1");
        playslot2 = new JMenuItem("Слот2");
        playslot3 = new JMenuItem("Слот3");
        saveslot1 = new JMenuItem("Слот1");
        saveslot2 = new JMenuItem("Слот2");
        saveslot3 = new JMenuItem("Слот3");
        playItem.add(playcurrent);
        playItem.add(playslot1);
        playItem.add(playslot2);
        playItem.add(playslot3);

        saveItem.add(saveslot1);
        saveItem.add(saveslot2);
        saveItem.add(saveslot3);

        workMenu.add(checkItem);
        workMenu.add(playItem);
        workMenu.add(saveItem);

        helpMenu = new JMenu("Помощь");
        aboutItem = new JMenuItem("О программе");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(EditMenu);
        menuBar.add(workMenu);
        menuBar.add(helpMenu);

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

        disableSave();
        disableCheck();
        disablePlay();


    }

    private void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        about= new About();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
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
        SwingUtilities.invokeLater(new Runnable() {
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
        SwingUtilities.invokeLater(new Runnable() {
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
                } catch (IllegalArgumentException e){
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
                checkItem.setEnabled(true);
                enablePlay();
            }
        });



        var checkAction = new AbstractAction("check"){
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
        };

        var save1Action = new AbstractAction("save1"){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var fos = new FileOutputStream(slot1);
                    fos.write(Files.readAllBytes(new File(tempfile).toPath()));
                    fos.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        var save2Action = new AbstractAction("save2"){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var fos = new FileOutputStream(slot2);
                    fos.write(Files.readAllBytes(new File(tempfile).toPath()));
                    fos.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        var save3Action = new AbstractAction("save3"){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var fos = new FileOutputStream(slot3);
                    fos.write(Files.readAllBytes(new File(tempfile).toPath()));
                    fos.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        saveslot1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("ctrl S 1"), "save1");

        saveslot2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("ctrl S 2"), "save2");


        String bind = "check";

        var voidAction = new AbstractAction(bind){
            @Override
            public void actionPerformed(ActionEvent e1) {
                showMessageDialog(null, "void");
            }
        };

        checkAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        check.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
               KeyStroke.getKeyStroke("control alt 1"), bind);// KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), bind);
        //KeyStroke.getKeyStroke("ctrl B") ///ctrl C

        check.getActionMap().put(bind, checkAction);


        check.addActionListener(checkAction);

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
        akt.on_success=new OnSuccess() {
            @Override
            public void passed() {
                enableSave();
                disableCheck();
            }
        };
      //  showMessageDialog(null, "AKtor spawned");
    }

    public static void  main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        SoundRecord sr = new SoundRecord();
        sr.preperaGUI();
        sr.initNetworkSettinFrame();
        sr.initSoundSettingFrame();
        sr.initListeners();
        sr.initinterop();
        sr.initAboutFrame();
        sr.prepareAktor();
        sr.frame.setVisible(true);


    }

    interface OnSuccess{
        public void passed();
    }

    public class AppAktor extends JAktor {
        public interop checkedViaForm;
        public OnSuccess on_success;
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
                if ((resp.checkResult==0) && (resp.lastErrorInSession==0) && (resp.ResultLoadingSoSymbols==0)) {
                    save.setEnabled(true);
                    on_success.passed();
                }
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
