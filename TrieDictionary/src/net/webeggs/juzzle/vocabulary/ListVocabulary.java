package net.webeggs.juzzle.vocabulary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.webeggs.juzzle.Vocabulary;

/**
 * This class stores the words in a sorted list. Binary search is used to find the words
 * and the prefixes
 * @author Chiara
 *
 */
public class ListVocabulary implements Vocabulary {
	private List<String> words;

	public ListVocabulary() {
		// It's important to use a List that implements RandomAccess,
		// so that Collections.binarySearch() can work in O(logn) time
		words = new ArrayList<String>();
	}
	
	/**
	 * Constructor that adds alle the words and then sorts the underlying list
	 * @param words
	 */
	public ListVocabulary(Collection<String> words) {
		this();
		this.words.addAll(words);
		Collections.sort(this.words);
	}
	
	public boolean add(String word) {
		int pos = Collections.binarySearch(words, word);
		// pos > 0 means the word is already in the list. Insert only
		// if it's not there yet
		if (pos < 0) {
			words.add(-(pos+1), word);
			return true;
		}
		return false;
	}

	public boolean isPrefix(String prefix) {
		int pos = Collections.binarySearch(words, prefix) ;
		if (pos >= 0) {
			// The prefix is a word. Check the following word, because we are looking 
			// for words that are longer than the prefix
			if (pos +1 < words.size()) {
				String nextWord = words.get(pos+1);
				return nextWord.startsWith(prefix);
			}
			return false;
		}
		pos = -(pos+1);
		// The prefix is not a word. Check where it would be inserted and get the next word.
		// If it starts with prefix, return true.
		if (pos == words.size()) {
			return false;
		}
		String nextWord = words.get(pos);
		return nextWord.startsWith(prefix);
	}

	public boolean contains(String word) {
		int pos = Collections.binarySearch(words, word);
		return pos >= 0;
	}
	
	@Override
	public String getName() {
		return getClass().getName();
	}
}
