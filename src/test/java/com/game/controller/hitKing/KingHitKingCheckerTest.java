package com.game.controller.hitKing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.game.controller.GameFieldFactory;
import com.game.model.GameField;
import com.game.model.IGameField;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureHolder;
import com.game.model.figure.FigureType;

/**
 * This JUnit test is for the testing of the KingHitKingChecker class.
 * 
 * @author Bjoern Hullmann
 */
public class KingHitKingCheckerTest {
	
	/**
	 * The object which should be tested.
	 */
	private final KingHitKingChecker	kingHitKingChecker	= new KingHitKingChecker();
	/**
	 * The figure holder for getting all figure.
	 */
	private final FigureHolder			figureHolder		= new FigureHolder();
	/**
	 * The factory for the game field object.
	 */
	private final GameFieldFactory		gameFieldFactory	= new GameFieldFactory(figureHolder,
			GameField::new);
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
		Assert.assertFalse(kingHitKingChecker.canHitTheKing(Point.valueOf(4, 0),
				Point.valueOf(1, 0), gameField::getFigure));
	}
	
	/**
	 * Test an invalid move over other figure.
	 */
	@Test
	public void invalidMoveOverAPawnTest() {
		Assert.assertFalse(kingHitKingChecker.canHitTheKing(Point.valueOf(4, 0),
				Point.valueOf(5, 5), gameField::getFigure));
	}
	
	/**
	 * Test a valid move with hitting a queen
	 */
	@Test
	public void validMoveHitTheQueenTest() {
		gameField.setFigure(Point.valueOf(3, 1),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_2));
		Assert.assertTrue(kingHitKingChecker.canHitTheKing(Point.valueOf(4, 0), Point.valueOf(3, 1),
				gameField::getFigure));
	}
}
