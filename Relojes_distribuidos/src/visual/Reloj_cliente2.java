/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sadok
 */
public class Reloj_cliente2 extends javax.swing.JFrame {
        
    //Inicia los hilos y los componenetes
    public Reloj_cliente2() {
        initComponents();
        h1.start(); //Servidor
        try {//Sleep para prender primero todos los servers y después ya los clientes
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reloj_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        h2.start();//Cliente
        
    }
    
    //Reloj -> Conteo del reloj
    class Clock implements Runnable{
        private int horas;
        private int minutos;
        private int segundos;
        
        public int getHoras() {
            return horas;
        }
        public void setHoras(int horas) {
            this.horas = horas;
        }
        public int getMinutos() {
            return minutos;
        }
        public void setMinutos(int minutos) {
            this.minutos = minutos;
        }
        public int getSegundos() {
            return segundos;
        }
        public void setSegundos(int segundos) {
            this.segundos = segundos;
        }

        public Clock(int h, int m, int s) {
            this.horas = h;
            this.minutos = m;
            this.segundos = s;
        }
        
        //Funcion para aumentar el segundo, minutos u hora
        public Time iniciaReloj(int h, int m, int s) throws ParseException{
                try {
                    Thread.sleep(1000);//Da la simulación de conteo cada segundo
                } catch (InterruptedException e) {//Si se interrumpe cuando está el sleep anterior
                    try {//Detiene el tiempo en el TextArea para modificar hora
                        Thread.sleep(100);//Se dan 10 segundos para modificar la hora antes de iniciar el conteo nuevamente
                    } catch (InterruptedException ex) {
                        try {
                            Thread.sleep(15000);//Detiene el conteo por 15 segundos
                        } catch (InterruptedException ex1) {
                        }
                    }
                    return null;//Regresa NULL como un identificador para decir que modifique la hora
                }
                //Hace los calculos para cambiar segundo, minuto y hora
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
                return time;//Devuelve la hora + 1
        }
        
        //Funcion para leer la hora enviada y después incrementarla
        public void iniciaConteo(){
            Time hora = Time.valueOf(getHoras()+":"+getMinutos()+":"+getSegundos());//Lee la hora de los parametros h,m y s
            while(true){//Para que cada segundo modifique la hora en el TextArea
                try {
                    hora = iniciaReloj(hora.getHours(), hora.getMinutes(), hora.getSeconds());//Lee la hora incrementada
                    if(hora==null){//Si se obtiene el identificador NULL, se lee los valores h,m y s que ya se modificaron
                        hora = Time.valueOf(getHoras()+":"+getMinutos()+":"+getSegundos());//Asigna la hora 
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Imprimea la nueva hora ya sea la incrementada o la actualizada
                h.setText(String.format("%02d", hora.getHours()));
                m.setText(String.format("%02d", hora.getMinutes()));
                s.setText(String.format("%02d", hora.getSeconds()));
                //System.out.println(hora.getHours()+":"+hora.getMinutes()+":"+hora.getSeconds());
            }
        }
        @Override
        public void run(){
            iniciaConteo();
        }
    }
    
    
    //Cliente
    private Thread clock;//Hilo que controla el conteo e impresion del reloj
    private Clock reloj;
    class RelojDos implements Runnable{
        
        public void iniciaCliente() throws IOException, ClassNotFoundException {
            Socket socket = new Socket("127.0.0.1", 5001);//Conectar a:
            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());//Abre socket para recibir datos
            Date fecha = (Time) oi.readObject();//Establece hora enviada por el server

            reloj = new Clock(fecha.getHours(), fecha.getMinutes(), fecha.getSeconds());//Se inicializa reloj
            clock = new Thread(reloj,"Hora_contador");//Se crea hilo del reloj
            clock.start();//Se inicia el incremento del reloj

            while(true){//Para actualizar la hora
                fecha = (Time) oi.readObject();//Se queda en espera para recibir nueva hora
                //Cambia la hora con la hora actualizada recibida
                reloj.setHoras(fecha.getHours());
                reloj.setMinutos(fecha.getMinutes());
                reloj.setSegundos(fecha.getSeconds());
                clock.interrupt();//Manda a actualizar la hora
            }
        }

        @Override
        public void run(){
            try {
                iniciaCliente();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(RelojDos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    RelojDos hilo2 = new RelojDos();
    Thread h2 = new Thread(hilo2, "Hilo_cliente");
    
    //Servidor
    class RelojUno implements Runnable{
        public static final int puerto = 5000;//Puerto a usar para servir

        public Time setReloj(){//Para inicializar la hora 
            return Time.valueOf(LocalTime.of(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),LocalDateTime.now().getSecond()));
        }

        public void iniciaServer()throws IOException, InterruptedException, ParseException, ClassNotFoundException{
            ServerSocket socketServidor = new ServerSocket(puerto);//Sirve en el puerto:

            while(true){
                Socket socketCliente = socketServidor.accept();//Acepta conexion entrante
                ObjectOutputStream obs = new ObjectOutputStream(socketCliente.getOutputStream());//Crea socket para enviar objetos
                obs.writeObject(setReloj());//Envia la hora actual que tienes
                obs.flush();//Limpia el buffer

                while(true){
                    if(Thread.interrupted()){//Si se oprime el boton cambiar hora 
                        try {
                            //Se interrumpe el conteo y permite cambiar la hora del TextArea - 2 Necesarios
                            clock.interrupt();
                            Thread.sleep(50);
                            clock.interrupt();
                            Thread.sleep(15000);//Espera 10s antes de enviar la nueva hora
                            //Envia los datos obtenidos y limpia buffer
                            obs.writeObject(Time.valueOf(h.getText()+":"+m.getText()+":"+s.getText()));
                            obs.flush();
                        } catch (InterruptedException e) {//Si se vuelve a oprimir el boton de cambiar
                            obs.writeObject(Time.valueOf(h.getText()+":"+m.getText()+":"+s.getText()));//Envia datos ya modificados
                            obs.flush();
                        }
                        reloj.setHoras(Integer.parseInt(h.getText()));
                        reloj.setMinutos(Integer.parseInt(m.getText()));
                        reloj.setSegundos(Integer.parseInt(s.getText()));
                        clock.interrupt();
                    }
                }
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
    
    public RelojUno hilo1 = new RelojUno();
    public Thread h1 = new Thread(hilo1, "Hilo_Servidor");
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

        jLabel3.setText("RELOJ 2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
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
                        .addGap(23, 23, 23)
                        .addComponent(interrupcion)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(interrupcion)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void interrupcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interrupcionActionPerformed
        h1.interrupt();
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
            java.util.logging.Logger.getLogger(Reloj_cliente2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reloj_cliente2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reloj_cliente2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reloj_cliente2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reloj_cliente2().setVisible(true);
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
