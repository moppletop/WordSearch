package me.moppletop.wordsearch;

public class Location
{

	private final int _x, _y;

	public Location(int x, int y)
	{
		_x = x;
		_y = y;
	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

	public Location getRelative(Direction direction)
	{
		return direction.getRelative(_x, _y);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Location)
		{
			Location location = (Location) obj;

			if (location._x == _x && location._y == _y)
			{
				return true;
			}
		}

		return false;
	}
}
