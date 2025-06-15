import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            zoo.TelaZoologico tela = new zoo.TelaZoologico();
            tela.setVisible(true);
        });
    }
}
