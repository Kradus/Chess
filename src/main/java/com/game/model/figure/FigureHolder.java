package com.game.model.figure;

import com.game.model.Player;

/**
 * This class hold all figure for each player. So that it is not needed to create always new figure.
 * 
 * @author Bjoern Hullmann
 */
public class FigureHolder implements IFigureHolder {
	
	/**
	 * All figures which was moved for all players.
	 */
	private final Figure[][]	figures			= new Figure[Player.values().length][FigureType
			.values().length];
	/**
	 * All figures which was not moved for all players.
	 */
	private final Figure[][]	figuresUnmoved	= new Figure[Player.values().length][FigureType
			.values().length];
	
	/**
	 * Create a figure holder which hold for each figure type, for all players a moved and unmoved
	 * figure.
	 */
	public FigureHolder() {
		for (final Player player : Player.values()) {
			final Figure[] figureArrayFromPlayer = figures[player.ordinal()];
			final Figure[] figureUnmovedArrayFromPlayer = figuresUnmoved[player.ordinal()];
			
			for (final FigureType type : FigureType.values()) {
				figureArrayFromPlayer[type.ordinal()] = createFigureFor(player, type);
				figureUnmovedArrayFromPlayer[type.ordinal()] = createUnmovedFigureFor(player, type);
			}
		}
	}
	
	/**
	 * Create a figure which was moved.
	 * 
	 * @param player
	 *            The owner of the figure
	 * @param type
	 *            The FigureType of the figure
	 * @return The created Figure.
	 */
	private static Figure createFigureFor(final Player player, final FigureType type) {
		return type.isMoveStatusIsRelevant() ? new FigureWithMoveStatus(type, player, true)
				: new Figure(type, player);
	}
	
	/**
	 * Create a figure which is unmoved.
	 * 
	 * @param player
	 *            The owner of the figure
	 * @param type
	 *            The FigureType of the figure
	 * @return The created Figure.
	 */
	private final Figure createUnmovedFigureFor(final Player player, final FigureType type) {
		// Create a new unmoved figure or use the same from the other array.
		return type.isMoveStatusIsRelevant() ? new FigureWithMoveStatus(type, player, false)
				: figures[player.ordinal()][type.ordinal()];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final IFigure getFigure(final FigureType type, final Player player) {
		return figures[player.ordinal()][type.ordinal()];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IFigure getUnmovedFigure(final FigureType type, final Player player) {
		return figuresUnmoved[player.ordinal()][type.ordinal()];
	}
}
