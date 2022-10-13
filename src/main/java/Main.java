import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame("Space Invaders");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 820);
        f.add(new Space());
        f.setVisible(true);

    }



}
