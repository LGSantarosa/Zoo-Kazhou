package zoo.gui;

import zoo.animais.Animal;
import zoo.animais.comportamento.*;
import zoo.animais.caracteristicas.*;
import zoo.model.Ocorrencia;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AnimalForm extends JFrame {
    private JTextField txtNome;
    private JTextField txtIdade;
    private JTextField txtEspecie;
    private JTextField txtProcedencia;
    private JComboBox<String> cmbStatusSaude;
    private JTextField txtDietaEspecial;
    private JCheckBox chkQuarentena;
    private JTextField txtLocalizacao;
    private JTextField txtVeterinario;
    private JTextField txtPeso;
    private JTextArea txtCuidadosEspeciais;
    private JTextArea txtVacinas;
    private JTextArea txtOcorrencias;
    
    private JComboBox<String> cmbComportamentoAlimentar;
    private JComboBox<String> cmbComportamentoSonoro;
    
    private Animal animalAtual;

    public AnimalForm() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        setTitle("Cadastro de Animal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Painel principal com GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dados Básicos
        JPanel dadosBasicosPanel = criarPainelTitulado("Dados Básicos");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(dadosBasicosPanel, gbc);

        // Campos de dados básicos
        txtNome = new JTextField(20);
        txtIdade = new JTextField(20);
        txtEspecie = new JTextField(20);
        txtProcedencia = new JTextField(20);

        adicionarCampo(dadosBasicosPanel, "Nome:", txtNome);
        adicionarCampo(dadosBasicosPanel, "Idade:", txtIdade);
        adicionarCampo(dadosBasicosPanel, "Espécie:", txtEspecie);
        adicionarCampo(dadosBasicosPanel, "Procedência:", txtProcedencia);

        // Status e Saúde
        JPanel statusPanel = criarPainelTitulado("Status e Saúde");
        gbc.gridy = 1;
        mainPanel.add(statusPanel, gbc);

        String[] statusOptions = {"SAUDÁVEL", "EM_TRATAMENTO", "CRÍTICO"};
        cmbStatusSaude = new JComboBox<>(statusOptions);
        txtDietaEspecial = new JTextField(20);
        chkQuarentena = new JCheckBox("Em Quarentena");
        txtLocalizacao = new JTextField(20);
        txtVeterinario = new JTextField(20);
        txtPeso = new JTextField(20);

        adicionarCampo(statusPanel, "Status:", cmbStatusSaude);
        adicionarCampo(statusPanel, "Dieta Especial:", txtDietaEspecial);
        statusPanel.add(chkQuarentena);
        adicionarCampo(statusPanel, "Localização:", txtLocalizacao);
        adicionarCampo(statusPanel, "Veterinário:", txtVeterinario);
        adicionarCampo(statusPanel, "Peso (kg):", txtPeso);

        // Comportamentos
        JPanel comportamentosPanel = criarPainelTitulado("Comportamentos");
        gbc.gridy = 2;
        mainPanel.add(comportamentosPanel, gbc);

        String[] comportamentosAlimentares = {"Carnívoro", "Herbívoro"};
        String[] comportamentosSonoros = {"Rugido", "Grasnido"};
        cmbComportamentoAlimentar = new JComboBox<>(comportamentosAlimentares);
        cmbComportamentoSonoro = new JComboBox<>(comportamentosSonoros);

        adicionarCampo(comportamentosPanel, "Comportamento Alimentar:", cmbComportamentoAlimentar);
        adicionarCampo(comportamentosPanel, "Comportamento Sonoro:", cmbComportamentoSonoro);

        // Cuidados e Vacinas
        JPanel cuidadosPanel = criarPainelTitulado("Cuidados e Vacinas");
        gbc.gridy = 3;
        mainPanel.add(cuidadosPanel, gbc);

        txtCuidadosEspeciais = new JTextArea(3, 20);
        txtVacinas = new JTextArea(3, 20);
        JScrollPane scrollCuidados = new JScrollPane(txtCuidadosEspeciais);
        JScrollPane scrollVacinas = new JScrollPane(txtVacinas);

        adicionarCampo(cuidadosPanel, "Cuidados Especiais:", scrollCuidados);
        adicionarCampo(cuidadosPanel, "Vacinas:", scrollVacinas);

        // Ocorrências
        JPanel ocorrenciasPanel = criarPainelTitulado("Ocorrências");
        gbc.gridy = 4;
        mainPanel.add(ocorrenciasPanel, gbc);

        txtOcorrencias = new JTextArea(5, 40);
        JScrollPane scrollOcorrencias = new JScrollPane(txtOcorrencias);
        ocorrenciasPanel.add(scrollOcorrencias);

        // Botões
        JPanel botoesPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvarAnimal());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnLimpar);
        botoesPanel.add(btnCancelar);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(botoesPanel, gbc);

        // Adiciona o painel principal ao frame
        add(new JScrollPane(mainPanel));
    }

    private JPanel criarPainelTitulado(String titulo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private void adicionarCampo(JPanel panel, String label, JComponent campo) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = panel.getComponentCount() / 2;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void setupFrame() {
        setLocationRelativeTo(null);
    }

    private void salvarAnimal() {
        try {
            Animal animal = new Animal();
            animal.setNome(txtNome.getText());
            animal.setIdade(Integer.parseInt(txtIdade.getText()));
            animal.setEspecie(txtEspecie.getText());
            
            // Configurar comportamentos
            if (cmbComportamentoAlimentar.getSelectedItem().equals("Carnívoro")) {
                animal.setComportamentoAlimentar(new Carnivoro());
            } else {
                animal.setComportamentoAlimentar(new Herbivoro());
            }

            if (cmbComportamentoSonoro.getSelectedItem().equals("Rugido")) {
                animal.setComportamentoSonoro(new Rugido());
            } else {
                animal.setComportamentoSonoro(new Grasnido());
            }

            // Configurar características físicas
            CaracteristicasFisicas fisicas = new CaracteristicasFisicas(
                Double.parseDouble(txtPeso.getText()),
                0.0, // altura padrão
                "", // cor padrão
                "", // tipo de pele padrão
                txtProcedencia.getText()
            );
            animal.setCaracteristicasFisicas(fisicas);

            // Configurar características comportamentais
            CaracteristicasComportamentais comportamentais = new CaracteristicasComportamentais(
                false, // social
                false, // noturno
                "médio", // agressividade
                "médio"  // inteligência
            );
            animal.setCaracteristicasComportamentais(comportamentais);

            // Configurar outros atributos
            animal.atualizarStatusSaude((String) cmbStatusSaude.getSelectedItem());
            animal.definirDietaEspecial(txtDietaEspecial.getText());
            if (chkQuarentena.isSelected()) {
                animal.iniciarQuarentena("Quarentena inicial");
            }
            animal.transferirLocalizacao(txtLocalizacao.getText());
            animal.designarVeterinario(txtVeterinario.getText());
            animal.registrarPeso(Double.parseDouble(txtPeso.getText()));

            // Adicionar cuidados especiais
            String[] cuidados = txtCuidadosEspeciais.getText().split("\n");
            for (String cuidado : cuidados) {
                if (!cuidado.trim().isEmpty()) {
                    animal.adicionarCuidadoEspecial(cuidado.trim());
                }
            }

            // Adicionar vacinas
            String[] vacinas = txtVacinas.getText().split("\n");
            for (String vacina : vacinas) {
                if (!vacina.trim().isEmpty()) {
                    animal.registrarVacina(vacina.trim());
                }
            }

            JOptionPane.showMessageDialog(this, "Animal salvo com sucesso!");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos numéricos corretamente.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar animal: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtEspecie.setText("");
        txtProcedencia.setText("");
        cmbStatusSaude.setSelectedIndex(0);
        txtDietaEspecial.setText("");
        chkQuarentena.setSelected(false);
        txtLocalizacao.setText("");
        txtVeterinario.setText("");
        txtPeso.setText("");
        txtCuidadosEspeciais.setText("");
        txtVacinas.setText("");
        txtOcorrencias.setText("");
        cmbComportamentoAlimentar.setSelectedIndex(0);
        cmbComportamentoSonoro.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AnimalForm().setVisible(true);
        });
    }
} 