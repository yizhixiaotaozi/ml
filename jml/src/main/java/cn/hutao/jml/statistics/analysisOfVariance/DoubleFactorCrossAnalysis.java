package cn.hutao.jml.statistics.analysisOfVariance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.FDistribution;

import cn.hutao.jml.tools.Vector;

/**
 * 双因素方差分析 两个因素有交互作用
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月16日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class DoubleFactorCrossAnalysis {

    public static void main(String[] args) {
        //4种不同的燃料A1-A4 和 3种不同的推进器B1-B3。 每组做两次实验。
        //检验H01: 4种不同燃料之间是否有显著区别
        //检验H02: 3种不同推进器之间是否有显著区别
        //检验H03: 燃料和推进器的不同组合期间是否有不同区别
        //double[] A1B1
        Vector a1b1 = new Vector(new Double[] {58.2,52.6});
        Vector a1b2 = new Vector(new Double[] {56.2,41.2});
        Vector a1b3 = new Vector(new Double[] {65.3,60.8});
        Vector a2b1 = new Vector(new Double[] {49.1,42.8});
        Vector a2b2 = new Vector(new Double[] {54.1,50.5});
        Vector a2b3 = new Vector(new Double[] {51.6,48.4});
        Vector a3b1 = new Vector(new Double[] {60.1,58.3});
        Vector a3b2 = new Vector(new Double[] {70.9,73.2});
        Vector a3b3 = new Vector(new Double[] {39.2,40.7});
        Vector a4b1 = new Vector(new Double[] {75.8,71.5});
        Vector a4b2 = new Vector(new Double[] {58.2,51.0});
        Vector a4b3 = new Vector(new Double[] {48.7,41.4});

        List<Vector> a1Group = new ArrayList<Vector>();
        a1Group.add(a1b1);
        a1Group.add(a1b2);
        a1Group.add(a1b3);
        
        List<Vector> a2Group = new ArrayList<Vector>();
        a2Group.add(a2b1);
        a2Group.add(a2b2);
        a2Group.add(a2b3);
        
        List<Vector> a3Group = new ArrayList<Vector>();
        a3Group.add(a3b1);
        a3Group.add(a3b2);
        a3Group.add(a3b3);
        
        List<Vector> a4Group = new ArrayList<Vector>();
        a4Group.add(a4b1);
        a4Group.add(a4b2);
        a4Group.add(a4b3);
        
        List<Vector> all = new ArrayList<Vector>();
        all.addAll(a1Group);
        all.addAll(a2Group);
        all.addAll(a3Group);
        all.addAll(a4Group);
        
        List<Vector> b1Group = new ArrayList<Vector>();
        b1Group.add(a1b1);
        b1Group.add(a2b1);
        b1Group.add(a3b1);
        b1Group.add(a4b1);
        
        List<Vector> b2Group = new ArrayList<Vector>();
        b2Group.add(a1b2);
        b2Group.add(a2b2);
        b2Group.add(a3b2);
        b2Group.add(a4b2);
        
        List<Vector> b3Group = new ArrayList<Vector>();
        b3Group.add(a1b3);
        b3Group.add(a2b3);
        b3Group.add(a3b3);
        b3Group.add(a4b3);
        
        int r = 4;//A有四个水平
        int s = 3;//B有三个水平
        int t = 2;//每组实验两次
        //所有数据之和
        double T = sumAll(all);
        //总偏差平方和
        double St = 0.0;
        for(Vector v:all) {
            for(double d:v.getData()) {
                St = St + d*d;
            }
        }
        St = St - T*T/(r*s*t);
        
        double Ta1 = sumAll(a1Group);
        double Ta2 = sumAll(a2Group);
        double Ta3 = sumAll(a3Group);
        double Ta4 = sumAll(a4Group);
        double Sa = (Ta1*Ta1 + Ta2*Ta2 + Ta3*Ta3 + Ta4*Ta4)/(s*t) - T*T/(r*s*t);
        
        double Tb1 = sumAll(b1Group);
        double Tb2 = sumAll(b2Group);
        double Tb3 = sumAll(b3Group);
        double Sb = (Tb1*Tb1 + Tb2*Tb2 + Tb3*Tb3)/(r*t) - T*T/(r*s*t);
        
        double Sab = 0.0;
        for(Vector v:all) {
            Sab = Sab + v.getSum()*v.getSum();
        }
        Sab = Sab/t - T*T/(r*s*t) - Sa - Sb;
        
        double Se = St - Sa - Sb - Sab;
        
        System.out.println("总变差St:"+St);
        System.out.println("A的效应平方和Sa:"+Sa+"自由度"+(r-1));
        System.out.println("B的效应平方和Sb:"+Sb+"自由度"+(s-1));
        System.out.println("AB交互效应平方和Sab:"+Sab+"自由度"+(r-1)*(s-1));
        System.out.println("误差平方和:"+Se+"自由度"+r*s*(t-1));
        
        double Fa = (Sa/(r-1))/(Se/(r*s*(t-1)));
        System.out.println("Fa:"+Fa);
        FDistribution fad = new FDistribution(r-1,r*s*(t-1));
        double FaUpper = fad.inverseCumulativeProbability(0.95);
        System.out.println("Fa上限:"+FaUpper);
        if(Fa > FaUpper) {
            System.out.println("A的各个水平有明显差异");
        }
        
        double Fb = (Sb/(s-1))/(Se/(r*s*(t-1)));
        System.out.println("Fb:"+Fb);
        FDistribution fbd = new FDistribution(s-1,r*s*(t-1));
        double FbUpper = fbd.inverseCumulativeProbability(0.95);
        System.out.println("Fb上限:"+FbUpper);
        if(Fb > FbUpper) {
            System.out.println("B的各个水平有明显差异");
        }
        
        double Fab = (Sab/((r-1)*(s-1)))/(Se/(r*s*(t-1)));
        System.out.println("Fab:"+Fab);
        FDistribution fabd = new FDistribution((r-1)*(s-1),r*s*(t-1));
        double FabUpper = fabd.inverseCumulativeProbability(0.95);
        System.out.println("Fab上限:"+FabUpper);
        if(Fab > FabUpper) {
            System.out.println("A*B的各个水平有明显差异");
        }
    }
    
    private static double sumAll(List<Vector> list) {
        double sum = 0.0;
        for(Vector v:list) {
            sum = sum + v.getSum();
        }
        return sum;
    }
}
