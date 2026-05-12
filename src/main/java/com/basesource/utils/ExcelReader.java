package com.basesource.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private static FileInputStream fis;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;

	private static HSSFWorkbook xlsworkbook;
	private static HSSFSheet xlssheet;
	private static HSSFRow xlsrow;
	private static HSSFCell xlscell;

	public ExcelReader(String filePath) throws IOException {
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(0);
		fis.close();
	}

	public ExcelReader(File file) throws IOException {
		fis = new FileInputStream(file);
		xlsworkbook = new HSSFWorkbook(fis);
		xlssheet = xlsworkbook.getSheetAt(0);
		fis.close();
	}

	public final int getSheetColumns(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		return (row.getLastCellNum());
	}

	public final String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			return (cell.getStringCellValue());
		} catch (NullPointerException e) {
			return null;
		}
	}

	public final String getCellData(String sheetName, String colName, int rowNum) {
		int colNum = -1;
		int index = workbook.getSheetIndex(sheetName);
		try {
			sheet = workbook.getSheetAt(index);
			for (int i = 0; i < getSheetColumns(sheetName); i++) {
				row = sheet.getRow(0);
				cell = row.getCell(i);
				if (cell.getStringCellValue().equals(colName)) {
					colNum = cell.getColumnIndex();
					break;
				}
			}
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);

			cell.setCellType(XSSFCell.CELL_TYPE_STRING);

			return (cell.getStringCellValue());
		} catch (NullPointerException e) {
			return null;
		}
	}

	public final int getSheetColumns(String sheetName, int rowNum) {
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum);
		return (row.getLastCellNum());
	}

	public final int getRowNumber(String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
						return row.getRowNum();
					}
				}
			}
		}
		return 0;
	}

	/*
	 * This method fetches all the column header records from the given sheet of Excel file
	 */
	public final String getHeaderDataOfExpectedSheet(File file, String sheetName) throws IOException {
		try {

			int index = xlsworkbook.getSheetIndex(sheetName);
			xlssheet = xlsworkbook.getSheetAt(index);
			xlsrow = xlssheet.getRow(0);
			String columnName = " ";
			for(int i=0;i<xlsrow.getLastCellNum();i++){
				xlscell =xlsrow.getCell(i);
				columnName = columnName + ", " + xlscell.getStringCellValue();
			}
			columnName = columnName.replaceAll(" ,", "");
			return columnName;

		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	/**
	 * This Method returns all the Sheets within a workbook
	 * @param file
	 * @return
	 */
	public final List<String> getAllSheetName(File file) {
		List<String> lstSheetNames = new ArrayList<>();
		try {
			int sheetCount = workbook.getNumberOfSheets();

			for (int i = 0; i < sheetCount; i++) {
				lstSheetNames.add(workbook.getSheetName(i));
			}
			return lstSheetNames;
		} catch (Exception e) {
			return null;
		}
	}
}
