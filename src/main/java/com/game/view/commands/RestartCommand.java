package com.game.view.commands;

import javax.inject.Inject;
import javax.inject.Named;

import com.game.controller.IController;

/**
 * The command for the game reset.
 * 
 * @author Bjoern Hullmann
 */
@Named
class RestartCommand implements ICommand {
	
	/**
	 * The controller of the game which is needed for the execution from the action of this command.
	 */
	private final IController controller;
	
	/**
	 * Create a command for the reseting of the game.
	 * 
	 * @param controller
	 *            The controller of the game. On this controller this command will do his action.
	 */
	@Inject
	RestartCommand(final IController controller) {
		this.controller = controller;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommand() {
		return "reset";
	}
	
	/**
	 * It restart the game.
	 * 
	 * @param params
	 *            the parameters, which the player enters. It is not use here.
	 */
	@Override
	public void doAction(final String[] params) {
		controller.restartGame();
	}
}
