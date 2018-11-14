package app.GUIModules.Video;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPaint extends JFrame {
    private Dimension size = WebcamResolution.HD.getSize();
    final PaintPanel paintPan = new PaintPanel();
    public MainPaint() {
        setTitle("test paint");
        setSize(1280, 1280);
        setLayout(new BorderLayout());
        var	webcam = Webcam.getDefault();
	webcam.setViewSize(size);
        var panel = new WebcamPanel(webcam, false);
	panel.setFPSDisplayed(true);
        add(panel);
        if (webcam.isOpen()) 
            webcam.close();
	int i = 0;
        webcam.open();
	panel.start();
        setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException {
         var g=new MainPaint();
      

    }
}

class PaintPanel extends JPanel {

    private int x, y;
    private Color color = null;

    public PaintPanel() {
        setBackground(Color.ORANGE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D drawImage = (Graphics2D) g;
        if (color != null) {
            drawImage.setColor(color);
            drawImage.drawRect(100, 150, x, y);
        }
    }

    public void updateGraphics(int length, int width) {
        color = Color.RED;
        x = length;
        y = width;
        repaint();
    }
}