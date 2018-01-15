package me.moppletop.wordsearch;

import java.util.Random;

public enum Direction
{

	UP(-1, 0),
	DOWN(1, 0),
	LEFT(0, -1),
	RIGHT(0, 1),
	UP_LEFT(UP._xMod, LEFT._yMod),
	UP_RIGHT(UP._xMod, UP._yMod),
	DOWN_LEFT(DOWN._xMod, LEFT._yMod),
	DOWN_RIGHT(DOWN._xMod, RIGHT._yMod);

	public static Direction random(Random random)
	{
		return values()[random.nextInt(values().length)];
	}

	private final int _xMod, _yMod;

	Direction(int xMod, int yMod)
	{
		_xMod = xMod;
		_yMod = yMod;
	}

	public Location getRelative(int x, int y)
	{
		return new Location(x + _xMod, y + _yMod);
	}
}
