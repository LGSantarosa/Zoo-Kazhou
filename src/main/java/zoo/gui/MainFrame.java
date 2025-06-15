package zoo.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        initComponents();
        setupFrame();
    }
    
    private void initComponents() {
        // Configurações básicas do frame
        setTitle("Zoo Kazhou - Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Menu principal
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Animais
        JMenu menuAnimais = new JMenu("Animais");
        JMenuItem itemListarAnimais = new JMenuItem("Listar Animais");
        JMenuItem itemAdicionarAnimal = new JMenuItem("Adicionar Animal");
        
        itemAdicionarAnimal.addActionListener(e -> {
            new AnimalForm().setVisible(true);
        });
        
        menuAnimais.add(itemListarAnimais);
        menuAnimais.add(itemAdicionarAnimal);
        
        // Menu Tarefas
        JMenu menuTarefas = new JMenu("Tarefas");
        JMenuItem itemListarTarefas = new JMenuItem("Listar Tarefas");
        JMenuItem itemNovaTarefa = new JMenuItem("Nova Tarefa");
        menuTarefas.add(itemListarTarefas);
        menuTarefas.add(itemNovaTarefa);
        
        // Menu Funcionários
        JMenu menuFuncionarios = new JMenu("Funcionários");
        JMenuItem itemListarFuncionarios = new JMenuItem("Listar Funcionários");
        JMenuItem itemNovoFuncionario = new JMenuItem("Novo Funcionário");
        menuFuncionarios.add(itemListarFuncionarios);
        menuFuncionarios.add(itemNovoFuncionario);
        
        // Adiciona menus à barra
        menuBar.add(menuAnimais);
        menuBar.add(menuTarefas);
        menuBar.add(menuFuncionarios);
        
        setJMenuBar(menuBar);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Adiciona um painel de boas-vindas
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gerenciamento do Zoo Kazhou");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        
        mainPanel.add(welcomePanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private void setupFrame() {
        // Centraliza a janela na tela
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        // Configura o look and feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicia a aplicação
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
} 