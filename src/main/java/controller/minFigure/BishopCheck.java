package controller.minFigure;

import java.util.Map;

import javax.inject.Named;

import model.figure.FigureType;

/**
 * For checking if a player has enough figure. This condition is for two bishop.
 * 
 * @author Bjoern Hullmann
 */
@Named
class BishopCheck implements IMinFigureCheck {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMinFigure(final Map<FigureType, Integer> allFigure) {
		return allFigure.getOrDefault(FigureType.BISHOP, 0) > 1;
	}
}
