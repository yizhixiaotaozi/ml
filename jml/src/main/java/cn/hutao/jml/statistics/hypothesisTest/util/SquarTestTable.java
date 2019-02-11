package cn.hutao.jml.statistics.hypothesisTest.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 卡方拟合检验计算表
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * tao.hu 	1.0  		2019年2月11日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class SquarTestTable {

    public SquarTestTable(String key, Integer f, Integer n, double p){
        this.key = key;
        this.f = f;
        this.n = n;
        this.p = p;
        keySet = new HashSet<String>();
        keySet.add(key);

        np = n * p;
        f2DivNp = f * f / (n * p);
    }

    private String      key;

    private Set<String> keySet;

    private Integer     f;       // 频次

    private Integer     n;       // 样本总量

    private double      p;       // 假设的概率

    private double      np;      // n*p

    private double      f2DivNp; // f*f/(n*p)

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<String> getKeySet() {
        return keySet;
    }

    public void setKeySet(Set<String> keySet) {
        this.keySet = keySet;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public double getNp() {
        return np;
    }

    public void setNp(double np) {
        this.np = np;
    }

    public double getF2DivNp() {
        return f2DivNp;
    }

    public void setF2DivNp(double f2DivNp) {
        this.f2DivNp = f2DivNp;
    }

}
