package com.game.controller;

import com.game.controller.moveHandler.IMoveHandler;
import com.game.model.figure.FigureType;

/**
 * Classes with this interface give you for every figure type a MoveHandler. With this you can check
 * if it is a valid move or you can translate this move to a MoveInPointFigurePairs.
 * 
 * @author Bjoern Hullmann
 */
interface IFigureMoveHandler {
	
	/**
	 * Get the MoveHandler for the specific figure type
	 * 
	 * @param type
	 *            The type for which you want the MoveHandler
	 * @return The MoveHandler for the given type.
	 */
	IMoveHandler getHandler(FigureType type);
}
