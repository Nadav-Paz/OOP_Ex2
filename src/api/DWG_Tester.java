package api;

import static org.junit.Assert.*;

import org.junit.Test;

public class DWG_Tester {

	@Test
	public void testA() {
		DWG G=new DWG();
		G.addNode(0);
		G.addNode(1);
		G.connect(0, 1, 5.0);
		boolean flag = (5 == G.getEdge(0, 1).getWeight());
		assertTrue(flag);
	}
	
	@Test
	public void testB() {
		DWG G=new DWG();
		G.addNode(0);
		G.addNode(1);
		G.connect(0, 1, 5.0);
		boolean flag = (null == G.getEdge(1, 0));
		assertTrue(flag);
	}

}
