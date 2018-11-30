/**
 * 	helloWorld.java
 * 	authors:
 *  Marnix Jansma, 23-11-18
 *  Gijs Hendriks 
 *  
 *  To compile:
 *  javac -classpath "$FLORADIR/java/flora2java.jar":"$FLORADIR/java/interprolog.jar" helloWorld.java
 *  
 *  To run:
 *  java -DPROLOGDIR=$PROLOGDIR -DFLORADIR=$FLORADIR -classpath $FLORADIR/java/flora2java.jar:$FLORADIR/java/interprolog.jar:./ helloWorld
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;
import javax.swing.*;	//swing

public class helloWorld{
	
	public static void main(String[] args) {
				
			FloraSession session = new FloraSession();
			System.out.println("Flora-2 session started");
			System.out.println("Starting Gui");
			initGUI();
		}
	
	
	
	public static void initGUI(){
		JFrame frame = new JFrame("Breast cancer risk assesment");
	    GridLayout layout = new GridLayout(5,0);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    
	    JButton nextButton = new JButton("Click me");
	    nextButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	    System.out.println("clicked a button");
	    	  }
	    	});
	    
	    frame.add(nextButton);
	    frame.setLayout(layout);
	    frame.setSize(1000, 500);
	    frame.setLocation(300, 300);
	    frame.setVisible(true);
	    
	}

}
