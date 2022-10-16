import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Pause {
    public static boolean click = false;
    Image img = new ImageIcon("res/btn.png").getImage();
    Image img1 = new ImageIcon("res/space.jpg").getImage();
    Image img2 = new ImageIcon("res/planet.png").getImage();
    Image img3 = new ImageIcon("res/player.png").getImage();
    private int x;
    private int y;
    private int w;
    private int h;
    int n;
    Color[] color = new Color[3];
    String[] list = new String[3];

    public int getX(){

        return x;
    }
    public int getY(){
        return y;
    }
    public int getW(){
        return w;
    }
    public int getH(){
        return h;
    }
    public Pause(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x = size.height*1/8;
        y = 0;
        w = size.height*1/2;
        h = size.height*104/800;
        n = 2;
        list[0] = "Continue";
        list[1] = "Exit";
        color[0] = Color.BLACK;
        color[1] = Color.BLACK;
    }

    public void draw(Graphics2D g) throws IOException, FontFormatException {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        g.drawImage(img1, 0, 0, (size.height-30)*6/8, size.height+170, null);
        g.drawImage(img2, 0, (size.height-30)*3/8, (size.height-30)*6/8, (size.height-30)*6/9, null);
        g.drawImage(img3, Space.p.x, Space.p.y, 50, 50, null);
        Iterator<Entities> j = Space.p.alien.iterator();
        while (j.hasNext()) {
            Entities e = j.next();
            if(e.y != 0) {
                g.drawImage(e.img, e.x, e.y, e.w, e.h, null);
            }
        }
        j = Space.p.bullet.iterator();
        while (j.hasNext()) {
            Entities e = j.next();
            if(e.y != 0) {
                g.drawImage(e.img, e.x, e.y, e.w, e.h, null);
            }
        }
        Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File("res/pico-8.ttf")).deriveFont(36f);
        g.setFont(font1);
        long length = (int) g.getFontMetrics().getStringBounds("Pause" , g).getWidth();
        g.setColor(Color.RED);
        g.drawString("Pause" , (x+w/2) - (int) (length / 2) - 2, 50 + (h/3)*2);
        g.setColor(Color.YELLOW);
        g.drawString("Pause" , (x+w/2) - (int) (length / 2) + 2, 50 + (h/3)*2);
        for(int i = 1; i<n+1; i++){
            g.drawImage(img, x, (y+size.height*140/800)*i + 50, w, h, null);
            g.setColor(color[i-1]);
            length = (int) g.getFontMetrics().getStringBounds(list[i-1], g).getWidth();
            g.drawString(list[i-1], (x+w/2) - (int) (length / 2), (y+size.height*140/800)*i + 50 + (h/3)*2);
        }
    }
}
