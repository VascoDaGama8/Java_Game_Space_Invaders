import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Entities {
    static BulletsFac bulletsFac;
    int score;
    int x;
    int y;
    int w;
    int h;
    int inc_x;
    int inc_y;
    static int bullets;

    int time;
    List<Entities> alien = new ArrayList();
    List<Entities> bullet = new ArrayList();
    Image img;
    int nh;
    public void move(){
        x += inc_x;
        y += inc_y;
    }
    public void eqvival(Entities a, Entities b){
        if(a.x == b.x && a.y == b.y){
            a.nh --;

        }
    }
    public Rectangle getRec(){
        return new Rectangle(x,y, w, h);
    }
    public void hit(){
        nh--;
    }

}
