package com.lpoo.atividade06;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AutorTableModel extends AbstractTableModel {

    private List<Autor> autores;
    private final String[] colunas = {"ID", "Nome", "Data Nascimento", "Documento", "Naturalidade", "Livros"};

    public AutorTableModel(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public int getRowCount() {
        return autores.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Autor autor = autores.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return autor.getId();

            case 1:
                return autor.getNome();

            case 2:
                return autor.getDataNascimento();

            case 3:
                return autor.getDocumento();

            case 4:
                return autor.getNaturalidade();

            case 5:
                StringBuilder livros = new StringBuilder();

                for (Livro livro : autor.getLivros()) {
                    if (livros.length() > 0) {
                        livros.append("; ");
                    }

                    livros.append(livro.getTitulo());
                }

                return livros.toString();

            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
        fireTableDataChanged();
    }
}
