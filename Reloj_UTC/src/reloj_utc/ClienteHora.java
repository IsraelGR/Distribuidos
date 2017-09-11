package reloj_utc;
/**
 *
 * @author Sadok
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;


public class ClienteHora {
    
    public static void main (String[] args) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("127.0.0.1", 5000);
        ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
        
        Date fecha = (Date) oi.readObject();
//        Clock reloj = new Clock(fecha.getHours(), fecha.getMinutes(), fecha.getSeconds());
        Clock reloj = new Clock(22, 22, 22);
        Thread hora = new Thread(reloj, "Hora_Cliente");
        hora.start();
        
        while(true){
            fecha = (Date) oi.readObject();
            reloj.setH(fecha.getHours());
            reloj.setM(fecha.getMinutes());
            reloj.setS(fecha.getSeconds());
            hora.interrupt();
        }
    }
}
