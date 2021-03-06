package app.GUIModules.Interface.GetBio.Audio;

import Message.BKKCheck.InputMessage;
import Message.BKKCheck.ResponceMessage;
import Message.abstractions.BinaryMessage;
import app.Essens.AppAktor;
import app.Essens.CypherImpl;
import app.Essens.Sound_Settings;
import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.GUIModules.NetworkSettings;
import app.Sound.Sound;
import app.abstractions.*;
import app.utils.Cypher;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;
public class SoundRecord extends ModuleGUI {
    AbstractAction mergeAction;
    AbstractAction voidAction;
    AbstractAction startRecord;
    AbstractAction stopRecord;
    AbstractAction checkAction;
    AbstractAction save1Action;
    AbstractAction save2Action;
    AbstractAction save3Action;
    AbstractAction playCurrent;
    AbstractAction playSlot1;
    AbstractAction playSlot2;
    AbstractAction playSlot3;
    AbstractAction createBundle;
    AbstractAction remindAction;
    private Cypher cypher;
    public final String slot1 = "slot1.wav";
    public final String slot2 = "slot2.wav";
    public final String slot3 = "slot3.wav";
    public final String check_shortcut = "control C";
    public final String playcurrent_shortcut = "alt Q";
    public final String playslot1_shortcut = "alt 1";
    public final String playslot2_shortcut = "alt 2";
    public final String playslot3_shortcut = "alt 3";
    public final String createbundle_shortcut = "alt B";
    public final String play_current = "Playcurrent" ;
    public final String play_slot1 = "Playslot1" ;
    public final String play_slot2 = "Playslot2" ;
    public final String play_slot3 = "Playslot3" ;
    public final String tempmerged = "merged.wav";

    public final String merge_shortcut = "control M";
    public final String merge= "merge";
    public final String letsmarked = "letsmarked";
    public final String createbundle = "createbundle";  

    public final String saveslot1_shortcut = "control 1";
    public final String saveslot2_shortcut = "control 2";
    public final String saveslot3_shortcut = "control 3";
    public final String startrecord="startrecord";
    public final String stoprecord="stoprecord";
    public final String startrec_shortcut = "control S";
    public final String stoptrec_shortcut = "control F";
    public final String remind_shortcut = "control H";
    public final String remind = "remind";

    public boolean slot1ready=false;
    public boolean slot2ready=false;
    public boolean slot3ready=false;


    public final String Tempfile = "temp.wav";


    Thread FPatchAndCheck;

    public Map<String, Integer> tableRequest = new HashMap<>();

    JMenuItem letsMarked;

    JMenu PlayItem;
    JMenu SaveItem;
    JMenuItem CheckItem;

    JMenuItem SsItem;

    JMenuItem Playslot1;
    JMenuItem Playslot2;
    JMenuItem Playslot3;
    JMenuItem Playcurrent;
    JMenuItem Saveslot1;
    JMenuItem Saveslot2;
    JMenuItem Saveslot3;
    JMenuItem HelpItem;

    JMenuItem MergerSlots;

    JMenuItem CreateBundle;

    public AppMenu MainMenu;

