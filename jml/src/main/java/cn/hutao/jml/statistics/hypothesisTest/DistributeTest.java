package cn.hutao.jml.statistics.hypothesisTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import cn.hutao.jml.statistics.hypothesisTest.util.SquarTestTable;

/**
 * 没有未知参数的分布假设检验
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
public class DistributeTest {

    public static void main(String[] args) {
        // 某个地区在夏季一个月中，100个气象站报告的下雨次数。问下雨次数是否服从均值为1的泊松分布
        List<SquarTestTable> list = new ArrayList<SquarTestTable>();
        int n = 100;
        SquarTestTable A0 = new SquarTestTable("A0:{X=0}",22,n,Math.pow(Math.E, -1));
        list.add(A0);
        SquarTestTable A1 = new SquarTestTable("A1:{X=1}",37,n,Math.pow(Math.E, -1));
        list.add(A1);
        SquarTestTable A2 = new SquarTestTable("A2:{X=2}",20,n,Math.pow(Math.E, -1)/2.0);
        list.add(A2);
        SquarTestTable A3 = new SquarTestTable("A3:{X=3}",13,n,Math.pow(Math.E, -1)/6.0);
        list.add(A3);
        SquarTestTable A4 = new SquarTestTable("A4:{X=4}",6,n,Math.pow(Math.E, -1)/24.0);
        list.add(A4);
        SquarTestTable A5 = new SquarTestTable("A5:{X=5}",2,n,Math.pow(Math.E, -1)/120.0);
        list.add(A5);
        double p6 = 1 - Math.pow(Math.E, -1) - Math.pow(Math.E, -1) - Math.pow(Math.E, -1)/2.0 - Math.pow(Math.E, -1)/6.0 - Math.pow(Math.E, -1)/24.0 - Math.pow(Math.E, -1)/120.0;
        SquarTestTable A6 = new SquarTestTable("A6:{X>=6}",0,n,p6);
        list.add(A6);
        
        //合并记录保证 np >= 5
        list = merge(list);
        printTable(list);
        //计算统计量
        double x2 = 0.0;
        for(int i = 0;i<list.size();i++) {
            x2 = list.get(i).getF2DivNp() + x2;
        }
        x2 = x2 - n;
        //构造分布
        ChiSquaredDistribution d = new ChiSquaredDistribution(list.size() - 1);
        double upper = d.inverseCumulativeProbability(0.95);
        System.out.println(upper);
        System.out.println(x2);
        if(x2 > upper) {
            System.out.println("拒绝H0，认为不服从泊松分布");
        }else {
            System.out.println("接受H0，认为服从泊松分布");
        }
    }

    private static void printTable(List<SquarTestTable> list) {
        for(SquarTestTable t:list) {
            System.out.println("key:"+t.getKey()+"\tnp:"+t.getNp()+"\tf2/np:"+t.getF2DivNp());
        }
    }

    private static List<SquarTestTable> merge(List<SquarTestTable> list) {
        if(list.size() == 1) {
            SquarTestTable data = list.get(0);
            if(data.getNp() < 5.0) {
                throw new RuntimeException("不能进行检验");
            }
        }
        SquarTestTable one = null;
        SquarTestTable two = null;
        int index = 0;
        for(int i=0;i<list.size();i++) {
            SquarTestTable data = list.get(i);
            if(data.getNp() < 5.0) {
                if(i != list.size() -1) {
                    //不是最后一项，和下一项合并
                    one = data;
                    two = list.get(i+1);
                    index = i;
                    break;
                }else {
                    if(list.size() >= 2) {
                        //和前一项合并
                        one = data;
                        two = list.get(i-1);
                        index = i;
                        break;
                    }else {
                        throw new RuntimeException("不能进行检验");
                    }
                }
            }
        }
        if(one != null) {
            String key = one.getKey() + "," + two.getKey();
            int f = one.getF() + two.getF();
            double p = one.getP() + two.getP();
            double np = one.getNp() + two.getNp();
            
            one.getKeySet().addAll(two.getKeySet());
            Set<String> keySet = one.getKeySet();
            SquarTestTable merged = new SquarTestTable(key,f,one.getN(),p);
            merged.setKeySet(keySet);
            merged.setNp(np);
            
            if(index == list.size() -1) {
                //往前合并
                list = list.subList(0, index-1);
                list.add(merged);
            }else {
                List<SquarTestTable> newList = new ArrayList<SquarTestTable>();
                for(int i = 0;i<list.size();i++) {
                    if(i != index && i != index + 1) {
                        newList.add(list.get(i));
                    }
                    if(i == index) {
                        newList.add(merged);
                    }
                }
                list = newList;
            }
            return merge(list);
        }
        //没有需要合并的
        return list;
    }
}
