/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.system;

import name.martingeisse.labyrinth.game.Direction;

/**
 * Formula for +/- x movement: (x^2 / a^2) - (y^2 / b^2) = 1
 * <p>
 * a is also the minimum absolute x, that is, minimumDelta. b should be a defined multiple of a -- since a higher factor
 * gets neutralized by higher y, this factor is the "wideness" of the allowed area. We multiply both sides
 * by minimumDelta^2 and get:
 * <p>
 * x^2 - (y^2 / WIDENESS^2) = minimumDelta^2
 * <p>
 * or
 * <p>
 * x^2 = (y^2 / WIDENESS^2) + minimumDelta^2
 * <p>
 * Replace the equality by >= to allow greater absolute x values.
 */
public class TouchInputStrategy implements InputStrategy {

	private static final float WIDENESS = 0.8f;
	private static final float ONE_BY_WIDENESS_SQUARED = 1.0f / (WIDENESS * WIDENESS);

	private final TouchInputSource source;
	private final float minimumDelta;
	private final float minimumDeltaSquared;
	private final boolean positiveYIsDown;
	private boolean baseInitialized = false;
	private float baseX, baseY;

	public TouchInputStrategy(TouchInputSource source, float minimumDelta, boolean positiveYIsDown) {
		this.source = source;
		this.minimumDelta = minimumDelta;
		this.minimumDeltaSquared = minimumDelta * minimumDelta;
		this.positiveYIsDown = positiveYIsDown;
	}

	@Override
	public Direction getInputDirection() {
		if (!source.isTouching()) {
			baseInitialized = false;
			return null;
		}
		float x = source.getX();
		float y = source.getY();
		if (!baseInitialized) {
			baseX = x;
			baseY = y;
			baseInitialized = true;
		}
		float dx = x - baseX;
		float dy = y - baseY;
		if (checkHyperbola(dx, dy)) {
			return (dx > 0 ? Direction.EAST : Direction.WEST);
		}
		if (checkHyperbola(dy, dx)) {
			return (((dy > 0) ^ positiveYIsDown) ? Direction.NORTH : Direction.SOUTH);
		}
		return null;
	}

	private boolean checkHyperbola(float x, float y) {
		return (x * x) >= y * y * ONE_BY_WIDENESS_SQUARED + minimumDeltaSquared;
	}

}
