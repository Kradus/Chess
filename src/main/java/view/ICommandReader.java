package view;

/**
 * This interface is for the commands, which a user can use. Classes with this interface should be
 * handled the commands from the user.
 * 
 * @author Bjoern Hullmann
 */
public interface ICommandReader {
	
	/**
	 * With the method the command reader start and begin to read the input from the user.
	 */
	public void start();
}
