package app.GUIModules.Video;
import app.Essens.CypherImpl;
import app.Essens.OnClosed;
import app.Essens.Video_Settings;
import app.GUIModules.About;
import app.GUIModules.Audio.MergeFrame;
import app.GUIModules.NetworkSettings;
import app.abstractions.ModuleGUI;
import app.utils.Cypher;
import app.utils.timeBasedUUID;
import essens.InputMessage;
import essens.ResponceMessage;
import essens.TablesEBSCheck;
import impl.JAktor;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;


public class PhotoMake extends ModuleGUI {
    grab gr;
    Scalar scalar;
    AbstractAction checkAction;
    AbstractAction drawinCanvas;
    AbstractAction makeShot;
    AbstractAction videoSetts;
    AbstractAction openVideoFrame;
    AbstractAction closeHelp;
    private Cypher cypher;

    public final String chackaction = "check action";
    public final String checkaction_shortcut = "control C";
    public final String makeshot = "makeshot";
    public final String makeshot_shortcut = "control S";
    public final String openVideoFrame_shortcut = "control H";
    public final String openvideoframe = "openvideoframe";
    public final String closeHelp_shortcut="control Z";
    public final String closehelp="closehelp";
    public Video_Settings VideoSetts;
    public final String IMG_PATH = "tested.png";
    public final String stopped = "stopped.png";
    camera cam;

    public Map<String, Integer> tableRequest = new HashMap<>();
    JMenuBar MenuBar;

    JMenu FileMenu;
    JMenu EditMenu;
    JMenu helpMenu ;
    JMenu SettsMenu;
    JMenu WorkMenu;

    JMenuItem SaveItem;
    JMenuItem CheckItem;
    JMenuItem ExitItem;

    JMenuItem NsItem;
    JMenuItem VsItem;
    JMenuItem AboutItem;

    BufferedImage img;

    JMenuItem MergerSlots;

    JMenuItem CreateBundle;

    JMenuItem CreateFinal;
    JLabel labelImg;
    public VSettings VideoSettings;
    public app.GUIModules.NetworkSettings NetworkSettings;
    public app.GUIModules.About About;
    public MergeFrame MF;

    public JPanel Panel;
    public JPanel PhotoPanel;
    public JPanel ButtonPanel;

    public JButton Check;
    public JButton Start;
    public JButton OpenHelp;
    public JButton CloseHelp;

    public JLabel StartLabel;

    public boolean checked = false;
    public interop exchange;
    AppAktor akt;
    public CamPanel camPanel;
    public JLabel LabelCam;
    timeBasedUUID Uuid = new timeBasedUUID();
    public PhotoMake() throws IOException {
        cypher = new CypherImpl();
        frame = new JFrame("EBS GUI Client PhotoMake 1.5");
        MenuBar = new JMenuBar();
        FileMenu = new JMenu("Файл");
        ExitItem = new JMenuItem("Выйти");
        EditMenu = new JMenu("Правка");
        NsItem = new JMenuItem("Настройки сервиса");
        VsItem = new JMenuItem("Настройки видео");
        SettsMenu = new JMenu("Настройкм");
        WorkMenu = new JMenu("Сервис");
        LabelCam = new JLabel();

        CheckItem = new JMenuItem("Проверить фото");
        SaveItem = new JMenu("Сохранить фото");
        MergerSlots = new JMenuItem("Упаковать аудио и видео");
        helpMenu = new JMenu("Помощь");
        AboutItem = new JMenuItem("О программе");
        Panel = new JPanel(new BorderLayout());
        Check = new JButton("Проверить фото  (Ctrl+C)");
        OpenHelp = new JButton("Запустить камеру (Ctrl+H)");
        CloseHelp = new JButton("Закрыть камеру(Ctrl+Z)");
        var blt = new BorderLayout();

        PhotoPanel = new JPanel(blt);
        ButtonPanel = new JPanel(new BorderLayout());

        Start = new JButton("Сделать фото");

        StartLabel = new JLabel("Сделать фото");

        CreateBundle=new JMenuItem("Создать фотосборку");
        CreateFinal = new JMenuItem("Создать финальную сборку(аудио + фото)");

        camPanel = new CamPanel();

        scalar = new Scalar(25, 255, 17);

        if (new File(IMG_PATH).exists()){
            img = ImageIO.read(new File(IMG_PATH));
            ImageIcon icon =  new ImageIcon(
                    img.getScaledInstance(
                            img.getWidth(null)/4,
                            img.getHeight(null)/4,
                            Image.SCALE_SMOOTH )
            );

            labelImg = new JLabel(icon);
            return;
        }

        img = null;
        labelImg=new JLabel();


    }

    public void setCypher(Cypher cypher){
        this.cypher=cypher;
    }

    public void initinterop(){
        exchange=new interop();
        exchange.checked=false;
        exchange.errorcode=-2;
        exchange.resultcheck=-2;
        exchange.resultloadso=-2;
    }

