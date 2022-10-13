import javax.swing.*;

public class Bullet extends Entities{
    Bullet(int x, int y){
        this.x = x + 20;
        this.y = y - 20;
        w = 7;
        h = 21;
        inc_y = -7;
        inc_x = 0;
        img = new ImageIcon("res/bullet.png").getImage();
    }

}
