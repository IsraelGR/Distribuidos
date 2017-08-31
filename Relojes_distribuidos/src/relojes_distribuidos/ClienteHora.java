package relojes_distribuidos;
/**
 *
 * @author Sadok
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;


public class ClienteHora {
    
    public static void main (String[] args) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("127.0.0.1", ServidorHora.puerto);
        
        Date fecha;
        String horaInicial;
        
        ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
        //Establece hora
        fecha = (Time) oi.readObject();
        horaInicial = fecha.getHours()+":"+fecha.getMinutes()+":"+fecha.getSeconds();
        System.out.println(horaInicial);
        //Envia hora para actualizar
        ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
        Time time = Time.valueOf("00:05:29");
//        
//        
//        //Envia y recibe hora
        while(true){
            obs.writeObject(time);
            time = (Time) oi.readObject();
            System.err.println(time.getHours()+":"+time.getMinutes()+":"+time.getSeconds());
        }
        
        //br.close();
        //socket.close();

        
        
    }
}
