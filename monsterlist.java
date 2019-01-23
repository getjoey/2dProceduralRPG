import java.util.ArrayList;

public class monsterlist 
{
	int mapNumber;
	ArrayList<monster[]> mlist = new ArrayList<monster[]>();
	
	monsterlist(int mapNumber)
	{
		this.mapNumber = mapNumber;
		createMonsterList(mapNumber); 
		
	}
	
	
	//hardcoded database for maps and which mobs happen there
	
	public void createMonsterList(int mapNumber)
	{
		if(mapNumber == 1)
		{
			monster[] mobs = new monster[3];
			mobs[0] = new monster("Bat1", 1);
			mobs[1] = new monster("Bat2", 1);
			mobs[2] = new monster("Bat3", 1);
			mlist.add(mobs);
			
			mobs = new monster[1];
			mobs[0] = new monster("Ogre", 2);
			mlist.add(mobs);
			
			
			mobs = new monster[2];
			mobs[0] = new monster("Goblin1", 3);
			mobs[1] = new monster("Goblin2", 3);
			mlist.add(mobs);
		}
	}

}
