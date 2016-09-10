package model.figure;

/**
 * This are all types, which a figure can be.
 * 
 * @author Bjoern Hullmann
 */
public enum FigureType {
	/**
	 * The pawn
	 */
	PAWN(' ', false, true),
	/**
	 * The Knight
	 */
	KNIGHT('N', true, false),
	/**
	 * The Bishop
	 */
	BISHOP('B', true, false),
	/**
	 * The Rook
	 */
	ROOK('R', true, true),
	/**
	 * The Queen
	 */
	QUEEN('Q', true, false),
	/**
	 * The King
	 */
	KING('K', false, true);
	
	/**
	 * This is the index for the old constellations. On this position in the list is always the
	 * constellation which is relevant for a move from the pawn.
	 */
	public static final int	INDEX_OF_OLD_CONSTELLATION_FOR_PAWN_MOVE	= 1;
																		
	/**
	 * The print key
	 */
	private final char		key;
	/**
	 * Can a pawn change to it, when it reach the end of the game field
	 */
	private final boolean	pawnCanChangeIntoIt;
	/**
	 * If it is relevant if the figure was move or not for a move from a figure.
	 */
	private final boolean	moveStatusIsRelevant;
							
	/**
	 * Create a figure type.
	 * 
	 * @param key
	 *            The print key.
	 * @param pawnCanChangeIntoIt
	 *            If a pawn can change into it.
	 * @param moveStatusIsRelevant
	 *            If the move status is relevant for any move.
	 */
	private FigureType(final char key, final boolean pawnCanChangeIntoIt,
			final boolean moveStatusIsRelevant) {
		this.key = key;
		this.pawnCanChangeIntoIt = pawnCanChangeIntoIt;
		this.moveStatusIsRelevant = moveStatusIsRelevant;
	}
	
	/**
	 * Get the key for the printing.
	 * 
	 * @return the char of the figure type.
	 */
	public char getKey() {
		return key;
	}
	
	/**
	 * Check if a pawn can change to this figure type.
	 * 
	 * @return true if a pawn can change to it.
	 */
	public boolean isPawnCanChangeIntoIt() {
		return pawnCanChangeIntoIt;
	}
	
	/**
	 * Check if the move status of a figure type is relevant
	 * 
	 * @return true if the move status is relevant
	 */
	public boolean isMoveStatusIsRelevant() {
		return moveStatusIsRelevant;
	}
}
