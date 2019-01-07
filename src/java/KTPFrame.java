/*
 * 
 * TODO Use other layout?
 * 
 */



package src.java;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;

public class KTPFrame extends JFrame {
	public List<Question> questionsList;
	public List<Question> answeredQuestions;
	public int currentPanel;
	private JScrollPane histScrollPane;
	private JTextArea histTextArea;
	private FloraController fc;
	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	KTPFrame(List<Question> questionsList, FloraController fc){
		super("Genetic disorder referal assesment");
		this.questionsList = questionsList;
		this.fc = fc;
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
			    "Medical professionals were consulted during the making of this application,\n" +
			    "however any advice should be treated as given by some AI students (which it is).",
			    "Risk warning",
			    JOptionPane.WARNING_MESSAGE);
		
		
		
		//set layoutmanager
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		this.setLayout(layout);
		//this.setLayout(new GridLayout(0,2));
	    
	    // create previous, extra information and next buttons
	    JButton nextButton = createNextButton("Next");
	    JButton prevButton = createPrevButton("Previous");
	    JButton infoButton = createInfoButton("Extra explanation");
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(0,3));
	    buttonPanel.add(prevButton);
	    buttonPanel.add(infoButton);
	    buttonPanel.add(nextButton);
	    // add the panel to the top left
	    c.gridx = 0;
	    c.gridy = 0;
	    this.add(buttonPanel, c);
	    
	    //add history panel
	    histTextArea = new JTextArea(10, 50);
	    histScrollPane = new JScrollPane(histTextArea);
	    histScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    histTextArea.setEditable(false);
	    JPanel textPanel = new JPanel();
	    textPanel.add(histScrollPane);
	    // add the panel to the top right
	    c.gridx = 1;
	    c.gridy = 0;
	    c.gridheight = 2;
	    this.add(textPanel,c);
	    
	    // add the panel of the first question
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridheight = 1;
	    this.add(this.questionsList.get(0).getPanel(), c); 

	    
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// exit application on closing
	    this.pack();												// size frame
	    this.setLocationByPlatform(true);							// set the frame in the middle of the screen
	    this.setVisible(true);										// show the frame
	    
	    
	    
	    
	}
	
	
	/**
	 * Shows the result in the interface.
	 * @param result Whether to go to the doctor
	 */
	public void showResult(boolean result){
		
		this.setTitle("Result of test");
		this.getContentPane().removeAll();			//remove all components
		
		JPanel containerPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		containerPanel.setLayout(layout);

		
		// Label with result
		String text;
		if(result)
			text = "Based on the answers you have given we advise you to seek contact with your doctor.\n" +
					"Tell him you did this test and ask if he can refer you to a clinical geneticist.\n" +
					"Please keep in mind this is not the advice of a professional and should be taken\n" +
					"with a grain of salt.";
		else
			text = 	"Base on the answers you have given we have not found sufficient evidence to suggest you\n" +
					"need to seek professional help.\n" +
					"Please keep in mind this is not the advice of a professional and should be taken\n" +
					"with a grain of salt.";
		JTextArea textLabel = new JTextArea(text);
		c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = GridBagConstraints.REMAINDER;				// last component in this row
	    containerPanel.add(textLabel, c);
	    
	    
	  //button to exit the program
	    JButton exitButton = new JButton("Exit");
	    
	    c.gridx = 1;
	    c.gridy = 1;
	    c.gridwidth = GridBagConstraints.REMAINDER;
	    containerPanel.add(exitButton, c);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.err.println("Goodbye!");
                System.exit(0);
            }
        });
	    
	    //button to go back to the beginning
	    JButton startOverButton = new JButton("Start over");
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.LINE_END;
	    containerPanel.add(startOverButton, c);
	    startOverButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		getContentPane().removeAll();			//remove all components
	    		answeredQuestions.clear();				//remove all answers
	    		currentPanel = 0;						//reset currenPanel counter
	    		setTitle("Genetic disorder risk assesment");	// reset the title of the frame
	    		initGUI();								// re-init GUI
	    	}
	    });
	    
	    
	    
		this.add(containerPanel);
		this.pack();
		//this.setLocationByPlatform(true);
		this.repaint();
	}
	
	
	

	
	
	
	/**
	 * Shows the next panel in the question list.	 * 
	 * Beware, checks if this is possible must be done before!
	 */
	public void showNextPanel(){
		this.remove(this.questionsList.get(currentPanel).getPanel());	// remove current panel
		this.currentPanel++;
		
		
		// add next panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
	    c.gridy = 1;
	    c.gridheight = 1;
		this.add(this.questionsList.get(currentPanel).getPanel(), c);
		
		
		
		this.invalidate();
		this.validate();
		this.repaint();
	}
	
	
	
	
	
	
	/**
	 * Shows the previous panel in the question list.
	 * Beware, checks if this is possible must be done before!
	 */	
	public void showPrevPanel(){
		this.remove(this.questionsList.get(currentPanel).getPanel());	// remove current panel
		this.currentPanel--;
		
		// add prev panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
	    c.gridy = 1;
	    c.gridheight = 1;
		this.add(this.questionsList.get(currentPanel).getPanel(), c);
		
		invalidate();
		validate();
		repaint();
		
	}
	
	

	
	
	/**
	 * Adds the new data from the panel to the answered questions.
	 * Also adds this to the history panel.
	 * @param current
	 */
	public void sendQuestionData(int current){
		String s = "";
		Question currentQ = this.questionsList.get(current);		// get current question
		currentQ.setAnswers();										// set the answers of that question
		this.answeredQuestions.add(currentQ);						// add answers to answer list
		this.histTextArea.setText(this.currentHist());				// set the history text panel
		JScrollBar vertical = this.histScrollPane.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );					// scroll the pane down
		// System.out.println(currentAnswer());
		// System.out.println(currentKeyword());
		// fc.addFact(transformKeyword(currentKeyword()));
		transformKeyword(currentKeyword());
		// if(currentKeyword().equals("ageSelf")){
			// s = "user[age->" + currentAnswer() + "]";
			// System.out.println(s);
			// fc.addFact(s);
		if(fc.askQuery("goToDoctor(?GTD)").equals("false")){
			System.out.println("Don't go");
		}
		if(fc.askQuery("goToDoctor(?GTD)").equals("true")){
			showResult();
		}
		// }
