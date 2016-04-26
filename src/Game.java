import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.*;

public class Game extends JFrame implements ActionListener, KeyListener
{
	private Timer timeClock;
	private Timer gameTime;
	private Tetri activePiece;
	private Tetri nextPiece;
	private Board gameBoard;
	private int level;
	private int score;
	private int levelsCleared;
	private int countSec;
	
	
	//gui variables
	private JLabel labelArray[];
	private JLabel scoretxt, actualScore;
	private JLabel leveltxt, actualLevel;
	private JLabel levelclearedtxt, actualLevelCleared;
	private JLabel nextPiecetxt, actualNextPiece;
	private JLabel timetxt, actualTime;
	private JMenuBar menu;
	private JMenu file;
	private JMenu about;
	private JMenuItem newGame;
	private JMenuItem quit;
	private JMenuItem abouttxt;
	private JMenuItem help;
	private JButton left;
	private JButton right;
	private JButton softDrop;
	private JButton hardDrop;
	private JButton rotate;
	private int lasize, lacount;
	private Icon iconArray[];
	private String names[] = 
		{  "bluesquare.jpg", "blacksquare.jpg", "whitesquare.jpg" };
	
	public Game()
	{
		super( "Tetris" );
		
		level = 1;
		score = 0;
		levelsCleared = 0;
		timeClock = new Timer(this.calcDelay(), this);
		gameTime = new Timer(1000, new gameTimeAction());
		
		//gui
		
		
		Container c = getContentPane();
	    c.setLayout( new BorderLayout() );

	    
	    menu = new JMenuBar();
	    file = new JMenu("File");
	    about = new JMenu("About/Help");
	    newGame = new JMenuItem("New Game");
	    newGame.addActionListener(new startGameAction());
	    quit = new JMenuItem("Quit");
	    quit.addActionListener(new quitAction());
	    abouttxt = new JMenuItem("About");
	    abouttxt.addActionListener(new aboutAction());
	    help = new JMenuItem("Help");
	    help.addActionListener(new helpAction());
	    
	    menu.add(file);
	    menu.add(about);
	    file.add(newGame);
	    file.add(quit);
	    about.add(abouttxt);
	    about.add(help);
	    
		JPanel labelPanel = new JPanel ();
		int row = 20;
		int column = 10;
		labelPanel.setLayout (new GridLayout (row, column));
		labelPanel.setSize( row*20, column * 20 );
		
		JPanel scoreHolder = new JPanel();
		scoretxt = new JLabel("Score: ");
		actualScore = new JLabel("0");
		leveltxt = new JLabel("Level: ");
		actualLevel = new JLabel("1");
		levelclearedtxt = new JLabel("Lines Cleared: ");
		actualLevelCleared = new JLabel("0");
		nextPiecetxt = new JLabel("Next Piece: ");
		actualNextPiece = new JLabel("IDK");
		timetxt = new JLabel("Time: ");
		actualTime = new JLabel("0");
		scoreHolder.setLayout(new GridLayout(5, 2));
		
		
		JPanel buttonHolder = new JPanel();
		left = new JButton("Left");
		left.addActionListener(new leftAction());
		right = new JButton("Right");
		right.addActionListener(new rightAction());
		softDrop = new JButton("Soft Drop");
		softDrop.addActionListener(new softDropAction());
		hardDrop = new JButton("Hard Drop");
		hardDrop.addActionListener(new hardDropAction());
		rotate = new JButton("Rotate");
		rotate.addActionListener(new rotateAction());
		buttonHolder.setLayout(new GridLayout(3,2));
		
		c.add(menu, BorderLayout.NORTH);
		c.add (labelPanel, BorderLayout.CENTER );
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		c.add(scoreHolder, BorderLayout.SOUTH);
		c.add(buttonHolder, BorderLayout.WEST);
		
		// create and add icons
		   iconArray = new Icon [ names.length ];

		   for ( int count = 0; count < names.length; count++ ) 
		   {
			   iconArray[ count ] = new ImageIcon ( names[ count ] );
		   }
		   
		   lasize = row * column;
		   lacount = 0;

		   labelArray = new JLabel [ lasize ];
		   for ( int count = 0; count < lasize; count++ ) 
		   {
			   labelArray[ count ] = new JLabel ( iconArray [ 2 ] );
			   labelPanel.add (labelArray [ count ]);
		   }
		   
		   scoreHolder.add(scoretxt);
		   scoreHolder.add(actualScore);
		   scoreHolder.add(leveltxt);
		   scoreHolder.add(actualLevel);
		   scoreHolder.add(levelclearedtxt);
		   scoreHolder.add(actualLevelCleared);
		   scoreHolder.add(nextPiecetxt);
		   scoreHolder.add(actualNextPiece);
		   scoreHolder.add(timetxt);
		   scoreHolder.add(actualTime);
		   
		   buttonHolder.add(softDrop);
		   buttonHolder.add(hardDrop);
		   buttonHolder.add(left);
		   buttonHolder.add(right);
		   buttonHolder.add(rotate);
		   
		   
		   pack();
		      show();
	}
	
