/////////////////////////////////////////// FILE  HEADER ///////////////////////////////////////////
//
//Title: BooKeeper
//Files: Main.java, BooKeeper.java, Bookings.java, Financials.java, Transaction.java
//This File: Financials.java
//
//Name: Alex Hertadi, Benjamin Tarmann, Qingqi Wu
//
///////////////////////////////////////// 100 COLUMNS WIDE /////////////////////////////////////////

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
 * @author Alex Hertadi, Benjamin Tarmann, Qingqi Wu
 * @version 1.0
 * 
 */
public class Financials {
  
  /**
   * Create Financials based on the current BooKeeper Accounts
   * 
   * @param workBook the workbook to add worksheet
   * @param bk bookeeper for list of accounts
   * @param name company name
   */
  public static void generateFinancials(BooKeeper bk, Stage stage, javafx.scene.text.Font font) {
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
    generateBS(workBook, bk, file.getName().replace(".xlsx", ""), font);
    generateIS(workBook, bk, file.getName().replace(".xlsx", ""), font);
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
  private static void generateBS(XSSFWorkbook workBook, BooKeeper bk, String name, javafx.scene.text.Font font) {

    // Create a sheet
    XSSFSheet balSheet = workBook.createSheet("Balance Sheet");
    balSheet.setDefaultColumnWidth(40);
    // Create title
    int rowNumber = 0;
    Row title = balSheet.createRow(rowNumber++);
    
    CellStyle header = titleStyle(workBook, font);
    Cell titleCell = title.createCell(0);
    titleCell.setCellValue(name.toUpperCase() + "\nCONSOLIDATED BALANCE SHEET"
        + "\n(in thousands, except per share data) ");
    titleCell.setCellStyle(header);
    
    CellStyle plain = plainStyle(workBook, font);
    
    // Create Headers
    Row dateRow = balSheet.createRow(rowNumber++);
    CellStyle subheader = subtitleStyle(workBook, font); 
    // Assets
    dateRow.createCell(0).setCellValue("Assets");
    dateRow.createCell(1).setCellValue("YE 2020");
    dateRow.getCell(0).setCellStyle(subheader);
    dateRow.getCell(1).setCellStyle(subheader);
    // Current Assets
    rowNumber = createSections(balSheet, rowNumber, "Current Assets", new int[] {1, 0, 0}, bk, subheader, plain);
    // Fixed Assets
    rowNumber = createSections(balSheet, rowNumber, "Fixed Assets", new int[] {1, 0, 1}, bk, subheader, plain);


    // Liabilities
    Row liabRow = balSheet.createRow(rowNumber++);
    liabRow.createCell(0).setCellValue("Liabilities");
    liabRow.getCell(0).setCellStyle(subheader);
    // Current Liabilities
    rowNumber = createSections(balSheet, rowNumber, "Current Liabilities", new int[] {1, 1, 0}, bk, subheader, plain);
    // Long Term Liabilities
    rowNumber =
        createSections(balSheet, rowNumber, "Long Term Liabilities", new int[] {1, 1, 1}, bk, subheader, plain);

    // Stockholder's Equity
    Row seRow = balSheet.createRow(rowNumber++);
    seRow.createCell(0).setCellValue("Stockholder's Equity");
    seRow.getCell(0).setCellStyle(subheader);
    // Paid-in Capital
    rowNumber = createSections(balSheet, rowNumber, "Paid-in Capital", new int[] {1, 2, 0}, bk, subheader, plain);
    // Other
    rowNumber = createSections(balSheet, rowNumber, "Other", new int[] {1, 2, 1}, bk, subheader, plain);

  }

  /**
   * Create an income statement on a worksheet
   * @param workBook the workbook to add worksheet
   * @param bk bookeeper for list of accounts
   * @param name company name
   */
  private static void generateIS(XSSFWorkbook workBook, BooKeeper bk, String name, javafx.scene.text.Font font) {
    // Create a sheet
    XSSFSheet incSheet = workBook.createSheet("Income Statement");
    incSheet.setDefaultColumnWidth(40);
    // Create title
    
    CellStyle plain = plainStyle(workBook, font);
    CellStyle subheader = subtitleStyle(workBook, font); 
    
    int rowNumber = 0;
    Row title = incSheet.createRow(rowNumber++);
    CellStyle header = titleStyle(workBook, font);  
    Cell titleCell = title.createCell(0);
    titleCell.setCellValue(name.toUpperCase() + "\nINCOME STATEMENT"
        + "\n(in thousands, except per share data) ");
    titleCell.setCellStyle(header);
    
    // date
    Row dateRow = incSheet.createRow(rowNumber++);
    dateRow.createCell(1).setCellValue("YE 2020");
    dateRow.getCell(1).setCellStyle(subheader);
    
    ArrayList<Integer> rowNums = new ArrayList<Integer>();
    //sales
    rowNums.add(rowNumber);
    Row netSaleRow = incSheet.createRow(rowNumber++);
    netSaleRow.createCell(0).setCellValue("Net Sales");
    netSaleRow.createCell(1).setCellValue(bk.getNetRevenue());
    netSaleRow.getCell(0).setCellStyle(plain);
    netSaleRow.getCell(1).setCellStyle(plain);
    
    //cogs
    rowNums.add(rowNumber);
    Row cogsRow = incSheet.createRow(rowNumber++);
    cogsRow.createCell(0).setCellValue("Cost of Goods Sold");
    cogsRow.createCell(1).setCellValue(bk.getAccount("Cost of Goods Sold").getAmount());
    cogsRow.getCell(0).setCellStyle(plain);
    cogsRow.getCell(1).setCellStyle(plain);
    
    //add blank row
    incSheet.createRow(rowNumber++);
    
    //gross row
    Row sumRow = incSheet.createRow(rowNumber++);
    sumRow.createCell(0).setCellValue("Gross Profit");
    sumRow.getCell(0).setCellStyle(plain);
    Cell sumCell = sumRow.createCell(1);
    sumFormula(sumCell, rowNums, 1);
    sumCell.setCellStyle(plain);
    String gross = excelCol(1) + (rowNumber-1);
    
    rowNums.clear();
    rowNums.add(rowNumber);
    //Operating expenses
    rowNumber = createSections(incSheet, rowNumber, "Operating Expenses", new int[] {0,1}, bk, subheader, plain);
    rowNums.add(rowNumber-1);
    //sum it up
    String opExp = excelCol(1) + (rowNumber-1);
    
    
  //non ops
    rowNumber = createSections(incSheet, rowNumber, "Other Revenues and Gains", new int[] {0,2}, bk, subheader, plain);
    rowNums.add(rowNumber-1);
    //sum it up
    String nonOpRev = excelCol(1) + (rowNumber-1);
    
    rowNumber = createSections(incSheet, rowNumber, "Other Expenses and Losses", new int[] {0,3}, bk, subheader, plain);
    rowNums.add(rowNumber-1);
    //sum it up
    String nonOpExp = excelCol(1) + (rowNumber-1);
    
  //before tax sum row
    Row totalRow = incSheet.createRow(rowNumber++);
    totalRow.createCell(0).setCellValue("Income before income Taxes");
    totalRow.getCell(0).setCellStyle(plain);
    Cell totalCell = totalRow.createCell(1);
    String col = excelCol(1);
    String formula= gross+"-"+opExp+"+"+nonOpRev+"-"+nonOpExp;
    System.out.println(formula);
    totalCell.setCellFormula(formula);
    totalCell.setCellStyle(plain);
    String totalBTax = excelCol(1) + (rowNumber);
    
    Row taxRow = incSheet.createRow(rowNumber++);
    taxRow.createCell(0).setCellValue("Income Tax");
    taxRow.createCell(1).setCellFormula(totalBTax+"*"+bk.taxRate);;
    taxRow.getCell(0).setCellStyle(plain);
    taxRow.getCell(1).setCellStyle(plain);
    String totalTax = excelCol(1) + (rowNumber);
    
    
    Row netIncomeRow = incSheet.createRow(rowNumber++);
    netIncomeRow.createCell(0).setCellValue("Net Income");
    netIncomeRow.createCell(1).setCellFormula(totalBTax+"-"+totalTax);
    netIncomeRow.getCell(0).setCellStyle(plain);
    netIncomeRow.getCell(1).setCellStyle(plain);
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
      int[] identifier, BooKeeper bk, CellStyle subtitle, CellStyle plain) {
    Row row = sheet.createRow(startingRow++);
    row.createCell(0).setCellValue(rowHeader);
    row.getCell(0).setCellStyle(subtitle);
    
    ArrayList<Account> accts = getAccounts(identifier, bk);
    ArrayList<Integer> rowNums = new ArrayList<>();

    for (Account acct : accts) {
      rowNums.add(startingRow);
      Row subRow = sheet.createRow(startingRow++);
      subRow.createCell(0).setCellValue(acct.getAccountName());
      subRow.createCell(1).setCellValue(acct.getAmount());
      subRow.getCell(0).setCellStyle(plain);
      subRow.getCell(1).setCellStyle(plain);
    }
    
    //summation row
    Row sumRow = sheet.createRow(startingRow++);
    sumRow.createCell(0).setCellValue("Total "+rowHeader);
    sumRow.getCell(0).setCellStyle(plain);
    Cell sumCell = sumRow.createCell(1);
    sumFormula(sumCell, rowNums, 1);
    sumCell.setCellStyle(plain);

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
    String formula= "SUM("+col+(rowNumToSum.get(0)+1)+":"+col+(rowNumToSum.get(rowNumToSum.size()-1)+1)+")";
    //System.out.println("formula: " + formula);
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
  
  /**
   * Creates style for subtitles
   * @param workBook
   * @param font
   * @return
   */
  private static CellStyle subtitleStyle(XSSFWorkbook workBook, javafx.scene.text.Font font) {
    Font title = workBook.createFont();
    title.setFontName(font.getName());
    title.setFontHeightInPoints((short) font.getSize());
    title.setBold(true);
    
    CellStyle subtitleStyle = workBook.createCellStyle();
    subtitleStyle.setAlignment(HorizontalAlignment.LEFT);
    subtitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    subtitleStyle.setFont(title);
    subtitleStyle.setWrapText(true);
    
    return subtitleStyle;
  }
  
  /**
   * creates style for the title
   * @param workBook
   * @param font
   * @return
   */
  private static CellStyle titleStyle(XSSFWorkbook workBook, javafx.scene.text.Font font) {
    Font title = workBook.createFont();
    title.setFontName(font.getName());
    title.setFontHeightInPoints((short) font.getSize());
    title.setBold(true);
    
    CellStyle titleStyle = workBook.createCellStyle();
    titleStyle.setAlignment(HorizontalAlignment.CENTER);
    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    titleStyle.setFont(title);
    titleStyle.setWrapText(true);
    
    return titleStyle;
    
  }
  
  /**
   * creates style for the default text
   * @param workBook
   * @param font
   * @return
   */
  private static CellStyle plainStyle(XSSFWorkbook workBook, javafx.scene.text.Font font) {
    Font title = workBook.createFont();
    title.setFontName(font.getName());
    title.setFontHeightInPoints((short) font.getSize());
    title.setBold(false);
    
    CellStyle plainStyle = workBook.createCellStyle();
    plainStyle.setAlignment(HorizontalAlignment.LEFT);
    plainStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    plainStyle.setFont(title);
    plainStyle.setWrapText(true);
    
    return plainStyle;
  }
}
