package cn.hutao.jml.statistics.hypothesisTest;

import org.apache.commons.math3.distribution.TDistribution;

import cn.hutao.jml.tools.Vector;

/**
 * 两个正态分布均值差假设检验
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
public class NormalAvgDiffTest {

    public static void main(String[] args) {
        //用两种方法AB测的冰块融化的时间，请问A方法测的结果是不是比B方法小
        //构造t统计量
        Double[] A = new Double[] {79.98,80.04,80.02,80.04,80.03,80.03,80.04,79.97,80.05,80.03,80.02,80.0,80.02};
        Vector vA = new Vector(A);
        
        Double[] B = new Double[] {80.02,79.94,79.98,79.97,79.97,80.03,79.95,79.97};
        Vector vB = new Vector(B);
        
        System.out.println(vA.getLength()+" "+vB.getLength());
        double s2A = vA.getSampleVariance();
        double s2B = vB.getSampleVariance();
        double s2W = ((vA.getLength()-1)*s2A + (vB.getLength()-1)*s2B)/(vA.getLength()+vB.getLength() - 2);
        System.out.println(s2W);
        double sW = Math.sqrt(s2W);
        
        //H0: avg1-avg2 <= 0
        double tongjiliang = (vA.getAvg()-vB.getAvg() - 0)/(sW * Math.sqrt(1.0/vA.getLength() + 1.0/vB.getLength()));
        System.out.println(tongjiliang);
        TDistribution t = new TDistribution(vA.getLength() + vB.getLength() -2);
        if(tongjiliang > t.inverseCumulativeProbability(0.975)) {
            System.out.println(t.inverseCumulativeProbability(0.975));
            System.out.println("落在了拒绝域,A比B大");
        }else{
            System.out.println("相同");
        }
    }
}
