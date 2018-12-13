package app.GUIModules.Interface;
import Message.*;
import app.GUIModules.NetworkSettings;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;
import app.utils.Cypher;
import app.utils.Extractor;
import app.utils.timeBasedUUID;
import essens.ResponceMessage;
import essens.TablesEBSCheck;
import impl.JAktor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
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


    public Map<String, Integer> tableRequest = new HashMap<>();

    public AbstractAction openSetts;
    public AbstractAction sendRequestInESIA;
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
    public JPanel PSender, Pra, PFIO, PPass, PButton;

    public JPanel MainPanel, PsnilsPanel, RootPanel;

    public JButton check, OpenSetts, saveOID;

    public NetworkSettings ns;
    AppAktor akt;
    public timeBasedUUID Uuid ;

    private Cypher cypher;

    public FindESIAFrame(SettingsContainer sc){
        this.SettsContainer=sc;
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
        TOperSnils= new JTextField("",3);
        Tra = new JTextField("",3);
        TFIO=new JTextField("",3);
        TPass = new JTextField("",3);

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


        PButton = new JPanel(new GridLayout());
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

        frame.pack();

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


    }

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





        sendRequestInESIA= new AbstractAction("Check"){
            @Override
            public void actionPerformed(ActionEvent e1) {
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    var msg = new ESIAFindMessage();
                    var FIO = Extractor.getFIO(TFIO.getText());
                    var Pass = Extractor.getPass(TPass.getText());
                    if (FIO.size()<3){
                        showMessageDialog(null, "заполните ФИО!");
                        return;
                    }
                    msg.UUID=uuid_;
                    msg.Ra=Tra.getText();
                    msg.OperatorSnils=TOperSnils.getText();
                    msg.Surname=FIO.get(0);
                    msg.Name=FIO.get(1);
                    msg.MiddleName=FIO.get(2);
                    msg.OperatorSnils=TOperSnils.getText();
                    msg.Passseria=Pass.get(0);
                    msg.Passnumber=Pass.get(1);
                    akt.send(ESIAFindMessage.saveESIAFindMessage(msg), ns.sets.address);
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

    public static void main(String[] args ) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, InterruptedException {
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