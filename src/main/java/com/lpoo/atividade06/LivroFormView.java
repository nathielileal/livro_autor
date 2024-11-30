package com.lpoo.atividade06;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Date;

public class LivroFormView extends JDialog {
    
    private final JTextField tituloText;
    private final JTextField assuntoText;
    private final JTextField isbnText;
    private final JFormattedTextField dataText;
    private final JComboBox<Autor> cmbAutores;
    
    private final JButton btnSalvar;
    private final JButton btnCancelar;

    public LivroFormView(Frame parent, List<Autor> autores) {
        super(parent, "Adicionar Livro", true);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Título:"));
        tituloText = new JTextField();
        add(tituloText);

        add(new JLabel("Assunto:"));
        assuntoText = new JTextField();
        add(assuntoText);

        add(new JLabel("ISBN:"));
        isbnText = new JTextField();
        add(isbnText);

        add(new JLabel("Data de Publicação:"));
        dataText = new JFormattedTextField();
        add(dataText);

        add(new JLabel("Autores:"));
        cmbAutores = new JComboBox<>(new DefaultComboBoxModel<>(autores.toArray(new Autor[0])));
        add(cmbAutores);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarLivro());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        add(btnSalvar);
        add(btnCancelar);

        setSize(300, 250);
        setLocationRelativeTo(parent);
    }

    private void salvarLivro() {
        String titulo = tituloText.getText();
        String assunto = assuntoText.getText();
        String isbn = isbnText.getText();
        Date dataPublicacao = java.sql.Date.valueOf(dataText.getText());
        Autor autorSelecionado = (Autor) cmbAutores.getSelectedItem();

        Livro novoLivro = new Livro(titulo, List.of(autorSelecionado), assunto, isbn, dataPublicacao);
        LivroDAO livroDAO = new LivroDAO();
        livroDAO.inserirLivro(novoLivro);

        JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso.");
        dispose();
    }
}
