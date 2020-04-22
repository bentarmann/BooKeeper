package application;
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           BooKeeper.java 
// Files:           (a list of all source files used by that program)
// Course:          CS400, Spring 2020
//
// Author:          Qingqi Wu, Alexander Hertadi, Benjamin Tarmann
// Email:           qwu86@wisc.edu, btarmann@wisc.edu
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * BooKeeper - TODO Description
 * 
 * Bugs: none known
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class BooKeeper {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // asset accounts
    int[] currentAsset = {1,0,0};
    int[] ppe = {1,0,1};
    
    Account cash = new Account("Cash");
    cash.addIdentifier(currentAsset);
    
    Account sInvest = new Account("Short-term Investments");
    sInvest.addIdentifier(currentAsset);
    
    Account accRec = new Account("Account Receivable");
    accRec.addIdentifier(currentAsset);
    
    Account prepaidExp = new Account("Prepaid Expenses");
    prepaidExp.addIdentifier(currentAsset);
    
    Account inventory = new Account("Inventory");
    inventory.addIdentifier(currentAsset);
    
    Account supplies = new Account("Supplies");
    supplies.addIdentifier(currentAsset);
    
    Account lInvest = new Account("Long-term Investments");
    lInvest.addIdentifier(ppe);

    Account land = new Account("Land");
    land.addIdentifier(ppe);

    Account buildings = new Account("Buildings");
    buildings.addIdentifier(ppe);
    
    Account equipment = new Account("Equipment");
    equipment.addIdentifier(ppe);
    
    Account vehicles = new Account("Vehicles");
    vehicles.addIdentifier(ppe);
    
    Account furniture = new Account("Furniture & Fixtures");
    furniture.addIdentifier(ppe);
   
    Account accDep = new Account("Accumulated Depreciation");
    accDep.addIdentifier(ppe);
    
    // liability accounts
    int[] currentLiability = {1, 1, 0};
    int[] nonCurrentLiability = {1, 1, 1};
    
    Account sLoansPayable = new Account("Short-Term Loans Payable");
    sLoansPayable.addIdentifier(currentLiability);
    
    Account curLDebt = new Account("Current Portion of Long-Term Debt");
    curLDebt.addIdentifier(currentLiability);
    
    Account accountsPayable = new Account("Accounts Payable");
    accountsPayable.addIdentifier(currentLiability);
    
    Account accruedExpenses = new Account("Accrued Expenses");
    accruedExpenses.addIdentifier(currentLiability);
    
    Account unearnedDeferredRev = new Account("Unearned or Deferred Revenues");
    unearnedDeferredRev.addIdentifier(currentLiability);
    
    Account installmentLoansPayable = new Account("Installment Loans Payable");
    installmentLoansPayable.addIdentifier(nonCurrentLiability);
    
    Account mortgageLoansPayable = new Account("Mortgage Loans Payable");
    mortgageLoansPayable.addIdentifier(nonCurrentLiability);
    
    // stockholders' equity accounts
    int[] paidInCapital = {1, 2, 0};
    int[] otherSSEItems = {1, 2, 1};
    
    Account commonStock = new Account("Common Stock");
    commonStock.addIdentifier(paidInCapital);
    
    Account paidInCapitalInExcessOfParValue = new Account("Paid-In-Capital In Excess of the Par Value "
        + "of the Common Stock");   
    paidInCapitalInExcessOfParValue.addIdentifier(paidInCapital);
    
    Account retainedEarnings = new Account("Retained Earnings");
    retainedEarnings.addIdentifier(otherSSEItems);
    
    // income statement accounts
    int[] operatingRevenue = {0,0};
    
    
    Account sales = new Account("Sales");
    sales.addIdentifier(operatingRevenue);
    
    //another name for sales
    Account revenue = new Account("Revenue");
    revenue.addIdentifier(operatingRevenue);
    
    Account salesRev = new Account("Sales Revenues");
    salesRev.addIdentifier(operatingRevenue);
    
    Account serviceRev = new Account("Service Rev");
    serviceRev.addIdentifier(operatingRevenue);
    
    Account feesEarned = new Account("Fees Earned");
    feesEarned.addIdentifier(operatingRevenue);
    
    //A Contra-sales account
    Account salesReturnsAndAllowances = new Account("Sales Returns and Allowances");
    salesReturnsAndAllowances.addIdentifier(operatingRevenue);
    
    //also a contra sales account
    Account salesDiscount = new Account("Sales Discount");
    salesDiscount.addIdentifier(operatingRevenue);
    
    int[] operatingExp = {0,1};
    
    Account cogs = new Account("Cost of Goods Sold");
    cogs.addIdentifier(operatingExp);
    
    Account sga = new Account("Selling, General and Administrative Expenses");
    sga.addIdentifier(operatingExp);
    
    Account salariesExp = new Account("Salaries Expense");
    salariesExp.addIdentifier(operatingExp);
    
    Account rentExp = new Account("rentExp");
    rentExp.addIdentifier(operatingExp);
    
    Account utilitiesExp = new Account("Utilities Expense");
    utilitiesExp.addIdentifier(operatingExp);
    
    Account depreciationExp = new Account("Depreciation Expense");
    depreciationExp.addIdentifier(operatingExp);
    
    Account repairsExp = new Account("Repairs Expense");
    repairsExp.addIdentifier(operatingExp);
    
    int[] nonOpRev = {0,2};
    
    Account interestRev = new Account("Interest Revenue");
    interestRev.addIdentifier(nonOpRev);
    
    int[] nonOpExp = {0,3};
    
    Account interestExp = new Account("Interest Expense");
    interestExp.addIdentifier(nonOpExp);
    
  }

}