
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class gameloop extends JPanel implements KeyListener, Runnable
{
	int sizex = 1280;  //40
	int sizey = 768; //24 
	
	int threadTimer = 50; //thread timer of run in miliseconds		 ie. 10 = 10loops/sec
	
	int blocksize = 16;
	
	//graphics
	Image offScreen;
	Graphics d;
	Font myFont = new Font("Serif", Font.BOLD, 30);
	
	//our character
	hero Hero = new hero();
	
	//map
	//String seed = "99999999999999999999";
	//String seed = "99999999999999999999";
	//String seed = "11111111111111111111";
	//String seed = "00000000000000000000";
	//String seed = "47612379410411349183";
	//String seed = "90129389238226262677";
	//String seed = "89428923478942378942";
	//String seed = "21667342780990609969";
	String seed = "12345678901234567890";
	map mymap = new map(blocksize,200,200,1, seed); //sizex,sizey,mapNumber
	map currentmap = mymap;
	battleScene bs;
	boolean createdBS = false;
	
	
		   
	
	
	
	int motion = 0;
	
	gameloop()
	{
		addKeyListener(this); // this makes it possible to use keys, without this it wont work tis way of coding it
	}
	
	
	
	public void run() 
	{
		while(true)
		{
			//movement function is called every loop
			Hero.handleMovement(mymap);
			
			if(Hero.inBattle)
			{	
				//System.out.println("battle");
				if(createdBS == false)
				{
					System.out.println("creating bs");
					
					bs = new battleScene(Hero, currentmap.mlist.mlist.get(currentmap.randomMob()));
					createdBS = true;
				}
				bs.handleMenuMovement();
				if(bs.battleOver)
				{
					Hero.inBattle = false;
					createdBS = false;
				}
			}
			//System.out.println("hero x,y "+Hero.x+" "+Hero.y);
			
			
			
			//this creates the visible map to be drawn later
			mymap.makevisiblemap(Hero);
			
			
			//THREAD SPEED
			try 
			{ 
				Thread.sleep(threadTimer); //speed of movement
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();//tells you what happened
			}
			
			
			//repaint done here now!
			repaint();
			
			
		}
		
		
		
	}
	
	
	
	//I guess this is our loop
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		//draw the battle scene if were in a battle... else draw the main map
		if(Hero.inBattle)
		{
			//black background...
			g.setColor(Color.BLACK);
			for (int i=0; i<40;i++)
			{
				for (int j=0; j<24;j++)
				{
					g.fillRect(i*32, j*32, 32, 32);
				}
			}
			
			
			//hero
			g.setColor(Color.GREEN);
			g.fillRect(900,200,32,32);
			
			//monsters
			for(int i=0; i<bs.monster.length; i++)
			{
				if(bs.monster[i].hp> 0)
				{
					g.setColor(Color.RED);
					g.fillRect(300,100+i*100,32,32);
					g.setColor(Color.YELLOW);
					g.fillRect(308,108+bs.menuMonster*100,16,16);
				}
				
			}
			
			
			
			
			//draw menu area
			//int sizex = 1280;  //40
			//int sizey = 768; //24
			//maybe we do it block by block...
			for (int i=0; i<40;i++)
			{	
				for (int j=14; j<24;j++)
				{
					g.setColor(Color.BLUE);
					g.fillRect(i*32, j*32, 32, 32);
				}
			}
			
			//show the option menu but then it disapears after you pick an action
			if(bs.menuChoosed == false && bs.attacking ==false)
			{
				//attack menu
				for (int i=7; i<17;i++)
				{	
					for (int j=17; j<20;j++)
					{	
						g.setColor(Color.RED);
						if(bs.menu == 0)
						{
							if(j == 19)
							{
								g.setColor(Color.YELLOW);		
							}
						}
						g.fillRect(i*32, j*32, 32, 32);
					}
				}	
				//run menu
				for (int i=23; i<33;i++)
				{	
					for (int j=17; j<20;j++)
					{
						g.setColor(Color.RED);
						if(bs.menu == 1)
						{
							if(j == 19)
							{
								g.setColor(Color.YELLOW);
							}
						}
						g.fillRect(i*32, j*32, 32, 32);
					}
				}
				
				myFont = new Font("Serif", Font.BOLD, 30);
				g.setColor(Color.BLACK);
				g.setFont(myFont);
				g.drawString("Attack", 10*32, 18*32);
				g.drawString("Run", 27*32, 18*32);
			}
			else
			{
				myFont = new Font("Serif", Font.BOLD, 30);
				g.setColor(Color.RED);
				g.setFont(myFont);
				g.drawString("Choose a Target! (use arrow keys then press enter)", 10*32, 18*32);
				
				//display info about your character and what's happening
				g.drawString("Xp "+Hero.exp+"/"+Hero.calculateExpNeeded(), 10*32, 19*32);
				g.drawString("Att: "+Hero.att, 10*32, 20*32);
				g.drawString("Def: "+Hero.def, 10*32, 21*32);
				g.drawString("Spd: "+Hero.speed, 10*32, 22*32);
				
				
				
				//this does the attacking animation ... just temporary playing around with what it would be like
				if(bs.attacking)
				{
					
					if(motion <= 3)
					{
						//draw hero
						g.setColor(Color.GREEN);
						g.fillRect(900-motion*10,200,32,32);
						motion++;
					}
					if( motion >= 3 && motion <= 5)
					{
						for(int i=0; i < bs.monster.length; i++)
						{
							if(bs.monster[i].hp> 0)
							{
								g.setColor(Color.RED);
								g.fillRect(300+motion*10,100+i*100,32,32);
								
							}
						}
							motion++;
					}
					if(motion == 5)
					{
						bs.attacking = false;
						motion = 0;
					}
					
				}
	
			}
			
			
			
			
			
			//display hp --always
			for(int i=0; i<bs.monster.length; i++)
			{
				myFont = new Font("Serif", Font.BOLD, 20);
				g.setFont(myFont);
				g.setColor(Color.RED);
				if(bs.monster[i].hp > 0)
				{
					g.drawString("HP: "+bs.monster[i].hp, 300,98+i*100);
				}
				//g.fillRect(300,100+i*100,32,32);
				
				
				//display your own hp of course
				g.drawString("HP: "+Hero.hp, 900,198);
				//g.fillRect(900,200,32,32);
				
			}
			
			
		}
		else // else we draw main map cuz were not in a battle
		{
			for (int i=0; i<(int)sizex/blocksize;i++)
			{
				for (int j=0; j<(int)sizey/blocksize;j++)
				{
					if(mymap.shownmap[i][j] == 0)
					{
						g.setColor(Color.BLUE);
					}
					if(mymap.shownmap[i][j] == 1)
					{
						g.setColor(Color.DARK_GRAY);
					}
					if(mymap.shownmap[i][j] == 2)
					{
						g.setColor(Color.BLACK);
						
					}
					if(mymap.shownmap[i][j] == 3)
					{
						g.setColor(Color.GREEN);
					}
					g.fillRect(i*blocksize, j*blocksize, blocksize, blocksize);
					
				}
			}
		}
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	
	

	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 37) //37left,40down,clockwise
		{
			Hero.left = true;
			//System.out.println("pressed x--");
			
		}
		if(e.getKeyCode() == 38) 
		{
			Hero.up = true;	
		}
		if(e.getKeyCode() == 39) 
		{
			Hero.right = true;	
		}
		if(e.getKeyCode() == 40) 
		{
			Hero.down = true;	
		}
		if(e.getKeyCode() == 10)
		{
			Hero.enter = true;
		}
		
		
		
		
	}
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == 37)
		{
			Hero.left = false;
		}
		if(e.getKeyCode() == 38) 
		{
			Hero.up = false;
		}
		if(e.getKeyCode() == 39) 
		{
			Hero.right = false;
		}
		if(e.getKeyCode() == 40) 
		{
			Hero.down = false;
		}
		if(e.getKeyCode() == 10)
		{
			Hero.enter = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not used
		
	}






	

}
