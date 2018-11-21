/**
 * 
 * @author hasanahmed Student ID: 250897473
 * this class represents the key attribute of a record in the ordered dictionary
 *
 */
public class Pair {
	//declare the variables 
	private String word = "";
	private String type;
	
	/**
	 * constructor method 
	 * @param word
	 * @param type
	 * a constructor which initializes a new Pair object with the specified word and type
	 */
	public Pair(String word, String type) {
		this.word = word;
		this.type = type;
	}
	
	/**
	 * 
	 * @return the word in key
	 * returns the word stored in this Pair object
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * 
	 * @return the type in key
	 * returns the type stored in this Pair object
	 */
	public String getType() {
		return type;
	}
	/**
	 * 
	 * @param key
	 * @return if keys are equal then 0, if this key is smaller return 1, otherwise -1
	 */
	public int compareTo(Pair k) {
		int strCompare = word.toLowerCase().compareTo(k.getWord().toLowerCase());
		if ((strCompare == 0) && (type.equals(k.getType()))) {
			return 0;
		}
		else if ((strCompare == 0) && (!type.equals(k.getType()))) {
			return -1;
		}
		else if (strCompare < 0) {
			return -1;
		}
		else {
			return 1;
		}
	}
}
