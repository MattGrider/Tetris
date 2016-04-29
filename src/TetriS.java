/*
 * TetriS
 */

public class TetriS extends Tetri 
{
	//makes a tetris piece shaped like an S
	public TetriS()
	{
		Pair<Integer> temp[] = new Pair[4];
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = new Pair<Integer>();
		}
		
		temp[0].setX(2);
		temp[0].setY(0);
		
		temp[1].setX(1);
		temp[1].setY(0);
		
		temp[2].setX(1);
		temp[2].setY(1);
		
		temp[3].setX(0);
		temp[3].setY(1);
		
		super.setLocations(temp);
		
	}
	
	//returns the type as a string
	public String getType()
	{
		return "S";
	}
}
