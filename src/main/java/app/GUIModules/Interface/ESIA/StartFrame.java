package app.GUIModules.Interface.ESIA;
import Message.BKKCheck.ResponceMessage;
import Message.abstractions.BinaryMessage;
import Message.toSMEV.EBS.Essens.OtherInfo;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
import Message.toSMEV.ESIAFind.ESIAFindMessageResult;
import Message.toSMEV.MessageSMEV;
import Table.TablesEBSCheck;
import app.Essens.AppAktor;
import app.Essens.CypherImpl;
import app.GUIModules.About;
import app.GUIModules.Interface.Blocks.Panels.ClientPanel;
import app.GUIModules.Interface.Blocks.Panels.EBSOperatorPanel;
import app.GUIModules.Interface.Blocks.MainMenu.AppMenu;
import app.GUIModules.NetworkSettings;
import app.abstractions.Model;
import app.abstractions.ModuleGUI;
import app.abstractions.OnFailure;
import app.abstractions.SettingsContainer;
import app.utils.Cypher;
import app.utils.Extractor;
import app.utils.timeBasedUUID;
import impl.JAktor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class StartFrame extends ModuleGUI {
    public Color InitialColor;
    public final String saveReceivedOID ="OID.bin";

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

    public JButton MakeRequest;
    public JButton ProceedRegister, CreateESIAFRomScratch, UpgradeCurrentEsia;

    public JLabel LMobile;
    public JTextField TMobile;

    public JLabel LSNILS;
    public JTextField TSNILS;

    public EBSOperatorPanel ExtendedPanel;
    public ClientPanel ClientPanelP;

    public OtherInfo oi;

    public NetworkSettings ns;
    AppAktor akt;
    public timeBasedUUID Uuid ;

    private Cypher cypher;
    public Model Modell;
    public AppMenu MainMenu;
    public app.GUIModules.About About;

    public void initOtherInfo(){
        oi = new OtherInfo();
        oi.Mnemonic= "ESIA";
        oi.RegMnemonic = "981601_3T";
    };

    public StartFrame(SettingsContainer sc) throws IOException {
        ExtendedPanel = new EBSOperatorPanel();
        ClientPanelP = new ClientPanel();
        this.SettsContainer=sc;
        cypher = new CypherImpl();
        frame = new JFrame(sc.VersionProg);

        initOtherInfo();



        LsenderPanel=new JLabel("Отправитель");
        LOperSnils=new JLabel("Снилс оператора");
        Lra = new JLabel("Идентификатор центра обслуживания");
        LFIO = new JLabel("ФИО");
        LPass = new JLabel("Паспорт: <серия><номер>(10 значащих цифр)");
        LMobile=new JLabel("Мобильный телефон обратившегося (10 значащих цифр)");
        LSNILS=new JLabel("СНИЛС обратившегося (11 значащих цифр)");

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

        MainPanel = new JPanel();;    //////// new JPanel(gr_l);
        PsnilsPanel = new JPanel(new GridLayout());
        timeBasedUUID Uuid = new timeBasedUUID();

        MakeRequest = new JButton("Найти запись (Ctrl+F)");
        PButton = new JPanel();
        InitialColor = ProceedRegister.getBackground();
        this.Uuid = new timeBasedUUID();

        MainMenu= new AppMenu();

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
        Modell.SNILSoper=ExtendedPanel.TOperSnils.getText();
        Modell.RA=ExtendedPanel.TRA.getText();
        Modell.Pass=ClientPanelP.TPass.getText();
        Modell.FIO=ClientPanelP.TFIO.getText();
        Modell.SNILS=ClientPanelP.TSNILS.getText();
        Modell.Mobile=ClientPanelP.TMobile.getText();
        var fos = new FileOutputStream(SettsContainer.DumpModelFile);
        fos.write(Model.saveModel(Modell));
        fos.close();

    }

    public void restore_last(){
        ClientPanelP.TPass.setText(Modell.Pass);
        ClientPanelP.TFIO.setText(Modell.FIO);
        ExtendedPanel.TRA.setText(Modell.RA);
        ExtendedPanel.TOperSnils.setText(Modell.SNILSoper);
        ClientPanelP.TSNILS.setText(Modell.SNILS);
        ClientPanelP.TMobile.setText(Modell.Mobile);
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


    private void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        About = new About(this.SettsContainer);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    About.preperaGUI();
                    About.initListeners();
                    About.frame.setLocationRelativeTo(null);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.getContentPane().add(RootPanel);

        disableProceed();
        disableUpgrade();
        disableCreate();

        frame.setJMenuBar(MainMenu);

        RootPanel.add(MainPanel, BorderLayout.NORTH);

        RootPanel.add(PButton, BorderLayout.SOUTH);

        MainPanel.add(ExtendedPanel);
        MainPanel.add(ClientPanelP);

        ClientPanelP.add(ClientPanelP.PFIO);
        ClientPanelP.add(ClientPanelP.PPass);
    //    ClientPanelP.add(ClientPanelP.PSNILS);
        ClientPanelP.add(ClientPanelP.PMobile);

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

        MainMenu.AboutFrame=About.frame;
        MainMenu.ParentFrame=this.frame;

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

        MainMenu.NsItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(opensetts_shortcut), opensetts);
        MainMenu.NsItem.getActionMap().put(opensetts, openSetts);
        MainMenu.NsItem.addActionListener(openSetts);


        MainMenu.ExitItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(exititem_shortcut), exititem);
        MainMenu.ExitItem.getActionMap().put(exititem, exitItem);
        MainMenu.ExitItem.addActionListener(exitItem);


        MakeRequest.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(makerequest_shortcut), makerequest);
        MakeRequest.getActionMap().put(makerequest, makeRequest);
        MakeRequest.addActionListener(makeRequest);

        ProceedRegister.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(proceedregister_shortcut), proceedregister);
        ProceedRegister.getActionMap().put(proceedregister, proceedRegister);
        ProceedRegister.addActionListener(proceedRegister);


        MainMenu.NsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                ns.frame.setVisible(true);
            }
        });


    }

    boolean checkFIO(){
        var FIO = Extractor.getFIO(ClientPanelP.TFIO.getText());
        if (FIO.size()<3)
            return false;
        return true;
    };

    boolean checkMobile(){
        if (ClientPanelP.TMobile.getText().length()!=10)
            return false;
        return true;
    };

    boolean checkClientSNILS(){
        if (ClientPanelP.TSNILS.getText().length() ==0)
            return false;
        return true;
    };

    boolean checkPass(){
        if (ClientPanelP.TPass.getText().length() !=10)
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


        proceedRegister = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showMessageDialog(null, "Proceed register");
                try {
                    Process p = Runtime.getRuntime().exec(SettsContainer.runMainApp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                    var FIO = Extractor.getFIO(ClientPanelP.TFIO.getText());
                    var Pass = Extractor.getPass(ClientPanelP.TPass.getText());
                    if (!checkFIO()){
                        showMessageDialog(null, "заполните ФИО!");
                        return;
                    }
                    if (!checkMobile()){
                        showMessageDialog(null, "заполните мобильный телефон клиента => 10 значащих цифр, \nнапример 9604451213");
                        return;
                    }
                 //   if (!checkClientSNILS()){
                 //       showMessageDialog(null, "заполните СНИЛС клиента");
                 //       return;
                 //   }
                    if (!checkPass()){
                        showMessageDialog(null, "заполните паспорт клиента");
                        return;
                    }
                    msg.ID=uuid_;
                    msg.Ra=ExtendedPanel.TRA.getText();;//Tra.getText();        /////         ExtendedPanel.TRA.getText()
                    msg.OperatorSnils=ESIAFindMessageInitial.getSNILSfromplain(ExtendedPanel.TOperSnils.getText());//TOperSnils.getText());      ////   ExtendedPanel.TOperSnils.getText()
                    System.out.println("OPER SNILS=>"+ESIAFindMessageInitial.getSNILSfromplain(ExtendedPanel.TOperSnils.getText()));
                    msg.Surname=FIO.get(0);
                    msg.Name=FIO.get(1);
                    msg.MiddleName=FIO.get(2);
                //    msg.OperatorSnils=ESIAFindMessageInitial.getSNILSfromplain(TOperSnils.getText());
                    msg.Passseria=Pass.get(0);
                    msg.Passnumber=Pass.get(1);
                //    msg.SNILS=ESIAFindMessageInitial.getSNILSfromplain(ClientPanelP.TSNILS.getText());
                    msg.MobileNumber=ESIAFindMessageInitial.getMobilefromplain(ClientPanelP.TMobile.getText());
                    byte[] datatoWork = BinaryMessage.savedToBLOB(msg);

                    var SMEVMsg = new MessageSMEV(uuid_, "findesia", datatoWork, akt.rollbackAdressURL());

                    akt.send(BinaryMessage.savedToBLOB(SMEVMsg), ns.sets.address);
                    System.out.println("\n\n\n\nSENDING FINISHED!!!...");
                    lockinputs();

                    unlockinputs();///<====
                    enableRequest();
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
                    unlockinputs();
                    enableRequest();
               }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
                    unlockinputs();
                    enableRequest();

                }
            }
        };
    }






    private void prepareAktor() throws InterruptedException {
        akt = new FindAppAktor();
        akt.setAddress(SettsContainer.ESIAClient);
        akt.setCypher(cypher);
        akt.spawn();
        akt.on_success= new app.abstractions.OnSuccess() {
            @Override
            public void passed() {
                enableProceed();
            }
        };
        akt.on_failure=new OnFailure() {
            @Override
            public void failed(ResponceMessage resp) {
                showMessageDialog(null, "Учетная запись не найдена или не подтверждена");
            }
        };
        //  showMessageDialog(null, "AKtor spawned");
    }

    public static void main(String[] args ) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, InterruptedException, IOException {
        var fr = new StartFrame(new SettingsContainer());
        fr.initAboutFrame();
        fr.preperaGUI();
        fr.prepareAktor();
        fr.initNetworkSettinFrame();
        fr.initListeners();

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





    public class FindAppAktor extends AppAktor {
        @Override
        public void receive(byte[] message_) throws IOException {
            ESIAFindMessageResult resp = (ESIAFindMessageResult) BinaryMessage.restored(message_);
            if (tableRequest.get(resp.ID)==null)
                return;
            tableRequest.remove(resp.ID);
            enableRequest();
            unlockinputs();
            System.out.println("Received!!!! via console");
            byte[] message =  cypher.decrypt(message_);
            if (resp.BioStu!=null){
                if (resp.BioStu.toUpperCase().equals("Y")){
                    showMessageDialog(null, "Клиент уже зарегестрирован в ЕБС");
                  //////  return;
                }
            }
            System.out.println("\n\n\nRECEIVED\n\n"+resp.oid+"\n\n"+resp.trusted);

            if ((resp.oid!=null) && resp.trusted.equals("trusted")) {
                oi.OID=resp.oid;
                oi.RA=ExtendedPanel.TRA.getText();
                oi.OperSNILS=ESIAFindMessageInitial.getSNILSfromplain(ExtendedPanel.TOperSnils.getText());
                BinaryMessage.write(BinaryMessage.savedToBLOB(oi), SettsContainer.SaveOtherInfoToFile);
                on_success.passed();
            }
            if (resp.oid==null)
                on_failure.failed(null);



           //     enableCreate();
           // if ((resp.oid!=null) && (!resp.trusted.equals("trusted")))
           //     enableUpgrade();
        //    showMessageDialog(null, "Status => "+ resp.trusted+"\nOID=>"+resp.oid);
        //        else
        //          this.label_resultCheck.setText("проверка не пройдена");

        }
    }

}
