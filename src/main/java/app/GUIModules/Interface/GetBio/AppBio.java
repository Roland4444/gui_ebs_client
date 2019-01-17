package app.GUIModules.Interface.GetBio;


import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.GUIModules.Interface.GetBio.Audio.SoundRecord;
import app.GUIModules.Interface.GetBio.Video.PhotoMake;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppBio extends ModuleGUI {
    public AppMenu MainMenu;
    public app.GUIModules.About About;
    public JPanel RootPanel;
    public JButton StartSound;
    public JButton StartPhoto;
    public SoundRecord SR;
    public PhotoMake PM;
    public AppBio(SettingsContainer sc){
        this.SettsContainer=sc;
        this.MainMenu=new AppMenu();
        this.frame= new JFrame();
        this.RootPanel=new JPanel(new BorderLayout());
        StartSound = new JButton("Запись голоса");
        StartPhoto = new JButton("Сделать фото");

    }


    public void initSoundFrame(){

    };

    public void initPhotoFrame(){

    };

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.frame.setSize(600, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.MainMenu);

        this.frame.getContentPane().add(RootPanel, BorderLayout.NORTH);
        RootPanel.add(StartSound, BorderLayout.LINE_START);
        RootPanel.add(StartPhoto, BorderLayout.LINE_END);

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

    }

    @Override
    public void initActions() {

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        AppBio app = new AppBio(new SettingsContainer());
        app.initAboutFrame();
        app.preperaGUI();

        app.frame.setVisible(true);

    }

}
