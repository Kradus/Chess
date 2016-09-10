package controller.hitKing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.GameFieldFactory;
import model.IGameField;
import model.Player;
import model.Point;
import model.figure.FigureHolder;
import model.figure.FigureType;

/**
 * This JUnit test is for the testing of the RookHitKingChecker class.
 * 
 * @author Bjoern Hullmann
 */
public class RookHitKingCheckerTest {
	
	/**
	 * The object which should be tested.
	 */
	private final RookHitKingChecker	rookHitKingChecker	= new RookHitKingChecker();
	/**
	 * The figure holder for getting all figure.
	 */
	private final FigureHolder			figureHolder		= new FigureHolder();
	/**
	 * The factory for the game field object.
	 */
	private final GameFieldFactory		gameFieldFactory	= new GameFieldFactory(figureHolder);
	/**
	 * The actual game field.
	 */
	private IGameField					gameField;
										
	/**
	 * Initialization of the game field.
	 */
	@Before
	public void init() {
		gameField = gameFieldFactory.createGameField();
	}
	
	/**
	 * Test an invalid move.
	 */
	@Test
	public void invalidMoveTest() {
		Assert.assertFalse(rookHitKingChecker.canHitTheKing(Point.valueOf(0, 0),
				Point.valueOf(2, 0), gameField::getFigure));
	}
	
	/**
	 * Test an invalid move over other figure.
	 */
	@Test
	public void invalidMoveOverAPawnTest() {
		Assert.assertFalse(rookHitKingChecker.canHitTheKing(Point.valueOf(0, 0),
				Point.valueOf(0, 4), gameField::getFigure));
	}
	
	/**
	 * Test an invalid move over other figure.
	 */
	@Test
	public void invalidMoveOverAFigureTest() {
		Assert.assertFalse(rookHitKingChecker.canHitTheKing(Point.valueOf(0, 0),
				Point.valueOf(8, 0), gameField::getFigure));
	}
	
	/**
	 * Test a valid move with hitting a queen
	 */
	@Test
	public void validMoveHitTheQueenTest() {
		gameField.setFigure(Point.valueOf(0, 1), null);
		gameField.setFigure(Point.valueOf(0, 4),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_2));
		Assert.assertTrue(rookHitKingChecker.canHitTheKing(Point.valueOf(0, 0), Point.valueOf(0, 4),
				gameField::getFigure));
	}
}
