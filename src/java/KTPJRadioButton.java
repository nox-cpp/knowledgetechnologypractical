package src.java;

import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.JRadioButton;

public class KTPJRadioButton extends JRadioButton implements KTPJComponent {
	
	public KTPJRadioButton(String s){
		super(s);		
	}

	public String getAnswer() {
		if(this.isSelected()){
			//System.out.println("Found a selected radiobutton = "+ this.getText());
			return this.getText();
		}
		return null;
	}
	
	/**
	 * Returns whether the one of the buttons in the group is checked 
	 */
	public boolean isWithinBounds(){
		ButtonGroup group = ((DefaultButtonModel)this.getModel()).getGroup();
		return (group.getSelection() != null);
	}

}
