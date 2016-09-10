package com.game.controller.moveHandler;

import com.game.controller.Move;
import com.game.controller.MoveInPointFigurePairs;
import com.game.model.Point;
import com.game.model.figure.FigureType;

/**
 * Classes with this interface can check and translate move from a specific type.
 * 
 * @author Bjoern Hullmann
 */
public interface IMoveHandler {
	
	/**
	 * Check for a figure if it is a valid move. This not only check if he can hit the king. It not
	 * check if the king is after this in check.
	 * 
	 * @param move
	 *            The move which should be check.
	 * @return true if it a valid move
	 */
	boolean isItAValidMove(Move move);
	
	/**
	 * Translate the move in MoveInPointFigurePairs
	 * 
	 * @param move
	 *            the move which should be translated
	 * @return The MoveInPointFigurePairs object where every position change is set
	 */
	MoveInPointFigurePairs translateMove(Move move);
	
	/**
	 * Get the FigureType for which this MoveHandler is
	 * 
	 * @return The FigureType for which this is
	 */
	FigureType getFigureType();
	
	/**
	 * Check if the specific figure type has a turn on the actual game field
	 * 
	 * @param from
	 *            the position where the figure stand
	 * @return true if the figure have a turn
	 */
	boolean hasFigureATurnFor(Point from);
}
