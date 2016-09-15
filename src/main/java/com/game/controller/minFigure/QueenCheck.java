package com.game.controller.minFigure;

import java.util.Map;

import javax.inject.Named;

import com.game.model.figure.FigureType;

/**
 * For checking if a player has enough figure. This condition is for the check if the player has at
 * least a queen.
 * 
 * @author Bjoern Hullmann
 */
@Named
class QueenCheck implements IMinFigureCheck {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMinFigure(final Map<FigureType, Integer> allFigure) {
		return allFigure.getOrDefault(FigureType.QUEEN, 0) > 0;
	}
}
