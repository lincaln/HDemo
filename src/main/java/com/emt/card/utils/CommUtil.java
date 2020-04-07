package com.emt.card.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CommUtil {
	/**
	 * 获取订单号
	 * @return
	 */
	public static String getOrderNo(){
	 String orderNo=new Date().getTime()+"";
	 for (int i = 0; i < 6; i++) {
		Random random=new Random();
		int number=random.nextInt(10);
		orderNo+=number;
	 }
	 return orderNo;
	}
	public static String getOrderNo(int mun){
		 String orderNo=new Date().getTime()+"";
			orderNo+=getRandomNo(mun);
		 return orderNo;
		}
	public static String userCode(Date date){
		String ds=DateUtil.getDateFormat(date, "yyMMddHHmm");
		String uc=ds+getRandomNo(2);
		return uc;
	}
	
	public static byte[] input2byte(InputStream inStream,int size)  
	        throws IOException {
	    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
	    byte[] buff = new byte[size];  
	    int rc = 0;  
	    while ((rc = inStream.read(buff, 0, size)) > 0) {  
	        swapStream.write(buff, 0, rc);  
	    }  
	    byte[] in2b = swapStream.toByteArray();  
	    return in2b;  
	} 
	public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }  
	/**
	 * 获得数据数字字符串
	 * @param mun
	 * @return
	 */
	public static String getRandomNo(int mun){
		 String no="";
		 for (int i = 0; i <mun; i++) {
			Random random=new Random();
			int number=random.nextInt(10);
			no+=number;
		 }
		 return no;
		}
	
	public static String arrayToString(List<?> list,String fg){
		String str=list.get(0).toString();
		for (int i = 1; i <list.size(); i++) {
			str+=fg+list.get(i).toString();
		}
		return str;
	}
	
	public static boolean isafter(String date,Integer hsState ,Integer ysState) throws ParseException {
		if(date==null)return true;
		  Date date1 = DateUtil.StringFormatDate(date, DateUtil.dayFormat);
		  Date date2=DateUtil.convertStringToDate(DateUtil.dayFormat,DateUtil.getNow());
		  return date1.before(date2)&&hsState==3&&ysState==3;
	}
	
	/**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale, 
             int roundingMode) {   
        BigDecimal bd = new BigDecimal(value);   
        bd = bd.setScale(scale, roundingMode);   
        double d = bd.doubleValue();   
        bd = null;   
        return d;   
    }   


     /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2).doubleValue(); 
    } 


    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    } 

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.multiply(bd2).doubleValue(); 
    } 


    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double div(double d1,double d2,int scale){ 
        //  当然在此之前，你要判断分母是否为0，   
        //  为0你可以根据实际需求做相应的处理 

        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide 
               (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    }

	public static Date getReferralTime(String visitDateTime, Integer followCycle) {
		Date visDate = new Date();
		Calendar c = Calendar.getInstance();   
        try {
			visDate=DateUtil.convertStringToDate(DateUtil.dateFormat,visitDateTime+":00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        c.setTime(visDate);//设置当前日期  
        if(followCycle==1){
        c.add(Calendar.DATE, 7);
        }
        if(followCycle==2){
            c.add(Calendar.DATE, 14);
        }
        if(followCycle==3){
            c.add(Calendar.MONTH, 1); 
        }
        if(followCycle==4){
            c.add(Calendar.MONTH, 2); 
        }
        if(followCycle==5){
            c.add(Calendar.MONTH, 3);  
        }
        Date date = c.getTime(); //日期处理结果 
        return date;
	}


	/**
	 * 随机字符串
	 */
		public static String randomString(int length) {
			String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			Random random = new Random();
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int num = random.nextInt(62);
				buf.append(str.charAt(num));
			}
			return buf.toString();
		}

	public static String toA(String string, String string2) {
		String a="<a href='"+string2+"'>"+string+"</a>";
		return a;
	}


	public static BigDecimal getWeeklyData(BigDecimal thisWeek, BigDecimal lastWeek) {
		return thisWeek.divide(lastWeek,4, RoundingMode.HALF_UP).subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
	}


}
