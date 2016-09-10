package model.figure;

import model.Player;

/**
 * A interface for the figure.
 * 
 * @author Bjoern Hullmann
 */
public interface IFigure {
	
	/**
	 * Get the type of the figure
	 * 
	 * @return The FigureType of the figure
	 */
	FigureType getType();
	
	/**
	 * Get the owner of the figure
	 * 
	 * @return The Player which own this figure
	 */
	Player getPlayer();
}
