package src.java;

public interface KTPJComponent {
	
	/**
	 * Abstract method that returns the answer
	 * @return Answer string
	 */
	public String getAnswer();
	
	/**
	 * Abstract method that returns whether the input is within bounds.
	 * @return 
	 */
	public boolean isWithinBounds();
}
