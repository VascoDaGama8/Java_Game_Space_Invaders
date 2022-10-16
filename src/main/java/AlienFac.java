import java.awt.*;
import java.util.Random;

public class AlienFac extends Thread{
    public boolean pus = false;
    Entities p;
    AlienFac(Entities p){
        this.p = p;
    }
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public void run(){
        while(!pus){
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000) + 3000);
                int i = rand.nextInt(4);
                if (i==0){
                    p.alien.add(new Alien_A(rand.nextInt((size.height-30)*6/8-50) + 50));
                }
                if (i==1){
                    p.alien.add(new Alien_B(rand.nextInt((size.height-30)*6/8-50) + 50));
                }
                if (i==2){
                    p.alien.add(new Alien_S(rand.nextInt((size.height-30)*6/8-50) + 50));
                }
                if (i==3){
                    p.alien.add(new Alien_F(rand.nextInt((size.height-30)*6/8-50) + 50));
                }


            } catch (InterruptedException e) {
                System.out.println("");
            }
        }
        this.interrupt();
    }
}
