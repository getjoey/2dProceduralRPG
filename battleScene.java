import java.util.Random;

public class battleScene {

	//graphic
 
	int menu = 0;
	int menuMonster = 0;
	boolean menuChoosed = false;
	hero hero;
	monster[] monster;
	monster[] monstertemp;
	boolean battleOver = false;
	Random ran = new Random();
	boolean attacking =false;
	
	
	int exp = 0;
	
	battleScene(hero hero, monster[] m) 
	{
		this.hero = hero;
		this.monster = new monster[m.length];
		
		//need to copy it cuz if we direct reference it itll destroy our mlist database
		for(int i=0; i<m.length; i++)
		{
			this.monster[i] = new monster(m[i]); // made a clone type constructor...
		}
		
		
	}
	
	
	public void handleMenuMovement()
	{
		if (menuChoosed)
		{
			//we are attacking
			if(menu == 0)
			{
				chooseTarget();
				attack(menuMonster);
			}
			//we are running
			if(menu == 1)
			{
				battleOver = true;
			}
		}
		else
		{
			chooseMenu();
		}
	}
	
	public void chooseMenu()
	{
		if (hero.left)
		{	
			if(menu>0)
			{
				menu--;
			}
				
			System.out.println("On Attack Menu");
			
			
		}
		
		if (hero.right)
		{
			
			if(menu <1)
			{
				menu++;
			}
			System.out.println("on RUN Menu");
			
			
		}
		
		if(hero.enter)
		{
			menuChoosed = true;
		}
	}
	
	
	
	
	public void chooseTarget()
	{
		if (hero.up)
		{
			if(menuMonster>0)
			{
				menuMonster--;
				System.out.println("on Mob: "+monster[menuMonster].name);
			}
		}
		if(hero.down)
		{
			if(menuMonster<monster.length-1)
			{
				menuMonster++;
				System.out.println("on Mob: "+monster[menuMonster].name);
			}
		}
	}
	
	public void attack(int x)
	{
		if(hero.enter && attacking == false)
		{
			System.out.println("ATTACKING monster :"+monster[x].name);
			monster[x].hp = monster[x].hp - calculateDamage(hero.att , monster[x].def);
			menuChoosed = false;
			attacking = true;
			
			if(monster[x].hp <=0)
			{
				System.out.println(monster[x].name+" is dead");
				
				//need to level up and shit ... you only level up if you kill everything
				exp+=monster[x].exp;
				
				
				
				//need to remove it somehow
				monstertemp = new monster[monster.length-1]; // new list 1 less
				int counter = 0;
				for (int i=0 ; i<monster.length; i++)
				{
					if(monster[i].hp > 0 )
					{
						monstertemp[counter] = monster[i];
						counter++;
					}
					
				}
				monster = monstertemp;
				
				if(monster.length == 0)
				{
					battleOver = true;
					System.out.println("battle is over");
					hero.addExp(exp);
					System.out.println("Gained exp : "+exp);
				}	
				
			}
			
			//monsters retaliate!
			for(int i=0; i < monster.length;i++)
			{
				hero.hp = hero.hp - calculateDamage(monster[x].att , hero.def);
			}
		}
		
		

	}
	
	public int calculateDamage(int a , int d)
	{
		int damage = 0;

		damage = 1+ (int) (a - d/3);   // def takes off 1/3 of def as damage so if def  = 18 you get -6dmg?
		
		if(damage<=0)
		{
			damage = 1+ (int) (a /3);  // min dmg is 1/3 of att
		}
		return damage;
	}
	
	
	
	
	
	
	
	

}
