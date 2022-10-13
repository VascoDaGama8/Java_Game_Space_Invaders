import javax.swing.*;
import java.util.Random;

public class Alien_F extends Entities{
    Alien_F(int x){
        Random rand = new Random();
        inc_x = (int) (3 * Math.pow(-1,rand.nextInt(3)));
        inc_y = 3;
        w = 50;
        h = 50;
        nh = 1;
        score = 70;
        time = 5;
        this.x = x;
        y = 0;
        img = new ImageIcon("res/Alien_F.png").getImage();
    }
}
