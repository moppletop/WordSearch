package me.moppletop.wordsearch;

import java.util.List;
import java.util.Map;

import me.moppletop.wordsearch.util.GridFormat;

public class WordSearch extends GridFormat
{

	private final Map<String, List<Location>> _words;
	private final String[][] _grid;

	public WordSearch(int xLength, int yLength, Map<String, List<Location>> words, String[][] grid)
	{
		super(xLength, yLength);

		_words = words;
		_grid = grid;
	}

	public Map<String, List<Location>> getWords()
	{
		return _words;
	}

	public String[][] getGrid()
	{
		return _grid;
	}
}
