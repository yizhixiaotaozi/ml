package cn.hutao.jml.statistics.linearRegression;

import org.apache.commons.math3.distribution.TDistribution;

import cn.hutao.jml.tools.Vector;

/***
 * 一元线性回归
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月17日 	Created
 *
 * </pre>
 * @since 1.
 */
public class OneFactorRegression {
    public static void main(String[] args) {
        Vector x = new Vector(new Double[] {100.0,110.0,120.0,130.0,140.0,150.0,160.0,170.0,180.0,190.0});
        Vector y = new Vector(new Double[] { 45.0, 51.0, 54.0, 61.0, 66.0, 70.0, 74.0, 78.0, 85.0, 89.0});
        
        double Sxx = x.multiply(x) - x.getSum()*x.getSum()/x.getLength();
        double Syy = y.multiply(y) - y.getSum()*y.getSum()/y.getLength();
        double Sxy = x.multiply(y) - x.getSum()*y.getSum()/x.getLength();
        
        double b = Sxy/Sxx;
        double a = y.getAvg() - x.getAvg()*b;
        
        System.out.println("回归方程为：y = "+b+"x + " + a);
        
        double q2 = (Syy - b * Sxy)/(x.getLength() - 2);
        System.out.println("残差的无偏估计:"+q2);
        
        
        //假设检验 H0； x的系数b == 0;
        //构造统计量 b=0时，服从n-2的t分布
        double t = (b*Math.sqrt(Sxx))/Math.sqrt(q2);
        System.out.println("检验系数的统计量："+t);
        TDistribution td = new TDistribution(x.getLength() - 2);
        double lower = td.inverseCumulativeProbability(0.025);
        double upper = td.inverseCumulativeProbability(0.975);
        if(t < lower || t > upper) {
            System.out.println("拒绝H0，说明x的系数不等于0，认为是有效的");
            System.out.println("区间估计下限：" + (b + lower*Math.sqrt(q2)/Math.sqrt(Sxx)));
            System.out.println("区间估计上限：" + (b + upper*Math.sqrt(q2)/Math.sqrt(Sxx)));
        }else {
            System.out.println("x的系数无效");
        }
        
        //估计出的函数的值 的估计 x = 125
        double testX = 125.0;
        double lx = testX*b + a + lower*Math.sqrt(q2)*Math.sqrt(1.0/x.getLength() + (testX -x.getAvg())*(testX -x.getAvg())/Sxx);
        double ux = testX*b + a + upper*Math.sqrt(q2)*Math.sqrt(1.0/x.getLength() + (testX -x.getAvg())*(testX -x.getAvg())/Sxx); 
        System.out.println("回归函数在x=125处的估计"+lx+"-"+ux);
        //观测值的区间估计
        double lx2 = testX*b + a + lower*Math.sqrt(q2)*Math.sqrt(1 + 1.0/x.getLength() + (testX -x.getAvg())*(testX -x.getAvg())/Sxx);
        double ux2 = testX*b + a + upper*Math.sqrt(q2)*Math.sqrt(1 + 1.0/x.getLength() + (testX -x.getAvg())*(testX -x.getAvg())/Sxx); 
        System.out.println("观测值在x=125处的估计"+lx2+"-"+ux2);
    }
}