	private Tetri randomPickPiece()
	{
		Random rand = new Random();
		
		int num = (rand.nextInt(7) + 1);
		
		if(num == 1)
		{
			System.out.println("Piece: I");
			return new TetriI();
		}
		if(num == 2)
		{
			System.out.println("Piece: J");
			return new TetriJ();
		}
		if(num == 3)
		{
			System.out.println("Piece: L");
			return new TetriL();
		}
		if(num == 4)
		{
			System.out.println("Piece: O");
			return new TetriO();
		}
		if(num == 5)
		{
			System.out.println("Piece: S");
			return new TetriS();
		}
		if(num == 6)
		{
			System.out.println("Piece: T");
			return new TetriT();
		}
		
		System.out.println("Piece: Z");
		return new TetriZ();
	}
	
	private int calcDelay()
	{
		int delay;
		if(level < 25)
		{
			delay = (int)(((50.0 - (level * 2.0)) / 60.0) * 1000);
		}
		else
		{
			delay = (int) (((50.0 - (24 * 2.0)) / 60.0) * 1000);
		}
		
		System.out.println("Delay: " + delay);
		return delay;
	}
	
	private boolean gameOver()
	{
		boolean temp[][] = gameBoard.getBoard();
		
		for(int i = 0; i < temp[0].length; i++)
		{
			if(temp[0][i] == true)
			{
				gameTime.stop();
				return true;
			}
		}
		
		return false;
	}
	
	private void centerPiece()
	{
		for(int i = 0; i < 4; i++)
		{
			activePiece.moveRight();
		}
	}
	
	public void startGame()
	{
		if(timeClock.isRunning())
		{
			timeClock.stop();
		}
		
		countSec = 0;
		
		if(gameTime.isRunning())
		{
			gameTime.restart();
		}
		else
		{
			gameTime.start();
		}
		
		gameBoard = new Board();
		level = 1;
		score = 0;
		levelsCleared = 0;
		activePiece = this.randomPickPiece();
		nextPiece = randomPickPiece();
		actualNextPiece.setText(nextPiece.getType());
		centerPiece();
		
		actualScore.setText(("" + score));
		actualLevel.setText(("" + level));
		actualLevelCleared.setText(("" + levelsCleared));
		
			timeClock.setDelay(calcDelay());
			//timeClock = new Timer(1000, this);
			drawBoard();
			drawPiece();
			timeClock.start();
	}
	
	
	private boolean checkPiece(Pair<Integer> temp)
	{
		boolean checker[][] = gameBoard.getBoard();
		
		if(temp.getY() >= (checker.length - 1))
		{
			return false;
		}
		if(checker[(temp.getY() + 1)][temp.getX()] == true)
		{
			return false;
		}
		return true;
	}
	
	private boolean movePiece()
	{
		for(Pair<Integer> i : activePiece.getLocations())
		{
			if(!checkPiece(i))
			{
				return false;
			}
		}
		
		activePiece.move();
		
		return true;
	}
	
