/*
 * Board
 * 
 * holds all info of the current game board
 */
public class Board 
{
	private boolean area[][]; //a 2d array of booleans that represents the game board
	
	//creates a board of size 20x10
	public Board()
	{
		area = new boolean[20][10];
		
		for(int i = 0; i < area.length; i++)
		{
			for(int j = 0 ; j < area[i].length; j++)
			{
				area[i][j] = false;
			}
		}
	}
	
	
	//getter for the board
	public boolean[][] getBoard()
	{
		return area;
	}
	
	//checks for full rows then deletes and shifts them down
	private int[] checkScore(int level)
	{
		int counter = 0;
		int numlevel = 0;
		boolean flag = true;
		
		for(int i = (area.length - 1); i >= 0; i--)
		{
			//checks if row is all filled
			for(int j = (area[0].length - 1); j >= 0; j--)
			{
				if(area[i][j] != true)
				{
					flag = false;
				}
			}
			
			if(flag)//if row filled
			{
				System.out.println("moving rows down");
				numlevel++;
				//shifts all rows down on top
				for(int temp = i; temp > 0; temp--)
				{
					for(int j = 0; j < area[0].length; j++)
					{
						area[temp][j] = area[(temp - 1)][j];
					}
				}
				for(int j = 0; j < area[0].length; j++)
				{
					area[0][j] = false;
				}
				i++;
				
			}
			
			flag = true;
		}
		
			//assigns score values
			if(numlevel == 1)
			{
				counter += 40 * level;
			}
			if(numlevel == 2)
			{
				counter += 100 * level;
			}
			if(numlevel == 3)
			{
				counter += 300 * level;
			}
			if(numlevel == 4)
			{
				counter += 1200 * level;
			}
			
		
		
		int[] temp = {counter, numlevel};
		
		return temp;
	}
	
	//a wrapper for the check score method
	private int[] updateScore(int level)
	{
		int temp[] = checkScore(level);
		
		return temp;
	}
	
	//checks the board for overlapping peices then adds piece to the board and checks if it needs to update the score or rows cleared
	public int[] updateBoard(int level, Tetri piece)
	{
		Pair<Integer>[] temp = piece.getLocations();
		
		for(Pair<Integer> i : temp)
		{
			if(area[i.getY()][i.getX()] == false)
			{
				area[i.getY()][i.getX()] = true;
			}
			else
			{
				System.out.println("ERROR: Pieces cannot overlap");
			}
		}
		
		return this.updateScore(level);
	}
}
