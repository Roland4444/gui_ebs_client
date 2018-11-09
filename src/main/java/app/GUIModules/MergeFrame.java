package app.GUIModules;

import app.abstractions.ModuleGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MergeFrame extends ModuleGUI {
    public final int width=5;
    public JPanel Section09, Panel;
    public JPanel Section90;
    public JPanel Section090;
    public JMenuBar Menubar;
    public JMenu File, Edit;

    JPanel ContainerPanel1;
    JPanel leftPanel1;
    JPanel rigthPanel1 ;

    JPanel ContainerPanel2;
    JPanel leftPanel2;
    JPanel rigthPanel2;

    JPanel ContainerPanel3;
    JPanel leftPanel3;
    JPanel rigthPanel3;

    JPanel ContentPanel1 ;
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
    private JLabel lBegin1;
    private JLabel lBegin2;
    private JLabel lBegin3;
    private JLabel lEnd1;
    private JLabel lEnd2;
    private JLabel lEnd3;

    public MergeFrame(){
        frame = new JFrame("Модуль подготовки голосового слепка");
        Menubar = new JMenuBar();
        File = new JMenu("Файл");
        Edit = new JMenu("Сервис");
        Section90=new JPanel();
        Section090 =new JPanel();
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
    }


    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        frame.setJMenuBar(Menubar);
        Menubar.add(File);
        Menubar.add(Edit);

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

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MergeFrame MF = new MergeFrame();
        MF.preperaGUI();
        MF.initListeners();
        MF.frame.setVisible(true);

    }
}
