package me.moppletop.wordsearch.util;

/**
 * An utility class that provides static immutable methods to assist with manipulation of the Alphabet.
 */
public class Alphabet
{

	/**
	 * The alphabet as an immutable string.
	 */
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * @return the entire alphabet as a string in the form ABC..XYZ.
	 */
	public static String getAlphabet()
	{
		return ALPHABET;
	}

	/**
	 * Returns the alphabet as a string in the same form as {@link #getAlphabet()} though with any characters in
	 * the exclude string are removed from the returned string. For example
	 *
	 * <pre>
	 *     getAlphabet("ABC") == "DEF...XYZ"
	 * </pre>
	 *
	 * @param exclude a string containing all the characters you want removed from the alphabet, in the form
	 * @return A new string instance of the alphabet containing only characters that were not excluded.
	 */
	public static String getAlphabet(String exclude)
	{
		String alphabet = ALPHABET;

		for (char character : exclude.toCharArray())
		{
			alphabet = alphabet.replaceFirst(String.valueOf(character), "");
		}

		return alphabet;
	}
}
