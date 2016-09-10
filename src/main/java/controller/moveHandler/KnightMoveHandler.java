package controller.moveHandler;

import javax.inject.Inject;
import javax.inject.Named;

import controller.ICheckChecker;
import controller.Move;
import controller.hitKing.KnightHitKingChecker;
import model.IGame;
import model.IGameField;
import model.Player;
import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;
import model.figure.IFigureHolder;

/**
 * The MoveHandler for the knight
 * 
 * @author Bjoern Hullmann
 */
@Named
class KnightMoveHandler extends AbstractMoveHandler implements IMoveHandler {
	
	/**
	 * All moves from a knight (x, y)
	 */
	private static final int[][]		MOVES	= { { 1, 2 }, { 2, 1 }, { -1, 2 }, { 2, -1 },
			{ 1, -2 }, { -2, 1 }, { -1, -2 }, { -2, -1 } };
			
	/**
	 * For checking if it is a valid move.
	 */
	private final KnightHitKingChecker	knightHitKingChecker;
										
	/**
	 * Create a MoveHandler for the rook
	 * 
	 * @param game
	 *            The game for which the MoveHandler his.
	 * @param figureHolder
	 *            The figure holder for getting all figure.
	 * @param checkChecker
	 *            The checker to check if a figure is in check.
	 * @param knightHitKingChecker
	 *            For checking if it is a valid move.
	 */
	@Inject
	KnightMoveHandler(final IGame game, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker, final KnightHitKingChecker knightHitKingChecker) {
		super(game, figureHolder, checkChecker, FigureType.KNIGHT);
		this.knightHitKingChecker = knightHitKingChecker;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasFigureATurnFor(final Point from) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		final IGameField gameField = game.getGameField();
		final IFigure figure = gameField.getFigure(from);
		
		for (final int[] posOffset : MOVES) {
			final int x = fromX + posOffset[0];
			final int y = fromY + posOffset[1];
			if (x < 0 || x >= IGameField.FIELD_SIZE) {
				continue;
			}
			if (y < 0 || y >= IGameField.FIELD_SIZE) {
				continue;
			}
			final Point to = Point.valueOf(x, y);
			final IFigure figureOnGoal = gameField.getFigure(to);
			final Player player = figure.getPlayer();
			if (figureOnGoal == null || figureOnGoal.getPlayer() != player) {
				if (isKingNotInCheckThen(from, to, figure, gameField)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItAValidMove(final Move move) {
		return knightHitKingChecker.canHitTheKing(move.getFrom(), move.getTo(),
				game.getGameField()::getFigure);
	}
}
