package com.game.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.game.model.figure.FigureHolder;
import com.game.model.figure.FigureType;

/**
 * This JUnit test is for the testing of the game field class.
 * 
 * @author Bjoern Hullmann
 */
public class GameFieldTest {
	
	/**
	 * The figure holder for getting all figure.
	 */
	private final FigureHolder	figureHolder	= new FigureHolder();
	/**
	 * The game field object which should be tested.
	 */
	private GameField			gameField;
	/**
	 * A counter for the method forEachTest().
	 */
	private int					counter			= 0;
	
	/**
	 * Initialization of the game field object.
	 */
	@Before
	public void init() {
		gameField = new GameField();
		gameField.setFigure(Point.valueOf(1, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(2, 1),
				figureHolder.getFigure(FigureType.KING, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(1, 4),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(5, 6),
				figureHolder.getFigure(FigureType.BISHOP, Player.PLAYER_1));
	}
	
	/**
	 * Test if the cloning works.
	 */
	@Test
	public void cloneTest() {
		final GameField clone = gameField.deepClone();
		
		Assert.assertEquals(gameField, clone);
	}
	
	/**
	 * Test if the setFigure() and getFigure() works right.
	 */
	@Test
	public void setAndGetterTest() {
		Assert.assertEquals(gameField.getFigure(Point.valueOf(1, 1)),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		Assert.assertEquals(gameField.getFigure(Point.valueOf(2, 1)),
				figureHolder.getFigure(FigureType.KING, Player.PLAYER_1));
		Assert.assertEquals(gameField.getFigure(Point.valueOf(1, 4)),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_1));
		Assert.assertEquals(gameField.getFigure(Point.valueOf(5, 6)),
				figureHolder.getFigure(FigureType.BISHOP, Player.PLAYER_1));
		for (int x = 0; x < IGameField.FIELD_SIZE; x++) {
			Assert.assertNull(gameField.getFigure(Point.valueOf(x, 0)));
			Assert.assertNull(gameField.getFigure(Point.valueOf(x, 2)));
			Assert.assertNull(gameField.getFigure(Point.valueOf(x, 3)));
			Assert.assertNull(gameField.getFigure(Point.valueOf(x, 5)));
			Assert.assertNull(gameField.getFigure(Point.valueOf(x, 7)));
		}
	}
	
	/**
	 * Test if the forEach method goes over all position of the game field
	 */
	@Test
	public void forEachTest() {
		counter = 0;
		gameField.forEach(figure -> {
			if (figure != null) {
				counter++;
			}
		});
		
		Assert.assertEquals(counter, 4);
	}
}
