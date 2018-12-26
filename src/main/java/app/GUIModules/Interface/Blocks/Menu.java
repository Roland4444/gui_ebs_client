package app.GUIModules.Interface.Blocks;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Menu extends JMenuBar {
    public JMenu FileMenu;
    public JMenuItem ExitItem;
    public JMenu EditMenu;
    public JMenuItem AboutItem, NsItem;
    public JMenu  WorkMenu, SettsMenu ;;
    public JMenuBar MenuBar;
    public JMenu HelpMenu;
    public JFrame ParentFrame;
    public JFrame AboutFrame;
    public Menu(JFrame Parent, JFrame Info){
        this.ParentFrame=Parent;
        this.AboutFrame=Info;
        MenuBar = new JMenuBar();

        FileMenu = new JMenu("Файл");
        EditMenu = new JMenu("Правка");
        SettsMenu = new JMenu("Настройкм");
        WorkMenu = new JMenu("Сервис");
        HelpMenu = new JMenu("Помощь");

        ExitItem = new JMenuItem("Выйти");
        NsItem = new JMenuItem("Настройки сервиса");
        AboutItem = new JMenuItem("О программе");

        FileMenu.add(ExitItem);
        EditMenu.add(SettsMenu);
        SettsMenu.add(NsItem);
        HelpMenu.add(AboutItem);

        MenuBar.add(FileMenu);
        MenuBar.add(EditMenu);
        MenuBar.add(HelpMenu);

        initListeners();

    }

    public void initListeners(){
        ExitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParentFrame.dispatchEvent(new WindowEvent(ParentFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        AboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutFrame.setVisible(true);
            }
        });


    }

}
