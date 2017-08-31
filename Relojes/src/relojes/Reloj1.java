package relojes;

import java.time.LocalDateTime;


public class Reloj1 implements Runnable{
    int segundos = LocalDateTime.now().getSecond();
    int minutos = LocalDateTime.now().getMinute();
    int horas = LocalDateTime.now().getHour();
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
            System.out.printf("Mx: %02d:%02d:%02d\n", h, m, s);
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
