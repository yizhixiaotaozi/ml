package cn.hutao.jml.algorithm.principalComponent;

import java.math.RoundingMode;
import java.text.NumberFormat;

import cn.hutao.jml.tools.Matrix;
import cn.hutao.jml.tools.Vector;

public class Jacobi2 {
    
    /**
     * jacobi求特征矩阵
     * @param S 输入矩阵
     * @param e 阈值
     * @param maxTry 最大尝试次数
     * @return
     */
    public static Matrix[] jacobo(Matrix S,double e,int maxTry) {
        if(S.getCols() != S.getRows()) {
            throw new RuntimeException("非方阵，无法计算");
        }
        int tryCount = 0;
        Matrix identity = Matrix.getIdentity(S.getRows());
        return recursive(S,identity,e,tryCount,maxTry);
    }

    private static Matrix[] recursive(Matrix S, Matrix A,double e,int tryCount, int maxTry) {
        if(tryCount +1 > maxTry) {
            System.out.println("迭代了"+tryCount+"次");
            //已达到最大迭代次数
            return new Matrix[] {S,A};
        }
        //从原矩阵的非对角元素中，找到绝对值最大的元素
        int i = 0;//行标
        int j = 0;//列标
        Double maxElement = 0.0;
        for(int r = 0;r<S.getRows();r++) {
            for(int l = 0;l<S.getCols();l++) {
                if(r != l) {
                    if(Math.abs(S.getCell(r,l)) > Math.abs(maxElement)) {
                        i = r;
                        j = l;
                        maxElement = S.getCell(r,l);
                    }
                }
            }
        }
        if(Math.abs(maxElement) <= e) {
            System.out.println("达到最小精度，迭代"+tryCount+"次");
            //已经没有迭代的价值
            return new Matrix[] {S,A};
        }
        //计算旋转角度
        double angle = Math.atan2(2.0*S.getCell(i, j),S.getCell(j, j)-S.getCell(i, i));
        double cos = Math.cos(0.5*angle);
        double sin = Math.sin(0.5*angle);
        if(S.getCell(j, j) - S.getCell(i, i) < 0) {
            sin = -sin;
        }
            
        //计算Upq
        Matrix G = Matrix.getIdentity(S.getRows());
        G.setCell(i, i, cos);
        G.setCell(i, j, sin);
        G.setCell(j, i, -sin);
        G.setCell(j, j, cos);
        
        // 特征矩阵迭代
        A = A.multiply(G);
        // 当前矩阵进入下一轮
        S = nextM(S,sin,cos,i,j);
        return recursive(S,A,e,tryCount,maxTry);
    }

    private static Matrix nextM(Matrix S, double sin, double cos,int i,int j) {
        Matrix next = S.copy();
        next.setCell(i, i, cos*cos*S.getCell(i, i) - 2*sin*cos*S.getCell(i, j) + sin*sin*S.getCell(j, j));
        next.setCell(j, j, sin*sin*S.getCell(i, i) + 2*sin*cos*S.getCell(i, j) + cos*cos*S.getCell(j, j));
        next.setCell(i, j, (cos*cos - sin*sin)*S.getCell(i, j) + sin*cos*(S.getCell(i, i) - S.getCell(j, j)));
        next.setCell(j, i, next.getCell(i, j));
        
        for(int k=0;k<S.getCols();k++) {
            if(i == k || j == k) {
                continue;
            }
            next.setCell(i, k, cos*S.getCell(i, k) - sin*S.getCell(j, k));
            next.setCell(k, i, next.getCell(i, k));
        }
        
        for(int k=0;k<S.getCols();k++) {
            if(i == k || j == k) {
                continue;
            }
            next.setCell(j, k, sin*S.getCell(i, k) + cos*S.getCell(j, k));
            next.setCell(k, j, next.getCell(j, k));
        }
        return next;
    }
    
    public static void main(String[] args) {
        Double[][] data = new Double[4][4];
        data[0] = new Double[]{4.0,-30.0,60.0,-35.0};
        data[1] = new Double[] {-30.0,300.0,-675.0,420.0};
        data[2] = new Double[] {60.0,-675.0,1620.0,-1050.0};
        data[3] = new Double[] {-35.0,420.0,-1050.0,700.0};
        Matrix m = Matrix.getByCol(data);
        
        Matrix[] ret = jacobo(m,0.000000001,19);
        System.out.println(ret[0]);
        System.out.println(ret[1]);
        
        
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(6);
        f.setRoundingMode(RoundingMode.HALF_UP);
        
        Vector v = new Vector(ret[1].getCol(0));
        Vector v1 = new Vector(ret[1].getCol(1));
        Vector v2 = new Vector(ret[1].getCol(2));
        Vector v3 = new Vector(ret[1].getCol(3));
        System.out.println(f.format(v.multiply(v1)));
        System.out.println(f.format(v.multiply(v2)));
        System.out.println(f.format(v.multiply(v3)));
        System.out.println(f.format(v1.multiply(v2)));
        System.out.println(f.format(v1.multiply(v3)));
        System.out.println(f.format(v2.multiply(v3)));
    }
}
