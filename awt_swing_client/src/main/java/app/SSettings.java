package app;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;

public class SSettings {
    public static boolean RIGHT_TO_LEFT = false;
    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("BorderLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.

        JPanel panelControlButtons = new JPanel(new BorderLayout());
        frame.getContentPane().add(panelControlButtons, BorderLayout.PAGE_END);

        JButton loadSets = new JButton("Load settings");
        JButton saveSets = new JButton("Save settings");
        panelControlButtons.add(loadSets, BorderLayout.WEST);
        panelControlButtons.add(saveSets, BorderLayout.EAST);

        JPanel SetsPane = new JPanel(new BorderLayout());
        frame.getContentPane().add(SetsPane, BorderLayout.PAGE_START);

        JPanel sampleRatePane = new JPanel(new GridLayout());
        SetsPane.add(sampleRatePane, BorderLayout.PAGE_END);

        JLabel sampleRateLabel = new JLabel("Частота дискретизации");
        JTextField sampleRateInput = new JTextField();

        sampleRatePane.add(sampleRateLabel);
        sampleRatePane.add(sampleRateInput);


    /*    JPanel sampleRatePane = new JPanel(new GridLayout());
        SetsPane.add(sampleRatePane, BorderLayout.PAGE_END);

        JLabel sampleRateLabel = new JLabel("Частота дискретизации");
        JTextField sampleRateInput = new JTextField();

        sampleRatePane.add(sampleRateLabel);
        sampleRatePane.add(sampleRateInput);

     */









        //Use the content pane's default BorderLayout. No need for
        //setLayout(new BorderLayout());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
