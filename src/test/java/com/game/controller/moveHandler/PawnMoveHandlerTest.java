package com.game.controller.moveHandler;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.game.controller.ICheckChecker;
import com.game.controller.Move;
import com.game.controller.MoveInPointFigurePairs;
import com.game.model.Game;
import com.game.model.GameField;
import com.game.model.GameState;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureHolder;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;

/**
 * This JUnit test is for the testing of the PawnMoveHandler class.
 * 
 * @author Bjoern Hullmann
 */
public class PawnMoveHandlerTest {
	
	/**
	 * The object that should be tested
	 */
	private PawnMoveHandler		handler;
	/**
	 * The game on which the test runs
	 */
	private final Game			game			= new Game();
	/**
	 * The game field on which the test runs
	 */
	private final GameField		gameField		= new GameField();
	/**
	 * The figure holder for getting all figure.
	 */
	private final FigureHolder	figureHolder	= new FigureHolder();
												
	/**
	 * Initialization of the game and game field object and the object which should be tested here.
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		final ICheckChecker checkChecker = Mockito.mock(ICheckChecker.class);
		Mockito.when(checkChecker.isKingInCheckForPlayer(Matchers.any())).thenReturn(false);
		Mockito.when(checkChecker.isKingInCheckForPlayer(Matchers.any(),
				(MoveInPointFigurePairs) Matchers.any())).thenReturn(false);
		Mockito.when(checkChecker.isKingInCheckForPlayer(Matchers.any(),
				(Function<Point, IFigure>) Matchers.any())).thenReturn(false);
		Mockito.when(checkChecker.isPositionSafeForPlayer(Matchers.any(), Matchers.any(),
				Matchers.any())).thenReturn(true);
				
		game.setGameField(gameField);
		
		handler = new PawnMoveHandler(game, figureHolder, checkChecker);
		
		gameField.setFigure(Point.valueOf(7, 6),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
	}
	
	/**
	 * Check if it has the right figure type
	 */
	@Test
	public void rightFigureType() {
		Assert.assertEquals(handler.getFigureType(), FigureType.PAWN);
	}
	
	/**
	 * Test an invalid move
	 */
	@Test
	public void invalidMoveTest() {
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(7, 6), Point.valueOf(7, 3))));
	}
	
	/**
	 * Test an other invalid move
	 */
	@Test
	public void invalidMoveTest2() {
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(7, 6), Point.valueOf(6, 5))));
	}
	
	/**
	 * Test a valid move
	 */
	@Test
	public void validMoveTest() {
		gameField.setFigure(Point.valueOf(7, 6),
				figureHolder.getUnmovedFigure(FigureType.PAWN, Player.PLAYER_1));
		Assert.assertTrue(
				handler.isItAValidMove(new Move(Point.valueOf(7, 6), Point.valueOf(7, 4))));
	}
	
	/**
	 * Test a valid move with hitting a pawn
	 */
	@Test
	public void validMoveTest2() {
		gameField.setFigure(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
				
		Assert.assertTrue(
				handler.isItAValidMove(new Move(Point.valueOf(7, 6), Point.valueOf(6, 5))));
	}
	
	/**
	 * Test an invalid move over a pawn
	 */
	@Test
	public void invalidMoveOverAPawnTest() {
		gameField.setFigure(Point.valueOf(6, 6),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(7, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
				
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(7, 6), Point.valueOf(7, 5))));
	}
	
	/**
	 * Test a valid move where a bishop is hitting
	 */
	@Test
	public void validMoveHitTheBishopTest() {
		gameField.setFigure(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.BISHOP, Player.PLAYER_2));
		Assert.assertTrue(
				handler.isItAValidMove(new Move(Point.valueOf(7, 6), Point.valueOf(6, 5))));
	}
	
	/**
	 * Test if the translateMove method works right
	 */
	@Test
	public void translateMoveTest() {
		gameField.setFigure(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		final Move move = new Move(Point.valueOf(7, 6), Point.valueOf(6, 5));
		
		final MoveInPointFigurePairs translatedMove = new MoveInPointFigurePairs();
		translatedMove.setNextGameState(GameState.TURN_OF_PLAYER_2);
		translatedMove.setWasAPawnMove(true);
		translatedMove.setWasAFigureHit(true);
		translatedMove.addNewPosition(Point.valueOf(7, 6), null);
		translatedMove.addNewPosition(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
				
		Assert.assertEquals(handler.translateMove(move), translatedMove);
	}
	
	/**
	 * The pawn is surrounded from own pawns
	 */
	public void hasFigureATurnTest() {
		gameField.setFigure(Point.valueOf(6, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(7, 5),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
				
		Assert.assertFalse(handler.hasFigureATurnFor(Point.valueOf(7, 6)));
	}
	
	/**
	 * The rook is surrounded from other pawns
	 */
	public void hasFigureATurnTest2() {
		gameField.setFigure(Point.valueOf(1, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(0, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(1, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
				
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(7, 6)));
	}
	
	/**
	 * Free Field
	 */
	public void hasFigureATurnTest4() {
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(7, 6)));
	}
	
	/**
	 * Only one possible
	 */
	public void hasFigureATurnTest5() {
		gameField.setFigure(Point.valueOf(7, 4),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
				
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(7, 6)));
	}
}
