package api;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class AlgoTest {

	@Test
	public void TestA() 
	{
		DWG G=new DWG();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	@Test
	public void TestB() 
	{
		DWG G=new DWG();
		G.addNode(0);
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	@Test
	public void TestC1() 
	{
		DWG G=new DWG();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		G.addNode(0);
		G.addNode(1);
		boolean flag=A.isConnected();
		assertFalse(flag);
	}
	@Test
	public void TestC2() 
	{
		DWG G=new DWG();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		G.addNode(0);
		G.addNode(1);
		G.connect(0,1,3);
		G.connect(1,0,2);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	public DWG GraphBulidTestD()
	{
		DWG G=new DWG();
		for(int i=0;i<5;i++)G.addNode(i);
		G.connect(0,1,5);
		G.connect(1,0,8);
		G.connect(0,2,2);
		G.connect(2,0,1);
		G.connect(2,3,1);
		G.connect(3,2,0.5);
		G.connect(3,4,12);
		G.connect(4,3,1);
		G.connect(0,4,20);
		G.connect(4,0,20);
		G.connect(4,1,5);
		G.connect(1,4,5);
		return G;
	}
	public void PrintList(List<node_data> L)
	{
		Iterator<node_data> ite=L.iterator();
		while(ite.hasNext())
		{
			node_data N=ite.next();
			System.out.print(N.toString());
			if(ite.hasNext())System.out.print("-->");
		}
		System.out.println();
	}
	@Test
	public void TestD1() 
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		double L=A.shortestPathDist(0,4);
		boolean flag=(L==10);
		//System.out.println("D1 :"+L);
		assertTrue(flag);
	}
	@Test
	public void TestD2() 
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
//		List<node_data> L=A.shortestPath(0,4);
		//System.out.println("D2 : "+L.size());
		//PrintList(L);
		assertEquals(A.shortestPath(0,4).size(),3);	
	}
	@Test
	public void TestD3() 
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		double L=A.shortestPathDist(4,0);
		//System.out.println("D3 :"+L);
		boolean flag=(L==2.5);
		assertTrue(flag);
		
	}
	@Test
	public void TestD4() 
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		List<node_data> L=A.shortestPath(4,0);
		//System.out.println("D4 : "+L.size());
		//PrintList(L);
		assertEquals(L.size(),4);
		
	}
	@Test
	public void TestD5() 
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		G.removeNode(2);
		List<node_data> L=A.shortestPath(4,0);
		//System.out.println("D5 : "+L.size());
		//PrintList(L);
		assertEquals(L.size(),3);
		
	}
	@Test
	public void TestD6() 
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		G.removeNode(2);
		double L=A.shortestPathDist(4,0);
		boolean flag=(L==13);
		//System.out.println("D6 : "+ L );
		assertTrue(flag);
	}
	public DWG ConnectedGraph(int Num)
	{
		DWG G=new DWG();
		for(int i=0;i<Num;i++)G.addNode(i);
		for(int i=2;i<Num;i++)
		{
			double W=Math.random()*10;
			G.connect(0,i,W);
			G.connect(1,i,W);
		}
		G.connect(1,0,1);
		G.connect(0,1,1);
		return G;
	}
	public DWG ConnectedGraph2(int Num)
	{
		DWG G=new DWG();
		for(int i=0;i<Num;i++)G.addNode(i);
		for(int i=0;i<Num;i++)
		{
			double W=Math.random()*10;
			if(i+1<Num)G.connect(i,i+1, W);
		}
		G.connect(0,Num-1,1);
		return G;
	}
	@Test
	public void TestE1()
	{
		DWG G=ConnectedGraph(100);
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	@Test
	public void TestE2()
	{
		DWG G=ConnectedGraph(10000);
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	@Test
	public void TestE3()
	{
		DWG G=ConnectedGraph(1000000);
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	@Test
	public void TestE4()
	{
		DWG G=ConnectedGraph2(10000);
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.isConnected();
		assertTrue(flag);
	}
	@Test
	public void TestF1()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		boolean flag=A.save("TestF1.txt");
		assertTrue(flag);
	}
	@Test
	public void TestF2()
	{
		Algo_Graph A=new Algo_Graph();
		boolean flag=A.load("TestF1.txt");
		assertTrue(flag);
	}
	@Test
	public void TestF3()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		A.save("TestF1");
		Algo_Graph A1=new Algo_Graph();
		A1.load("TestF1");
		assertEquals(A.getGraph(),A1.getGraph());
		
	}
	@Test
	public void TestG1()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		DWG G1=(DWG)A.copy();
		assertEquals(5,G1.nodeSize());
	}
	@Test
	public void TestG2()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		DWG G1=(DWG)A.copy();
		assertEquals(12,G1.edgeSize());
	}
	@Test
	public void TestG3()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		DWG G1=(DWG)A.copy();
		G1.removeNode(1);
		assertEquals(8,G1.edgeSize());
	}
	@Test
	public void TestG4()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		DWG G1=(DWG)A.copy();
		G.removeNode(1);
		assertEquals(5,G1.nodeSize());
	}
	@Test
	public void TestG5()
	{
		DWG G=GraphBulidTestD();
		Algo_Graph A=new Algo_Graph();
		A.init(G);
		DWG G1=(DWG)A.copy();
		G.removeNode(1);
		//System.out.println(G.toString());
		G1.removeNode(1);
		assertEquals(A.getGraph(),G1);
	}
	
	


}
