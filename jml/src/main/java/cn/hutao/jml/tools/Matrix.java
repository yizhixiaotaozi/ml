package cn.hutao.jml.tools;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;

public final class Matrix implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3011214028752701127L;

    private Matrix() {};
    
    public static Matrix getByCol(Double[][] data){
        Matrix matrix = new Matrix();
        matrix.cols = data.length;
        matrix.rows = data[0].length;
        matrix.setColView(data);
        return matrix;
    }
    
    public static Matrix getByRow(Double[][] data){
        Matrix matrix = new Matrix();
        matrix.rows = data.length;
        matrix.cols = data[0].length;
        matrix.setRowView(data);
        return matrix;
    }
    
    public static Matrix getIdentity(int n) {
        Double[][] data = new Double[n][n];
        for(int i = 0;i<n;i++) {
            for(int j = 0;j<n;j++) {
                if(i == j) {
                    data[i][j] = 1.0;
                }else {
                    data[i][j] = 0.0;
                }
            }
        }
        return getByRow(data);
    }

    private Double[][] colView;
    
    private Double[][] rowView;

    private int        cols = 0;

    private int        rows = 0;
    
    
    public void setCell(int row,int col,double newValue) {
        this.rowView[row][col] = newValue;
        this.colView[col][row] = newValue;
    }
    
    public Double getCell(int row,int col) {
        return this.rowView[row][col];
    }
    
    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public Double[][] getColView() {
        return colView;
    }
    
    public void setColView(Double[][] colView) {
        this.colView = colView;
        this.rowView = new Double[this.rows][this.cols];
        for(int i = 0;i<cols;i++) {
            for(int j = 0;j<rows;j++) {
                rowView[j][i] = colView[i][j];
            }
        }
    }
    
    public Double[][] getRowView() {
        return rowView;
    }

    
    public void setRowView(Double[][] rowView) {
        this.rowView = rowView;
        this.colView = new Double[this.cols][this.rows];
        for(int i = 0;i<rows;i++) {
            for(int j = 0;j<cols;j++) {
                colView[j][i] = rowView[i][j];
            }
        }
    }

    /**
     * 将该矩阵中心化
     */
    public void centralizeCol() {
        for (Double[] col : this.colView) {
            Double sum = 0.0;
            for (Double d : col) {
                sum = sum + d;
            }
            if (sum.compareTo(0.0) == 0) {
                continue;
            }
            // 中心化
            double avg = sum / col.length;
            for (int i = 0; i < col.length; i++) {
                col[i] = col[i] - avg;
            }
        }
        this.setColView(colView);
    }
    
    public void normalizeCol() {
        for (Double[] col : this.colView) {
            Double sum = 0.0;
            for (Double d : col) {
                sum = sum + d;
            }
            if (sum.compareTo(0.0) == 0) {
                continue;
            }
            // 标准化
            double avg = sum / col.length;
            Double eSum = 0.0;
            for (int i = 0; i < col.length; i++) {
                eSum = eSum + (col[i] - avg)*(col[i] - avg);
            }
            double e = Math.sqrt(eSum)/(col.length - 1.0);
            
            for (int i = 0; i < col.length; i++) {
                col[i] = (col[i] - avg)/e;
            }
        }
        this.setColView(colView);
    }

    /**
     * 将该矩阵转置
     * 
     * @return
     */
    public Matrix transpose() {
        Double[][] newData = new Double[rows][cols];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                newData[j][i] = this.colView[i][j];
            }
        }
        return getByCol(newData);
    }

    /**
     * 相乘
     * 
     * @param m
     * @return
     */
    public Matrix multiply(Matrix m) {
        if (m.rows != this.cols) {
            throw new RuntimeException("矩阵无法相乘");
        }
        Double[][] newData = new Double[m.cols][this.rows];
        for (int i = 0; i < m.cols; i++) {
            Double[] col = m.colView[i];

            for (int j = 0; j < this.rows; j++) {
                double sum = 0.0;

                for (int r = 0; r < col.length; r++) {
                    sum = sum + col[r] * this.colView[r][j];
                }
                newData[i][j] = sum;
            }
        }
        return getByCol(newData);
    }

    public Matrix divideNum(double n) {
        Double[][] newData = new Double[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                newData[i][j] = colView[i][j] / n;
            }
        }
        return getByCol(newData);
    }

    @Override
    public String toString() {
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(6);
        f.setRoundingMode(RoundingMode.HALF_UP);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(f.format(rowView[i][j])).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 复制一个新的矩阵
     * @return
     */
    public Matrix copy() {
        Double[][] newData = new Double[cols][rows];
        for(int i=0;i<cols;i++) {
            for(int j=0;j<rows;j++) {
                newData[i][j] = this.colView[i][j];
            }
        }
        return getByCol(newData);
    }

    public Double[] getCol(int c) {
        return Arrays.copyOf(colView[c], rows);
    }

    
    public Double[] getRow(int r) {
        return Arrays.copyOf(rowView[r], cols);
    }
}
