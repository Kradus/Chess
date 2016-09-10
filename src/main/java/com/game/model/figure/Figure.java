package com.game.model.figure;

import com.game.model.Player;

/**
 * These are the figure on the game field. It hold the owner and the type of the figure. It is an
 * immutable object.
 * 
 * @author Bjoern Hullmann
 */
class Figure implements IFigure {
	
	/**
	 * The type of the figure.
	 */
	private final FigureType	type;
	/**
	 * The owner of the figure.
	 */
	private final Player		player;
	
	/**
	 * Create a figure
	 * 
	 * @param type
	 *            The type of the figure.
	 * @param player
	 *            The owner of the figure.
	 */
	Figure(final FigureType type, final Player player) {
		this.type = type;
		this.player = player;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FigureType getType() {
		return type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (player == null ? 0 : player.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Figure)) {
			return false;
		}
		final Figure other = (Figure) obj;
		if (player != other.player) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Figure [type=" + type + ", player=" + player + "]";
	}
}
