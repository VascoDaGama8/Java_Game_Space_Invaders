import java.awt.event.KeyEvent;

class BulletsFac extends Thread{
    Entities p;
    BulletsFac(Entities p){
        this.p = p;
    }
    public void run(){
        try {
            while(p.bullets != 0) {
                Thread.sleep(1000);
                p.bullet.add(new Bullet(p.x, p.y));
                p.bullets--;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.interrupt();
    }
}