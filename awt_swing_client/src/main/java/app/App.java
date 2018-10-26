package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame{
    private int voron = 0;
    private JLabel countLabel;
    private JButton addCrow;
    private JButton removeCrow;
    public App(){
        super("My First Window"); //Заголовок окна
        countLabel = new JLabel("Crows:" + voron);
        addCrow = new JButton("Add Crow");
        removeCrow = new JButton("Remove Crow");


        JPanel buttonsPanel = new JPanel(new FlowLayout());

        add(countLabel, BorderLayout.NORTH); //О размещении компонент поговорим позже

        buttonsPanel.add(addCrow);
        buttonsPanel.add(removeCrow);

        add(buttonsPanel, BorderLayout.SOUTH);
        initListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initListeners() {
        addCrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voron = voron + 1;   /* Добавляем одну ворону  */
                updateCrowCounter(); /* Сообщаем аппликации, что количество ворон изменилось  */
            }
        });
        removeCrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (voron > 0) {
                    voron = voron - 1;
                    updateCrowCounter(); /* Сообщаем аппликации, что количество ворон изменилось  */
                }
            }
        });
    }
    private void updateCrowCounter() {
        countLabel.setText("Crows:" + voron);
    }
    public static void main(String[] args) { //эта функция может быть и в другом классе
        App app = new App();
        app.setVisible(true);
        app.pack();
    }
}


