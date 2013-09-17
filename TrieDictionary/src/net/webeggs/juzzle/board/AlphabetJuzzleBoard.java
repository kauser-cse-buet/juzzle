package net.webeggs.juzzle.board;

import static net.webeggs.juzzle.Alphabet.LOWERCASE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.webeggs.juzzle.vocabulary.LowercaseTrieVocabulary;

public class AlphabetJuzzleBoard extends AbstractJuzzleBoard<LowercaseTrieVocabulary>{

	byte[][] alphabetBoard;

	/**
	 * Creates a default 4x4 board
	 * 
	 * @param letters
	 */
	public AlphabetJuzzleBoard(char[] letters) {
		this(4, 4, letters);
	}

	public AlphabetJuzzleBoard(int r, int c, char[] letters) {
		super(r, c, letters);
		alphabetBoard = new byte[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				int k = i * c + j;
				alphabetBoard[i][j] = LOWERCASE.getIndex(letters[k]);
			}
		}
	}

	@Override
	protected int recurse(LowercaseTrieVocabulary vocabulary,
			LinkedList<JuzzleBoardCell> usedCells, ArrayList<List<JuzzleBoardCell>> words) {
		int r = 1;
		byte[] word = getInt(usedCells);
		if (vocabulary.contains(word, 0)) {
			// System.out.println(word);
			words.add(new ArrayList<>(usedCells));
		}
		if (vocabulary.isPrefix(word, 0)) {
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

	
	
	
	
	protected byte[] getInt(List<JuzzleBoardCell> list) {
		byte[] r = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			JuzzleBoardCell cell = list.get(i);
			r[i] = alphabetBoard[cell.row][cell.col];
		}
		return r;
	}

	@Override
	protected char getCharAt(int row, int col) {
		return LOWERCASE.alphabet.charAt(alphabetBoard[row][col]);
	}
}
