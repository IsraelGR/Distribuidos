/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author Sadok
 */

public class Relojes extends javax.swing.JFrame {

    /**
     * Creates new form Relojes
     */
    public Relojes() {
        initComponents();
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        
    }
    
    //---------------------------------------------- Se agregan als clases de los relojes--------------
    class Reloj1 implements Runnable{
        private boolean interruption = false;

        public void inicia(int h, int m, int s){
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                    return;
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
                r1h.setText(String.format("%02d", h));
                r1m.setText(String.format("%02d", m));
                r1s.setText(String.format("%02d", s));
            }
        }

        @Override
        public void run(){
            r1h.setText(String.format("%02d", LocalDateTime.now().getHour()));
            r1m.setText(String.format("%02d", LocalDateTime.now().getMinute()));
            r1s.setText(String.format("%02d", LocalDateTime.now().getSecond()));
            while(true){
                if(Thread.interrupted()){
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                }

                if(interruption){
                    try {
                        Thread.sleep(30000);
                        interruption = !interruption;
                    } catch (Exception e) {
                        interruption = !interruption;
                    }
                }else{
                    inicia(Integer.parseInt(r1h.getText()), Integer.parseInt(r1m.getText()), Integer.parseInt(r1s.getText()));
                }
            }
        }
    }
    
    class Reloj2 implements Runnable{
        private boolean interruption = false;

        public void inicia(int h, int m, int s){
            
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                    return;
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
                r2h.setText(String.format("%02d", h));//Cambiar por h m y s para agarrar los vaores que se tiene en el text
                r2m.setText(String.format("%02d", m));
                r2s.setText(String.format("%02d", s));
            }
        }
        @Override
        public void run(){
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
            r2h.setText(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
            r2m.setText(String.format("%02d", calendar.get(Calendar.MINUTE)));
            r2s.setText(String.format("%02d", calendar.get(Calendar.SECOND)));
            while(true){
                if(Thread.interrupted()){
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                }

                if(interruption){
                    try {
                        Thread.sleep(30000);
                        interruption = !interruption;
                    } catch (Exception e) {
                        interruption = !interruption;
                    }
                }else{
                    inicia(Integer.parseInt(r2h.getText()), Integer.parseInt(r2m.getText()), Integer.parseInt(r2s.getText()));
                }
            }
        }
    }
    
    class Reloj3 implements Runnable{
        private boolean interruption = false;

        public void inicia(int h, int m, int s){
            
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                    return;
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
                r3h.setText(String.format("%02d", h));//Cambiar por h m y s para agarrar los vaores que se tiene en el text
                r3m.setText(String.format("%02d", m));
                r3s.setText(String.format("%02d", s));
            }
        }
        @Override
        public void run(){
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
            r3h.setText(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
            r3m.setText(String.format("%02d", calendar.get(Calendar.MINUTE)));
            r3s.setText(String.format("%02d", calendar.get(Calendar.SECOND)));
            while(true){
                if(Thread.interrupted()){
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                }

                if(interruption){
                    try {
                        Thread.sleep(30000);
                        interruption = !interruption;
                    } catch (Exception e) {
                        interruption = !interruption;
                    }
                }else{
                    inicia(Integer.parseInt(r3h.getText()), Integer.parseInt(r3m.getText()), Integer.parseInt(r3s.getText()));
                }
            }
        }
    }
    
    class Reloj4 implements Runnable{
        private boolean interruption = false;

        public void inicia(int h, int m, int s){
            
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                    return;
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
                r4h.setText(String.format("%02d", h));//Cambiar por h m y s para agarrar los vaores que se tiene en el text
                r4m.setText(String.format("%02d", m));
                r4s.setText(String.format("%02d", s));
            }
        }
        @Override
        public void run(){
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(TimeZone.getTimeZone("Africa/Accra"));
            r4h.setText(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
            r4m.setText(String.format("%02d", calendar.get(Calendar.MINUTE)));
            r4s.setText(String.format("%02d", calendar.get(Calendar.SECOND)));
            while(true){
                if(Thread.interrupted()){
                    interruption = !interruption;
                    System.err.println("Interrumpido");
                }

                if(interruption){
                    try {
                        Thread.sleep(30000);
                        interruption = !interruption;
                    } catch (Exception e) {
                        interruption = !interruption;
                    }
                }else{
                    inicia(Integer.parseInt(r4h.getText()), Integer.parseInt(r4m.getText()), Integer.parseInt(r4s.getText()));
                }
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    private Reloj1 hilo1 = new Reloj1();
    private Thread h1 = new Thread(hilo1, "Hilo_Secuencia1");
    private Reloj2 hilo2 = new Reloj2();
    private Thread h2 = new Thread(hilo2, "Hilo_Secuencia2");
    private Reloj3 hilo3 = new Reloj3();
    private Thread h3 = new Thread(hilo3, "Hilo_Secuencia3");
    private Reloj4 hilo4 = new Reloj4();
    private Thread h4 = new Thread(hilo4, "Hilo_Secuencia4");
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        r1h = new javax.swing.JTextField();
        r1m = new javax.swing.JTextField();
        r1s = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        r2h = new javax.swing.JTextField();
        r2m = new javax.swing.JTextField();
        r2s = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        r3h = new javax.swing.JTextField();
        r3m = new javax.swing.JTextField();
        r3s = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        r4h = new javax.swing.JTextField();
        r4m = new javax.swing.JTextField();
        r4s = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        r1h.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r1h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r1h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1hActionPerformed(evt);
            }
        });

        r1m.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r1m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r1m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1mActionPerformed(evt);
            }
        });

        r1s.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r1s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r1s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1sActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("México");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Horarios");

        r2h.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r2h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r2h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2hActionPerformed(evt);
            }
        });

        r2m.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r2m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r2m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2mActionPerformed(evt);
            }
        });

        r2s.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r2s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r2s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2sActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Amsterdam");

        r3h.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r3h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r3h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3hActionPerformed(evt);
            }
        });

        r3m.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r3m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r3m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3mActionPerformed(evt);
            }
        });

        r3s.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r3s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r3s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3sActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Kiev");

        r4h.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r4h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r4h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r4hActionPerformed(evt);
            }
        });

        r4m.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r4m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r4m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r4mActionPerformed(evt);
            }
        });

        r4s.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r4s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r4s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r4sActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Accra");

        jButton1.setText("Modificar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(r3h, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(r3m, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(r3s, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(r1h, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(r1m, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(r1s, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel4)))
                        .addGap(116, 116, 116)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(r4h, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(r4m, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(r4s, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton2)
                                            .addComponent(jLabel5))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(r2h, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2)
                                    .addComponent(r2m, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2)
                                    .addComponent(r2s, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jButton1)))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(r1h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r1m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r1s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(r2h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r2m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r2s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(11, 11, 11)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(r3h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(r4h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r4m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r4s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void r1hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r1hActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r1hActionPerformed

    private void r1mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r1mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r1mActionPerformed

    private void r1sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r1sActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r1sActionPerformed

    private void r2hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r2hActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r2hActionPerformed

    private void r2mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r2mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r2mActionPerformed

    private void r2sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r2sActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r2sActionPerformed

    private void r3hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r3hActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r3hActionPerformed

    private void r3mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r3mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r3mActionPerformed

    private void r3sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r3sActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r3sActionPerformed

    private void r4hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r4hActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r4hActionPerformed

    private void r4mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r4mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r4mActionPerformed

    private void r4sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r4sActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r4sActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        h1.interrupt();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        h2.interrupt();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        h3.interrupt();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        h4.interrupt();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Relojes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relojes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relojes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relojes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Relojes().setVisible(true);
            }
        });

    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField r1h;
    private javax.swing.JTextField r1m;
    private javax.swing.JTextField r1s;
    private javax.swing.JTextField r2h;
    private javax.swing.JTextField r2m;
    private javax.swing.JTextField r2s;
    private javax.swing.JTextField r3h;
    private javax.swing.JTextField r3m;
    private javax.swing.JTextField r3s;
    private javax.swing.JTextField r4h;
    private javax.swing.JTextField r4m;
    private javax.swing.JTextField r4s;
    // End of variables declaration//GEN-END:variables
}
