package model.figure;

import model.Player;

/**
 * This class hold all figure. Through this you can get all figure for the game.
 * 
 * @author Bjoern Hullmann
 */
public interface IFigureHolder {
	
	/**
	 * Get a immutable, moved figure
	 * 
	 * @param type
	 *            The type of the Figure
	 * @param player
	 *            The owner of the figure
	 * @return A moved Figure with the given type and owner
	 */
	IFigure getFigure(FigureType type, Player player);
	
	/**
	 * Get a immutable, unmoved figure
	 * 
	 * @param type
	 *            The type of the Figure
	 * @param player
	 *            The owner of the figure
	 * @return A unmoved Figure with the given type and owner
	 */
	IFigure getUnmovedFigure(FigureType type, Player player);
}
