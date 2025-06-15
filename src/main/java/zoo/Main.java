package zoo;

import zoo.gui.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Zoo Kazhou - Sistema de Gerenciamento");
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}