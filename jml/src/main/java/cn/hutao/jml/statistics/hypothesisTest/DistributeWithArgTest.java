package cn.hutao.jml.statistics.hypothesisTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import cn.hutao.jml.statistics.hypothesisTest.util.SquarTestTable;

/**
 * 有参数的分布假设检验
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
public class DistributeWithArgTest {

    public static void main(String[] args) {
        // 观察粒子打到屏幕上的次数100次。问次数是否服从均值未知的泊松分布
        List<SquarTestTable> list = new ArrayList<SquarTestTable>();
        int n = 100;
        SquarTestTable A0 = new SquarTestTable("A0:{X=0}",1,n,0.015);
        list.add(A0);
        SquarTestTable A1 = new SquarTestTable("A1:{X=1}",5,n,0.063);
        list.add(A1);
        SquarTestTable A2 = new SquarTestTable("A2:{X=2}",16,n,0.132);
        list.add(A2);
        SquarTestTable A3 = new SquarTestTable("A3:{X=3}",17,n,0.185);
        list.add(A3);
        SquarTestTable A4 = new SquarTestTable("A4:{X=4}",26,n,0.194);
        list.add(A4);
        SquarTestTable A5 = new SquarTestTable("A5:{X=5}",11,n,0.163);
        list.add(A5);
        SquarTestTable A6 = new SquarTestTable("A6:{X=6}",9,n,0.114);
        list.add(A6);
        SquarTestTable A7 = new SquarTestTable("A7:{X=7}",9,n,0.069);
        list.add(A7);
        SquarTestTable A8 = new SquarTestTable("A8:{X=8}",2,n,0.036);
        list.add(A8);
        SquarTestTable A9 = new SquarTestTable("A9:{X=9}",1,n,0.017);
        list.add(A9);
        SquarTestTable A10 = new SquarTestTable("A10:{X=10}",2,n,0.007);
        list.add(A10);
        SquarTestTable A11 = new SquarTestTable("A11:{X=11}",1,n,0.003);
        list.add(A11);
        SquarTestTable A12 = new SquarTestTable("A12:{X>=12}",0,n,0.002);
        list.add(A12);
        //先求未知参数入的估计量。泊松分布刚好是均值
        int sum = 0;
        for(int i = 0;i<list.size();i++) {
            sum = sum + list.get(i).getF()*(i);
        }
        System.out.println("未知参数的估计量是:"+(sum+0.0)/(n+0.0));
        //合并记录保证 np >= 5
        list = merge(list);
        printTable(list);
        //计算统计量
        double x2 = 0.0;
        for(int i = 0;i<list.size();i++) {
            x2 = list.get(i).getF2DivNp() + x2;
        }
        x2 = x2 - n;
        //构造分布 有一个未知参数，所以再减1
        ChiSquaredDistribution d = new ChiSquaredDistribution(list.size() - 1 - 1);
        double upper = d.inverseCumulativeProbability(0.95);
        System.out.println(upper);
        System.out.println(x2);
        if(x2 > upper) {
            System.out.println("拒绝H0，认为不服从泊松分布");
        }else {
            //但是不能说服从4.2的泊松分布
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
