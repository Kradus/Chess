package controller.minFigure;

import java.util.Map;

import javax.inject.Named;

import model.figure.FigureType;

/**
 * For checking if a player has enough figure. This condition is for a rook.
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
