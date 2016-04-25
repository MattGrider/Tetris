/*
 * Class that will be used by all other tetris items
 */
public abstract class Tetri 
{
	//array that holds the 4 locations of the blocks
	private  Pair<Integer> locations[];
	
	
	public void setLocations(Pair<Integer> v[])
	{
		locations = v;
	}
	
	//returns the location of the squares
	public Pair<Integer>[] getLocations()
	{
		return locations;
	}
	
	public void move()
	{
		for(int i = 0; i < locations.length; i++)
		{
			locations[i].setY((locations[i].getY() + 1));
		}
	}
	
	public void moveLeft()
	{
		for(int i = 0; i < locations.length; i++)
		{
			locations[i].setX((locations[i].getX() - 1));
		}
	}
	
	public void moveRight()
	{
		for(int i = 0; i < locations.length; i++)
		{
			locations[i].setX((locations[i].getX() + 1));
		}
	}
	
	//creates a deep copy of locations
	private Pair<Integer>[] copyLocations()
	{
		Pair<Integer> temp[] = new Pair[locations.length];
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = new Pair<Integer>();
		}
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i].setX(locations[i].getX());
			temp[i].setY(locations[i].getY());
		}
		
		return temp;
	}
	
	public void copy(Tetri copyThis)
	{
		Pair<Integer> temp[] = copyThis.getLocations();
		
		for(int i = 0; i < locations.length; i++)
		{
			locations[i].setX(temp[i].getX());
			locations[i].setY(temp[i].getY());
		}
		
		
	}
	
	public void rotateRight()
	{
		Pair<Integer> holder[] = copyLocations();
		
		int Rx = holder[0].getX();
		int Ry = holder[0].getY();
		int Px;
		int Py;
		
		for(int i = 0; i < holder.length; i++)
		{
			Px = holder[i].getX();
			Py = holder[i].getY();
			
			if((Rx + Ry - Py) < 0)
			{
				return;
			}
			if(((Rx * (-1)) + Ry + Px) < 0)
			{
				return;
			}
			
			holder[i].setX((Rx + Ry - Py));
			holder[i].setY(((Rx * (-1)) + Ry + Px));
		}
		locations = holder;
	}
	
	public void rotateLeft()
	{
Pair<Integer> holder[] = copyLocations();
		
		int Rx = holder[0].getX();
		int Ry = holder[0].getY();
		int Px;
		int Py;
		
		for(int i = 0; i < holder.length; i++)
		{
			Px = holder[i].getX();
			Py = holder[i].getY();
			
			if((Rx - Ry + Py) < 0)
			{
				return;
			}
			if((Rx + Ry - Px) < 0)
			{
				return;
			}
			
			holder[i].setX((Rx - Ry + Py));
			holder[i].setY((Rx + Ry - Px));
		}
		locations = holder;
	}
	
	public String getType()
	{
		return "Tetri";
	}
	
	
	
}
