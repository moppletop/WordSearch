package me.moppletop.wordsearch.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.moppletop.wordsearch.Direction;
import me.moppletop.wordsearch.Location;
import me.moppletop.wordsearch.WordSearch;
import me.moppletop.wordsearch.util.Alphabet;
import me.moppletop.wordsearch.util.GridFormat;

public class WordSearchCompiler extends GridFormat
{

	/**
	 * A string containing the entire alphabet as specified by {@link Alphabet#getAlphabet()}.
	 */
	private static final String FULL_ALPHABET = Alphabet.getAlphabet();

	/**
	 * A string containing the common letters of the alphabet as specified by {@link Alphabet#getAlphabet()}.
	 */
	private static final String COMMON_ALPHABET = Alphabet.getAlphabet("QXZ");

	private static final int MAX_ATTEMPTS = 100;

	private final Random _random;

	private final Map<String, List<Location>> _words;
	private final String[][] _characters;

	private boolean _fillLetters;
	private boolean _onlyCommonLetters;

	public WordSearchCompiler(int xLength, int yLength, List<String> words)
	{
		super(xLength, yLength);

		_random = new Random();
		_words = new HashMap<>(words.size());
		words.forEach(word -> _words.put(word, new ArrayList<>(word.length())));
		_characters = new String[xLength][yLength];
	}

	public WordSearchCompiler setFillLetters(boolean fillLetters)
	{
		_fillLetters = fillLetters;
		return this;
	}

	public WordSearchCompiler setOnlyCommonLetters(boolean onlyCommonLetters)
	{
		_onlyCommonLetters = onlyCommonLetters;
		return this;
	}

	public WordSearch compile()
	{
		for (String word : _words.keySet())
		{
			int attempts = 0;
			System.out.println("--- Word: " + word + " ---");

			while (!fillWord(word))
			{
				System.out.println("--- Attempt: " + attempts + " ---");

				if (++attempts == MAX_ATTEMPTS)
				{
					System.exit(1);
					return null;
				}
			}

			System.out.println();
		}

		if (_fillLetters)
		{
			String alphabet = _onlyCommonLetters ? COMMON_ALPHABET : FULL_ALPHABET;
			int alphabetLength = alphabet.length();

			for (String[] row : _characters)
			{
				for (int j = 0; j < row.length; j++)
				{
					String column = row[j];

					if (column == null)
					{
						row[j] = String.valueOf(alphabet.charAt(_random.nextInt(alphabetLength)));
					}
				}
			}
		}

		return new WordSearch(getXLength(), getYLength(), _words, _characters);
	}

	private boolean fillWord(String word)
	{
		List<Location> wordLocations = new ArrayList<>(word.length());
		int startRow = _random.nextInt(_characters.length);
		int startColumn = _random.nextInt(_characters[startRow].length);

		System.out.println("startRow: " + startRow + " & startColumn: " + startColumn);

		String current = _characters[startRow][startColumn];

		if (current != null)
		{
			System.out.println("Another character found: " + current);
			return false;
		}

		char[] wordArray = word.toCharArray();
		Location location = new Location(startRow, startColumn);
		Direction direction = Direction.random(_random);
		System.out.println("Direction: " + direction.toString());

		wordLocations.add(location);

		for (char character : wordArray)
		{
			System.out.println("Character: " + character);
			Location nextLocation = location.getRelative(direction);
			String stringCharacter = String.valueOf(character);

			if (!isOnGrid(nextLocation) || !isEmptyOrEqual(nextLocation, stringCharacter))
			{
				System.out.println("Failed!");
				return false;
			}

			wordLocations.add(nextLocation);
			location = nextLocation;
		}

		location = location.getRelative(direction);

		if (isOnGrid(location) && !isEmptyOrEqual(location, word))
		{
			System.out.println("Next character is the start of another word");
			return false;
		}

		wordLocations.remove(wordLocations.size() - 1);

		for (int i = 0; i < wordArray.length; i++)
		{
			String character = String.valueOf(wordArray[i]);
			Location wordLocation = wordLocations.get(i);

			_words.put(word, wordLocations);
			_characters[wordLocation.getX()][wordLocation.getY()] = character;
		}

		return true;
	}

	private boolean isOnGrid(Location location)
	{
		int x = location.getX();
		int y = location.getY();

		if (x < 0 || x >= _characters.length)
		{
			System.out.println("Off the X scale");
			return false;
		}

		String[] column = _characters[location.getX()];

		if (y < 0 || y >= column.length)
		{
			System.out.println("Off the Y scale");
			return false;
		}

		return true;
	}

	private boolean isEmptyOrEqual(Location location, String character)
	{
		String current = _characters[location.getX()][location.getY()];

		if (current != null && !current.equals(character))
		{
			System.out.println("Another non-equal character found");
			return false;
		}

		return true;
	}
}
