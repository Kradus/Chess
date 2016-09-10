package controller.hitKing;

import java.util.function.Function;

import javax.inject.Named;

import model.Player;
import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;

/**
 * This class is for checking, if a pawn can hit the king.
 * 
 * @author Bjoern Hullmann
 */
@Named
class PawnHitKingChecker extends AbstractHitKingChecker implements IHitKingChecker {
	
	/**
	 * Create a PawnHitKingChecker.
	 */
	PawnHitKingChecker() {
		super(FigureType.PAWN);
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
		
		final IFigure pawn = figureGetter.apply(from);
		
		final int diffYEquals = pawn.getPlayer() == Player.PLAYER_1 ? 1 : -1;
		return diffY == diffYEquals && (diffX == 1 || diffX == -1);
	}
}
