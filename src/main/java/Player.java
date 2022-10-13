import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Player extends Entities{
    Player(){
        x = 265;
        y = 650;
        w = 51;
        h = 58;
        img = new ImageIcon("res/player.png").getImage();
        bullets = 0;
        nh = 3;
        score = 0;
        bulletsFac = new BulletsFac(this);
    }
}
