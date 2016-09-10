package com.game.model;

/**
 * The state of a game.
 * 
 * @author Bjoern Hullmann
 */
public enum GameState {
	/**
	 * It is the turn of player 1
	 */
	TURN_OF_PLAYER_1(false),
	/**
	 * It is the turn of player 2
	 */
	TURN_OF_PLAYER_2(false),
	/**
	 * Player 1 has a pawn on the end of the game field. Now he can change it to an other figure.
	 */
	PAWN_CHOOSE_PLAYER_1(false),
	/**
	 * Player 2 has a pawn on the end of the game field. Now he can change it to an other figure.
	 */
	PAWN_CHOOSE_PLAYER_2(false),
	/**
	 * Player 1 win the game.
	 */
	PLAYER_1_WIN(true),
	/**
	 * Player 2 win the game.
	 */
	PLAYER_2_WIN(true),
	/**
	 * The games goes in a drawn. No figure was hit or pawn was set since 50 turns.
	 */
	DRAWN_50_TURNS(true),
	/**
	 * The games goes in a drawn. The actual player can not make any move and his king is not in
	 * check.
	 */
	DRAWN_NO_TURN(true),
	/**
	 * The games goes in a drawn. Both players have not enough figure, so that they can hit the
	 * other king.
	 */
	DRAWN_NOT_ENOUGH_FIGURE(true),
	/**
	 * The games goes in a drawn because three times was reach the same game field constellation.
	 */
	DRAWN_THREE_TIMES_SAME_CONSTELLATION(true);
	
	/**
	 * If a game is finish or not.
	 */
	private final boolean isEndState;
	
	/**
	 * Create a game state.
	 * 
	 * @param isEndState
	 *            true if the game is finish when it reach this state.
	 */
	private GameState(final boolean isEndState) {
		this.isEndState = isEndState;
	}
	
	/**
	 * Return if this is a end state
	 * 
	 * @return true if it is a end state
	 */
	public boolean isEndState() {
		return isEndState;
	}
}
