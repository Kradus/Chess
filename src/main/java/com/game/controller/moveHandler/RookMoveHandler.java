package com.game.controller.moveHandler;

import javax.inject.Inject;
import javax.inject.Named;

import com.game.controller.ICheckChecker;
import com.game.controller.Move;
import com.game.controller.hitKing.RookHitKingChecker;
import com.game.model.IGame;
import com.game.model.IGameField;
import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;
import com.game.model.figure.IFigureHolder;

/**
 * The MoveHandler for the rook
 * 
 * @author Bjoern Hullmann
 */
@Named
class RookMoveHandler extends AbstractMoveHandler implements IMoveHandler {
	
	/**
	 * For checking if it is a valid move.
	 */
	private final RookHitKingChecker rookHitKingChecker;
	
	/**
	 * Create a MoveHandler for the rook
	 * 
	 * @param game
	 *            The game for which the MoveHandler his.
	 * @param figureHolder
	 *            The figure holder for getting all figure.
	 * @param checkChecker
	 *            The checker to check if the king is in check.
	 * @param rookHitKingChecker
	 *            For checking if it is a valid move.
	 */
	@Inject
	RookMoveHandler(final IGame game, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker, final RookHitKingChecker rookHitKingChecker) {
		super(game, figureHolder, checkChecker, FigureType.ROOK);
		this.rookHitKingChecker = rookHitKingChecker;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasFigureATurnFor(final Point from) {
		final IGameField gameField = game.getGameField();
		final IFigure figure = gameField.getFigure(from);
		
		if (checkDirtection(from, gameField, figure, 0, 1)) {
			return true;
		}
		if (checkDirtection(from, gameField, figure, 0, -1)) {
			return true;
		}
		if (checkDirtection(from, gameField, figure, 1, 0)) {
			return true;
		}
		if (checkDirtection(from, gameField, figure, -1, 0)) {
			return true;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItAValidMove(final Move move) {
		return rookHitKingChecker.canHitTheKing(move.getFrom(), move.getTo(),
				game.getGameField()::getFigure);
	}
}
