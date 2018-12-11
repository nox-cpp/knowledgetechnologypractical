/**
 * 
 */
package src.java;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * @author s2410540
 *
 */
public class KTPJSpinner extends JSpinner implements KTPJComponent {


	/**
	 * @param arg0
	 */
	public KTPJSpinner(SpinnerModel arg0) {
		super(arg0);
	}

	/**
	 * 
	 */
	public String getAnswer() {
		System.out.println("Found a value for the spinner = " + this.getValue());
		return this.getValue().toString();
	}

}
