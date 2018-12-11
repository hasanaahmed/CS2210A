/**
 * 
 * @author hasanahmed
 * This class implements an edge of the graph
 *
 */
public class GraphEdge {
	//Attributes
		private GraphNode start, end;		
		private char busLine;		
		
		/**
		 * 
		 * The constructor for the class
		 * @param u
		 * @param v
		 * @param busLine
		 */
		public GraphEdge(GraphNode u, GraphNode v, char busLine) {
			start = u;			
			end = v;
			this.busLine = busLine;
		}
		
		/* Methods */
		
		/**
		 * 
		 * Returns the first endpoint of the edge.
		 * @return
		 */
		public GraphNode firstEndpoint() {
			return start;
		}
		
		/**
		 * 
		 * Returns the second endpoint of the edge.
		 * @return
		 */
		public GraphNode secondEndpoint() {
			return end;
		}
		
		/**
		 * 
		 * Returns the name of the busLine to which the edge belongs.
		 * @return
		 */
		public char getBusLine() {
			return busLine;
		}

}
