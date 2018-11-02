package app.GUIModules;

import app.abstractions.ModuleGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class About extends ModuleGUI {
    public JFrame frame;
    public JPanel about;
    public JTextArea info;
    public JButton ok;
    public JPanel main;
    public JPanel buttonPanel;
    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("О программе");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 350);
        var gr_layout = new GridLayout(2,1);
        gr_layout.setVgap(40);
        buttonPanel=new JPanel(new FlowLayout());
        main =  new JPanel(gr_layout);
        about = new JPanel();
        info = new JTextArea("\n\n      Программа для сбора биометрических данных       \n      Версия 1.2\n      Written Roman Pastushkov @ 2018 @ Vcabank\n      November 2018 Build\n\n");
        frame.getContentPane().add(main);
        about.add(info);
        ok = new JButton("OK");
        ok.setSize(150, 50);
        buttonPanel.add(ok);
        main.add(about);
        main.add(buttonPanel);

    }

    @Override
    public void initListeners() {
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        var ab = new About();
        ab.preperaGUI();
        ab.initListeners();
        ab.frame.setLocationRelativeTo(null);
        ab.frame.setVisible(true);
    }
}
