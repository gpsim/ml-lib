package com.spuerh.hz.mllib.struct.classify;

/**
 * @Describe:分类对象通用接口
 */
public interface Distinguishable{

	/**
	 * Return class mark
	 */
	public int getDistinguishSign();
	
	/**
	 * Set class mark of this Distinguishable object
	 */
	public void setDistinguishSign(int distinguishSign);
	
}
