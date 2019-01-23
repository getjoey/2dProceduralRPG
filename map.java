
import java.util.Random;

public class map {
	 
	int sizex;
	int sizey;
	int padding = 100;
	int pixelblocksize = 32;
	int[][] map;
	int[][] shownmap;
	int blockx = 40;  //at 32pix its 40
	int blocky = 24;       //at 32pix its 24
	int mapNumber;
	monsterlist mlist;
	Random ran = new Random();
	int randomMob = 0;
	String seed;
	
	
	//idea make different biomes
	//water biome, regular land, big land, dungeons(rooms with lots of narrow passages) etc.
	
	map(int blocksize, int sizex, int sizey, int mapNumber, String seed)
	{
		this.pixelblocksize = blocksize;
		this.sizex = sizex;
		this.sizey = sizey;
		this.blockx = (int)1280/blocksize;
		this.blocky = (int) 768/blocksize;
		
		map = new int[sizex+padding][sizey+padding];
	
	
		shownmap = new int[blockx][blocky];
		mlist = new monsterlist(mapNumber);
		
		this.seed = seed;
		makeSeedMap(seed);
		
		
		cleanMap();
		cleanMap();
		cleanMap();
		cleanMap();
		cleanMap();
		cleanMap();
		cleanMap();
		cleanMap();
		widenRivers();
		cleanLand();
		cleanLand();
		cleanLand();
		cleanLand();
		cleanLand();
		cleanLand();
		
		
	}
	
	
	public void makeRandomTestMap()
	{
		Random ran = new Random();
		
		for(int x=0; x<sizex+padding; x++)
		{
			for(int y=0; y<sizey+padding; y++)
			{
				if( (x >padding-1 && x<sizex+1) && (y>padding-1 && y<sizey+1) )
				{
					if (ran.nextInt(5) == 0)
					{
						map[x][y] = 1;
					}
					else
					{
						map[x][y] = 0;
					}
				}
				else
				{
					map[x][y] = 1;
				}
				
			}
		}
	}
	
	public void makeSeedMap(String seedString)  //ex seed 36172671737612734829 l=20  //map size 100x100 or wtv goes by sizex
	{

		for(int x=0; x<sizex+padding; x++)
		{
			for(int y=0; y<sizey+padding; y++)
			{
				if( (x >padding-1 && x<sizex+1) && (y>padding-1 && y<sizey+1) )
				{
					//within the boundaries is where we make map
					//100x100 is 10000 blocks
					
					int xx = x%9;
					int yy= y%9;
					
					
					if(  Math.sin(Integer.parseInt(seedString.substring(xx,yy+xx+1))) * Math.sin(x*y)  > 0.9)
					{
						//System.out.println(" aaa x :"+x+" y:"+y);
						//map[x][y] = 2;
						makeBlock(seedString.substring(xx,yy+xx+1), x ,y);
					}
					
					
					
					if(  Math.sin(Integer.parseInt(seedString.substring(yy,yy+xx+1))) * Math.sin(x*y)  > 0.9)
					{
						//System.out.println(" bbb x :"+x+" y:"+y);
						//map[x][y] = 2;
						makeBlock(seedString.substring(yy,yy+xx+1),x,y);
					}
					
				
					if(  Math.cos(Integer.parseInt(seedString.substring(yy,yy+xx+1))) * Math.sin(x*y)  > 0.9)
					{
						//System.out.println(" ccc x :"+x+" y:"+y);
						//map[x][y] = 2;
						makeBlock(seedString.substring(yy,yy+xx+1),x,y);
					}
					
				}
				else
				{
					//outside boundaries is the padding
					//we use padding to avoid negatives when drawing the visible map
					map[x][y] = 1;
				}
				
			}
		}
	}
	
	//this makes the blocks
	//0 is walkable
	//1 is not (wall)
	public void makeBlock(String subseed, int x, int y)
	{
		//System.out.println(""+subseed);
		
		//make Bigblocks from starting 32x32 block
		int xx= x% 17;
		int yy =y% 17;
		
		int height =  Integer.parseInt(seed.substring(xx,xx+2)) % 9;
		int width =  Integer.parseInt(seed.substring(yy,yy+2)) %9;
		if(height < 3 )
		{
			height = 1;
		}
		if(width <3 )
		{
			width =1;
		}
		
		//System.out.println("h = "+height+" w = "+width);
		
		for(int a = x; a<x+width; a++)
		{
			for(int b = y; b<y+height; b++)
			{		
				map[a][b] = 2;
			}
		}
		
	}
	
	
	
