/**
 * 
 */
package src.java;

import java.text.ParseException;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * @author s2410540
 *
 */
public class KTPJSpinner extends JSpinner implements KTPJComponent {


	/**
	 * Constructor
	 * @param arg0 SpinnerModel that determines behavior of the spinner
	 */
	public KTPJSpinner(SpinnerModel arg0) {
		super(arg0);
	}


	public String getAnswer() {
		//System.out.println("Found a value for the spinner = " + this.getValue());
		return this.getValue().toString();
	}

	/**
	 * This function always returns true, because a spinner always has a valid value.
	 */
	public boolean isWithinBounds() {
		return true;
	}

}
