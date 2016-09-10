package controller.minFigure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import model.figure.FigureType;

/**
 * This JUnit test is for the testing of the BishopCheck class.
 * 
 * @author Bjoern Hullmann
 */
public class BishopCheckTest {
	
	/**
	 * Condition which is tested
	 */
	private final BishopCheck checker = new BishopCheck();
	
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
	 * Test only with one bishop.
	 */
	@Test
	public void onlyOneBishopFigure() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.BISHOP, 1);
		Assert.assertFalse(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test with enough figure.
	 */
	@Test
	public void haveBothFigure() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.BISHOP, 2);
		Assert.assertTrue(checker.hasMinFigure(figure));
	}
}
