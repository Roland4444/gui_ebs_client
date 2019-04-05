package app.abstractions;

import app.Essens.AppAktor;
import app.utils.timeBasedUUID;

import javax.swing.*;

public  abstract class ModuleGUI {
    public JFrame frame;
    abstract public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException;
    abstract public void initListeners();
    abstract public void initActions();
    public SettingsContainer SettsContainer;
    protected timeBasedUUID Uuid = new timeBasedUUID();
    public JMenuBar MenuBar;
}
