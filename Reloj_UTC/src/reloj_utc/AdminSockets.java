package reloj_utc;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sadok
 */
public class AdminSockets implements Runnable{
    
    public static final int PUERTO = 5000;
    private static final int HILOS_PERMITIDOS = 5;
    private final Socket clientes[] = new Socket[HILOS_PERMITIDOS];
    private final ObjectOutputStream oos[] = new ObjectOutputStream[HILOS_PERMITIDOS];
    private int index = 0;
    private int count;
    private RelojUno reloj;
    
    public String getIP(String ip){
        return ip.substring(ip.indexOf("/")+1, ip.indexOf(":"));
    }
    public void actualiza() throws IOException{
        for(count=0; count<index;count++){
            try {
                System.out.println(clientes[count]);
                System.out.println(oos[count]);
                System.out.println(getIP(clientes[count].getRemoteSocketAddress().toString()));
                reloj = new RelojUno(clientes[count],oos[count]);
                reloj.iniciaServer(); //Este no es para inicar si no actualizar hora
            } catch (InterruptedException | ParseException | ClassNotFoundException ex) {
                Logger.getLogger(AdminSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void inicia() throws IOException{
        ServerSocket socketServidor = new ServerSocket(PUERTO);
        //Para verificar al presionar el boton actualizar
        
        while(true){
            if(index < HILOS_PERMITIDOS){
                Socket socketCliente = socketServidor.accept();//Socket para enviar datos
                clientes[index]=socketCliente;
                oos[index] = new ObjectOutputStream(socketCliente.getOutputStream());
                Thread thread = new Thread(new RelojUno(clientes[index],oos[index]), "Reloj");
                thread.start();
                index++;
            }
        }
    }
    
    @Override
    public void run(){
        try {
            inicia();
        } catch (IOException ex) {
            Logger.getLogger(AdminSockets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
