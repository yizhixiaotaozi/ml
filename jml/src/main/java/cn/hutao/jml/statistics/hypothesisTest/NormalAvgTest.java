package cn.hutao.jml.statistics.hypothesisTest;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import cn.hutao.jml.tools.Vector;

/**
 * 正态分布均值检验
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
public class NormalAvgTest {

    public static void main(String[] args) {
        //已知总体方差的情况下，(样本均值-总体均值)/(总体方差/sqrt(n)) 服从正态分布
        // 如果这个值落在拒绝域，则认为小概率事件在一次实验中就出现，因此否定假设，选择备择假设
        double avg = 0.511;
        double n = 9;
        double a = 0.05;//显著水平
        double s = 0.015;
        //在显著水平a下，正对H1（不等于0.5）检验H0（等于0.5）
        NormalDistribution d = new NormalDistribution();
        double lower = d.inverseCumulativeProbability(a/2.0);
        double upper = -lower;
        double tongjiliang = (avg-0.5)/(s/Math.sqrt(n));
        System.out.println(tongjiliang);
        System.out.println(lower);
        System.out.println(upper);
        if( tongjiliang < lower || tongjiliang > upper) {
            System.out.println("拒绝原假设，认为均值不等于0.5");
        }else {
            System.out.println("接受原假设，认为均值等于0.5");
        }
                
        //如果总体方差未知，则需要构造t统计量 （样本均值-总体均值）/（样本方差/sqrt(n)) 服从t分布
        //某种原件的寿命X 服从正态分布，取16个样本.有没有理由认为元件的平均寿命大于225
        Double[] data = new Double[] {159.0,280.0,101.0,212.0,224.0,379.0,179.0,264.0,222.0,362.0,168.0,250.0,149.0,260.0,485.0,170.0};
        Vector v = new Vector(data);
        
        /*做区间估计*/
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
        System.out.println("置信下限："+(v.getAvg() + v.getSampleStandardDeviation()/Math.sqrt(v.getLength()) * Tupper));
        System.out.println("置信下限："+(v.getAvg() + v.getSampleStandardDeviation()/Math.sqrt(v.getLength()) * Tlower));
        
        //H0: <=225  H1: > 225
        double testUpper = t.inverseCumulativeProbability(0.95);
        System.out.println(testUpper);
        double tTongjiliang = (v.getAvg()-225.0)/(v.getSampleStandardDeviation()/Math.sqrt(v.getLength()));
        System.out.println(tTongjiliang);
        if(tTongjiliang > testUpper) {
            System.out.println("小概率发生认为大于225");
        }else {
            System.out.println("接受原假设，小于225");
        }
    }
}
