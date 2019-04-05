package app.GUIModules.Interface.GetBio;


import Message.BKKCheck.ResponceMessage;
import Message.abstractions.BinaryMessage;
import Message.abstractions.FileInBinary;
import Message.toSMEV.EBS.EBSMessage;
import Message.toSMEV.EBS.Essens.OtherInfo;
import Message.toSMEV.EBS.Essens.PhotoBundle;
import Message.toSMEV.EBS.Essens.SoundBundle;
import Message.toSMEV.MessageSMEV;
import Table.TablesEBSCheck;
import app.Essens.CypherImpl;
import app.Essens.Sound_Settings;
import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.GUIModules.Interface.GetBio.Audio.SoundRecord;
import app.GUIModules.Interface.GetBio.Video.PhotoMake;
import app.GUIModules.NetworkSettings;
import app.Sound.Sound;
import app.abstractions.ModuleGUI;
import app.abstractions.OnSuccess;
import app.abstractions.SettingsContainer;
import app.abstractions.interop;
import app.utils.Cypher;
import impl.JAktor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;


public class AppBio extends ModuleGUI {
    public Map<String, Integer> tableRequest = new HashMap<>();
    BufferedImage img;
    private Cypher cypher;
    AbstractAction createFatbundle;
    AbstractAction runSoundrecord;
    AbstractAction runPhotomake;
    AbstractAction watchBundle;
    AbstractAction sendPreparedBundle;
    Sound_Settings ss;
    Sound binarySound;
    OtherInfo of;
    public String createfatbundle = "createfatbundle";
    public String createfatbundle_shortcut = "control F";

    public app.GUIModules.NetworkSettings NetworkSettings;
    AppAktor akt;

    public String runsoundrecord_shortcut = "alt S";
    public String watchbundle_shortcut = "control W";
    public String watchbundle = "watchbundle";
    public String runphotomake_shortcut = "alt P";
    public String runsoundrecord = "runsoundrecord";
    public String runphotomake = "runphotomake";
    public String sendpreparedbundle = "sendpreparedbundle";
    public String sendpreparedbundle_shortcut = "alt S";
    public AppMenu MainMenu;
    public app.GUIModules.About About;
    public JPanel RootPanel;
    public JButton RunSound;
    public JButton RunPhoto;
    public JButton WatchButton;
    public JLabel LImg;
    public SoundRecord SR;
    public PhotoMake PM;
    public JPanel WatchPanel;
    public JPanel PImage;
    public JLabel LOID;
    public JTextField TOID;
    public EBSMessage EBSM;
    public JButton Createfatbundle;
    public JButton SendPreparedBundle;
    PhotoBundle pb= null;
    SoundBundle sb = null;
    public AppBio(SettingsContainer sc) throws IOException {
        this.SettsContainer=sc;
        cypher = new CypherImpl();
        this.MainMenu=new AppMenu();
        this.frame= new JFrame();
        this.RootPanel=new JPanel(new BorderLayout());
        RunSound = new JButton("Запись голоса");
        RunPhoto = new JButton("Сделать фото");
        WatchPanel=new JPanel();
        WatchButton = new JButton("Просмотреть сборку");
        LImg = new JLabel();
        ss = Sound_Settings.restoreBytesToSetiings(Files.readAllBytes(new File(SettsContainer.SoundSettings).toPath()));
        binarySound = new Sound(ss);
        PImage =new JPanel(new BorderLayout());
        LOID = new JLabel("oid клиента");
        TOID = new JTextField("",3);
        Createfatbundle= new JButton("Сделать финальную сборку");
        SendPreparedBundle = new JButton("Отправить подготовленную сборку");
    }






    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.frame.setSize(600, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.MainMenu);
        WatchPanel.add(WatchButton);
        PImage.add(LImg, BorderLayout.WEST);
        PImage.add(LOID, BorderLayout.EAST);
        PImage.add(Createfatbundle, BorderLayout.CENTER);
        PImage.add(SendPreparedBundle, BorderLayout.SOUTH);
        this.frame.getContentPane().add(RootPanel, BorderLayout.NORTH);
        RootPanel.add(RunSound, BorderLayout.LINE_START);
        RootPanel.add(RunPhoto, BorderLayout.LINE_END);
        RootPanel.add(WatchPanel, BorderLayout.CENTER);
        RootPanel.add(PImage, BorderLayout.SOUTH);

