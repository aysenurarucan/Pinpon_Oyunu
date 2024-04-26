import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {

    int id;
    int yVelocity;
    int speed = 5; // Hızı düşürdüm, istediğiniz gibi ayarlayabilirsiniz.

    // Yapıcı metot oluşturuldu.
    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        try {
            switch (id) {
                case 1:
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        setYDirection(-speed);
                    } else if (e.getKeyCode() == KeyEvent.VK_S) {
                        setYDirection(speed);
                    } else if (Character.isDigit(e.getKeyChar())) {
                        throw new IllegalArgumentException("Lütfen geçerli bir tuşa basınız!!");
                    }
                    break;
                case 2:
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        setYDirection(-speed);
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        setYDirection(speed);
                    }
                    break;
            }
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                }
                break;
        }
    }

    // Raketler sadece y ekseninde hareket edeceği için y yönünü belirledik.
    
    //Overloading
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void setYDirection(boolean moveUp) {
        if (moveUp) {
            yVelocity = -speed;
        } else {
            yVelocity = speed;
        }
    }
     
    
    public void move() {
        y = y + yVelocity;
    }
    
    
    

    public void draw(Graphics g) {
        try {
            if (id == 1)
                g.setColor(Color.blue);
            else
                g.setColor(Color.red);

            g.fillRect(x, y, width, height);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
}

