package com.lpoo.atividade06;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class LivroTableModel extends AbstractTableModel {
    private List<Livro> livros;
    private final String[] colunas = {"ID", "Título", "Assunto", "ISBN", "Data de Publicação", "Autores"};

    public LivroTableModel(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public int getRowCount() {
        return livros.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Livro livro = livros.get(rowIndex);
        
        switch (columnIndex) {
            case 0: 
                return livro.getId();
                
            case 1: 
                return livro.getTitulo();
                
            case 2: 
                return livro.getAssunto();
                
            case 3: 
                return livro.getCodigoISBN();
                
            case 4: 
                return livro.getDataPublicacao();
                
            case 5: 
                StringBuilder autores = new StringBuilder();
                
                for (Autor autor : livro.getAutores()) {
                    if (autores.length() > 0) autores.append("; ");
                    autores.append(autor.toString());
                }
                
                return autores.toString();
                
            default: 
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
        fireTableDataChanged();
    }
}
