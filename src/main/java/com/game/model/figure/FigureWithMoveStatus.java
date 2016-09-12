package com.game.model.figure;

import com.game.model.Player;

/**
 * The implementation of a figure with move status. It hold the status if the figure was move or
 * not. This is a immutable object.
 * 
 * @author Bjoern Hullmann
 */
class FigureWithMoveStatus extends Figure implements IFigureWithMoveStatus {
	
	/**
	 * If the figure was moved in any turn of the whole game round.
	 */
	private final boolean wasMoved;
	
	/**
	 * Create a figure.
	 * 
	 * @param type
	 *            The type from which the figure is.
	 * @param player
	 *            The owner of the figure.
	 * @param wasMoved
	 *            If the figure was moved or not.
	 */
	FigureWithMoveStatus(final FigureType type, final Player player, final boolean wasMoved) {
		super(type, player);
		this.wasMoved = wasMoved;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean wasMoved() {
		return wasMoved;
	}
}
