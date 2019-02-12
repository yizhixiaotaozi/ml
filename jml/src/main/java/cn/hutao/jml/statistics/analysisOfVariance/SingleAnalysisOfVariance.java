package cn.hutao.jml.statistics.analysisOfVariance;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import cn.hutao.jml.tools.Vector;

/**
 * 单因素方差分析
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月12日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class SingleAnalysisOfVariance {

    public static void main(String[] args) {
        //设有三台机器生产铝合金板，取样，H0: u1 = u2 = u3
        Double[] data1 = new Double[] {0.236,0.238,0.248,0.245,0.243};
        Vector v1 = new Vector(data1);
        Double[] data2 = new Double[] {0.257,0.253,0.255,0.254,0.261};
        Vector v2 = new Vector(data2);
        Double[] data3 = new Double[] {0.258,0.264,0.259,0.267,0.262};
        Vector v3 = new Vector(data3);
        
        double T1 = v1.getSum();
        double T2 = v2.getSum();
        double T3 = v3.getSum();
        
        double T = T1+T2+T3;
        int s = 3;//三个水平
        
        double St = calSt(v1,v2,v3,T);
        double Sa = calSa(v1,v2,v3,T);
        double Se = St - Sa;//误差平方和
        System.out.println("St:"+St);
        System.out.println("Sa:"+Sa);
        System.out.println("Se:"+Se);
        
        //构造统计量
        int n = v1.getLength() + v2.getLength() + v3.getLength();
        double f = (Sa/(3-1))/(Se/(n-3));
        System.out.println("f:"+f);
        //构造f分布
        FDistribution d = new FDistribution(s-1,n-s);
        double upper = d.inverseCumulativeProbability(0.95);
        System.out.println("upper:"+upper);
        if(f > upper) {
            System.out.println("拒绝H0，认为均值不相等");
        }else {
            System.out.println("接受H0");
        }
        // 无论是否接受H0，  Se/(n-s)都是总体方差的无偏估计
        // u是总体均值的无偏估计， u1,u2,u3是各个水平下的无偏估计
        
        // 如果说拒绝H0，可以得到各个水平均值差的区间估计
        TDistribution td = new TDistribution(n-s);
        double tUpper = td.inverseCumulativeProbability(0.975);
        //u1-u2
        System.out.println("u1-u2的区间估计："+(v1.getAvg() - v2.getAvg() - tUpper*Math.sqrt((Se/(n-s)) * (1/5.0 + 1/5.0))));
        System.out.println("u1-u2的区间估计："+(v1.getAvg() - v2.getAvg() + tUpper*Math.sqrt((Se/(n-s)) * (1/5.0 + 1/5.0))));

    }

    /**
     * 计算因素的效应平方和
     * @param v1
     * @param v2
     * @param v3
     * @param t
     * @return
     */
    private static double calSa(Vector v1, Vector v2, Vector v3, double t) {
        double T1 = v1.getSum();
        double T2 = v2.getSum();
        double T3 = v3.getSum();
        int n = v1.getLength() + v2.getLength() + v3.getLength();
        double sa = T1*T1/v1.getLength() + T2*T2/v2.getLength() + T3*T3/v3.getLength() - t*t/n;
        return sa;
    }

    /**
     * 计算st 总偏差平方和
     * @param v1
     * @param v2
     * @param v3
     * @param t
     * @return
     */
    private static double calSt(Vector v1, Vector v2, Vector v3, double t) {
        double xij2 = 0.0;
        for(double d:v1.getData()) {
            xij2 = xij2 + d*d;
        }
        for(double d:v2.getData()) {
            xij2 = xij2 + d*d;
        }
        for(double d:v3.getData()) {
            xij2 = xij2 + d*d;
        }
        int n = v1.getLength() + v2.getLength() + v3.getLength();
        return xij2 - t*t/n;
    }
}
