
public class Node {
	private Configuration entry;
	private Node next;
	
	public Node(Configuration entry) {
		this.entry = entry;
		this.next = null;
	}
	
	public Configuration getNodeEntry() {
		return this.entry;
	}
	
	public void setNextNode(Node nextNode) {
		this.next = nextNode;
	}
	
	public Node getNextNode() {
		return this.next;
	}
}
