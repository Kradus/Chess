package model;

/**
 * The Player object. With this you can decided e.g. which figure contains to which player.
 * 
 * @author Bjoern Hullmann
 */
public enum Player {
	/**
	 * The first player. He is always on the bottom of the game field.
	 */
	PLAYER_1(GameState.TURN_OF_PLAYER_1, GameState.PLAYER_1_WIN),
	/**
	 * The first player. He is always on the top of the game field.
	 */
	PLAYER_2(GameState.TURN_OF_PLAYER_2, GameState.PLAYER_2_WIN);
	
	/**
	 * The state when this player can make a turn.
	 */
	private final GameState	turnState;
	/**
	 * The state when this player win.
	 */
	private final GameState	winState;
							
	/**
	 * Create a player.
	 * 
	 * @param turnState
	 *            The turn state of the player.
	 * @param winState
	 *            The win state of the player.
	 */
	private Player(final GameState turnState, final GameState winState) {
		this.turnState = turnState;
		this.winState = winState;
	}
	
	/**
	 * Get the turn game state for this player
	 * 
	 * @return the game state for his turn
	 */
	public GameState getTurnState() {
		return turnState;
	}
	
	/**
	 * Get the win game state for this player
	 * 
	 * @return the game state when he win the game
	 */
	public GameState getWinState() {
		return winState;
	}
}
