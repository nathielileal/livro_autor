package com.lpoo.atividade06;

import javax.swing.JFrame;

public class Atividade06 {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Livro/Autor");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LivroAutorView livroAutorView = new LivroAutorView();
        frame.add(livroAutorView);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

}
