import javax.swing.*;
import java.util.Random;

public class Alien_K  extends Entities{
    Alien_K(int x){
        Random rand = new Random();
        inc_x = (int) (2 * Math.pow(-1,rand.nextInt(3)));
        inc_y = 2;
        w = 58;
        h = 48;
        nh = 1;
        time = 1;
        score = -50;
        k = true;
        this.x = x;
        y = 0;
        img = new ImageIcon("res/Alien_K.png").getImage();
    }
}
