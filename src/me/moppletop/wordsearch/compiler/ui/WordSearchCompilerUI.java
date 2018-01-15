package me.moppletop.wordsearch.compiler.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import me.moppletop.wordsearch.Location;
import me.moppletop.wordsearch.WordSearch;
import me.moppletop.wordsearch.ui.WordSearchUI;
import me.moppletop.wordsearch.util.TableHelper;

public class WordSearchCompilerUI extends WordSearchUI
{

	private static final int WORDS_PER_ROW = 5;
	private static final Color FOUND_COLOUR = new Color(102, 255, 153);
	private static final Color SELECTED_COLOUR = new Color(77, 184, 255);

	private final Random _random;
	private final JPanel _contentPane;
	private final List<Location> _selectedCells;
	private final List<Location> _foundCells;
	private final List<String> _foundWords;

	private JTable _gridTable;
	private JTable _wordTable;

	public WordSearchCompilerUI(String title, WordSearch wordSearch) throws HeadlessException
	{
		super(title, wordSearch);

		_random = new Random();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 80 * wordSearch.getXLength() + 50, 45 * wordSearch.getYLength());
		_contentPane = new JPanel();
		_contentPane.setBackground(Color.LIGHT_GRAY);
		_contentPane.setBorder(new LineBorder(Color.BLACK, 3, true));
		setContentPane(_contentPane);

		_selectedCells = new ArrayList<>();
		_foundCells = new ArrayList<>();
		_foundWords = new ArrayList<>(wordSearch.getWords().size());
	}

	@Override
	public WordSearchUI createUI()
	{
		TableCellEditor editor = TableHelper.getStaticCellEditor();

		{
			String[] columnNames = new String[_wordSearch.getXLength()];
			Arrays.fill(columnNames, "");

			JTable table = new JTable(_wordSearch.getGrid(), columnNames)
			{
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
				{
					Component component = super.prepareRenderer(renderer, row, column);
					Location location = new Location(row, column);

					if (_foundCells.contains(location))
					{
						component.setBackground(FOUND_COLOUR);
					}
					else if (_selectedCells.contains(location))
					{
						component.setBackground(SELECTED_COLOUR);
					}
					else
					{
						component.setBackground(Color.WHITE);
					}

					return component;
				}
			};
			table.setRowHeight(40);
			table.setFont(TableHelper.getStandardTableFont(20));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setRowSelectionAllowed(false);
			table.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e)
				{
					handleClick(table.getSelectedRow(), table.getSelectedColumn());
					table.repaint();
				}
			});

			TableColumnModel model = table.getColumnModel();
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);

			for (int i = 0; i < table.getColumnCount(); i++)
			{
				TableColumn column = model.getColumn(i);

				column.setPreferredWidth(40);
				column.setCellEditor(editor);
				column.setCellRenderer(renderer);
			}

			_gridTable = table;
			_contentPane.add(table);
		}
		{
			List<String> words = new ArrayList<>(_wordSearch.getWords().keySet());
			words.sort(String::compareToIgnoreCase);
			String[][] wordRows = new String[(int) Math.ceil(words.size() / WORDS_PER_ROW) + 1][WORDS_PER_ROW];

			int i = 0;
			int j = 0;

			for (String word : words)
			{
				wordRows[i][j] = word;

				if (++j == WORDS_PER_ROW)
				{
					i++;
					j = 0;
				}
			}

			String[] columnNames = new String[WORDS_PER_ROW];
			Arrays.fill(columnNames, "");

			JTable table = new JTable(wordRows, columnNames)
			{
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
				{
					Component component = super.prepareRenderer(renderer, row, column);
					Object value = getModel().getValueAt(row, column);

					if (_foundWords.contains(value))
					{
						component.setBackground(FOUND_COLOUR);
					}
					else
					{
						component.setBackground(Color.WHITE);
					}

					return component;
				}
			};
			table.setRowHeight(20);
			table.setFont(TableHelper.getStandardTableFont(14));
			table.setColumnSelectionAllowed(false);
			table.setRowSelectionAllowed(false);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			TableColumnModel model = table.getColumnModel();

			for (int k = 0; k < table.getColumnCount(); k++)
			{
				TableColumn column = model.getColumn(k);

				column.setPreferredWidth(100);
				column.setCellEditor(editor);
			}

			_wordTable = table;
			_contentPane.add(table);
		}
		{
			JButton button = new JButton("Hint");

			button.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e)
				{
					for (Entry<String, List<Location>> entry : _wordSearch.getWords().entrySet())
					{
						String word = entry.getKey();
						List<Location> locations = entry.getValue();

						if (_foundWords.contains(word))
						{
							continue;
						}

						_selectedCells.add(locations.get(_random.nextInt(locations.size())));
						_gridTable.repaint();
						return;
					}
				}
			});

			_contentPane.add(button);
		}

		return this;
	}

	private void handleClick(int x, int y)
	{
		Location location = new Location(x, y);

		if (!_selectedCells.remove(location))
		{
			_selectedCells.add(location);
		}

		_wordSearch.getWords().forEach((word, locations) ->
		{
			if (_selectedCells.size() != locations.size())
			{
				return;
			}

			for (Location cell : locations)
			{
				if (!_selectedCells.contains(cell))
				{
					return;
				}
			}

			_foundWords.add(word);
			_foundCells.addAll(_selectedCells);
			_selectedCells.clear();
			_wordTable.repaint();
		});
	}
}
