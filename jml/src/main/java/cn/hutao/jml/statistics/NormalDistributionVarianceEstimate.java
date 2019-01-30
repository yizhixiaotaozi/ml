package cn.hutao.jml.statistics;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import cn.hutao.jml.tools.Vector;

/**
 * 正态分布方差区间估计
 * 未知均值
 * 样本方差*(n-1)/总体方差 服从 卡方分布
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年1月29日 	Created
 *
 * </pre>
 * @since 1.
 */
public class NormalDistributionVarianceEstimate {

    public static void main(String[] args) {
        //水果糖 16袋重量，求0.95的置信区间（对称）
        Double[] data = new Double[] {506.0,508.0,499.0,503.0,504.0,510.0,497.0,512.0,514.0,505.0,493.0,496.0,506.0,502.0,509.0,496.0};
        System.out.println(data.length);
        Vector v = new Vector(data);
        System.out.println("样本均值："+v.getAvg());
        System.out.println("样本方差："+v.getSampleVariance());
        System.out.println("样本标准差："+v.getSampleStandardDeviation());
        //构造卡方分布
        ChiSquaredDistribution d = new ChiSquaredDistribution(v.getLength() -1);
        double chilower = d.inverseCumulativeProbability(0.025);
        double chiUpper = d.inverseCumulativeProbability(0.975);
        System.out.println("卡方分布下限："+chilower);
        System.out.println("卡方分布上限："+chiUpper);
        
        double lower = (v.getLength()-1)*v.getSampleVariance()/chilower;
        double upper = (v.getLength()-1)*v.getSampleVariance()/chiUpper;
        System.out.println("方差的置信下限："+lower);
        System.out.println("方差的置信上限："+upper);
        
        //标准差可以直接开方
        System.out.println("标准差置信下限："+Math.sqrt(lower));
        System.out.println("标准差置信上限："+Math.sqrt(upper));
    }
}
