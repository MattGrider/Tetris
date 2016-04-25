
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabelTest2 extends JFrame {
   private JLabel labelArray[];
	private int lasize, lacount;
	private JButton timeButton;
	private boolean timeToggle;
	private Timer timeClock;
	private Icon iconArray[];
	private String names[] = 
		{  "bluesquare.jpg", "blacksquare.jpg", "whitesquare.jpg" };


   public LabelTest2()
   {
      super( "Testing JLabel" );

      Container c = getContentPane();
      c.setLayout( new FlowLayout() );


	   // create and add icons
	   iconArray = new Icon [ names.length ];

	   for ( int count = 0; count < names.length; count++ ) 
	   {
		   iconArray[ count ] = new ImageIcon ( names[ count ] );
	   }

	   lasize = 4;
	   lacount = 0;

	   labelArray = new JLabel [ lasize ];
	   for ( int count = 0; count < lasize; count++ ) 
	   {
		   labelArray[ count ] = new JLabel ( iconArray [ count % names.length ] );
		   c.add (labelArray [ count ]);
	   }
	   
	   int delay = 1000;
	   timeClock = new Timer(delay, new TimerHandler () );
	   // create button
	   timeButton = new JButton( "Start Changing Colors" );
	   c.add( timeButton );
	   ButtonHandler handler = new ButtonHandler();
	   timeButton.addActionListener( handler );
	   timeToggle = false;
	
	   
	   setSize( 275, 170 );
      show();
   }

   public static void main( String args[] )
   { 
      LabelTest2 app = new LabelTest2();

      app.addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
               System.exit( 0 );
            }
         }
      );
   }

	// inner class for button event handling
	private class ButtonHandler implements ActionListener 
	{

		// handle button event
		public void actionPerformed( ActionEvent event )
		{
			if (timeToggle == false)
			{
				timeClock.start();
				timeButton.setText ("Stop Changing Colors");
			}											
			else
			{
				timeClock.stop();
				timeButton.setText ("Start Changing Colors");
			}
			timeToggle = !timeToggle;
		}

	} // end private inner class ButtonHandler

	// inner class for timer event handling
	private class TimerHandler implements ActionListener 
	{

		// handle button event
		public void actionPerformed( ActionEvent event )
		{
			int rval = (int) Math.floor(Math.random() * names.length);

			labelArray[lacount].setIcon(iconArray[rval]);

			lacount = (lacount+1)%lasize;
		}

	} // end private inner class TimerHandler2
}

