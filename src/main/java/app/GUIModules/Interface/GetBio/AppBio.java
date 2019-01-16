package app.GUIModules.Interface.GetBio;


import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;

import javax.swing.*;
import java.io.IOException;

public class AppBio extends ModuleGUI {
    public AppMenu MainMenu;
    public app.GUIModules.About About;
    public AppBio(SettingsContainer sc){
        this.SettsContainer=sc;
        this.MainMenu=new AppMenu();
        this.frame= new JFrame();
    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.frame.setSize(600, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.MainMenu);
        this.frame.pack();

        MainMenu.AboutFrame=this.About.frame;
        MainMenu.ParentFrame=this.frame;

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
