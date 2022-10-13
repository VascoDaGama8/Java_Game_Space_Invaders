import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class Space extends JPanel implements ActionListener{

    Timer mainTimer = new Timer(60, this);
    public static enum STATES{MainMenu, EndMenu, Play, ScoreMenu, Pause}
    public static STATES state = STATES.MainMenu;

    public static int mouseX;
    public static int mouseY;

    public static int bestScore = 0;
    FileWriter fileWriter;
    FileReader fileReader = new FileReader("res/score.txt");


    Image img1 = new ImageIcon("res/space.jpg").getImage();
    Image img2 = new ImageIcon("res/planet.png").getImage();
    static Entities p = new Player();
    static AlienFac alienFac = new AlienFac(p);
    Menu menu = new Menu();
    EndMenu emenu = new EndMenu();

    Pause pause = new Pause();

    public Space() throws IOException {
        mainTimer.start();
        alienFac.start();
        addKeyListener(new Listeners());
        addMouseMotionListener(new Listeners());
        addMouseListener(new Listeners());
        setFocusable(true);
        bestScore = fileReader.read();
    }

    public void paint(Graphics g ) {
        g = (Graphics2D) g;
        if (state.equals(STATES.MainMenu)) {
            try {
                menu.draw((Graphics2D) g);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            }
        }
        if (state.equals(STATES.EndMenu)) {
            try {
                emenu.draw((Graphics2D) g);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            }
        }
        if (state.equals(STATES.Pause)) {
            alienFac.pus = true;
            try {
                pause.draw((Graphics2D) g);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            }
        }

        if (state.equals(STATES.Play)) {
            g.drawImage(img1, 0, 0, 600, 1000, null);
            g.drawImage(img2, 0, 370, 600, 500, null);
            if (p.x <= 0 || p.x >= 530) {
                p.inc_x = 0;
            }
            g.drawImage(p.img, p.x, p.y, 50, 50, null);
            Iterator<Entities> i = p.alien.iterator();
            while (i.hasNext()) {
                Entities e = i.next();
                e.time--;
                if (e.getRec().intersects(p.getRec()) && e.inc_y != 0){
                    p.nh--;
                    e.inc_x = 0;
                    e.inc_y = 0;
                    e.img = new ImageIcon("res/boom.png").getImage();
                    e.time = 3;
                    if (p.nh == 0) {
                        p.img = new ImageIcon("res/boom.png").getImage();
                        state = STATES.EndMenu;
                        if (p.score > bestScore) {
                            bestScore = p.score;
                        }
                        p.score = 0;
                        try {
                            fileWriter = new FileWriter("res/score.txt");
                            fileWriter.write(String.valueOf(bestScore));
                            fileWriter.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                if (e.inc_x == 0 && e.score != 0) {
                    Font font1;
                    try {
                        font1 = Font.createFont(Font.TRUETYPE_FONT, new File("res/pico-8.ttf")).deriveFont(10f);
                    } catch (FontFormatException t) {
                        throw new RuntimeException(t);
                    } catch (IOException t) {
                        throw new RuntimeException(t);
                    }
                    g.setFont(font1);
                    g.setColor(Color.WHITE);
                    g.drawString(String.valueOf(e.score), e.x + 15, e.y - 5);
                    if (e.time == 0) {
                        i.remove();
                    }
                }
                if (e.time == 0 && e.score == 0) {
                    i.remove();
                }
                if (e.time == 0 && e.inc_x != 0) {
                    Random rand = new Random();
                    e.inc_x *= (int) (Math.pow(-1, rand.nextInt(3)));
                    e.time = 15;
                }
                if (e.x >= 535 || e.x <= 0) {
                    e.inc_x *= -1;
                }
                if (e.y > 650 && e.inc_y != 0) {
                    state = STATES.EndMenu;
                    if (p.score > bestScore) {
                        bestScore = p.score;
                    }
                    p.score = 0;
                    try {
                        fileWriter = new FileWriter("res/score.txt");
                        fileWriter.write(String.valueOf(bestScore));
                        fileWriter.flush();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                e.move();
                g.drawImage(e.img, e.x, e.y, e.w, e.h, null);
            }
            i = p.bullet.iterator();
            while (i.hasNext()) {
                Entities e = i.next();
                if (e.getRec().intersects(p.getRec()) && e.inc_y > 0) {
                    p.nh--;
                    e.inc_x = 0;
                    e.inc_y = 0;
                    e.img = new ImageIcon("res/boom.png").getImage();
                    e.time = 3;
                    e.w = 50;
                    e.h = 50;
                    if(p.nh != 0) {
                        p.alien.add(e);
                    }
                    i.remove();
                    if (p.nh == 0) {
                        p.img = new ImageIcon("res/boom.png").getImage();
                        state = STATES.EndMenu;
                        if (p.score > bestScore) {
                            bestScore = p.score;
                        }
                        p.score = 0;
                        try {
                            fileWriter = new FileWriter("res/score.txt");
                            fileWriter.write(String.valueOf(bestScore));
                            fileWriter.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                if (e.y < 0 || e.y > 820) {
                    i.remove();
                } else {
                    e.move();
                    g.drawImage(e.img, e.x, e.y, e.w, e.h, null);
                }
            }
            for (int h = 0; h < p.alien.size(); h++) {
                for (int j = 0; j < p.bullet.size(); j++) {
                    if (p.alien.get(h).getRec().intersects(p.bullet.get(j).getRec()) && p.bullet.get(j).inc_y < 0) {
                        if (p.alien.get(h).nh == 2) {
                            p.bullet.get(j).inc_x = 0;
                            p.bullet.get(j).inc_y = 0;
                            p.bullet.get(j).img = new ImageIcon("res/boom.png").getImage();
                            p.bullet.get(j).time = 3;
                            p.bullet.get(j).w = 50;
                            p.bullet.get(j).h = 50;
                            p.alien.add(0, p.bullet.get(j));
                            p.alien.get(h + 1).img = new ImageIcon("res/dem.png").getImage();
                            p.alien.get(h + 1).hit();
                        } else {
                            p.alien.get(h).hit();
                        }
                        p.bullet.remove(j);

                        if (p.alien.get(h).nh == 0) {
                            p.score += p.alien.get(h).score;
                            p.alien.get(h).inc_x = 0;
                            p.alien.get(h).inc_y = 0;
                            p.alien.get(h).img = new ImageIcon("res/boom.png").getImage();
                            p.alien.get(h).time = 8;
                        }

                        break;
                    }
                }
            }
            Font font1;
            try {
                font1 = Font.createFont(Font.TRUETYPE_FONT, new File("res/pico-8.ttf")).deriveFont(10f);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.setFont(font1);
            g.setColor(Color.WHITE);
            g.drawString("Health: ", 450, 20);
            g.drawString("Score: " + p.score, 450, 40);
            for (int x = 0; x < p.nh; x++) {
                g.drawImage(new ImageIcon("res/heart.png").getImage(), 520 + x * 18, 9, 16, 13, null);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (state.equals(STATES.MainMenu)){
            repaint();
            for(int i = 1; i < menu.n+1; i++) {
                if (Space.mouseX > menu.getX() && Space.mouseX < menu.getX() + menu.getW() &&
                        Space.mouseY > (menu.getY() + 140) * i + 50 && Space.mouseY < (menu.getY() + 140) * i + 50 + menu.getH()) {
                    menu.color[i-1] = Color.WHITE;
                    if(Menu.click == true && i == 1){
                        state = STATES.Play;
                        p.img = new ImageIcon("res/player.png").getImage();
                        p.nh = 3;
                        try {
                            fileReader = new FileReader("res/score.txt");
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        p.alien.clear();
                        p.bullet.clear();
                        Menu.click = false;
                    }
                    if(Menu.click == true && i == 3){
                        System.exit(1);
                    }
                } else {
                    menu.color[i-1] = Color.BLACK;
                }
            }
        }
        if(state.equals(STATES.EndMenu)){
            repaint();
            for(int i = 1; i < emenu.n+1; i++) {
                if (Space.mouseX > emenu.getX() && Space.mouseX < emenu.getX() + emenu.getW() &&
                        Space.mouseY > (emenu.getY() + 140) * i + 50 && Space.mouseY < (emenu.getY() + 140) * i + 50 + emenu.getH()) {
                    emenu.color[i-1] = Color.WHITE;
                    if(EndMenu.click == true && i == 1){
                        state = STATES.Play;
                        p.img = new ImageIcon("res/player.png").getImage();
                        p.nh = 3;
                        try {
                            fileReader = new FileReader("res/score.txt");
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        p.alien.clear();
                        p.bullet.clear();
                        p.x = 265;
                        p.y = 650;
                        EndMenu.click = false;
                    }
                    if(EndMenu.click == true && i == 2){
                        state = STATES.MainMenu;
                        p.alien.clear();
                        p.bullet.clear();
                        p.x = 215;
                        p.y = 650;
                        EndMenu.click = false;
                    }
                } else {
                    emenu.color[i-1] = Color.BLACK;
                }
            }
        }
        if(state.equals(STATES.Pause)){
            repaint();
            for(int i = 1; i < pause.n+1; i++) {
                if (Space.mouseX > pause.getX() && Space.mouseX < pause.getX() + pause.getW() &&
                        Space.mouseY > (pause.getY() + 140) * i + 50 && Space.mouseY < (pause.getY() + 140) * i + 50 + pause.getH()) {
                    pause.color[i-1] = Color.WHITE;
                    if(Pause.click == true && i == 1){
                        state = STATES.Play;
                        p.img = new ImageIcon("res/player.png").getImage();
                        try {
                            fileReader = new FileReader("res/score.txt");
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        alienFac = new AlienFac(p);
                        alienFac.start();
                        Pause.click = false;
                    }
                    if(Pause.click == true && i == 2){
                        state = STATES.MainMenu;
                        p.alien.clear();
                        p.bullet.clear();
                        p.x = 215;
                        p.y = 650;
                        Pause.click = false;
                    }
                } else {
                    pause.color[i-1] = Color.BLACK;
                }
            }
        }
        if(state.equals(STATES.Play)){
            p.move();
            repaint();
        }

    }

}
