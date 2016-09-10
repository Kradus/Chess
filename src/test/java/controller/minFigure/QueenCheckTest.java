package controller.minFigure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import model.figure.FigureType;

/**
 * This JUnit test is for the testing of the QueenCheck class.
 * 
 * @author Bjoern Hullmann
 */
public class QueenCheckTest {
	
	/**
	 * Condition which is tested
	 */
	private final QueenCheck checker = new QueenCheck();
	
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
		figure.put(FigureType.ROOK, 10);
		Assert.assertFalse(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test with enough figure.
	 */
	@Test
	public void haveOneQueen() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.QUEEN, 1);
		Assert.assertTrue(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test with enough figure.
	 */
	@Test
	public void haveMoreQueen() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.QUEEN, 10);
		Assert.assertTrue(checker.hasMinFigure(figure));
	}
}
