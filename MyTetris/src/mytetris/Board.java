/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mytetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author A
 */
public class Board extends JPanel implements KeyListener {

    public static int g_over = 0;
    public static int g_score = 0;

    private static int FPS = 60;
    private static int delay = FPS / 1000;

    private Random random;
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;
    private Timer looper;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"),
        Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};

    private Shape[] shapes = new Shape[7];
    private Shape currentShape;

    public Board() {
        random = new Random();
        shapes[0] = new Shape(new int[][]{
            {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
            {1, 1, 1},
            {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
            {0, 1, 1},
            {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
            {1, 1, 0},
            {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
            {1, 1},
            {1, 1}, // O shape;
        }, this, colors[6]);

        currentShape = shapes[random.nextInt(shapes.length)];

        looper = new Timer(delay, new ActionListener() {
            int n = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint(); //paintComponent will redo
            }
        });
        looper.start();
    }

    public void update() {
        if (g_over == 0) {
            currentShape.update();
        }
    }

    public void setCurrentShape() {
        currentShape = shapes[random.nextInt(shapes.length)];
        currentShape.reset();
        checkGameOver();
    }

    private void checkGameOver() {
        int[][] coords = currentShape.getCoords();
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    if (board[row + currentShape.getY()][col + currentShape.getX()] != null) {
                        g_over = 1;
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        currentShape.render(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {

                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }

            }
        }

        // drawing board
        g.setColor(Color.white);
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }

        g.setColor(Color.white);
        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
        }
        g.drawString("SCORE: ", 310, 200);
        if (g_over == 0) {
            g_score++;
        }
        g.drawString(String.valueOf(g_score), 370, 200);
        if (g_over == 1) {
            g.setColor(Color.white);
            g.drawString("GAME OVER", 50, 200);
            g.drawString("SCORE: ", 50, 250);
            g.drawString(String.valueOf(g_score), 100, 250);
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            currentShape.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            currentShape.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            currentShape.rotateShape();
        }

        if (g_over == 1) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                for (int row = 0; row < board.length; row++) {
                    for (int col = 0; col < board[row].length; col++) {
                        board[row][col] = null;
                    }
                }
                setCurrentShape();
                g_over = 0;
                g_score = 0;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedDown();
        }
    }
}
