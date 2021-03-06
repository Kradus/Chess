package com.game.controller;

import java.util.function.Function;

import com.game.model.IGameField;
import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * Classes with this interface can check and do all the action that a user want.
 * 
 * @author Bjoern Hullmann
 */
public interface IController {
	
	/**
	 * Reset the game, if the game is in a end state.
	 */
	void restartGame();
	
	/**
	 * Move a figure
	 * 
	 * @param move
	 *            The Move with should be down
	 */
	void moveFigure(Move move);
	
	/**
	 * Change a pawn, which is on the end of the game field.
	 * 
	 * @param type
	 *            The FigureType in which the pawn should be changed.
	 */
	void changePawnInto(FigureType type);
	
	/**
	 * Create a figure getter whit the help from the given game field and the move. It used first
	 * the position of the given move. When there is no move in it, it use the position of the game
	 * field. The figure getter show the game field after the given move.
	 * 
	 * @param afterThisMove
	 *            The move which should use
	 * @param gameField
	 *            The game field which should be use
	 * @return A figureGetter where you can get a figure for a given position
	 */
	public static Function<Point, IFigure> getFigureGetter(
			final MoveInPointFigurePairs afterThisMove, final IGameField gameField) {
		return pos -> afterThisMove.contains(pos) ? afterThisMove.getFigure(pos)
				: gameField.getFigure(pos);
	}
}
