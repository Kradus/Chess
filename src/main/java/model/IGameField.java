package model;

import java.util.function.Consumer;

import model.figure.IFigure;

/**
 * The interface for the game field. Classes with this interface should hold all figure and there
 * positions.
 * 
 * @author Bjoern Hullmann
 */
public interface IGameField {
	
	/**
	 * The height and the width of the game field.
	 */
	int FIELD_SIZE = 8;
	
	/**
	 * Set a figure on a field or remove it when figure = null
	 * 
	 * @param pos
	 *            the field position
	 * @param figure
	 *            the figure which should be set on this field
	 */
	void setFigure(Point pos, IFigure figure);
	
	/**
	 * Get the Figure on the given position
	 *
	 * @param pos
	 *            The position from where you want the Figure
	 * @return The Figure from the given position
	 */
	IFigure getFigure(Point pos);
	
	/**
	 * Increment the counter how often no pawn was set or no figure was hit.
	 */
	void incrementTurnsAfterHitOrMoveAPawn();
	
	/**
	 * Get the number of turns where no pawn was set and no figure was hit.
	 * 
	 * @return the number of turns
	 */
	int getTurnsAfterHitOrMoveAPawn();
	
	/**
	 * Set the number of turns to zero, where no pawn was set and no figure was hit.
	 */
	void resetTurnsAfterHitOrMoveAPawn();
	
	/**
	 * Perform the given action for all Figure on the field. In the order left to right and then top
	 * to bottom.
	 * 
	 * @param consumer
	 *            The action which are perform for each figure.
	 */
	void forEach(Consumer<IFigure> consumer);
	
	/**
	 * Clone the GameField
	 * 
	 * @return a clone
	 */
	IGameField deepClone();
}
