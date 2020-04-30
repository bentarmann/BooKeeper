//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Financials.java
// Files: (a list of all source files used by that program)
// Course: CS400 Lec001, Spring, 2020
//
// Author: Qingqi Wu
// Email: qwu86@wisc.edu
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Financials - Uses Apache POI to generate excel tables from the data structure
 * 
 * Bugs: none known
 * 
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class Financials {

  /**
   * Create Financials based on the current BooKeeper Accounts
   * 
   * @param bk
   */
  public static void generateFinancials(BooKeeper bk, Stage stage) {
    // create a new excel workbook
    XSSFWorkbook workBook = new XSSFWorkbook();


    // let user choose a location
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter xlsxFilter =
        new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx");
    fileChooser.getExtensionFilters().add(xlsxFilter);
    fileChooser.setTitle("Save Financials");
    File file = fileChooser.showSaveDialog(stage);

    // generate the files, use the file name as company name
    generateBalanceSheet(workBook, bk, file.getName().replace(".xlsx", ""));

    // export to xlsx
    if (file != null) {
      try {
        FileOutputStream fos = new FileOutputStream(file);
        workBook.write(fos);
        fos.close();
        Alert success = new Alert(AlertType.CONFIRMATION, "Export Success!");
        success.showAndWait();
      } catch (Exception e) {
        String exportError = "Something wrong happen while exporting the file!";
        Alert exportAlert = new Alert(AlertType.ERROR, exportError);
        exportAlert.showAndWait();
      }
    }
  }

  /**
   * Create a worksheet of balance sheet
   * 
   * @param bk
   * @return
   */
  private static void generateBalanceSheet(XSSFWorkbook workBook, BooKeeper bk, String name) {

    // Create a sheet
    XSSFSheet balSheet = workBook.createSheet("Balance Sheet");
    // Create title
    int rowNumber = 0;
    Row title = balSheet.createRow(rowNumber++);
    title.createCell(0).setCellValue(name.toUpperCase() + "\nCONSOLIDATED BALANCE SHEET"
        + "\n(in thousands, except per share data) ");
    // Create Headers
    Row dateRow = balSheet.createRow(rowNumber++);
    // Assets
    dateRow.createCell(0).setCellValue("Assets");
    dateRow.createCell(1).setCellValue("YE 2020");
    // Current Assets
    rowNumber = createSections(balSheet, rowNumber, "Current Assets", new int[] {1, 0, 0}, bk);
    // Fixed Assets
    rowNumber = createSections(balSheet, rowNumber, "Fixed Assets", new int[] {1, 0, 1}, bk);


    // Liabilities
    Row liabRow = balSheet.createRow(rowNumber++);
    liabRow.createCell(0).setCellValue("Liabilities");
    // Current Liabilities
    rowNumber = createSections(balSheet, rowNumber, "Current Liabilities", new int[] {1, 1, 0}, bk);
    // Long Term Liabilities
    rowNumber =
        createSections(balSheet, rowNumber, "Long Term Liabilities", new int[] {1, 1, 1}, bk);

    // Stockholder's Equity
    Row seRow = balSheet.createRow(rowNumber++);
    seRow.createCell(0).setCellValue("Stockholder's Equity");
    // Paid-in Capital
    rowNumber = createSections(balSheet, rowNumber, "Paid-in Capital", new int[] {1, 2, 0}, bk);
    // Other
    rowNumber = createSections(balSheet, rowNumber, "Other", new int[] {1, 2, 1}, bk);

  }

  /**
   * create sections of accounts on a sheet
   * 
   * @param sheet
   * @param startingRow
   * @param rowHeader
   * @param identifier
   * @return the ending row number
   */
  private static int createSections(XSSFSheet sheet, int startingRow, String rowHeader,
      int[] identifier, BooKeeper bk) {
    Row row = sheet.createRow(startingRow++);
    row.createCell(0).setCellValue(rowHeader);

    ArrayList<Account> accts = getAccounts(identifier, bk);
    ArrayList<Integer> rowNums = new ArrayList<>();

    for (Account acct : accts) {
      rowNums.add(startingRow);
      Row fixedAssetRow = sheet.createRow(startingRow++);
      fixedAssetRow.createCell(0).setCellValue(acct.getAccountName());
      fixedAssetRow.createCell(1).setCellValue(acct.getAmount());
    }
    Row sumRow = sheet.createRow(startingRow++);
    Cell sumCell = sumRow.createCell(1);
    sumFormula(sumCell, rowNums, 1);

    return startingRow;
  }


  /**
   * create a cell with the summation formula
   * 
   * @param cell        the cell to set formula
   * @param rowNumToSum rows of the sum area
   * @param colNum      col of sum area
   * @return
   */
  private static void sumFormula(Cell cell, List<Integer> rowNumToSum, int colNum) {


    String col = excelCol(colNum);
String formula= "SUM("+col+rowNumToSum.get(0)+":"+col+rowNumToSum.get(rowNumToSum.size()-1)+")";
    System.out.println("formula: " + formula);
    // remove the last +
    
    cell.setCellFormula(formula);
  }


  /**
   * given column number, returns the column in excel
   * 
   * @param columnNumber
   * @return column in excel
   */
  private static String excelCol(int colNum) {
    StringBuilder column = new StringBuilder();

    while (colNum >= 0) {
      // Find remainder
      int remainder = colNum % 26;

      column.append((char) ((remainder) + 'A'));
      colNum = (colNum / 26) - 1;
    }

    // Reverse the string
    return column.reverse().toString();
  }

  /**
   * Return a list of account based on the given identifier
   * 
   * @param identifiers
   * @param bk
   * @return
   */
  private static ArrayList<Account> getAccounts(int[] identifiers, BooKeeper bk) {
    ArrayList<Account> acctList = new ArrayList<Account>();
    for (Account acct : bk.accounts) {
      List<int[]> ids = acct.getIdentifiers();
      for (int[] id : ids) {
        //System.out.println("comparing " + id + " against " + identifiers);
        if (Arrays.equals(id,identifiers))
          acctList.add(acct);
      }
    }
    Collections.sort(acctList);
    return acctList;
  }
  
  
//  public static void main(String[] args) {
//    int[] ca = {1, 0, 0};
//    ArrayList<Account> accts = getAccounts(ca, new BooKeeper());
//    for (Account acct : accts)
//      System.out.println(acct.getAccountName());
//
//  }
}
