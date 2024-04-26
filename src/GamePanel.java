import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable 
{

	static final int GAME_WIDTH = 1000; // Ekran eni
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); // Ekran boyu
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20; // Topun çapı
	static final int PADDLE_WIDTH = 25; // Raket genişliği
	static final int PADDLE_HEIGHT = 100; // Raket yüksekliği
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	
	
	String name1;
	String name2;
	
	
	// Yapıcı metot oluşturuldu.
	GamePanel() {
        try {
            boolean isValidInput = false;

            while (!isValidInput) {
                name1 = JOptionPane.showInputDialog("Player-1 Name: ");
                if (isValidName(name1)) {
                    isValidInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Geçerli bir isim giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }

            isValidInput = false;

            while (!isValidInput) {
                name2 = JOptionPane.showInputDialog("Player-2 Name: ");
                if (isValidName(name2)) {
                    isValidInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Geçerli bir isim giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }

            newPaddles(); // Raketleri yenile
            newBall();    // Topu yenile
            score = new Score(GAME_WIDTH, GAME_HEIGHT);

            this.setFocusable(true);
            this.addKeyListener(new AL());
            this.setPreferredSize(SCREEN_SIZE);

            gameThread = new Thread(this);
            gameThread.start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Bir hata oluştu. Program sonlandırılıyor.", "Hata", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Hata durumunda programı sonlandır
        }
    }
	
	//Overloading
	private boolean isValidName(String str) {
        return str.matches("[a-zA-Z]+");
    }

	
	private boolean isValidName(String str, int minLength, int maxLength) {
        return str.matches("[a-zA-Z]+") && str.length() >= minLength && str.length() <= maxLength;
    }

	
	//Overloading
		private boolean isTeamName(String str) {
	        return str.matches("[a-zA-Z]+");
	    }

		
		private boolean isTeamName(String str, int minLength, int maxLength) {
	        return str.matches("[a-zA-Z]+") && str.length() >= minLength && str.length() <= maxLength;
	    }

	public void newBall() 
	{
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
	}
	
	public void newPaddles() 
	{
		paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	
	public void paint(Graphics g) 
	{
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	
	public void draw(Graphics g) 
	{
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		Toolkit.getDefaultToolkit().sync(); 
	}
	
	public void move() 
	{
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	
	public void checkCollision() 
	{
		// Topu üst ve alt pencere kenarlarından sektir.
		if(ball.y <=0) 
		{
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) 
		{
			ball.setYDirection(-ball.yVelocity);
		}
		
		// Raketlerden top sektirmek.
		if(ball.intersects(paddle1)) 
		{
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; 
			
			if(ball.yVelocity>0)
				ball.yVelocity++; 
			else
				ball.yVelocity--;
			
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		if(ball.intersects(paddle2)) 
		{
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; 
			
			if(ball.yVelocity>0)
				ball.yVelocity++; 
			else
				ball.yVelocity--;
			
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		// Raketleri pencere kenarlarında durdurur
		if(paddle1.y<=0)
			paddle1.y=0;
		if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
		if(paddle2.y<=0)
			paddle2.y=0;
		if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
		
		// Bir oyuncuya 1 puan verin ve yeni raketler ve top oluşturun.
		if(ball.x <= 0) 
		{
			 newPaddles();
			 newBall();
				
				if(score.player2>=0 && score.player2<10) 
				{
					score.player2++;
					System.out.println("Player 2: "+score.player2);
					
					if(score.player2==10) 
					{
						System.out.println("!! Kazanan Kırmızı Raket !!");
						JOptionPane.showMessageDialog(null,"!! Kazanan " + name2 + " !!");
						
						while(true) 
						{
							if(score.player2>=10)
								break;
						}
						while(true) 
						{
							if(score.player1>=10)
								break;
						}
					}
				}
				
				
		 }
		 if(ball.x >= GAME_WIDTH-BALL_DIAMETER) 
		 {
			newPaddles();
			newBall();
			
				if(score.player1>=0 && score.player1<10) 
				{
					score.player1++;
					System.out.println("Player 1: "+score.player1);
					
					if(score.player1==10) 
					{
						System.out.println("!! Kazanan Mavi Raket !!");
						JOptionPane.showMessageDialog(null,"!! Kazanan " + name1 + " !!");
						
						while(true) 
						{
							if(score.player1>=10)
								break;
						}
						
						while(true) 
						{
							if(score.player2>=10)
								break;
						}
						
					}
				}
				
		}
	}
	public void run() {
	    // Oyun döngüsü
	    long lastTime = System.nanoTime();
	    double amountOfTicks = 60.0;
	    double ns = 1000000000 / amountOfTicks;
	    double delta = 0;

	    while (true) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;

	        try {
	            if (delta >= 1) {
	                move();
	                checkCollision();
	                repaint();
	                delta--;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Hata ile başa çıkmak için gereken işlemleri buraya ekleyebilirsiniz
	        }
	    }
	}

	
	// İç içe class oluşturuldu.
	public class AL extends KeyAdapter
	{
		public void keyPressed(KeyEvent e) 
		{
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) 
		{
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}
