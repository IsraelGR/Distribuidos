package relojes_distribuidos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RelojUno implements Runnable{
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
                }
                
                //obs.close();
                //socketCliente.close();
            }
    }

    @Override
    public void run(){
        try {
            iniciaServer();
        } catch (IOException | InterruptedException | ParseException | ClassNotFoundException ex) {
            Logger.getLogger(RelojUno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
