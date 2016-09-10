package controller.moveHandler;

import javax.inject.Inject;
import javax.inject.Named;

import controller.ICheckChecker;
import controller.Move;
import controller.hitKing.BishopHitKingChecker;
import model.IGame;
import model.IGameField;
import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;
import model.figure.IFigureHolder;

/**
 * The MoveHandler for the bishop
 * 
 * @author Bjoern Hullmann
 */
@Named
class BishopMoveHandler extends AbstractMoveHandler implements IMoveHandler {
	
	/**
	 * For checking if it is a valid move.
	 */
	private final BishopHitKingChecker bishopHitKingChecker;
	
	/**
	 * Create a MoveHandler for the bishop
	 * 
	 * @param game
	 *            The game for which the MoveHandler his.
	 * @param figureHolder
	 *            The figure holder for getting all figure.
	 * @param checkChecker
	 *            The checker to check if a figure is in check.
	 * @param bishopHitKingChecker
	 *            For checking if it is a valid move.
	 */
	@Inject
	BishopMoveHandler(final IGame game, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker, final BishopHitKingChecker bishopHitKingChecker) {
		super(game, figureHolder, checkChecker, FigureType.BISHOP);
		this.bishopHitKingChecker = bishopHitKingChecker;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasFigureATurnFor(final Point from) {
		final IGameField gameField = game.getGameField();
		final IFigure figure = gameField.getFigure(from);
		
		if (checkDirtection(from, gameField, figure, 1, 1)) {
			return true;
		}
		if (checkDirtection(from, gameField, figure, -1, 1)) {
			return true;
		}
		if (checkDirtection(from, gameField, figure, 1, -1)) {
			return true;
		}
		if (checkDirtection(from, gameField, figure, -1, -1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItAValidMove(final Move move) {
		return bishopHitKingChecker.canHitTheKing(move.getFrom(), move.getTo(),
				game.getGameField()::getFigure);
	}
}
