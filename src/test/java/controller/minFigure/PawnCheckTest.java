package controller.minFigure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import model.figure.FigureType;

/**
 * This JUnit test is for the testing of the PawnCheck class.
 * 
 * @author Bjoern Hullmann
 */
public class PawnCheckTest {
	
	/**
	 * Condition which is tested
	 */
	private final PawnCheck checker = new PawnCheck();
	
	/**
	 * Test it with a empty Map.
	 */
	@Test
	public void emptyMap() {
		Assert.assertFalse(checker.hasMinFigure(Collections.emptyMap()));
	}
	
	/**
	 * Test it with a wrong figure.
	 */
	@Test
	public void wrongFigure() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.QUEEN, 10);
		Assert.assertFalse(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test with enough figure.
	 */
	@Test
	public void haveOnePawn() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.PAWN, 1);
		Assert.assertTrue(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test with enough figure.
	 */
	@Test
	public void haveMorePawn() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.PAWN, 10);
		Assert.assertTrue(checker.hasMinFigure(figure));
	}
}
