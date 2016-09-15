package com.game.controller.minFigure;

import java.util.Map;

import javax.inject.Named;

import com.game.model.figure.FigureType;

/**
 * For checking if a player has enough figure. This condition is for the check if the player has at
 * least a rook.
 * 
 * @author Bjoern Hullmann
 */
@Named
class RookCheck implements IMinFigureCheck {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMinFigure(final Map<FigureType, Integer> allFigure) {
		return allFigure.getOrDefault(FigureType.ROOK, 0) > 0;
	}
}
