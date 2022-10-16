import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame("Space Invaders");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize((size.height-30)*6/8, size.height-30);
        f.add(new Space());
        f.setVisible(true);

    }



}
