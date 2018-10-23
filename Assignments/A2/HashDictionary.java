/**
 * 
 * @author hasanahmed
 *
 */
public class HashDictionary implements DictionaryADT {

	/**
	 * 
	 */
	Node[] hashtable = null;
	int size = 0;
	
	/**
	 * 
	 * @param size
	 */
	public HashDictionary(int size) {
		this.size = size;
		hashtable = new Node[size];
	}

	/**
	 * 
	 * @param configStr
	 * @return
	 */
	private int hashFunction(String configStr) {
		configStr = configStr.replaceAll(" ", "");
		int key = 0;
		for (int i = 0; i < configStr.length(); i++) {
			key += (((int)configStr.charAt(i))% size);
		}
		
		key %= size;
		return key;
	}
	
	/**
	 * 
	 */
	public int put(Configuration pair) throws DictionaryException {
		if (this.getScore(pair.getStringConfiguration()) != -1) {
			throw new DictionaryException("This entry already exists in HashDictionary!");
		}
		
		int key = hashFunction(pair.getStringConfiguration());
		Node newNode = new Node(pair);
		
		if (hashtable[key] == null) {
			hashtable[key] = newNode;
		}
		else {
			Node currentNode = hashtable[key];
			while (currentNode.getNextNode() != null) {
				currentNode = currentNode.getNextNode();
			}
			currentNode.setNextNode(newNode);
		}
		return 0;
	}

	/**
	 * 
	 */
	public void remove(String config) throws DictionaryException {
		if (this.getScore(config) == -1) {
			throw new DictionaryException("Could not remove: was not found!");
		}
		
		int key = hashFunction(config);
		
		if (hashtable[key].getNodeEntry().getStringConfiguration().equals(config)) {
			hashtable[key] = hashtable[key].getNextNode();
		}
		else {
			Node currentNode = hashtable[key];
			while (true) {
				if (currentNode.getNextNode() == null) {
					break;
				}
				else if (currentNode.getNextNode().getNodeEntry().getStringConfiguration().equals(config) && currentNode.getNextNode().getNextNode() != null) {
					currentNode.setNextNode(currentNode.getNextNode().getNextNode());
				}
				else if (currentNode.getNextNode().getNodeEntry().getStringConfiguration().equals(config) && currentNode.getNextNode().getNextNode() == null) {
					currentNode.setNextNode(null);
				}
				currentNode = currentNode.getNextNode();
			}
		}
	}

	/**
	 * 
	 */
	public int getScore(String config) {
		int result = -1;
		int key = hashFunction(config);
		if (hashtable[key] == null) {
			return -1;
		}
		if (hashtable[key].getNodeEntry().getStringConfiguration().equals(config)) {
			return hashtable[key].getNodeEntry().getScore();
		}
		else {
			Node currentNode = hashtable[key];
			while (currentNode.getNextNode() != null) {
				currentNode = currentNode.getNextNode();
				if (currentNode.getNodeEntry().getStringConfiguration().equals(config)) {
					result = currentNode.getNodeEntry().getScore();
					break;
				}
			}
		}
		return result;
	}
}
