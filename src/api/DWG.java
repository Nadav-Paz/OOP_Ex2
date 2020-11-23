package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DWG implements directed_weighted_graph {


	private HashMap <Integer, node_data> verticies;
	private HashMap <Long, EdgeData> edges;
	private int id;
	private int mode_counter;

	public class NodeData implements node_data {

		private int key;
		private int node_tag;
		private geo_location geo;
		private double node_weight;
		private String node_info;
		// A set of all the keys for all the edges this node points at
		private HashSet<Long> out_set;
		// A set of all the keys for all the edges that point at this node
		private HashSet<Long> in_set;
		
		public NodeData(int key, geo_location geo, int tag, double weight, String info){
			this.key = key;
			this.geo = new Geo(geo);
			this.node_tag = tag;
			this.node_weight = weight;
			this.node_info = new String(info);
			this.out_set = new HashSet<>();
			this.in_set = new HashSet<>();
			
			id++;
		}

		public NodeData(node_data node) {
			this(node.getKey(), node.getLocation(), node.getTag(), node.getWeight(), node.getInfo());
		}
		
		public NodeData(int key) {
			this(key, new Geo(), 0, 0, "");
		}

		@Override
		public int getKey() {
			return this.key;
		}

		@Override
		public geo_location getLocation() {
			return geo;
		}

		@Override
		public void setLocation(geo_location p) {
			this.geo = new Geo(p);
		}

		@Override
		public double getWeight() {
			return node_weight;
		}

		@Override
		public void setWeight(double w) {
			this.node_weight = w;
		}

		@Override
		public String getInfo() {
			return this.node_info;
		}

		@Override
		public void setInfo(String s) {
			this.node_info = new String(s);
		}

		@Override
		public int getTag() {
			return this.node_tag;
		}

		@Override
		public void setTag(int t) {
			this.node_tag = t;
		}
		public String toString()
		{
			return ("["+key+","+(Geo)geo+","+node_weight+","+node_tag+","+node_info+"]");
		}

	}

	private class EdgeData implements edge_data{

		private NodeData src, dst;
		private int edge_tag;
		private double edge_weight;
		private String edge_info;
		private long edge_key;

		private EdgeData(node_data src, node_data dst, double weight, int tag, String info){
			this.src = (NodeData) src;
			this.dst = (NodeData) dst;
			this.edge_tag = tag;
			this.edge_info = new String(info);
			this.edge_key = keyCalcuclate(this.src.key, this.dst.key);
			
			setWeight(weight);

			this.src.out_set.add(this.edge_key);
			this.dst.in_set.add(this.edge_key);
			edges.put(this.edge_key, this);
		}
		public EdgeData(node_data src, node_data dst, double weight) {
			this(src, dst, weight, 0, "");
		}

		@Override
		public int getSrc() {
			return this.src.key;
		}

		@Override
		public int getDest() {
			return this.dst.key;
		}

		@Override
		public double getWeight() {
			return this.edge_weight;
		}

		public void setWeight(double weight) {
			weight = weight < 0 ? 0 : weight;
			this.edge_weight = weight;
		}

		@Override
		public String getInfo() {
			return this.edge_info;
		}

		@Override
		public void setInfo(String s) {
			this.edge_info = new String(s);
		}

		@Override
		public int getTag() {
			return this.edge_tag;
		}

		@Override
		public void setTag(int t) {
			this.edge_tag = t;
		}

	}

	public DWG(){
		this.verticies = new HashMap<>();
		this.edges = new HashMap<>();
		this.mode_counter = 0;
		this.id = 0;
	}
	
	@Override
	public node_data getNode(int key) {
		return this.verticies.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {		
		return this.edges.get(keyCalcuclate(src, dest));
	}

	@Override
	public void addNode(node_data n) {
		if(n == null) {
			return;
		}
		this.mode_counter++;
		this.verticies.put(n.getKey(), new NodeData(n));
	}
	
	public void addNode(int key){
		addNode(new NodeData(key));
	}
	
	public boolean canEdge(int src,int dest)
	{
		return (src != dest && getNode(src) != null && getNode(dest) != null);
	}
	
	@Override
	public void connect(int src, int dest, double w) {
		if(!canEdge(src, dest))
			return;
		
		long key = keyCalcuclate(src, dest);
		EdgeData edge = this.edges.get(key);
		if(edge == null) {
			edge = new EdgeData(getNode(src), getNode(dest), w);
		}
		else
			edge.setWeight(w);
		this.mode_counter++;

	}

	@Override
	public Collection<node_data> getV() {
		return this.verticies.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		NodeData node = (NodeData)getNode(node_id);
		if(node == null) {
			return null;
		}
		Collection<Long> edge_keys = ((NodeData)getNode(node_id)).out_set;
		Collection<edge_data> collection = new ArrayList<edge_data>(edge_keys.size());
		for(long key : edge_keys) {
			collection.add(this.edges.get(key));
		}
		return collection;
	}

	@Override
	public node_data removeNode(int key) {
		NodeData node = (NodeData)getNode(key);
		
		if(node == null) {
			return null;
		}
		
		for(long l : node.in_set) {
			EdgeData edge = this.edges.remove(l);
			edge.dst.out_set.remove(l);
			this.mode_counter++;
		}
		
		for(long l : node.out_set) {
			this.edges.remove(l);		
			this.mode_counter++;
		}
		node.out_set = new HashSet<>();
		node.in_set = new HashSet<>();
		
		this.mode_counter++;
		return this.verticies.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		// TODO Auto-generated method stub
		this.mode_counter++;

		return null;
	}

	@Override
	public int nodeSize() {
		return this.verticies.size();
	}

	@Override
	public int edgeSize() {
		return this.edges.size();
	}

	@Override
	public int getMC() {
		return this.mode_counter;
	}

	@Override
	public String toString() {
		String str = "";
		for(node_data node : this.verticies.values())
			str += node;
		return str;
	}
	
	public static long keyCalcuclate(int src, int dst) {
		return (long) ((long)src << 32 | dst);
	}

}
