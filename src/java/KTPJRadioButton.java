package src.java;

import javax.swing.JRadioButton;

public class KTPJRadioButton extends JRadioButton implements KTPJComponent {
	
	public KTPJRadioButton(String s){
		super(s);		
	}

	public String getAnswer() {
		if(this.isSelected()){
			System.out.println("Found a selected radiobutton = "+ this.getText());
			return this.getText();
		}
		return null;
	}

}
