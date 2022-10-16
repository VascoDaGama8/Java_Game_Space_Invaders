import java.awt.*;
import java.awt.event.*;

public class Listeners implements MouseListener, KeyListener, MouseMotionListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        if (key == KeyEvent.VK_RIGHT && Space.p.x <= (size.height-30)*6/8-70){
            Space.p.inc_x = 5;
        }
        if (key == KeyEvent.VK_LEFT && Space.p.x >= 0){
            Space.p.inc_x = -5;
        }
        if(key == KeyEvent.VK_ESCAPE && Space.state.equals(Space.STATES.Play)){
            Space.state = Space.STATES.Pause;
            Space.alienFac.interrupt();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT){
            Space.p.inc_x = 0;
        }
        if (key == KeyEvent.VK_SPACE && !Space.p.bulletsFac.isAlive()){
            Space.p.bullets++;
            Space.p.bulletsFac = new BulletsFac(Space.p);
            Space.p.bulletsFac.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 ){
            if(Space.state == Space.STATES.MainMenu){
                Menu.click = true;
            }
            if(Space.state == Space.STATES.EndMenu){
                EndMenu.click = true;
            }
            if(Space.state == Space.STATES.Pause){
                Pause.click = true;
            }
            if(Space.state == Space.STATES.ScoreMenu){
                ScoreMenu.click = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 ){
            if(Space.state == Space.STATES.MainMenu){
                Menu.click = false;
            }
            if(Space.state == Space.STATES.EndMenu){
                EndMenu.click = false;
            }
            if(Space.state == Space.STATES.Pause){
                Pause.click = false;
            }
            if(Space.state == Space.STATES.ScoreMenu){
                ScoreMenu.click = false;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Space.mouseY = e.getY();
        Space.mouseX = e.getX();
    }
}
