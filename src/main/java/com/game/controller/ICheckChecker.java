package com.game.controller;

import java.util.function.Function;

import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.IFigure;

/**
 * Class with this interface can check if the king is in check or if position is safe for the king.
 * 
 * @author Bjoern Hullmann
 */
public interface ICheckChecker {
	
	/**
	 * Check if the king of a player is in check. This function use the actual game field to get the
	 * king and the position of the other figures
	 * 
	 * @param player
	 *            The player for which should be check if his king is in check
	 * @return true if the king from the player is in check
	 */
	public boolean isKingInCheckForPlayer(final Player player);
	
	/**
	 * Check if the king of a player is in check. This function use the actual game field to get the
	 * king and the position of the other figures. But only when the position are not change with
	 * the given move. Then the position of the move are used.
	 * 
	 * @param player
	 *            The player for which should be check if his king is in check
	 * @param afterThisMove
	 *            To check if the king is in check, when this move is done.
	 * @return true if the king from the player is in check
	 */
	public boolean isKingInCheckForPlayer(final Player player,
			final MoveInPointFigurePairs afterThisMove);
			
	/**
	 * Check if the king of a player is in check. The function use the given figureGetter to get the
	 * position of the king and the other figure.
	 * 
	 * @param player
	 *            The player for which should be check if his king is in check
	 * @param figureGetter
	 *            A function with them the method can get for each position which figure is on this
	 *            position.
	 * @return true if the king from the player is in check
	 */
	public boolean isKingInCheckForPlayer(final Player player,
			final Function<Point, IFigure> figureGetter);
			
	/**
	 * Check if a figure can go to this field. It is not checking if the king in check when it
	 * moved.
	 * 
	 * @param player
	 *            For which player the position should be save
	 * @param pos
	 *            The position which should be save
	 * @param figureGetter
	 *            A function with them the method can get for each position which figure is on this
	 *            position.
	 * @return true if the other player can not reach the position
	 */
	public boolean isPositionSafeForPlayer(final Player player, final Point pos,
			final Function<Point, IFigure> figureGetter);
}
