/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Juego extends JFrame implements KeyListener, Runnable {

    protected int frameWidth = 500, frameHeight = 700;  ///declarando variables
    private int x = 210, y = 50;
    private int xasteroide = 450, yasteroide = 520;
    private Image background = new ImageIcon(getClass().getResource("/resources/nebula.gif")).getImage().getScaledInstance(500, 600, 0);
    private int posicionX = 210;
    private int posicionY = 520;
    private Image sitting = new ImageIcon(getClass().getResource("/resources/sitting.png")).getImage();
    private Image jumping = new ImageIcon(getClass().getResource("/resources/jumping.png")).getImage();
    private Image asteroide = new ImageIcon(getClass().getResource("/resources/asteroide.png")).getImage();

    private boolean jump = false;
    private boolean ascendiendo = true;
    private boolean descendiendo = false;
    private boolean derecha = false;
    private boolean izquierdad = true;
    private Thread thread;
    private boolean corriendo;
    private JButton reiniciarBoton;
    private JPanel contentPane;
    private boolean vivo;

    public Juego() {
        setBounds(100, 100, 0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setTitle("Jumping Planets");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        reiniciarBoton = new JButton("Jugar");
        reiniciarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                xasteroide = 450;
                yasteroide = 520;
                reiniciarBoton.setVisible(false);
                requestFocus();
                start();

            }

        });
        reiniciarBoton.setBounds(210, 600, 89, 23);
        reiniciarBoton.setVisible(false);
        contentPane.add(reiniciarBoton);

        setBounds(500, 0, frameWidth, frameHeight);
    }

    public void renderizaAsteroide() {
        if (izquierdad) {
            xasteroide -= 30;
            if (xasteroide < 0) {
                izquierdad = false;
                derecha = true;
            }
        }
        if (derecha) {
            xasteroide += 30;
            if (xasteroide >= 450) {
                derecha = false;
                izquierdad = true;
            }
        }

    }

    public void renderizaNebula() {
        if (jump) {

            if (ascendiendo) {
                posicionY -= 50;
                if (posicionY <= 300) {
                    ascendiendo = false;
                    descendiendo = true;
                }
            }

            if (descendiendo) {
                posicionY += 50;
                if (posicionY >= 500) {
                    descendiendo = false;
                    jump = false;
                    ascendiendo = true;
                }

            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Juego().start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (corriendo) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
            }
            renderizaNebula();
            renderizaAsteroide();
            repaint();

            if (((xasteroide + 35) >= posicionX) && ((xasteroide - 35) <= posicionX) && posicionY > 500) {
                corriendo = false;
                stop();
            }

        }

    }

public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(500, 0, frameWidth, frameHeight);
        g.drawImage(background, 0, 0, null);
        g.drawImage(sitting, posicionX, posicionY, null);
        g.drawImage(asteroide, xasteroide, yasteroide, null);

    }

    public void start() {
        thread = new Thread(this);
        corriendo = true;
        thread.start();
    }

    private void stop() {
        corriendo = false;
        reiniciarBoton.setVisible(true);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
