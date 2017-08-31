package relojes;


public class Relojes {
    
    public static void main(String[] args) {
        
        Reloj1 hilo1 = new Reloj1();
        Thread h1 = new Thread(hilo1, "Hilo_Secuencia1");
        h1.start();
        Reloj2 hilo2 = new Reloj2();
        Thread h2 = new Thread(hilo2, "Hilo_Secuencia1");
        h2.start();
        
        try {
            Thread.sleep(4000); //Detiene reloj
        } catch (Exception e) {
        }
        h1.interrupt();
        try {
            Thread.sleep(3000); //Inicia reloj
        } catch (Exception e) {
        }
        h1.interrupt();
        
        
        
        
        
        
        
        
        /*
        Reloj4 hilo4 = new Reloj4();
        Thread h4 = new Thread(hilo4, "Hilo_Secuencia4");
        h4.start();
        Reloj3 hilo3 = new Reloj3();
        Thread h3 = new Thread(hilo3, "Hilo_Secuencia4");
        h3.start();
        Reloj2 hilo2 = new Reloj2();
        Thread h2 = new Thread(hilo2, "Hilo_Secuencia4");
        h2.start();*/
        
    }
    
}
