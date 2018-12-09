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
	
	Question(String question){
		this.question = question;
		
	}
	
	Question(String question, List<JComponent> componentList){
		this.question = question;
		this.componentList = componentList;
		
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
}
