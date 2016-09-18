package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.google.common.collect.BiMap;


import com.google.common.collect.HashBiMap;

import javax.management.Query;









import mlcs.PointNode;
import mlcs.SucTable;

public class Construct_ICSG {
	
   private Construct_ICSG(){
		
	}
   
   //�������еĺ�̽ڵ�
   public static HashBiMap<Integer, PointNode> ConstructIgsg(List<SucTable> suc_table) {
	   
	   Queue<PointNode> qpoint=new LinkedList<PointNode>();   //���ɶ��� qpoint
	   int[] p=new int[suc_table.size()];           
	   int indexOfPoint=0;
	   PointNode point=new PointNode();         //���ɵ� point
	   point.setPoint(p);
	   point.setID(0);
	   qpoint.offer(point);
	   HashBiMap<Integer,PointNode> DM=HashBiMap.create();   //����˫��hash��
	   BiMap<PointNode, Integer> inverse = DM.inverse();
	   DM.forcePut(indexOfPoint, point);      //�ѳ�ʼ��������
	   indexOfPoint++;                 //��ż�1
	   while(!qpoint.isEmpty()){      //���зǿ�
		   PointNode q=qpoint.poll();   //����������̵Ľڵ�
		   Queue<PointNode> queuepoint=new LinkedList<PointNode>();
		   ProduceSuc(q, suc_table, queuepoint);   //���ݺ�̱�����˵�ĺ�̷���queuepoint������
		   while(!queuepoint.isEmpty()){         //�ж϶����Ƿ�Ϊ��
			   PointNode ptem=queuepoint.poll();
			   if(!DM.containsValue(ptem)){
				   ptem.setID(ptem.getID()+1);
				   qpoint.offer(ptem);
				   DM.forcePut(indexOfPoint, ptem);
				   indexOfPoint++;
			   }else{
				   PointNode PN=DM.get(inverse.get(ptem));
				   PN.setID(PN.getID()+1);
			   }
		   }
		 }
	   return DM;
	   
	   
}
   /**
    * 
    * �����ĸ���̽ڵ�
    * 
    */
   public static void ProduceSuc(PointNode point,List<SucTable> suc_table,Queue<PointNode> queuepoint){
	   int[] pold=new int[suc_table.size()];
	   int[] pnew;
	   PointNode pn;
	   pold=point.getPoint();
	   for(int i=0;i<4;i++){
		   int flag=1;
		   pnew=new int[suc_table.size()];
		   pn=new PointNode();
		   for(int j=0;j<suc_table.size();j++){
			   int m=suc_table.get(j).getLength()+1;
			   int[][] table=new int[4][m];
			   table=suc_table.get(j).getTable();			   
			   pnew[j]=table[i][pold[j]];
			   if(pnew[j]==0){
				  flag=0;
				  break;
			   }
		   }
		   
		   if(flag==1){
		    pn.setPoint(pnew);
		    pn.setID(0);
		    queuepoint.offer(pn);
		   }
		   
		 }

   }

