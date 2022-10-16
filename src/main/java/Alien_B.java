import javax.swing.*;
import java.util.Random;

public class Alien_B extends Entities{
    Alien_B(int x){
        Random rand = new Random();
        inc_x = (int) (2 * Math.pow(-1,rand.nextInt(3)));
        inc_y = 2;
        w = 48;
        h = 50;
        nh = 1;
        score = 80;
        time = 5;
        k = false;
        this.x = x;
        y = 0;
        img = new ImageIcon("res/Alien_B.png").getImage();
    }
    public void move(){
        x += inc_x;
        y += inc_y;
        Random rand = new Random();
        int i = rand.nextInt(2000);
        if(i < 30) {
            Bullet b = new Bullet(x, y+h);
            b.inc_y = 7;
            b.img = new ImageIcon("res/bullet_a.png").getImage();
            Space.p.bullet.add(b);
        }
    }
}
