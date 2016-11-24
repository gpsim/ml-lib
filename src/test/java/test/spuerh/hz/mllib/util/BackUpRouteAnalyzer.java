package test.spuerh.hz.mllib.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.spuerh.hz.mllib.algrithm.association.PrefixSpan;
import com.spuerh.hz.mllib.struct.association.Item;
import com.spuerh.hz.mllib.struct.association.ItemSet;
import com.spuerh.hz.mllib.struct.association.Sequence;

/**
 *  @Describe: 热门路线分析
 */
public class BackUpRouteAnalyzer {
	public static String getTopnRoutes(List<String> routeSet, int Topn,int MaxPattern,int minLength,double minsupport) {
		if(routeSet.size()<1) return "";
		if(Topn<=0) return "";
		if(MaxPattern<=0) return "";
		if(minsupport<=0||minsupport>1) return "";
		if(minLength<=0)minLength=1;
		PrefixSpan p = new PrefixSpan(routeSet, minsupport);
		p.setMaxGap(0);
		//if(MaxPattern<Integer.MAX_VALUE&&MaxPattern>0){
			p.setMaxPattern(MaxPattern);
//System.out.println("MaxPattern is invalid ,instead of "+MaxPattern);

		System.out.println(p);
		System.out.println("\n===============================");

		p.prefixSpan();
		List<Sequence> fs = p.getFreqPattern();
		System.out.println("\nFrequent Patterns:");
		for (Sequence s : fs) {
			System.out.println(s);
		}
		System.out.println("\n===============================");

		System.out.println("\nTop-n Patterns:");
		SequenceComparator comparator = new SequenceComparator();
		Collections.sort(fs, comparator);
		
//		for (Sequence s : fs) {
//			System.out.println(s);
//		}

//		List<Sequence> 
//		Iterator<Sequence> fsIt = fs.iterator();
//		while (fsIt.hasNext() && n > 0) {
//			
//		}

		// cell1,cell2,cell3|weight;cell4,cell5,cell6|weight;value
		List<String> hotLine = new ArrayList<String>();
		int totalSequence = p.getDatabase().size();
		StringBuffer result = new StringBuffer();
		Iterator<Sequence> fsIt = fs.iterator();
		while (fsIt.hasNext() && Topn > 0) {
			Topn--;
			Sequence s = fsIt.next();
			String hot = "";
			String pre = "";
			StringBuffer nowLoad = new StringBuffer();
			for (ItemSet iset : s.getItemSets()) {
				for (Item i : iset.getItems()) {
					if(!pre.equals(i.getLabel())){
						nowLoad.append(i.getLabel() + ",");
						//result.append(i.getLabel() + ",");
						//hot+=(i.getLabel() + ",");
						pre = i.getLabel();
					}
				}
			}
			
//System.out.println(nowLoad.toString().split(",").length+" :"+nowLoad);
			if(nowLoad.toString().split(",").length>=minLength){
				//System.out.println(nowLoad.toString().split(",").length+" :"+nowLoad);
				result.append(nowLoad.toString());
				result.deleteCharAt(result.length() - 1);
				double weight = (double) s.getIds().size() / totalSequence;
				result.append("|" + weight);
				result.append(";");
			}
			nowLoad.delete(0, nowLoad.length()-1);

			//hot+=("|" + weight+";");
//System.out.println("hot               "+hot);
//hotLine.add(hot);
		}
		int pointLength = result.toString().split(";").length;
		//System.out.println(pointLength);
		if(pointLength>1){
			result.append(totalSequence);
		}
		//System.out.println(result);
		return result.toString();
	}

