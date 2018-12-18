/*
 * 
 * TODO Use grouplayout?
 * TODO JSON reading questions
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

	
	 
	/**
	 * Constructor of ktpFrame. Sets the title and initializes fields.
	 */
	KTPFrame(){
		super("Genetic disorder risk assesment");
		questionsList = new ArrayList<Question>();
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
		
		
		
	    
	    
		readQuestions();
	    
	    


		
		
	    
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
	    this.add(this.questionsList.get(0).getPanel()); // add the panel of the first question

	    
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    GridLayout layout = new GridLayout(0,1);
	    this.setLayout(layout);
	    this.setSize(1000, 500);
	    this.setLocation(200, 200);
	    //this.pack();
	    this.setVisible(true);
	    
	    
	    this.readQuestionsXML();
	    
	}
	
	
	
	
	
	/**
	 * Adds all the questions for the model to the panels and add those to the panel list.
	 * TODO make this less ugly
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
	
	
	
	
	private void readQuestionsXML(){
		
		
		System.out.println("starting xml jdom");
		
		/*try {
	         File inputFile = new File("src/resource/questions.xml");
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(inputFile);
	         System.out.println("Root element :" + document.getRootElement().getName());
	         Element classElement = document.getRootElement();

	         List<Element> studentList = classElement.getChildren();
	         System.out.println("----------------------------");

	         for (int temp = 0; temp < studentList.size(); temp++) {    
	            Element student = studentList.get(temp);
	            System.out.println("\nCurrent Element :" 
	               + student.getName());
	            Attribute attribute =  student.getAttribute("rollno");
	            System.out.println("Student roll no : " 
	               + attribute.getValue() );
	            System.out.println("First Name : "
	               + student.getChild("firstname").getText());
	            System.out.println("Last Name : "
	               + student.getChild("lastname").getText());
	            System.out.println("Nick Name : "
	               + student.getChild("nickname").getText());
	            System.out.println("Marks : "
	               + student.getChild("marks").getText());
	         }
	      } catch(JDOMException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }*/
		
		try{
			File inputFile = new File("src/resource/questions.xml");
	        SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(inputFile);
	        Element classElement = document.getRootElement();					// get root
	        System.out.println("Root element :" + classElement.getName());		// print root
	        List<Element> qList = classElement.getChildren();					// get list of questions
	        System.out.println("Found " + qList.size() + " questions in " + inputFile.getAbsolutePath());
	        
	        for(int temp = 0; temp< qList.size(); temp++){
	        	Element q = qList.get(temp);
	        	// make a new question
	        	String keyword = q.getChild("keyword").getText();
	        	String question = q.getChild("question").getText();
	        	String extra = null;
	        	if(q.getChild("extra") != null){
	        		extra = q.getChild("extra").getText();
	        	}
	        	List<KTPJComponent> componentList = new ArrayList<KTPJComponent>();
	        	
	        	//System.out.println(q.getAttribute("inputtype").toString());
	        	

	        	
	        	
	        	if(q.getAttribute("inputtype").toString().equals(new Attribute("inputtype", "spinner").toString())){
	        		//System.out.println("spinner");
	        		String[] strings = q.getChild("spinner").getText().split(",");
	        		int[] ints = new int[4];
	        		int i = 0;
	        		for(String str: strings){
	        			ints[i] = Integer.parseInt(str); 
	        			i++;
	        		}
	        		
	        		SpinnerModel model = new SpinnerNumberModel(ints[0], ints[1], ints[2], ints[3]);     
	        		KTPJSpinner spinner = new KTPJSpinner(model);
	        		componentList.add(spinner);
	        		
	        	}else if(q.getAttribute("inputtype").toString().equals(new Attribute("inputtype", "checkbox").toString())){
	        		//System.out.println("checkbox");
	        		
	        	}else if(q.getAttribute("inputtype").toString().equals(new Attribute("inputtype", "radiobutton").toString())){
	        		//System.out.println("radiobutton");
	        		
	        	}else{
	        		System.out.println("Something went wrong");
	        	}
	        	
	        	
	        	
	        	
	        	
	        	Question newq = new Question(keyword, question, extra, componentList);
	        	//String keyword, String question, String extra, List<KTPJComponent> componentList
	        }
	        
	        
	        
		}catch(JDOMException e) {
			e.printStackTrace();
	    }catch(IOException ioe) {
	    	ioe.printStackTrace();
	    }
		
		
	}
	
	
	/**
	 * !!!!!!!!!!!!!!!
	 * THIS FUNCTION DOES NOT WORK YET
	 * !!!!!!!!!!!!!!!
	 * 
	 * probally xml is better for this
	 * TODO covert to xml and finish this
	 * 
	 * 
	 * Reads the questions from questions.csv and convert them to question objects
	 */
	private void readQuestionsCSV(){
		String csvFile = "src/resource/questions.csv";
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
            	String[] arr = line.split(cvsSplitBy);
                System.out.println(Arrays.toString(arr)); //print each line
                
                List<KTPJComponent> compList = null;
                
                Question q = new Question(arr[0], arr[1], arr[2], compList);
                //String question, String extra, List<KTPJComponent> componentList
                

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
	 * Adds the new data from the panel to the answered questions
	 * @param current
	 */
	public void sendQuestionData(int current){
		//TODO update to flora
		Question currentQ = this.questionsList.get(current);
		currentQ.setAnswers();
		this.answeredQuestions.add(currentQ);
		this.printAnswerList();
		
	}
	
	/**
	 * Removes the last answer from the answered questions
	 * and removes the answer from the question object.
	 */
	public void removeLastAnswer(){
		//TODO update to flora
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
