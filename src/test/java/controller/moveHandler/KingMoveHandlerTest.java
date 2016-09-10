package controller.moveHandler;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import controller.ICheckChecker;
import controller.Move;
import controller.MoveInPointFigurePairs;
import model.Game;
import model.GameField;
import model.GameState;
import model.Player;
import model.Point;
import model.figure.FigureHolder;
import model.figure.FigureType;
import model.figure.IFigure;

/**
 * This JUnit test is for the testing of the KingMoveHandler class.
 * 
 * @author Bjoern Hullmann
 */
public class KingMoveHandlerTest {
	
	/**
	 * The object that should be tested
	 */
	private KingMoveHandler		handler;
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
		
		handler = new KingMoveHandler(game, figureHolder, checkChecker);
		
		gameField.setFigure(Point.valueOf(4, 0),
				figureHolder.getUnmovedFigure(FigureType.KING, Player.PLAYER_1));
	}
	
	/**
	 * Check if it has the right figure type
	 */
	@Test
	public void rightFigureType() {
		Assert.assertEquals(handler.getFigureType(), FigureType.KING);
	}
	
	/**
	 * Test an invalid move
	 */
	@Test
	public void invalidMoveTest() {
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(2, 1))));
	}
	
	/**
	 * Test the castling without rook
	 */
	@Test
	public void invalidSpecialMoveTest() {
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(6, 0))));
	}
	
	/**
	 * Test the castling with rook on the right side
	 */
	@Test
	public void validSpecialMoveTest1() {
		gameField.setFigure(Point.valueOf(7, 0),
				figureHolder.getUnmovedFigure(FigureType.ROOK, Player.PLAYER_1));
				
		Assert.assertTrue(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(6, 0))));
	}
	
	/**
	 * Test the castling with rook on the left side
	 */
	@Test
	public void validSpecialMoveTest2() {
		gameField.setFigure(Point.valueOf(0, 0),
				figureHolder.getUnmovedFigure(FigureType.ROOK, Player.PLAYER_1));
				
		Assert.assertTrue(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(2, 0))));
	}
	
	/**
	 * Test the castling with a rook, which was already moved
	 */
	@Test
	public void inValidSpecialMoveTest2() {
		gameField.setFigure(Point.valueOf(0, 0),
				figureHolder.getFigure(FigureType.ROOK, Player.PLAYER_1));
				
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(2, 0))));
	}
	
	/**
	 * Test an invalid move over a pawn
	 */
	@Test
	public void invalidMoveOverAPawnTest() {
		gameField.setFigure(Point.valueOf(5, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(4, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(5, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(3, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(3, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
				
		Assert.assertFalse(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(6, 0))));
	}
	
	/**
	 * Test a valid move where a bishop is hitting
	 */
	@Test
	public void validMoveHitTheBishopTest() {
		gameField.setFigure(Point.valueOf(5, 1),
				figureHolder.getFigure(FigureType.BISHOP, Player.PLAYER_2));
		Assert.assertTrue(
				handler.isItAValidMove(new Move(Point.valueOf(4, 0), Point.valueOf(5, 1))));
	}
	
	/**
	 * Test if the translateMove method works right
	 */
	@Test
	public void translateMoveTest() {
		gameField.setFigure(Point.valueOf(7, 0),
				figureHolder.getFigure(FigureType.ROOK, Player.PLAYER_1));
		final Move move = new Move(Point.valueOf(4, 0), Point.valueOf(6, 0));
		
		final MoveInPointFigurePairs translatedMove = new MoveInPointFigurePairs();
		translatedMove.setNextGameState(GameState.TURN_OF_PLAYER_2);
		translatedMove.addNewPosition(Point.valueOf(4, 0), null);
		translatedMove.addNewPosition(Point.valueOf(6, 0),
				figureHolder.getFigure(FigureType.KING, Player.PLAYER_1));
		translatedMove.addNewPosition(Point.valueOf(7, 0), null);
		translatedMove.addNewPosition(Point.valueOf(5, 0),
				figureHolder.getFigure(FigureType.ROOK, Player.PLAYER_1));
				
		Assert.assertEquals(handler.translateMove(move), translatedMove);
	}
	
	/**
	 * The king is surrounded from own pawns
	 */
	public void hasFigureATurnTest() {
		gameField.setFigure(Point.valueOf(5, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(4, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(5, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(3, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(3, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
				
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(4, 0)));
	}
	
	/**
	 * The king is surrounded from other pawns
	 */
	public void hasFigureATurnTest2() {
		gameField.setFigure(Point.valueOf(5, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(4, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(5, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(3, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
		gameField.setFigure(Point.valueOf(3, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_2));
				
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(4, 0)));
	}
	
	/**
	 * Free Field
	 */
	public void hasFigureATurnTest4() {
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(4, 0)));
	}
	
	/**
	 * Only one possible
	 */
	public void hasFigureATurnTest5() {
		gameField.setFigure(Point.valueOf(5, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(4, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(5, 1),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
		gameField.setFigure(Point.valueOf(3, 0),
				figureHolder.getFigure(FigureType.PAWN, Player.PLAYER_1));
				
		Assert.assertTrue(handler.hasFigureATurnFor(Point.valueOf(4, 0)));
	}
}
