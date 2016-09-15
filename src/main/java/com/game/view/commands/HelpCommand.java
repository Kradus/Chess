package com.game.view.commands;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Named;

import com.game.main.ResourceConst;

/**
 * The help command. It print a overview over all commands. This read it out from a extra file.
 * 
 * @author Bjoern Hullmann
 */
@Named
class HelpCommand implements ICommand {
	
	/**
	 * The help text which is printed when this command would be called.
	 */
	private final String helpText;
	
	/**
	 * Create a help command which read the help text out of a file.
	 */
	HelpCommand() {
		final Path path = FileSystems.getDefault()
				.getPath(ResourceConst.USER_DIR + "/" + ResourceConst.HELP_MESSAGE);
		try {
			final List<String> file = Files.readAllLines(path);
			final StringBuilder sb = new StringBuilder();
			file.forEach(line -> {
				sb.append(line).append("\n");
			});
			helpText = sb.toString();
		} catch (final IOException e) {
			throw new InternalError(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommand() {
		return "help";
	}
	
	/**
	 * Print all commands.
	 * 
	 * @param params
	 *            the parameters, which the player enters. It is not use here.
	 */
	@Override
	public void doAction(final String[] params) {
		System.out.println(helpText);
	}
}
