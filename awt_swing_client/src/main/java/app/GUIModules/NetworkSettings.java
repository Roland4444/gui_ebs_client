package app.GUIModules;

import app.Essens.IPSetts;
import app.abstractions.ModuleGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static javax.swing.JOptionPane.showMessageDialog;

public class NetworkSettings extends ModuleGUI {
    public final String settingFiles="ns.bin";
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

    public NetworkSettings(){


    }

    public String getPort(String fulladdress){
        int init = fulladdress.lastIndexOf(":");
        if (init < 0)
            return "";
        return fulladdress.substring(init+1, fulladdress.length()-1);
    }

    public String getAddress(String fulladdress){
        int init = fulladdress.indexOf("/");
        int stooped = fulladdress.lastIndexOf(":");
        if ((init < 0) || (stooped<0))
            return "";
        return fulladdress.substring(init+2, stooped);
    }


    public void tryReadData() throws IOException {
        byte[] arr = Files.readAllBytes(new File(settingFiles).toPath());;
        IPSetts sets = IPSetts.restoreBytesToIPSetts(arr);
        serverAdress.setText(getAddress(sets.address));
        port.setText(getPort(sets.address));

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
                IPSetts sets = new IPSetts();
                sets.address="http://"+serverAdress.getText()+":"+port.getText()+"/";
                try {
                    FileOutputStream fos = new FileOutputStream(settingFiles);
                    fos.write(IPSetts.saveIPSettsToBytes(sets));
                    fos.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });


    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        NetworkSettings ns=new NetworkSettings();
        ns.preperaGUI();
        ns.initListeners();
        ns.tryReadData();
        ns.frame.setVisible(true);
    }
}
