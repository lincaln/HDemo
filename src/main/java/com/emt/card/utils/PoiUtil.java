package com.emt.card.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUtil {
	
	/**

	     * 生成excel
	     * @throws IOException 
	     */
@SuppressWarnings("resource")
public static byte[] createExcel(
		String[] wrwmclist,List<Map<String,String>> datalist,String name) throws IOException{
//创建HSSFWorkbook对象(excel的文档对象)  
HSSFWorkbook wb = new HSSFWorkbook();
// 建立新的sheet对象（excel的表单）
HSSFSheet sheet = wb.createSheet(name);
// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
HSSFRow row0 = sheet.createRow(0);
// 添加表头
for(int i = 0;i<wrwmclist.length;i++){
String wrwmc = wrwmclist[i];
row0.createCell(i).setCellValue(wrwmc);
}

// 在sheet里创建往下的行
for(int i= 0;i<datalist.size();i++){
	Map<String,String> db = datalist.get(i);
	HSSFRow row = sheet.createRow(i+1);
	for(int j = 0;j<wrwmclist.length;j++){
		String wrwmc = wrwmclist[j];
		row.createCell(j).setCellValue(db.get(wrwmc));
	}
}

/*String filename = "d:/workbook.xls";


           FileOutputStream out = new FileOutputStream(filename);
           wb.write(out);
           out.close();*/
ByteArrayOutputStream os = new ByteArrayOutputStream();
	wb.write(os);
byte[] content = os.toByteArray();
return content;
}


	


	 public static List<Map<String,String>> readExcel(Workbook wb,int index){
		 List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	        if(wb != null){
	        	//开始解析
	            Sheet sheet = wb.getSheetAt(index);     //读取sheet 0

	            int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
	            int lastRowIndex = sheet.getLastRowNum();
	            Row krow = sheet.getRow(sheet.getFirstRowNum());
	            List<String> keys=new ArrayList<String>();
	            if (krow != null) {
	                int firstCellIndex = krow.getFirstCellNum();
	                int lastCellIndex = krow.getLastCellNum();
	                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
	                    Cell cell = krow.getCell(cIndex);
	                    if (cell != null) {
	                        keys.add(cIndex, cell.toString());
	                    }
	                }
	            }
	            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
	                Row row = sheet.getRow(rIndex);
	                if (row != null) {
	                	Map<String,String> map=new LinkedHashMap<String,String>();
	                    int firstCellIndex = row.getFirstCellNum();
	                    int lastCellIndex = row.getLastCellNum();
	                    for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
	                        Cell cell = row.getCell(cIndex);
	                        if (cell != null&&keys.get(cIndex)!=null) {
	                            map.put(keys.get(cIndex), cell.toString());
	                        }
	                    }
	                    list.add(map);
	                }
	            }

	        }
			return list;
	 }
	 public static List<Map<String,String>> readExcel(InputStream is,String extString,int index){
		 Workbook wb = null;
	        try {
	            if(extString.indexOf("xlsx")>-1){
	            	wb = new XSSFWorkbook(is);
	            }else if(extString.indexOf("xls")>-1){
	            	wb = new HSSFWorkbook(is);
	            }
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return readExcel(wb, index);
	 }
    //读取excel
    static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

}
