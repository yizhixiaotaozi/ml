package cn.hutao.jml.tools;

import java.io.Serializable;

public class Vector implements Serializable {

    private static final long serialVersionUID = 6784607785011063500L;

    private Double[]          data;

    public Vector(Double[] data){
        this.data = data;
    }

    public Double[] getData() {
        return data;
    }

    public void setData(Double[] data) {
        this.data = data;
    }

    /**
     * 返回合
     * 
     * @return
     */
    public Double getSum() {
        double sum = 0.0;
        for (Double d : data) {
            sum = sum + d;
        }
        return sum;
    }

    /**
     * 获取样本均值
     * 
     * @return
     */
    public Double getAvg() {
        if (data.length == 0) {
            return 0.0;
        }
        double sum = 0;
        for (Double d : data) {
            sum = sum + d;
        }
        return sum / data.length;
    }

    /**
     * 获取样本方差
     * 
     * @return
     */
    public Double getSampleVariance() {
        if (data.length == 0) {
            return 0.0;
        }
        if (data.length == 1) {
            return 0.0;
        }
        double avg = getAvg();
        double sum = 0.0;
        for (Double d : data) {
            sum = sum + (d - avg) * (d - avg);
        }
        return sum / (data.length - 1);
    }

    /**
     * 获取样本标准差
     * 
     * @return
     */
    public Double getSampleStandardDeviation() {
        return Math.sqrt(getSampleVariance());
    }

    public Integer getLength() {
        return data.length;
    }

    public Double multiply(Vector other) {
        if (other.data.length != this.data.length) {
            throw new RuntimeException("不能相乘");
        }
        double sum = 0;
        for (int i = 0; i < this.data.length; i++) {
            sum = sum + other.data[i] * this.data[i];
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.data.length; i++) {
            sb.append(this.data[i]);
            if (i != this.data.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
