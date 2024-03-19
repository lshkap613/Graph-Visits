import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class adjacenciesTest {
    private static WeightedGraph<Integer> wg;

	@BeforeEach
	void setup() {
		wg = new WeightedGraph<Integer>(5);
		wg.addVertex(1);
		wg.addVertex(2);
		wg.addVertex(3);
		wg.addVertex(4);
		wg.addVertex(5);
		
		wg.addEdge(1, 2, 1);
		wg.addEdge(1, 3, 1);
		wg.addEdge(1, 5, 1);
	}
	
	
	@Test
	void testGoFromVertex() {
		
		Queue<Integer> adjs = wg.goFromVertex(1);
				
		Queue<Integer> expectedAdjs = new LinkedList<Integer>();
		expectedAdjs.add(2);
		expectedAdjs.add(3);
		expectedAdjs.add(5);
		Assertions.assertIterableEquals(adjs, expectedAdjs);		
	}
	
	@Test
	void testGoFromVertex2() {
		Queue<Integer> adjs = wg.goFromVertex(2);
		
		Assertions.assertEquals(adjs.size(), 0);		
	}
	
	
	

}
