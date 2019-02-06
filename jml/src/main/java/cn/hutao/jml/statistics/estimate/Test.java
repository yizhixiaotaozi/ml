package cn.hutao.jml.statistics.estimate;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

public class Test {

    public static void main(String[] args) {
        NormalDistribution d = new NormalDistribution();
        System.out.println(d.cumulativeProbability(0));
        System.out.println(d.cumulativeProbability(1.6448536269514724));
        System.out.println(d.inverseCumulativeProbability(0.95));
        System.out.println(d.inverseCumulativeProbability(0.975));
        System.out.println(d.cumulativeProbability(-1.959963984540054));
        
        TDistribution t = new TDistribution(0.1);
    }
}
