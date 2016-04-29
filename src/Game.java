/*
 * Game
 * 
 * runs the game and controls what happens during movement
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;

public class Game extends JFrame implements ActionListener, KeyListener
{
	private Timer timeClock; //controls how fast pieces drop
	private Timer gameTime; //counts active game time
	private Tetri activePiece; //live piece
	private Tetri nextPiece; //next piece after current one placed
	private Board gameBoard; //the board itself
	private int level; //current level
	private int score; //current score
	private int levelsCleared; //rows cleared
	private int countSec; //seconds elapsed
	
	
	//gui variables
	private JLabel labelArray[]; //holds all the board pics
	private JLabel scoretxt, actualScore; //shows score
	private JLabel leveltxt, actualLevel; //shows level
	private JLabel levelclearedtxt, actualLevelCleared; //shows cleared rows
	private JLabel nextPiecetxt, actualNextPiece; //shows next piece
	private JLabel timetxt, actualTime; // shows actual time
	private JMenuBar menu; //holder for menu
	private JMenu file; //bin for menu
	private JMenu about; //bin for menu
	private JMenuItem newGame; //new game tab
	private JMenuItem quit; //quit tab
	private JMenuItem abouttxt; //about game tab
	private JMenuItem help; //help tab
	private JButton left; //move piece left
	private JButton right; //move piece righ
	private JButton softDrop; //soft drop
	private JButton hardDrop; //hard drop
	private JButton rotate; //rotate
	private int lasize, lacount; //holds size fo board
	private Icon iconArray[]; //holds pics
	private String names[] = 
		{  "bluesquare.jpg", "blacksquare.jpg", "whitesquare.jpg" }; // pic names
	
	//sets gui and basic game scores
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
	
	//picks random piece for the next piece after current one is dropped
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
	
	//claculates the current delay based off level
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
	
	//checks if the game is over
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
	
	//moves piece to middle
	private void centerPiece()
	{
		for(int i = 0; i < 4; i++)
		{
			activePiece.moveRight();
		}
	}
	
	//starts/restarts game
	public void startGame()
	{
		//reseting of all clocks and values
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
			drawBoard();
			drawPiece();
			timeClock.start();
	}
	
	
	//checks if piece can move down
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
	
	//moves the piece down after checking if it is clear
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
	
	//draws the piece object to the screen
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
	
	//draws the board with already placed pieces to the screen
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
	
	//action performed for the delay timer
	public void actionPerformed(ActionEvent e)
	{
		//if piece cant move it runs all its checks
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
			
			//if row was cleared it updates score and possibly level
			if(checkChange != 0)
			{
				score = checkChange;
				System.out.println("new Level system");
				level = ((levelsCleared / 10) + 1);
				
				actualScore.setText(("" + score));
				actualLevel.setText(("" + level));
				actualLevelCleared.setText(("" + levelsCleared));
				
				System.out.println("level up");
			}
			//ends game if its over
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
	
	//getter for board
	public Board getGameBoard()
	{
		return gameBoard;
	}
	
	//getter for level
	public int getLevel()
	{
		return level;
	}
	
	//getter for score
	public int getScore()
	{
		return score;
	}
	
	//checks if piece can move left
	private boolean checkPieceLeft(Pair<Integer> temp)
	{
		if((temp.getX() - 1) < 0 || gameBoard.getBoard()[temp.getY()][temp.getX() - 1] == true)
		{
			return false;
		}
		return true;
	}
	
	//checks if piece can move right
	private boolean checkPieceRight(Pair<Integer> temp)
	{
		if((temp.getX() + 1) >= gameBoard.getBoard()[0].length || gameBoard.getBoard()[temp.getY()][temp.getX() + 1] == true)
		{
			return false;
		}
		return true;
	}
	
	//makes a hard drop which means it moves the pieces as far down as it can go immediatly
	private void hardDrop()
	{
		while(movePiece());
		//runs checks once piece can no longer move
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
			
			//if row cleared updates score and possibly level
			if(checkChange != 0)
			{
				score = checkChange;
				System.out.println("new Level system");
				level = ((levelsCleared / 10) + 1);
				actualScore.setText(("" + score));
				actualLevel.setText(("" + level));
				actualLevelCleared.setText(("" + levelsCleared));
				timeClock.setDelay(calcDelay());;
				System.out.println("level up");
			}
			//checks if game is over
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
	
	//moves the piece down one row
	private void softDrop()
	{
		//checks if peice can move
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
			
			//if row cleared update score and possibly level
			if(checkChange != 0)
			{
				score = checkChange;
				System.out.println("new Level system");
				level = ((levelsCleared / 10) + 1);
				actualScore.setText(("" + score));
				actualLevel.setText(("" + level));
				actualLevelCleared.setText(("" + levelsCleared));
				timeClock.setDelay(calcDelay());;
				System.out.println("level up");
			}
			//checks if game is over
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
	
	//checks if rotating the current piece will cause it to move off the board
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
	
	//key pressed events
	public void keyPressed(KeyEvent e) {

	    int key = e.getKeyCode();

	    //moves piece left
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

	    //moves piece right
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

	    //rotates piece
	    if (key == KeyEvent.VK_UP) 
	    {
	    	if(Objects.equals(activePiece.getType(), new String("O")))
	    	{
	    		System.out.println("its on O");
	    		timeClock.stop();
	    		return;
	    	}
	    	
				if(!checkRotate())
				{
					timeClock.stop();
					return;
				}
	    	
	    	
	    	activePiece.rotateRight();
	    	drawBoard();
	    	drawPiece();
	    	timeClock.stop();
	    }

	    //hard drop
	    if (key == KeyEvent.VK_DOWN) 
	    {
	        hardDrop();
	    }
	    
	    //soft drop
	    if(key == KeyEvent.VK_SPACE)
	    {
	    	softDrop();
	    }
	}
	
	//unused key typed event
	public void keyTyped(KeyEvent e) {}
	
	//key release event
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		//unpauses delay after rotation
		if (key == KeyEvent.VK_UP) 
	    {
	    	
	    	timeClock.start();
	    	
	    	drawBoard();
	    	drawPiece();
	    }
	}
	
	//runs the program
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
	
	//action for new game, starts new game
	private class startGameAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			startGame();
		}
	}
	
	//action for quit game, quits game
	private class quitAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			System.exit(0);
		}
	}
	
	//actions for about, shows a description of game and creators
	private class aboutAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			JOptionPane.showMessageDialog(null, "This is a Tetris game made by Matt G, Matt K, and Ray. The 5th programming assignment for CS 342 It's pretty cool. At least I think so", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//action for help, shows simple game help
	private class helpAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			JOptionPane.showMessageDialog(null, "The way you control the tetris blocks is with the arrow keys \n         <UP> rotates \n <LEFT> <RIGHT> shift blocks respectively \n      <DOWN> hard drops the block \n      <SPACE> soft drops the blcok i.e: one block at a time", "Help", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//action for game timer, times how long game has been running
	private class gameTimeAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			countSec++;
			actualTime.setText(("" + countSec));
		}
	}
	
	//action for left button, moves piece left
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
	
	//action for right button, moves piece right
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
	
	//action for rotation button, rotates piece
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
	
	//action for hard drop button, hard drops piece
	private class hardDropAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			hardDrop();
		}
	}
	
	//action for soft drop button, soft drops piece
	private class softDropAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			softDrop();
		}
	}
	
}