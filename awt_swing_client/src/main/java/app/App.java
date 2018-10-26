package app;
import app.utils.timeBasedUUID;
import essens.ResponceMessage;
import impl.JAktor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

public class App {
    private int voron = 0;
    private Frame mainFrame;
    private JLabel countLabel;
    private JButton addCrow;
    private JButton removeCrow;
    private JButton checkButton;
    public String fullpathtoCheckFile;
    timeBasedUUID uuid = new timeBasedUUID();
    public Map<String, Integer> tableRequest = new HashMap<>();
    AppAktor akt;


    public class AppAktor extends JAktor {
        App formBack;
        Boolean justSpawned=false;
        public void setFormBack(App in){
            this.formBack=in;
        }
        @Override
        public void receive(byte[] message) throws IOException {
            var resp = ResponceMessage.restoreBytesToResponceMessage(message);
            showMessageDialog(null, "App started");
            System.out.println("\n\n\nRECEIVED");
            showMessageDialog(null, "JUST RECEIVED");
            if (tableRequest.get(resp.ID)!=null){
                tableRequest.remove(resp.ID);
                tableRequest.put(resp.ID, resp.checkResult);
                showMessageDialog(null, "JUST RECEIVED");
            }
        }
    }

    private void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.setAddress("http://127.0.0.1:14444");
        akt.spawn();
    }

    private void preperaGUI(){
        mainFrame = new JFrame("CLEINT EBS");
        mainFrame.setSize(600, 400);
        checkButton = new JButton("Choose file");

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(checkButton);

        mainFrame.add(buttonsPanel, BorderLayout.SOUTH);
        initListeners();
        showMessageDialog(null, "App started");

        mainFrame.setVisible(true);
    }

    public App() throws InterruptedException {
        preperaGUI();
        prepareAktor();
    }
    private void initListeners() {
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.LOAD);
                fd.setVisible(true);
                fullpathtoCheckFile = fd.getDirectory()+fd.getFile();
                System.out.print(fullpathtoCheckFile);
            }
        });

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                System.out.println("==============================>CLOSING!!!!");
                akt.terminate();
                Runtime.getRuntime().exit(0);
            }
        });
    }




    public static void main(String[] args) throws InterruptedException {
        App app = new App();
    }
}


