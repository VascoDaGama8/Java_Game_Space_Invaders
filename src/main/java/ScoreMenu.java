import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScoreMenu {
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
    Color[] color = new Color[1];
    String[] list = new String[1];

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
    public ScoreMenu(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x = size.height*1/8;
        y = 0;
        w = size.height*1/2;
        h = size.height*104/800;
        n = 1;
        list[0] = "Exit";
        color[0] = Color.BLACK;
    }

    public void draw(Graphics2D g) throws IOException, FontFormatException {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        g.drawImage(img1, 0, 0, (size.height-30)*6/8, size.height+170, null);
        g.drawImage(img2, 0, (size.height-30)*3/8, (size.height-30)*6/8, (size.height-30)*6/9, null);
        g.drawImage(img3, (size.height-30)*6/16-35, size.height*47/64, 50, 50, null);
        Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File("res/pico-8.ttf")).deriveFont(36f);
        g.setFont(font1);
        long length = (int) g.getFontMetrics().getStringBounds("Space Invaders" , g).getWidth();
        g.setColor(Color.RED);
        g.drawString("The best score" , (x+w/2) - (int) (length / 2) - 2, 50 + (h/3)*2);
        g.setColor(Color.YELLOW);
        g.drawString("The best score" , (x+w/2) - (int) (length / 2) + 2, 50 + (h/3)*2);
        length = (int) g.getFontMetrics().getStringBounds(Integer.toString(Space.bestScore) , g).getWidth();
        g.setColor(Color.RED);
        g.drawString(Integer.toString(Space.bestScore)  , (x+w/2) - (int) (length / 2) - 2, (y+size.height*140/800) + 50 + (h/3)*2);
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(Space.bestScore) , (x+w/2) - (int) (length / 2) + 2, (y+size.height*140/800) + 50 + (h/3)*2);
        for(int i = 2; i<n+2; i++){
            g.drawImage(img, x, (y+size.height*140/800)*i + 50, w, h, null);
            g.setColor(color[i-2]);
            length = (int) g.getFontMetrics().getStringBounds(list[i-2], g).getWidth();
            g.drawString(list[i-2], (x+w/2) - (int) (length / 2), (y+size.height*140/800)*i + 50 + (h/3)*2);
        }
    }
}
