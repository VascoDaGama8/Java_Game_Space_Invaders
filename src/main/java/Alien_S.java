import javax.swing.*;
import java.util.Random;

public class Alien_S extends Entities{
    Alien_S(int x){
        Random rand = new Random();
        inc_x = (int) (1 * Math.pow(-1,rand.nextInt(3)));
        inc_y = 1;
        w = 58;
        h = 50;
        nh = 2;
        score = 100;
        time = 5;
        k = false;
        this.x = x;
        y = 0;
        img = new ImageIcon("res/Alien_S.png").getImage();
    }
}
