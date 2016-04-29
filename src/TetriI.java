/*
 * TetriI
 */


public class TetriI extends Tetri 
{
	//makes a tetris peice in the shape of an I
	public TetriI()
	{
		Pair<Integer> temp[] = new Pair[4];
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = new Pair<Integer>();
		}
		
		for(int i = 0; i < 4; i++)
		{
			temp[i].setX(0);
			temp[i].setY(i);
		}
		
		super.setLocations(temp);
		
	}
	
	//returns the type as a string
	public String getType()
	{
		return "I";
	}
	
}
