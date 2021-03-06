package com.game.model.figure;

/**
 * A figure with the status if it was move.
 * 
 * @author Bjoern Hullmann
 */
public interface IFigureWithMoveStatus extends IFigure {
	
	/**
	 * Get the info if the figure was moved
	 * 
	 * @return true when the figure was moved
	 */
	boolean wasMoved();
}
