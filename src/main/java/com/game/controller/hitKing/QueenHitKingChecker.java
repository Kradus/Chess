package com.game.controller.hitKing;

import java.util.function.Function;

import javax.inject.Named;

import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * This class is for checking, if a queen can hit the king.
 * 
 * @author Bjoern Hullmann
 */
@Named
public class QueenHitKingChecker extends AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * Create a QueenHitKingChecker.
	 */
	public QueenHitKingChecker() {
		super(FigureType.QUEEN);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canHitTheKing(final Point from, final Point kingPos,
			final Function<Point, IFigure> figureGetter) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		int diffX = fromX - kingPos.getX();
		int diffY = fromY - kingPos.getY();
		
		if (diffX != 0 && diffY != 0 && Math.abs(diffX) != Math.abs(diffY)) {
			return false;
		}
		
		if (diffX == 0) {
			final int minY = Math.min(fromY, kingPos.getY());
			diffY = Math.abs(diffY);
			for (int i = 1; i < diffY; i++) {
				if (figureGetter.apply(Point.valueOf(fromX, minY + i)) != null) {
					return false;
				}
			}
		} else if (diffY == 0) {
			final int minX = Math.min(fromX, kingPos.getX());
			diffX = Math.abs(diffX);
			for (int i = 1; i < diffX; i++) {
				if (figureGetter.apply(Point.valueOf(minX + i, fromY)) != null) {
					return false;
				}
			}
		} else {
			final int until = Math.abs(diffX);
			for (int i = 1; i < until; i++) {
				final int offsetX = diffX > 0 ? -i : i;
				final int offsetY = diffY > 0 ? -i : i;
				if (figureGetter.apply(Point.valueOf(fromX + offsetX, fromY + offsetY)) != null) {
					return false;
				}
			}
		}
		return true;
	}
}
