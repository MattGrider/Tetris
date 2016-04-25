
public class TetriL extends Tetri 
{
	public TetriL()
	{
		Pair<Integer> temp[] = new Pair[4];
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = new Pair<Integer>();
		}
		
		temp[0].setX(0);
		temp[0].setY(0);
		
		temp[1].setX(0);
		temp[1].setY(1);
		
		temp[2].setX(0);
		temp[2].setY(2);
		
		temp[3].setX(1);
		temp[3].setY(2);
		
		super.setLocations(temp);
		
	}
	
	public String getType()
	{
		return "L";
	}
}
