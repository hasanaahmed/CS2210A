/**
 * 
 * @author hasanahmed
 * This class represents the city map with bus lines
 * 
 */

// import necessary 
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BusLines {
	
	//initialize
	private Graph BusLines = null;
	private int a, b, c, beginning, destination;
	private char curLine, prevLine;
	Stack<GraphNode> thePath = new Stack<GraphNode>();

	/**
	 * 
	 * Constructor for building a city map with its bus lines from
	 * the input file
	 * @param inputFile
	 * @throws MapException
	 * @throws GraphException
	 */
	public BusLines(String inputFile) throws MapException, GraphException {
		int nd = 0;
		String line;
		char holder;

		try {
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			StringTokenizer st = new StringTokenizer(input.readLine());

			Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			BusLines = new Graph(a*b);

			for(int i=0; i<(2*b)-1; i++) {
				line = input.readLine();
				for(int j=0; j<(2*a)-1; j++) {
					holder = line.charAt(j);
					if(holder == 'S') {
						beginning = nd;
						nd++;
					}
					else if(holder == 'D') {
						destination = nd;
						nd ++;
					}
					else if(holder == '.') {
						nd++;
					}
					else {
						if((i % 2)== 0) {
							BusLines.insertEdge(BusLines.getNode(nd-1), BusLines.getNode(nd), holder);
						}
						else if((j%2) == 0){
							BusLines.insertEdge(BusLines.getNode(nd-a+((j+1)/2)), BusLines.getNode(nd+((j+1)/2)), holder);
						}

					}
				}
			}
			input.close();

		}
		catch (IOException e){
			throw new MapException("Error: input file does not exist");
		}


	}

	/**
	 * 
	 * Returns the graph representing the map
	 * @return
	 * @throws MapException
	 */
	Graph getGraph() throws MapException {
		if (BusLines == null) {
			throw new MapException("Error: cannot retrieve the map");
		}
		else {
			return BusLines;
		}
	}

	/**
	 * 
	 * helper method
	 * @param current
	 * @param destination
	 * @param busChange
	 * @return
	 * @throws GraphException
	 */
	private Iterator<GraphNode> tripHelper(GraphNode current, GraphNode destination, int busChange) throws GraphException {

		Iterator<GraphNode> trip = thePath.iterator();
		Iterator<GraphEdge> incidents = BusLines.incidentEdges(current);

		current.setMark(true);
		if(thePath.isEmpty() || thePath.peek() != current) {
			thePath.push(current);
		}
		prevLine = curLine;

		if(current.getName() == destination.getName()) {
			trip = thePath.iterator();
			return trip;
		}

		while(incidents.hasNext()) {
			GraphNode next = incidents.next().secondEndpoint();
			curLine = BusLines.getEdge(current, next).getBusLine();

			if(prevLine == curLine && next.getMark() == false) {
				return tripHelper(next, destination, busChange);
			}
			else if((busChange+1)<c && next.getMark() == false) {
				busChange++;
				return tripHelper(next,destination,busChange);
			}
		}

		thePath.pop();
		curLine = prevLine;
		return tripHelper(thePath.peek(), destination, busChange);


	}

	/**
	 * 
	 * Returns a Java Iterator containing the nodes along the path
	 * from the starting point to the destination, if such a path exists
	 * @return
	 * @throws GraphException
	 */
    public Iterator<GraphNode> trip() throws GraphException{
    	curLine = BusLines.getEdge(BusLines.getNode(beginning), BusLines.getNode(beginning+1)).getBusLine();
        Iterator<GraphNode> route=tripHelper(BusLines.getNode(beginning), BusLines.getNode(destination),0);
        return route;
    }
}
