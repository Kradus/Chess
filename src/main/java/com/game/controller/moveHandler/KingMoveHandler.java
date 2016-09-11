package com.game.controller.moveHandler;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.game.controller.ICheckChecker;
import com.game.controller.Move;
import com.game.controller.MoveInPointFigurePairs;
import com.game.model.IGame;
import com.game.model.IGameField;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;
import com.game.model.figure.IFigureHolder;
import com.game.model.figure.IFigureWithMoveStatus;

/**
 * The MoveHandler for the king
 * 
 * @author Bjoern Hullmann
 */
@Named
class KingMoveHandler extends AbstractMoveHandler implements IMoveHandler {
	
	/**
	 * Create a MoveHandler for the rook
	 * 
	 * @param game
	 *            The game for which the MoveHandler his.
	 * @param figureHolder
	 *            The figure holder for getting all figure.
	 * @param checkChecker
	 *            The checker to check if the king is in check.
	 */
	@Inject
	KingMoveHandler(final IGame game, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker) {
		super(game, figureHolder, checkChecker, FigureType.KING);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveInPointFigurePairs translateMove(final Move move) {
		final MoveInPointFigurePairs translatedMove = super.translateMove(move);
		final Point from = move.getFrom();
		final Point to = move.getTo();
		final int fromX = from.getX();
		final int fromY = from.getY();
		final int diffX = fromX - to.getX();
		final int diffY = fromY - to.getY();
		
		if (Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1) {
			// Only normal move, no rook must be moved
			return translatedMove;
		}
		
		final int rookPosX = to.getX() > fromX ? IGameField.FIELD_SIZE - 1 : 0;
		
		final Point positionOfRook = Point.valueOf(rookPosX, fromY);
		translatedMove.addNewPosition(positionOfRook, null);
		
		IFigure moveRookFigure = game.getGameField().getFigure(positionOfRook);
		if (!((IFigureWithMoveStatus) moveRookFigure).wasMoved()) {
			moveRookFigure = figureHolder.getFigure(moveRookFigure.getType(),
					moveRookFigure.getPlayer());
		}
		final Point newRookPosition = Point.valueOf(fromX + to.getX() >> 1, fromY);
		translatedMove.addNewPosition(newRookPosition, moveRookFigure);
		
		return translatedMove;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasFigureATurnFor(final Point from) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		
		final IGameField gameField = game.getGameField();
		final IFigureWithMoveStatus king = (IFigureWithMoveStatus) gameField.getFigure(from);
		
		final int minX = Math.max(0, fromX - 1);
		final int minY = Math.max(0, fromY - 1);
		final int maxX = Math.min(IGameField.FIELD_SIZE, fromX + 2);
		final int maxY = Math.min(IGameField.FIELD_SIZE, fromY + 2);
		for (int x = minX; x < maxX; x++) {
			for (final int y = minY; x < maxY; x++) {
				if (x == fromX && y == fromY) {
					continue;
				}
				final Point to = Point.valueOf(x, y);
				final IFigure figureOnGoal = gameField.getFigure(to);
				final Player player = king.getPlayer();
				if (figureOnGoal == null || figureOnGoal.getPlayer() != player) {
					if (isKingNotInCheckThen(from, to, king, gameField)) {
						return true;
					}
				}
			}
		}
		// It is not needed to check for the castling. Is the castling valid then is also a move
		// without castling valid.
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItAValidMove(final Move move) {
		final Point from = move.getFrom();
		final Point to = move.getTo();
		final int fromX = from.getX();
		final int fromY = from.getY();
		final int diffX = fromX - to.getX();
		final int diffY = fromY - to.getY();
		
		final IGameField gameField = game.getGameField();
		final IFigureWithMoveStatus king = (IFigureWithMoveStatus) gameField.getFigure(from);
		
		// Normal move
		if (Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1) {
			// The move is allow.
			return true;
		}
		
		// Special move
		if (!king.wasMoved() && diffY == 0 && Math.abs(diffX) == 2) {
			// When he stand in check this should not possible
			final int rookPosX = to.getX() > fromX ? IGameField.FIELD_SIZE - 1 : 0;
			final Point positionOfRook = Point.valueOf(rookPosX, fromY);
			final IFigure figure = gameField.getFigure(positionOfRook);
			if (figure == null || figure.getType() != FigureType.ROOK) {
				return false;
			}
			final IFigureWithMoveStatus rook = (IFigureWithMoveStatus) figure;
			if (rook.wasMoved()) {
				return false;
			}
			
			// Check if all fields between king and rook are free
			if (rookPosX > fromX) {
				for (int i = fromX + 1; i < rookPosX; i++) {
					checkIfFieldIsNull(Point.valueOf(i, fromY));
				}
			} else {
				for (int i = rookPosX + 1; i < fromX; i++) {
					checkIfFieldIsNull(Point.valueOf(i, fromY));
				}
			}
			
			final Function<Point, IFigure> figureGetterWithoutThisKing = pos -> {
				final IFigure otherFigure = gameField.getFigure(pos);
				if (otherFigure != null && otherFigure.equals(king)) {
					return null;
				}
				return otherFigure;
			};
			
			if (!checkChecker.isPositionSafeForPlayer(king.getPlayer(), to,
					figureGetterWithoutThisKing)) {
				return false;
			}
			
			if (!checkChecker.isPositionSafeForPlayer(king.getPlayer(),
					Point.valueOf(fromX + to.getX() >> 1, fromY), figureGetterWithoutThisKing)) {
				return false;
			}
			
			if (!checkChecker.isPositionSafeForPlayer(king.getPlayer(), from,
					figureGetterWithoutThisKing)) {
				return false;
			}
			// The move is allowed
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if a position is free.
	 * 
	 * @param pos
	 *            The position to check.
	 */
	private void checkIfFieldIsNull(final Point pos) {
		if (game.getGameField().getFigure(pos) != null) {
			throw new IllegalArgumentException("A figure is between the rook and the king.");
		}
	}
}
