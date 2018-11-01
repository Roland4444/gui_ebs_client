package app.GUIModules;

import app.abstractions.ModuleGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class NetworkSettings extends ModuleGUI {
    public JFrame frame;

    public JButton saveSets;
    public JButton Exit;


    public JTextField serverAdress;
    public JTextField port;
    public JLabel serverNameLabel;
    public JLabel portLabel;
    public JLabel info;
    public JLabel ender;

    public JPanel SettingPanel;

    public JPanel ButtonSaveExitpanel ;

    public NetworkSettings() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        preperaGUI();
        initListeners();
    }


    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame=new JFrame("Сетевые настройки");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        info = new JLabel("Введите адрес сервиса проверки биометрических данных");
        ButtonSaveExitpanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(ButtonSaveExitpanel, BorderLayout.PAGE_END);

        saveSets = new JButton("Сохранить и закрыть");
        Exit = new JButton("Закрыть без сохранения");

        ButtonSaveExitpanel.add(saveSets, BorderLayout.WEST);
        ButtonSaveExitpanel.add(Exit, BorderLayout.EAST);

        serverNameLabel = new JLabel("http://");
        portLabel=new JLabel(":");
        ender = new JLabel("/");

        serverAdress = new JTextField("", 10);
        port=new JTextField("", 6);

        SettingPanel = new JPanel(new FlowLayout());


        frame.getContentPane().add(info, BorderLayout.PAGE_START);
        frame.getContentPane().add(SettingPanel, BorderLayout.CENTER);

        SettingPanel.add(serverNameLabel);
        SettingPanel.add(serverAdress);
        SettingPanel.add(portLabel);
        SettingPanel.add(port);
        SettingPanel.add(ender);

        frame.pack();
    }

    @Override
    public void initListeners() {
        saveSets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((serverAdress.getText().length() == 0) || (port.getText().length()==0)){
                    showMessageDialog(null, "Заполните все поля!");
                    return;
                }
            }
        });
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });


    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        NetworkSettings ns=new NetworkSettings();
        ns.frame.setVisible(true);
    }
}
