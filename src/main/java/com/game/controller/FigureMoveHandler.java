package com.game.controller;

import java.util.EnumMap;
import java.util.Set;

import javax.inject.Inject;

import com.game.controller.moveHandler.IMoveHandler;
import com.game.model.figure.FigureType;

/**
 * This class hold all the MoveHandler for each figureType.
 * 
 * @author Bjoern Hullmann
 */
class FigureMoveHandler implements IFigureMoveHandler {
	
	/**
	 * All MoveHandler which this object hold.
	 */
	private final EnumMap<FigureType, IMoveHandler> figureToMoveHandler = new EnumMap<>(
			FigureType.class);
			
	/**
	 * Create a FigureMoveHandler with all the given MoveHandler
	 * 
	 * @param moveHandlers
	 *            All MoveHandler which this object should hold.
	 */
	@Inject
	FigureMoveHandler(final Set<IMoveHandler> moveHandlers) {
		moveHandlers.forEach(
				moveHandler -> figureToMoveHandler.put(moveHandler.getFigureType(), moveHandler));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMoveHandler getHandler(final FigureType type) {
		return figureToMoveHandler.get(type);
	}
}
