package com.game.controller.minFigure;

import java.util.Map;

import javax.inject.Named;

import com.game.model.figure.FigureType;

/**
 * For checking if a player has enough figure. This condition is for a pawn.
 * 
 * @author Bjoern Hullmann
 */
@Named
class PawnCheck implements IMinFigureCheck {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMinFigure(final Map<FigureType, Integer> allFigure) {
		return allFigure.getOrDefault(FigureType.PAWN, 0) > 0;
	}
}
