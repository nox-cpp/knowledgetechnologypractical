/**
 * 	MainController.java
 * 	authors:
 *  Marnix Jansma, 23-11-18
 *  Gijs Hendriks (s2410540)
 *  Hidde
 *  
 *  To compile:
 *  javac -d build/main -classpath "$FLORADIR"/java/flora2java.jar:"$FLORADIR"/java/interprolog.jar: src/java/*.java
 *  javac -classpath "$FLORADIR/java/flora2java.jar":"$FLORADIR/java/interprolog.jar": src/java/helloWorld.java
 *  
 *  To run:
 *  java -DPROLOGDIR=$PROLOGDIR -DFLORADIR=$FLORADIR -classpath $FLORADIR/java/flora2java.jar:$FLORADIR/java/interprolog.jar:./ src/java/helloWorld
 */


package src.java;
import java.io.File;
import java.io.IOException;
// import java.awt.List;
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import javax.swing.*;	//swing

public class MainController{
	public static void main(String[] args) {
				
			FloraController floraController = new FloraController();
			System.out.println("Flora-2 session started");
			floraController.loadModel();							// Load the knowledgebase into the Flora session
			
			
			KTPFrame frame = new KTPFrame(readQuestionsXML());		// Create a frame for the program
			System.out.println("Made a Frame");
			
			floraController.closeSession();							// close session
		}
	
	
	
private static List<Question> readQuestionsXML(){
		
		
		System.out.println("starting xml jdom");
		try{
			List<Question> questionsList = new ArrayList<Question>();
			File inputFile = new File("src/resource/questions.xml");			// new file
	        SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(inputFile);
	        Element classElement = document.getRootElement();					// get root
	        System.out.println("Root element :" + classElement.getName());		// print root
	        List<Element> qList = classElement.getChildren();					// get list of questions
	        System.out.println("Found " + qList.size() + " questions in " + inputFile.getAbsolutePath());
	        
	        for(int temp = 0; temp< qList.size(); temp++){						// for every question
	        	Element q = qList.get(temp);
	        	// make a new question object
	        	String keyword = q.getChild("keyword").getText();
	        	String question = q.getChild("question").getText();
	        	String extra = null;
	        	if(q.getChild("extra") != null){
	        		extra = q.getChild("extra").getText();
	        	}
	        	List<KTPJComponent> componentList = new ArrayList<KTPJComponent>();
	        	
	        	//System.out.println(q.getAttribute("inputtype").toString());
	        	

	        	
	        	// if attribute inputtype == spinner
	        	if(q.getAttribute("inputtype").toString().equals(new Attribute("inputtype", "spinner").toString())){
	        		//System.out.println("spinner");
	        		String[] strings = q.getChild("spinner").getText().split(",");		// split into strings
	        		int[] ints = new int[4];											// make new int array
	        		int i = 0;
	        		for(String str: strings){
	        			ints[i] = Integer.parseInt(str); 				// parse strings to ints
	        			i++;
	        		}
	        		
	        		//make new spinner
	        		SpinnerModel model = new SpinnerNumberModel(ints[0], ints[1], ints[2], ints[3]);     
	        		KTPJSpinner spinner = new KTPJSpinner(model);
	        		componentList.add(spinner);
	        		
	        		
	        	// if attribute inputtype == checkbox
	        	}else if(q.getAttribute("inputtype").toString().equals(new Attribute("inputtype", "checkbox").toString())){
	        		//System.out.println("checkbox");
	        		
	        		List<Element> list = q.getChildren("checkbox"); 				// get all checkboxes
	        		for (Element e : list){					// for each checkbox
	        			//System.out.println(e.toString());
	        			KTPJCheckBox but = new KTPJCheckBox(e.getText());			// make new checkbox
	        			componentList.add(but);										// add checkbox to the component list
	        		}
	        		
	        		
	        		
	        		
	        	
	        	// if attribute inputtype == radiobutton
	        	}else if(q.getAttribute("inputtype").toString().equals(new Attribute("inputtype", "radiobutton").toString())){
	        		//System.out.println("radiobutton");
	        		
	        		List<Element> list = q.getChildren("radiobutton"); 				// get all radiobuttons
	        		ButtonGroup group = new ButtonGroup();							// make new button group
	        		for (Element e : list){					// for each checkbox
	        			//System.out.println(e.toString());
	        			KTPJRadioButton but = new KTPJRadioButton(e.getText());		// make new button
	        			group.add(but);												// add button to the button group
	        			componentList.add(but);										// add button to the component list
	        		}
	        		
	        		
	        		
	        	}else{
	        		System.err.println("Something went wrong in parsing the input types in the xml file.");
	        	}
	        	
	        	
	        	
	        	
	        	
	        	Question newq = new Question(keyword, question, extra, componentList);			// construct the new question
	        	questionsList.add(newq);														// add question to the questionsList
	        }
	    
	    return questionsList;
	        
	        
		}catch(JDOMException e) {
			e.printStackTrace();
	    }catch(IOException ioe) {
	    	ioe.printStackTrace();
	    }
		System.err.println("Something went wrong reading the xml file.");
		System.exit(0);
		return null;
	}

}

