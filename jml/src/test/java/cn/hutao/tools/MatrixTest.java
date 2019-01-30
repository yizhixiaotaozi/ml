package cn.hutao.tools;

import cn.hutao.jml.tools.Matrix;

public class MatrixTest {

    public static void main(String[] args) {
        Double[][] data = new Double[3][2];
        data[0] = new Double[] {1.0,4.0};
        data[1] = new Double[] {2.0,5.0};
        data[2] = new Double[] {3.0,6.0};
        Matrix m = Matrix.getByCol(data);
        System.out.println(m);
        System.out.println(m.transpose());
        
        Double[][] data2 = new Double[2][3];
        data2[0] = new Double[] {1.0,2.0,3.0};
        data2[1] = new Double[] {4.0,5.0,6.0};
        Matrix m2 = Matrix.getByCol(data2);
        System.out.println(m.multiply(m2));
    }
}
