package com.game.controller.moveHandler;

import com.game.controller.ICheckChecker;
import com.game.controller.Move;
import com.game.controller.MoveInPointFigurePairs;
import com.game.model.GameState;
import com.game.model.IGame;
import com.game.model.IGameField;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;
import com.game.model.figure.IFigureHolder;
import com.game.model.figure.IFigureWithMoveStatus;

/**
 * A abstract moveHandler which are hold the code, which are equals in all moveHandler
 * 
 * @author Bjoern Hullmann
 */
abstract class AbstractMoveHandler implements IMoveHandler {
	
	/**
	 * The game
	 */
	final IGame					game;
	/**
	 * The figure holder for getting all figure.
	 */
	final IFigureHolder			figureHolder;
	/**
	 * The checker to check if the king is in check.
	 */
	final ICheckChecker			checkChecker;
	/**
	 * The figure type where the checker belongs to.
	 */
	private final FigureType	figureType;
	
	/**
	 * Create a AbstractMoveHandler
	 * 
	 * @param game
	 *            The game for which the MoveHandler is.
	 * @param figureHolder
	 *            The figure holder for getting all figure.
	 * @param checkChecker
	 *            The checker to check if the king is in check.
	 * @param figureType
	 *            The figure type where the checker belongs to.
	 */
	AbstractMoveHandler(final IGame game, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker, final FigureType figureType) {
		this.game = game;
		this.figureHolder = figureHolder;
		this.checkChecker = checkChecker;
		this.figureType = figureType;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveInPointFigurePairs translateMove(final Move move) {
		final MoveInPointFigurePairs translatedMove = new MoveInPointFigurePairs();
		
		translatedMove.addNewPosition(move.getFrom(), null);
		
		final IGameField gameField = game.getGameField();
		IFigure moveFigure = gameField.getFigure(move.getFrom());
		if (moveFigure.getType().isMoveStatusIsRelevant()
				&& !((IFigureWithMoveStatus) moveFigure).wasMoved()) {
			moveFigure = figureHolder.getFigure(moveFigure.getType(), moveFigure.getPlayer());
		}
		translatedMove.addNewPosition(move.getTo(), moveFigure);
		
		if (gameField.getFigure(move.getTo()) != null)
		
		{
			translatedMove.setWasAFigureHit(true);
		}
		
		translatedMove.setNextGameState(game.getGameState() == GameState.TURN_OF_PLAYER_1
				? GameState.TURN_OF_PLAYER_2 : GameState.TURN_OF_PLAYER_1);
		return translatedMove;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FigureType getFigureType() {
		return figureType;
	}
	
	/**
	 * Check if the king is in check when the figure was move.
	 * 
	 * @param from
	 *            The position where the figure stand before the move.
	 * @param to
	 *            The position where the figure stand after the move.
	 * @param figure
	 *            The figure which should be move.
	 * @param gameField
	 *            The game field on which the figure stand.
	 * @return true if the king is not in check
	 */
	boolean isKingNotInCheckThen(final Point from, final Point to, final IFigure figure,
			final IGameField gameField) {
		final boolean kingInCheck = checkChecker.isKingInCheckForPlayer(figure.getPlayer(), pos -> {
			if (pos.equals(from)) {
				return null;
			}
			if (pos.equals(to)) {
				return figure;
			}
			return gameField.getFigure(pos);
		});
		return !kingInCheck;
	}
	
	/**
	 * Check if a figure has a move in a specific direction
	 * 
	 * @param from
	 *            The position where the figure stand.
	 * @param gameField
	 *            The game field of which the figure stand.
	 * @param figure
	 *            The Figure which should be check.
	 * @param xDirection
	 *            The x-Direction. It is the step size for checking.
	 * @param yDirection
	 *            The y-Direction. It is the step size for checking.
	 * @return true if the figure can make a move in the direction
	 */
	boolean checkDirtection(final Point from, final IGameField gameField, final IFigure figure,
			final int xDirection, final int yDirection) {
		final int fromX = from.getX();
		final int fromY = from.getY();
		
		for (int i = 1; i < IGameField.FIELD_SIZE; i++) {
			final int x = fromX + i * xDirection;
			final int y = fromY + i * yDirection;
			if (x < 0 || x >= IGameField.FIELD_SIZE) {
				break;
			}
			if (y < 0 || y >= IGameField.FIELD_SIZE) {
				break;
			}
			final Point to = Point.valueOf(x, y);
			final IFigure figureOnGoal = gameField.getFigure(to);
			final Player player = figure.getPlayer();
			if (figureOnGoal == null) {
				if (isKingNotInCheckThen(from, to, figure, gameField)) {
					return true;
				}
			} else if (figureOnGoal.getPlayer() != player) {
				if (isKingNotInCheckThen(from, to, figure, gameField)) {
					return true;
				}
				break;
			}
		}
		return false;
	}
}
