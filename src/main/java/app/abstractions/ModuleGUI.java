package app.abstractions;

import javax.swing.*;

public  abstract class ModuleGUI {
    public JFrame Frame;
    abstract public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException;
    abstract public void initListeners();


}
