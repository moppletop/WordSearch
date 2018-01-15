package me.moppletop.wordsearch.util;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class TableHelper
{

	private static final TableCellEditor STATIC_CELL_EDITOR = new TableCellEditor()
	{
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
		{
			return null;
		}

		@Override
		public Object getCellEditorValue()
		{
			return null;
		}

		@Override
		public boolean isCellEditable(EventObject anEvent)
		{
			return false;
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent)
		{
			return false;
		}

		@Override
		public boolean stopCellEditing()
		{
			return false;
		}

		@Override
		public void cancelCellEditing()
		{

		}

		@Override
		public void addCellEditorListener(CellEditorListener l)
		{

		}

		@Override
		public void removeCellEditorListener(CellEditorListener l)
		{

		}
	};

	public static TableCellEditor getStaticCellEditor()
	{
		return STATIC_CELL_EDITOR;
	}

	public static Font getStandardTableFont(int size)
	{
		return new Font("Times New Roman", Font.PLAIN, size);
	}
}
