package com.simulador.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadToExcel {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	public static Properties allProperties;
	private static org.apache.poi.ss.usermodel.Cell Cell;

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 0;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			// System.out.println(totalRows + "," + totalCols);
			tabArray = new String[totalRows][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellDataDDT(i, j);
					// System.out.println(tabArray[ci][cj]);
				}
			}
		} catch (FileNotFoundException e) {
			throw new Exception(
					"Class ExcelUtils | Method getTableArray | Exception desc: Excel not found: " + e.getMessage());
		} catch (IOException e) {
			throw new Exception(
					"Class ExcelUtils | Method getTableArray | Exception desc: Could not read the Excel sheet: "
							+ e.getMessage());
		}
		return (tabArray);
	}

	@SuppressWarnings("deprecation")
	public static String getCellDataDDT(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = Cell.getCellType();

			return (dataType == 0) ? String.valueOf(Cell.getNumericCellValue())
					: (dataType == 3) ? "" : Cell.getStringCellValue();
		} catch (Exception e) {
			throw new Exception("Class ExcelUtils | Method getCellDataDDT | Exception desc: " + e.getMessage());
		}
	}

	public static void writeExcel(String filePath, String fileName, String sheetName, String[] dataToWrite)
			throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath + "\\" + fileName);

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook guru99Workbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			guru99Workbook = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			guru99Workbook = new HSSFWorkbook(inputStream);

		}

		// Read excel sheet by sheet name

		Sheet sheet = guru99Workbook.getSheet(sheetName);

		// Get the current count of rows in excel file

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Get the first row from the sheet

		Row row = sheet.getRow(0);

		// Create a new row and append it at last of sheet

		Row newRow = sheet.createRow(rowCount + 1);

		// Create a loop over the cell of newly created Row

		for (int j = 0; j < row.getLastCellNum(); j++) {

			// Fill data in row

			Cell cell = newRow.createCell(j);

			cell.setCellValue(dataToWrite[j]);

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
