package me.moppletop.wordsearch.ui;

import javax.swing.*;

import java.awt.*;

import me.moppletop.wordsearch.WordSearch;

public abstract class WordSearchUI extends JFrame
{

	protected final WordSearch _wordSearch;

	public WordSearchUI(String title, WordSearch wordSearch) throws HeadlessException
	{
		super(title);

		_wordSearch = wordSearch;
	}

	public abstract WordSearchUI createUI();

	public WordSearch getWordSearch()
	{
		return _wordSearch;
	}
}
