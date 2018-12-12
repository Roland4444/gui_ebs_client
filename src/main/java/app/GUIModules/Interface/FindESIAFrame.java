package app.GUIModules.Interface;
import Message.*;
import app.GUIModules.Audio.SoundRecord;
import app.GUIModules.NetworkSettings;
import app.abstractions.ModuleGUI;
import app.utils.Cypher;
import app.utils.Extractor;
import app.utils.timeBasedUUID;
import essens.InputMessage;
import essens.ResponceMessage;
import essens.TablesEBSCheck;
import impl.JAktor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class FindESIAFrame extends ModuleGUI {
    public final String saveRecivedOID="OID.bin";

    public Map<String, Integer> tableRequest = new HashMap<>();

    public AbstractAction OpenSetts;
    public AbstractAction sendRequestInESIA;
    public AbstractAction SaveReceuvedOID;
    public JLabel LsenderPanel;

    public JLabel LOperSnils;
    public JTextField TOperSnils;

    public JLabel Lra;
    public JTextField  Tra;

    public JLabel LFIO;
    public JTextField TFIO;

    public JLabel LPass;
    public JTextField TPass;
    public JPanel PSender, Pra, PFIO, PPass;

    public JPanel MainPanel, PsnilsPanel;

    public JButton check, networkSetts, saveOID;

    public NetworkSettings ns;
    AppAktor akt;
    public timeBasedUUID Uuid ;

    private Cypher cypher;

    public FindESIAFrame(){
        frame = new JFrame("EBS GUI Client 1.5");
        LsenderPanel=new JLabel("Отправитель");
        LOperSnils=new JLabel("Снилс оператора");
        Lra = new JLabel("Идентификатор центра обслуживания");
        LFIO = new JLabel("ФИО");
        LPass = new JLabel("Паспорт: <серия>-<номер>");
        TOperSnils= new JTextField("",3);
        Tra = new JTextField("",3);
        TFIO=new JTextField("",3);
        TPass = new JTextField("",3);

        PSender = new JPanel(new GridLayout());
        Pra = new JPanel(new GridLayout());
        PFIO = new JPanel(new GridLayout());
        PPass = new JPanel(new GridLayout());
        var gr_l = new GridLayout(10,1);

        MainPanel = new JPanel(gr_l);
        PsnilsPanel = new JPanel(new GridLayout());
        timeBasedUUID Uuid = new timeBasedUUID();

        ns = new NetworkSettings("smev3service.bin");
    }

    public void enableSave(){

    };

    public void disableCheck(){

    };

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.getContentPane().add(MainPanel);

        MainPanel.add(LsenderPanel);
        MainPanel.add(PSender);
        MainPanel.add(Pra);
        MainPanel.add(PFIO);
        MainPanel.add(PPass);

        PSender.add(PsnilsPanel);

        PsnilsPanel.add(LOperSnils);
        PsnilsPanel.add(TOperSnils);

        Pra.add(Lra);
        Pra.add(Tra);

        PFIO.add(LFIO);
        PFIO.add(TFIO);

        PPass.add(LPass);
        PPass.add(TPass);

        frame.pack();
        ns.info.setText("Введите адрес сервиса СМЕВ3");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initActions() {
        sendRequestInESIA= new AbstractAction("Check"){
            @Override
            public void actionPerformed(ActionEvent e1) {
                byte[] fileContent = null;

                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    var msg = new ESIAFindMessage();
                    msg.OperatorSnils=TOperSnils.getText();
                    var FIO = Extractor.getFIO(TFIO.getText());
                    if (FIO.size()<3){
                        showMessageDialog(null, "заполните ФИО!");
                        return;

                    }
                    msg.Surname=FIO.get(0);
                    msg.Name=FIO.get(1);
                    msg.MiddleName=FIO.get(2);
                    msg.OperatorSnils=TOperSnils.getText();
                    msg.Passnumber

                    akt.send(InputMessage.saveMessageToBytes(inp), ns.sets.address);
                    System.out.println("\n\n\n\nSENDING FINISHED!!!...");
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");

               }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
               }
            }
        };
    }




    public static void main(String[] args ) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        var fr = new FindESIAFrame();
        fr.preperaGUI();
    }

    private void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.setAddress("http://127.0.0.1:17777/");
        akt.setCypher(cypher);
        akt.spawn();
        akt.on_success=new OnSuccess() {
            @Override
            public void passed() {
                enableSave();
                disableCheck();
            }
        };
        //  showMessageDialog(null, "AKtor spawned");
    }

    interface OnSuccess{
        public void passed();
    }




    public class AppAktor extends JAktor {

        public OnSuccess on_success;
        public JButton save;
        public TablesEBSCheck tebs = new TablesEBSCheck();
        private Cypher cypher;
        public void setCypher(Cypher cypher){
            this.cypher=cypher;
        }

        @Override
        public int send(byte[] message, String address) throws IOException {
            return this.client.send(this.cypher.encrypt(message), address);
        }

        @Override
        public void receive(byte[] message_) throws IOException {
            System.out.println("Received!!!! via console");
            byte[] message =  cypher.decrypt(message_);
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
                if ((resp.checkResult==0) && (resp.lastErrorInSession==0) && (resp.ResultLoadingSoSymbols==0)) {
                    on_success.passed();
                }
                //        else
                //          this.label_resultCheck.setText("проверка не пройдена");

            }
        }
    }

}