	public static String getTopnRoutesOutLimit(List<String> routeSet, int Topn, int MaxPattern, int minLength,
			double minsupport) {
		if (routeSet.size() < 1)
			return "";
		if (Topn <= 0)
			return "";
		if (MaxPattern <= 0)
			return "";
		if (minsupport <= 0 || minsupport > 1)
			return "";
		if (minLength <= 0)
			minLength = 1;
		PrefixSpan p = new PrefixSpan(routeSet, minsupport);
		p.setMaxGap(0);
		// if(MaxPattern<Integer.MAX_VALUE&&MaxPattern>0){
		p.setMaxPattern(MaxPattern);
		// System.out.println("MaxPattern is invalid ,instead of "+MaxPattern);

		System.out.println(p);
		System.out.println("\n===============================");

		p.prefixSpan();
		List<Sequence> fs = p.getFreqPattern();
		System.out.println("\nFrequent Patterns:");
		for (Sequence s : fs) {
			System.out.println(s);
		}
		System.out.println("\n===============================");

		System.out.println("\nTop-n Patterns:");
		SequenceComparator comparator = new SequenceComparator();
		Collections.sort(fs, comparator);

		// for (Sequence s : fs) {
		// System.out.println(s);
		// }

		// List<Sequence>
		// Iterator<Sequence> fsIt = fs.iterator();
		// while (fsIt.hasNext() && n > 0) {
		//
		// }

		// cell1,cell2,cell3|weight;cell4,cell5,cell6|weight;value
		List<String> hotLine = new ArrayList<String>();
		int totalSequence = p.getDatabase().size();
		StringBuffer result = new StringBuffer();
		Iterator<Sequence> fsIt = fs.iterator();
		while (fsIt.hasNext() && Topn > 0) {
			Topn--;
			Sequence s = fsIt.next();
			String hot = "";
			String pre = "";

			for (ItemSet iset : s.getItemSets()) {
				for (Item i : iset.getItems()) {
					if (!pre.equals(i.getLabel())) {

						result.append(i.getLabel() + ",");
						// hot+=(i.getLabel() + ",");
						pre = i.getLabel();
					}
				}
			}

			result.deleteCharAt(result.length() - 1);
			double weight = (double) s.getIds().size() / totalSequence;
			result.append("|" + weight);
			result.append(";");

			// hot+=("|" + weight+";");
			// System.out.println("hot "+hot);
			// hotLine.add(hot);
		}
		result.append(totalSequence);
		return result.toString();
	}

	
	public static List<String> getTopn(List<String> routeSet, int n, double minsupport) {
		PrefixSpan p = new PrefixSpan(routeSet, minsupport);
		p.setMaxGap(2);
		p.setMaxPattern(20);
		System.out.println(p);
		System.out.println("\n===============================");


		p.prefixSpan();
		List<Sequence> fs = p.getFreqPattern();
		System.out.println("\nFrequent Patterns:");
		for (Sequence s : fs) {
			System.out.println(s);
		}
		System.out.println("\n===============================");

		System.out.println("\nTop-n Patterns:");
		SequenceComparator comparator = new SequenceComparator();
		Collections.sort(fs, comparator);

		// for (Sequence s : fs) {
		// System.out.println(s);
		// }

		// List<Sequence>
		// Iterator<Sequence> fsIt = fs.iterator();
		// while (fsIt.hasNext() && n > 0) {
		//
		// }

		// cell1,cell2,cell3|weight;cell4,cell5,cell6|weight;value
		List<String> hotLine = new ArrayList<String>();
		int totalSequence = p.getDatabase().size();
		StringBuffer result = new StringBuffer();
		Iterator<Sequence> fsIt = fs.iterator();
		TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
		while (fsIt.hasNext()&&n>0) {
			n--;
			String hot = "";
			Sequence s = fsIt.next();
			s.getItemSets().size();
			// System.out.println(s.getIds().size());
			for (ItemSet iset : s.getItemSets()) {
				String pre = "";
				for (Item i : iset.getItems()) {
					String now = i.getLabel();
					if (!now.equals(pre)) {
						hot += (i.getLabel() + ",");
					}
					pre = now;
				}
			}
			double weight = (double) s.getIds().size() / totalSequence;
			// System.out.println("------------------- "+hot.split(",").length+"
			// ------------------");
			// System.out.println(hot.substring(0, hot.length()-1)+"
			// "+s.getIds().size()+"/"+totalSequence+"="+weight);
			//hotLine.add(hot.substring(0, hot.length() - 1) + "     " + s.getIds().size() + "/" + totalSequence + "="
			//		+ weight);
			//hotLine.add(hot.substring(0, hot.length() - 1) + "|" + weight+";");
			
		}

		while (fsIt.hasNext() && n > 0) {
			n--;
			Sequence s = fsIt.next();
			String hot = "";
			String pre="";
			for (ItemSet iset : s.getItemSets()) {
				for (Item i : iset.getItems()) {
					if(!pre.equals(i.getLabel())){
System.out.println(pre+"   "+i.getLabel());
					hot += (i.getLabel() + ",");
					pre = i.getLabel();
					}
				}
			}
			double weight = (double) s.getIds().size() / totalSequence;
			hot += ("|" + weight + ";");
			System.out.println("hot               " + hot);
			hotLine.add(hot);
		}
		 
		return hotLine;
	}
	


}
class SequenceComparator implements Comparator<Sequence> {
	@Override
	public int compare(Sequence o1, Sequence o2) {
		int flag = 0;
		if (o1.getItemSets().get(0).getItems().size() > o2.getItemSets().get(0)
				.getItems().size()) {
			flag = 1;
		} else if (o1.getItemSets().get(0).getItems().size() < o2.getItemSets()
				.get(0).getItems().size()) {
			flag = -1;
		} else {
			if (o1.getIds().size() > o2.getIds().size()) {
				flag = 1;
			} else if (o1.getIds().size() < o2.getIds().size()) {
				flag = -1;
			}
		}
		return -flag;
	}
}

