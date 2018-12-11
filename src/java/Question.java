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
	public List<KTPJComponent> componentList;
	private String answer;
	// TODO Add an extra button to show extra explanation for this question
	

	/**
	 * Constructor for Question object
	 * @param question The string that contains the question itself.
	 * @param componentList List of KTPJComponents that you want to show. (Radiobuttons etc)
	 */
	Question(String question, List<KTPJComponent> componentList){
		this.question = question;
		this.componentList = componentList;
		this.answer = null;
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
		return "Question object: " + this.question + " with "+ this.componentList.size() + " components" ;
	}
	
	
	public void setAnswer(String a){
		this.answer = a;
	}
	
	
	/**
	 * Returns the Answer currently given by the components in the componentList.
	 * @return String with the answer
	 */
	public String getAnswer(){
		
		//System.out.println("The current question = " + this.question + "\n" +
		//		"This question has " + this.componentList.size() + " items");
		
		for(KTPJComponent item : componentList){
			//System.out.println("answer = " + item.getAnswer());
			//System.out.println("getAnswer is called from question current component = " + item);
			if(item.getAnswer() != null){
				this.answer = item.getAnswer();
			}			
		}
		
		
		return this.answer;
	}
}
