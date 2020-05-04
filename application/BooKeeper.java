/////////////////////////////////////////// FILE  HEADER ///////////////////////////////////////////
//
//Title: BooKeeper
//Files: Main.java, BooKeeper.java, Bookings.java, Financials.java, Transaction.java
//This File: BooKeeper.java
//
//Name: Alex Hertadi, Benjamin Tarmann, Qingqi Wu
//
///////////////////////////////////////// 100 COLUMNS WIDE /////////////////////////////////////////

package application;

import java.util.ArrayList;
import java.util.List;

/**
 * BooKeeper - book keeping a list of accounts
 * 
 * Bugs: none known
 * 
 * @author Alex Hertadi, Benjamin Tarmann,Qingqi Wu
 * @version 1.0
 * 
 */
public class BooKeeper {

  // set to public so changes can be made directly into the list
  public List<Account> accounts;
  public double taxRate = 0.25;

  public BooKeeper() {
    accounts = new ArrayList<>();
    // asset accounts
    int[] currentAsset = {1, 0, 0};
    int[] ppe = {1, 0, 1};

    Account cash = new Account("Cash", true);
    cash.addIdentifier(currentAsset);
    accounts.add(cash);

    Account sInvest = new Account("Short-term Investments", true);
    sInvest.addIdentifier(currentAsset);
    accounts.add(sInvest);

    Account accRec = new Account("Account Receivable", true);
    accRec.addIdentifier(currentAsset);
    accounts.add(accRec);

    Account prepaidExp = new Account("Prepaid Expenses", true);
    prepaidExp.addIdentifier(currentAsset);
    accounts.add(prepaidExp);

    Account inventory = new Account("Inventory", true);
    inventory.addIdentifier(currentAsset);
    accounts.add(inventory);

    Account supplies = new Account("Supplies", true);
    supplies.addIdentifier(currentAsset);
    accounts.add(supplies);

    Account lInvest = new Account("Long-term Investments", true);
    lInvest.addIdentifier(ppe);
    accounts.add(lInvest);

    Account land = new Account("Land", true);
    land.addIdentifier(ppe);
    accounts.add(land);

    Account buildings = new Account("Buildings", true);
    buildings.addIdentifier(ppe);
    accounts.add(buildings);

    Account equipment = new Account("Equipment", true);
    equipment.addIdentifier(ppe);
    accounts.add(equipment);

    Account vehicles = new Account("Vehicles", true);
    vehicles.addIdentifier(ppe);
    accounts.add(vehicles);

    Account furniture = new Account("Furniture & Fixtures", true);
    furniture.addIdentifier(ppe);
    accounts.add(furniture);

    Account accDep = new Account("Accumulated Depreciation", false);
    accDep.addIdentifier(ppe);
    accounts.add(accDep);

    // liability accounts
    int[] currentLiability = {1, 1, 0};
    int[] nonCurrentLiability = {1, 1, 1};

    Account sLoansPayable = new Account("Short-Term Loans Payable", false);
    sLoansPayable.addIdentifier(currentLiability);
    accounts.add(sLoansPayable);

    Account curLDebt = new Account("Current Portion of Long-Term Debt", false);
    curLDebt.addIdentifier(currentLiability);
    accounts.add(curLDebt);

    Account accountsPayable = new Account("Accounts Payable", false);
    accountsPayable.addIdentifier(currentLiability);
    accounts.add(accountsPayable);

    Account accruedExpenses = new Account("Accrued Expenses", false);
    accruedExpenses.addIdentifier(currentLiability);
    accounts.add(accruedExpenses);

    Account unearnedDeferredRev = new Account("Unearned or Deferred Revenues", false);
    unearnedDeferredRev.addIdentifier(currentLiability);
    accounts.add(unearnedDeferredRev);

    Account installmentLoansPayable = new Account("Installment Loans Payable", false);
    installmentLoansPayable.addIdentifier(nonCurrentLiability);
    accounts.add(installmentLoansPayable);

    Account mortgageLoansPayable = new Account("Mortgage Loans Payable", false);
    mortgageLoansPayable.addIdentifier(nonCurrentLiability);
    accounts.add(mortgageLoansPayable);

    // stockholders' equity accounts
    int[] paidInCapital = {1, 2, 0};
    int[] otherSSEItems = {1, 2, 1};

    Account commonStock = new Account("Common Stock", false);
    commonStock.addIdentifier(paidInCapital);
    accounts.add(commonStock);

    Account paidInCapitalInExcessOfParValue =
        new Account("Paid-In-Capital In Excess of the Par Value " + "of the Common Stock", false);
    paidInCapitalInExcessOfParValue.addIdentifier(paidInCapital);
    accounts.add(paidInCapitalInExcessOfParValue);

    Account retainedEarnings = new Account("Retained Earnings", false);
    retainedEarnings.addIdentifier(otherSSEItems);
    accounts.add(retainedEarnings);

    Account dividends = new Account("Dividends", true);
    dividends.addIdentifier(otherSSEItems);
    accounts.add(dividends);

    // income statement accounts
    int[] operatingRevenue = {0, 0};

    Account sales = new Account("Sales", false);
    sales.addIdentifier(operatingRevenue);
    accounts.add(sales);

    // another name for sales
    Account revenue = new Account("Revenue", false);
    revenue.addIdentifier(operatingRevenue);
    accounts.add(revenue);

    Account salesRev = new Account("Sales Revenue", false);
    salesRev.addIdentifier(operatingRevenue);
    accounts.add(salesRev);

    Account serviceRev = new Account("Service Revenue", false);
    serviceRev.addIdentifier(operatingRevenue);
    accounts.add(serviceRev);

    Account feesEarned = new Account("Fees Earned", false);
    feesEarned.addIdentifier(operatingRevenue);
    accounts.add(feesEarned);

    // A Contra-sales account
    Account salesReturnsAndAllowances = new Account("Sales Returns and Allowances", true);
    salesReturnsAndAllowances.addIdentifier(operatingRevenue);
    accounts.add(salesReturnsAndAllowances);

    // also a contra sales account
    Account salesDiscount = new Account("Sales Discount", true);
    salesDiscount.addIdentifier(operatingRevenue);
    accounts.add(salesDiscount);

    int[] operatingExp = {0, 1};

    Account cogs = new Account("Cost of Goods Sold", true);
    cogs.addIdentifier(operatingExp);
    accounts.add(cogs);

    Account sga = new Account("Selling, General and Administrative Expenses", true);
    sga.addIdentifier(operatingExp);
    accounts.add(sga);

    Account salariesExp = new Account("Salaries Expense", true);
    salariesExp.addIdentifier(operatingExp);
    accounts.add(salariesExp);

    Account rentExp = new Account("Rent Expense", true);
    rentExp.addIdentifier(operatingExp);
    accounts.add(rentExp);

    Account utilitiesExp = new Account("Utilities Expense", true);
    utilitiesExp.addIdentifier(operatingExp);
    accounts.add(utilitiesExp);

    Account depreciationExp = new Account("Depreciation Expense", true);
    depreciationExp.addIdentifier(operatingExp);
    accounts.add(depreciationExp);

    Account repairsExp = new Account("Repairs Expense", true);
    repairsExp.addIdentifier(operatingExp);
    accounts.add(repairsExp);

    int[] nonOpRev = {0, 2};

    Account interestRev = new Account("Interest Revenue", false);
    interestRev.addIdentifier(nonOpRev);
    accounts.add(interestRev);

    int[] nonOpExp = {0, 3};

    Account interestExp = new Account("Interest Expense", true);
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
   * 
   * @param account
   */
  public void addAccounts(Account account) {
    this.accounts.add(account);
  }

  /**
   * removes a particular account
   * 
   * @param account the account object to remove
   * @return true if success
   */
  public boolean removeAccounts(Account account) {
    return this.accounts.remove(account);
  }

  /**
   * Remove an account based on its name
   * 
   * @param acctName the name String of the account
   * @return true if success
   */
  public boolean removeAccounts(String acctName) {
    return this.accounts.removeIf(acct -> acct.getAccountName().equalsIgnoreCase(acctName));
  }

  /**
   * Get a particular account in this book keeper
   * 
   * @param acctName
   * @return
   */
  public Account getAccount(String acctName) {
    for (Account acct : this.accounts) {
      if (acct.getAccountName().equalsIgnoreCase(acctName.strip()))
        return acct;
    }
    throw new IllegalArgumentException("Incorrect Account Name/Account name does not exist!");
  }

  /**
   * Returns the total net revenue
   * 
   * @return revenue -discounts/returns
   */
  public int getNetRevenue() {
    return getAccount("Sales").getAmount() + getAccount("Revenue").getAmount()
        + getAccount("Sales Revenue").getAmount() + getAccount("Service Revenue").getAmount()
        + getAccount("Fees Earned").getAmount()
        - getAccount("Sales Returns and Allowances").getAmount()
        + getAccount("Sales Discount").getAmount();
  }
}
