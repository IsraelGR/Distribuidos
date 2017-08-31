package relojes_distribuidos;

public class Relojes_distribuidos {

    public static void main(String[] args) {
        RelojDos hilo2 = new RelojDos();
        Thread h2 = new Thread(hilo2, "Hilo_Secuencia2");
        h2.start(); 
    }
    
}
