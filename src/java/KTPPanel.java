package src.java;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Iterator;
import javax.swing.*;

// TODO merge this class with Question.java?
@SuppressWarnings("serial")
public class KTPPanel extends JPanel {

	private Question question;


	KTPPanel(Question q){
		this.question = q;
		initPanel(q);
		
	}
	
	
	/**
	 * Returns the Question object of this panel
	 * @return Question object
	 */
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * initiate the panel
	 * @param q The question to be displayed by the panel.
	 */
	void initPanel(Question q){
		this.setLayout(new GridLayout(0,1));
		// add label for the title
		JLabel question = new JLabel(q.question);
		this.add(question);
		// add each component for q
		for(Iterator<KTPJComponent> i = q.componentList.iterator(); i.hasNext();){
			KTPJComponent item = i.next();
			//TODO mabey create new add method?
			this.add((Component) item);
			
		}
		

	}

}
