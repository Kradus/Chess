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
 * This JUnit test is for the testing of the PawnHitKingChecker class.
 * 
 * @author Bjoern Hullmann
 */
public class PawnHitKingCheckerTest {
	
	/**
	 * The object which should be tested.
	 */
	private final PawnHitKingChecker	pawnHitKingChecker	= new PawnHitKingChecker();
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
		Assert.assertFalse(pawnHitKingChecker.canHitTheKing(Point.valueOf(1, 1),
				Point.valueOf(1, 2), gameField::getFigure));
	}
	
	/**
	 * Test an invalid move because of trying to hit a own pawn.
	 */
	@Test
	public void invalidMoveHitAOwnPawnTest() {
		Assert.assertFalse(pawnHitKingChecker.canHitTheKing(Point.valueOf(1, 1),
				Point.valueOf(2, 1), gameField::getFigure));
	}
	
	/**
	 * Test a valid move with hitting a queen
	 */
	@Test
	public void validMoveHitTheQueenTest() {
		gameField.setFigure(Point.valueOf(2, 2),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_2));
		Assert.assertTrue(pawnHitKingChecker.canHitTheKing(Point.valueOf(1, 1), Point.valueOf(2, 2),
				gameField::getFigure));
	}
}
