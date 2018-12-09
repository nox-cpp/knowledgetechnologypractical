package src.java;
import java.awt.GridLayout;
import java.util.Iterator;
import src.java.*;
import javax.swing.*;

public class ktpPanel extends JPanel {

	public int debug;

	ktpPanel(String title, int debug){
		initPanel(title);
		this.debug = debug;
	}

	ktpPanel(Question q){
		this.debug = 0;
		initPanel(q);
		
	}
	
	/**
	 * initiate the panel for debug purposes.
	 * @param title Title of the panel. This is shown in a label for debug purposes.
	 */
	void initPanel(String title){
		JLabel t = new JLabel(title);
		this.add(t);
		this.setLayout(new GridLayout(0,1));
	}
	
	/**
	 * initiate the panel
	 */
	void initPanel(Question q){
		this.setLayout(new GridLayout(0,1));
		// add label for the title
		JLabel question = new JLabel(q.question);
		this.add(question);
		// add each component for q
		for(Iterator<JComponent> i = q.componentList.iterator(); i.hasNext();){
			JComponent item = i.next();
			this.add(item);
			
		}
		

	}

}
