package model;

import java.util.Arrays;
import java.util.function.Consumer;

import model.figure.IFigure;

/**
 * This is the data class which hold old figure with here position and how many turns are gone after
 * a pawn or a figure was hit.
 * 
 * @author Bjoern Hullmann
 */
public class GameField implements IGameField {
	
	/**
	 * The field with all figure on it. The first index is the y position and the second the x.
	 * Because you can better print it.
	 */
	private final IFigure[][]	field						= new IFigure[FIELD_SIZE][FIELD_SIZE];
	/**
	 * The amount of turns which are gone after the last pawn was set or are figure was hit.
	 */
	private int					turnsAfterHitOrMoveAPawn	= 0;
															
	/**
	 * Create a empty game field.
	 */
	public GameField() {}
	
	/**
	 * Clone a game field.
	 * 
	 * @param toClone
	 *            The game field, which should be clone.
	 */
	private GameField(final GameField toClone) {
		for (int i = 0; i < FIELD_SIZE; i++) {
			System.arraycopy(toClone.field[i], 0, field[i], 0, FIELD_SIZE);
		}
		turnsAfterHitOrMoveAPawn = toClone.turnsAfterHitOrMoveAPawn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFigure(final Point pos, final IFigure figure) {
		field[pos.getY()][pos.getX()] = figure;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IFigure getFigure(final Point pos) {
		return field[pos.getY()][pos.getX()];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementTurnsAfterHitOrMoveAPawn() {
		turnsAfterHitOrMoveAPawn++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTurnsAfterHitOrMoveAPawn() {
		return turnsAfterHitOrMoveAPawn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetTurnsAfterHitOrMoveAPawn() {
		turnsAfterHitOrMoveAPawn = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(final Consumer<IFigure> consumer) {
		for (int y = 0; y < FIELD_SIZE; y++) {
			final IFigure[] row = field[y];
			for (final IFigure figure : row) {
				consumer.accept(figure);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(field);
		result = prime * result + turnsAfterHitOrMoveAPawn;
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
		if (!(obj instanceof GameField)) {
			return false;
		}
		final GameField other = (GameField) obj;
		if (!Arrays.deepEquals(field, other.field)) {
			return false;
		}
		if (turnsAfterHitOrMoveAPawn != other.turnsAfterHitOrMoveAPawn) {
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "GameField [field=" + Arrays.toString(field) + ", turnAfterHitOrMoveAPawn="
				+ turnsAfterHitOrMoveAPawn + "]";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameField deepClone() {
		return new GameField(this);
	}
}
