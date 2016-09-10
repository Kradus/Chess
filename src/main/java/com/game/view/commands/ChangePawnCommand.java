package com.game.view.commands;

import javax.inject.Inject;
import javax.inject.Named;

import com.game.controller.IController;
import com.game.model.figure.FigureType;

/**
 * Command for changing a pawn in an other figure.
 * 
 * @author Bjoern Hullmann
 */
@Named
class ChangePawnCommand implements ICommand {
	
	/**
	 * The controller of the game which is needed for the execution from the action of this command.
	 */
	private final IController controller;
	
	/**
	 * Create a command for the changing of a pawn in an other figure.
	 * 
	 * @param controller
	 *            The controller of the game. On this controller this command will do his action.
	 */
	@Inject
	ChangePawnCommand(final IController controller) {
		this.controller = controller;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommand() {
		return "change";
	}
	
	/**
	 * Change a pawn into a other figure.
	 * 
	 * @param params
	 *            The first entry is the figure in which the pawn should turn.
	 */
	@Override
	public void doAction(final String[] params) {
		if (params.length < 1) {
			System.out.println("You must insert the figure type.");
			return;
		}
		
		FigureType type;
		try {
			type = FigureType.valueOf(params[0].toUpperCase());
		} catch (final IllegalArgumentException e) {
			System.out.println("You insert an invalid figure type.");
			return;
		}
		
		controller.changePawnInto(type);
	}
}
