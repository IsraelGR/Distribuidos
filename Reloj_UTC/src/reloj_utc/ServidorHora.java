package reloj_utc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;
import java.sql.Time;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

/**
 * @author Sadok
 */ 
public class ServidorHora {
    public static final int puerto = 5000;
    
    public ServidorHora(){
        
    }
    
    private long getUTC(){
        String TIME_SERVER = "time-a.nist.gov";   
        NTPUDPClient timeClient = new NTPUDPClient();

        InetAddress inetAddress;
        try {
            
            inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            return timeInfo.getReturnTime();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Reloj_UTC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reloj_UTC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public Time iniciaReloj(int h, int m, int s) throws ParseException{

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

    }
    
    public void iniciaServer()throws IOException, InterruptedException, ParseException, ClassNotFoundException{
        ServerSocket socketServidor = new ServerSocket(puerto);
//        Time hora;
        
        
        
        while(true){
            Socket socketCliente = socketServidor.accept();
            Thread thread = new Thread(new Clock(0, 0, 0), "Reloj");
            System.out.println(socketCliente.getRemoteSocketAddress().toString());
            thread.start();
        }
//        while(true){
//            Socket socketCliente = socketServidor.accept();
//            ObjectOutputStream obs = new ObjectOutputStream(socketCliente.getOutputStream());
//            obs.writeObject(getUTC());
//            obs.flush();
//            ObjectInputStream oi = new ObjectInputStream(socketCliente.getInputStream());
//
//
//            while(true){//Se quito el sleep ya que ya se tiene uno arriba y el tiempo de proceso cubre el segundo
//                if(Thread.interrupted()){
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                    }
//                }
//                hora = (Time) oi.readObject();
//                obs.writeObject(iniciaReloj(hora.getHours(),hora.getMinutes(),hora.getSeconds()));
//                obs.flush();
//                Thread.currentThread().interrupt();
//            }
//
//            //obs.close();
//            //socketCliente.close();
//        }
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
//----------------------------------------------------
//        RelojUno hilo1 = new RelojUno();
//        Thread h1 = new Thread(hilo1, "Hilo_Secuencia1");
//        h1.start();
//-------------------------------------------------
        
    }
}
    