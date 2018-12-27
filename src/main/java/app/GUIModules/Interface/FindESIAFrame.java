package app.GUIModules.Interface;
import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
import Message.toSMEV.ESIAFind.ESIAFindMessageResult;
import Message.toSMEV.MessageSMEV;
import Table.TablesEBSCheck;
import app.Essens.CypherImpl;
import app.GUIModules.Interface.Blocks.EBSOperatorPanel;
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
    public Color InitialColor;
    public final String saveRecivedOID="OID.bin";

    public final String exititem_shortcut="alt F4";
    public final String exititem= "close frame";
    public final String opensetts = "opensetts";
    public final String opensetts_shortcut = "control S";
    public final String makerequest_shortcut = "control F";
    public final String makerequest ="makerteq";
    public final String proceedregister = "proceedregister";
    public final String proceedregister_shortcut = "control P";


    public Map<String, Integer> tableRequest = new HashMap<>();

    public AbstractAction openSetts;
    public AbstractAction makeRequest;
    public AbstractAction saveReceuvedOID;
    public AbstractAction closeFrame;
    public AbstractAction exitItem;
    public AbstractAction proceedRegister;
    public JLabel LsenderPanel;

    public JLabel LOperSnils;
    public JTextField TOperSnils;

    public JLabel Lra;
    public JTextField  Tra;

    public JLabel LFIO;
    public JTextField TFIO;

    public JLabel LPass;
    public JTextField TPass;
    public JMenuItem OpenSetts;
    public JPanel PSender, Pra, PFIO, PPass, PButton, PMobile, PSnils;

    public JPanel MainPanel, PsnilsPanel, RootPanel;

    public JButton MakeRequest, saveOID;
    public JButton ProceedRegister, CreateESIAFRomScratch, UpgradeCurrentEsia;

    public JLabel LMobile;
    public JTextField TMobile;

    public JLabel LSNILS;
    public JTextField TSNILS;

    public EBSOperatorPanel ExtendedPanel;

    public NetworkSettings ns;
    AppAktor akt;
    public timeBasedUUID Uuid ;

    private Cypher cypher;
    public Model Modell;

    public FindESIAFrame(SettingsContainer sc) throws IOException {
        ExtendedPanel = new EBSOperatorPanel();
        this.SettsContainer=sc;
        cypher = new CypherImpl();
        frame = new JFrame(sc.VersionProg);
        MenuBar = new JMenuBar();
        FileMenu = new JMenu("Файл");
        ExitItem = new JMenuItem("Выйти");
        EditMenu = new JMenu("Правка");

        HelpMenu = new JMenu("Помощь");
        LsenderPanel=new JLabel("Отправитель");
        LOperSnils=new JLabel("Снилс оператора");
        Lra = new JLabel("Идентификатор центра обслуживания");
        LFIO = new JLabel("ФИО");
        LPass = new JLabel("Паспорт: <серия><номер>(10 значащих цифр)");
        LMobile=new JLabel("Мобильный телефон обратившегося (10 значащих цифр)");
        LSNILS=new JLabel("СНИЛС обратившегося (11 значащих цифр)");
        OpenSetts = new JMenuItem("Открыть настройки (Ctrl+S)");
        TOperSnils= new JTextField("",3);
        Tra = new JTextField("",3);
        TFIO=new JTextField("",3);
        TPass = new JTextField("",3);
        TMobile = new JTextField("",3);
        TSNILS = new JTextField("",3);
        ProceedRegister = new JButton("Продолжить регистрацию в ЕБС");
        CreateESIAFRomScratch = new JButton("Зарегистрировать новую запись в ЕСИА");
        UpgradeCurrentEsia = new JButton("Подтвердить упрощенную запись в ЕСИА");
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

        MakeRequest = new JButton("Найти запись (Ctrl+F)");
        PButton = new JPanel();
        InitialColor = ProceedRegister.getBackground();
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

    public void disableUpgrade(){
        UpgradeCurrentEsia.setVisible(false);
        UpgradeCurrentEsia.setEnabled(false);
        UpgradeCurrentEsia.setBackground(InitialColor);
    };

    public void enableUpgrade(){
        UpgradeCurrentEsia.setVisible(true);
        UpgradeCurrentEsia.setEnabled(true);
        UpgradeCurrentEsia.setBackground(Color.ORANGE);
    };

    public void disableCreate(){
        CreateESIAFRomScratch.setVisible(false);
        CreateESIAFRomScratch.setEnabled(false);
        CreateESIAFRomScratch.setBackground(InitialColor);
    };

    public void enableCreate(){
        CreateESIAFRomScratch.setVisible(true);
        CreateESIAFRomScratch.setEnabled(true);
        CreateESIAFRomScratch.setBackground(Color.YELLOW);
    }

    public void disableProceed(){
        ProceedRegister.setVisible(false);
        ProceedRegister.setEnabled(false);
        ProceedRegister.setBackground(InitialColor);
    };

    public void enableProceed(){
        ProceedRegister.setVisible(true);
        ProceedRegister.setEnabled(true);
        ProceedRegister.setBackground(Color.green);
    };

    public void disableRequest(){
        MakeRequest.setEnabled(false);
    }

    public void enableRequest(){
        MakeRequest.setEnabled(true);
    }


    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.getContentPane().add(RootPanel);

        disableProceed();
        disableUpgrade();
        disableCreate();

        MenuBar.add(FileMenu);
        MenuBar.add(EditMenu);
        MenuBar.add(HelpMenu);
        EditMenu.add(OpenSetts);
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

        MainPanel.add(ExtendedPanel);

        PSender.add(PsnilsPanel);

        PsnilsPanel.add(LOperSnils);
        PsnilsPanel.add(TOperSnils);

        Pra.add(Lra);
        Pra.add(Tra);

        PFIO.add(LFIO);
        PFIO.add(TFIO);

        PPass.add(LPass);
        PPass.add(TPass);

        PButton.add(MakeRequest);
        PButton.add(ProceedRegister);
        PButton.add(UpgradeCurrentEsia);
        PButton.add(CreateESIAFRomScratch);

        PMobile.add(LMobile);
        PMobile.add(TMobile);

        PSnils.add(LSNILS);
        PSnils.add(TSNILS);


        frame.pack();

        restore_last();

    }


    public void unlockinputs(){
        this.TSNILS.enable();
        this.TMobile.enable();
        this.TOperSnils.enable();
        this.TFIO.enable();
        this.Tra.enable();
    }

    public void lockinputs(){
        this.TSNILS.disable();
        this.TSNILS.disable();
        this.TMobile.disable();
        this.TOperSnils.disable();
        this.TFIO.disable();
        this.Tra.disable();
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

    boolean checkPass(){
        if (TPass.getText().length() !=10)
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
                disableProceed();
                disableCreate();
                disableUpgrade();
                disableRequest();
                try {
                    savesession();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    var msg = new ESIAFindMessageInitial();
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
                    if (!checkPass()){
                        showMessageDialog(null, "заполните паспорт клиента");
                        return;
                    }
                    msg.ID=uuid_;
                    msg.Ra=Tra.getText();
                    msg.OperatorSnils=ESIAFindMessageInitial.getSNILSfromplain(TOperSnils.getText());
                    msg.Surname=FIO.get(0);
                    msg.Name=FIO.get(1);
                    msg.MiddleName=FIO.get(2);
                    msg.OperatorSnils=ESIAFindMessageInitial.getSNILSfromplain(TOperSnils.getText());
                    msg.Passseria=Pass.get(0);
                    msg.Passnumber=Pass.get(1);
                    msg.SNILS=ESIAFindMessageInitial.getSNILSfromplain(TSNILS.getText());
                    msg.MobileNumber=ESIAFindMessageInitial.getMobilefromplain(TMobile.getText());
                    byte[] datatoWork = BinaryMessage.savedToBLOB(msg);

                    var SMEVMsg = new MessageSMEV(uuid_, "findesia", datatoWork, akt.rollbackAdressURL());

                    akt.send(BinaryMessage.savedToBLOB(SMEVMsg), ns.sets.address);
                    System.out.println("\n\n\n\nSENDING FINISHED!!!...");
                    lockinputs();
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
            ESIAFindMessageResult resp = (ESIAFindMessageResult) BinaryMessage.restored(message_);
            if (tableRequest.get(resp.ID)==null)
                return;
            enableRequest();
            unlockinputs();
            System.out.println("Received!!!! via console");
            byte[] message =  cypher.decrypt(message_);

            System.out.println("\n\n\nRECEIVED");
            if ((resp.oid!=null) && resp.trusted.equals("trusted"))
                enableProceed();
            if (resp.oid==null)
                enableCreate();
            if ((resp.oid!=null) && (!resp.trusted.equals("trusted")))
                enableUpgrade();
        //    showMessageDialog(null, "Status => "+ resp.trusted+"\nOID=>"+resp.oid);



                //        else
                //          this.label_resultCheck.setText("проверка не пройдена");

            tableRequest.remove(resp.ID);
        }
    }

}