    public void disableSave(){
        SaveItem.setEnabled(false);
    }

    public void enableSave(){
        System.out.println("\n\n\nControl Passsed!!!\n\n\n");
        SaveItem.setEnabled(true);
    }

    public void enableCheck(){
        Check.setEnabled(true);
        CheckItem.setEnabled(true);
    }

    public void disableCheck(){
        System.out.println("Disabling check");
        Check.setEnabled(false);
        CheckItem.setEnabled(false);
    }


    public void disableBundle(){
        CreateBundle.setEnabled(false);
    }

    public void enableBundle(){
        CreateBundle.setEnabled(true);
    }

    public void prepereThreads(){
        cam =new camera();
        cam.mounted=LabelCam;
    }
    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        FileMenu.add(ExitItem);
        SettsMenu.add(NsItem);
        SettsMenu.add(VsItem);
        EditMenu.add(SettsMenu);

        WorkMenu.add(CheckItem);
        Panel.add(Check, BorderLayout.WEST);
        Panel.add(OpenHelp, BorderLayout.EAST);
        Panel.add(CloseHelp, BorderLayout.CENTER);
        WorkMenu.add(SaveItem);

        WorkMenu.add(MergerSlots);
        WorkMenu.add(CreateBundle);

        helpMenu.add(AboutItem);

        MenuBar.add(FileMenu);
        MenuBar.add(EditMenu);
        MenuBar.add(WorkMenu);
        MenuBar.add(helpMenu);

        frame.setJMenuBar(MenuBar);

        frame.getContentPane().add(Panel, BorderLayout.PAGE_END);

        Panel.add(Check, BorderLayout.WEST);

       // ButtonPanel.add(Start);
        ButtonPanel.add(Start);

        PhotoPanel.add(LabelCam, BorderLayout.WEST);

        PhotoPanel.add(labelImg, BorderLayout.EAST);
        frame.getContentPane().add(PhotoPanel, BorderLayout.CENTER);
        Panel.add(ButtonPanel, BorderLayout.SOUTH);

