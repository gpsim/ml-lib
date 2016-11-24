package test.spuerh.hz.mllib.algrithm.classify;

import java.lang.reflect.InvocationTargetException;

import com.spuerh.hz.mllib.struct.classify.DecisionNode;

/**
 * @Describe:推荐决策树
 */
public class RecommendDecisionNode extends DecisionNode {

	private String recommendRule;
	 
	public RecommendDecisionNode(String name,String recommendRule) {
		super(name);
		this.recommendRule = recommendRule;
	}

	
	@Override
	public boolean contains(Object o) {
		String attribute = recommendRule.split("::")[0];
		int attributeMinValue = Integer.parseInt(recommendRule.split("::")[1]);
		int attributeMaxValue = Integer.parseInt(recommendRule.split("::")[2]);
		Class<RecommendUser> clazz = (Class<RecommendUser>) ((RecommendUser)o).getClass();
		//clazz.getField(attribute).get(Integer);
		try {
			Object b = clazz.getDeclaredMethod("get" + attribute).invoke(o);
			int attributeValue = Integer.parseInt(b.toString());
			if(attributeValue >= attributeMinValue && attributeValue <= attributeMaxValue){
				return true;
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		RecommendDecisionNode rdn = new RecommendDecisionNode("test","Age::13::15");
		RecommendUser ru = new RecommendUser(16,1,150,2000,50000);
		System.out.println(rdn.contains(ru));
	}
}
