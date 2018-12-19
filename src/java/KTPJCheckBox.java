package src.java;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class KTPJCheckBox extends JCheckBox implements KTPJComponent {


	public KTPJCheckBox(String arg0) {
		super(arg0);
	}


	public KTPJCheckBox(String arg0, boolean arg1) {
		super(arg0, arg1);
	}


	public String getAnswer() {
		if(this.isSelected()){
			return this.getText();
		}
		return null;
	}

	/**
	 * Returns whether this checkbox is selected
	 */
	public boolean isWithinBounds() {
		return this.isSelected();
	}

}
