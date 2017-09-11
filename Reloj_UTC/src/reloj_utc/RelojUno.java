package reloj_utc;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

//SERVIDOR
public class RelojUno implements Runnable{
    
    private Socket socketCliente;
    private ObjectOutputStream obs;
    
    public RelojUno(Socket socketCliente, ObjectOutputStream obs){
        this.socketCliente = socketCliente;
        this.obs = obs;
    }
    
    public void iniciaServer()throws IOException, InterruptedException, ParseException, ClassNotFoundException{
        
        obs.writeObject(Time.valueOf("22:22:22"));
        obs.flush();
        
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