    public SSettings SoundSettings;
    public app.GUIModules.NetworkSettings NetworkSettings;
    public app.GUIModules.About About;
    public MergeAudioFrames MF;
    public boolean recording = false;
    public Sound BinarySound = null;
    public JPanel Panel;
    public JPanel ControlsPanel;
    public JPanel StartPanel;
    public JPanel StopPPanel;
    public JButton Check;
    public JButton Start;
    public JButton Stop;
    public JLabel StartLabel;
    public JLabel StopLabel;
    public JLabel InfoLabel;
    public boolean checked = false;
    public interop exchange;
    AppAktor akt;
    public Color StartBackgroundColor;
    public SoundRecord(SettingsContainer sc) throws IOException {
        this.SettsContainer = sc;
        cypher = new CypherImpl();
        frame = new JFrame(sc.VersionProg);
        SsItem = new JMenuItem("Настройки звука");
        PlayItem = new JMenu("Воспроизвести");
        CheckItem = new JMenuItem("Проверить записанный фрагмент");
        SaveItem = new JMenu("Сохранить фрагмент в слот...");
        HelpItem = new JMenuItem("Напомнить последовательности?(Ctrl + H)");
        Playcurrent = new JMenuItem("Текущий фрагмент   "+playcurrent_shortcut);
        Playslot1 = new JMenuItem("Слот1    "+playslot1_shortcut);
        Playslot2 = new JMenuItem("Слот2    "+playslot2_shortcut);
        Playslot3 = new JMenuItem("Слот3    "+playslot3_shortcut);
        Saveslot1 = new JMenuItem("Слот1    (Ctrl+1)");
        Saveslot2 = new JMenuItem("Слот2    (Ctrl+2)");
        Saveslot3 = new JMenuItem("Слот3    (Ctrl+3)");
        InfoLabel = new JLabel("");
        MergerSlots = new JMenuItem("Склеить образцы");
        Panel = new JPanel(new BorderLayout());
        Check = new JButton("Проверить записанный фрагмент  (Ctrl+C)");
        Panel.add(Check, BorderLayout.WEST);
        var controlPanelLayout = new FlowLayout();
        controlPanelLayout.setHgap(100);
        controlPanelLayout.setVgap(40);
        ControlsPanel = new JPanel(controlPanelLayout);
        StartPanel = new JPanel(new GridLayout(2,1));
        StopPPanel = new JPanel(new GridLayout(2,1));
        Start = new JButton("START");
        Stop = new JButton("STOP");
        StartLabel = new JLabel("Начать запись звукового фрагмента  (Ctrl+S)");
        StopLabel =new JLabel("Остановить запись     (Ctrl+F)");
        letsMarked = new JMenuItem("Промаркировать секции");
        CreateBundle=new JMenuItem("Создать аудиосборку (" +createbundle_shortcut);
        MainMenu = new AppMenu();


        var fos = new FileOutputStream(SettsContainer.lockSoundRecordfile);
        fos.write("".getBytes());
        fos.close();
    }
    
    public void setCypher(Cypher cypher){
        this.cypher=cypher;
    }

    public void initinterop(){
        exchange=new interop();
        exchange.checked=false;
        exchange.errorcode=-2;
        exchange.resultcheck=-2;
        exchange.resultloadso=-2;
    }

    public void disableSave(){
        SaveItem.setEnabled(false);
    }

    public void enableSave(){
        System.out.println("\n\n\nControl Passsed!!!\n\n\n");
        SaveItem.setEnabled(true);
    }

    public void enableCheck(){
        Check.setEnabled(true);
        CheckItem.setEnabled(true);
    }

    public void disableCheck(){
        System.out.println("Disabling check");
        Check.setEnabled(false);
        CheckItem.setEnabled(false);
    }

    public void enablePlay(){
        PlayItem.setEnabled(true);
    };


    public void disablePlay(){
      Playcurrent.setEnabled(false);
      Playslot1.setEnabled(false);
      Playslot2.setEnabled(false);
      Playslot3.setEnabled(false);
    };

    public void disableLetsMarked(){
        letsMarked.setEnabled(false);
        MergerSlots.setEnabled(false);
    }

    public void disableBundle(){
        CreateBundle.setEnabled(false);
    }

