/**
 * 
 */
package src.java;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.*;

import javax.swing.*;

/**
 * @author s2410540
 * The question class is very important for our project.
 * The questions are read from a xml file.
 * It contains the following important fields:
 * 
 * keyword			: 	the keyword corresponding to the question, used for interaction between
 * 						flora and java.
 * 
 * question			: 	the string that is displayed.
 * 
 * componentList	:	The list of all Swing components that are used to answer the question,
 * 						they can only be of one type (KTPJCheckBox, KTPJSpinner or KTPJRadioButton).
 * 						They also implement the KTPJComponent interface.
 * 
 * extraExplanation	:	The string that is displayed in a dialog when the user asks for extra
 * 						information to clarify the question.
 * 
 * panel			:	JPanel generated in the question object itself to display components.
 * 
 * answer			:	List of Strings that contain the answer(s) to this question.
 */
public class Question {
	
	public String keyword;
	public String question;
	public List<KTPJComponent> componentList;
	public String extraExplanation;
	private JPanel panel;
	private List<String> answer;
	

	/**
	 * Constructor for Question object
	 * @param keyword the keyword used for the flora, java interaction.
	 * @param question The string that contains the question itself.
	 * @param extra The string that has extra explanation about the question. TODO what if null -> standard message?
	 * @param componentList List of KTPJComponents that you want to show. (Radiobuttons etc)
	 */
	Question(String keyword, String question, String extra, List<KTPJComponent> componentList){
		// TODO check if keyword is unique
		this.keyword = keyword;
		this.question = question;
		this.extraExplanation = extra;
		this.componentList = componentList;
		this.panel = new JPanel();
		this.answer = new ArrayList<String>();
		
		initPanel();
	}
	
	/**
	 * Initializes the panel. Adds the label containing the question to the panel,
	 * and every component.
	 */
	private void initPanel(){
		this.panel.setLayout(new GridLayout(0,1));
		// add label for the title
		JLabel question = new JLabel(this.question);
		panel.add(question);
		// add each component for q
		for(Iterator<KTPJComponent> i = this.componentList.iterator(); i.hasNext();){
			KTPJComponent item = i.next();
			//TODO mabey create new add method?
			panel.add((Component) item);
			
		}
	}
	
	
	/**
	 * Adds component to question
	 * @param c Component to be added
	 */
	public void addComponent(KTPJComponent c){
		this.componentList.add(c);
	}
	
	/**
	 * Returns a string representing of the object
	 */
	public String toString(){
		return "Question object: " + this.question + " with "+ this.componentList.size() + " components of type: " + this.componentList.get(0).toString();
	}
	
	
	/**
	 * Clears the answerList. This is called when the previous button is clicked.
	 */
	public void resetAnswers(){
		this.answer.clear();
	}
	
	
	/**
	 * Sets the answer list to the current answers in the component list.
	 * This is called when the next button is clicked.
	 */
	public void setAnswers(){
		for(KTPJComponent item : componentList){
			if(item.getAnswer() != null){
				this.answer.add(item.getAnswer());
			}
		}
	}

	/**
	 * @return The current answers to this question.
	 */
	public List<String> getAnswers(){
		return this.answer;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
}
