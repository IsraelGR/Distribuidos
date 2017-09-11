package visual;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sadok
 */
public class Reloj_cliente extends javax.swing.JFrame {
    
    public Reloj_cliente() {
        initComponents();
        hora.start();
        client.start();
    }
    
    //Inicia los hilos y los componenetes
    class Clock implements Runnable{
        private int hora;
        private int minuto;
        private int segundo;

        public Clock(int hora, int minuto, int segundo) {
            this.hora = hora;
            this.minuto = minuto;
            this.segundo = segundo;
        }

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

        public void iniciaConteo(){
            Time hora = Time.valueOf(getH()+":"+getM()+":"+getS());
            while(true){
                try {
                    hora = iniciaReloj(hora.getHours(), hora.getMinutes(), hora.getSeconds());
                    if(hora==null){
                        hora = Time.valueOf(getH()+":"+getM()+":"+getS());
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Imprime en el textarea la hora
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
    Clock reloj = new Clock(22, 05, 11);
    Thread hora = new Thread(reloj, "Hora_Cliente");

    class Cliente implements Runnable{
        
        public void iniciaCliente() throws IOException, ClassNotFoundException, InterruptedException{
            Socket socket = new Socket("127.0.0.1", 5000);
            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
            Date fechaAntes = new Date();//Para comparar la fecha que se tenía en el jframe
            boolean suspend = false;//Para saber si el hilo esta suspendido
            boolean flag = false;//Para saber si se debe de suspender o no
            
            Date fecha = (Date) oi.readObject();//Recibe la hora del servidor
    //        Clock reloj = new Clock(fecha.getHours(), fecha.getMinutes(), fecha.getSeconds());//Descomentar para sincronizar al inicio

            while(true){
                fecha = (Date) oi.readObject();//Lee la hora el servidor
                fechaAntes = Time.valueOf(h.getText()+":"+m.getText()+":"+s.getText());//Para compara la hora anterior con la nueva
                flag = Detiene(fecha, fechaAntes);//Compara horas para saber si suspender o no la hora
                
                if( (flag==true)&(suspend==false) ){
                    //Si la esta hora esta adelantada se detiene conteo
                    hora.suspend();
                    suspend = true;
                }else if( (flag==false)&(suspend==true) ){
                    //Si la esta hora esta atrasada y se detuvo el conteo del reloj entonces se actualiza
                    reloj.setH(fecha.getHours());
                    reloj.setM(fecha.getMinutes());
                    reloj.setS(fecha.getSeconds());
                    hora.resume();
                    hora.sleep(10);
                    suspend = false;
                    hora.interrupt();
                    
                }else if((flag==false)&(suspend==false)){
                    //Si la esta hora esta atrasada actualiza
                    reloj.setH(fecha.getHours());
                    reloj.setM(fecha.getMinutes());
                    reloj.setS(fecha.getSeconds());
                    hora.interrupt();
                }                
            }
        }
        
        public boolean Detiene(Date fecha, Date fechaAntes){//Para Saber si se detiene o no dependiendo de sus valores
            if( fecha.getHours()<fechaAntes.getHours() ){
                return true;
            }else if( (fecha.getHours()==fechaAntes.getHours())&(fecha.getMinutes()<fechaAntes.getMinutes()) ){
                return true;
            }else if( (fecha.getHours()==fechaAntes.getHours())&(fecha.getMinutes()==fechaAntes.getMinutes())&(fecha.getSeconds()<fechaAntes.getSeconds()) ){
                return true;
            }else
                return false;//Si no se debe de detener
        }
        
        @Override
        public void run(){
            try {
                iniciaCliente();
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                Logger.getLogger(Reloj_cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    Cliente cliente = new Cliente();
    Thread client = new Thread(cliente, "Hilo_Cliente");
    
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText(":");

        jLabel2.setText(":");

        jLabel3.setText("RELOJ 1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
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
                        .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(81, Short.MAX_VALUE))
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
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Reloj_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reloj_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reloj_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reloj_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reloj_cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField h;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField m;
    private javax.swing.JTextField s;
    // End of variables declaration//GEN-END:variables
}
