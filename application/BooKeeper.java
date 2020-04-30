package application;

import java.util.ArrayList;
import java.util.List;

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
 * BooKeeper - book keeping a list of accounts
 * 
 * Bugs: none known
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class BooKeeper {
  
    //set to public so changes can be made directly into the list
    public List<Account> accounts;

    public BooKeeper () {
      accounts = new ArrayList<>();
   // asset accounts
      int[] currentAsset = {1,0,0};
      int[] ppe = {1,0,1};
      
      Account cash = new Account("Cash");
      cash.addIdentifier(currentAsset);
      accounts.add(cash);
      
      Account sInvest = new Account("Short-term Investments");
      sInvest.addIdentifier(currentAsset);
      accounts.add(sInvest);
      
      Account accRec = new Account("Account Receivable");
      accRec.addIdentifier(currentAsset);
      accounts.add(accRec);
      
      Account prepaidExp = new Account("Prepaid Expenses");
      prepaidExp.addIdentifier(currentAsset);
      accounts.add(prepaidExp);
      
      Account inventory = new Account("Inventory");
      inventory.addIdentifier(currentAsset);
      accounts.add(inventory);
      
      Account supplies = new Account("Supplies");
      supplies.addIdentifier(currentAsset);
      accounts.add(supplies);
      
      Account lInvest = new Account("Long-term Investments");
      lInvest.addIdentifier(ppe);
      accounts.add(lInvest);
      
      Account land = new Account("Land");
      land.addIdentifier(ppe);
      accounts.add(land);

      Account buildings = new Account("Buildings");
      buildings.addIdentifier(ppe);
      accounts.add(buildings);
      
      Account equipment = new Account("Equipment");
      equipment.addIdentifier(ppe);
      accounts.add(equipment);
      
      Account vehicles = new Account("Vehicles");
      vehicles.addIdentifier(ppe);
      accounts.add(vehicles);
      
      Account furniture = new Account("Furniture & Fixtures");
      furniture.addIdentifier(ppe);
      accounts.add(furniture);
     
      Account accDep = new Account("Accumulated Depreciation");
      accDep.addIdentifier(ppe);
      accounts.add(accDep);
      
      // liability accounts
      int[] currentLiability = {1, 1, 0};
      int[] nonCurrentLiability = {1, 1, 1};
      
      Account sLoansPayable = new Account("Short-Term Loans Payable");
      sLoansPayable.addIdentifier(currentLiability);
      accounts.add(sLoansPayable);
      
      Account curLDebt = new Account("Current Portion of Long-Term Debt");
      curLDebt.addIdentifier(currentLiability);
      accounts.add(curLDebt);
      
      Account accountsPayable = new Account("Accounts Payable");
      accountsPayable.addIdentifier(currentLiability);
      accounts.add(accountsPayable);
      
      Account accruedExpenses = new Account("Accrued Expenses");
      accruedExpenses.addIdentifier(currentLiability);
      accounts.add(accruedExpenses);
      
      Account unearnedDeferredRev = new Account("Unearned or Deferred Revenues");
      unearnedDeferredRev.addIdentifier(currentLiability);
      accounts.add(unearnedDeferredRev);
      
      Account installmentLoansPayable = new Account("Installment Loans Payable");
      installmentLoansPayable.addIdentifier(nonCurrentLiability);
      accounts.add(installmentLoansPayable);
      
      Account mortgageLoansPayable = new Account("Mortgage Loans Payable");
      mortgageLoansPayable.addIdentifier(nonCurrentLiability);
      accounts.add(mortgageLoansPayable);
      
      // stockholders' equity accounts
      int[] paidInCapital = {1, 2, 0};
      int[] otherSSEItems = {1, 2, 1};
      
      Account commonStock = new Account("Common Stock");
      commonStock.addIdentifier(paidInCapital);
      accounts.add(commonStock);
      
      Account paidInCapitalInExcessOfParValue = new Account("Paid-In-Capital In Excess of the Par Value "
          + "of the Common Stock");   
      paidInCapitalInExcessOfParValue.addIdentifier(paidInCapital);
      accounts.add(paidInCapitalInExcessOfParValue);
      
      Account retainedEarnings = new Account("Retained Earnings");
      retainedEarnings.addIdentifier(otherSSEItems);
      accounts.add(retainedEarnings);
      
      // income statement accounts
      int[] operatingRevenue = {0,0};
      
      
      Account sales = new Account("Sales");
      sales.addIdentifier(operatingRevenue);
      accounts.add(sales);
      
      //another name for sales
      Account revenue = new Account("Revenue");
      revenue.addIdentifier(operatingRevenue);
      accounts.add(revenue);
      
      Account salesRev = new Account("Sales Revenue");
      salesRev.addIdentifier(operatingRevenue);
      accounts.add(salesRev);
      
      Account serviceRev = new Account("Service Revenue");
      serviceRev.addIdentifier(operatingRevenue);
      accounts.add(serviceRev);
      
      Account feesEarned = new Account("Fees Earned");
      feesEarned.addIdentifier(operatingRevenue);
      accounts.add(feesEarned);
      
      //A Contra-sales account
      Account salesReturnsAndAllowances = new Account("Sales Returns and Allowances");
      salesReturnsAndAllowances.addIdentifier(operatingRevenue);
      accounts.add(salesReturnsAndAllowances);
      
      //also a contra sales account
      Account salesDiscount = new Account("Sales Discount");
      salesDiscount.addIdentifier(operatingRevenue);
      accounts.add(salesDiscount);
      
      int[] operatingExp = {0,1};
      
      Account cogs = new Account("Cost of Goods Sold");
      cogs.addIdentifier(operatingExp);
      accounts.add(cogs);
      
      Account sga = new Account("Selling, General and Administrative Expenses");
      sga.addIdentifier(operatingExp);
      accounts.add(sga);
      
      Account salariesExp = new Account("Salaries Expense");
      salariesExp.addIdentifier(operatingExp);
      accounts.add(salariesExp);
      
      Account rentExp = new Account("rentExp");
      rentExp.addIdentifier(operatingExp);
      accounts.add(rentExp);
      
      Account utilitiesExp = new Account("Utilities Expense");
      utilitiesExp.addIdentifier(operatingExp);
      accounts.add(utilitiesExp);
      
      Account depreciationExp = new Account("Depreciation Expense");
      depreciationExp.addIdentifier(operatingExp);
      accounts.add(depreciationExp);
      
      Account repairsExp = new Account("Repairs Expense");
      repairsExp.addIdentifier(operatingExp);
      accounts.add(repairsExp);
      
      int[] nonOpRev = {0,2};
      
      Account interestRev = new Account("Interest Revenue");
      interestRev.addIdentifier(nonOpRev);
      accounts.add(interestRev);
      
      int[] nonOpExp = {0,3};
      
      Account interestExp = new Account("Interest Expense");
      interestExp.addIdentifier(nonOpExp);
      accounts.add(interestExp);
    }
    
    /**
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
      return accounts;
    }

    /**
     * @param accounts the list of accounts to set
     */
    public void setAccounts(List<Account> accounts) {
      this.accounts = accounts;
    }
  
    /**
     * Add an account for bookkeeping
     * @param account
     */
    public void addAccounts(Account account) {
      this.accounts.add(account);
    }

    /**
     * removes a particular account
     * @param account the account object to remove
     * @return true if success
     */
    public boolean removeAccounts(Account account) {
      return this.accounts.remove(account);
    }
    
    /**
     * Remove an account based on its name
     * @param acctName the name String of the account
     * @return true if success
     */
    public boolean removeAccounts(String acctName) {
      return this.accounts.removeIf(acct -> acct.getAccountName().equalsIgnoreCase(acctName));
    }
    
    /**
     * Get a particular account in this book keeper
     * @param acctName
     * @return
     */
    public Account getAccount(String acctName) {
      for(Account acct : this.accounts) {
        if(acct.getAccountName().equalsIgnoreCase(acctName.strip()))
          return acct;
      }
      throw new IllegalArgumentException("Incorrect Account Name/Account name does not exist!");
    }
}