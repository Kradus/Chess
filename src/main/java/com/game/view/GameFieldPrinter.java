package com.game.view;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import com.game.main.ResourceConst;
import com.game.model.IGame;
import com.game.model.IGameField;
import com.game.model.Player;

/**
 * This method print always the current game field when it was changed. Furthermore he printed for
 * each game state a specific test. So that the users know what they can do.
 * 
 * @author Bjoern Hullmann
 */
class GameFieldPrinter implements IGameFieldPrinter {
	
	/**
	 * The game which he print
	 */
	private final IGame		game;
	/**
	 * The game field template for printing.
	 */
	private final String	gameField;
	
	/**
	 * Create printer with read out the template from a file.
	 * 
	 * @param game
	 *            The game which he should print.
	 */
	@Inject
	GameFieldPrinter(final IGame game) {
		this.game = game;
		
		final Path path = FileSystems.getDefault()
				.getPath(ResourceConst.USER_DIR + "/" + ResourceConst.GAME_FIELD_FOR_PRINTING);
		try {
			final List<String> file = Files.readAllLines(path);
			final StringBuilder sb = new StringBuilder();
			file.forEach(line -> {
				sb.append(line).append("\n");
			});
			gameField = sb.toString();
		} catch (final IOException e) {
			throw new InternalError(e);
		}
		game.addObserver(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		printGameField();
		printState();
	}
	
	/**
	 * Prints the current game field
	 */
	private void printGameField() {
		final List<String> data = new ArrayList<>(IGameField.FIELD_SIZE * IGameField.FIELD_SIZE);
		game.getGameField().forEach(figure -> {
			if (figure == null) {
				data.add("  ");
			} else if (figure.getPlayer() == Player.PLAYER_1) {
				data.add(figure.getType().getKey() + "1");
			} else {
				data.add(figure.getType().getKey() + "2");
			}
		});
		System.out.println(MessageFormat.format(gameField, data.toArray()));
	}
	
	/**
	 * Prints a game state specific message for the player.
	 */
	private void printState() {
		switch (game.getGameState()) {
		case DRAW_50_TURNS:
			System.out.println(
					"Game ends in a draw. There was now pawn move or figure hit for 50 turns.");
			break;
		case DRAW_NOT_ENOUGH_FIGURE:
			System.out.println("Game ends in a draw. Players don't have enough figures.");
			break;
		case DRAW_NO_TURN:
			System.out.println("Game ends in a draw. Both players have no possible turns left.");
			break;
		case DRAW_THREE_TIMES_SAME_CONSTELLATION:
			System.out.println(
					"Game ends in a draw. The same constellation was reached for the third time.");
			break;
		case PAWN_CHOOSE_PLAYER_1:
			System.out.println("Player 1 choose a figure type for your pawn.");
			break;
		case PAWN_CHOOSE_PLAYER_2:
			System.out.println("Player 2 choose a figure type for your pawn.");
			break;
		case PLAYER_1_WIN:
			System.out.println("Player 1 wins the game.");
			break;
		case PLAYER_2_WIN:
			System.out.println("Player 2 wins the game.");
			break;
		case TURN_OF_PLAYER_1:
			System.out.println("It is the turn of player 1.");
			break;
		case TURN_OF_PLAYER_2:
			System.out.println("It is the turn of player 2.");
			break;
		}
	}
}
