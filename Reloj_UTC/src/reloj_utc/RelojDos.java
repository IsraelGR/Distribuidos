package reloj_utc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


//CLIENTE
public class RelojDos implements Runnable{
    
    public void iniciaCliente() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", 5000);
        
        Date fecha;
        ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
        //Establece hora
        fecha = (Time) oi.readObject();
        
        System.out.println(fecha);
        
        Clock hilo1 = new Clock(fecha.getHours(), fecha.getMinutes(), fecha.getSeconds());
        Thread h1 = new Thread(hilo1,"Hora_contador");
        h1.start();
        
        
        h1.interrupt();
        hilo1.setH(22);
        hilo1.setM(22);
        hilo1.setS(22);
        
        
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RelojDos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fecha = (Time) oi.readObject();
        h1.interrupt();
        hilo1.setH(fecha.getHours());
        hilo1.setM(fecha.getMinutes());
        hilo1.setS(fecha.getSeconds());
        //Envia hora para actualizar
        //ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
//        Time time = Time.valueOf("00:05:29");
//        
//        
//        //Envia y recibe hora
//        while(true){
//            System.err.println(fecha);
//            obs.writeObject(time);
//            time = (Time) oi.readObject();
//            
//        }
        
        //br.close();
        //socket.close();

        
        
     
    }

    @Override
    public void run(){
        try {
            iniciaCliente();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RelojDos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
