package cn.hutao.jml.statistics.estimate;

import org.apache.commons.math3.distribution.FDistribution;

/**
 * 正太分布的两个总体方差比值估计
 * 样本方差比/总体方差比 服从F分布
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月5日 	Created
 *
 * </pre>
 * @since 1.
 */
public class NormalVarianceDiffEstimate {
    public static void main(String[] args) {
        //两个机器AB，从A抽取18根管子，测得样本方差为0.34,从B抽取13根管子，样本方差0.29。 问，两台机器谁更稳定
        double sX2 = 0.34;
        double sY2 = 0.29;
        
        FDistribution f = new FDistribution(18-1,13-1);
        
        double lower = (sX2/sY2)/(f.inverseCumulativeProbability(0.05));
        double upper = (sX2/sY2)/(f.inverseCumulativeProbability(1-0.05));
        System.out.println("置信下限:"+lower);
        System.out.println("置信上限:"+upper);
        //因为包含1 ，所以认为没有区别
    }
}
