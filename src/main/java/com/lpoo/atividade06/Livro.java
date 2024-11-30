package com.lpoo.atividade06;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Livro {

    private int id;
    private String titulo;
    private String assunto;
    private String codigoISBN;
    private Date dataPublicacao;
    private List<Autor> autores;

    public Livro(String titulo, List<Autor> autores, String assunto, String codigoISBN, Date dataPublicacao) {
        this.titulo = titulo;
        this.autores = autores;
        this.assunto = assunto;
        this.codigoISBN = codigoISBN;
        this.dataPublicacao = dataPublicacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setCodigoISBN(String codigoISBN) {
        this.codigoISBN = codigoISBN;
    }

    public String getCodigoISBN() {
        return codigoISBN;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setAutores(List<Autor> autores) {
        Iterator iterator = autores.iterator();

        while (iterator.hasNext()) {
            Autor autor = (Autor) iterator.next();
            this.adicionarAutor(autor);
        }
    }

    public List<Autor> getAutores() {
        return this.autores;
    }

    public void adicionarAutor(Autor autor) {
        if (!this.getAutores().contains(autor)) {
            this.autores.add(autor);
            autor.adicionarLivro(this);
        }
    }

    public void removerAutor(Autor autor) {
        if (!this.getAutores().contains(autor)) {
            this.autores.remove(autor);
            autor.removerLivro(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
