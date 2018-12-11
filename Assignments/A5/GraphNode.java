/**
 * 
 * @author hasanahmed
 * This class implements a node of the graph
 *
 */
public class GraphNode {
	// initialize
	private int name; 
	private boolean mark; 
	
	/**
	 * 
	 * This is the constructor for the class and it creates an unmarked node
	 * @param name
	 */
	public GraphNode(int name) {
		this.name = name;
		mark = false;
	}

	/* Methods */

	/**
	 * 
	 * Marks the node with the specified value.
	 * @param mark
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}

	/**
	 * 
	 *  Returns the value with which the node has been marked
	 * @return
	 */
	public boolean getMark() {
		return mark;
	}

	/**
	 * 
	 * Returns the name of the node.
	 * @return
	 */
	public int getName() {
		return name;
	}
}
