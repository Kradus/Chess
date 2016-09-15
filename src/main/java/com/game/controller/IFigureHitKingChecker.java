package com.game.controller;

import com.game.controller.hitKing.IHitKingChecker;
import com.game.model.figure.FigureType;

/**
 * Classes with this interface give you for every figure type a HitKingChecker. With this you can
 * check if the figure can hit the king.
 * 
 * @author Bjoern Hullmann
 */
interface IFigureHitKingChecker {
	
	/**
	 * Get a checker if the figure can hit the king for the specific figure type
	 * 
	 * @param type
	 *            The type for which you want the checker
	 * @return A HitKingChecker with them you can check, if the figure can hit the king
	 */
	IHitKingChecker getChecker(FigureType type);
}
