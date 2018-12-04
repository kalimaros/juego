/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Juego extends JFrame implements KeyListener, Runnable {

    protected int frameWidth = 500, frameHeight = 700;  ///declarando variables
    private int x = 210, y = 50;
    private int xasteroide = 450, yasteroide = 630;
    int dx = 10, dy = 10;
    private int xa = 210, ya = 50;
    private Image background = new ImageIcon(getClass().getResource("/resources/nebula.gif")).getImage();
    private int posicionX = 210;
    private int posicionY = 630;
    private Image sitting = new ImageIcon(getClass().getResource("/resources/sitting.png")).getImage();
    private Image jumping = new ImageIcon(getClass().getResource("/resources/jumping.png")).getImage();
    private Image asteroide = new ImageIcon(getClass().getResource("/resources/asteroide.png")).getImage();
    private boolean jump = false;
    private boolean descendiendo = false;
    private boolean derecha = false;
    private boolean izquierdad = true;
    private int u = 80, t = 1, g = 10;

    public Juego() {
        setBounds(50, 50, frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Jumping Planets");
        iniciaHiloAsteroide();
        iniciaHiloPlaneta();
    }

    public void iniciaHiloAsteroide() {
        Thread asteroideThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(izquierdad){
                        xasteroide -= 30;
                        if(xasteroide <= 0){
                            izquierdad = false;
                            derecha = true;
                        }
                    }
                    if(derecha){
                        xasteroide += 30;
                        if(xasteroide >= 450){
                            derecha = false;
                            izquierdad = true;
                        }
                    }
                    if(((xasteroide + 35) >= posicionX) && ((xasteroide - 35) <= posicionX) && posicionY > 600){
                        break;
                    }
                    try {
                        Thread.sleep(100); //original 80
                    } catch (InterruptedException e) {
                    }
                    repaint();
                }
            }
        });
        asteroideThread.start();
    }

    public void iniciaHiloPlaneta() {
        Thread planetaThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (jump) {
                        posicionY -= 50;
                        if (posicionY <= 400) {
                            jump = false;
                            descendiendo = true;
                        }
                    }

                    if (descendiendo) {
                        posicionY += 50;
                        if (posicionY >= 600) {
                            descendiendo = false;
                        }

                    }
                    try {

                        Thread.sleep(90); //original 80
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        planetaThread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Juego();
        /*Juego nebula = new Nebula();
        Juego planeta = new Planeta();*/

        //Thread planetaThread = new Thread(planeta);

        /*nebulaThread.start();
        planetaThread.start();*/
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void paint(Graphics g) {
        g.fillRect(0, 0, frameWidth, frameHeight);
        g.drawImage(background, 0, 0, null);
        g.drawImage(sitting, posicionX, posicionY, null);
        g.drawImage(asteroide, xasteroide, yasteroide, null);

    }

}
