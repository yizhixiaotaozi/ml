package cn.hutao.jml.statistics;

import org.apache.commons.math3.distribution.TDistribution;

import cn.hutao.jml.tools.Vector;

/**
 * 正态分布总体均值的区间估计
 * 已知样本均值、样本方差，
 * 构造t分布，得到区间估计
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年1月29日 	Created
 *
 * </pre>
 * @since 1.
 */
public class NormalDistirbutionAvgIntervalEstimate {

    public static void main(String[] args) {
        //水果糖 16袋重量，求0.95的置信区间（对称）
        Double[] data = new Double[] {506.0,508.0,499.0,503.0,504.0,510.0,497.0,512.0,514.0,505.0,493.0,496.0,506.0,502.0,509.0,496.0};
        System.out.println(data.length);
        Vector v = new Vector(data);
        System.out.println("样本均值："+v.getAvg());
        System.out.println("样本方差："+v.getSampleVariance());
        System.out.println("样本标准差："+v.getSampleStandardDeviation());
        //构造t分布
        TDistribution t = new TDistribution(v.getLength()-1);
        double Tupper = t.inverseCumulativeProbability(0.975);
        System.out.println(Tupper);
        double Tlower = t.inverseCumulativeProbability(0.025);
        System.out.println(Tlower);
        //置信上限
        double upper = v.getAvg() + v.getSampleStandardDeviation()/Math.sqrt(v.getLength()) * Tupper;
        System.out.println("置信下限："+upper);
        double lower = v.getAvg() + v.getSampleStandardDeviation()/Math.sqrt(v.getLength()) * Tlower;
        System.out.println("置信下限："+lower);
        //也就是说，均值在500.4-507之间的概率为95%
        //在这区间内，取一个平均值，最大误差不超过v.getSampleStandardDeviation()/Math.sqrt(v.getLength()) * Tupper * 2
    }
}
