package app.GUIModules.Interface.GetBio.Audio;


import Message.toSMEV.EBS.Essens.SoundBundle;
import app.abstractions.ModuleGUI;
import app.abstractions.SettingsContainer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

public class MergeAudioFrames extends ModuleGUI {
    public Map<Character, Boolean> dictionary = new HashMap<>();
    public final String create_bundle_shortcut = "control B";
    public final String launch_helper_shortcut = "alt H";
    public final String lauchhelper = "launchhelper";
    AbstractAction bundleAction;
    AbstractAction launchHelper;
    public final String bundleaction = "bundle";
    public final int width=5;
    public JPanel Panel;

    public JMenuBar Menubar;
    public JMenu File_, Edit;

    JPanel ContainerPanel1;
    JPanel leftPanel1;
    JPanel rigthPanel1 ;

    JPanel ContainerPanel2;
    JPanel leftPanel2;
    JPanel rigthPanel2;

    JPanel ContainerPanel3;
    JPanel leftPanel3;
    JPanel rigthPanel3;

    JPanel ContentPanel1;
    JPanel ContentPanel2;
    JPanel ContentPanel3 ;

    public JLabel l09;
    public JTextField t09Begin;
    public JTextField t09End;
    public JPanel BeginPanel09;
    public JPanel EndPanel09;

    public JLabel l90;
    public JTextField t90Begin;
    public JTextField t90End;

    public JPanel BeginPanel90;
    public JPanel EndPanel90;

    Border etched = BorderFactory.createEtchedBorder();
    public JLabel l090;
    public JTextField t090Begin;
    public JTextField t090End;

    public JPanel BeginPanel090;
    public JPanel EndPanel090;
    public JMenuItem ExitItem;
    public JMenuItem LaunchHelper;
    private JLabel lBegin1;
    private JLabel lBegin2;
    private JLabel lBegin3;
    private JLabel lEnd1;
    private JLabel lEnd2;
    private JLabel lEnd3;

    JMenuItem CreateSoundBundle;

    public MergeAudioFrames(SettingsContainer sc){
        this.SettsContainer=sc;
        initDict();
        frame = new JFrame("Модуль подготовки голосового слепка");
        Menubar = new JMenuBar();
        File_ = new JMenu("Файл");
        Edit = new JMenu("Правка");
        Panel = new JPanel(new GridLayout(1,3));
        ContentPanel1 = new JPanel(new BorderLayout());
        ContentPanel2 = new JPanel(new BorderLayout());
        ContentPanel3 = new JPanel(new BorderLayout());
        GridLayout gridlayer = new GridLayout(1,2);
        GridLayout gridPanel = new GridLayout(2,1);
        ContainerPanel1 = new JPanel(gridlayer);
        leftPanel1 = new JPanel(gridPanel);
        rigthPanel1 = new JPanel(gridPanel);
        ContainerPanel2 = new JPanel(gridlayer);
        leftPanel2 = new JPanel(gridPanel);
        rigthPanel2 = new JPanel(gridPanel);
        ContainerPanel3 = new JPanel(gridlayer);
        leftPanel3 = new JPanel(gridPanel);
        rigthPanel3 = new JPanel(gridPanel);
        l09 =new JLabel("Секция 0-9");
        t09Begin =new JTextField("",width);
        t09End =new JTextField("",width);
        lBegin1 =new JLabel("Начало ");
        lBegin2 =new JLabel("Начало ");
        lBegin3 =new JLabel("Начало ");
        lEnd1 =new JLabel("Конец ");
        lEnd2 =new JLabel("Конец ");
        lEnd3 =new JLabel("Конец ");
        BeginPanel09 = new JPanel();
        BeginPanel90 = new JPanel();
        BeginPanel090 = new JPanel();
        EndPanel09 = new JPanel();
        EndPanel90 = new JPanel();
        EndPanel090 = new JPanel();
        l90 =new JLabel("Секция 9-0");
        t90Begin =new JTextField("",width);
        t90End =new JTextField("",width);
        l090 =new JLabel("Случайная Секция");
        t090Begin =new JTextField("",width);
        t090End =new JTextField("",width);
        CreateSoundBundle = new JMenuItem("Сформировать звуковой слепок (Ctrl+B)");
        LaunchHelper = new JMenuItem("Запустить помошника (Alt+H)");
        ExitItem = new JMenuItem("Выйти");
    }

    public boolean isnumber(String input){
        for (int i=0; i<input.length();i++){
            if (dictionary.get(input.charAt(i)) == null)
                return false;
        }
        return true;
    }

    public boolean isbadField(String input){
        if (input.length()==0) return true;
        return !isnumber(input);
    }


    public boolean increasingCheck(){
        float f1 = Float.parseFloat(t09Begin.getText());
        float f2=Float.parseFloat(t90End.getText());
        float f3=Float.parseFloat(t90Begin.getText());
        float f4=Float.parseFloat(t90End.getText());
        float f5 =Float.parseFloat(t090Begin.getText());
        float f6 =Float.parseFloat(t090End.getText());
        if ((f1<f2) && (f2<f3) && (f3<f4) && (f4<f5) && (f5<f6))
            return true;
        return true;//false;
    }



