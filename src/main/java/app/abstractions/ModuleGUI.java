package app.abstractions;

import javax.swing.*;

public  abstract class ModuleGUI {
    public JFrame frame;
    abstract public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException;
    abstract public void initListeners();
    abstract public void initActions();
    public SettingsContainer SettsContainer;

    public JMenuBar MenuBar;
    public JMenu FileMenu;
    public JMenu EditMenu;
    public JMenu HelpMenu;

    public JMenuItem ExitItem;

}
