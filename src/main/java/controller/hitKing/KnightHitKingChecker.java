package controller.hitKing;

import java.util.function.Function;

import javax.inject.Named;

import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;

/**
 * This class is for checking, if a knight can hit the king.
 * 
 * @author Bjoern Hullmann
 */
@Named
public class KnightHitKingChecker extends AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * Create a KnightHitKingChecker.
	 */
	public KnightHitKingChecker() {
		super(FigureType.KNIGHT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canHitTheKing(final Point from, final Point kingPos,
			final Function<Point, IFigure> figureGetter) {
		final int diffX = Math.abs(from.getX() - kingPos.getX());
		final int diffY = Math.abs(from.getY() - kingPos.getY());
		
		return diffX == 1 && diffY == 2 || diffX == 2 && diffY == 1;
	}
}
