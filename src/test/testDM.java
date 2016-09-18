package test;

import com.google.common.collect.HashBiMap;

import mlcs.PointNode;

public class testDM {
	public static void main(String[] args) {
		PointNode node1=new PointNode(); 
		node1.setID(1);
		int[] A = {1,2,3};
		node1.setPoint(A);
		
		PointNode node2=new PointNode();
		node2.setID(2);
		int[] B={1,2,3};
		node2.setPoint(B);
		
		HashBiMap<Integer, PointNode> DM = HashBiMap.create();
		DM.forcePut(1, node1);
		
		boolean containsValue = DM.containsValue(node2);
		System.out.println(containsValue);
	}
}
