/*
 * 
 * TODO Use grouplayout?
 * 
 */



package src.java;
import src.java.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class ktpFrame extends JFrame {
	public List<ktpPanel> panelList;
	public int currentPanel;

	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	ktpFrame(){
		super("Genetic disorder risk assesment");
		panelList = new ArrayList<ktpPanel>();
		this.currentPanel = 0;
		initGUI();
		
	}
	
	/**
	 * Initializes the GUI
	 */
	public void initGUI(){
		
		/*JOptionPane.showMessageDialog(this,
			    "This application is made by students for a course as an exercise in Knowledge systems.\n" +
			    "Medical professionals where consulted during the making of this application,\n" +
			    "however any advice should be treated as given by some AI students (which it is).",
			    "Risk warning",
			    JOptionPane.WARNING_MESSAGE);*/
		
		
		// first example question
		List<JComponent> lst = new ArrayList<JComponent>();
		lst.add(new JTextField());
		Question firstq = new Question("What is your age?", lst);
	    ktpPanel first = new ktpPanel(firstq);
	    
	    
	    //second example question
	    lst.clear();
		lst.add(new JRadioButton("Man"));
		lst.add(new JRadioButton("Woman"));
		Question secondq = new Question("What is your gender?", lst);
	    ktpPanel second = new ktpPanel(secondq);

	    
	    this.panelList.add(first);
	    this.panelList.add(second);
	    
	    
	    JButton nextButton = createNextButton("Next");
	    JButton prevButton = createPrevButton("Previous");
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(0,2));
	    
	    buttonPanel.add(prevButton);
	    buttonPanel.add(nextButton);
	    this.add(buttonPanel);
	    this.add(panelList.get(0));

	    
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    GridLayout layout = new GridLayout(0,1);
	    this.setLayout(layout);
	    this.setSize(1000, 500);
	    this.setLocation(200, 200);
	    //this.pack();
	    this.setVisible(true);
	    
	}
	
	
	/**
	 * Removes one panel, and replaces with another
	 * @param toRemove Panel to be removed
	 * @param toAdd	Panel to be shown next
	 */
	public void changePanel(ktpPanel toRemove, ktpPanel toAdd){
		
		this.remove(toRemove);
		this.add(toAdd);
		this.currentPanel = this.panelList.indexOf(toAdd);
		
		System.out.println(toRemove.debug);
		System.out.println(toAdd.debug);
		invalidate();
		validate();
		repaint();
		
	}
	
	
	
	

	
	/** 
	 * Creates the next button with action listener
	 * @param text The text of the button
	 */
	public JButton createNextButton(String text){
		JButton nextButton = new JButton(text);
		
	    nextButton.addActionListener(new ActionListener(){	    	
	    	public void actionPerformed(ActionEvent e){
	    		if(currentPanel + 1 < panelList.size()){
	    			System.out.format("clicked a button currentPanel = %d new = %d\n", currentPanel, (1-currentPanel), (1-0));
	    			changePanel(panelList.get(currentPanel), panelList.get(currentPanel+1));
	    		}else{
	    			System.out.println("No next panel");
	    		}
	    	}
	    });
	    return nextButton;
	}
	

	/** Creates the previous button with action listener
	 * @param text The text of the button
	 */

	public JButton createPrevButton(String text){
		
		JButton prevButton = new JButton(text);
		prevButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e){
	    		  if(currentPanel - 1 >= 0 ){
	    			  System.out.format("clicked prev button currentPanel = %d new = %d\n", currentPanel, (1-currentPanel), (1-0));
			    	  changePanel(panelList.get(currentPanel), panelList.get(currentPanel-1));
	    		  }else{
	    			  System.out.println("No previous panel");
	    		  }
	    	  }
	    	});
	    return prevButton;
		
	}

}
