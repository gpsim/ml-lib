package test.spuerh.hz.mllib.algrithm.association;

import java.util.List;

import com.spuerh.hz.mllib.algrithm.association.AprioriAlgori;
import com.spuerh.hz.mllib.algrithm.association.CommonApriori;
import com.spuerh.hz.mllib.algrithm.association.OrderApriori;
import com.spuerh.hz.mllib.struct.association.apriori.OrderItemSet;
import com.spuerh.hz.mllib.util.common.FileTool;


/**
 *  @Describe:apriori频繁项集经典算法
 */
public class TestApriori {
	
	public static void main(String[] args) {

		//List<String> list = FileTool.getContentFromFile("C:\\Users\\zmcc\\Desktop\\loadset.txt",10000);
		List<String> list = FileTool.getContentFromFile("C:\\Users\\huangchaohz\\Desktop\\position-data\\loadset.txt");
		System.out.println(list.size());
		long startTime = System.currentTimeMillis();
		//testPrefixSpan(list);
		//testCommonApriori(list);
		testOrderApriori(list);
		long endTime = System.currentTimeMillis();
		System.out.println("total time is :" + (endTime-startTime));
	}
	
	public static void testCommonApriori(List<String> list){
		CommonApriori a = new CommonApriori(list,",",0.01,2,5,2,20);
		System.out.println(a.getDatabase().size());
		a.excavate();
		System.out.println("====================================================");
		System.out.println("result frequent size = " + a.getFreItemSet().size());
		System.out.println("result frequent is:" + a.getFreItemSet().toArray());
	}
	
	public static void testOrderApriori(List<String> list){
		AprioriAlgori<String,OrderItemSet<String>> a = new OrderApriori(list,"-",0.9,0,100,1,100);
		System.out.println(a.getDatabase().size());
		a.excavate();
		System.out.println("====================================================");
		System.out.println("result frequent size = " + a.getFreItemSet().size());
		//System.out.println("result frequent is:" + a.getFreItemSet());
		Object[] is =  a.getFreItemSet().toArray();
		for(int i=0;i<is.length;i++){
			System.out.println("result frequent is:" + is[i]);
			System.out.println("result frequent is:" + ((OrderItemSet<String>)is[i]).getSeqIds().size());
			/*if(i==(is.length-1)){
				break;
			}*/
		}
 		/*System.out.println("result frequent is:" + a.freItemSet);*/
	}
	
}
