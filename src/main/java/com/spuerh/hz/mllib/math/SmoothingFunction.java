package com.spuerh.hz.mllib.math;

import java.util.ArrayList;
import java.util.List;

/**
 *	平滑函数
 * 
 */

public class SmoothingFunction {
	public static void simpleSmoothing(List<Double> list, int leftNumber,
			double centerWeight) {

		int winLen = 2 * leftNumber + 1; // ���ڳ���
		double[] weight = new double[winLen];
		double tmp;
		int listLen = list.size();
		double[] tmpValue = new double[listLen];

		// ȷ��Ȩ��

		tmp = (1 - centerWeight) / (winLen - 1);
		weight[leftNumber] = centerWeight;
		for (int i = 0; i < leftNumber; i++) {
			weight[i] = tmp;
			weight[winLen - i - 1] = tmp;
		}

		// �鿴Ȩ��
		// for (int i = 0; i < winLen; i++) {
		// System.out.println(weight[i]);
		// }

		// �����ݽ��й⻬�����±���ʼΪsigmaInt,listLen-sigmaInt-1
		for (int i = leftNumber; i < listLen - leftNumber; i++) {
			tmpValue[i] = 0;
			for (int j = 0; j < winLen; j++) {
				tmpValue[i] += weight[j] * list.get(i + j - leftNumber);
			}
		}

		// ����list�е�����
		for (int i = leftNumber; i < listLen - leftNumber; i++) {
			list.set(i, tmpValue[i]);
		}

	}
	public static void getSimpleSmoothing(List<Double> list, int leftNumber) {

		int winLen = 2 * leftNumber + 1; // ���ڳ���
		double[] weight = new double[winLen];
		double deltaWeight;
		int listLen = list.size();
		double[] tmpValue = new double[listLen];

		// ȷ��Ȩ��

		deltaWeight = (double) (1.0 / Math.pow(leftNumber + 1, 2));
		weight[leftNumber] = (1 + leftNumber) * deltaWeight;
		for (int i = 0; i < leftNumber; i++) {
			weight[i] = (i + 1) * deltaWeight;
			weight[winLen - i - 1] = weight[i];
		}

		// �鿴Ȩ��
		// for (int i = 0; i < winLen; i++) {
		// System.out.println(weight[i]);
		// }

		// �����ݽ��й⻬�����±���ʼΪsigmaInt,listLen-sigmaInt-1
		for (int i = leftNumber; i < listLen - leftNumber; i++) {
			tmpValue[i] = 0;
			for (int j = 0; j < winLen; j++) {
				tmpValue[i] += weight[j] * list.get(i + j - leftNumber);
			}
		}

		// ����list�е�����
		for (int i = leftNumber; i < listLen - leftNumber; i++) {
			list.set(i, tmpValue[i]);
		}
		
		


	}
	/**
	 * @function ���ԵȲ�ݼ�����Ȩ�أ�������߳���Ϊ3, ��Ȩ�ط���Ϊ x 2x 3x 4x 3x 2x x
	 */
	public static void simpleSmoothing(List<Double> list, int leftNumber) {

		int winLen = 2 * leftNumber + 1; // ���ڳ���
		double[] weight = new double[winLen];
		double deltaWeight;
		int listLen = list.size();
		double[] tmpValue = new double[listLen];

		// ȷ��Ȩ��

		deltaWeight = (double) (1.0 / Math.pow(leftNumber + 1, 2));
		weight[leftNumber] = (1 + leftNumber) * deltaWeight;
		for (int i = 0; i < leftNumber; i++) {
			weight[i] = (i + 1) * deltaWeight;
			weight[winLen - i - 1] = weight[i];
		}

		// �鿴Ȩ��
		// for (int i = 0; i < winLen; i++) {
		// System.out.println(weight[i]);
		// }

		// �����ݽ��й⻬�����±���ʼΪsigmaInt,listLen-sigmaInt-1
		for (int i = leftNumber; i < listLen - leftNumber; i++) {
			tmpValue[i] = 0;
			for (int j = 0; j < winLen; j++) {
				tmpValue[i] += weight[j] * list.get(i + j - leftNumber);
			}
		}

		// ����list�е�����
		for (int i = leftNumber; i < listLen - leftNumber; i++) {
			list.set(i, tmpValue[i]);
		}


	}

