package com.game.controller.hitKing;

import java.util.function.Function;

import javax.inject.Named;

import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * This class is for checking, if a king can hit the king.
 * 
 * @author Bjoern Hullmann
 */
@Named
class KingHitKingChecker extends AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * Create a KingHitKingChecker.
	 */
	KingHitKingChecker() {
		super(FigureType.KING);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canHitTheKing(final Point from, final Point kingPos,
			final Function<Point, IFigure> figureGetter) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		final int diffX = fromX - kingPos.getX();
		final int diffY = fromY - kingPos.getY();
		
		return Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1;
	}
}
