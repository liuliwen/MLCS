package mlcs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.HashBiMap;

import utils.Construct_ICSG;
import utils.STUtils;

public class Main {


	public static void main(String[] args) {
	        String file="sequence";
			List<SucTable> suc_table = STUtils.buildSTfromFile(file);
			
			 int[] end=new int[suc_table.size()];     //初始化终节点
			   for(int i=0;i<suc_table.size();i++){
				   end[i]=-1;
			   }
			   PointNode p_end=new PointNode();
			   p_end.setPoint(end);
			   p_end.setPrecursor(new LinkedList<PointNode>());
			   
			   int maxLevel=0;
     /*		for (SucTable sucTable : suc_table) {          //输出后继表
				System.out.println(sucTable);
			}*/
			
			
			PointNode pn=new PointNode();
			int[] point=new int[suc_table.size()];
			pn.setPoint(point);
			pn.setID(0);
			Queue<PointNode> qpoint=new LinkedList<PointNode>();
			Construct_ICSG.ProduceSuc(pn, suc_table, qpoint);
/*			while(!qpoint.isEmpty()){
				System.out.println(qpoint.poll());
			}*/
			HashBiMap<Integer,PointNode> DM=HashBiMap.create();
			DM=Construct_ICSG.ConstructIgsg(suc_table);
			System.out.println(DM.size());
			
			for(int i=0;i<DM.size();i++){
				System.out.print(i+",");
				System.out.println(DM.get(i));
			}
			
			maxLevel=Construct_ICSG.Forwardtopsort(suc_table, DM, maxLevel, p_end);
			Construct_ICSG.BackwardTopSort(suc_table, DM, maxLevel, p_end);
			List<String> allMlcs = Construct_ICSG.getAllMlcs(DM,p_end);
			
			
		}

	}