        disableBundle();
        disableSave();
        disableCheck();
    }

    private void initAboutFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        About = new About();
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

    private void initNetworkSettinFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        NetworkSettings =new NetworkSettings("NetworkSettingsVid.bin");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    NetworkSettings.preperaGUI();
                    NetworkSettings.initListeners();
                    NetworkSettings.tryReadData();
                    NetworkSettings.frame.setLocationRelativeTo(null);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("DEfault file setting not found");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void initVideoSettingFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        VideoSettings = new VSettings();
        VideoSettings.onclosed = new OnClosed() {
            @Override
            public void onClosed() throws IOException, InterruptedException {
                if (cam.cameraWorks)
                    cam.stopIt();
                startCam();
            }
        };
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    VideoSettings.preperaGUI();
                    VideoSettings.initListeners();
                    VideoSettings.loadSets(VSettings.defaultFileName_static);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("DEfault file setting not found");
                }
            }
        });
        VideoSetts = Video_Settings.restoreBytesToSetiings(Files.readAllBytes(new File(VSettings.defaultFileName_static).toPath()));
    }

    private void initCreateBundle(){
        MF = new MergeFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    MF.preperaGUI();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                MF.initListeners();

            }
        });
    }

    public void initActions(){
        closeHelp = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cam.cameraWorks) {
                    System.out.println("\n\nStopping cam\n\n");
                    try {
                        cam.stopIt();
                    } catch (IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        };

        drawinCanvas = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var grab = new grab();
                try {
                    grab.getFrame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        makeShot = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cam.cameraWorks) {
                    try {
                        cam.stopIt();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                gr = new grab(VideoSetts.width, VideoSetts.heigth);
                byte[] arrImg=null;
                try {
                   arrImg= gr.getFrame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    img = ImageIO.read(new ByteArrayInputStream(arrImg));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("img=>"+arrImg);
                ImageIcon icon =  new ImageIcon(img.getScaledInstance(img.getWidth(null)/2,img.getHeight(null)/2, Image.SCALE_SMOOTH ));

                labelImg.setIcon(icon);
                labelImg.updateUI();
                startCam();
                enableCheck();
               // gr.
            }
        };

        videoSetts = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VideoSettings.frame.setVisible(true);
            }
        };

        openVideoFrame = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startCam();
            }
        };

        checkAction = new AbstractAction("Check"){
            @Override
            public void actionPerformed(ActionEvent e1) {
                byte[] fileContent = null;
                var checkfile = new File(IMG_PATH);
                try {
                    fileContent = Files.readAllBytes(checkfile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var uuid_ = Uuid.generate();
                tableRequest.put(uuid_,-3);
                try {
                    InputMessage inp = new  InputMessage(checkfile.getName(), fileContent,  akt.tebs.photo, akt.getURL_thisAktor(), uuid_);
                    System.out.println("\n\n\n\nSTARTING SENDING...");
                    System.out.println("AKTOR ADRESS="+akt.getURL_thisAktor());
                    System.out.println("SENDING =>> "+ NetworkSettings.sets.address);
                    akt.send(InputMessage.saveMessageToBytes(inp), NetworkSettings.sets.address);
                    System.out.println("\n\n\n\nSENDING FINISHED!!!...");
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");

                }
                catch (CompletionException e){
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ");
                }
            }
        };
    }


    @Override
    public void initListeners() {
        initActions();

        Start.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(makeshot_shortcut), makeshot);
        Start.getActionMap().put(makeshot, makeShot);
        Start.addActionListener(makeShot);

        VsItem.addActionListener(videoSetts);

        NsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                NetworkSettings.frame.setVisible(true);
            }
        });

        OpenHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(openVideoFrame_shortcut), openvideoframe);
        OpenHelp.getActionMap().put(openvideoframe, openVideoFrame);
        OpenHelp.addActionListener(openVideoFrame);

        CloseHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(closeHelp_shortcut), closehelp);
        CloseHelp.getActionMap().put(closehelp, closeHelp);
        CloseHelp.addActionListener(closeHelp);

        Check.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(checkaction_shortcut), chackaction);
        Check.getActionMap().put(chackaction, checkAction);
        Check.addActionListener(checkAction);


    }
    public void setAktor(AppAktor jktr){
        this.akt=jktr;
    }


    private void prepareAktor() throws InterruptedException {
        akt = new AppAktor();
        akt.checkedViaForm=exchange;
        akt.setAddress("http://127.0.0.1:14444/");
        akt.setCypher(cypher);
        akt.spawn();
        akt.on_success=new OnSuccess() {
            @Override
            public void passed() {
                enableSave();
                disableCheck();
            }
        };
    }

    public static void  main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        PhotoMake sr = new PhotoMake();
        sr.preperaGUI();
        sr.initNetworkSettinFrame();
        sr.initVideoSettingFrame();
        sr.initListeners();
        sr.initinterop();
        sr.initAboutFrame();
        sr.initCreateBundle();
        sr.prepareAktor();
        sr.prepereThreads();
        sr.frame.setVisible(true);
    }

    interface OnSuccess{
        public void passed();
    }

    public class AppAktor extends JAktor {
        public interop checkedViaForm;
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
            }
        }
    }

    public void startCam(){
        cam =new camera();
        cam.mounted=LabelCam;
        Video_Settings vs = null;
        try {
            vs = Video_Settings.restoreBytesToSetiings(Files.readAllBytes(new File(VSettings.defaultFileName_static).toPath()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        cam.vs=vs;
        cam.start();

    }

    public class interop{
        public boolean checked;
        public int resultloadso;
        public int resultcheck;
        public int errorcode;
    }

    class camera extends Thread {
        public JLabel mounted;
        public Video_Settings vs;
        public Image im;
        VideoCapture vc;
        public boolean cameraWorks=false;

        public void stopIt() throws IOException, InterruptedException {
            this.interrupt();
            vc.release();

            cameraWorks=false;
            ImageIcon icon0=null;
            var img0 = ImageIO.read(new File(stopped));
                icon0 =  new ImageIcon(
                        img0.getScaledInstance(
                                img0.getWidth(null),
                                img0.getHeight(null),
                                Image.SCALE_SMOOTH ));
            Thread.sleep(1000);
            System.out.println("Setting up JLabel");
            mounted.setIcon(icon0);

            mounted.updateUI();
        }

        public void run() {
            cameraWorks=true;
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            vc = new VideoCapture(0);
            vc.set(3, vs.width); //1280);
            vc.set(4, vs.heigth);//720);
            var mat = new Mat();
            while (vc.isOpened()) {
             //   while (true) {
                    vc.read(mat);
                    if (VideoSetts.CheckFaces)
                        detectFace(mat);
                    var mem = new MatOfByte();
                    Highgui.imencode(".png", mat, mem);
                    try {
                        im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    var icon2 = new ImageIcon(im.getScaledInstance(
                            im.getWidth(null) / 2,
                            im.getHeight(null) / 2,
                            Image.SCALE_SMOOTH));

                    mounted.setIcon(icon2);
                    mounted.updateUI();
            //    }
            }
        }

        public void detectFace(Mat img){
            MatOfRect faceDetections = new MatOfRect();
            CascadeClassifier faceDetector = new CascadeClassifier("cascade.xml");
            faceDetector.detectMultiScale(img, faceDetections);

            System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
            for (Rect rect : faceDetections.toArray()) {
                Core.rectangle(img, new org.opencv.core.Point(rect.x, rect.y), new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                        scalar, 5);
            }
        }
    }

}
