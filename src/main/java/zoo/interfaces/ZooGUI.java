package zoo.interfaces;

import javax.swing.*;

public class ZooGUI extends JFrame {
    public ZooGUI() {
        setTitle("Zoológico");
        setSize(400, 300);
        add(new JButton("Cadastrar Animal"));
    }
} 