/*
 * TetriO
 */

public class TetriO extends Tetri 
{
	//makes a tetris piece shaped like an O
	public TetriO()
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
		
		temp[2].setX(0);
		temp[2].setY(1);
		
		temp[3].setX(1);
		temp[3].setY(1);
		
		super.setLocations(temp);
		
	}
	
	//returns type as a string
	public String getType()
	{
		return "O";
	}
	
	//overload the rotate methods so it will not move when rotated
	public void rotateRight()
	{
		return;
	}
	
	public void rotateLeft()
	{
		return;
	}
}
