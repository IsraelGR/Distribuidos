package relojes_distribuidos;

import visual.Reloj_cliente;
import visual.Reloj_cliente2;

public class Relojes_distribuidos {

    public static void main(String[] args) {
//        RelojUno hilo1 = new RelojUno();
//        Thread h1 = new Thread(hilo1, "Hilo_Secuencia2");
//        h1.start(); 
//        
//        RelojDos hilo2 = new RelojDos();
//        Thread h2 = new Thread(hilo2, "Hilo_Secuencia2");
//        h2.start(); 
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Reloj_servidor().setVisible(true);
//            }
//        });
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reloj_cliente().setVisible(true);
            }
        });
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reloj_cliente2().setVisible(true);
            }
        });
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Reloj_cliente2().setVisible(true);
//            }
//        });
    }
    
}
