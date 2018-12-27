package app.GUIModules.Interface.Blocks;

import javax.swing.*;
import java.awt.*;


public class EBSOperatorPanel extends JPanel {
    public JTextField TOperSnils;
    public JTextField TRA;
    public JLabel LOperSnils;
    public JLabel LRA;
    public JLabel LPanel;
    public JPanel PSNILS;
    public JPanel PRA;


    public EBSOperatorPanel(){
        TOperSnils=  new JTextField("",3);
        TRA= new JTextField("",3);
        LOperSnils = new JLabel("СНИЛС Оператора");
        LRA = new JLabel("RA");
        LPanel = new JLabel("Данные поставщика");

        PSNILS=new JPanel(new GridLayout());
        PRA=new JPanel(new GridLayout());

        this.setLayout(new GridLayout(3, 1));
        initListeners();
        prepareGUI();
    }




    public void initActions(){
    }

    public void initListeners (){
        initActions();
    }

    public void prepareGUI(){
        PSNILS.add(LOperSnils);
        PSNILS.add(TOperSnils);

        PRA.add(LRA);
        PRA.add(TRA);

        this.add(LPanel);
        this.add(PSNILS);
        this.add(PRA);

    };

    public static void main(String[] args){
        EBSOperatorPanel p = new EBSOperatorPanel();
        JFrame frame = new JFrame("Test");
        frame.setSize(500, 500);
        frame.getContentPane().setLayout(new GridLayout());
        frame.getContentPane().add(p);
        frame.pack();
        frame.setVisible(true);

    }

}
