package com.game.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.game.main.InjectionLoader;
import com.game.main.ResourceConst;
import com.game.model.IGameField;
import com.game.model.Player;
import com.game.model.Point;
import com.game.model.figure.FigureType;
import com.game.model.figure.IFigureHolder;

/**
 * This factory create new game fields with the start constellation. For this it use a xml where the
 * position of each figure stand.
 * 
 * @author Bjoern Hullmann
 */
public class GameFieldFactory implements IGameFieldFactory {
	
	/**
	 * The figure node.
	 */
	private static final String	FIGURE	= "figure";
	/**
	 * The attribute for the type of the figure.
	 */
	private static final String	TYPE	= "type";
	/**
	 * The attribute and Node for the owner of the figure.
	 */
	private static final String	PLAYER	= "player";
	/**
	 * The attribute for the x position of the figure in the start constellation.
	 */
	private static final String	POS_Y	= "posY";
	/**
	 * The attribute for the y position of the figure in the start constellation.
	 */
	private static final String	POS_X	= "posX";
										
	/**
	 * The figure holder for getting all figure.
	 */
	private final IFigureHolder	figureHolder;
	/**
	 * The xml document for loading the start constellation.
	 */
	private final Document		doc;
								
	/**
	 * Create a game field factory for the start constellation. He read it out from a xml file.
	 * 
	 * @param figureHolder
	 *            The object where it can get the figure from.
	 */
	@Inject
	public GameFieldFactory(final IFigureHolder figureHolder) {
		this.figureHolder = figureHolder;
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setIgnoringComments(true);
			final DocumentBuilder docBuilder = factory.newDocumentBuilder();
			docBuilder.setErrorHandler(null);
			final Path path = FileSystems.getDefault()
					.getPath(ResourceConst.USER_DIR + "/" + ResourceConst.START_CONSTELLATION);
			doc = docBuilder.parse(Files.newInputStream(path, StandardOpenOption.READ));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// Should not happen
			throw new InternalError(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGameField createGameField() {
		final IGameField gameField = InjectionLoader.INSTANCE.getInstanceOf(IGameField.class);
		
		final NodeList players = doc.getElementsByTagName(PLAYER);
		for (int i = 0; i < players.getLength(); i++) {
			final Element element = (Element) players.item(i);
			
			final Player player = Player.valueOf(element.getAttribute(PLAYER).toUpperCase());
			
			final NodeList figure = element.getElementsByTagName(FIGURE);
			for (int j = 0; j < figure.getLength(); j++) {
				final Element child = (Element) figure.item(j);
				
				final int x = Integer.parseInt(child.getAttribute(POS_X));
				final int y = Integer.parseInt(child.getAttribute(POS_Y));
				final FigureType type = FigureType.valueOf(child.getAttribute(TYPE).toUpperCase());
				
				gameField.setFigure(Point.valueOf(x, y),
						figureHolder.getUnmovedFigure(type, player));
			}
		}
		return gameField;
	}
}
