package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import view.commands.ICommand;

/**
 * This class is for the commands, which a user can use. It use for that the input stream from the
 * system. It parse the input from the user and do then the command which the user insert.
 * 
 * @author Bjoern Hullmann
 */
class CommandReader implements ICommandReader {
	
	/**
	 * All commands that are allow from the users.
	 */
	private final Map<String, ICommand> commands = new HashMap<>();
	
	/**
	 * Create a command reader.
	 * 
	 * @param commands
	 *            All commands which should be allow.
	 */
	@Inject
	CommandReader(final Set<ICommand> commands) {
		commands.forEach(command -> this.commands.put(command.getCommand(), command));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				try {
					parseInput(line);
				} catch (final IllegalArgumentException | IllegalStateException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		System.out.println("Games crashed.");
	}
	
	/**
	 * Parse the input from the player and transfer it to command, which the player enter
	 * 
	 * @param line
	 *            the command which the player enter
	 */
	private void parseInput(String line) {
		line = line.trim();
		if (line.isEmpty()) {
			return;
		}
		final String[] elements = line.split(" ", 2);
		final String[] params = elements.length < 2 ? new String[0] : elements[1].split(" ");
		
		final ICommand command = commands.get(elements[0]);
		if (command == null) {
			System.out.println(
					"You insert an invalid command. Use the \"help\" to find out which commands you can use.");
			return;
		}
		
		command.doAction(params);
	}
}
