package com.game.model;

import java.util.LinkedList;
import java.util.Observable;

/**
 * This class old the actual game field and the old constellation of the game field. Furthermore
 * hold it the current game state.
 * 
 * @author Bjoern Hullmann
 */
public class Game extends Observable implements IGame {
	
	/**
	 * The actual state of the game.
	 */
	private GameState						state				= GameState.TURN_OF_PLAYER_1;
	/**
	 * The actual game field.
	 */
	private IGameField						gameField;
	/**
	 * A list with the last constellation of the game field.
	 */
	private final LinkedList<IGameField>	lastConstellation	= new LinkedList<>();
																
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGameField(final IGameField gameField) {
		this.gameField = gameField;
		setChanged();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameState getGameState() {
		return state;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGameState(final GameState state) {
		this.state = state;
		setChanged();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGameField getGameField() {
		return gameField;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int sizeOfLastConstellations() {
		return lastConstellation.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addConstellation(final IGameField gameField) {
		lastConstellation.addFirst(gameField);
		setChanged();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeOldestConstellation() {
		lastConstellation.removeLast();
		setChanged();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGameField getOldConstellation(final int index) {
		return lastConstellation.get(index);
	}
}
