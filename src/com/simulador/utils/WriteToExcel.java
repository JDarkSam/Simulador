package com.simulador.utils;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToExcel {

	public void writeExcel(String filePath, String fileName, String sheetName, String[] dataToWrite)
			throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath + "\\" + fileName);

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		XSSFWorkbook guru99Workbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			guru99Workbook = new XSSFWorkbook(inputStream);

		}

		// Read excel sheet by sheet name

		XSSFSheet sheet = guru99Workbook.getSheet(sheetName);

		// Get the current count of rows in excel file

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Get the first row from the sheet

		XSSFRow row = sheet.getRow(0);

		// Create a new row and append it at last of sheet

		XSSFRow newRow = sheet.createRow(rowCount + 1);

		// Create a loop over the cell of newly created Row

		for (int j = 0; j < row.getLastCellNum(); j++) {

			// Fill data in row
			XSSFCell cell = newRow.createCell(j);
			cell.setCellValue(dataToWrite[j]);

			if (j == row.getLastCellNum() - 1) {
				XSSFCellStyle style = guru99Workbook.createCellStyle();
				XSSFColor myColor = new XSSFColor(java.awt.Color.GREEN);
				style.setFillForegroundColor(myColor);
				style.setFillBackgroundColor(myColor);
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cell.setCellStyle(style); 
			}
		}

		// Close input stream

		inputStream.close();

		// Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file

		guru99Workbook.write(outputStream);

		// close output stream

		outputStream.close();

	}

}
