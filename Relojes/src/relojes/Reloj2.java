package relojes;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Reloj2 implements Runnable{
    int segundos, minutos, horas;
    private boolean interruption = false;
    
    public void inicia(int h, int m, int s){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        segundos = calendar.get(Calendar.SECOND);
        minutos = calendar.get(Calendar.MINUTE);
        horas = calendar.get(Calendar.HOUR_OF_DAY);
        
        
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interruption = !interruption;
                System.err.println("Interrumpido");
                return;
            }
            if(segundos < 59){
                segundos++;
            }else if(minutos < 59){
                segundos = 0;
                minutos += 1;
            }else if(horas < 23){
                minutos = 0;
                segundos = 0;
                horas += 1;
            }else{
                minutos = 0;
                segundos = 0;
                horas = 0;
            }
            System.out.printf("Am: %02d:%02d:%02d\n", horas, minutos, segundos);
        }
    }
    @Override
    public void run(){
        while(true){
            if(Thread.interrupted()){
                interruption = !interruption;
                System.err.println("Interrumpido");
            }
            
            if(interruption){
                try {
                    Thread.sleep(10000);
                    interruption = !interruption;
                } catch (Exception e) {
                    interruption = !interruption;
                }
            }else{
                inicia(horas, minutos, segundos);
            }
        }
    }
}
