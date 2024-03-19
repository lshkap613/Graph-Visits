import java.util.LinkedList;

public class Main2 {

	public static void main(String[] args) {
		Place flatbush = new Place("Flatbush");
		Place boroPark = new Place("Boro Park");
		Place passaic = new Place("Passiac, NJ");
		Place home = new Place("Malden, MA");
		Place twinRivers = new Place("Twin Rivers, NJ");
		Place naugatuck = new Place("Naugatuck, CT");
				
		WeightedGraph<Place> g = new WeightedGraph<Place>();
		
		g.addVertex(flatbush);
		g.addVertex(boroPark);
		g.addVertex(passaic);
		g.addVertex(home);
		g.addVertex(twinRivers);
		g.addVertex(naugatuck);

		g.addEdge(flatbush, boroPark, 5);
		//g.addEdge(flatbush, passaic, 1);
		g.addEdge(flatbush, home, 3);

		
		g.addEdge(boroPark, flatbush, 5);
		//g.addEdge(boroPark, twinRivers, 2);
		g.addEdge(boroPark, naugatuck, 4);

		
		g.addEdge(passaic, boroPark, 4);
		//g.addEdge(passaic,  naugatuck, 2);
		g.addEdge(passaic, home, 2);

		
		g.addEdge(home, naugatuck, 5);
		//g.addEdge(home, twinRivers, 3);
		g.addEdge(home, boroPark, 1);

		
		g.addEdge(twinRivers, home, 4);
		//g.addEdge(twinRivers, flatbush, 5);
		g.addEdge(twinRivers, passaic, 1);

		
		g.addEdge(naugatuck, home, 5);
		//g.addEdge(naugatuck, flatbush, 2);
		g.addEdge(naugatuck, passaic, 5);
		
		
		System.out.println("~~~~~~~~~~~~~~~ Adjacency table: ~~~~~~~~~~~~~~");
		g.printAdjacencyTable();
		
		System.out.println("\n~~~~~~~~~~~~~~~ Three paths: ~~~~~~~~~~~~~~~~~~");
		printPath(g, home, flatbush);
		printPath(g, passaic, boroPark);
		printPath(g, naugatuck, twinRivers);
		
	}
	
	public static void printPath(WeightedGraph<Place> g, Place start, Place end) {
		System.out.println("Start: " + start + ", End: " + end);
		
		if (g.breadthFirstSearch(start, end) == null) {
			System.out.println("No path between " + start + " and " + end);
		} else {
			LinkedList<Place> path = new LinkedList<Place>(g.breadthFirstSearch(start, end));	
			for (Place stop : path) {
				System.out.print(stop + " --> ");
			}
			System.out.println("\n");
		}
	}

}
