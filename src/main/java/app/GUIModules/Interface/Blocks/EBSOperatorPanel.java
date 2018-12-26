package app.GUIModules.Interface.Blocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class EBSOperatorPanel extends JPanel {
    public AbstractAction clickButton;
    public String click_button="click button";
    public String click_button_shortcut="control O";
    public JTextField Jtxt;
    public JButton ExampleButton;
    public EBSOperatorPanel(){
        this.setLayout(new GridLayout());
        ExampleButton=new JButton("Example");
        Jtxt = new JTextField();
        this.add(Jtxt);
        this.add(ExampleButton);
        initListeners();
    }

    public void initActions(){
        clickButton = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showMessageDialog(null, "click complete!");
            }
        };
    }

    public void initListeners (){
        initActions();

        ExampleButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(click_button_shortcut), click_button);
        ExampleButton.getActionMap().put(click_button, clickButton);
        ExampleButton.addActionListener(clickButton);
    }

}
