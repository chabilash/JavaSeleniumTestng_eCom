package com.webstar.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviderClass {
	
	@DataProvider
	public String[][] getData() throws Exception {
		
		File excelFile = new File("./src/main/resources/TestData.xlsx");
		System.out.println(excelFile.exists());
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workBook.getSheet("Sheet1");
		System.out.println(sheet.getPhysicalNumberOfRows());
		System.out.println(sheet.getLastRowNum());
		int noOfRows    = sheet.getPhysicalNumberOfRows();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		
		//Creating Two-dimensional String array to pass as a DataProvider
		String[][] data = new String[noOfRows-1][noOfColumns];
		for (int i = 0; i < noOfRows-1; i++) {
			for (int j = 0; j < noOfColumns ; j++) {
//				System.out.println(sheet.getRow(i).getCell(j).getStringCellValue());
				DataFormatter df = new DataFormatter();
//				System.out.println(df.formatCellValue(sheet.getRow(i).getCell(j)));
				data[i][j] = df.formatCellValue(sheet.getRow(i+1).getCell(j));
			}
			System.out.println();
		}
		
		
		workBook.close();
		fis.close();
		
		return data;
		
	}
	
}
