package reloj_utc;


import java.sql.Time;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sadok
 */
public class Clock implements Runnable{
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
            System.out.println(hora);
        }
    }
    
    @Override
    public void run(){
        iniciaConteo();
    }
}
