package net.webeggs.juzzle.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.webeggs.juzzle.Vocabulary;

public abstract class AbstractJuzzleBoard<T extends Vocabulary> {

	protected int rows;
	protected int cols;

	public final static class JuzzleBoardCell {
		int row;
		int col;

		public JuzzleBoardCell(int i, int j) {
			this.row = i;
			this.col = j;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof JuzzleBoardCell) {
				JuzzleBoardCell j = (JuzzleBoardCell) obj;
				return j.row == row && j.col == col;
			}
			return false;
		}

		@Override
		public int hashCode() {
			// works for up to 10 rows board
			return row * 10 + col;
		}
	}

	protected AbstractJuzzleBoard(int r, int c, char[] letters) {
		rows = r;
		cols = c;
		if (letters.length != r * c) {
			throw new IllegalArgumentException(
					"Wrong number of letters for board");
		}
	}

	public final int findWords(T vocabulary,
			ArrayList<List<JuzzleBoardCell>> words) {
		LinkedList<JuzzleBoardCell> usedCells = new LinkedList<>();
		int moves = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				JuzzleBoardCell cell = new JuzzleBoardCell(i, j);
				usedCells.add(cell);
				moves += recurse(vocabulary, usedCells, words);
				usedCells.pollLast();
			}
		}
		return moves;
	}

	protected abstract int recurse(T vocabulary,
			LinkedList<JuzzleBoardCell> usedCells,
			ArrayList<List<JuzzleBoardCell>> words);


	protected List<JuzzleBoardCell> getPossibleMoves(
			LinkedList<JuzzleBoardCell> usedCells) {

		// Possible moves are up/down/left/right/ur/ul/dr/dl from last cell
		LinkedList<JuzzleBoardCell> moves = new LinkedList<>();
		JuzzleBoardCell last = usedCells.peekLast();
		boolean u, d, r, l;
		u = last.row - 1 >= 0;
		d = last.row + 1 < rows;
		r = last.col + 1 < cols;
		l = last.col - 1 >= 0;
		if (u) { // up
			moves.add(new JuzzleBoardCell(last.row - 1, last.col));
		}
		if (d) { // down
			moves.add(new JuzzleBoardCell(last.row + 1, last.col));
		}

		if (r) { // right
			moves.add(new JuzzleBoardCell(last.row, last.col + 1));
		}
		if (l) { // left
			moves.add(new JuzzleBoardCell(last.row, last.col - 1));
		}
		if (u && l) { // up and left
			moves.add(new JuzzleBoardCell(last.row - 1, last.col - 1));
		}
		if (u && r) { // up and right
			moves.add(new JuzzleBoardCell(last.row - 1, last.col + 1));
		}
		if (d && l) { // down and left
			moves.add(new JuzzleBoardCell(last.row + 1, last.col - 1));
		}
		if (d && r) { // down and right
			moves.add(new JuzzleBoardCell(last.row + 1, last.col + 1));
		}
		// Don't go back
		moves.removeAll(usedCells);
		return moves;
	}

	protected abstract char getCharAt(int row, int col);

	protected String getWord(List<JuzzleBoardCell> usedCells) {
		StringBuffer sb = new StringBuffer(usedCells.size());
		for (JuzzleBoardCell cell : usedCells) {
			sb.append(getCharAt(cell.row, cell.col));
		}
		return sb.toString();
	}

}
