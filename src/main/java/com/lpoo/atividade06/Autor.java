package com.lpoo.atividade06;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Autor {
    private int id;
    private String nome;
    private Date dataNascimento;
    private String documento;
    private String naturalidade;
    private List<Livro> livros;

    public Autor(String nome, Date dataNascimento, String documento, String naturalidade) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.documento = documento;
        this.naturalidade = naturalidade;
        this.livros = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        for (Livro livro : livros) {
            this.adicionarLivro(livro);
        }
    }

    public void adicionarLivro(Livro livro) {
        if (!this.livros.contains(livro)) {
            this.livros.add(livro);
            livro.adicionarAutor(this);
        }
    }

    public void removerLivro(Livro livro) {
        if (this.livros.contains(livro)) {
            this.livros.remove(livro);
            livro.removerAutor(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
