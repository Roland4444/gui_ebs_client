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
    public NetworkSettings(){
        frame=new JFrame("Сетевые настройки");

        info = new JLabel("Введите адрес сервиса проверки биометрических данных");
        ButtonSaveExitpanel = new JPanel(new BorderLayout());

        saveSets = new JButton("Сохранить и закрыть");
        Exit = new JButton("Закрыть без сохранения");

        serverNameLabel = new JLabel("http://");
        portLabel=new JLabel(":");
        ender = new JLabel("/");

        serverAdress = new JTextField("", 10);
        port=new JTextField("", 6);

        SettingPanel = new JPanel(new FlowLayout());
    }
    
    public NetworkSettings(String sets){
        settingFiles = sets;
        frame=new JFrame("Сетевые настройки");

        info = new JLabel("Введите адрес сервиса проверки биометрических данных");
        ButtonSaveExitpanel = new JPanel(new BorderLayout());

        saveSets = new JButton("Сохранить и закрыть");
        Exit = new JButton("Закрыть без сохранения");

        serverNameLabel = new JLabel("http://");
        portLabel=new JLabel(":");
        ender = new JLabel("/");

        serverAdress = new JTextField("", 10);
        port=new JTextField("", 6);

        SettingPanel = new JPanel(new FlowLayout());
    }

    private String settingFiles="NetworkSettings.bin";
    private final String defaultAdress =  "http://127.0.0.1:12121/";
    private IPSetts defaultSets = new IPSetts( defaultAdress);
    public JFrame frame;

    private JButton saveSets;
    private JButton Exit;

    public IPSetts sets;


    private JTextField serverAdress;
    private JTextField port;
    private JLabel serverNameLabel;
    private JLabel portLabel;
    private JLabel info;
    private JLabel ender;

    private JPanel SettingPanel;

    private JPanel ButtonSaveExitpanel ;

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


    public void tryReadData() throws IOException, InterruptedException {
        if (!new File(settingFiles).exists()){
            var fos = new FileOutputStream(settingFiles);
            fos.write(IPSetts.saveIPSettsToBytes(defaultSets));
            fos.close();
            Thread.sleep(400);
        }

        byte[] arr = Files.readAllBytes(new File(settingFiles).toPath());;
        sets = IPSetts.restoreBytesToIPSetts(arr);
        serverAdress.setText(getAddress(sets.address));
        port.setText(getPort(sets.address));

    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(ButtonSaveExitpanel, BorderLayout.PAGE_END);
        ButtonSaveExitpanel.add(saveSets, BorderLayout.WEST);
        ButtonSaveExitpanel.add(Exit, BorderLayout.EAST);


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
                try {
                    tryReadData();
                    System.out.println("Reload setts...");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
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

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {

        NetworkSettings ns=new NetworkSettings();
        ns.preperaGUI();
        ns.initListeners();
        ns.tryReadData();
        ns.frame.setVisible(true);
    }

    @Override
    public void initActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
