package com.lpoo.atividade06;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.*;
import java.util.List;

public class AutorFormView extends JDialog {

    private final JTextField nomeText;
    private final JTextField docText;
    private final JTextField natText;
    private final JFormattedTextField dataText;

    private final JButton btnSalvar;
    private final JButton btnCancelar;

    public AutorFormView(Frame parent) {
        super(parent, "Adicionar Autor", true);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nome:"));
        nomeText = new JTextField();
        add(nomeText);

        add(new JLabel("Data de Nascimento:"));
        dataText = new JFormattedTextField();
        add(dataText);

        add(new JLabel("Documento:"));
        docText = new JTextField();
        add(docText);

        add(new JLabel("Naturalidade:"));
        natText = new JTextField();
        add(natText);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarAutor());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        add(btnSalvar);
        add(btnCancelar);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void salvarAutor() {
        String nome = nomeText.getText().trim();
        String documento = docText.getText().trim();
        String naturalidade = natText.getText().trim();

        Date dataNascimento;
        try {
            dataNascimento = java.sql.Date.valueOf(dataText.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida! Use o formato yyyy-MM-dd.");
            return;
        }

        Autor novoAutor = new Autor(nome, dataNascimento, documento, naturalidade);

        AutorDAO autorDAO = new AutorDAO();
        autorDAO.inserirAutor(novoAutor);

        JOptionPane.showMessageDialog(this, "Autor adicionado com sucesso.");
        dispose();
    }

}
