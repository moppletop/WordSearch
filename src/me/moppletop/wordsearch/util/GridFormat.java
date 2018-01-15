package me.moppletop.wordsearch.util;

public class GridFormat
{

	private final int _xLength, _yLength;

	public GridFormat(int xLength, int yLength)
	{
		_xLength = xLength;
		_yLength = yLength;
	}

	public int getXLength()
	{
		return _xLength;
	}

	public int getYLength()
	{
		return _yLength;
	}
}
