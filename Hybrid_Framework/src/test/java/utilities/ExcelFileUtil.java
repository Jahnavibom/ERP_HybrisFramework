package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;
//constructor for reading excel path
public ExcelFileUtil(String ExcelPath)throws Throwable
{
	FileInputStream fi = new FileInputStream(ExcelPath);
	wb = WorkbookFactory.create(fi);
}
//method for counting no of rows
public int rowCount(String SheetName)
{
	return wb.getSheet(SheetName).getLastRowNum();
}
//method for reading cell data
public String getCellData(String SheetName,int row,int column)
{
	String data ="";
	if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
	{
		int celldata =(int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
		data =String.valueOf(celldata);
	}
	else
	{
		data = wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
				
	}
	return data;
	
}
//method for writing cell data
public void setCellData(String Sheetname,int row,int column,String status,String WriteExcel)throws Throwable
{
	//get sheet from wb
	Sheet ws = wb.getSheet(Sheetname);
	//get row from sheet
	Row rowNum =ws.getRow(row);
	//create cell in row
	Cell cell =rowNum.createCell(column);
	//write status into cell
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		//color with green
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
		
	}
	else if(status.equalsIgnoreCase("Fail"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		//color with RED
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("blocked"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		//Color with BLUE
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	FileOutputStream fo = new FileOutputStream(WriteExcel);
	wb.write(fo);
	
}
public static void main(String[] args) throws Throwable {
	//create object for class
	ExcelFileUtil xl = new ExcelFileUtil("D:/LoginData.xlsx");
	//count no of rows in a sheet
	int rc = xl.rowCount("Testng");
	System.out.println("no of rows are::"+rc);
	for(int i=1;i<=rc;i++)
	{
		String fname = xl.getCellData("Testng", i, 0);
		String mname = xl.getCellData("Testng", i, 1);
		String lname = xl.getCellData("Testng", i, 2);
		String eid = xl.getCellData("Testng", i, 3);
		System.out.println(fname+"      "+mname+"     "+lname+"   "+eid);
//	xl.setCellData("Testng", i, 4, "Pass", "D:/outputs.xlsx");
		//xl.setCellData("Testng", i, 4, "Fail", "D:/outputs.xlsx");
		xl.setCellData("Testng", i, 4, "Blocked", "D:/outputs.xlsx");
	}
	
}
}