    public void enableBundle(){
        CreateBundle.setEnabled(true);
    }



    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 480);
        frame.setLocationRelativeTo(null);

        PlayItem.add(Playcurrent);
        PlayItem.add(Playslot1);
        PlayItem.add(Playslot2);
        PlayItem.add(Playslot3);

        SaveItem.add(Saveslot1);
        SaveItem.add(Saveslot2);
        SaveItem.add(Saveslot3);

        MainMenu.EditMenu.getItem(0).add(SsItem);

        MainMenu.WorkMenu.add(CheckItem);
        MainMenu.WorkMenu.add(PlayItem);
        MainMenu.WorkMenu.add(SaveItem);

        MainMenu.WorkMenu.add(MergerSlots);
        MainMenu.WorkMenu.add(CreateBundle);
        MainMenu.HelpMenu.add(HelpItem);

        frame.setJMenuBar(MainMenu);
        frame.getContentPane().add(Panel, BorderLayout.PAGE_END);

    //    Panel.add(Check, BorderLayout.WEST);
        Panel.add(InfoLabel, BorderLayout.WEST);//BorderLayout.EAST);

        InfoLabel.setText("I am here");

        StartPanel.add(Start);
        StartPanel.add(StartLabel);

        StopPPanel.add(Stop);
        StopPPanel.add(StopLabel);

        ControlsPanel.add(StartPanel, BorderLayout.WEST);
        ControlsPanel.add(StopPPanel, BorderLayout.EAST);

        frame.getContentPane().add(ControlsPanel, BorderLayout.CENTER);

        disableBundle();
        disableSave();
        disableCheck();
        disablePlay();
        disableLetsMarked();
        MainMenu.AboutFrame=About.frame;
        MainMenu.ParentFrame=this.frame;

    }

    public void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        About = new About(this.SettsContainer);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    About.preperaGUI();
                    About.initListeners();
                    About.frame.setLocationRelativeTo(null);
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

    public void initNetworkSettinFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        NetworkSettings =new NetworkSettings(SettsContainer.AudioCheckServiceAddrfile);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    NetworkSettings.preperaGUI();
                    NetworkSettings.initListeners();
                    NetworkSettings.tryReadData();
                    NetworkSettings.frame.setLocationRelativeTo(null);
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
    public void initSoundSettingFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        SoundSettings = new SSettings();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    SoundSettings.preperaGUI();
                    SoundSettings.initListeners();
                    SoundSettings.loadSets(SettsContainer.SoundSettings);
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

    public void initCreateBundle(){
        MF = new MergeAudioFrames(this.SettsContainer);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    MF.preperaGUI();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                MF.initListeners();

            }
        });
    }

    public void clearInfo() throws InterruptedException {
        InfoLabel.setText("");
        InfoLabel.updateUI();
        Thread.sleep(1000);
    }

    public void check(){
        Stop.setBackground(StartBackgroundColor);
        FPatchAndCheck = new Thread() {
            @Override
            public void run() {
                try {
                    hookThat(Tempfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] fileContent = null;
                var checkfile = new File(Tempfile);
                try {
                    fileContent = Files.readAllBytes(checkfile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    InputMessage inp = new  InputMessage(checkfile.getName(), fileContent,  akt.getURL_thisAktor(), uuid_);
                    System.out.println("\n\n\n\nSTARTING SENDING...");
                    System.out.println("AKTOR ADRESS="+akt.getURL_thisAktor());
                    System.out.println("SENDING =>> "+ NetworkSettings.sets.address);
                    akt.send(BinaryMessage.savedToBLOB(inp), NetworkSettings.sets.address);
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
        FPatchAndCheck.start();


    }

    public void initActions(){

        createBundle = new AbstractAction(createbundle) {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullCyclePlayed = true;
                MF.frame.setVisible(true);
            }
        };

        startRecord = new AbstractAction(startrecord){
            @Override
            public void actionPerformed(ActionEvent e1) {
                System.out.println("Pressed");
                checked=false;
                StartBackgroundColor = Start.getBackground();
                Start.setBackground(Color.RED);
                if (recording)
                    return;
                Start.setBackground(Color.RED);
                Sound_Settings ss = null;
                try {
                    ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File(SettsContainer.SoundSettings).toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BinarySound = new Sound(ss);
                InfoLabel.setText("");
                try {
                    BinarySound.startRecord("temp.wav");
                } catch (LineUnavailableException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА => ПРОВЕРЬТЕ ПРАВИЛЬНОСТЬ ЗВУКОВЫХ НАСТРОЕК");
                    Start.setBackground(StartBackgroundColor);
                    recording=false;
                    return;
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА => ПРОВЕРЬТЕ ПРАВИЛЬНОСТЬ ЗВУКОВЫХ НАСТРОЕК");
                    Start.setBackground(StartBackgroundColor);
                    recording=false;
                    return;
                } catch (IllegalArgumentException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА => ПРОВЕРЬТЕ ПРАВИЛЬНОСТЬ ЗВУКОВЫХ НАСТРОЕК");
                    Start.setBackground(StartBackgroundColor);
                    recording=false;
                    return;
                }
                recording=true;
            }
        };

        stopRecord = new  AbstractAction(stoprecord){
            @Override
            public void actionPerformed(ActionEvent e1) {

                if (!recording)
                    return;
                Stop.setBackground(Color.YELLOW);
                Start.setBackground(StartBackgroundColor);

                BinarySound.stopRecord();
                recording = false;
                enablePlay();
                enableCheck();
                Playcurrent.setEnabled(true);
                check();
            }
        };
        remindAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showMessageDialog(null, "0 1 2 3 4 5 6 7 8 9\n9 8 7 6 5 4 3 2 1 0\n5 9 4 7 3 1 8 6 0 2");
            }
        };

        checkAction = new AbstractAction("Check"){
            @Override
            public void actionPerformed(ActionEvent e1) {
               check();
            }
        };

        save1Action = new AbstractAction("save1"){
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    var fos = new FileOutputStream(slot1);
                    fos.write(Files.readAllBytes(new File(Tempfile).toPath()));
                    fos.close();
                    showMessageDialog(null, "Успешно сохранен!");
                    slot1ready=true;
                    Playslot1.setEnabled(true);
                    checkAllSlots();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        save2Action = new AbstractAction("save2"){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var fos = new FileOutputStream(slot2);
                    fos.write(Files.readAllBytes(new File(Tempfile).toPath()));
                    fos.close();
                    showMessageDialog(null, "Успешно сохранен!");
                    slot2ready=true;
                    Playslot2.setEnabled(true);
                    checkAllSlots();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        save3Action = new AbstractAction("save3"){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var fos = new FileOutputStream(slot3);
                    fos.write(Files.readAllBytes(new File(Tempfile).toPath()));
                    fos.close();
                    showMessageDialog(null, "Успешно сохранен!");
                    slot3ready=true;
                    checkAllSlots();
                    Playslot3.setEnabled(true);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        playCurrent = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound_Settings ss = null;
                try {
                    ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File(SettsContainer.SoundSettings).toPath()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                BinarySound = new Sound(ss);
                BinarySound.playSound(Tempfile);
            }
        };



        playSlot1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Try to play =>"+slot1);
                BinarySound.playSound(slot1);
            }
        };

        playSlot2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Try to play =>"+slot2);
                BinarySound.playSound(slot2);
            }
        };

        playSlot3 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Try to play =>"+slot3);
                BinarySound.playSound(slot3);
            }
        };

        mergeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound_Settings ss = null;
                try {
                    ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File("sound_settings.bin").toPath()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                BinarySound = new Sound(ss);
                BinarySound.margeWav(slot1, slot2, tempmerged);
                BinarySound.margeWav(tempmerged, slot3, SettsContainer.resultmerged);
                showMessageDialog(null, "Merge complete!");
                enableBundle();
            }
        };


        String bind = "Check";

        voidAction = new AbstractAction(bind){
            @Override
            public void actionPerformed(ActionEvent e1) {
                showMessageDialog(null, "void");
            }
        };

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                proceedExitTry("Аудиосборка не сформирована. Вы действительно хотите выйти?",SettsContainer.lockSoundRecordfile);
            }
        });


    }

    private void hookThat(String tempfile) throws IOException {
        Process p = Runtime.getRuntime().exec("./repack.sh");
    }


    @Override
    public void initListeners() {
        initActions();

        Start.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(startrec_shortcut), startrecord);
        Start.getActionMap().put(startrecord, startRecord);
        Start.addActionListener(startRecord);


        Stop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(stoptrec_shortcut), stoprecord);
        Stop.getActionMap().put(stoprecord, stopRecord);
        Stop.addActionListener(stopRecord);

        Saveslot1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(saveslot1_shortcut), slot1);
        Saveslot1.getActionMap().put(slot1, save1Action);
        Saveslot1.addActionListener(save1Action);


        Saveslot2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(saveslot2_shortcut), slot2);
        Saveslot2.getActionMap().put(slot2, save2Action);
        Saveslot2.addActionListener(save2Action);

        Saveslot3.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(saveslot3_shortcut), slot3);
        Saveslot3.getActionMap().put(slot3, save3Action);
        Saveslot3.addActionListener(save3Action);

        Playcurrent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(playcurrent_shortcut), play_current);
        Playcurrent.getActionMap().put(play_current, playCurrent);
        Playcurrent.addActionListener(playCurrent);

        Playslot1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(playslot1_shortcut), play_slot1);
        Playslot1.getActionMap().put(play_slot1, playSlot1);
        Playslot1.addActionListener(playSlot1);

        Playslot2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(playslot2_shortcut), play_slot2);
        Playslot2.getActionMap().put(play_slot2, playSlot2);
        Playslot2.addActionListener(playSlot2);

        Playslot3.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(playslot3_shortcut), play_slot3);
        Playslot3.getActionMap().put(play_slot3, playSlot3);
        Playslot3.addActionListener(playSlot3);

        MergerSlots.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(merge_shortcut), merge);
        MergerSlots.getActionMap().put(merge, mergeAction);
        MergerSlots.addActionListener(mergeAction);

        CreateBundle.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(createbundle_shortcut), createbundle);
        CreateBundle.getActionMap().put(createbundle, createBundle);
        CreateBundle.addActionListener(createBundle);

        HelpItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(remind_shortcut), remind);
        HelpItem.getActionMap().put(remind, remindAction);
        HelpItem.addActionListener(remindAction);


        String bind = "Check";



        checkAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        Check.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
               KeyStroke.getKeyStroke(check_shortcut), bind);// KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), bind);
        //KeyStroke.getKeyStroke("ctrl B") ///ctrl C

        Check.getActionMap().put(bind, checkAction);


        Check.addActionListener(checkAction);

        MainMenu.NsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                NetworkSettings.frame.setVisible(true);
            }
        });

        SsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                SoundSettings.frame.setVisible(true);
            }
        });
    }

    public void checkAllSlots(){
        if (slot1ready && slot2ready && slot3ready)
            MergerSlots.setEnabled(true);
    }


    public void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.setTableReqs(this.tableRequest);
        akt.checkedViaForm=exchange;
        akt.setAddress(SettsContainer.AudioClient);
        akt.setCypher(cypher);
        akt.spawn();
        akt.on_success=new OnSuccess() {
            @Override
            public void passed() {
                enableSave();
                disableCheck();
                InfoLabel.setText("   Проверка пройдена");
                InfoLabel.setForeground(Color.green);
            }
        };
        akt.on_failure=new OnFailure() {
            @Override
            public void failed(ResponceMessage resp) {
                InfoLabel.setForeground(Color.red);
                InfoLabel.setText("Error code="+resp.checkResult+"\n   "+SettsContainer.SoundErrorsDict.get(resp.checkResult));
                InfoLabel.updateUI();
            }


        };
    }

    public static void  main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        var sc = new SettingsContainer();
        SoundRecord sr = new SoundRecord(sc);

        sr.initNetworkSettinFrame();
        sr.initSoundSettingFrame();
        sr.initListeners();
        sr.initinterop();
        sr.initAboutFrame();
        sr.initCreateBundle();
        sr.prepareAktor();
        sr.preperaGUI();
        sr.frame.setVisible(true);
    }
}

