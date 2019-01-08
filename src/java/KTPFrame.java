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
	private int familyMember;
	private boolean previous;
	private List<Question> originalQuestionsList;

	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	KTPFrame(List<Question> questionsList, FloraController fc){
		super("Genetic disorder referal assesment");
		this.questionsList = questionsList;
    this.originalQuestionsList = new ArrayList<Question>();
		this.originalQuestionsList.addAll(questionsList);
		this.fc = fc;
		answeredQuestions = new ArrayList<Question>();
		this.currentPanel = 0;
		this.familyMember = 0;
		this.previous = false;
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
	    		
	    		//remove answers from log
	    		
	    		for(Question q : originalQuestionsList){
	    				q.resetAnswers();
	    		}
	    							
	    		fc.loadModel();
					questionsList.clear();
					questionsList.addAll(originalQuestionsList);
	    		setTitle("Genetic disorder risk assesment");	// reset the title of the frame
			    setVisible(false);										// show the frame
	    		initGUI();								// re-init GUI
	    	}
	    });
	    
	    
	    
		this.add(containerPanel);
		this.pack();
		// this.setLocationByPlatform(true);
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
		
		
		this.pack();
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
	public boolean sendQuestionData(int current){
		previous = false;
		Question currentQ = this.questionsList.get(current);		// get current question
		currentQ.setAnswers();										// set the answers of that question
		this.answeredQuestions.add(currentQ);						// add answers to answer list
		this.histTextArea.setText(this.currentHist());				// set the history text panel
		JScrollBar vertical = this.histScrollPane.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );					// scroll the pane down
		transformKeyword(currentKeyword());
		updateQuestions();
		if(fc.askQuery("goToDoctor(?GTD)").equals("true")){
			showResult(true);
			return true;
		}
		if(fc.askQuery("goToDoctor(?GTD)").equals("false") || questionsList.size()==answeredQuestions.size()){
			showResult(false);
			return true;
		}
		this.printAnswerList();										// print answers for debug
		return false;
	}


	private void transformKeyword(String keyword){
		switch (keyword){
			case "ageSelf": 
				if(Integer.parseInt(currentAnswer())>=70){
					fc.addFact("user[age->70]");
				}
				break;
			case "diseaseType": addType("Cancer" ,"Heart_and_vasculair_disease" ,"Mental_disability");
				break;
			case "cancerType": addType("Breastcancer", "Coloncancer", "Other_types_of_cancer");
				break;
			case "ageCancer25": ifKnowledge("[age->25]");
				break;
			case "genderBreast": ifKnowledge("[gender->male, type->breast]");
				break;
			case "ageBreast40": loopKnowledge(40, "breast", 1);
				break;
			case "ageBreast50": loopKnowledge(50, "breast", 1);
				break;
			case "restBreast": loopKnowledge("breast");
				break;
			case "ageColon501": loopKnowledge(50, "colon", 1);
				break;
			case "ageColon502": loopKnowledge(50, "colon", 2);
				break;
			case "ageColon701": loopKnowledge(70, "colon", 1);
				break;
			case "ageColon702": loopKnowledge(70, "colon", 2);
				break;
			case "ageColon80": loopKnowledge(80, "colon", 1);
				break;
			case "ageHeart45": ifKnowledge("[age->45, type->heart_and_vasculair, relation->1]");
				break;
			case "ageHeart50": loopKnowledge(50, "heart_and_vasculair", 1);
				break;
			case "ageHeart55": loopKnowledge(55, "heart_and_vasculair");
				break;
			case "disability1": ifKnowledge("[type->mental_disability, relation->1]");
				break;
			case "disability2": loopKnowledge("mental_disability", 2);
				break;
		}
	}

  private void updateQuestions(){
  	if(fc.askQuery("Cancer(?C)").equals("false")){
  		for (Iterator<Question> iter = questionsList.listIterator(); iter.hasNext(); ) {
    		Question q = iter.next();
    		if (q.getType().equals("cancer") || q.getType().equals("Breastcancer") || q.getType().equals("Coloncancer")){
    	    iter.remove();
    		}
  		}
  	}
  	if(fc.askQuery("Heart_and_vasculair_disease(?C)").equals("false")){
  		for (Iterator<Question> iter = questionsList.listIterator(); iter.hasNext(); ) {
    		Question q = iter.next();
    		if (q.getType().equals("Heart_and_vasculair_disease")){
    	    iter.remove();
    		}
  		}
  	}
  	if(fc.askQuery("Breastcancer(?C)").equals("false")){
  		for (Iterator<Question> iter = questionsList.listIterator(); iter.hasNext(); ) {
    		Question q = iter.next();
    		if (q.getType().equals("Breastcancer")){
    	    iter.remove();
    		}
  		}
  	}
  	if(fc.askQuery("Coloncancer(?C)").equals("false")){
  		for (Iterator<Question> iter = questionsList.listIterator(); iter.hasNext(); ) {
    		Question q = iter.next();
    		if (q.getType().equals("Coloncancer")){
    	    iter.remove();
    		}
  		}
 		}
  	if(fc.askQuery("Mental_disability(?C)").equals("false")){
  		for (Iterator<Question> iter = questionsList.listIterator(); iter.hasNext(); ) {
    		Question q = iter.next();
    		if (q.getType().equals("Mental_disability")){
    	    iter.remove();
    		}
  		}
 		}
	}

	private void addType(String opt1, String opt2, String opt3){
		List<String> results = recentAnswer();
		for(int x=0; results.size()>x; x++){
			results.set(x, results.get(x).replace(" ", "_"));
		}
		int x=0;
		if(previous==false){
			while(results.size()>x){
				fc.addFact(results.get(results.size()-1-x) +"(true)");
				System.out.println(results.get(results.size()-1-x) +"(true)");
				x++;
			}
		 	if(!fc.askQuery(opt1+"(?C)").equals("true")){
		 		fc.addFact(opt1 + "(false)");
		 		System.out.println(opt1 + "(false)");
			}
			if(!fc.askQuery(opt2+"(?C)").equals("true")){
		 		fc.addFact(opt2 + "(false)");
		 		System.out.println(opt2 + "(false)");
			}
			if(!fc.askQuery(opt3+"(?C)").equals("true")){
		 		fc.addFact(opt3 + "(false)");
		 		System.out.println(opt3 + "(false)");
			}
		} else {
			 	if(fc.askQuery(opt1+"(?C)").equals("true")){
					fc.removeFact(opt1 + "(true)");
		 			System.out.println(opt1 + "(true)");
				} else {
					fc.removeFact(opt1 + "(false)");
			 		System.out.println(opt3 + "(false)");
				}
			 	if(fc.askQuery(opt2 + "(?C)").equals("true")){
					fc.removeFact(opt2 + "(true)");
		 			System.out.println(opt2 + "(true)");
				} else {
					fc.removeFact(opt2 + "(false)");
		 			System.out.println(opt2 + "(false)");
				}
			 	if(fc.askQuery(opt3 + "(?C)").equals("true")){
					fc.removeFact(opt3 + "(true)");
		 			System.out.println(opt3 + "(true)");

				} else {
					fc.removeFact(opt3 + "(false)");
		 			System.out.println(opt3 + "(false)");
				}
				questionsList.clear();
				questionsList.addAll(originalQuestionsList);
			}
	}
	
	private void loopKnowledge(int age, String type, int relationship){
		int total=Integer.parseInt(currentAnswer());
		for(int x=0; x<total; x++){
			if(previous==false){
				fc.addFact(familyMember +"[age->" + age + ", type->" + type +",relation->" + relationship + "]");
				familyMember++;
			} else {
				familyMember--;					
				fc.removeFact(familyMember +"[age->" + age + ", type->" + type +",relation->" + relationship + "]");
			}
		}
	}

	private void loopKnowledge(int age, String type){
		int total=Integer.parseInt(currentAnswer());
		for(int x=0; x<total; x++){
			if(previous==false){
				fc.addFact(familyMember +"[age->" + age + ", type->" + type + "]");
				familyMember++;
			} else {
				familyMember--;					
				fc.removeFact(familyMember +"[age->" + age + ", type->" + type + "]");
			}
		}
	}


	private void loopKnowledge(String type, int relationship){
		int total=Integer.parseInt(currentAnswer());
		for(int x=0; x<total; x++){
			if(previous==false){
				fc.addFact(familyMember +"[type->" + type +",relation->" + relationship + "]");
				familyMember++;
			} else {
				familyMember--;					
				fc.removeFact(familyMember +"[type->" + type +",relation->" + relationship + "]");
			}
		}
	}

	private void loopKnowledge(String type){
		int total=Integer.parseInt(currentAnswer());
		for(int x=0; x<total; x++){
			if(previous==false){
				fc.addFact(familyMember +"[type->" + type + "]");
				familyMember++;
			} else {
				familyMember--;					
				fc.removeFact(familyMember +"[type->" + type + "]");
			}
		}
	}

	private void ifKnowledge(String yes){
		if(currentAnswer().equals("Yes")){
			if(previous==false){
				fc.addFact(familyMember + "" + yes);
				familyMember++;
			} else {
				familyMember--;
				fc.removeFact(familyMember + "" + yes);
			}
		}
	}
	/**
	 * Removes the last answer from the answered questions
	 * and removes the answer from the question object.
	 * Also removes the answers from the history panel.
	 */
	public void removeLastAnswer(){
		previous=true;
		transformKeyword(currentKeyword());
		updateQuestions();
		// // System.out.println(currentAnswer());
		// String s ="s";
		// if(currentKeyword().equals("ageSelf")){
		// 	s = "user[age->" + currentAnswer() + "]";
		// }
		// fc.removeFact(s);
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
 							if(!sendQuestionData(currentPanel)){
	    				showNextPanel();
	    				}
	    			}
	    			else{
	    				// The input is not within bounds
	    				JOptionPane.showMessageDialog(new JFrame(),
	    					    "Either you did not enter a value or the value is not whithin the bounds",
	    					    "Input error",
	    					    JOptionPane.WARNING_MESSAGE);
	    			}
	    		}else{
						if(!sendQuestionData(currentPanel)){
		    			showResult(false);
		    		}
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
	    			  showPrevPanel();				// show previous panel
	    			  removeLastAnswer();			// remove answer from the answerList
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
				System.out.println(s);
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
	public List<String> recentAnswer(){
		return answeredQuestions.get(answeredQuestions.size()-1).getAnswers();
	}

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
