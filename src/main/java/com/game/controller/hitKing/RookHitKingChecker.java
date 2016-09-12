package com.game.controller.hitKing;

import java.util.function.Function;

import javax.inject.Named;

import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * This class is for checking, if a rook can hit the king.
 * 
 * @author Bjoern Hullmann
 */
@Named
public class RookHitKingChecker extends AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * Create a RookHitKingChecker.
	 */
	public RookHitKingChecker() {
		super(FigureType.ROOK);
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
		
		if (diffX != 0 && diffY != 0) {
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
		} else {
			final int minX = Math.min(fromX, kingPos.getX());
			diffX = Math.abs(diffX);
			for (int i = 1; i < diffX; i++) {
				if (figureGetter.apply(Point.valueOf(minX + i, fromY)) != null) {
					return false;
				}
			}
		}
		return true;
	}
}
