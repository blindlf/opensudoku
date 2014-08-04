/* 
 * Copyright (C) 2009 Roman Masek
 * 
 * This file is part of OpenSudoku.
 * 
 * OpenSudoku is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OpenSudoku is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OpenSudoku.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package cz.romario.opensudoku.game.command;

import android.os.Bundle;
import cz.romario.opensudoku.game.Cell;
import cz.romario.opensudoku.game.CellNote;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RemoveCellNoteCommand extends AbstractCellCommand {

    private int[] mCellsRowIndexes;
    private int[] mCellsColumnIndexes;
    private CellNote[] mNotes;
    private CellNote[] mOldNotes;

    public RemoveCellNoteCommand(ArrayList<Cell> cells, ArrayList<CellNote> notes) {
        mCellsRowIndexes = new int[cells.size()];
        mCellsColumnIndexes = new int[cells.size()];
        mNotes = new CellNote[notes.size()];

        for (int i=0; i<cells.size(); i++) {
            mCellsRowIndexes[i] = cells.get(i).getRowIndex();
            mCellsColumnIndexes[i] = cells.get(i).getColumnIndex();
            mNotes[i] = notes.get(i);
        }
    }

    RemoveCellNoteCommand() {

    }

    @Override
    void saveState(Bundle outState) {
        super.saveState(outState);

        //outState.putString("notes", mNotes.serialize());
        //outState.putString("oldNotes", mOldNotes.serialize());
    }

    @Override
    void restoreState(Bundle inState) {
        super.restoreState(inState);

        //mNote = CellNote.deserialize(inState.getString("notes"));
        //mOldNote = CellNote.deserialize(inState.getString("oldNotes"));
    }

    @Override
    void execute() {
        mOldNotes = new CellNote[mNotes.length];

        for (int i=0; i<mCellsRowIndexes.length; i++) {
            Cell cell = getCells().getCell(mCellsRowIndexes[i], mCellsColumnIndexes[i]);
            mOldNotes[i] = cell.getNote();
            cell.setNote(mNotes[i]);
        }
    }

    @Override
    void undo() {
        for (int i=0; i<mCellsRowIndexes.length; i++) {
            Cell cell = getCells().getCell(mCellsRowIndexes[i], mCellsColumnIndexes[i]);
            cell.setNote(mOldNotes[i]);
        }
    }
}
