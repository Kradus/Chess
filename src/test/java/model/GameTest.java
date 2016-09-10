package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.figure.FigureHolder;
import model.figure.FigureType;

/**
 * This JUnit test is for the testing of the game class.
 * 
 * @author Bjoern Hullmann
 */
public class GameTest {
	
	/**
	 * The figure holder for getting all figure.
	 */
	private final FigureHolder	figureHolder	= new FigureHolder();
	/**
	 * The game object which sould be tested
	 */
	private Game				game;
								
	/**
	 * Initialization of the game object.
	 */
	@Before
	public void init() {
		this.game = new Game();
		game.setGameField(new GameField());
	}
	
	/**
	 * Test if the holding of the old constellation works.
	 */
	@Test
	public void oldConstellationTest() {
		final IGameField gameField = game.getGameField();
		gameField.setFigure(Point.valueOf(1, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(2, 1),
				figureHolder.getFigure(FigureType.KING, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(1, 4),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(5, 6),
				figureHolder.getFigure(FigureType.BISHOP, Player.PLAYER_1));
				
		game.addConstellation(gameField);
		
		final IGameField gameField2 = new GameField();
		gameField2.setFigure(Point.valueOf(1, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField2.setFigure(Point.valueOf(1, 2),
				figureHolder.getFigure(FigureType.KING, Player.PLAYER_1));
		gameField2.setFigure(Point.valueOf(4, 1),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_1));
		gameField2.setFigure(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.BISHOP, Player.PLAYER_1));
		game.setGameField(gameField2);
		
		game.addConstellation(gameField2);
		
		Assert.assertEquals(game.getGameField(), game.getOldConstellation(0));
		Assert.assertNotEquals(game.getGameField(), game.getOldConstellation(1));
		
		Assert.assertEquals(gameField, game.getOldConstellation(1));
	}
	
	/**
	 * Test if there no phantom constellation
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void oldConstellationNotExistTest() {
		game.getOldConstellation(0);
	}
}
