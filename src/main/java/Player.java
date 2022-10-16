import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Player extends Entities{
    Player(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x = (size.height-30)*6/16-35;
        y = size.height*47/64;
        w = 51;
        h = 58;
        img = new ImageIcon("res/player.png").getImage();
        bullets = 0;
        nh = 3;
        score = 0;
        bulletsFac = new BulletsFac(this);
    }
}
