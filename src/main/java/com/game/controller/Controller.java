package com.game.controller;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import com.game.controller.minFigure.IMinFigureCheck;
import com.game.controller.moveHandler.IMoveHandler;
import com.game.model.GameState;
import com.game.model.IGame;
import com.game.model.IGameField;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigure;
import com.game.model.figure.IFigureHolder;

/**
 * A controller that check if the input from the user are valid. Furthermore change this class the
 * model.
 * 
 * @author Bjoern Hullmann
 */
public class Controller implements IController {
	
	/**
	 * Number of turns when it is stalemate when no pawn was move or a figure was hit
	 */
	private static final int			NUMBER_OF_TURN_WHEN_IS_STALEMATE						= 50;
	/**
	 * The maximal amount of old constellation which are needed for the logic.
	 */
	static final int					NEED_AMOUNT_OF_SAVE_FROM_LAST_CONSTELLATIONS	= 9;
	/**
	 * The index of the three old constellation which are must check if the user reach the same game
	 * field three times.
	 */
	private static final int[]			TURN_TO_BE_EQUALS								= { 0, 4,
			8 };
	
	/**
	 * The FigureMoveHandler for getting MoveHandlers.
	 */
	private final IFigureMoveHandler	figureMoveHandler;
	/**
	 * The figure holder for getting all figure.
	 */
	private final IFigureHolder			figureHolder;
	/**
	 * The checker to check if the king is in check.
	 */
	private final ICheckChecker			checkChecker;
	/**
	 * The game
	 */
	private final IGame					game;
	/**
	 * The provider where the controller can get new filled game field.
	 */
	private final Provider<IGameField>	gameFieldProvider;
	/**
	 * A set with all condition for the check if a player have enough figure left.
	 */
	private final Set<IMinFigureCheck>	minFigureChecks;
	
