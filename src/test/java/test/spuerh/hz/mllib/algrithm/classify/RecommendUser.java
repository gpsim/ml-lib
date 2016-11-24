package test.spuerh.hz.mllib.algrithm.classify;

import com.spuerh.hz.mllib.struct.classify.DistinguishDistancable;
import com.spuerh.hz.mllib.struct.cluster.DistanceComparable;


/**
 * @Describe:推荐目标用户
 */
public class RecommendUser implements DistinguishDistancable {

	private int age;
	private int gender;
	private int arup;
	private int pv;
	private int consumption ;
	private int distinguishSign;	//0代表可推荐，1代表不可推荐
	
	public RecommendUser(int age,int gender,int arup,int pv,int consumption){
		this.age = age; 
		this.gender = gender; 
		this.arup = arup; 
		this.pv = pv; 
		this.consumption = consumption; 
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getArup() {
		return arup;
	}
	public void setArup(int arup) {
		this.arup = arup;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public int getConsumption() {
		return consumption;
	}
	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	@Override
	public int getDistinguishSign() {
		return distinguishSign;
	}

	@Override
	public void setDistinguishSign(int distinguishSign) {
		this.distinguishSign = distinguishSign;
	}

	@Override
	public double distance(Object o) {
		return Math.abs(this.getArup()-((RecommendUser)o).getArup());
	}

	@Override
	public DistanceComparable getCerter(Object o) {
		int centerArup = (this.getArup()+((RecommendUser)o).getArup())/2;
		return new RecommendUser(age,gender,centerArup,pv,consumption);
	}
	
}
