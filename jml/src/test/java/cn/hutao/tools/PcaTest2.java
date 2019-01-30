package cn.hutao.tools;

import cn.hutao.jml.tools.Matrix;

public class PcaTest2 {
    public static void main(String[] args) {
        Double[][] data = new Double[2][10];
        data[0] = new Double[] {2.5,0.5,2.2,1.9,3.1,2.3,2.0,1.0,1.5,1.1};
        data[1] = new Double[] {2.4,0.7,2.9,2.2,3.0,2.7,1.6,1.1,1.6,0.9};
        Matrix m = Matrix.getByCol(data);
        System.out.println(m);
        m.centralizeCol();
        System.out.println(m);
        
        Matrix t = m.transpose();
        Matrix cov = t.multiply(m);
        System.out.println(cov.divideNum(9.0));
    }
    
}
