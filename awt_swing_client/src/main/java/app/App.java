package app;
import app.utils.timeBasedUUID;
import essens.InputMessage;
import essens.ResponceMessage;
import essens.TablesEBSCheck;
import impl.JAktor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

public class App {
    TablesEBSCheck tebs = new TablesEBSCheck();
    private int voron = 0;
    private JFrame mainFrame;
    private JLabel infoLabel;
    JLabel label_resultLoadSo;
    JLabel label_resultCheck;
    JLabel label_errorCode;
    private JButton addCrow;
    private JButton removeCrow;
    private JButton checkButton;
    public String fullpathtoCheckFile;
    timeBasedUUID uuid = new timeBasedUUID();
    public Map<String, Integer> tableRequest = new HashMap<>();
    AppAktor akt;


    public class AppAktor extends JAktor {
        Frame formBack;
        JLabel label_resultLoadSo;
        JLabel label_resultCheck;
        JLabel label_errorCode;
        Boolean justSpawned=false;
        @Override
        public void receive(byte[] message) throws IOException {

            System.out.println("Received!!!! via console");
            label_resultLoadSo.setText("Changed via AKTOR; Message = Received");
           // showMessageDialog(null, "JUST RECEIVED");
            var resp = ResponceMessage.restoreBytesToResponceMessage(message);
            System.out.println("\n\n\nRECEIVED");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tableRequest.get(resp.ID)!=null){
                tableRequest.remove(resp.ID);
                tableRequest.put(resp.ID, resp.checkResult);
                this.label_resultLoadSo.setText(tebs.onLoadLibraryErrors.get(resp.ResultLoadingSoSymbols));
                this.label_errorCode.setText("Code result check=>"+resp.lastErrorInSession);
                if ((resp.checkResult==0) && (resp.lastErrorInSession==0) && (resp.ResultLoadingSoSymbols==0))
                    this.label_resultCheck.setText("проверка успешна");
                else
                    this.label_resultCheck.setText("проверка не пройдена");

            }
        }
    }

    private void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.formBack=mainFrame;
        akt.label_resultLoadSo = infoLabel;
        akt.label_errorCode=label_errorCode;
        akt.label_resultCheck=label_resultCheck;

        akt.setAddress("http://127.0.0.1:14444/");
        akt.spawn();
        showMessageDialog(null, "AKtor spawned");
    }

    private void preperaGUI(){
        mainFrame = new JFrame("CLIENT EBS");
        mainFrame.setSize(600, 400);
        checkButton = new JButton("Choose file");
        infoLabel = new JLabel("INFO LABEL");
        label_resultLoadSo= new JLabel("RESULT LOAD SO LABEL");
        label_resultCheck= new JLabel("LABEL Result");
        label_errorCode= new JLabel("Label Error CODE");

        mainFrame.add( label_resultLoadSo);
        mainFrame.add(label_resultCheck);
        mainFrame.add(label_errorCode);
        JPanel buttonsPanel = new JPanel(new FlowLayout());



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
            public void actionPerformed(ActionEvent e2) {
                FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.LOAD);
                fd.setVisible(true);
                fullpathtoCheckFile = fd.getDirectory()+fd.getFile();
                System.out.print(fullpathtoCheckFile);
                File file = new File(fullpathtoCheckFile);
                if (file != null) {
                    byte [] fileContent=null;
                    try {
                        fileContent = Files.readAllBytes(file.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    var uuid_ = uuid.generate();
                    tableRequest.put(uuid_,-3);
                    try {
                        InputMessage inp = new  InputMessage(file.getName(), fileContent,  "photo", akt.getURL_thisAktor(), uuid_);
                        System.out.println("\n\n\n\nSTARTING SENDING...");
                        System.out.println("AKTOR ADRESS="+akt.getURL_thisAktor());
                        akt.send(InputMessage.saveMessageToBytes(inp), "http://127.0.0.1:12121/");
                        System.out.println("\n\n\n\nSENDING FINISHED!!!...");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //   akt.send(InputMessage)
                }
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


