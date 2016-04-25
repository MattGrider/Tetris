// Fig. 12.4: LabelTest.java
// Demonstrating the JLabel class.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabelTest extends JFrame {
   private JLabel label1, label2, label3, label4;

   public LabelTest()
   {
      super( "Testing JLabel" );

      Container c = getContentPane();
      c.setLayout( new FlowLayout() );

	   // JLabel constructor with string, Icon and
	   // alignment arguments
	   Icon blue = new ImageIcon( "bluesquare.jpg" );
	   Icon black = new ImageIcon( "blacksquare.jpg" );
	   Icon white = new ImageIcon( "whitesquare.jpg" );
	   
	   // JLabel constructor with a string argument
      label1 = new JLabel( blue );
      c.add( label1 );

      label2 = new JLabel( black );
      c.add( label2 );

      label3 = new JLabel( white );
      c.add( label3 );

	   label4 = new JLabel( blue );
	   c.add( label4 );
	   
	   setSize( 275, 170 );
      show();
   }

   public static void main( String args[] )
   { 
      LabelTest app = new LabelTest();

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