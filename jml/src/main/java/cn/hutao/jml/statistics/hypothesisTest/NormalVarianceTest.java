package cn.hutao.jml.statistics.hypothesisTest;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

/**
 * 正态总体方差的假设检验
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月6日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class NormalVarianceTest {

    public static void main(String[] args) {
        //(n-1)*样本方差/总体方差 服从自由度为n-1的卡方分布
        //某厂的电池服从方差=5000的正态分布，现在抽26只，测的样本方差为9200，请问电池的寿命波动较以往有没有明显变化。
        double tongjiliang = (26-1)*9200.0/5000.0;
        System.out.println(tongjiliang);
        ChiSquaredDistribution d = new ChiSquaredDistribution(25.0);
        if(tongjiliang < d.inverseCumulativeProbability(0.01) || tongjiliang > d.inverseCumulativeProbability(0.99)) {
            System.out.println(d.inverseCumulativeProbability(0.99));
            System.out.println("落在了拒绝域，认为方差不等于5000");
        }else {
            System.out.println("接受H0，认为等于5000");
        }
    }
}
