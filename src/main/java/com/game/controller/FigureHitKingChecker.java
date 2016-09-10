package com.game.controller;

import java.util.EnumMap;
import java.util.Set;

import javax.inject.Inject;

import com.game.controller.hitKing.IHitKingChecker;
import com.game.model.figure.FigureType;

/**
 * This class hold all the HitKingChecker for each figureType.
 * 
 * @author Bjoern Hullmann
 */
class FigureHitKingChecker implements IFigureHitKingChecker {
	
	/**
	 * All HitKingChecker which this object hold.
	 */
	private final EnumMap<FigureType, IHitKingChecker> figureToHitKingChecker = new EnumMap<>(
			FigureType.class);
			
	/**
	 * Create a FigureHitKingChecker with all the given HitKingChecker
	 * 
	 * @param hitKingCheckers
	 *            All HitKingChecker which this object should hold.
	 */
	@Inject
	FigureHitKingChecker(final Set<IHitKingChecker> hitKingCheckers) {
		hitKingCheckers.forEach(hitKingChecker -> figureToHitKingChecker
				.put(hitKingChecker.getFigureType(), hitKingChecker));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IHitKingChecker getChecker(final FigureType type) {
		return figureToHitKingChecker.get(type);
	}
}
