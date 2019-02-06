package cn.hutao.jml.statistics.estimate;

import org.apache.commons.math3.distribution.TDistribution;

/**
 * 两个正太分布的均值插的区间估计
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月5日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class NormalAvgDiffEstimate {

    public static void main(String[] args) {
        // 已知方差服从正态分布
        // 上限 (avgX-avgY) + NormalDistribution.inverseCumulativeProbability(0.025)*Math.sqrt(varX/n1 + varY/n2);
        
        // 未知方差，构造t分布
        double avgX = 500.0;//x样本均值
        double sX = 1.10;//x样本标准差
        double n1 = 10;
        
        double avgY = 496.0;
        double sY = 1.20;
        double n2 = 20;
        
        double Sw = ((n1-1)*sX*sX + (n2-1)*sY*sY)/(n1+n2-2);
        double Swq = Math.sqrt(Sw);
        //构造t分布
        TDistribution t = new TDistribution(n1+n2-2);
        
        double lower = (avgX-avgY) + t.inverseCumulativeProbability(0.025)*Swq*Math.sqrt(1/n1 + 1/n2);
        double upper = (avgX-avgY) - t.inverseCumulativeProbability(0.025)*Swq*Math.sqrt(1/n1 + 1/n2);
        System.out.println("均值差的置信下限:"+lower);
        System.out.println("均值差的置信上限:"+upper);
        
        //如果说置信区间包含0，一般我们认为均值没有显著差异
        
    }
}
