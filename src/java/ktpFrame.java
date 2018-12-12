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
	public List<Question> answeredQuestions;
	public int currentPanel;

	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	ktpFrame(){
		super("Genetic disorder risk assesment");
		panelList = new ArrayList<ktpPanel>();
		answeredQuestions = new ArrayList<Question>();
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
		Question firstq = new Question("What is your age?", "" ,lst);
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
		Question secondq = new Question("What is your gender?", "", lst2);
	    ktpPanel second = new ktpPanel(secondq);
	    
	    
	    //fourth example question
	    List<KTPJComponent> lst4 = new ArrayList<KTPJComponent>();
	    KTPJCheckBox cancer = new KTPJCheckBox("Cancer");
	    KTPJCheckBox heart = new KTPJCheckBox("Heart and vascular disease");
	    KTPJCheckBox mental = new KTPJCheckBox("Mental illness");
		lst4.add(cancer);
		lst4.add(heart);
		lst4.add(mental);
		Question fourthq = new Question("Which type of disease?", "blah blah blah" , lst4);
	    ktpPanel fourth = new ktpPanel(fourthq);
	    
	    
	    
	    
	    
	    //third example question
	    List<KTPJComponent> lst3 = new ArrayList<KTPJComponent>();
	    KTPJRadioButton yes = new KTPJRadioButton("Yes");
	    KTPJRadioButton no = new KTPJRadioButton("No");
	    ButtonGroup group2 = new ButtonGroup();
	    group2.add(yes);
	    group2.add(no);
		lst3.add(yes);
		lst3.add(no);
		Question thirdq = new Question("Do you have disease in the family?", "Diseases include: cancer blah" , lst3);
	    ktpPanel third = new ktpPanel(thirdq);
	    
	    

	    
	    

	    // add the panels to the panel list
	    this.panelList.add(first);
	    this.panelList.add(second);
	    this.panelList.add(fourth);
	    this.panelList.add(third);
	    
	    
	    // create next and previous buttons
	    JButton nextButton = createNextButton("Next");
	    JButton prevButton = createPrevButton("Previous");
	    JButton infoButton = createInfoButton("Extra explanation");
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(0,3));
	    buttonPanel.add(prevButton);
	    buttonPanel.add(infoButton);
	    buttonPanel.add(nextButton);
	    this.add(buttonPanel);
	    this.add(panelList.get(0));		// add the first panel

	    
	    
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
	
	
	/**
	 * Adds the new data from the panel to the answered questions
	 * @param current
	 */
	public void sendQuestionData(int current){
		Question currentQ = this.panelList.get(current).getQuestion();
		currentQ.setAnswers();
		this.answeredQuestions.add(currentQ);
		this.printAnswerList();
		
	}
	
	/**
	 * Removes the last answer from the answered questions
	 * and removes the answer from the question object.
	 */
	public void removeLastAnswer(){
		this.answeredQuestions.get(this.answeredQuestions.size() -1).resetAnswers();
		this.answeredQuestions.remove(this.answeredQuestions.size() -1);
	}
	
	
	/**
	 * Checks the input bounds of the current panel before sending the data to the controller.
	 * True is returned when a component has a legal value. 
	 * @param current The current panel
	 * @return Whether the inputs are within bounds.
	 */
	public boolean checkInputBounds(int current){
		for(KTPJComponent comp : this.panelList.get(current).getQuestion().componentList){
			if(comp.isWithinBounds()){
				return true;
			}
		}
		return false;
	}
	
	
	
	/** 
	 * Creates the next button with action listener. 
	 * When clicked the data from the current panel is added to this.answerList.
	 * Also the next panel is shown if the value entered is within bounds.
	 * 
	 * @param text The text of the button
	 */
	private JButton createNextButton(String text){
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

	private JButton createPrevButton(String text){
		
		JButton prevButton = new JButton(text);
		prevButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e){
	    		  if(currentPanel - 1 >= 0 ){		// check if there is a previous panel
	    			  removeLastAnswer();
	    			  changePanel(panelList.get(currentPanel), panelList.get(currentPanel-1));
	    			  printAnswerList();
	    		  }else{
	    			  System.out.println("No previous panel");
	    		  }
	    	  }
	    });
	    return prevButton;
		
	}
	
	
	private JButton createInfoButton(String text){
		JButton bt = new JButton(text);
		bt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				String s = panelList.get(currentPanel).getQuestion().extraExplanation;
				JOptionPane.showMessageDialog(new JFrame(), s);
			}
		});
		
		return bt;
	}
	
	
	
	/**
	 * Helper function to print the current list of given answers
	 */
	public void printAnswerList(){
		
		System.out.println("\n\nThe list so far: " + this.answeredQuestions.size());
		for(Question q : this.answeredQuestions){
			System.out.println(q.getAnswers());
			for (String s : q.getAnswers()){
				
				//System.out.println("the answer list of q = " + q.getAnswers().size() + s + "\t" + q.toString());
			}
		}
	}

}
