package app.GUIModules.Video;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPaint extends JFrame {
    final PaintPanel paintPan = new PaintPanel();
    public MainPaint() {
        setTitle("test paint");
        setSize(800, 400);
        setLayout(new BorderLayout());


        JButton testButon = new JButton("Display shape");
        add(paintPan, BorderLayout.CENTER);
        add(testButon, BorderLayout.PAGE_END);

        testButon.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                paintPan.updateGraphics(50, 50);
                repaint();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException {
         var g=new MainPaint();
        for (int i=0; i<100;i++){
            g.paintPan.updateGraphics(50+i*6, 50+i);
            g.repaint();
            Thread.sleep(15);
        }

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