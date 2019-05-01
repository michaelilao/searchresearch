package searchresearch.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import com.sun.jdi.InvalidTypeException;

/**
  * 
  * @author Donisius Wigie
  * Class containing methods to parse through xls files. Uses the Apache POI library (poi-4.0.1.jar and poi-ooxml-4.0.1.jar)
  * obtained from https://poi.apache.org/download.html. License available in documents folder.
  * 
  *
  */
public class XLSTable {
	
	private final HSSFSheet dataSheet;
	private int maxColumn;
	private int maxRow;
	
	/**
	  * Constructor for the XLSTable class. Uses the HSSFWorkbook module from the Apache POI library (poi-4.0.1.jar and poi-ooxml-4.0.1.jar)
	  * obtained from https://poi.apache.org/download.html. License available in documents folder.
	  * @param fileName Name of the file to parse.
	  * @throws FileNotFoundException Problem opening or finding the given .xls file.
	  * @throws IOException Problem reading the given .xls file.
	  */
	public XLSTable(String fileName) throws FileNotFoundException, IOException {
		HSSFWorkbook xlsWorkbook = new HSSFWorkbook(new FileInputStream(fileName));
		dataSheet = xlsWorkbook.getSheetAt(0);
		xlsWorkbook.close();
		this.maxRow = dataSheet.getLastRowNum();
		this.maxColumn = dataSheet.getRow(0).getLastCellNum();
	}
	
	/**
	  * Gets the maximum column number in the given file.
	  * 
	  * @return maxColumn The last column of the given file.
	  */
	public int getMaxColumn() {
		return maxColumn;
	}
	
	/**
	  * Gets the maximum row number in the given file.
	  * 
	  * @return maxRow The last column of the given file.
	  */
	public int getMaxRow() {
		return maxRow;
	}
	
	/**
	  * Gets the information at the given row and column index of an xls file.
	  * 
	  * @param row The row at which the desired information exists.
	  * @param column The column at which the desired information exists.
	  * @return The information at the given row and column index.
	  * @throws InvalidTypeException Input type is invalid.
	  */
	public String getStringCellContent(int row, int column) throws InvalidTypeException {
		Cell cell = dataSheet.getRow(row).getCell(column);
		if (cell.getCellType() != CellType.STRING) {
			throw new InvalidTypeException("Cell at row: " + row + ", column: " + column + " is not a String Cell");
		}
		return cell.getStringCellValue();
	}
}

/*   Copyright 2019 Group_8_2XB3

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
