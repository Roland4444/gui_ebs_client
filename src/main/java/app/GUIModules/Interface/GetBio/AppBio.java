package app.GUIModules.Interface.GetBio;


import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.GUIModules.Interface.GetBio.Audio.SoundRecord;
import app.GUIModules.Interface.GetBio.Video.PhotoMake;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;


public class AppBio extends ModuleGUI {
    AbstractAction runSoundrecord;
    AbstractAction runPhotomake;
    public String runsoundrecord_shortcut = "alt S";
    public String runphotomake_shortcut = "alt P";
    public String runsoundrecord = "runsoundrecord";
    public String runphotomake = "runphotomake";
    public AppMenu MainMenu;
    public app.GUIModules.About About;
    public JPanel RootPanel;
    public JButton RunSound;
    public JButton RunPhoto;
    public SoundRecord SR;
    public PhotoMake PM;
    public AppBio(SettingsContainer sc){
        this.SettsContainer=sc;
        this.MainMenu=new AppMenu();
        this.frame= new JFrame();
        this.RootPanel=new JPanel(new BorderLayout());
        RunSound = new JButton("Запись голоса");
        RunPhoto = new JButton("Сделать фото");

    }






    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.frame.setSize(600, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.MainMenu);

        this.frame.getContentPane().add(RootPanel, BorderLayout.NORTH);
        RootPanel.add(RunSound, BorderLayout.LINE_START);
        RootPanel.add(RunPhoto, BorderLayout.LINE_END);

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
    }

    @Override
    public void initActions() {
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

}