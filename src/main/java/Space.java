import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

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
    ScoreMenu scoremenu = new ScoreMenu();

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
        if (state.equals(STATES.ScoreMenu)) {
            try {
                scoremenu.draw((Graphics2D) g);
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
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            g.drawImage(img1, 0, 0, (size.height-30)*6/8, size.height+170, null);
            g.drawImage(img2, 0, (size.height-30)*3/8, (size.height-30)*6/8, (size.height-30)*6/9, null);
            if (p.x <= 0 || p.x >= (size.height-30)*6/8-70) {
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
                            fileWriter.write(bestScore);
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
                if (e.x >= (size.height-30)*6/8-65 || e.x <= 0) {
                    e.inc_x *= -1;
                }
                if (e.y > size.height*47/64 + 50 && e.inc_y != 0) {
                    if(e.k == false) {
                        state = STATES.EndMenu;
                        if (p.score > bestScore) {
                            bestScore = p.score;
                        }
                        p.score = 0;
                        try {
                            fileWriter = new FileWriter("res/score.txt");
                            fileWriter.write(bestScore);
                            fileWriter.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(e.k == true){
                        p.score -= e.score;
                        i.remove();
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
                            fileWriter.write(bestScore);
                            fileWriter.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                if (e.y < 0 || e.y > size.height) {
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
            g.drawString("Health: ", (size.height-30)*11/20, 20);
            g.drawString("Score: " + p.score, (size.height-30)*11/20, 40);
            for (int x = 0; x < p.nh; x++) {
                g.drawImage(new ImageIcon("res/heart.png").getImage(), (size.height-30)*11/20+70 + x * 18, 9, 16, 13, null);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (state.equals(STATES.MainMenu)){
            repaint();
            for(int i = 1; i < menu.n+1; i++) {
                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                if (Space.mouseX > menu.getX() && Space.mouseX < menu.getX() + menu.getW() &&
                        Space.mouseY > (menu.getY() + size.height*140/800) * i + 50 && Space.mouseY < (menu.getY() + size.height*140/800) * i + 50 + menu.getH()) {
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
                    if(Menu.click == true && i == 2){
                        state = STATES.ScoreMenu;
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
        if(state.equals(STATES.ScoreMenu)){
            repaint();
            for(int i = 2; i < scoremenu.n+2; i++) {
                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                if (Space.mouseX > scoremenu.getX() && Space.mouseX < scoremenu.getX() + scoremenu.getW() &&
                        Space.mouseY > (scoremenu.getY() + size.height*140/800) * i + 50 && Space.mouseY < (scoremenu.getY() + size.height*140/800) * i + 50 + scoremenu.getH()) {
                    scoremenu.color[i - 2] = Color.WHITE;
                    if (ScoreMenu.click == true && i == 2) {
                        state = STATES.MainMenu;
                        ScoreMenu.click = false;
                    }
                }
                else {
                        scoremenu.color[i-2] = Color.BLACK;
                }
            }
        }
        if(state.equals(STATES.EndMenu)){
            repaint();
            for(int i = 1; i < emenu.n+1; i++) {
                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                if (Space.mouseX > emenu.getX() && Space.mouseX < emenu.getX() + emenu.getW() &&
                        Space.mouseY > (emenu.getY() + size.height*140/800) * i + 50 && Space.mouseY < (emenu.getY() + size.height*140/800) * i + 50 + emenu.getH()) {
                    emenu.color[i - 1] = Color.WHITE;
                    if (EndMenu.click == true && i == 1) {
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
                        p.x = (size.height-30)*6/16-35;
                        p.y = size.height*47/64;
                        EndMenu.click = false;
                    }
                    if (EndMenu.click == true && i == 2) {
                        state = STATES.MainMenu;
                        p.alien.clear();
                        p.bullet.clear();
                        p.x = (size.height-30)*6/16-35;
                        p.y = size.height*47/64;
                        EndMenu.click = false;
                    }
                }
                else {
                    emenu.color[i-1] = Color.BLACK;
                }
            }
        }
        if(state.equals(STATES.Pause)){
            repaint();
            for(int i = 1; i < pause.n+1; i++) {
                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                if (Space.mouseX > pause.getX() && Space.mouseX < pause.getX() + pause.getW() &&
                        Space.mouseY > (pause.getY() + size.height*140/800) * i + 50 && Space.mouseY < (pause.getY() + size.height*140/800) * i + 50 + pause.getH()) {
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
                        p.x = (size.height-30)*6/16-35;
                        p.y = size.height*47/64;
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
