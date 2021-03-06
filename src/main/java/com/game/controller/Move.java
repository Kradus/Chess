package com.game.controller;

import com.game.model.Point;

/**
 * This class has has the start and end point of a move. This is a immutable object.
 * 
 * @author Bjoern Hullmann
 */
public class Move {
	
	/**
	 * The start position.
	 */
	private final Point	from;
	/**
	 * The goal position.
	 */
	private final Point	to;
	
	/**
	 * Create a move.
	 * 
	 * @param from
	 *            The start position.
	 * @param to
	 *            The goal position.
	 */
	public Move(final Point from, final Point to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Get the start position from the move
	 * 
	 * @return The start position
	 */
	public Point getFrom() {
		return from;
	}
	
	/**
	 * Get the gaol position from the move
	 * 
	 * @return The goal position
	 */
	public Point getTo() {
		return to;
	}
}
