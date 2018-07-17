package cn.itcast.jk.utils;


// ����һ������ѧ�����class ����ͼ���ɵ�ʱ����Ҫ�õ���

import java.math.BigDecimal;
import java.util.Random;
public class Arith {
	//Ĭ�ϳ������㾫��
    private static final int DEF_DIV_SCALE = 10;
     /**
       * �ṩ��ȷ�ļӷ����㡣
       * @param v1 ������
       * @param v2 ����
       * @return ���������ĺ�
       */
      public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
      } 
      /**
       * �ṩ��ȷ�ļ������㡣
       * @param v1 ������
       * @param v2 ����
       * @return ���������Ĳ�
       */
      public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
      }
      /**
       * �ṩ��ȷ�ĳ˷����㡣
       * @param v1 ������
       * @param v2 ����
       * @return ���������Ļ�
       */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
      /**
       * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
       * С�����Ժ�10λ���Ժ�������������롣
       * @param v1 ������
       * @param v2 ����
       * @return ������������
       */
      public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
      }
      /**
       * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
       * �����ȣ��Ժ�������������롣
       * @param v1 ������
       * @param v2 ����
       * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
       * @return ������������
       */
      public static double div(double v1,double v2,int scale){
        if(scale<0){
          throw new IllegalArgumentException(
          "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
      }
      /**
       * �ṩ��ȷ��С��λ�������봦��
       * @param v ��Ҫ�������������
       * @param scale С���������λ
       * @return ���������Ľ��
       */
      public static double round(double v,int scale){
        if(scale<0){
          throw new IllegalArgumentException(
          "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
      }
      
      //���������λ by tony 20111006
      public int round(int i1,int i2){
    	  int modi = 0;
    	  modi = i1 % i2;
    	  int i = i1/i2;
    	  if(modi==0){
    		  return i;
    	  }else{
    		  return i+1;
    	  }
      }
      
      //ʹ��ʱһ��Ҫע�����С�����ɳ�����Χ
      public int pow(int i1,int i2){
    	  double d1 = (double)i1;
    	  double d2 = (double)i2;
    	  return (int)java.lang.Math.pow(d1, d2);
      }
      
      //�Ը�����Ŀ����0��ʼ����Ϊ1���������н�������
      public static int[] getSequence(int maxnum) {
          int[] sequence = new int[maxnum];
          for(int i = 0; i < maxnum; i++){
              sequence[i] = i;
          }
          Random random = new Random();
          for(int i = 0; i < maxnum; i++){
              int p = random.nextInt(maxnum);
              int tmp = sequence[i];
              sequence[i] = sequence[p];
              sequence[p] = tmp;
          }
          random = null;
          return sequence;
      } 
      
      public static void main(String[] agrs){
    	  Arith arith = new Arith();
    	  int[] i = arith.getSequence(300);
    	  for(int n=0;n<i.length;n++){
    		  System.out.println(i[n]);
    	  }
      }
}
