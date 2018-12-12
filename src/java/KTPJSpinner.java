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
	 * @param arg0
	 */
	public KTPJSpinner(SpinnerModel arg0) {
		super(arg0);
	}

	/**
	 * 
	 */
	public String getAnswer() {
		//System.out.println("Found a value for the spinner = " + this.getValue());
		return this.getValue().toString();
	}

	/**
	 * TODO This does not work yet!!
	 */
	public boolean isWithinBounds() {
		try {
			this.commitEdit();
			return true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
