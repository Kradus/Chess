package com.game.controller.hitKing;

import java.util.function.Function;

import javax.inject.Named;

import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * This class is for checking, if a bishop can hit the king.
 * 
 * @author Bjoern Hullmann
 */
@Named
public class BishopHitKingChecker extends AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * Create a BishopHitKingChecker.
	 */
	public BishopHitKingChecker() {
		super(FigureType.BISHOP);
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
		
		if (Math.abs(diffX) != Math.abs(diffY)) {
			return false;
		}
		
		final int until = Math.abs(diffX);
		for (int i = 1; i < until; i++) {
			final int offsetX = diffX > 0 ? -i : i;
			final int offsetY = diffY > 0 ? -i : i;
			if (figureGetter.apply(Point.valueOf(fromX + offsetX, fromY + offsetY)) != null) {
				return false;
			}
		}
		return true;
	}
}
