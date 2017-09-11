package reloj_utc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient; 
import org.apache.commons.net.ntp.TimeInfo;
/**
 *
 * @author Sadok
 */
public class Reloj_UTC {

    public static void main(String[] args) throws IOException {
//        Reloj_UTC reloj = new Reloj_UTC();
//        System.out.println(reloj.getRT("google.com"));
            
        Thread reloj = new Thread(new Clock(0, 0, 0), "Hora_UCT");
        reloj.start();
        
        AdminSockets adminSockets = new AdminSockets();
        Thread admor = new Thread(adminSockets, "Administrador_Sockets");
        admor.start();
        
        try {
            Thread.sleep(6000);
            System.out.println("Interrumpe");
            adminSockets.actualiza();
            reloj.suspend();
            Thread.sleep(6000);
            System.out.println("Interrumpe");
            adminSockets.actualiza();
            reloj.resume();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Reloj_UTC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
//        Reloj_UTC reloj = new Reloj_UTC();
//        String domainName = "facebook.com";
//        
//        System.out.println(reloj.getRT(domainName));

//        Clock clk = new Clock(0, 0, 0);
//        clk.iniciaConteo();
        
        
    }
    
    private Date getRT(String domainName){
        //in windows
        String command = "ping -n 3 " + domainName;

        //System.out.println(output);
        //Primero lanza el ping
        long delay = getDelay(executeCommand(command));
        System.out.println("Latency of: " + domainName + " => " + delay);
        //ahora consigue utc suma delay
        Date utc = getUTC(delay);
        return utc;
    }
    
    //Para inicializar server con hora UTC
    private Date getUTC(long delay){
        String TIME_SERVER = "time-a.nist.gov";   
        NTPUDPClient timeClient = new NTPUDPClient();

        InetAddress inetAddress;
        try {
            
            inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getReturnTime();
            Date time = new Date(returnTime+delay);//Se hace cargo del delay
            return time;
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Reloj_UTC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reloj_UTC.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("Time from " + TIME_SERVER + ": " + time);
        return null;
    }

    private String executeCommand(String command) {

        StringBuilder output = new StringBuilder();
        Process p;
        try {
                p = Runtime.getRuntime().exec(command);
                p.waitFor();
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";
                while ((line = reader.readLine())!= null) {
                        output.append(line).append("\n");
                }
        } catch (IOException | InterruptedException e) {
        }
        return output.toString();
    }
    
    private int getDelay(String output){
        return Integer.parseInt(output.substring(output.indexOf("Media = ")+8, output.length()-3));
    }
    
    

}