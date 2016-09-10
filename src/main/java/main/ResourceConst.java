package main;

/**
 * This class hold all constants, which are path for the resource folder.
 * 
 * @author Bjoern Hullmann
 */
public class ResourceConst {
	
	/**
	 * Don't create any instance from this.
	 */
	private ResourceConst() {
		throw new AssertionError();
	}
	
	/**
	 * The folder from which this programm is running
	 */
	public static final String	USER_DIR					= System.getProperty("user.dir");
															
	/**
	 * The folder where all files are for the dependency injection.
	 */
	public static final String	FOLDER_WITH_INJECTION_FILES	= "resources/injection";
															
	/**
	 * The file where stand what the start constellation of the game field is.
	 */
	public static final String	START_CONSTELLATION			= "resources/startConstellation.xml";
															
	/**
	 * The template for the printing of the actual game.
	 */
	public static final String	GAME_FIELD_FOR_PRINTING		= "resources/gameField.txt";
															
	/**
	 * The text for the help command.
	 */
	public static final String	HELP_MESSAGE				= "resources/commands.txt";
}
