package relojes_distribuidos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.*;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import visual.Reloj_cliente;
import visual.Reloj_servidor;

/**
 * @author Sadok
 */ 
public class ServidorHora {
    public static final int puerto = 5000;
    
    public Time setReloj(){
        return Time.valueOf(LocalTime.of(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),LocalDateTime.now().getSecond()));
    }
    
    public Time iniciaReloj(int h, int m, int s) throws ParseException{
//        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
            if(s < 59){
                s++;
            }else if(m < 59){
                s = 0;
                m += 1;
            }else if(h < 23){
                m = 0;
                s = 0;
                h += 1;
            }else{
                m = 0;
                s = 0;
                h = 0;
            }
            
            Time time = Time.valueOf(h+":"+m+":"+s);
            return time;
//        }
    }
    
    public void iniciaServer()throws IOException, InterruptedException, ParseException, ClassNotFoundException{
        ServerSocket socketServidor = new ServerSocket(puerto);
        Time hora;
        
            while(true){
                Socket socketCliente = socketServidor.accept();
                ObjectOutputStream obs = new ObjectOutputStream(socketCliente.getOutputStream());
                obs.writeObject(setReloj());
                obs.flush();
                ObjectInputStream oi = new ObjectInputStream(socketCliente.getInputStream());
                
            
                while(true){//Se quito el sleep ya que ya se tiene uno arriba y el tiempo de proceso cubre el segundo
                    if(Thread.interrupted()){
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                        }
                    }
                    hora = (Time) oi.readObject();
                    obs.writeObject(iniciaReloj(hora.getHours(),hora.getMinutes(),hora.getSeconds()));
                    obs.flush();
                    Thread.currentThread().interrupt();
                }
                
                //obs.close();
                //socketCliente.close();
            }
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException{
        try {
            
            new ServidorHora().iniciaServer();
            
        } catch (IOException | InterruptedException | ParseException ex) {
            Logger.getLogger(ServidorHora.class.getName()).log(Level.SEVERE, null, ex);
        }
//----------------------------------------------------
//        RelojUno hilo1 = new RelojUno();
//        Thread h1 = new Thread(hilo1, "Hilo_Secuencia1");
//        h1.start();
//-------------------------------------------------
        
    }
}
    