package app.GUIModules.Interface;
import Message.BKKCheck.ResponceMessage;
import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIAFindMessage;
import Message.toSMEV.MessageSMEV;
import Table.TablesEBSCheck;
import app.Essens.CypherImpl;
import app.GUIModules.NetworkSettings;
import app.abstractions.Model;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;
import app.utils.Cypher;
import app.utils.Extractor;
import app.utils.timeBasedUUID;
import impl.JAktor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class FindESIAFrame extends ModuleGUI {

    public final String saveRecivedOID="OID.bin";

    public final String exititem_shortcut="alt F4";
    public final String exititem= "close frame";
    public final String opensetts = "opensetts";
    public final String opensetts_shortcut = "control S";
    public final String makerequest_shortcut = "control R";
    public final String makerequest ="makerteq";


    public Map<String, Integer> tableRequest = new HashMap<>();

    public AbstractAction openSetts;
    public AbstractAction makeRequest;
    public AbstractAction saveReceuvedOID;
    public AbstractAction closeFrame;
    public AbstractAction exitItem;
    public JLabel LsenderPanel;

    public JLabel LOperSnils;
    public JTextField TOperSnils;

    public JLabel Lra;
    public JTextField  Tra;

    public JLabel LFIO;
    public JTextField TFIO;

    public JLabel LPass;
    public JTextField TPass;
    public JPanel PSender, Pra, PFIO, PPass, PButton, PMobile, PSnils;

    public JPanel MainPanel, PsnilsPanel, RootPanel;

    public JButton MakeRequest, OpenSetts, saveOID;

    public JLabel LMobile;
    public JTextField TMobile;

    public JLabel LSNILS;
    public JTextField TSNILS;



    public NetworkSettings ns;
    AppAktor akt;
    public timeBasedUUID Uuid ;

    private Cypher cypher;
    public Model Modell;

    public FindESIAFrame(SettingsContainer sc) throws IOException {

        this.SettsContainer=sc;
        cypher = new CypherImpl();
        frame = new JFrame("EBS GUI Client 1.5");
        MenuBar = new JMenuBar();
        FileMenu = new JMenu("Файл");
        ExitItem = new JMenuItem("Выйти");
        EditMenu = new JMenu("Правка");
        HelpMenu = new JMenu("Помощь");
        LsenderPanel=new JLabel("Отправитель");
        LOperSnils=new JLabel("Снилс оператора");
        Lra = new JLabel("Идентификатор центра обслуживания");
        LFIO = new JLabel("ФИО");
        LPass = new JLabel("Паспорт: <серия>-<номер>");
        LMobile=new JLabel("Мобильный телефон обратившегося (10 значащих цифр)");
        LSNILS=new JLabel("СНИЛС обратившегося (11 значащих цифр)");

        TOperSnils= new JTextField("",3);
        Tra = new JTextField("",3);
        TFIO=new JTextField("",3);
        TPass = new JTextField("",3);
        TMobile = new JTextField("",3);
        TSNILS = new JTextField("",3);


        PSnils = new JPanel(new GridLayout());
        PMobile = new JPanel(new GridLayout());

        PSender = new JPanel(new GridLayout());

        Pra = new JPanel(new GridLayout());
        PFIO = new JPanel(new GridLayout());
        PPass = new JPanel(new GridLayout());
        var gr_l = new GridLayout(10,1);
        RootPanel = new JPanel(new BorderLayout());

        MainPanel = new JPanel(gr_l);
        PsnilsPanel = new JPanel(new GridLayout());
        timeBasedUUID Uuid = new timeBasedUUID();
        OpenSetts = new JButton("Открыть настройки (Ctrl+S)");

        MakeRequest = new JButton("Найти запись (Ctrl+F)");
        PButton = new JPanel();

        this.Uuid = new timeBasedUUID();

        if (new File(SettsContainer.DumpModelFile).exists()){
            Modell = Model.restoredModel(Files.readAllBytes(new File(SettsContainer.DumpModelFile).toPath()));
        }
        else
        {
            var fos = new FileOutputStream(SettsContainer.DumpModelFile);
            Modell = new Model("","","","","","");
            fos.write(Model.saveModel(Modell));
            fos.close();
        }
    }

    public void savesession() throws IOException {
        Modell.SNILSoper=TOperSnils.getText();
        Modell.RA=Tra.getText();
        Modell.Pass=TPass.getText();
        Modell.FIO=TFIO.getText();
        Modell.SNILS=TSNILS.getText();
        Modell.Mobile=TMobile.getText();
        var fos = new FileOutputStream(SettsContainer.DumpModelFile);
        fos.write(Model.saveModel(Modell));
        fos.close();

    }

    public void restore_last(){
        TPass.setText(Modell.Pass);
        TFIO.setText(Modell.FIO);
        Tra.setText(Modell.RA);
        TOperSnils.setText(Modell.SNILSoper);
        TSNILS.setText(Modell.SNILS);
        TMobile.setText(Modell.Mobile);
    }

    public void enableSave(){

    };

    public void disableCheck(){

    };

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.getContentPane().add(RootPanel);



        MenuBar.add(FileMenu);
        MenuBar.add(EditMenu);
        MenuBar.add(HelpMenu);

        FileMenu.add(ExitItem);

        frame.setJMenuBar(MenuBar);
      //  MenuBar.add(FileMenu, )
        RootPanel.add(MainPanel, BorderLayout.NORTH);

        RootPanel.add(PButton, BorderLayout.SOUTH);

        MainPanel.add(LsenderPanel);
        MainPanel.add(PSender);
        MainPanel.add(Pra);
        MainPanel.add(PFIO);
        MainPanel.add(PPass);
        MainPanel.add(PSnils);
        MainPanel.add(PMobile);

        PSender.add(PsnilsPanel);

        PsnilsPanel.add(LOperSnils);
        PsnilsPanel.add(TOperSnils);

        Pra.add(Lra);
        Pra.add(Tra);

        PFIO.add(LFIO);
        PFIO.add(TFIO);

        PPass.add(LPass);
        PPass.add(TPass);

        PButton.add(OpenSetts);
        PButton.add(MakeRequest);

        PMobile.add(LMobile);
        PMobile.add(TMobile);

        PSnils.add(LSNILS);
        PSnils.add(TSNILS);


        frame.pack();

        restore_last();

    }

    @Override
    public void initListeners() {
        initActions();

        OpenSetts.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(opensetts_shortcut), opensetts);
        OpenSetts.getActionMap().put(opensetts, openSetts);
        OpenSetts.addActionListener(openSetts);


        ExitItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(exititem_shortcut), exititem);
        ExitItem.getActionMap().put(exititem, exitItem);
        ExitItem.addActionListener(exitItem);


        MakeRequest.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(makerequest_shortcut), makerequest);
        MakeRequest.getActionMap().put(makerequest, makeRequest);
        MakeRequest.addActionListener(makeRequest);
    }

    boolean checkFIO(){
        var FIO = Extractor.getFIO(TFIO.getText());
        if (FIO.size()<3)
            return false;
        return true;
    };

    boolean checkMobile(){
        if (TMobile.getText().length()!=10)
            return false;
        return true;
    };

    boolean checkClientSNILS(){
        if (TSNILS.getText().length() ==0)
            return false;
        return true;
    };




    @Override
    public void initActions() {
        openSetts = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ns.frame.setVisible(true);
            }
        };


        exitItem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            };
        };



        makeRequest = new AbstractAction("Check"){
            @Override
            public void actionPerformed(ActionEvent e1) {
                try {
                    savesession();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    var msg = new ESIAFindMessage();
                    var FIO = Extractor.getFIO(TFIO.getText());
                    var Pass = Extractor.getPass(TPass.getText());
                    if (!checkFIO()){
                        showMessageDialog(null, "заполните ФИО!");
                        return;
                    }
                    if (!checkMobile()){
                        showMessageDialog(null, "заполните мобильный телефон клиента => 10 значащих цифр, \nнапример 9604451213");
                        return;
                    }
                    if (!checkClientSNILS()){
                        showMessageDialog(null, "заполните СНИЛС клиента");
                        return;
                    }
                    msg.ID=uuid_;
                    msg.Ra=Tra.getText();
                    msg.OperatorSnils=ESIAFindMessage.getSNILSfromplain(TOperSnils.getText());
                    msg.Surname=FIO.get(0);
                    msg.Name=FIO.get(1);
                    msg.MiddleName=FIO.get(2);
                    msg.OperatorSnils=ESIAFindMessage.getSNILSfromplain(TOperSnils.getText());
                    msg.Passseria=Pass.get(0);
                    msg.Passnumber=Pass.get(1);
                    msg.SNILS=ESIAFindMessage.getSNILSfromplain(TSNILS.getText());
                    msg.MobileNumber=ESIAFindMessage.getMobilefromplain(TMobile.getText());
                    byte[] datatoWork = BinaryMessage.savedToBLOB(msg);

                    var SMEVMsg = new MessageSMEV(uuid_, "findesia", datatoWork, akt.rollbackAdressURL());

                    akt.send(BinaryMessage.savedToBLOB(SMEVMsg), ns.sets.address);
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






    private void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.setAddress(SettsContainer.ESIAClient);
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

    public static void main(String[] args ) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, InterruptedException, IOException {
        var fr = new FindESIAFrame(new SettingsContainer());
        fr.preperaGUI();
        fr.prepareAktor();
        fr.initNetworkSettinFrame();
        fr.initListeners();
    }

    interface OnSuccess{
        public void passed();
    }


    private void initNetworkSettinFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        ns = new NetworkSettings(SettsContainer.Smev3addressfile);
        ns.info.setText("Введите адрес сервиса СМЕВ3");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ns.preperaGUI();
                    ns.initListeners();
                    ns.tryReadData();

            } catch (IOException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            };
    });
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
            ResponceMessage resp = (ResponceMessage) BinaryMessage.restored(message);
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
