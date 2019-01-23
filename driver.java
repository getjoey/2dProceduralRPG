
import java.awt.Color;

import javax.swing.JFrame;

public class driver {

	public static void main(String[] args) 
	{
		 
		
		gameloop game = new gameloop();
		game.setBackground(Color.BLACK);
		game.setFocusable(true);
		
		
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setSize(game.sizex, game.sizey);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		game.run();  //this runs the runnable thread in our gameloop.java class
		
	}

}
