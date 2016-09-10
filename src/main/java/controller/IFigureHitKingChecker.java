package controller;

import controller.hitKing.IHitKingChecker;
import model.figure.FigureType;

/**
 * Classes with this interface give you for every figure type a HitKingChecker. With this you can
 * check if the figure can hit the king.
 * 
 * @author Bjoern Hullmann
 */
interface IFigureHitKingChecker {
	
	/**
	 * Get a Checker if the figure can hit the king for the specific figure type
	 * 
	 * @param type
	 *            The type for which you want the checker
	 * @return A HitKingChecker with them you can check, if the figure can hit the king
	 */
	IHitKingChecker getChecker(FigureType type);
}
