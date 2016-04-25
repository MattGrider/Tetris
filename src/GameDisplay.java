import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class GameDisplay extends JFrame 
{
	private JLabel labelArray[];
	private int lasize, lacount;
	private JButton timeButton;
	private boolean timeToggle;
	private Timer timeClock;
	private Icon iconArray[];
	private String names[] = 
		{  "bluesquare.jpg", "blacksquare.jpg", "whitesquare.jpg" };
	
	public GameDisplay()
	{
		super( "TetrisTest" );
		
		Container c = getContentPane();
	    c.setLayout( new BorderLayout() );

		JPanel labelPanel = new JPanel ();
		int row = 20;
		int column = 10;
		labelPanel.setLayout (new GridLayout (row, column));
		labelPanel.setSize( row*20, column * 20 );
		
		c.add (labelPanel, BorderLayout.NORTH );
		
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
			   labelArray[ count ] = new JLabel ( iconArray [ count % names.length ] );
			   labelPanel.add (labelArray [ count ]);
		   }
		   
		   pack();
		      show();
	}
	
	public static void main( String args[] )
	   { 
	      GameDisplay app = new GameDisplay();

	      app.addWindowListener(
	         new WindowAdapter() {
	            public void windowClosing( WindowEvent e )
	            {
	               System.exit( 0 );
	            }
	         }
	      );
	   }
}
