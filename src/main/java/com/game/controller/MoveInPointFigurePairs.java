package com.game.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.game.model.GameState;
import com.game.model.Point;
import com.game.model.figure.IFigure;

// TODO überarbeite diesen kommentar
/**
 * This class has the data on which positions change for a move and it has the information over the
 * next game state. Furthermore he has the information if a figure was hit or a pawn was set.
 * 
 * @author Bjoern Hullmann
 */
public class MoveInPointFigurePairs implements Iterable<Entry<Point, IFigure>> {
	
	/**
	 * A map which hold all fields which are change and the new figure on it.
	 */
	private final Map<Point, IFigure>	newPositions	= new HashMap<>(8);
	/**
	 * Boolean if a figure was hit or not.
	 */
	private boolean						wasAFigureHit	= false;
	/**
	 * Boolean if a pawn was set or not.
	 */
	private boolean						wasAPawnMove	= false;
	/**
	 * The next game state of the game when this move is done.
	 */
	private GameState					nextGameState;
	
	/**
	 * Add a new position which are change with this move
	 * 
	 * @param pos
	 *            The position which will be change
	 * @param figure
	 *            The new Figure on this field or null
	 */
	public void addNewPosition(final Point pos, final IFigure figure) {
		newPositions.put(pos, figure);
	}
	
	/**
	 * Check if the field on the given position are change with this move
	 * 
	 * @param pos
	 *            The position to check
	 * @return true if the field will be changed
	 */
	public boolean contains(final Point pos) {
		return newPositions.containsKey(pos);
	}
	
	/**
	 * Get the changes for a given position
	 * 
	 * @param pos
	 *            The position from which you want the changes
	 * @return The figure or null if nothing on the given position
	 */
	public IFigure getFigure(final Point pos) {
		return newPositions.get(pos);
	}
	
	/**
	 * Check if a figure was hit in this move
	 * 
	 * @return true if a figure was hit
	 */
	public boolean isWasAFigureHit() {
		return wasAFigureHit;
	}
	
	/**
	 * Set if a figure was hit in this move
	 * 
	 * @param wasAFigureHit
	 *            Was a figure hit.
	 */
	public void setWasAFigureHit(final boolean wasAFigureHit) {
		this.wasAFigureHit = wasAFigureHit;
	}
	
	/**
	 * Check if a pawn was set in this move
	 * 
	 * @return true if a pawn was set
	 */
	public boolean isWasAPawnMove() {
		return wasAPawnMove;
	}
	
	/**
	 * Set if a pawn was set in this move
	 * 
	 * @param wasAPawnMove
	 *            was a paw was set
	 */
	public void setWasAPawnMove(final boolean wasAPawnMove) {
		this.wasAPawnMove = wasAPawnMove;
	}
	
	/**
	 * Get the gameState after this move is done
	 * 
	 * @return The next game state
	 */
	public GameState getNextGameState() {
		return nextGameState;
	}
	
	/**
	 * Set the game state which should be after this move is done
	 * 
	 * @param nextGameState
	 *            The game state which is after this move
	 */
	public void setNextGameState(final GameState nextGameState) {
		this.nextGameState = nextGameState;
	}
	
	/**
	 * Return a iterator over the all changed position with the new figure on it
	 * 
	 * @return The iterator
	 */
	@Override
	public Iterator<Entry<Point, IFigure>> iterator() {
		return newPositions.entrySet().iterator();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newPositions == null) ? 0 : newPositions.hashCode());
		result = prime * result + ((nextGameState == null) ? 0 : nextGameState.hashCode());
		result = prime * result + (wasAFigureHit ? 1231 : 1237);
		result = prime * result + (wasAPawnMove ? 1231 : 1237);
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MoveInPointFigurePairs)) {
			return false;
		}
		final MoveInPointFigurePairs other = (MoveInPointFigurePairs) obj;
		if (newPositions == null) {
			if (other.newPositions != null) {
				return false;
			}
		} else if (!newPositions.equals(other.newPositions)) {
			return false;
		}
		if (nextGameState != other.nextGameState) {
			return false;
		}
		if (wasAFigureHit != other.wasAFigureHit) {
			return false;
		}
		if (wasAPawnMove != other.wasAPawnMove) {
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "MoveInPointFigurePairs [newPositions=" + newPositions + ", wasAFigureHit="
				+ wasAFigureHit + ", wasAPawnMove=" + wasAPawnMove + ", nextGameState="
				+ nextGameState + "]";
	}
}
