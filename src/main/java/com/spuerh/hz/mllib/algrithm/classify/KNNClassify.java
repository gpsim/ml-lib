package com.spuerh.hz.mllib.algrithm.classify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spuerh.hz.mllib.struct.classify.DistinguishDistancable;


/**
 * @Describe:Knn分类
 */
public class KNNClassify<T extends DistinguishDistancable> {

	/**
	 * Training sample list of known distinguish signs.
	 */
	private List<T> trainSampleList;

	/**
	 * number of nearest neighbors.
	 */
	private int k;

	public KNNClassify(List<T> trainSampleList, int k) {
		this.trainSampleList = trainSampleList;
		this.k = k;
	}

	/**
	 * Main process of the algorithm.
	 */
	public void classify(T t) {

		Map<Integer, Double> topK = new HashMap<Integer, Double>();
		Map<Integer, Integer> distinguishSignMap = new HashMap<Integer, Integer>();

		for (int sampleIndex = 0; sampleIndex < trainSampleList.size(); sampleIndex++) {
			T trainSample = trainSampleList.get(sampleIndex);

			// get distinguish sign and its number,put into
			// map-distinguishSignMap
			int sampleDistinguishSign = trainSample.getDistinguishSign();
			if (!distinguishSignMap.containsKey(sampleDistinguishSign)) {
				distinguishSignMap.put(sampleDistinguishSign, 1);
			} else {
				int signNumber = distinguishSignMap.get(sampleDistinguishSign);
				signNumber++;
				distinguishSignMap.put(sampleDistinguishSign, signNumber);
			}

			double distance = trainSample.distance(t);

			// find k nearest neighbors.
			if (topK.size() < k) {
				topK.put(sampleIndex, distance);
			} else {
				double maxDistance = 0.0;
				int maxIndex = 0;

				for (Integer j : topK.keySet()) {
					if (topK.get(j) > maxDistance) {
						maxDistance = topK.get(j);
						maxIndex = j;
					}
				}

				if (distance < maxDistance) {
					topK.remove(maxIndex);
					topK.put(sampleIndex, distance);
				}

			}
		}

		Map<Integer, Integer> distinguishSignMapTopK = new HashMap<Integer, Integer>();

		// get distinguish sign and its number from k nearest neighbors,
		// put into map-distinguishSignMapTopK
		for (Integer sampleIndex : topK.keySet()) {

			T trainSample = trainSampleList.get(sampleIndex);

			int sampleDistinguishSign = trainSample.getDistinguishSign();

			if (!distinguishSignMapTopK.containsKey(sampleDistinguishSign)) {
				distinguishSignMapTopK.put(sampleDistinguishSign, 1);
			} else {
				int signNumber = distinguishSignMapTopK.get(sampleDistinguishSign);
				signNumber++;
				distinguishSignMapTopK.put(sampleDistinguishSign, signNumber);
			}

		}

		double approximationRatio = 0.0;
		int approximationSign = 0;

		// find distinguish sign of input DistinguishDistancable by
		// approximation ratio.
		for (Integer distinguishSign : distinguishSignMapTopK.keySet()) {

			int signNumberInTopK = distinguishSignMapTopK.get(distinguishSign);
			int signNumber = distinguishSignMap.get(distinguishSign);
			double signRatio = signNumberInTopK * 1.0 / signNumber;
			if (signRatio > approximationRatio) {
				approximationRatio = signRatio;
				approximationSign = distinguishSign;
			}

		}

		t.setDistinguishSign(approximationSign);

	}

}
