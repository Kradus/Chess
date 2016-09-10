package com.game.controller.hitKing;

import java.util.function.Function;

import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * Class with this interface can check if a specific figure can hit the king.
 * 
 * @author Bjoern Hullmann
 */
public interface IHitKingChecker {
	
	/**
	 * Check if a figure of a specific type can hit the king. It only check this not if the figure
	 * can reach the position without hitting the king.
	 * 
	 * @param from
	 *            The position where this figure stand
	 * @param kingPos
	 *            The position where there king from the other player stand
	 * @param figureGetter
	 *            A function with them the method can get for each position which figure is on this
	 *            position.
	 * @return true if the figure can hit the king
	 */
	boolean canHitTheKing(Point from, Point kingPos, Function<Point, IFigure> figureGetter);
	
	/**
	 * Get the FigureType for which this checker is
	 * 
	 * @return The FigureType for which this is
	 */
	FigureType getFigureType();
}
