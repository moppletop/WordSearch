package me.moppletop.wordsearch;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import me.moppletop.wordsearch.compiler.WordSearchCompiler;
import me.moppletop.wordsearch.compiler.ui.WordSearchCompilerUI;

public class Main
{

	public static void main(String[] args)
	{
		File inputFile = new File("input.dat");

		try
		{
			if (!inputFile.createNewFile())
			{
				List<String> inputFileContents = Files.readAllLines(inputFile.toPath());

				inputFileContents.removeIf(s -> s.startsWith("//"));

				if (!inputFileContents.isEmpty())
				{
					WordSearch data = new WordSearchCompiler(14, 14, inputFileContents)
							.setFillLetters(true)
							.setOnlyCommonLetters(true)
							.compile();

					EventQueue.invokeLater(() ->
					{
						new WordSearchCompilerUI("Word Search Compiler", data)
								.createUI()
								.setVisible(true);
					});
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
