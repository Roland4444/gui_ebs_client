package app.GUIModules;

import app.abstractions.ModuleGUI;

import javax.swing.*;
import java.awt.*;

public class MergeFrame extends ModuleGUI {
    public JPanel Section09, Panel;
    public JPanel Section90;
    public JPanel Section090;
    public JMenuBar Menubar;
    public JMenu File, Edit;


    public JLabel Section09Label;
    public JTextField Section09BeginSec;
    public JTextField Section09BeginMSec;
    public JTextField Section09EndSec;
    public JTextField Section09EndMSec;
    public JLabel Section09BeginSecLabel;
    public JLabel Section09BeginMSecLabel;
    public JLabel Section09EndSecLabel;
    public JLabel Section09EndMSecLabel;

    public JLabel Section90Label;
    public JTextField Section90BeginSec;
    public JTextField Section90BeginMSec;
    public JTextField Section90EndSec;
    public JTextField Section90EndMSec;
    public JLabel Section90BeginSecLabel;
    public JLabel Section90BeginMSecLabel;
    public JLabel Section90EndSecLabel;
    public JLabel Section90EndMSecLabel;


    public JLabel Section090Label;
    public JTextField Section090BeginSec;
    public JTextField Section090BeginMSec;
    public JTextField Section090EndSec;
    public JTextField Section090EndMSec;
    public JLabel Section090BeginSecLabel;
    public JLabel Section090BeginMSecLabel;
    public JLabel Section090EndSecLabel;
    public JLabel Section090EndMSecLabel;
    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        Frame = new JFrame("Модуль подготовки голосового слепка");
        Frame.setSize(500, 800);
        Menubar = new JMenuBar();
        File = new JMenu("Файл");
        Edit = new JMenu("Сервис");
        Frame.setJMenuBar(Menubar);
        Menubar.add(File);
        Menubar.add(Edit);
        //var Group1 = new GroupLayout();
        Section09=new JPanel(new BorderLayout());
        Section90=new JPanel(new BorderLayout());
        Section090 =new JPanel(new BorderLayout());
        Panel = new JPanel(new GridLayout(3,1));
        Frame.getContentPane().add(Panel, BorderLayout.PAGE_START);
        Panel.add(Section09);
        Panel.add(Section90);
        Panel.add(Section090);

        Section09Label=new JLabel("Последовательность 0-9");
        Section09BeginSec=new JTextField("",3);
        Section09BeginMSec=new JTextField("",3);
        Section09EndSec=new JTextField("",3);
        Section09EndMSec=new JTextField("",3);
        Section09BeginSecLabel=new JLabel("сек");
        Section09BeginMSecLabel=new JLabel("мсек");
        Section09EndSecLabel=new JLabel("сек");
        Section09EndMSecLabel=new JLabel("мсек");

        Section09.add(Section09Label, BorderLayout.PAGE_START);

        Section90Label=new JLabel("Последовательность 9-0");
        Section90BeginSec=new JTextField("",3);
        Section90BeginMSec=new JTextField("",3);
        Section90EndSec=new JTextField("",3);
        Section90EndMSec=new JTextField("",3);
        Section90BeginSecLabel=new JLabel("сек");
        Section90BeginMSecLabel=new JLabel("мсек");
        Section90EndSecLabel=new JLabel("сек");
        Section90EndMSecLabel=new JLabel("мсек");

        Section90.add(Section90Label, BorderLayout.PAGE_START);

        Section090Label=new JLabel("Последовательность случайная");
        Section090BeginSec=new JTextField("",3);
        Section090BeginMSec=new JTextField("",3);
        Section090EndSec=new JTextField("",3);
        Section090EndMSec=new JTextField("",3);
        Section090BeginSecLabel=new JLabel("сек");
        Section090BeginMSecLabel=new JLabel("мсек");
        Section090EndSecLabel=new JLabel("сек");
        Section090EndMSecLabel=new JLabel("мсек");

        Section090.add(Section090Label, BorderLayout.PAGE_START);


    }

    @Override
    public void initListeners() {

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MergeFrame MF = new MergeFrame();
        MF.preperaGUI();
        MF.initListeners();
        MF.Frame.setVisible(true);

    }
}
