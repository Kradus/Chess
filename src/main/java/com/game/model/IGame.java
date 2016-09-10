package com.game.model;

import java.util.Observer;

/**
 * The interface for the game field. Classes with this interface should should hold the game field
 * and the game state. Furthermore you can get throw this old constellation from the game field.
 * 
 * @author Bjoern Hullmann
 */
public interface IGame {
	
	/**
	 * Get the actual game state from the game.
	 * 
	 * @return The game state.
	 */
	GameState getGameState();
	
	/**
	 * Set the game state of the game.
	 * 
	 * @param state
	 *            The new state of the game.
	 */
	void setGameState(GameState state);
	
	/**
	 * Set the actual gameField
	 * 
	 * @param gameField
	 *            The new actual game field
	 */
	void setGameField(IGameField gameField);
	
	/**
	 * Get the actual game field
	 * 
	 * @return The actual GameField
	 */
	IGameField getGameField();
	
	/**
	 * The number of how many old constellation are holden
	 * 
	 * @return how many old constellation are hold
	 */
	int sizeOfLastConstellations();
	
	/**
	 * Add a game field constellation
	 * 
	 * @param gameField
	 *            the game field with should be hold as an old constellation
	 */
	void addConstellation(IGameField gameField);
	
	/**
	 * Delete the oldest constellation of the game fields
	 */
	void removeOldestConstellation();
	
	/**
	 * Get an old game field constellation. Index 0 means the youngest
	 * 
	 * @param index
	 *            Which constellation you want
	 * @return A GameField with the Constellation for (index + 1) turn
	 */
	IGameField getOldConstellation(int index);
	
	/**
	 * Notify all observers over changes
	 */
	void notifyObservers();
	
	/**
	 * Add a observer
	 * 
	 * @param observer
	 *            the observer to add
	 */
	void addObserver(Observer observer);
}
