package com.game.view.commands;

/**
 * Interface for a command.
 * 
 * @author Bjoern Hullmann
 */
public interface ICommand {
	
	/**
	 * Get the command name.
	 * 
	 * @return the command name
	 */
	String getCommand();
	
	/**
	 * Do the action from the command.
	 * 
	 * @param params
	 *            the parameters, which the player enters
	 */
	void doAction(String[] params);
}
