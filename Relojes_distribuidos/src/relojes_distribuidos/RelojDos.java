package relojes_distribuidos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import visual.*;

public class RelojDos implements Runnable{
    
    private Cliente cliente = new Cliente();
    
    public void iniciaCliente() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", ServidorHora.puerto);
        
        Date fecha;
        JTextField string = new JTextField();
        
        ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
        //Establece hora
        fecha = (Time) oi.readObject();
        string.setText(Integer.toString(fecha.getHours()));
        cliente.setH(string);
        System.out.println(cliente.getH());
        string.setText(Integer.toString(fecha.getMinutes()));
        cliente.setH(string);
        string.setText(Integer.toString(fecha.getSeconds()));
        cliente.setH(string);
        
        System.err.println(fecha);
        //Envia hora para actualizar
        ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
        Time time = Time.valueOf("00:05:29");
//        
//        
//        //Envia y recibe hora
        while(true){
            System.err.println(fecha);
            obs.writeObject(time);
            time = (Time) oi.readObject();
            string.setText(Integer.toString(time.getHours()));
            cliente.setH(string);
            string.setText(Integer.toString(time.getMinutes()));
            cliente.setH(string);
            string.setText(Integer.toString(time.getSeconds()));
            cliente.setH(string);
            
        }
        
        //br.close();
        //socket.close();

        
        
     
    }

    @Override
    public void run(){
        try {
            cliente.setVisible(true);
            iniciaCliente();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RelojDos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
