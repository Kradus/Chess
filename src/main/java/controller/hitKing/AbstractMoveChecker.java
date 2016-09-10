package controller.hitKing;

import model.figure.FigureType;

/**
 * This is the abstract class for checking, if a specific figure can hit the king.
 * 
 * @author Bjoern Hullmann
 */
abstract class AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * The figure type for which this check is.
	 */
	private final FigureType figureType;
	
	/**
	 * Create an AbstractHitKingChecker
	 * 
	 * @param figureType
	 *            The figure type for which this check is.
	 */
	AbstractHitKingChecker(final FigureType figureType) {
		this.figureType = figureType;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FigureType getFigureType() {
		return figureType;
	}
}
