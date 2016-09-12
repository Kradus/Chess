package com.game.view;

/**
 * This interface is for the commands, which an user can use. Classes with this interface should be
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
