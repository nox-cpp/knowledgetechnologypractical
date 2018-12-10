/**
 * 
 */
package src.java;

import java.util.*;
import javax.swing.*;

/**
 * @author s2410540
 *
 */
public class Question {
	
	public String question;
	public List<JComponent> componentList;
	private String answer;
	
	Question(String question){
		this.question = question;
		
	}
	
	Question(String question, List<JComponent> componentList){
		this.question = question;
		this.componentList = componentList;
		this.answer = null;
	}
	
	/**
	 * Adds component to question
	 * @param c Component to be added
	 */
	public void addComponent(JComponent c){
		this.componentList.add(c);
	}
	
	/**
	 * Returns a string representatin of the object
	 */
	public String toString(){
		return "Question object: " + this.question + " with "+ this.componentList.size() + " components" ;
	}
	
	
	public void setAnswer(String a){
		this.answer = a;
	}
	
	public String getAnswer(){
		
		
		for(JComponent item : componentList){
			if(item instanceof JTextField){
				this.setAnswer("The answer is in a radiobutton");
			}
			else if(item instanceof JRadioButton){
				
				this.setAnswer("The answer is in a textfield");
			}
			else if(item instanceof JButton){
				this.setAnswer("The answer is in a JButton");
			}
			
		}
		
		
		return this.answer;
	}
}
