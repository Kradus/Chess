package com.game.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.game.model.Game;
import com.game.model.GameField;
import com.game.model.IGameField;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureHolder;
import com.game.model.figure.FigureType;

/**
 * This JUnit test is for the testing of the Controller class.
 * 
 * @author Bjoern Hullmann
 */

public class ControllerTest {
	
	/**
	 * The figure holder for getting all figure.
	 */
	private final FigureHolder		figureHolder		= new FigureHolder();
	/**
	 * The factory for new filled game fields.
	 */
	private final GameFieldFactory	gameFieldFactory	= new GameFieldFactory(figureHolder,
			GameField::new);
	/**
	 * The controller object which should be tested.
	 */
	private Controller				controller;
	/**
	 * The game object on which the controller works.
	 */
	private Game					game;
	
	/**
	 * Initialization of the controller and game object.
	 */
	@Before
	public void init() {
		game = new Game();
		controller = new Controller(null, figureHolder, null, game,
				gameFieldFactory::createGameField, null);
	}
	
	/**
	 * Check if the players reach three times the same constellation but they did not make enough
	 * turns.
	 */
	@Test
	public void checkIfPlayersHaveReachThreeTimesTheSameConstellationTest_NotEnoughTurns() {
		Assert.assertFalse(controller.checkIfPlayersHaveReachThreeTimesTheSameConstellation());
	}
	
	/**
	 * Check if the players reach three times the same constellation but they did not make enough
	 * turns.
	 */
	@Test
	public void checkIfPlayersHaveReachThreeTimesTheSameConstellationTest_NotEnoughTurns2() {
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		Assert.assertFalse(controller.checkIfPlayersHaveReachThreeTimesTheSameConstellation());
	}
	
	/**
	 * Check if the players reach three times the same constellation. All old constellations are
	 * equals.
	 */
	@Test
	public void checkIfPlayersHaveReachThreeTimesTheSameConstellationTest_AllAreTheSame() {
		for (int i = 0; i < Controller.NEED_AMOUNT_OF_SAVE_FROM_LAST_CONSTELLATIONS; i++) {
			game.addConstellation(game.getGameField());
		}
		Assert.assertTrue(controller.checkIfPlayersHaveReachThreeTimesTheSameConstellation());
	}
	
	/**
	 * Check if the players reach three times the same constellation. The wrong are the same.
	 */
	@Test
	public void checkIfPlayersHaveReachThreeTimesTheSameConstellationTest_TheWrongAreTheSame() {
		game.addConstellation(game.getGameField().deepClone());
		game.addConstellation(game.getGameField().deepClone());
		game.addConstellation(game.getGameField().deepClone());
		game.getGameField().setFigure(Point.valueOf(5, 5),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_1));
		for (int i = 3; i < Controller.NEED_AMOUNT_OF_SAVE_FROM_LAST_CONSTELLATIONS; i++) {
			game.addConstellation(game.getGameField());
		}
		Assert.assertFalse(controller.checkIfPlayersHaveReachThreeTimesTheSameConstellation());
	}
	
	/**
	 * Check if the players reach three times the same constellation. The right ones are equals (1,
	 * 5, 9)
	 */
	@Test
	public void checkIfPlayersHaveReachThreeTimesTheSameConstellationTest_OnlyOneFiveAndNineAreTheSame() {
		final IGameField gameField = game.getGameField().deepClone();
		game.getGameField().setFigure(Point.valueOf(5, 5),
				figureHolder.getFigure(FigureType.QUEEN, Player.PLAYER_1));
		
		game.addConstellation(gameField);
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		game.addConstellation(gameField);
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		game.addConstellation(game.getGameField());
		game.addConstellation(gameField);
		
		Assert.assertTrue(controller.checkIfPlayersHaveReachThreeTimesTheSameConstellation());
	}
}
