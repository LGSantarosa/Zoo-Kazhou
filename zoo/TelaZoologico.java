package zoo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class TelaZoologico extends JFrame {
    private StatusZoo statusZoo;
    private JPanel mainPanel;
    private JButton btnAbrirFechar;
    private JTextArea areaStatus;
    private JLabel labelVisitantes;
    private JLabel labelStatus;
    private Timer timer;
    private Random random;
    private int visitantesAtuais;

    public TelaZoologico() {
        configurarJanela();
        try {
            inicializarComponentes();
            iniciarSimulacaoVisitantes();
        } catch (IOException e) {
            mostrarMensagemErro("Erro ao carregar dados: " + e.getMessage());
            System.exit(1);
        } catch (ZooException e) {
            throw new RuntimeException(e);
        }
    }

    private void configurarJanela() {
        setTitle("Zoo Kazhou - Sistema de Gerenciamento");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        random = new Random();
    }

    private void inicializarComponentes() throws IOException, ZooException {
        statusZoo = new StatusZoo();
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel Superior com Status
        JPanel statusPanel = criarPainelStatus();
        mainPanel.add(statusPanel, BorderLayout.NORTH);

        // Painel Central com Área de Status
        JPanel centralPanel = criarPainelCentral();
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        // Painel de Botões
        JPanel botoesPanel = criarPainelBotoes();
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        add(mainPanel);
        atualizarInterface();
    }

    private JPanel criarPainelStatus() {
        JPanel statusPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        statusPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Status do Zoológico",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        labelStatus = new JLabel("Status: Fechado", SwingConstants.CENTER);
        labelStatus.setFont(new Font("Arial", Font.BOLD, 14));
        labelStatus.setForeground(Color.RED);

        labelVisitantes = new JLabel("Visitantes: 0", SwingConstants.CENTER);
        labelVisitantes.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel labelHorario = new JLabel("Horário: 08:00 - 18:00", SwingConstants.CENTER);
        JLabel labelTemp = new JLabel("Temperatura: 25°C", SwingConstants.CENTER);

        statusPanel.add(labelStatus);
        statusPanel.add(labelVisitantes);
        statusPanel.add(labelHorario);
        statusPanel.add(labelTemp);

        return statusPanel;
    }

    private JPanel criarPainelCentral() {
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBorder(BorderFactory.createTitledBorder("Informações"));

        areaStatus = new JTextArea();
        areaStatus.setEditable(false);
        areaStatus.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaStatus.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(areaStatus);
        centralPanel.add(scrollPane, BorderLayout.CENTER);

        return centralPanel;
    }

    private JPanel criarPainelBotoes() {
        JPanel botoesPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        botoesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Botões existentes
        btnAbrirFechar = new JButton("Abrir Zoológico");
        estilizarBotao(btnAbrirFechar, new Color(46, 139, 87));
        btnAbrirFechar.addActionListener(e -> alternarEstadoZoologico());

        JButton btnVerAnimais = new JButton("Ver Animais");
        estilizarBotao(btnVerAnimais, new Color(70, 130, 180));
        btnVerAnimais.addActionListener(e -> mostrarAnimais());

        JButton btnVerFuncionarios = new JButton("Ver Funcionários");
        estilizarBotao(btnVerFuncionarios, new Color(70, 130, 180));
        btnVerFuncionarios.addActionListener(e -> mostrarFuncionarios());

        // Novos botões para funcionalidades adicionais
        JButton btnGerenciarAnimais = new JButton("Gerenciar Animais");
        estilizarBotao(btnGerenciarAnimais, new Color(148, 0, 211));
        btnGerenciarAnimais.addActionListener(e -> mostrarGerenciamentoAnimais());

        JButton btnGerenciarFuncionarios = new JButton("Gerenciar Funcionários");
        estilizarBotao(btnGerenciarFuncionarios, new Color(148, 0, 211));
        btnGerenciarFuncionarios.addActionListener(e -> mostrarGerenciamentoFuncionarios());

        JButton btnRecarregar = new JButton("Recarregar Dados");
        estilizarBotao(btnRecarregar, new Color(205, 133, 63));
        btnRecarregar.addActionListener(e -> {
            try {
                recarregarDados();
            } catch (IOException | ZooException ex) {
                mostrarMensagemErro("Erro ao recarregar dados: " + ex.getMessage());
            }
        });

        // Adicionando todos os botões
        botoesPanel.add(btnAbrirFechar);
        botoesPanel.add(btnVerAnimais);
        botoesPanel.add(btnVerFuncionarios);
        botoesPanel.add(btnGerenciarAnimais);
        botoesPanel.add(btnGerenciarFuncionarios);
        botoesPanel.add(btnRecarregar);

        return botoesPanel;
    }

    private void mostrarGerenciamentoAnimais() {
        JDialog dialog = new JDialog(this, "Gerenciamento de Animais", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 5, 5));
        painelBotoes.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnExaminar = new JButton("Examinar Saúde");
        JButton btnTransferir = new JButton("Transferir Habitat");
        JButton btnExercitar = new JButton("Fazer Exercício");

        painelBotoes.add(btnExaminar);
        painelBotoes.add(btnTransferir);
        painelBotoes.add(btnExercitar);

        // Lista de animais
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (Animal animal : statusZoo.getAnimais()) {
            modeloLista.addElement(animal.getNome());
        }
        JList<String> listaAnimais = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaAnimais);

        // Ações dos botões
        btnExaminar.addActionListener(e -> {
            String selectedAnimal = listaAnimais.getSelectedValue();
            if (selectedAnimal != null) {
                Animal animal = encontrarAnimal(selectedAnimal);
                if (animal != null) {
                    boolean saudavel = animal.examinarSaude();
                    JOptionPane.showMessageDialog(dialog,
                            "Estado de saúde de " + animal.getNome() + ": " +
                                    (saudavel ? "Saudável" : "Precisa de atenção"));
                }
            }
        });

        btnTransferir.addActionListener(e -> {
            String selectedAnimal = listaAnimais.getSelectedValue();
            if (selectedAnimal != null) {
                Animal animal = encontrarAnimal(selectedAnimal);
                if (animal != null) {
                    String novoHabitat = JOptionPane.showInputDialog(dialog,
                            "Novo habitat para " + animal.getNome() + ":");
                    if (novoHabitat != null && !novoHabitat.trim().isEmpty()) {
                        animal.transferirHabitat(novoHabitat);
                        JOptionPane.showMessageDialog(dialog,
                                animal.getNome() + " foi transferido para " + novoHabitat);
                    }
                }
            }
        });

        btnExercitar.addActionListener(e -> {
            String selectedAnimal = listaAnimais.getSelectedValue();
            if (selectedAnimal != null) {
                Animal animal = encontrarAnimal(selectedAnimal);
                if (animal != null) {
                    animal.fazerExercicio();
                    JOptionPane.showMessageDialog(dialog,
                            animal.getNome() + " fez exercício");
                }
            }
        });

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarGerenciamentoFuncionarios() {
        JDialog dialog = new JDialog(this, "Gerenciamento de Funcionários", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 5, 5));
        painelBotoes.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnFerias = new JButton("Gerenciar Férias");
        JButton btnTarefas = new JButton("Atribuir Tarefas");

        painelBotoes.add(btnFerias);
        painelBotoes.add(btnTarefas);

        // Lista de funcionários
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (Funcionario func : statusZoo.getFuncionarios()) {
            modeloLista.addElement(func.getNome());
        }
        JList<String> listaFuncionarios = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaFuncionarios);

        btnFerias.addActionListener(e -> {
            String selectedFunc = listaFuncionarios.getSelectedValue();
            if (selectedFunc != null) {
                Funcionario func = encontrarFuncionario(selectedFunc);
                if (func != null) {
                    Object[] options = {"Tirar Férias", "Voltar ao Trabalho"};
                    int escolha = JOptionPane.showOptionDialog(dialog,
                            "O que deseja fazer com " + func.getNome() + "?",
                            "Gerenciar Férias",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (escolha == 0) {
                        func.tirarFerias();
                    } else if (escolha == 1) {
                        func.voltarAoTrabalho();
                    }
                }
            }
        });

        btnTarefas.addActionListener(e -> {
            String selectedFunc = listaFuncionarios.getSelectedValue();
            if (selectedFunc != null) {
                Funcionario func = encontrarFuncionario(selectedFunc);
                if (func != null) {
                    Object[] options = {"Alimentar Animal", "Limpar Habitat"};
                    int escolha = JOptionPane.showOptionDialog(dialog,
                            "Qual tarefa atribuir a " + func.getNome() + "?",
                            "Atribuir Tarefa",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (escolha == 0 || escolha == 1) {
                        // Mostrar lista de animais
                        String[] animaisNomes = statusZoo.getAnimais().stream()
                                .map(Animal::getNome)
                                .toArray(String[]::new);

                        String animalSelecionado = (String) JOptionPane.showInputDialog(dialog,
                                "Selecione um animal:",
                                "Escolher Animal",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                animaisNomes,
                                animaisNomes[0]);

                        if (animalSelecionado != null) {
                            Animal animal = encontrarAnimal(animalSelecionado);
                            if (animal != null) {
                                if (escolha == 0) {
                                    String alimento = JOptionPane.showInputDialog(dialog,
                                            "Qual alimento oferecer?");
                                    if (alimento != null && !alimento.trim().isEmpty()) {
                                        func.alimentarAnimal(animal, alimento);
                                    }
                                } else {
                                    func.limparHabitat(animal);
                                }
                            }
                        }
                    }
                }
            }
        });

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private Animal encontrarAnimal(String nome) {
        return statusZoo.getAnimais().stream()
                .filter(a -> a.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    private Funcionario encontrarFuncionario(String nome) {
        return statusZoo.getFuncionarios().stream()
                .filter(f -> f.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    private void estilizarBotao(JButton botao, Color cor) {
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBorder(BorderFactory.createRaisedBevelBorder());

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
    }

    private void alternarEstadoZoologico() {
        if (!statusZoo.isEstaAberto()) {
            areaStatus.setText(statusZoo.abrirZoologico());
            btnAbrirFechar.setText("Fechar Zoológico");
            btnAbrirFechar.setBackground(new Color(178, 34, 34));
            labelStatus.setText("Status: Aberto");
            labelStatus.setForeground(new Color(46, 139, 87));
            iniciarSimulacaoVisitantes();
        } else {
            areaStatus.setText(statusZoo.fecharZoologico());
            btnAbrirFechar.setText("Abrir Zoológico");
            btnAbrirFechar.setBackground(new Color(46, 139, 87));
            labelStatus.setText("Status: Fechado");
            labelStatus.setForeground(Color.RED);
            pararSimulacaoVisitantes();
        }
    }

    private void mostrarAnimais() {
        StringBuilder info = new StringBuilder();
        info.append("=== Lista de Animais ===\n\n");
        for (Animal animal : statusZoo.getAnimais()) {
            info.append(String.format("Nome: %-15s | Espécie: %-15s | Habitat: %-15s\n",
                    animal.getNome(), animal.getEspecie(), animal.getHabitat()));
        }
        areaStatus.setText(info.toString());
    }

    private void mostrarFuncionarios() {
        StringBuilder info = new StringBuilder();
        info.append("=== Lista de Funcionários ===\n\n");
        for (Funcionario func : statusZoo.getFuncionarios()) {
            info.append(String.format("Nome: %-15s | Cargo: %-15s | Idade: %d anos\n",
                    func.getNome(), func.getCargo(), func.getIdade()));
        }
        areaStatus.setText(info.toString());
    }

    private void iniciarSimulacaoVisitantes() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        visitantesAtuais = 0;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (statusZoo.isEstaAberto()) {
                    // Simula flutuação de visitantes
                    int variacao = random.nextInt(11) - 5; // -5 a +5
                    visitantesAtuais = Math.max(0, Math.min(200, visitantesAtuais + variacao));
                    SwingUtilities.invokeLater(() ->
                            labelVisitantes.setText("Visitantes: " + visitantesAtuais));
                }
            }
        }, 0, 3000); // Atualiza a cada 3 segundos
    }

    private void pararSimulacaoVisitantes() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        visitantesAtuais = 0;
        labelVisitantes.setText("Visitantes: 0");
    }

    private void recarregarDados() throws IOException, ZooException {
        statusZoo = new StatusZoo();
        areaStatus.setText("Dados recarregados com sucesso!");
        atualizarInterface();
    }

    private void atualizarInterface() {
        labelStatus.setText("Status: " + (statusZoo.isEstaAberto() ? "Aberto" : "Fechado"));
        labelStatus.setForeground(statusZoo.isEstaAberto() ? new Color(46, 139, 87) : Color.RED);
        btnAbrirFechar.setText(statusZoo.isEstaAberto() ? "Fechar Zoológico" : "Abrir Zoológico");
        btnAbrirFechar.setBackground(statusZoo.isEstaAberto() ? new Color(178, 34, 34) : new Color(46, 139, 87));
    }

    private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
}