	private void drawPiece()
	{
		Pair<Integer> pairDrawer[] = activePiece.getLocations();
		for(Pair<Integer> i : pairDrawer)
		{
			if((i.getX() + (10 * i.getY())) < labelArray.length)
			{
				System.out.println("x: " + i.getX() + " y: " + i.getY());
				labelArray[(i.getX() + (10 * i.getY()))].setIcon(iconArray[1]);
			}
		}
	}
	
	private void drawBoard()
	{
		boolean[][] boardDrawer = gameBoard.getBoard();
		for(int i = 0; i < boardDrawer.length; i++)
		{
			for(int j = 0; j < boardDrawer[i].length; j++)
			{
				if((j + (10 * i)) < labelArray.length)
				{
					if(boardDrawer[i][j] == false)
					{
						labelArray[(j + (10 * i))].setIcon(iconArray[2]);
					}
					else
					{
						labelArray[(j + (10 * i))].setIcon(iconArray[0]);
						System.out.println("final x: " + i + " y: " + j);
					}
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(!movePiece())
		{
			int multivalue[] = gameBoard.updateBoard(level, activePiece);
			
			int checkChange = multivalue[0];
			levelsCleared += multivalue[1];
			
			activePiece.copy(nextPiece);
			nextPiece = this.randomPickPiece();
			actualNextPiece.setText(nextPiece.getType());
			centerPiece();
			
			drawBoard();
			drawPiece();
			
			if(checkChange != 0)
			{
				score = checkChange;
				level++;
				
				actualScore.setText(("" + score));
				actualLevel.setText(("" + level));
				actualLevelCleared.setText(("" + levelsCleared));
				
				System.out.println("level up");
			}
			if(this.gameOver())
			{
				timeClock.stop();
				System.out.println("Game Over");
				JOptionPane.showMessageDialog(null, "GAME OVER", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			drawBoard();
			drawPiece();
		}
	}
	
	public Board getGameBoard()
	{
		return gameBoard;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getScore()
	{
		return score;
	}
	
	
	private boolean checkPieceLeft(Pair<Integer> temp)
	{
		if((temp.getX() - 1) < 0 || gameBoard.getBoard()[temp.getY()][temp.getX() - 1] == true)
		{
			return false;
		}
		return true;
	}
	
	private boolean checkPieceRight(Pair<Integer> temp)
	{
		if((temp.getX() + 1) >= gameBoard.getBoard()[0].length || gameBoard.getBoard()[temp.getY()][temp.getX() + 1] == true)
		{
			return false;
		}
		return true;
	}
	
	
	private void hardDrop()
	{
		while(movePiece());
		if(!movePiece())
		{
			int multivalue[] = gameBoard.updateBoard(level, activePiece);
			
			int checkChange = multivalue[0];
			levelsCleared += multivalue[1];
			
			activePiece.copy(nextPiece);
			nextPiece = this.randomPickPiece();
			actualNextPiece.setText(nextPiece.getType());
			centerPiece();
			
			drawBoard();
			drawPiece();
			
			if(checkChange != 0)
			{
				score = checkChange;
				level++;
				actualScore.setText(("" + score));
				actualLevel.setText(("" + level));
				actualLevelCleared.setText(("" + levelsCleared));
				timeClock.setDelay(calcDelay());;
				System.out.println("level up");
			}
			if(this.gameOver())
			{
				timeClock.stop();
				System.out.println("Game Over");
				JOptionPane.showMessageDialog(null, "GAME OVER", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				
			}
			else
			{
				drawBoard();
				drawPiece();
			}
		}
	}
	
	private void softDrop()
	{
		if(!movePiece())
		{
			int multivalue[] = gameBoard.updateBoard(level, activePiece);
			
			int checkChange = multivalue[0];
			levelsCleared += multivalue[1];
			
			activePiece.copy(nextPiece);
			nextPiece = this.randomPickPiece();
			actualNextPiece.setText(nextPiece.getType());
			centerPiece();
			
			drawBoard();
			drawPiece();
			
			if(checkChange != 0)
			{
				score = checkChange;
				level++;
				actualScore.setText(("" + score));
				actualLevel.setText(("" + level));
				actualLevelCleared.setText(("" + levelsCleared));
				timeClock.setDelay(calcDelay());;
				System.out.println("level up");
			}
			if(this.gameOver())
			{
				timeClock.stop();
				System.out.println("Game Over");
				JOptionPane.showMessageDialog(null, "GAME OVER", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				
			}
			else
			{
				drawBoard();
				drawPiece();
			}
		}
	}
	
	private boolean checkRotate()
	{
		activePiece.rotateRight();
		Pair<Integer> tester[] = activePiece.getLocations();
		for(Pair<Integer> i : tester)
		{
			if(i.getX() < 0 || i.getX() >= 10)
			{
				activePiece.rotateLeft();
				return false;
			}
			if(i.getY() < 0 || i.getY() >= gameBoard.getBoard().length)
			{
				activePiece.rotateLeft();
				return false;
			}
		}
		activePiece.rotateLeft();
		return true;
	}
	
	public void keyPressed(KeyEvent e) {

	    int key = e.getKeyCode();

	    if (key == KeyEvent.VK_LEFT) {
	    	for(Pair<Integer> i : activePiece.getLocations())
			{
				if(!checkPieceLeft(i))
				{
					return;
				}
			}
	    	
	    	activePiece.moveLeft();
	    	drawBoard();
			drawPiece();
	    }

	    if (key == KeyEvent.VK_RIGHT) 
	    {
	    	for(Pair<Integer> i : activePiece.getLocations())
			{
				if(!checkPieceRight(i))
				{
					return;
				}
			}
	    	
	    	activePiece.moveRight();
	    	drawBoard();
			drawPiece();
	    }

	    if (key == KeyEvent.VK_UP) 
	    {
	    	
	    	//for(Pair<Integer> i : activePiece.getLocations())
			//{
				if(!checkRotate())
				{
					timeClock.stop();
					return;
				}
			//}
	    	
	    	
	    	activePiece.rotateRight();
	    	drawBoard();
	    	drawPiece();
	    	timeClock.stop();
	    }

	    if (key == KeyEvent.VK_DOWN) 
	    {
	        hardDrop();
	    }
	    
	    if(key == KeyEvent.VK_SPACE)
	    {
	    	softDrop();
	    }
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) 
	    {
	    	
	    	timeClock.start();
	    	
	    	drawBoard();
	    	drawPiece();
	    }
	}
	
	public static void main( String args[] )
	   { 
	      Game app = new Game();

	      app.addWindowListener(
	         new WindowAdapter() {
	            public void windowClosing( WindowEvent e )
	            {
	               System.exit( 0 );
	            }
	         }
	      );
	      
	   }
	
	private class startGameAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			startGame();
		}
	}
	
	private class quitAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			System.exit(0);
		}
	}
	
	private class aboutAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			JOptionPane.showMessageDialog(null, "This is a Tetris game made by Matt G, Matt K, and Ray. The 5th programming assignment for CS 342 It's pretty cool. At least I think so", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class helpAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			JOptionPane.showMessageDialog(null, "The way you control the tetris blocks is with the arrow keys \n         <UP> rotates \n <LEFT> <RIGHT> shift blocks respectively \n      <DOWN> hard drops the block \n      <SPACE> soft drops the blcok i.e: one block at a time", "Help", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class gameTimeAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			countSec++;
			actualTime.setText(("" + countSec));
		}
	}
	
	private class leftAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for(Pair<Integer> i : activePiece.getLocations())
			{
				if(!checkPieceLeft(i))
				{
					return;
				}
			}
	    	
	    	activePiece.moveLeft();
	    	drawBoard();
			drawPiece();
		}
	}
	
	private class rightAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for(Pair<Integer> i : activePiece.getLocations())
			{
				if(!checkPieceRight(i))
				{
					return;
				}
			}
	    	
	    	activePiece.moveRight();
	    	drawBoard();
			drawPiece();
		}
	}
	
	private class rotateAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
				if(!checkRotate())
				{
					timeClock.stop();
			    	timeClock.start();
					return;
				}
	    	
	    	activePiece.rotateRight();
	    	drawBoard();
	    	drawPiece();
	    	timeClock.stop();
	    	timeClock.start();
		}
	}
	
	private class hardDropAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			hardDrop();
		}
	}
	
	private class softDropAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			softDrop();
		}
	}
	
}