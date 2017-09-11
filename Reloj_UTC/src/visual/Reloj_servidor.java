package visual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;


/**
 *
 * @author Sadok
 */
public class Reloj_servidor extends javax.swing.JFrame {
    
    //Inicia los hilos y los componenetes
    public Reloj_servidor() {
        initComponents();
        clock.start();//Para inicar el reloj
        admor.start();//Para iniciar el administrador de sockets
        
    }
    
    
//    -----------------------------Clases para el servidor ---------------------
    class Clock implements Runnable{
        private int hora;
        private int minuto;
        private int segundo;

        public int getH() {
            return hora;
        }
        public void setH(int hora) {
            this.hora = hora;
        }
        public int getM() {
            return minuto;
        }
        public void setM(int minuto) {
            this.minuto = minuto;
        }
        public int getS() {
            return segundo;
        }
        public void setS(int segundo) {
            this.segundo = segundo;
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
                Logger.getLogger(Reloj_servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Reloj_servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
    //        System.out.println("Time from " + TIME_SERVER + ": " + time);
            return null;
        }
        
        //Para hacer el conteo del reloj
        public Time iniciaReloj(int hora, int minuto, int segundo) throws ParseException{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return null;
                }
                if(segundo < 59){
                    segundo++;
                }else if(minuto < 59){
                    segundo = 0;
                    minuto += 1;
                }else if(hora < 23){
                    minuto = 0;
                    segundo = 0;
                    hora += 1;
                }else{
                    minuto = 0;
                    segundo = 0;
                    hora = 0;
                }
                Time time = Time.valueOf(hora+":"+minuto+":"+segundo);
                return time;
        }
        
        //Para inicializar el reloj del servidor y desplegar la hora en el jframe
        public void iniciaConteo(){
            //Consigue UTC de nist time
            Date hora = getUTC(0);
            //Time hora = Time.valueOf(fecha.getHours()+":"+fecha.getMinutes()+":"+fecha.getHours());//Descomentar tomar hora de la pc
            
            while(true){
                try {
                    hora = iniciaReloj(hora.getHours(), hora.getMinutes(), hora.getSeconds());
                    if(hora==null){
                        hora = Time.valueOf(getH()+":"+getM()+":"+getS());
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
                }
                h.setText(String.format("%02d", hora.getHours()));
                m.setText(String.format("%02d", hora.getMinutes()));
                s.setText(String.format("%02d", hora.getSeconds()));
//                System.out.println(hora);
            }
        }

        @Override
        public void run(){
            iniciaConteo();
        }
    }
    Clock reloj = new Clock();
    Thread clock = new Thread(reloj, "Hilo_Reloj");
    
    class AdminSockets implements Runnable{
        public static final int PUERTO = 5000;
        private static final int HILOS_PERMITIDOS = 5;
        private final Socket clientes[] = new Socket[HILOS_PERMITIDOS];
        private final ObjectOutputStream oos[] = new ObjectOutputStream[HILOS_PERMITIDOS];
        private int index = 0;
        private int count;
        private RelojUno reloj;


        public void actualiza() throws IOException{//Para actualizar la hora
            for(count=0; count<index;count++){
                try {
                    
//                    System.out.println(clientes[count]);//Datos para monitoreo
//                    System.out.println(oos[count]);//Datos para monitoreo
                    reloj = new RelojUno(clientes[count],oos[count]); //Se manda al reloj los datos del socket del reloj a actualizar
                    reloj.iniciaServer(); //Este no es para inicar si no actualizar hora
                    
                } catch (InterruptedException | ParseException | ClassNotFoundException ex) {
                    Logger.getLogger(AdminSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void inicia() throws IOException{//Inicia servicio de reloj 
            ServerSocket socketServidor = new ServerSocket(PUERTO);

            while(true){
                if(index < HILOS_PERMITIDOS){
                    Socket socketCliente = socketServidor.accept();//acepta Socket para enviar datos
                    System.out.println("Acepto a " + socketCliente.getRemoteSocketAddress());
                    clientes[index]=socketCliente; //almacena el socket
                    oos[index] = new ObjectOutputStream(socketCliente.getOutputStream()); //Almacena el objeto para enviar datos
                    Thread thread = new Thread(new RelojUno(clientes[index],oos[index]), "Reloj"+index);//Crea el hhilo para simpre aceptar conexiones
                    thread.start();
                    index++;//Incrementa el numero de conexiones establecidas
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
    AdminSockets adminSockets = new AdminSockets();
    Thread admor = new Thread(adminSockets, "Administrador_Sockets");
    
    //para enviar los datos a actualizar a traves de los sockets
    class RelojUno implements Runnable{   
        private Socket socketCliente;
        private ObjectOutputStream obs;
        private Date fecha = new Date();

        public RelojUno(Socket socketCliente, ObjectOutputStream obs){
            this.socketCliente = socketCliente;
            this.obs = obs;
        }
        
        private Date enviar(long delay){
            //Jala los datos del textarea
            fecha.setHours(Integer.valueOf(h.getText()));
            fecha.setMinutes(Integer.valueOf(m.getText()));
            fecha.setSeconds(Integer.valueOf(s.getText()));
            //Convierte esos datos en milisegundos
            long milisegundos = fecha.getTime();
            //Suma milisengudos con el delay de la hora a enviar por el socket
            milisegundos += delay;
            //Convierte a fecha
            fecha.setTime(milisegundos);
            return fecha;
        }
        
        private Date getRT(String domainName){//COnsigue el dalya con un ping y lo suma a la hora a regressars
            //in windows
            String command = "ping -n 3 " + domainName;

            //System.out.println(output);
            //Primero lanza el ping
            long delay = getDelay(executeCommand(command));//Consigue delay de la salida del comando
            System.out.println("Latency of: " + domainName + " => " + delay + "ms");
            //ahora consigue utc suma delay
            return enviar(delay);//Regresa la fecha con el delay
        }
        
        private String executeCommand(String command) {//Ejecuta un comando

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
        
        private int getDelay(String output){//Del string del comando solo regresa el valor de la latencia media
            return Integer.parseInt(output.substring(output.indexOf("Media = ")+8, output.length()-3));
        }
        
        public String getIP(String ip){//De los datos del socket.getremoteaddress solo regresa la ip
            return ip.substring(ip.indexOf("/")+1, ip.indexOf(":"));
        }
        
        //Enviar los datos de la fecha con delay a traves del socket
        public void iniciaServer()throws IOException, InterruptedException, ParseException, ClassNotFoundException{
            
            obs.writeObject(getRT(getIP(socketCliente.getRemoteSocketAddress().toString())));
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        h = new javax.swing.JTextField();
        m = new javax.swing.JTextField();
        s = new javax.swing.JTextField();
        interrupcion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        interrupcion.setText("Cambiar");
        interrupcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interrupcionActionPerformed(evt);
            }
        });

        jLabel1.setText(":");

        jLabel2.setText(":");

        jLabel3.setText("RELOJ UTC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(interrupcion)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(interrupcion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void interrupcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interrupcionActionPerformed
        try {//Para cuando se quiere actualizar la hora
            adminSockets.actualiza();
        } catch (IOException ex) {
            Logger.getLogger(Reloj_servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_interrupcionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reloj_servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reloj_servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reloj_servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reloj_servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reloj_servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField h;
    private javax.swing.JButton interrupcion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField m;
    private javax.swing.JTextField s;
    // End of variables declaration//GEN-END:variables
}
