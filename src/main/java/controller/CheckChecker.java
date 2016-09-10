package controller;

import java.util.function.Function;

import javax.inject.Inject;

import controller.hitKing.IHitKingChecker;
import model.IGame;
import model.IGameField;
import model.Player;
import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;

/**
 * This class is for checking if the king is in check or not.
 * 
 * @author Bjoern Hullmann
 */
public class CheckChecker implements ICheckChecker {
	
	/**
	 * The FigureHitKingChecker for getting HitKingCheckers.
	 */
	private final IFigureHitKingChecker	figureHitChecker;
	/**
	 * The game
	 */
	private final IGame					game;
										
	/**
	 * Create a CheckChecker
	 * 
	 * @param figureHitChecker
	 *            The FigureHitKingChecker for getting HitKingCheckers.
	 * @param game
	 *            The game for that the checker should check.
	 */
	@Inject
	public CheckChecker(final IFigureHitKingChecker figureHitChecker, final IGame game) {
		this.figureHitChecker = figureHitChecker;
		this.game = game;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKingInCheckForPlayer(final Player player) {
		return isKingInCheckForPlayer(player, game.getGameField()::getFigure);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKingInCheckForPlayer(final Player player,
			final MoveInPointFigurePairs afterThisMove) {
		final IGameField gameField = game.getGameField();
		return isKingInCheckForPlayer(player,
				IController.getFigureGetter(afterThisMove, gameField));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKingInCheckForPlayer(final Player player,
			final Function<Point, IFigure> figureGetter) {
		final Point kingPos = findKing(player, figureGetter);
		
		return !isPositionSafeForPlayer(player, kingPos, figureGetter);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPositionSafeForPlayer(final Player player, final Point pos,
			final Function<Point, IFigure> figureGetter) {
		for (int y = 0; y < IGameField.FIELD_SIZE; y++) {
			for (int x = 0; x < IGameField.FIELD_SIZE; x++) {
				final Point posToCheck = Point.valueOf(x, y);
				final IFigure figure = figureGetter.apply(posToCheck);
				if (figure == null || figure.getPlayer() == player) {
					continue;
				}
				final IHitKingChecker moveChecker = figureHitChecker.getChecker(figure.getType());
				if (moveChecker.canHitTheKing(posToCheck, pos, figureGetter)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Get the position of the king from a player
	 * 
	 * @param player
	 *            The player which should own the king
	 * @param figureGetter
	 *            A function with them the method can get for each position which figure is on this
	 *            position.
	 * @return The position of the king
	 */
	private static Point findKing(final Player player,
			final Function<Point, IFigure> figureGetter) {
		for (int y = 0; y < IGameField.FIELD_SIZE; y++) {
			for (int x = 0; x < IGameField.FIELD_SIZE; x++) {
				final Point posToCheck = Point.valueOf(x, y);
				final IFigure figure = figureGetter.apply(posToCheck);
				if (figure != null && figure.getType() == FigureType.KING
						&& figure.getPlayer() == player) {
					return posToCheck;
				}
			}
		}
		throw new AssertionError();
	}
}
