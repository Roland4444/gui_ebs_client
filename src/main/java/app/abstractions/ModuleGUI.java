package app.abstractions;

import app.Essens.AppAktor;
import app.utils.timeBasedUUID;

import javax.swing.*;
import java.io.File;

public  abstract class ModuleGUI {
    public JFrame frame;
    public boolean fullCyclePlayed = false;
    abstract public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException;
    abstract public void initListeners();
    abstract public void initActions();
    public SettingsContainer SettsContainer;
    protected timeBasedUUID Uuid = new timeBasedUUID();
    public JMenuBar MenuBar;
    public void clean(String Lock){
        var lockFile = new File(Lock);
        if (lockFile.delete())
            lockFile.delete();
        System.exit(0);
    }
    public void proceedExitTry(String WarningMessage, String LockFiles){
        if (fullCyclePlayed)
            clean(LockFiles);

        Object[] options = {"Да, выйти",
                "Нет, остаться"};
        int n = JOptionPane.showOptionDialog(frame,
                WarningMessage,
                "Внимание",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n==0)
            clean(LockFiles);
    }
}
