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

public class Juego extends JFrame implements KeyListener, Runnable, ActionListener{

    protected int frameWidth = 500, frameHeight = 700;  ///declarando variables
    private int x = 210, y = 50;
    private int xasteroide = 450, yasteroide = 630;
    private Image background = new ImageIcon(getClass().getResource("/resources/nebula.gif")).getImage();
    private int posicionX = 210;
    private int posicionY = 630;
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

    public Juego() {
        setBounds(50, 50, frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Jumping Planets");
    }

    public void renderizaAsteroide() {
        if (izquierdad) {
            xasteroide -= 30;
            if (xasteroide <= 0) {
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
                if (posicionY <= 400) {
                    ascendiendo = false;
                    descendiendo = true;
                }
            }

            if (descendiendo) {
                posicionY += 50;
                if (posicionY >= 600) {
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

            if (((xasteroide + 35) >= posicionX) && ((xasteroide - 35) <= posicionX) && posicionY > 600) {
                corriendo = false;
                stop();
            }

        }

    }

    public void paint(Graphics g) {
        g.fillRect(0, 0, frameWidth, frameHeight);
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
        getGraphics().clearRect(0, 0, frameWidth, frameHeight);
        reiniciarBoton = new JButton("SALIR");
        reiniciarBoton.setBounds(100, 150, 100, 30);
        reiniciarBoton.addActionListener(this);
        add(reiniciarBoton);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==reiniciarBoton) {
            System.out.println("");
        }
    }

}
