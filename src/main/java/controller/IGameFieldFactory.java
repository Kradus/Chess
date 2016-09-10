package controller;

import model.IGameField;

/**
 * Classes with this interface can be create a new game field with the start constellation.
 * 
 * @author Bjoern Hullmann
 */
interface IGameFieldFactory {
	
	/**
	 * Create a GameField with the start constellation.
	 * 
	 * @return A new GameField
	 */
	IGameField createGameField();
}
