/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relojes_distribuidos;


import java.sql.Time;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import visual.Reloj_cliente;

/**
 *
 * @author Sadok
 */
public class Clock implements Runnable{

    /**
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * @param h the h to set
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * @return the m
     */
    public int getM() {
        return m;
    }

    /**
     * @param m the m to set
     */
    public void setM(int m) {
        this.m = m;
    }

    /**
     * @return the s
     */
    public int getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(int s) {
        this.s = s;
    }
    private int h;
    private int m;
    private int s;
    
    public Clock(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }
    
    public Time iniciaReloj(int h, int m, int s) throws ParseException{
//        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                try {//Para de tiempo de modificar las variables en caso de ser lento
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
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
//        }
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
            System.out.println(hora);
        }
    }
    
    @Override
    public void run(){
        iniciaConteo();
    }
}
