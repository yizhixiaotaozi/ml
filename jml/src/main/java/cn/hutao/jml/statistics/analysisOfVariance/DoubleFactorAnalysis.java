package cn.hutao.jml.statistics.analysisOfVariance;

import org.apache.commons.math3.distribution.FDistribution;

import cn.hutao.jml.tools.Matrix;

/**
 * 双因素方差分析 两个因素没有相互影响
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
public class DoubleFactorAnalysis {

    public static void main(String[] args) {
        //在不同时间、不同地点测的空气颗粒物数量 A时间，B地点
        Double[] a1 = new Double[] {76.0,67.0,81.0,56.0,51.0};// 1月
        Double[] a2 = new Double[] {82.0,69.0,96.0,59.0,70.0};// 2月
        Double[] a3 = new Double[] {68.0,59.0,67.0,54.0,42.0};// 3月
        Double[] a4 = new Double[] {63.0,56.0,64.0,58.0,37.0};// 4月
        
        Double[][] data = new Double[][] {a1,a2,a3,a4};
        Matrix matrix = Matrix.getByRow(data);
        
        double T = 0.0;
        double St = 0.0;
        for(Double[] row:data) {
            for(Double d:row) {
                St = St + d*d;
                T = T + d;
            }
        }
        St = St - T*T/(matrix.getRows()*matrix.getCols());
        System.out.println("St:"+St);
        
        double Sa = 0.0;
        for(Double[] row:data) {
           double sum = 0;
           for(Double d:row) {
               sum = sum + d;
           }
           Sa = Sa + sum*sum;
        }
        Sa = Sa/matrix.getCols() - T*T/(matrix.getRows()*matrix.getCols());
        System.out.println("Sa:"+Sa+"自由度"+(matrix.getRows()-1));
        
        double Sb = 0.0;
        for(Double[] col:matrix.getColView()) {
           double sum = 0;
           for(Double d:col) {
               sum = sum + d;
           }
           Sb = Sb + sum*sum;
        }
        Sb = Sb/matrix.getRows() - T*T/(matrix.getRows()*matrix.getCols());
        System.out.println("Sb:"+Sb+"自由度"+(matrix.getCols()-1));

        double Se = St - Sa - Sb;
        System.out.println("Se:"+Se+"自由度"+(matrix.getCols()-1)*(matrix.getRows()-1));

        
        double Fa = (Sa/(matrix.getRows()-1))/(Se/((matrix.getCols()-1)*(matrix.getRows()-1)));
        System.out.println("Fa:"+Fa);
        FDistribution fad = new FDistribution(matrix.getRows() -1,(matrix.getCols()-1)*(matrix.getRows()-1));
        if(Fa > fad.inverseCumulativeProbability(0.95)) {
            System.out.println("不同时间有明显差异");
        }
        
        double Fb = (Sb/(matrix.getCols()-1))/(Se/((matrix.getCols()-1)*(matrix.getRows()-1)));
        System.out.println("Fb:"+Fb);
        FDistribution fbd = new FDistribution(matrix.getCols() -1,(matrix.getCols()-1)*(matrix.getRows()-1));
        if(Fb > fbd.inverseCumulativeProbability(0.95)) {
            System.out.println("不同地点有明显差异");
        }

    }
}
