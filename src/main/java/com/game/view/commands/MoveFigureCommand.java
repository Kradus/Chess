package com.game.view.commands;

import javax.inject.Inject;
import javax.inject.Named;

import com.game.controller.IController;
import com.game.controller.Move;
import com.game.model.IGameField;
import com.game.model.Point;

/**
 * The command to move a figure.
 * 
 * @author Bjoern Hullmann
 */
@Named
class MoveFigureCommand implements ICommand {
	
	/**
	 * The controller of the game which is needed for the execution from the action of this command.
	 */
	private final IController controller;
	
	/**
	 * Create a command for the moving of a figure.
	 * 
	 * @param controller
	 *            The controller of the game. On this controller this command will do his action.
	 */
	@Inject
	MoveFigureCommand(final IController controller) {
		this.controller = controller;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommand() {
		return "move";
	}
	
	/**
	 * Move a figure.
	 * 
	 * @param params
	 *            the first entry is the position from which the player want to move the figure and
	 *            the second is the goal.
	 */
	@Override
	public void doAction(final String[] params) {
		if (params.length < 2) {
			System.out.println(
					"You must insert two fields. The field with the figure and the goal field for this move.");
			return;
		}
		
		final Point from = parseField(params[0]);
		if (from == null) {
			System.out.println(
					"The \"from\" field is invalid. It must be a letter and a number. E.g.: a7.");
			return;
		}
		final Point to = parseField(params[1]);
		if (to == null) {
			System.out.println(
					"The \"to\" field is invalid. It must be a letter and a number. E.g.: a7.");
			return;
		}
		
		controller.moveFigure(new Move(from, to));
	}
	
	/**
	 * This method parse the give position from the field to the intern used coordinates from the
	 * field.
	 * 
	 * @param pos
	 *            The position on the game field.
	 * @return a Point with the intern coordinates of the field.
	 */
	private static Point parseField(String pos) {
		if (pos.length() != 2) {
			return null;
		}
		pos = pos.toLowerCase();
		// Transform the letter into a coordinate. E.g.: 'b' - 'a' = 1.
		final int x = pos.charAt(0) - 'a';
		// Transform the number into a coordinate. The numbers on the game field growns from bottom
		// to top. Intern the number increase from top to bottom.
		// E.g.: 8 - ('6' - '0') = 2.
		final int y = IGameField.FIELD_SIZE - (pos.charAt(1) - '0');
		
		if (y < 0 || y >= IGameField.FIELD_SIZE) {
			return null;
		}
		
		if (x < 0 || x >= IGameField.FIELD_SIZE) {
			return null;
		}
		
		return Point.valueOf(x, y);
	}
}
