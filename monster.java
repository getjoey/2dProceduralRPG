

public class monster {
	
	int hp;
	int mp;
	int level;
	
	int att;
	int def; 
	int speed;
	
	int exp;
	int mobNum;
	
	String name;
	
	monster(String name, int level)
	{
		this.level = level;
		createStats(this.level);
		
		this.name = name;
	}
	
	monster(monster m)
	{
		this.hp = m.hp;
		this.mp = m.mp;
		this.level = m.level;
		this.att = m.att;
		this.def = m.def;
		this.speed = m.def;
		
		this.exp = m.exp;
		this.name = m.name;
		
		createStats(this.level);
	}
	
	public void createStats(int mob)
	{
		
		if(mob == 1) //bat
		{
			hp = 15;
			mp = 10;
			
			att = 2;
			def = 1;
			speed = 3;
				
			exp = 10;
		}
		
		if(mob == 2) // ogre
		{
			hp = 25;
			mp = 10;
	
			att = 5;
			def = 3;
			speed = 1;
		
			exp = 40;		
		}
		
		if(mob == 3) // Goblin
		{
			hp = 15;
			mp = 10;
	
			att = 3;
			def = 1;
			speed = 2;
		
			exp = 20;		
		}
		
	}
	
	
	

}
