/*
 * 
 * TODO Use grouplayout?
 * 
 */



package src.java;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;

public class ktpFrame extends JFrame {
	public List<ktpPanel> panelList;
	public List<String> answerList;
	public int currentPanel;

	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	ktpFrame(){
		super("Genetic disorder risk assesment");
		panelList = new ArrayList<ktpPanel>();
		answerList = new ArrayList<String>();
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
		List<KTPJComponent> lst = new ArrayList<KTPJComponent>();
		SpinnerModel model = new SpinnerNumberModel(18, 18, 120, 1);     
		KTPJSpinner spinner = new KTPJSpinner(model);
		lst.add(spinner);
		Question firstq = new Question("What is your age?", lst);
		System.out.println("lst size = " + lst.size());
	    ktpPanel first = new ktpPanel(firstq);
	    
	    
	    //second example question
	    List<KTPJComponent> lst2 = new ArrayList<KTPJComponent>();
	    KTPJRadioButton man = new KTPJRadioButton("Man");
	    //man.setSelected(true);
	    KTPJRadioButton woman = new KTPJRadioButton("Woman");
	    
	    ButtonGroup group = new ButtonGroup();
	    group.add(man);
	    group.add(woman);
		lst2.add(man);
		lst2.add(woman);
		Question secondq = new Question("What is your gender?", lst2);
	    ktpPanel second = new ktpPanel(secondq);
	    
	    
	    //third example question
	    List<KTPJComponent> lst3 = new ArrayList<KTPJComponent>();
		lst3.add(new KTPJRadioButton("Yes"));
		lst3.add(new KTPJRadioButton("No"));
		Question thirdq = new Question("Do you have disease in the family?", lst3);
	    ktpPanel third = new ktpPanel(thirdq);


	    
	    this.panelList.add(first);
	    this.panelList.add(second);
	    this.panelList.add(third);
	    
	    
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
		invalidate();
		validate();
		repaint();
		
	}
	
	
	
	public void sendQuestionData(int current){
		ktpPanel p = this.panelList.get(current);
		answerList.add(p.getQuestion().getAnswer());
		for(String s : this.answerList){
			System.out.println(s);
		}
		
	}
	
	/**
	 * Removes the last answer from the answerlist
	 */
	public void removeLastAnswer(){
		this.answerList.remove(this.answerList.size() - 1);
	}
	
	
	/**
	 * Checks the input bounds of the current panel before sending the data to the controller.
	 * True is returned when every component has a legal value. 
	 * @param current The current panel
	 * @return Whether the inputs are within bounds.
	 */
	public boolean checkInputBounds(int current){
		for(KTPJComponent comp : this.panelList.get(current).getQuestion().componentList){
			if(!comp.isWithinBounds()){
				return false;
			}
		}
		return true;
	}
	
	
	
	/** 
	 * Creates the next button with action listener. 
	 * When clicked the data from the current panel is added to this.answerList.
	 * Also the next panel is shown if the value entered is within bounds.
	 * 
	 * @param text The text of the button
	 */
	public JButton createNextButton(String text){
		JButton nextButton = new JButton(text);
		
	    nextButton.addActionListener(new ActionListener(){	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		if(currentPanel + 1 < panelList.size()){	// check if there is a next panel
	    			if(checkInputBounds(currentPanel)){		// check if the input is within bounds
	    				sendQuestionData(currentPanel);
	    				changePanel(panelList.get(currentPanel), panelList.get(currentPanel+1));
	    			}
	    			else{
	    				// The input is not within bounds
	    				JOptionPane.showMessageDialog(new JFrame(),
	    					    "Either you did not enter a value or the value is not whithin the bounds",
	    					    "Input error",
	    					    JOptionPane.WARNING_MESSAGE);
	    			}
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
	    		  if(currentPanel - 1 >= 0 ){		// check if there is a previous panel
	    			  removeLastAnswer();
	    			  changePanel(panelList.get(currentPanel), panelList.get(currentPanel-1));
	    		  }else{
	    			  System.out.println("No previous panel");
	    		  }
	    	  }
	    	});
	    return prevButton;
		
	}

}