    public void prepareActions(){
        launchHelper = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Runtime r = Runtime.getRuntime();
                Process p = null;
                try {
                    p = r.exec("audacity "+ SettsContainer.resultmerged);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        bundleAction = new AbstractAction(bundleaction){
            @Override
            public void actionPerformed(ActionEvent e1) {
                if ((isbadField(t09Begin.getText())) || (isbadField(t09End.getText())) ||
                (isbadField(t90Begin.getText())) || (isbadField(t90End.getText())) ||
                (isbadField(t090Begin.getText())) || isbadField(t090End.getText())){
                    showMessageDialog(null, "Проверьте правильность и полноту введенных данных");
                    return;
                }
                if (!increasingCheck()){
                    showMessageDialog(null, "Данные должны быть в порядке возрастания");
                    return;
                }
                SoundBundle sb = new SoundBundle();
                sb.begin09  =Double.parseDouble(t09Begin.getText());
                sb.end09    =Double.parseDouble(t09End.getText());
                sb.begin90  =Double.parseDouble(t90Begin.getText());
                sb.end90    =Double.parseDouble(t90End.getText());
                sb.begin090 =Double.parseDouble(t090Begin.getText());
                sb.end090   =Double.parseDouble(t090End.getText());
                try {
                    sb.fileContent = Files.readAllBytes(new File(SettsContainer.resultmerged).toPath());
                    sb.filename=SettsContainer.mergedwav;
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    FileOutputStream  fos = new FileOutputStream(SettsContainer.SaveSoundDataWithtagsToFile);
                    fos.write(SoundBundle.saveToByte(sb));
                    fos.close();
                    showMessageDialog(null, "Успешно сохранен!");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        frame.setJMenuBar(Menubar);
        Menubar.add(File_);
        Menubar.add(Edit);
        File_.add(ExitItem);

        Edit.add(CreateSoundBundle);
        Edit.add(LaunchHelper);

        frame.getContentPane().add(Panel, BorderLayout.PAGE_START);

        prepare1Panel();
        prepare2Panel();
        prepare3Panel();

        Panel.add(ContentPanel1);
        Panel.add(ContentPanel2);
        Panel.add(ContentPanel3);

    }

    public void prepare1Panel(){
        leftPanel1.add(lBegin1);
        leftPanel1.add(t09Begin);
        rigthPanel1.add(lEnd1);
        rigthPanel1.add(t09End);
        ContainerPanel1.add(leftPanel1);
        ContainerPanel1.add(rigthPanel1);
        ContentPanel1.setBorder(etched);
        ContentPanel1.add(l09, BorderLayout.PAGE_START);
        ContentPanel1.add(ContainerPanel1, BorderLayout.CENTER);
    }

    public void prepare2Panel(){
        leftPanel2.add(lBegin2);
        leftPanel2.add(t90Begin);
        rigthPanel2.add(lEnd2);
        rigthPanel2.add(t90End);
        ContainerPanel2.add(leftPanel2);
        ContainerPanel2.add(rigthPanel2);
        ContentPanel2.setBorder(etched);
        ContentPanel2.add(l90, BorderLayout.PAGE_START);
        ContentPanel2.add(ContainerPanel2, BorderLayout.CENTER);
    }

    public void prepare3Panel(){
        leftPanel3.add(lBegin3);
        leftPanel3.add(t090Begin);
        rigthPanel3.add(lEnd3);
        rigthPanel3.add(t090End);
        ContainerPanel3.add(leftPanel3);
        ContainerPanel3.add(rigthPanel3);
        ContentPanel3.setBorder(etched);
        ContentPanel3.add(l090, BorderLayout.PAGE_START);
        ContentPanel3.add(ContainerPanel3, BorderLayout.CENTER);
    }



    @Override
    public void initListeners() {
        prepareActions();
        CreateSoundBundle.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(create_bundle_shortcut), bundleaction);
        CreateSoundBundle.getActionMap().put(bundleaction, bundleAction);
        CreateSoundBundle.addActionListener(bundleAction);

        ExitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        LaunchHelper.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(launch_helper_shortcut), lauchhelper);
        LaunchHelper.getActionMap().put(lauchhelper, launchHelper);
        LaunchHelper.addActionListener(launchHelper);



    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MergeAudioFrames MF = new MergeAudioFrames(new SettingsContainer());
        MF.preperaGUI();
        MF.initListeners();
        MF.frame.setVisible(true);

    }

    public void initDict(){
        this.dictionary.put('.', true);
        this.dictionary.put('0', true);
        this.dictionary.put('1', true);
        this.dictionary.put('2', true);
        this.dictionary.put('3', true);
        this.dictionary.put('4', true);
        this.dictionary.put('5', true);
        this.dictionary.put('6', true);
        this.dictionary.put('7', true);
        this.dictionary.put('8', true);
        this.dictionary.put('9', true);


    }

    @Override
    public void initActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
