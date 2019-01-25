package app.GUIModules.Interface.GetBio;


import Message.abstractions.BinaryMessage;
import Message.abstractions.FileInBinary;
import Message.toSMEV.EBS.Essens.PhotoBundle;
import Message.toSMEV.EBS.Essens.SoundBundle;
import app.Essens.Sound_Settings;
import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.GUIModules.Interface.GetBio.Audio.SoundRecord;
import app.GUIModules.Interface.GetBio.Video.PhotoMake;
import app.Sound.Sound;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static javax.swing.JOptionPane.showMessageDialog;


public class AppBio extends ModuleGUI {
    BufferedImage img;

    AbstractAction runSoundrecord;
    AbstractAction runPhotomake;
    AbstractAction watchBundle;
    Sound_Settings ss;
    Sound binarySound;
    public String runsoundrecord_shortcut = "alt S";
    public String watchbundle_shortcut = "control W";
    public String watchbundle = "watchbundle";
    public String runphotomake_shortcut = "alt P";
    public String runsoundrecord = "runsoundrecord";
    public String runphotomake = "runphotomake";
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
    public AppBio(SettingsContainer sc) throws IOException {
        this.SettsContainer=sc;
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
    }






    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.frame.setSize(600, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.MainMenu);
        WatchPanel.add(WatchButton);

       // PImage.add(LOID);
       // PImage.add(TOID);
        PImage.add(LImg, BorderLayout.WEST);
        PImage.add(LOID, BorderLayout.EAST);
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
            this.About = new About();
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
    }

    @Override
    public void initActions() {
        watchBundle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showMessageDialog(null, "Look that");
                byte[] arrImg = null;
                PhotoBundle pb= null;
                SoundBundle sb = null;
                if (!new File(SettsContainer.SavePhotoToFile).exists())
                    showMessageDialog(null, "Файла с фото нет");

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

                    //   FileInBinary.clean(pb);
                 //   FileInBinary.clean(sb);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                LOID.setText("DICK");
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

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        AppBio app = new AppBio(new SettingsContainer());
        app.initAboutFrame();
        app.preperaGUI();
        app.initActions();
        app.initListeners();

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

}
