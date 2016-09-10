package controller.moveHandler;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import controller.ICheckChecker;
import controller.Move;
import controller.MoveInPointFigurePairs;
import model.GameState;
import model.IGame;
import model.IGameField;
import model.Player;
import model.Point;
import model.figure.FigureType;
import model.figure.IFigure;
import model.figure.IFigureHolder;
import model.figure.IFigureWithMoveStatus;

/**
 * The MoveHandler for the pawn
 * 
 * @author Bjoern Hullmann
 */
@Named
class PawnMoveHandler extends AbstractMoveHandler implements IMoveHandler {
	
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
	PawnMoveHandler(final IGame game, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker) {
		super(game, figureHolder, checkChecker, FigureType.PAWN);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveInPointFigurePairs translateMove(final Move move) {
		final MoveInPointFigurePairs translatedMove = super.translateMove(move);
		final Point from = move.getFrom();
		final Point to = move.getTo();
		final int diffX = from.getX() - to.getX();
		
		final IGameField gameField = game.getGameField();
		if (diffX != 0 && gameField.getFigure(to) == null) {
			translatedMove.addNewPosition(Point.valueOf(to.getX(), from.getY()), null);
			translatedMove.setWasAFigureHit(true);
		} else if (to.getY() == 0 || to.getY() == IGameField.FIELD_SIZE - 1) {
			final IFigure pawn = gameField.getFigure(from);
			translatedMove.setNextGameState(pawn.getPlayer() == Player.PLAYER_1
					? GameState.PAWN_CHOOSE_PLAYER_1 : GameState.PAWN_CHOOSE_PLAYER_2);
		}
		
		translatedMove.setWasAPawnMove(true);
		return translatedMove;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasFigureATurnFor(final Point from) {
		final IGameField gameField = game.getGameField();
		final IFigure pawn = gameField.getFigure(from);
		
		if (pawn.getPlayer() == Player.PLAYER_1) {
			return haveFigureATurnBottom(from, gameField, pawn);
		}
		return haveFigureATurnTop(from, gameField, pawn);
	}
	
	/**
	 * Check if a pawn has a move for the player on the bottom.
	 * 
	 * @param from
	 *            The position where the pawn stand
	 * @param gameField
	 *            The actual game field
	 * @param pawn
	 *            The pawn figure
	 * @return true if the pawn have a turn.
	 */
	private boolean haveFigureATurnBottom(final Point from, final IGameField gameField,
			final IFigure pawn) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		
		final Point to = Point.valueOf(fromX, fromY - 1);
		if (gameField.getFigure(to) == null) {
			if (isKingNotInCheckThen(from, to, pawn, gameField)) {
				return true;
			}
		}
		// When you didn't make the move above, you can not move the figure over two fields
		
		int toX = fromX - 1;
		if (toX >= 0) {
			if (checkCanHitBottom(from, fromY, gameField, pawn, toX)) {
				return true;
			}
		}
		
		toX = fromX + 1;
		if (toX < IGameField.FIELD_SIZE) {
			if (checkCanHitBottom(from, fromY, gameField, pawn, toX)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method check if the figure has a turn free, where he can hit a figure. This method is
	 * for the player at the bottom of the field.
	 * 
	 * @param from
	 *            The position where the pawn stand
	 * @param fromY
	 *            The y-position of the pawn
	 * @param gameField
	 *            The actual game field
	 * @param pawn
	 *            The pawn figure for which this method should check
	 * @param toX
	 *            The goal x-position
	 * @return true if he can hit a figure
	 */
	private boolean checkCanHitBottom(final Point from, final int fromY, final IGameField gameField,
			final IFigure pawn, final int toX) {
		final Point to = Point.valueOf(toX, fromY - 1);
		IFigure otherFigure = gameField.getFigure(to);
		if (otherFigure != null) {
			if (otherFigure.getPlayer() != pawn.getPlayer()
					&& isKingNotInCheckThen(from, to, pawn, gameField)) {
				return true;
			}
		} else {
			otherFigure = gameField.getFigure(Point.valueOf(toX, fromY));
			if (otherFigure != null && otherFigure.getType() == FigureType.PAWN
					&& otherFigure.getPlayer() != pawn.getPlayer()) {
				final Point baseLinePawnPos = Point.valueOf(toX, fromY - 2);
				if (gameField.getFigure(baseLinePawnPos) == null
						&& game.sizeOfLastConstellations() > FigureType.INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE
						&& game.getOldConstellation(
								FigureType.INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE)
								.getFigure(baseLinePawnPos) != null
						&& isKingNotInCheckThenByHittingPawn(from, to, pawn, gameField)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if a pawn has a move for the player on the top.
	 * 
	 * @param from
	 *            The position where the pawn stand
	 * @param gameField
	 *            The actual game field
	 * @param pawn
	 *            The pawn figure
	 * @return true if the pawn have a turn.
	 */
	private boolean haveFigureATurnTop(final Point from, final IGameField gameField,
			final IFigure pawn) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		
		final Point to = Point.valueOf(fromX, fromY + 1);
		if (gameField.getFigure(to) == null && isKingNotInCheckThen(from, to, pawn, gameField)) {
			return true;
		}
		// When you didn't make the move above, you can not move the figure over two fields
		
		int toX = fromX - 1;
		if (toX >= 0 && checkCanHitTop(from, fromY, gameField, pawn, toX)) {
			return true;
		}
		
		toX = fromX + 1;
		if (toX < IGameField.FIELD_SIZE && checkCanHitTop(from, fromY, gameField, pawn, toX)) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * This method check if the figure has a turn free, where he can hit a figure. This method is
	 * for the player at the top of the field.
	 * 
	 * @param from
	 *            The position where the pawn stand
	 * @param fromY
	 *            The y-position of the pawn
	 * @param gameField
	 *            The actual game field
	 * @param pawn
	 *            The pawn figure for which this method should check
	 * @param toX
	 *            The goal x-position
	 * @return true if he can hit a figure
	 */
	private boolean checkCanHitTop(final Point from, final int fromY, final IGameField gameField,
			final IFigure pawn, final int toX) {
		final Point to = Point.valueOf(toX, fromY + 1);
		IFigure otherFigure = gameField.getFigure(to);
		if (otherFigure != null) {
			if (otherFigure.getPlayer() != pawn.getPlayer()
					&& isKingNotInCheckThen(from, to, pawn, gameField)) {
				return true;
			}
		} else {
			otherFigure = gameField.getFigure(Point.valueOf(toX, fromY));
			if (otherFigure != null && otherFigure.getType() == FigureType.PAWN
					&& otherFigure.getPlayer() != pawn.getPlayer()) {
				final Point baseLinePawnPos = Point.valueOf(toX, fromY + 2);
				if (gameField.getFigure(baseLinePawnPos) == null
						&& game.sizeOfLastConstellations() > FigureType.INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE
						&& game.getOldConstellation(
								FigureType.INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE)
								.getFigure(baseLinePawnPos) != null
						&& isKingNotInCheckThenByHittingPawn(from, to, pawn, gameField)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method check if the king is in check, when a pawn hit a other pawn, which move in the
	 * last turn over two fields.
	 * 
	 * @param from
	 *            The start position
	 * @param to
	 *            The goal position
	 * @param figure
	 *            The pawn figure which move
	 * @param gameField
	 *            The actual game field
	 * @return true if the king not in check, when he hit the pawn.
	 */
	private boolean isKingNotInCheckThenByHittingPawn(final Point from, final Point to,
			final IFigure figure, final IGameField gameField) {
		final Point posHitawn = Point.valueOf(to.getX(), from.getY());
		final boolean kingInCheck = checkChecker.isKingInCheckForPlayer(figure.getPlayer(), pos -> {
			if (pos.equals(from)) {
				return null;
			}
			if (pos.equals(to)) {
				return figure;
			}
			if (pos.equals(posHitawn)) {
				// The hit pawn
				return null;
			}
			return gameField.getFigure(pos);
		});
		return !kingInCheck;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItAValidMove(final Move move) {
		final Point from = move.getFrom();
		final Point to = move.getTo();
		
		final IGameField gameField = game.getGameField();
		final IFigure pawn = gameField.getFigure(from);
		
		final Function<Point, IFigure> turnBeforeFigureGetter;
		if (game.sizeOfLastConstellations() > FigureType.INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE) {
			turnBeforeFigureGetter = game.getOldConstellation(
					FigureType.INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE)::getFigure;
		} else {
			turnBeforeFigureGetter = gameField::getFigure;
		}
		
		if (pawn.getPlayer() == Player.PLAYER_1) {
			return ckeckForBottomPlayer(gameField::getFigure, turnBeforeFigureGetter, from, to,
					pawn);
		}
		return ckeckForTopPlayer(gameField::getFigure, turnBeforeFigureGetter, from, to, pawn);
	}
	
	/**
	 * This method check if this is a valid move for the player on the bottom.
	 * 
	 * @param figureGetter
	 *            A getter to get the figure from the position.
	 * @param turnBeforeFigureGetter
	 *            A getter to get the figure from the position a turn before.
	 * @param from
	 *            The start position
	 * @param to
	 *            The goal position
	 * @param pawn
	 *            The pawn figure which is moving
	 * @return true if it is a valid move.
	 */
	private static boolean ckeckForBottomPlayer(final Function<Point, IFigure> figureGetter,
			final Function<Point, IFigure> turnBeforeFigureGetter, final Point from, final Point to,
			final IFigure pawn) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		
		final int diffX = fromX - to.getX();
		final int diffY = fromY - to.getY();
		
		if (diffY == 2) {
			if (diffX != 0) {
				return false;
			}
			if (((IFigureWithMoveStatus) pawn).wasMoved()) {
				return false;
			}
			if (figureGetter.apply(Point.valueOf(fromX, fromY - 1)) != null
					&& figureGetter.apply(Point.valueOf(fromX, fromY - 2)) != null) {
				return false;
			}
			return true;
		} else if (diffY == 1) {
			if (diffX == 0) {
				if (figureGetter.apply(Point.valueOf(fromX, fromY - 1)) != null) {
					return false;
				}
				return true;
			}
			if (diffX == 1 || diffX == -1) {
				// Check if the pawn hit a other pawn which make a move over two fields
				if (figureGetter.apply(Point.valueOf(to.getX(), fromY - 1)) == null) {
					final IFigure figure = turnBeforeFigureGetter
							.apply(Point.valueOf(to.getX(), fromY - 2));
					// Look if in the last turn move a pawn and if he move two fields
					if (figure != null && figure.getType() == FigureType.PAWN
							&& figure.getPlayer() != pawn.getPlayer()) {
						if (figureGetter.apply(Point.valueOf(to.getX(), fromY - 2)) != null) {
							return false;
						}
						if (figureGetter.apply(Point.valueOf(to.getX(), fromY)) == null) {
							return false;
						}
						return true;
					}
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method check if this is a valid move for the player on the top.
	 * 
	 * @param figureGetter
	 *            A getter to get the figure from the position.
	 * @param turnBeforeFigureGetter
	 *            A getter to get the figure from the position a turn before.
	 * @param from
	 *            The start position
	 * @param to
	 *            The goal position
	 * @param pawn
	 *            The pawn figure which is moving
	 * @return true if it is a valid move.
	 */
	private static boolean ckeckForTopPlayer(final Function<Point, IFigure> figureGetter,
			final Function<Point, IFigure> turnBeforeFigureGetter, final Point from, final Point to,
			final IFigure pawn) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		
		final int diffX = fromX - to.getX();
		final int diffY = fromY - to.getY();
		
		if (diffY == -2) {
			if (diffX != 0) {
				return false;
			}
			if (((IFigureWithMoveStatus) pawn).wasMoved()) {
				return false;
			}
			if (figureGetter.apply(Point.valueOf(fromX, fromY + 1)) != null
					&& figureGetter.apply(Point.valueOf(fromX, fromY + 2)) != null) {
				return false;
			}
			return true;
		} else if (diffY == -1) {
			if (diffX == 0) {
				if (figureGetter.apply(Point.valueOf(fromX, fromY + 1)) != null) {
					return false;
				}
				return true;
			}
			if (diffX == 1 || diffX == -1) {
				// Check if the pawn hit a other pawn which make a move over two fields
				if (figureGetter.apply(Point.valueOf(to.getX(), fromY + 1)) == null) {
					
					final IFigure figure = turnBeforeFigureGetter
							.apply(Point.valueOf(to.getX(), fromY + 2));
					// Look if in the last turn move a pawn and if he move two fields
					if (figure != null && figure.getType() == FigureType.PAWN
							&& figure.getPlayer() != pawn.getPlayer()) {
						if (figureGetter.apply(Point.valueOf(to.getX(), fromY + 2)) != null) {
							return false;
						}
						if (figureGetter.apply(Point.valueOf(to.getX(), fromY)) == null) {
							return false;
						}
						return true;
					}
					return false;
				}
				return true;
			}
		}
		return false;
	}
}
