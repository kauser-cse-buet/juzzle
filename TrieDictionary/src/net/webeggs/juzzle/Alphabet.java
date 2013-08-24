package net.webeggs.juzzle;

public abstract class Alphabet {

	public final String alphabet;
	
	/**
	 * Basic alphabet class. Workd for alphabets up to 128 chars
	 */
	public final static Alphabet LOWERCASE = new Alphabet("abcdefghijklmonpqrstuvwxyz") {
		
		private final int charOffest = Character.getNumericValue('a');
		
		//@Override
		/**
		 * Faster way to retrieve the index
		 */
		public byte getIndex(char c) {
			return (byte) (Character.getNumericValue(c) - charOffest);
		}
	}; 
	
	
	public Alphabet(String alphabet) {
		this.alphabet = alphabet;
	}
	
	public byte[] toInt(String word) {
		byte[] r = new byte[word.length()];
		for (int i = 0; i < r.length; i++) {
			r[i] = getIndex(word.charAt(i));
		}
		return r;
	}

	
	public byte getIndex(char c) {
		return (byte) alphabet.indexOf(c);
	};


	public int size() {
		return alphabet.length();
	}
}
