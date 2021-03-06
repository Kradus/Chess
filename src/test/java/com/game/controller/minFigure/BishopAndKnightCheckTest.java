package com.game.controller.minFigure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.game.model.figure.FigureType;

/**
 * This JUnit test is for the testing of the BishopAndKnightCheck class.
 * 
 * @author Bjoern Hullmann
 */
public class BishopAndKnightCheckTest {
	
	/**
	 * Condition which is tested
	 */
	private final BishopAndKnightCheck checker = new BishopAndKnightCheck();
	
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
	public void onlyBishopFigure() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.BISHOP, 10);
		Assert.assertFalse(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test only with one knight.
	 */
	@Test
	public void onlyKnightFigure() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.KNIGHT, 10);
		Assert.assertFalse(checker.hasMinFigure(figure));
	}
	
	/**
	 * Test with enough figure.
	 */
	@Test
	public void haveBothFigure() {
		final Map<FigureType, Integer> figure = new HashMap<>();
		figure.put(FigureType.BISHOP, 1);
		figure.put(FigureType.KNIGHT, 1);
		Assert.assertTrue(checker.hasMinFigure(figure));
	}
}