	/**
	 * Create a controller for a game.
	 * 
	 * @param figureMoveHandler
	 *            The FigureMoveHandler for getting MoveHandlers
	 * @param figureHolder
	 *            The figure holder for getting all figure.
	 * @param checkChecker
	 *            The checker to check if the king is in check.
	 * @param game
	 *            The game for which the controller is.
	 * @param gameFieldProvider
	 *            The provider where the controller can get new filled game field.
	 * @param minFigureChecks
	 *            A set with all condition of minimal figure amount to set the king checkmate.
	 */
	@Inject
	public Controller(final IFigureMoveHandler figureMoveHandler, final IFigureHolder figureHolder,
			final ICheckChecker checkChecker, final IGame game,
			Provider<IGameField> gameFieldProvider, final Set<IMinFigureCheck> minFigureChecks) {
		this.figureMoveHandler = figureMoveHandler;
		this.figureHolder = figureHolder;
		this.checkChecker = checkChecker;
		this.game = game;
		this.gameFieldProvider = gameFieldProvider;
		this.minFigureChecks = minFigureChecks;
		restartGame(true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restartGame() {
		restartGame(false);
		game.notifyObservers();
	}
	
	/**
	 * Reset the game.
	 * 
	 * @param ignoreGameState
	 *            If true the actual game state has no influence if you can reset the game field.
	 */
	private void restartGame(final boolean ignoreGameState) {
		if (!ignoreGameState && !game.getGameState().isEndState()) {
			throw new IllegalStateException("The game is not finish now.");
		}
		while (game.sizeOfLastConstellations() > 0) {
			game.removeOldestConstellation();
		}
		game.setGameField(gameFieldProvider.get());
		game.setGameState(GameState.TURN_OF_PLAYER_1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveFigure(final Move move) {
		final GameState gameState = game.getGameState();
		if (gameState != GameState.TURN_OF_PLAYER_1 && gameState != GameState.TURN_OF_PLAYER_2) {
			throw new IllegalStateException("You can not do this in the moment.");
		}
		
		final Point from = checkIfValidField(move.getFrom());
		final Point to = checkIfValidField(move.getTo());
		
		final IFigure figure = game.getGameField().getFigure(from);
		if (figure == null) {
			throw new IllegalArgumentException("There is no figure which you can move");
		}
		final Player player = figure.getPlayer();
		if (player.getTurnState() != gameState) {
			throw new IllegalArgumentException(
					"On the position \"from\" is the a figure from the wrong player.");
		}
		
		final IFigure figureOfGoalField = game.getGameField().getFigure(to);
		if (figureOfGoalField != null && figureOfGoalField.getPlayer() == player) {
			throw new IllegalArgumentException(
					"You can not move a figure to a field, where already is a figure of you.");
		}
		
		if (from.equals(to)) {
			throw new IllegalArgumentException(
					"You can not move the figure to the field where he already stand.");
		}
		
		final IMoveHandler moveHandler = figureMoveHandler.getHandler(figure.getType());
		if (!moveHandler.isItAValidMove(move)) {
			throw new IllegalArgumentException("You insert an invalid move.");
		}
		final MoveInPointFigurePairs translatedMove = moveHandler.translateMove(move);
		
		if (checkChecker.isKingInCheckForPlayer(player, translatedMove)) {
			throw new IllegalArgumentException(
					"You can not move the figure to this field, when your king is then in check.");
		}
		
		doMove(translatedMove);
		
		if (game.getGameState() != GameState.PAWN_CHOOSE_PLAYER_1
				&& game.getGameState() != GameState.PAWN_CHOOSE_PLAYER_2) {
			saveConstellation();
			checkForWinOrStalemate(player);
		}
		
		game.notifyObservers();
	}
	
	/**
	 * Do the given move and change the actual game field
	 * 
	 * @param translatedMove
	 *            The move which should be done
	 */
	private void doMove(final MoveInPointFigurePairs translatedMove) {
		final IGameField gameField = game.getGameField();
		for (final Entry<Point, IFigure> posFigure : translatedMove) {
			gameField.setFigure(posFigure.getKey(), posFigure.getValue());
		}
		if (translatedMove.isWasAFigureHit() || translatedMove.isWasAPawnMove()) {
			gameField.resetTurnsAfterHitOrMoveAPawn();
		} else {
			gameField.incrementTurnsAfterHitOrMoveAPawn();
		}
		game.setGameState(translatedMove.getNextGameState());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changePawnInto(final FigureType type) {
		final GameState gameState = game.getGameState();
		if (gameState != GameState.PAWN_CHOOSE_PLAYER_1
				&& gameState != GameState.PAWN_CHOOSE_PLAYER_2) {
			throw new IllegalStateException("You can not do this in the moment.");
		}
		
		if (!type.isPawnCanChangeIntoIt()) {
			throw new IllegalArgumentException("You can not turn your pawn in this figure");
		}
		
		final Point pawnPos = findThePawn();
		final IGameField gameField = game.getGameField();
		final IFigure pawn = gameField.getFigure(pawnPos);
		final Player player = pawn.getPlayer();
		gameField.setFigure(pawnPos, figureHolder.getFigure(type, player));
		game.setGameState(player == Player.PLAYER_1 ? GameState.TURN_OF_PLAYER_2
				: GameState.TURN_OF_PLAYER_1);
		
		saveConstellation();
		checkForWinOrStalemate(player);
		
		game.notifyObservers();
	}
	
	/**
	 * This method save the actual constellation and delete useless constellation which are to old
	 */
	private void saveConstellation() {
		game.addConstellation(game.getGameField().deepClone());
		while (game.sizeOfLastConstellations() > NEED_AMOUNT_OF_SAVE_FROM_LAST_CONSTELLATIONS) {
			game.removeOldestConstellation();
		}
	}
	
	/**
	 * This method check if the a player win the game or if the game goes in a stalemate. Furthermore set
	 * this method the game state of the game, when a player win or when a stalemate occurred.
	 * 
	 * @param player
	 *            The player which have done the last move.
	 */
	private void checkForWinOrStalemate(final Player player) {
		if (!checkIfPlayerHasATurn(player == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1)) {
			if (checkIfPlayerWin(player)) {
				game.setGameState(player.getWinState());
			} else {
				game.setGameState(GameState.STALEMATE_NO_TURN);
			}
		} else if (checkIf50TurnNoFigureRemovedOrPawnWasSet()) {
			game.setGameState(GameState.STALEMATE_50_TURNS);
		} else if (!checkIfPlayersHaveEnoughFigures()) {
			game.setGameState(GameState.STALEMATE_NOT_ENOUGH_FIGURE);
		} else if (checkIfPlayersHaveReachThreeTimesTheSameConstellation()) {
			game.setGameState(GameState.STALEMATE_THREE_TIMES_SAME_CONSTELLATION);
		}
	}
	
	/**
	 * Find the pawn which reach the other end of the game field
	 * 
	 * @return the position of the pawn
	 */
	private Point findThePawn() {
		for (int x = 0; x < IGameField.FIELD_SIZE; x++) {
			Point pos = Point.valueOf(x, 0);
			IFigure figure = game.getGameField().getFigure(pos);
			if (figure != null && figure.getType() == FigureType.PAWN) {
				return pos;
			}
			
			pos = Point.valueOf(x, IGameField.FIELD_SIZE - 1);
			figure = game.getGameField().getFigure(pos);
			if (figure != null && figure.getType() == FigureType.PAWN) {
				return pos;
			}
		}
		// Should not happen.
		return null;
	}
	
	/**
	 * Check if the players reach three times the same constellation on the field.
	 * 
	 * @return true if they reach it
	 */
	boolean checkIfPlayersHaveReachThreeTimesTheSameConstellation() {
		// For three times the same constellation. This are the last, the constellation for 5 turn
		// and for 9 turns must be equals
		if (game.sizeOfLastConstellations() < NEED_AMOUNT_OF_SAVE_FROM_LAST_CONSTELLATIONS) {
			return false;
		}
		IGameField lastConstellation = game.getOldConstellation(TURN_TO_BE_EQUALS[0]);
		for (int i = 1; i < TURN_TO_BE_EQUALS.length; i++) {
			final IGameField otherOldConstellation = game.getOldConstellation(TURN_TO_BE_EQUALS[i]);
			if (!lastConstellation.equals(otherOldConstellation)) {
				return false;
			}
			lastConstellation = otherOldConstellation;
		}
		return true;
	}
	
	/**
	 * Check if one of the both players have enough figure to set the other player checkmate
	 * 
	 * @return true if one of the players have enough figure to set the other checkmate
	 */
	private boolean checkIfPlayersHaveEnoughFigures() {
		final IGameField gameField = game.getGameField();
		
		final Map<Player, Map<FigureType, Integer>> allFigure = new HashMap<>(
				(int) (Player.values().length / 0.75f));
		for (final Player player : Player.values()) {
			allFigure.put(player, new EnumMap<>(FigureType.class));
		}
		
		for (int y = 0; y < IGameField.FIELD_SIZE; y++) {
			for (int x = 0; x < IGameField.FIELD_SIZE; x++) {
				final Point posToCheck = Point.valueOf(x, y);
				final IFigure figure = gameField.getFigure(posToCheck);
				if (figure == null) {
					continue;
				}
				final Map<FigureType, Integer> figureTypeAmount = allFigure.get(figure.getPlayer());
				figureTypeAmount.put(figure.getType(),
						figureTypeAmount.getOrDefault(figure.getType(), 0) + 1);
				for (final IMinFigureCheck check : minFigureChecks) {
					if (check.hasMinFigure(figureTypeAmount)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Check if the player has a figure which can make any move.
	 * 
	 * @param player
	 *            The player for which should be check.
	 * @return true if the given user has minimum one move which he can make,
	 */
	private boolean checkIfPlayerHasATurn(final Player player) {
		final IGameField gameField = game.getGameField();
		
		for (int y = 0; y < IGameField.FIELD_SIZE; y++) {
			for (int x = 0; x < IGameField.FIELD_SIZE; x++) {
				final Point posToCheck = Point.valueOf(x, y);
				final IFigure figure = gameField.getFigure(posToCheck);
				if (figure == null || figure.getPlayer() != player) {
					continue;
				}
				final IMoveHandler moveHandler = figureMoveHandler.getHandler(figure.getType());
				if (moveHandler.hasFigureATurnFor(posToCheck)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if for 50 turns long no pawn was set and no figure was hit
	 * 
	 * @return true if over 50 turns no pawn are set
	 */
	private boolean checkIf50TurnNoFigureRemovedOrPawnWasSet() {
		return game.getGameField().getTurnsAfterHitOrMoveAPawn() >= NUMBER_OF_TURN_WHEN_IS_STALEMATE;
	}
	
	/**
	 * This method only check, if the other king is in check
	 *
	 * @param player
	 *            for which player should be check if he win the game
	 * @return true if the other king is in check
	 */
	private boolean checkIfPlayerWin(final Player player) {
		return checkChecker.isKingInCheckForPlayer(
				player == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1);
	}
	
	/**
	 * Check if the given position a position on the gameField. If not it throws a
	 * IllegalArgumentException
	 * 
	 * @param pos
	 *            The position to check
	 * @return The position to check
	 */
	private static Point checkIfValidField(final Point pos) {
		if (pos.getX() < 0 || pos.getX() >= IGameField.FIELD_SIZE) {
			throw new IllegalArgumentException("A given position is invalid.");
		}
		if (pos.getY() < 0 || pos.getY() >= IGameField.FIELD_SIZE) {
			throw new IllegalArgumentException("A given position is invalid.");
		}
		return pos;
	}
}