        MainMenu.AboutFrame=this.About.frame;
        MainMenu.ParentFrame=this.frame;
      //  this.frame.pack();
    }

    public void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
            this.About = new About(this.SettsContainer);
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


    @Override
    public void initListeners() {
        RunSound.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(runsoundrecord_shortcut), runsoundrecord);
        RunSound.getActionMap().put(runsoundrecord, runSoundrecord);
        RunSound.addActionListener(runSoundrecord);

        RunPhoto.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(runphotomake_shortcut), runphotomake);
        RunPhoto.getActionMap().put(runphotomake, runPhotomake);
        RunPhoto.addActionListener(runPhotomake);

        WatchButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(watchbundle_shortcut), watchbundle);
        WatchButton.getActionMap().put(watchbundle, watchBundle);
        WatchButton.addActionListener(watchBundle);

        Createfatbundle.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(createfatbundle_shortcut), createfatbundle);
        Createfatbundle.getActionMap().put(createfatbundle, createFatbundle);
        Createfatbundle.addActionListener(createFatbundle);

        SendPreparedBundle.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(sendpreparedbundle_shortcut), sendpreparedbundle);
        SendPreparedBundle.getActionMap().put(sendpreparedbundle, sendPreparedBundle);
        SendPreparedBundle.addActionListener(sendPreparedBundle);

        MainMenu.NsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                NetworkSettings.frame.setVisible(true);
            }
        });
    }


    public void initNetworkSettinFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        NetworkSettings =new NetworkSettings(SettsContainer.Smev3addressfile);
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

    @Override
    public void initActions() {
        sendPreparedBundle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String userDirLocation = System.getProperty("user.dir");
                File userDir = new File(userDirLocation);
                final JFileChooser fc = new JFileChooser(userDir);
                int returnVal = fc.showOpenDialog(null);
                if (returnVal != 0)
                    return;
                MessageSMEV msg = new MessageSMEV();
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                msg.ID=uuid_;
                msg.pseudo="ebs";
                try {
                    msg.DataToWork =Files.readAllBytes(fc.getSelectedFile().toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    msg.addressToReply=akt.getURL_thisAktor();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                try {
                    akt.send(BinaryMessage.savedToBLOB(msg),NetworkSettings.sets.address);
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                }


            }
        };
        createFatbundle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EBSM = new EBSMessage(of, sb, pb);

                try {
                    BinaryMessage.write(BinaryMessage.savedToBLOB(EBSM), SettsContainer.SaveClientDataToFile);
                    showMessageDialog(null, "Write complete!");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                MessageSMEV msg = new MessageSMEV();
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                msg.ID=uuid_;
                msg.pseudo="ebs";
                msg.DataToWork =BinaryMessage.savedToBLOB(EBSM);
                try {
                    msg.addressToReply=akt.getURL_thisAktor();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                try {
                    akt.send(BinaryMessage.savedToBLOB(msg),NetworkSettings.sets.address);
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n"+e);
                }


            }
        };

        watchBundle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showMessageDialog(null, "Look that");
                byte[] arrImg = null;

                if (!new File(SettsContainer.SavePhotoToFile).exists())
                    showMessageDialog(null, "Файла с фото нет");

                if (!new File(SettsContainer.SaveOtherInfoToFile).exists())
                    showMessageDialog(null, "Файла с доп данными нет");

                if (!new File(SettsContainer.SaveSoundDataWithtagsToFile).exists())
                    showMessageDialog(null, "Файла с аудио нет");

                try {
                    pb=(PhotoBundle) BinaryMessage.restored(Files.readAllBytes(new File(SettsContainer.SavePhotoToFile).toPath()));
                    sb = (SoundBundle) BinaryMessage.restored(Files.readAllBytes(new File(SettsContainer.SaveSoundDataWithtagsToFile).toPath()));
                    FileInBinary.suspendToDisk(pb);
                    FileInBinary.suspendToDisk(sb);

                    //Thread.sleep(1000);
                    img = ImageIO.read(new File(pb.filename));
                    System.out.println(img.getWidth());
                    ImageIcon icon =  new ImageIcon(
                            img.getScaledInstance(
                                    img.getWidth(null)/4,
                                    img.getHeight(null)/4,
                                    Image.SCALE_SMOOTH )
                    );
                    LImg.setIcon(icon);
                    LImg.updateUI();
                    playWav pw = new playWav();
                    pw.ss = binarySound;
                    pw.wav = sb.filename;
                    pw.start();
                    System.out.println(icon.getIconHeight());


                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    of = (OtherInfo) BinaryMessage.restored(Files.readAllBytes(new File(SettsContainer.SaveOtherInfoToFile).toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LOID.setText("OID=>  "+of.OID);
                LOID.updateUI();

            }
        };

        runPhotomake = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (SettsContainer.productionMode)
                    disablePhoto();
                try {
                    System.out.println("starting...");
                    Process p = Runtime.getRuntime().exec(SettsContainer.runPhotoMake);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        runSoundrecord = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (SettsContainer.productionMode)
                        disableAudio();
                    System.out.println("starting...");
                    Process p = Runtime.getRuntime().exec(SettsContainer.runSoundRecord);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    private void disableAudio() {
        RunSound.setEnabled(false);
    }

    private void disablePhoto() {
        RunPhoto.setEnabled(false);
    }

    public void initwatcher(){
        Watcher watcher = new Watcher();
        watcher.SoundButton=RunSound;
        watcher.PhotoButton=RunPhoto;
        watcher.sc=this.SettsContainer;
        watcher.start();
    }

    public void prepareAktor() throws InterruptedException {
        akt = new AppAktor();

        akt.setAddress(SettsContainer.SmevClient);
        akt.setCypher(cypher);
        akt.spawn();
        akt.on_success=new OnSuccess() {
            @Override
            public void passed() {

            }
        };
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        AppBio app = new AppBio(new SettingsContainer());
        app.initAboutFrame();
        app.initNetworkSettinFrame();
        app.preperaGUI();
        app.initActions();
        app.initListeners();
        app.prepareAktor();

        app.frame.setVisible(true);
        app.initwatcher();

    }

    class Watcher extends Thread{
        JButton PhotoButton, SoundButton;
        SettingsContainer sc;
        @Override
        public void run(){
            try {
                while (true){
                    Thread.sleep(5000);
                    if (!new File(sc.lockPhotomakefile).exists())
                        PhotoButton.setEnabled(true);
                    if (!new File(sc.lockSoundRecordfile).exists())
                        SoundButton.setEnabled(true);
                }



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class playWav extends Thread{
        public Sound ss;
        public String wav;

        @Override
        public void run(){
                ss.playSound(wav);
        }
    }

    public class AppAktor extends JAktor {
        public interop checkedViaForm;
        public OnSuccess on_success;
        public JButton save;
        public TablesEBSCheck tebs = new TablesEBSCheck();
        private Cypher cypher;
        public void setCypher(Cypher cypher){
            this.cypher=cypher;
        }

        @Override
        public int send(byte[] message, String address) throws IOException {
            return this.client.send(this.cypher.encrypt(message), address);
        }

        @Override
        public void receive(byte[] message_) throws IOException {
            System.out.println("Received!!!! via console");
            byte[] message =  cypher.decrypt(message_);
            ResponceMessage resp = (ResponceMessage) BinaryMessage.restored(message);
            System.out.println("\n\n\nRECEIVED");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tableRequest.get(resp.ID)!=null){
                tableRequest.remove(resp.ID);
                tableRequest.put(resp.ID, resp.checkResult);
                if ((resp.checkResult==0) ) {
                    on_success.passed();
                }
            }
        }
    }


}
