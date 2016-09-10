package controller.moveHandler;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import controller.ICheckChecker;
import controller.Move;
import controller.MoveInPointFigurePairs;
import model.IGame;
import model.IGameField;
import model.Player;
import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;
import model.figure.IFigureHolder;
import model.figure.IFigureWithMoveStatus;

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
	 *            The checker to check if a figure is in check.
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
		
		if (king.wasMoved()) {
			return false;
		}
		
		final Function<Point, IFigure> figureGetterWithoutThisKing = pos -> {
			final IFigure figure = gameField.getFigure(pos);
			if (figure != null && figure.equals(king)) {
				return null;
			}
			return figure;
		};
		
		if (!checkChecker.isPositionSafeForPlayer(king.getPlayer(), from,
				figureGetterWithoutThisKing)) {
			return false;
		}
		if (ckeckRook(0, fromY, fromX, gameField)) {
			// Look if the field where the king move over are in check
			if (checkChecker.isPositionSafeForPlayer(king.getPlayer(),
					Point.valueOf(fromX + 1, fromY), figureGetterWithoutThisKing)
					&& checkChecker.isPositionSafeForPlayer(king.getPlayer(),
							Point.valueOf(fromX + 2, fromY), figureGetterWithoutThisKing)) {
				return true;
			}
		}
		if (ckeckRook(IGameField.FIELD_SIZE - 1, fromY, fromX, gameField)) {
			// Look if the field where the king move over are in check
			if (checkChecker.isPositionSafeForPlayer(king.getPlayer(),
					Point.valueOf(fromX - 1, fromY), figureGetterWithoutThisKing)
					&& checkChecker.isPositionSafeForPlayer(king.getPlayer(),
							Point.valueOf(fromX - 2, fromY), figureGetterWithoutThisKing)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if there a unmoved rook for the castling and if the field are free between rook and
	 * king.
	 * 
	 * @param rookX
	 *            The x-position of the rook
	 * @param rookY
	 *            The y-position of the rook
	 * @param kingX
	 *            The x-position of the king.
	 * @param gameField
	 *            the actual game field
	 * @return true if a castling is possible. It is not checked if the fields are save.
	 */
	private static boolean ckeckRook(final int rookX, final int rookY, final int kingX,
			final IGameField gameField) {
		final IFigure figure = gameField.getFigure(Point.valueOf(rookX, rookY));
		if (figure == null || figure.getType() != FigureType.ROOK) {
			return false;
		}
		final IFigureWithMoveStatus rook = (IFigureWithMoveStatus) figure;
		if (rook.wasMoved()) {
			return false;
		}
		int from;
		int to;
		if (rookX < kingX) {
			from = rookX;
			to = kingX;
		} else {
			from = kingX;
			to = rookX;
		}
		for (int x = from + 1; x < to; x++) {
			if (gameField.getFigure(Point.valueOf(x, rookY)) != null) {
				return false;
			}
		}
		return true;
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
