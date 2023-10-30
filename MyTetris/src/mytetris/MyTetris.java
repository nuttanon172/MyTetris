/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mytetris;

import javax.swing.JFrame;

/**
 *
 * @author A
 */
public class MyTetris {
    private static final int WIDTH = 445, HEIGHT = 629;
    
    private Board board;
    private JFrame window;

    public MyTetris(){
        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null); 
    
        board = new Board();
        window.add(board);
        window.addKeyListener(board);
        window.setVisible(true);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new MyTetris();
    }

}
