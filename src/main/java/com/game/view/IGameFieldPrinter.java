package com.game.view;

import java.util.Observable;
import java.util.Observer;

/**
 * Class with this interface print the game field for the users. For this it use the observer
 * pattern.
 * 
 * @author Bjoern Hullmann
 */
public interface IGameFieldPrinter extends Observer {
	
	/**
	 * This method updates the view for the players
	 */
	@Override
	public void update(final Observable o, final Object arg);
}
