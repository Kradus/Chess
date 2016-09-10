package controller.minFigure;

import java.util.Map;

import javax.inject.Named;

import model.figure.FigureType;

/**
 * For checking if a player has enough figure. This condition is for a queen.
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
