/*
 * 
 * TODO Use other layout?
 * 
 */



package src.java;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class KTPFrame extends JFrame {
	public List<Question> questionsList;
	public List<Question> answeredQuestions;
	public int currentPanel;
	private JTextArea histTextArea;

	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	KTPFrame(List<Question> questionsList){
		super("Genetic disorder risk assesment");
		this.questionsList = questionsList;
		answeredQuestions = new ArrayList<Question>();
		
		this.currentPanel = 0;
		initGUI();
		
		
	}
	
	/**
	 * Initializes the GUI
	 */
	public void initGUI(){
		
		
		// Show warning
		JOptionPane.showMessageDialog(this,
			    "This application is made by students for a course as an exercise in Knowledge systems.\n" +
			    "Medical professionals where consulted during the making of this application,\n" +
			    "however any advice should be treated as given by some AI students (which it is).",
			    "Risk warning",
			    JOptionPane.WARNING_MESSAGE);
		
		
		
		//set layoutmanager
		this.setLayout(new GridLayout(0,2));
	    
	    // create previous, extra information and next buttons
	    JButton nextButton = createNextButton("Next");
	    JButton prevButton = createPrevButton("Previous");
	    JButton infoButton = createInfoButton("Extra explanation");
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(0,3));
	    buttonPanel.add(prevButton);
	    buttonPanel.add(infoButton);
	    buttonPanel.add(nextButton);
	    this.add(buttonPanel);
	    
	    //add history panel
	    histTextArea = new JTextArea(10, 50);
	    JScrollPane scrollPane = new JScrollPane(histTextArea); 
	    histTextArea.setEditable(false);
	    JPanel textPanel = new JPanel();
	    textPanel.add(scrollPane);
	    this.add(textPanel);
	    
	    
	    this.add(this.questionsList.get(0).getPanel()); // add the panel of the first question

	    
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //this.setSize(1000, 500);
	    this.setLocation(200, 200);
	    this.pack();
	    this.setVisible(true);
	    
	    
	    
	    
	}
	
	
	
	
	
	/**
	 * @deprecated
	 * Adds all the questions for the model to the panels and add those to the panel list.
	 * This function is now replaced by readQuestionsXML()
	 */
	private void readQuestions(){
		// first example question
		List<KTPJComponent> lst = new ArrayList<KTPJComponent>();
		SpinnerModel model = new SpinnerNumberModel(18, 18, 120, 1);     
		KTPJSpinner spinner = new KTPJSpinner(model);
		lst.add(spinner);
		Question firstq = new Question("age", "What is your age?", "" ,lst);
	    
	    
	    
	    //second example question
	    List<KTPJComponent> lst2 = new ArrayList<KTPJComponent>();
	    KTPJRadioButton man = new KTPJRadioButton("Man");
	    man.setSelected(true);
	    KTPJRadioButton woman = new KTPJRadioButton("Woman");
	    ButtonGroup group = new ButtonGroup();
	    group.add(man);
	    group.add(woman);
		lst2.add(man);
		lst2.add(woman);
		Question secondq = new Question("gender","What is your gender?", "", lst2);
	    
	    
	    //fourth example question
	    List<KTPJComponent> lst4 = new ArrayList<KTPJComponent>();
	    KTPJCheckBox cancer = new KTPJCheckBox("Cancer");
	    KTPJCheckBox heart = new KTPJCheckBox("Heart and vascular disease");
	    KTPJCheckBox mental = new KTPJCheckBox("Mental illness");
		lst4.add(cancer);
		lst4.add(heart);
		lst4.add(mental);
		Question fourthq = new Question("type", "Which type of disease?", "blah blah blah" , lst4);
	    
	    
	    //third example question
	    List<KTPJComponent> lst3 = new ArrayList<KTPJComponent>();
	    KTPJRadioButton yes = new KTPJRadioButton("Yes");
	    KTPJRadioButton no = new KTPJRadioButton("No");
	    ButtonGroup group2 = new ButtonGroup();
	    group2.add(yes);
	    group2.add(no);
		lst3.add(yes);
		lst3.add(no);
		Question thirdq = new Question("family", "Do you have disease in the family?", "Diseases include: cancer blah" , lst3);
		
		//add the questions to the list
		this.questionsList.add(firstq);
		this.questionsList.add(secondq);
		this.questionsList.add(fourthq);
		this.questionsList.add(thirdq);

		
	}
	
	
	
	/**
	 * Shows the next panel in the question list.	 * 
	 * Beware, checks if this is possible must be done before!
	 */
	public void showNextPanel(){
		this.remove(this.questionsList.get(currentPanel).getPanel());	// remove current panel
		this.currentPanel++;
		this.add(this.questionsList.get(currentPanel).getPanel());		// add next panel
		invalidate();
		validate();
		repaint();
		
	}
	
	/**
	 * Shows the previous panel in the question list.
	 * Beware, checks if this is possible must be done before!
	 */	
	public void showPrevPanel(){
		this.remove(this.questionsList.get(currentPanel).getPanel());	// remove current panel
		this.currentPanel--;
		this.add(this.questionsList.get(currentPanel).getPanel());		// add next panel
		invalidate();
		validate();
		repaint();
		
	}
	
	
	/**
	 * Returns a string to be displayed.
	 * This string shows all the currently given answers.
	 * @return
	 */
	public String currentHist(){
		String s = "";
		
		for (Question q : this.answeredQuestions){
			for(String ans : q.getAnswers()){
				s += q.question + "\t" + ans + "\n";
			}
			
		}
		return s;
	}
	
	
	/**
	 * Adds the new data from the panel to the answered questions.
	 * Also adds this to the history panel.
	 * @param current
	 */
	public void sendQuestionData(int current){
		//TODO update to flora
		Question currentQ = this.questionsList.get(current);
		currentQ.setAnswers();
		this.answeredQuestions.add(currentQ);
		//this.printAnswerList();
		System.out.println(this.currentHist());
		this.histTextArea.setText(this.currentHist());
		
	}
	
	/**
	 * Removes the last answer from the answered questions
	 * and removes the answer from the question object.
	 * Also removes the answers from the history panel.
	 */
	public void removeLastAnswer(){
		//TODO update to flora
		this.answeredQuestions.get(this.answeredQuestions.size() -1).resetAnswers();
		this.answeredQuestions.remove(this.answeredQuestions.size() -1);
		this.histTextArea.setText(this.currentHist());
	}
	
	
	/**
	 * Checks the input bounds of the current panel before sending the data to the controller.
	 * True is returned when a component has a legal value. 
	 * @param current The current panel
	 * @return Whether the inputs are within bounds.
	 */
	public boolean checkInputBounds(int current){
		for(KTPJComponent comp : this.questionsList.get(current).componentList){
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
	    		
	    		if(currentPanel + 1 < questionsList.size()){	// check if there is a next panel
	    			if(checkInputBounds(currentPanel)){		// check if the input is within bounds
	    				sendQuestionData(currentPanel);
	    				showNextPanel();
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
	    			  removeLastAnswer();			// remove answer from the answerList
	    			  showPrevPanel();				// show previous panel
	    			  printAnswerList();			// print the answer list so far
	    		  }else{
	    			  System.out.println("No previous panel");
	    		  }
	    	  }
	    });
	    return prevButton;
		
	}
	
	
	/**
	 * Creates the information button which, when clicked shows the extra information 
	 * provided by the question object.
	 * @param text The explanation to be shown.
	 * @return Retruns the button
	 */
	private JButton createInfoButton(String text){
		JButton bt = new JButton(text);
		bt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				String s = questionsList.get(currentPanel).extraExplanation;
				JOptionPane.showMessageDialog(new JFrame(), s);
			}
		});
		
		return bt;
	}
	
	
	
	/**
	 * Helper function to print the current list of given answers. Used for debug.
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
