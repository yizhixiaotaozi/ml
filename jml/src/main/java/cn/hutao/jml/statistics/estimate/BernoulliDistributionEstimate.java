package cn.hutao.jml.statistics.estimate;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * 0-1分布 伯努利分布
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
public class BernoulliDistributionEstimate {

    public static void main(String[] args) {
        // 样本容量要大于等于50
        
        // 从100个抽到60个一级品
        double n = 100;
        NormalDistribution d = new NormalDistribution();
        double a = n + d.inverseCumulativeProbability(0.025)*d.inverseCumulativeProbability(0.025);
        double b = -(2*n*(60.0/100.0) + d.inverseCumulativeProbability(0.025)*d.inverseCumulativeProbability(0.025));
        double c = 100*(60.0/100.0)*(60.0/100.0);
        
        double lower = (-b - Math.sqrt(b*b - 4*a*c))/(2*a);
        double upper = (-b + Math.sqrt(b*b - 4*a*c))/(2*a);
        System.out.println("置信下限：" + lower);
        System.out.println("置信上限：" + upper);

    }
}