	// ----------------------------------------------------------------------------
	/**
	 * 高斯平滑函数
	 */
	public static void gaussSmoothing(List<Double> list, double sigma) {
		//System.out.println("hello");
		int sigmaInt = (int) sigma; // ��sigmaȡ�����京��Ϊ���ڰ�߳���
		int winLen = 2 * sigmaInt + 1; // ���ڳ���
		double[] weight = new double[winLen];
		double weightSum = 0;
		double tmp, tmpSum;
		int listLen = list.size();
		double[] tmpValue = new double[listLen];

		// ���ȷ��ԭʼȨ��
		for (int i = 0; i < winLen; i++) {
			tmpSum = 0;
			for (int j = 0; j < winLen; j++) {
				tmp = (double) -Math.pow(i - j, 2) / (2 * sigma * sigma);
				tmpSum += Math.exp(tmp);
			}
			weight[i] = tmpSum;
			weightSum += weight[i];
		}

		// ��ԭʼȨ�ؽ��й�һ������
		for (int i = 0; i < winLen; i++) {
			weight[i] = weight[i] / weightSum;
		}

		// �����ݽ��й⻬�����±���ʼΪsigmaInt,listLen-sigmaInt-1
		for (int i = sigmaInt; i < listLen - sigmaInt; i++) {
			tmpValue[i] = 0;
			for (int j = 0; j < winLen; j++) {
				tmpValue[i] += weight[j] * list.get(i + j - sigmaInt);
			}
		}

		// ����list�е�����
		for (int i = sigmaInt; i < listLen - sigmaInt; i++) {
			list.set(i, tmpValue[i]);
		}
		

	}

	// -----------------------------------------------------------------------------
	/**
	 * ��˹ƽ��-�򵥷�ʽȷ��ԭʼȨ��
	 */
	public static void gaussSimpleSmoothing(List<Double> list, double sigma) {
		int sigmaInt = (int) sigma; // ��sigmaȡ�����京��Ϊ���ڰ�߳���
		int winLen = 2 * sigmaInt + 1; // ���ڳ���
		double[] weight = new double[winLen];
		double weightSum = 0;
		double tmp;
		int listLen = list.size();
		double[] tmpValue = new double[listLen];

		// �򵥷�ʽȷ��ԭʼȨ
		for (int i = 0; i < winLen; i++) {
			tmp = (double) -Math.pow(i - sigmaInt, 2) / (2 * sigma * sigma);
			weight[i] = (double) Math.exp(tmp);
			weightSum += weight[i];
		}

		// ��ԭʼȨ�ؽ��й�һ������
		for (int i = 0; i < winLen; i++) {
			weight[i] = weight[i] / weightSum;
		}

		// �����ݽ��й⻬�����±���ʼΪsigmaInt,listLen-sigmaInt-1
		for (int i = sigmaInt; i < listLen - sigmaInt; i++) {
			tmpValue[i] = 0;
			for (int j = 0; j < winLen; j++) {
				tmpValue[i] += weight[j] * list.get(i + j - sigmaInt);
			}
		}

		// ����list�е�����
		for (int i = sigmaInt; i < listLen - sigmaInt; i++) {
			list.set(i, tmpValue[i]);
		}

	}

	public static void main(String[] args) {
		List<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(0.91473038);
		list.add(0.911646141);
		list.add(0.970517065);
		list.add(1.139745011);
		list.add(0.965920684);
		list.add(0.960718723);
		list.add(0.968294951);
		list.add(0.973196981);
		list.add(0.953304003);
		list.add(0.984864109);
		list.add(1.116337077);
		list.add(1.250671069);
		list.add(1.183296941);
		list.add(1.092923413);
		list.add(1.031908214);
		list.add(1.045540304);
		list.add(1.032666655);
		list.add(1.020053554);
		list.add(1.004316628);
		list.add(1.006848991);
		list.add(1.02872663);
		list.add(1.027547815);
		list.add(1.011914257);
		list.add(1.019588906);
		list.add(1.012486203);
		list.add(1.053217534);
		list.add(1.054262739);
		list.add(1.022731341);
		list.add(1.005482283);
		list.add(1.00295729);
		list.add(0.990171623);
		list.add(0.974891252);
		list.add(0.970780569);
		list.add(0.982682019);
		list.add(0.959472198);
		list.add(0.940621321);
		list.add(0.923238967);
		list.add(0.911468958);
		list.add(1.156270973);
		list.add(1.00685574);
		list.add(1.002557848);
		list.add(0.986309892);
		list.add(0.967085174);
		list.add(0.961369002);
		list.add(0.930649185);
		list.add(0.924397869);
		list.add(0.98168544);
		gaussSmoothing(list,1);
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i)+",");
			//System.out.println(list.get(i));
		}

	}

}
