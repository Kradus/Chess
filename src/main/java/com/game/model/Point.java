package com.game.model;

// I implemented my own Point class, because the Java classes only have getter methods with double
// as return value. Furthermore i don't want dirty code by using the fields directly.
/**
 * This class hold the x and y position of field from the game field. This is a immutable object.
 * 
 * @author Bjoern Hullmann
 */
public class Point {
	
	/**
	 * The lowest coordinate for x and y for the cache of the points.
	 */
	private static final int		CACHE_LOW		= 0;
	/**
	 * The highest coordinate for x and y for the cache of the points.
	 */
	private static final int		CACHE_HEIGHT	= IGameField.FIELD_SIZE - 1;
	/**
	 * The cache for the points.
	 */
	private static final Point[][]	CACHE			= new Point[CACHE_HEIGHT - CACHE_LOW
			+ 1][CACHE_HEIGHT - CACHE_LOW + 1];
			
	static {
		for (int y = CACHE_LOW; y <= CACHE_HEIGHT; y++) {
			final Point[] cacheRow = CACHE[y + CACHE_LOW];
			for (int x = CACHE_LOW; x <= CACHE_HEIGHT; x++) {
				cacheRow[x + CACHE_LOW] = new Point(x, y);
			}
		}
	}
	
	/**
	 * The x-coordinate
	 */
	private final int	x;
	/**
	 * The y-coordinate
	 */
	private final int	y;
						
	/**
	 * Create a new point
	 * 
	 * @param x
	 *            The x-coordinate
	 * @param y
	 *            The y-coordinate
	 */
	private Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x-value
	 * 
	 * @return the x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the y-value
	 * 
	 * @return the y value
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Point other = (Point) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	/**
	 * Get a Point object with the values of x and y. This method will always cache all Point
	 * objects on the game field.
	 * 
	 * @param x
	 *            The x-coordinate
	 * @param y
	 *            The y-coordinate
	 * @return A Point object with the given x and y values.
	 */
	public static Point valueOf(int x, int y) {
		if (x >= CACHE_LOW && x <= CACHE_HEIGHT && y >= CACHE_LOW && y <= CACHE_HEIGHT) {
			return CACHE[y + CACHE_LOW][x + CACHE_LOW];
		}
		return new Point(x, y);
	}
}
