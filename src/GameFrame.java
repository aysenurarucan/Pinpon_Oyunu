import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame
{

	GamePanel panel;
	
	// Yapıcı metot oluşturuldu.
	GameFrame()
	{
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Pong Game");
		
		// İnsanların bunu yeniden boyutlandırmasını istemiyoruz.
		this.setResizable(false);
		this.setBackground(Color.black);
		// Pencere kapatıldığında programın sonlandırılmasını sağlar. 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}