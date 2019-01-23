
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;


public class hero {
	 
	
	//rpg stat related
	int hp, maxhp;
	int mp, maxmp;
	int level;
	
	int att;
	int def;
	int speed;
	
	int exp;
	
	String name;
	

	//graphic and movement related
	int x;
	int y;
	int movementHolder = 1;
	int mov = movementHolder;
	
	int sizeofHeroX;
	int sizeofHeroY;
	int sizeofscreenX;
	int sizeofscreenY;
	
	boolean left;
	boolean right;
	boolean up;
	boolean down;
	boolean enter;
	
	//random
	Random ran = new Random();
	
	//batling
	boolean inBattle = false;
	
	
	hero()
	{
		this.name = "Hero";
		this.hp = 40;
		this.maxhp = 40;
		this.mp = 20;
		this.maxmp = 20;
		this.att = 7;
		this.def = 4;
		this.speed = 5;
		this.exp = 0;
		this.level = 1;
		
		//positonal
		this.x = 100;
		this.y = 100;
		left = false;
		right = false;
		up =false;
		down = false;
		enter = false;
		
		//mov and size
		this.sizeofHeroY = 32;  //1block = 32 pixels
		this.sizeofHeroX = 32;
		
		
	}
	
	
	
	public void handleMovement(map mymap)
	{
		if(inBattle == false)
		{
			//movement
			if(left)
			{
				//check collisions
				//if(mymap.map[this.x-mov][this.y] == 0 )
				{
					x=x-mov;
				}
				
				this.randomEncounterChecker();
			}
			else if(right)
			{	
				//if(mymap.map[this.x+mov][this.y] == 0)
				{
					x=x+mov;
				}
				this.randomEncounterChecker();
			}
			else if(up)
			{	
				//if(mymap.map[this.x][this.y-mov] == 0)
				{
					y=y-mov;
				}
				this.randomEncounterChecker();
			}
			else if(down)
			{
				//if(mymap.map[this.x][this.y+mov] == 0)
				{
					y=y+mov;
				}
				this.randomEncounterChecker();
			}
			
			/*
			//check out of bounds
				if (y > mymap.sizey)
				{
					y = mymap.sizey;
				}
				if (y < mymap.padding)
				{
					y = mymap.padding;
				}
				if (x > mymap.sizex)
				{
					x = mymap.sizex;
				}
				if (x < mymap.padding)
				{
					x = mymap.padding;
				}
			*/	
		}
		
		
	}
	
	
	public void addExp( int xp)
	{
		this.exp += xp;
		
		//check for level up;
		while (exp >= calculateExpNeeded())
		{
			System.out.println("level up");
			this.exp = this.exp - calculateExpNeeded();
			
			
			this.maxhp+= 5;
			this.maxmp+= 3;
			
			this.hp = this.maxhp;
			this.mp = this.maxmp;
			
			this.att+= 2;
			this.def+= 2;
			this.speed+= 1;
			this.level ++;
		}
		
	}
	
	public int calculateExpNeeded()
	{
		int expNeeded = (int) (20 + ( (this.level*30) * this.level / 5 )) ;
		return expNeeded;
	}
	
	public void randomEncounterChecker()
	{

		if (ran.nextInt(10000) ==0)
		{
			inBattle = true;
			System.out.println("battle");
		}
		
	}
	
	
	

}