	//this is to help with the small puddles
	public void cleanMap()
	{
		int oldmap [][] = copymap(map);
		
		int sides = 0;
		
		for(int x=0; x<sizex+padding; x++)
		{
			for(int y=0; y<sizey+padding; y++)
			{
				if( (x >padding-1 && x<sizex+1) && (y>padding-1 && y<sizey+1) )
				{
					//cleans tiny puddles
					if (oldmap[x-1][y] == 2 )  // is this side walkable?
					{
						sides++;
					}
					if (oldmap[x+1][y] == 2 )  // is this side walkable?
					{
						sides++;
					}
					if (oldmap[x][y-1] == 2 )  // is this side walkable?
					{
						sides++;
					}
					if (oldmap[x][y+1] == 2 )  // is this side walkable?
					{
						sides++;
					}
					
					if(sides >= 3)  //if 3 or 4 sides are walkable lets remove it
					{
						map[x][y] = 2;
					}
					sides = 0;
					
					
					//Cleans small 4x4 lakes... theyre offputting
					if(oldmap[x][y] == 0)
					{
						if(oldmap[x+1][y] ==0  && oldmap[x+1][y+1] == 0  & oldmap[x][y+1] == 0)
						{
							//if its a 4block but also have to check its not more
							
							if(oldmap[x-1][y] ==2  && oldmap[x][y-1] ==2  && oldmap[x+1][y-1] ==2  && oldmap[x-1][y+1] ==2  && oldmap[x][y+2] ==2  && oldmap[x+1][y+2] ==2  && oldmap[x+2][y] ==2  && oldmap[x+2][y+1] ==2  )
							{
								map[x][y] =2;
								map[x+1][y] = 2;
								map[x+1][y+1] = 2;
								map[x][y+1] = 2;
								//System.out.println("clean 4");
							}	
						}
					}
					
					
					
					
				}
			}
		}	
	}
	
	public void cleanLand()
	{
		int oldmap [][] = copymap(map);
		
		int sides = 0;
		
		for(int x=0; x<sizex+padding; x++)
		{
			for(int y=0; y<sizey+padding; y++)
			{
				if( (x >padding-1 && x<sizex+1) && (y>padding-1 && y<sizey+1) )
				{
					//cleans tiny offputting land
					if (oldmap[x-1][y] == 0)  // is this a lake
					{
						sides++;
					}
					if (oldmap[x+1][y] == 0 ) // is this a lake
					{
						sides++;
					}
					if (oldmap[x][y-1] == 0 ) // is this a lake
					{
						sides++;
					}
					if (oldmap[x][y+1] == 0 ) // is this a lake
					{
						sides++;
					}
					
					if(sides >= 3)  //if 3 or 4 sides are walkable lets remove it
					{
						map[x][y] = 0;
					}
					sides = 0;
					
					
					
				}
			}
		}	
	}
	
	public void widenRivers()
	{
		
		int oldmap [][] = copymap(map);
		int sides = 0;
		
		for(int x=0; x<sizex+padding; x++)
		{
			for(int y=0; y<sizey+padding; y++)
			{
				if( (x >padding-1 && x<sizex+1) && (y>padding-1 && y<sizey+1) )
				{
					
					//widen rivers
					if(oldmap[x][y] == 0)
					{
						//connects some lakes
						if (oldmap[x+1][y+1] == 0 )  // is this a lake
						{
							map[x+1][y] =0;
							map[x][y+1] =0;
							
							//System.out.println("River Widened");
						}
						if (oldmap[x+1][y-1] == 0 )  // is this a lake
						{
							map[x+1][y] =0;
							map[x][y-1] =0;
							
							//System.out.println("River Widened");
						}	
					}
					
					
					
					
				}
			}
		}
	}
		
	
	
	
	
	//blocks are 32 by 32 or wtv i decide in gameloop
	//sizex = 1280;
	//sizey = 768;
	public void makevisiblemap(hero hero)
	{
		for (int x=0; x<blockx; x++)
		{
			for (int y=0; y<blocky; y++)
			{
				shownmap[x][y] = map[hero.x-((int)blockx/2)+x][hero.y-((int)blocky/2)+y];
				if(x == ((int)blockx/2) && y == ((int)blocky/2))
				{
					shownmap[x][y] = 3; // our hero drawn
				}
			}
		}
	}
	
	public int randomMob()
	{
		randomMob = ran.nextInt(mlist.mlist.size());
		return randomMob;
	}
	
	
	public int[][] copymap(int[][] mymap)
	{
		int l = mymap.length;
		int w = mymap[0].length;
		
		int copiedmap[][] = new int[l][w];
		
		for(int a = 0; a< l; a++)
		{
			for (int b=0; b<w; b++)
			{
				copiedmap[a][b] = mymap[a][b];
			}
		}
		
		return copiedmap;
	}
}
