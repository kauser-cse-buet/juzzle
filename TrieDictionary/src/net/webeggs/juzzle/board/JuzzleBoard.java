package net.webeggs.juzzle.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.webeggs.juzzle.Vocabulary;

public class JuzzleBoard extends AbstractJuzzleBoard<Vocabulary>{

	char[][] board;

	/**
	 * Creates a default 4x4 board
	 * 
	 * @param letters
	 */
	public JuzzleBoard(char[] letters) {
		this(4, 4, letters);
	}

	public JuzzleBoard(int r, int c, char[] letters) {
		super(r, c, letters);
		board = new char[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				int k = i * c + j;
				board[i][j] = letters[k];
			}
		}
	}

	@Override
	protected int recurse(Vocabulary vocabulary,
			LinkedList<JuzzleBoardCell> usedCells, ArrayList<List<JuzzleBoardCell>> words) {
		int r = 1;
		String word = getWord(usedCells);
		if (vocabulary.contains(word)) {
			// System.out.println(word);
			words.add(new ArrayList<>(usedCells));
		}
		if (vocabulary.isPrefix(word)) {
			// Search for moves
			List<JuzzleBoardCell> moves = getPossibleMoves(usedCells);
			for (JuzzleBoardCell move : moves) {
				usedCells.add(move);
				r += recurse(vocabulary, usedCells, words);
				usedCells.pollLast();
			}
		}
		return r;
	}

	@Override
	protected char getCharAt(int row, int col) {
		return board[row][col];
	}
}