//		this.printAnswerList();										// print answers for debug
	}

	private void transformKeyword(String keyword){
		switch (keyword){
			case "ageSelf": fc.addFact("user[age->" + currentAnswer() + "]");
			case "ageCancer25": loopRelatives(25, "cancer");
			case "ageCancer25": loopRelatives(25, "cancer");
			case "ageBreast40": loopRelatives(40, "breast");
			case "ageBreast50": loopRelatives(50, "breast");
			case "ageColon50": loopRelatives(50, "colon");
			case "ageColon70": loopRelatives(70, "colon");
			case "ageBreast40": loopRelatives(40, "breast");
			case "ageBreast50": loopRelatives(50, "breast");
			case "ageColon50": loopRelatives(50, "colon");
			case "ageColon70": loopRelatives(70, "colon");
			case "genderBreast": if(currentAnswer().equals("Yes")){
				fc.addFact("1[gender->male, type->breast]");
				System.out.println("male");
			} else {
				fc.addFact("1[gender->female, type->breast]");
			}
			case "diseaseType": fc.addFact("General[type->" +currentAnswer() +"]");
			case "cancerType": fc.addFact("General[cancerType->" +currentAnswer() +"]");
		}
	}

	private void loopRelatives(int age, String type){
		int total=Integer.parseInt(currentAnswer());
		for(int x=0; x<total; x++){
			fc.addFact(x+"[age->" + age + ", type->" + type + "]");
		}
	}
	/**
	 * Removes the last answer from the answered questions
	 * and removes the answer from the question object.
	 * Also removes the answers from the history panel.
	 */
	public void removeLastAnswer(){
		// System.out.println(currentAnswer());
		String s ="s";
		if(currentKeyword().equals("ageSelf")){
			s = "user[age->" + currentAnswer() + "]";
		}
		fc.removeFact(s);
		this.answeredQuestions.get(this.answeredQuestions.size() -1).resetAnswers();
		this.answeredQuestions.remove(this.answeredQuestions.size() -1);
		this.histTextArea.setText(this.currentHist());
		JScrollBar vertical = this.histScrollPane.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );					// scroll the pane down
		
		this.printAnswerList();										// print answers for debug
	}
	
	
	/**
	 * Checks the input bounds of the current panel before sending the data to the controller.
	 * True is returned when a co	at src.java.KTPFrame$1.actionPerformed(KTPFrame.java:301)
mponent has a legal value. 
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
	 * Returns a string to be displayed.
	 * This string shows all the currently given answers.
	 * @return
	 */
	public String currentAnswer(){
		String s = "";
		
		for (Question q : this.answeredQuestions){
			s = q.getLatestAnswer();			
		}
		return s;
	}

	public String currentKeyword(){
		String s = "";
		
		for (Question q : this.answeredQuestions){
			s = q.getKeyword();			
		}
		return s;
	}

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
	 * Helper function to print the current list of given answers. Used for debug.
	 */
	public void printAnswerList(){
		
		System.out.println("\n\nThe list so far: " + this.answeredQuestions.size() + "\n");
		System.out.println(this.currentHist());
	}

}
