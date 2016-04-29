/*
 * TetriT
 */

public class TetriT extends Tetri
{
	//makes a tetris piece shaped like a T
	public TetriT()
	{
		Pair<Integer> temp[] = new Pair[4];
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = new Pair<Integer>();
		}
		
		temp[0].setX(0);
		temp[0].setY(0);
		
		temp[1].setX(1);
		temp[1].setY(0);
		
		temp[2].setX(2);
		temp[2].setY(0);
		
		temp[3].setX(1);
		temp[3].setY(1);
		
		super.setLocations(temp);
		
	}
	
	//returns tetris type as a string
	public String getType()
	{
		return "T";
	}
}
