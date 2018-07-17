package jk28_web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class ExcelTest {
	
	@Test
	public void testPoi() throws IOException{
		//1,创建一个工作薄
		Workbook wb = new HSSFWorkbook();
		//2，创建工作表
		Sheet sheet = wb.createSheet();
		//3,创建行对象
		Row row = sheet.createRow(3);
		//4,创建单元格
		Cell cell = row.createCell(4);
		//5,设置单元格内容
		cell.setCellValue("掉渣土");
		//6,设置单元格的样式
		CellStyle cellstyle = wb.createCellStyle();
		Font font = wb.createFont();
		
		font.setFontHeightInPoints((short)28);
		font.setFontName("华文楷体");
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		OutputStream os = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\aa.xls");
		wb.write(os);
		os.close();
	}
}