   /**
    *����ǰ����������
    *
    */
   public static int Forwardtopsort(List<SucTable> suc_table,HashBiMap<Integer, PointNode> DM,int maxLevel,PointNode p_end){
	   
	   Queue<PointNode> qpoint=new LinkedList<PointNode>();  //D ��k����� ��ʱ����
	   Queue<PointNode> qpoint2; //D ��k+1�������ʱ����
	   BiMap<PointNode, Integer> inverse = DM.inverse();
	   
	   int k=0;                      //��ʼ������Ϊ��0��
	   PointNode point=DM.get(0);     //��ʼ�ڵ�
	   qpoint.offer(point);
	   
	  /* int[] end=new int[suc_table.size()];     //��ʼ���սڵ�
	   for(int i=0;i<suc_table.size();i++){
		   end[i]=-1;
	   }
	   PointNode p_end=new PointNode();
	   p_end.setPoint(end);*/
	   
	   while(!qpoint.isEmpty()){
		   int count=qpoint.size();
		   qpoint2=new LinkedList<PointNode>();
		   for(int i=0;i<count;i++){
			   PointNode q=qpoint.poll();   //����������̵Ľڵ�
			   Queue<PointNode> queuepoint=new LinkedList<PointNode>();
			   ProduceSuc(q, suc_table, queuepoint);    //queuepointΪ��̽ڵ�����
			   int flag=0;       //���ڱ�� q�Ƿ���ں�̽ڵ�
			   
			   while(!queuepoint.isEmpty()){
				   flag++;
				   PointNode ptem=queuepoint.poll();
				   PointNode PN=DM.get(inverse.get(ptem));
				   /**
				    * ����ֱ�Ӻ�̲���������
				    */
				   if(PN.getID()==1){
					   if(PN.getPrecursor()==null)//ǰ��û�г�ʼ���������Ҫ�ж��Ƿ��Ѿ���ʼ���ˡ�
						   PN.setPrecursor(new LinkedList<PointNode>());
					   PN.getPrecursor().add(q);
					   PN.setTleve(k+1);
				   }
				   PN.setID(PN.getID()-1);
				   if(PN.getID()==0){
					   qpoint2.offer(PN);
				   }
				   
			   }
			   
			   if(flag==0){
				   p_end.getPrecursor().add(q);
			   }
			   
		   }
		   
		   qpoint=qpoint2;
		   qpoint2=null;
		   k++;
		   
	   }
	   maxLevel=k-1;
	   return maxLevel;	   
   }
   
   /**
    *���к�����������
    *
    */
   public static void BackwardTopSort (List<SucTable> suc_table,HashBiMap<Integer, PointNode> DM,int maxLevel,PointNode p_end) {
	   Queue<PointNode> qpoint=new LinkedList<PointNode>();  //D ��k����� ��ʱ����
	   Queue<PointNode>  qpoint2;//D ��k+1�������ʱ����
	   BiMap<PointNode, Integer> inverse = DM.inverse();
	   
	   qpoint.offer(p_end);
	   int k=0;
	   
	   while(!qpoint.isEmpty()){
		   qpoint2=new LinkedList<PointNode>();
		   int count=qpoint.size();
		   for(int i=0;i<count;i++){
			   PointNode q=qpoint.poll();
			   //int count2=q.getPrecursor().size();
			   if(q.getPrecursor()!=null){
				   for(int j=0;j<q.getPrecursor().size();j++){
					   PointNode p=q.getPrecursor().get(j);
					   if(p.getTleve()+k!=maxLevel){
						   q.getPrecursor().remove(j);
						   j--;
					   }else{
						   qpoint2.offer(p);
					   }
				   }
			   }
			   
		   }
		   qpoint=qpoint2;		   
		   k++;
		   
	   }
  }
   
   /**
    *������Ľڵ����꣬��������֮���
    *
    */
   public static List<String> getAllMlcs(HashBiMap<Integer, PointNode> DM,PointNode p_end){
	   LinkedList<String> list = new LinkedList<String>();
	   LinkedList<List> allList = new LinkedList<List>();
	   ArrayList<PointNode> pointList = new ArrayList<PointNode>(100);
	   allList.add(pointList);
	   PrintLMCS(allList,pointList,p_end);
	   /**
	    * ��allList�б����pointת��ΪString
	    */
	   return list;
   }
   public static void PrintLMCS(LinkedList<List> allList,ArrayList<PointNode> pointList,PointNode p_end){
	   Stack<PointNode> pointstack=new Stack<PointNode>();
	   List<PointNode> p=p_end.getPrecursor();
	   if(p == null)
		   return;
	   System.out.println(p_end);
	   pointList.add(p_end);
	   ArrayList<PointNode> newList=new ArrayList<PointNode>(pointList);;
	   
	   for (int i = 0;i<p.size();++i) {
		   if (i>=1){
			   pointList = new ArrayList<PointNode>(pointList);
			   allList.add(pointList);
		   }
		   PointNode pointNode = p.get(i);
		   PrintLMCS(allList,pointList,pointNode);		
	   }
   }

}
