package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import mlcs.SucTable;

public class STUtils {
	private STUtils(){
		
	}
	/**
	 * 从文件中读取序列，为每一个序列构造一个后继表，
	 * @param file
	 * @return
	 */
	public static List<SucTable> buildSTfromFile(String file){
		LinkedList<SucTable> list = new LinkedList<SucTable>();
		SucTable st;
		try {
			BufferedReader br= new BufferedReader(new FileReader(new File(file)));
			String sequence;
			while((sequence=br.readLine())!=null){
				st=buildST(sequence);
				list.add(st);
			}
			return list;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 给定一个序列构造相应的后继表
	 * @param sequence
	 * @return得到的后继表
	 */
	public static SucTable buildST(String sequence){
		SucTable st=new SucTable();
		st.setSequence(sequence);
		char char_to_row[]=new char[256];
		char_to_row['A']=0;
		char_to_row['C']=1;
		char_to_row['T']=2;
		char_to_row['G']=3;
		int length=sequence.length();
		st.setLength(length);
		int table[][]=new int[4][length+1];
		
		for (int i = 0; i < length; ++i){
			int row = char_to_row[sequence.charAt(i)];
			for (int col = i, v = col + 1; col >= 0 && table[row][col] == 0; col--)
				table[row][col] = v;
		}
		st.setTable(table);		
		return st;
	}
}
