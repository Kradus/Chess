package com.game.controller.minFigure;

import java.util.Map;

import com.game.model.figure.FigureType;

/**
 * Class with this interface check if a player has enough figure, so that he can win the game.
 * 
 * @author Bjoern Hullmann
 */
public interface IMinFigureCheck {
	
	/**
	 * Check if a player has enough figure to checkmate the other king
	 * 
	 * @param allFigure
	 *            All figure from a player.
	 * @return true if the player have enough figure to set the other king checkmate
	 */
	boolean hasMinFigure(Map<FigureType, Integer> allFigure);
}
