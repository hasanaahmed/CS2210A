/**
 * 
 * @author hasanahmed Student ID: 250897473
 * this class represents a record in the ordered dictionary
 *
 */
public class Record {
	
	//declare variables
	private String data ="";
	private Pair key;
	
	/**
	 * constructor method for record
	 * @param k
	 * @param data
	 * a constructor which initializes a new Record object with the specified key and data. If the type in the key is “audio” or “image”, then data stores the name of the corresponding audio or image file
	 */
	public Record(Pair key, String data ) {
		this.key = key;
		this.data = data;
	}
	
	/**
	 * 
	 * @return returns the key stored in this Record object
	 */
	public Pair getKey() {
		return key;
	}
	
	/**
	 * 
	 * @return returns the data stored in this Record object
	 */
	public String getData() {
		return data;
	}
}
