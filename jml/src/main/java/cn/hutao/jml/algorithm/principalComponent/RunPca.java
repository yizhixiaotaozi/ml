package cn.hutao.jml.algorithm.principalComponent;

import cn.hutao.jml.tools.Matrix;

public class RunPca {

    public static void main(String[] args) {

        Double[][] data = new Double[4][30];
        data[0] = new Double[]{148.0, 139.0, 160.0, 149.0, 159.0, 142.0, 153.0, 150.0, 151.0, 139.0,
                               140.0, 161.0, 158.0, 140.0, 137.0, 152.0, 149.0, 145.0, 160.0, 156.0,
                               151.0, 147.0, 157.0, 147.0, 157.0, 151.0, 144.0, 141.0, 139.0, 148.0};
        data[1] = new Double[] {41.0, 34.0, 49.0, 36.0, 45.0, 31.0, 43.0, 43.0, 42.0, 31.0,
                                29.0, 47.0, 49.0, 33.0, 31.0, 35.0, 47.0, 35.0, 47.0, 44.0,
                                42.0, 38.0, 39.0, 30.0, 48.0, 36.0, 36.0, 30.0, 32.0, 38.0};
        data[2] = new Double[] {72.0, 71.0, 77.0, 67.0, 80.0, 66.0, 76.0, 77.0, 77.0, 68.0,
                                64.0, 78.0, 78.0, 67.0, 66.0, 73.0, 82.0, 70.0, 74.0, 78.0,
                                73.0, 73.0, 68.0, 65.0, 80.0, 74.0, 68.0, 67.0, 68.0, 70.0};
        data[3] = new Double[] {78.0, 76.0, 86.0, 79.0, 86.0, 76.0, 83.0, 79.0, 80.0, 74.0,
                                74.0, 84.0, 83.0, 77.0, 73.0, 79.0, 79.0, 77.0, 87.0, 85.0,
                                82.0, 78.0, 80.0, 75.0, 88.0, 80.0, 76.0, 76.0, 73.0, 78.0};
        
        Matrix m = Matrix.getByCol(data);
        //标准化
        m.normalizeCol();
        System.out.println(m);
        //求协方差矩阵
        Matrix t = m.transpose();
        Matrix cov = t.multiply(m);
        cov = cov.divideNum(29);

        Matrix[] result = Jacobi2.jacobo(cov,0.00000001,20000);
        System.out.println(result[0]);
        System.out.println(result[1]);

    }
}
