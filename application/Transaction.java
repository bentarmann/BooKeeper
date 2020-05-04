/////////////////////////////////////////// FILE  HEADER ///////////////////////////////////////////
//
//Title: BooKeeper
//Files: Main.java, BooKeeper.java, Bookings.java, Financials.java, Transaction.java
//This File: Transaction.java
//
//Name: Alex Hertadi, Benjamin Tarmann, Qingqi Wu
//
///////////////////////////////////////// 100 COLUMNS WIDE /////////////////////////////////////////

package application;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Transaction - This class uses arraylists that implement the ListADT 
 * to record the debit or credit transaction accounts and amounts, 
 * updates the accounts in the process 
 * 
 * Bugs: none known
 * @author Alex Hertadi, Benjamin Tarmann, Qingqi Wu
 * @version 1.0
 * 
 */
public class Transaction implements Comparable<Transaction> {

  private int transactionNumber;
  private LocalDateTime date; //stores the local date and time
  private ArrayList<Account> debitAccounts;
  private ArrayList<Integer> debitAmounts;
  private ArrayList<Account> creditAccounts;
  private ArrayList<Integer> creditAmounts;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
  
  public Transaction(LocalDateTime date, int transactionNumber) {
    this.date = date;
    debitAccounts = new ArrayList<>();
    debitAmounts = new ArrayList<>();
    creditAccounts = new ArrayList<>();
    creditAmounts = new ArrayList<>();
    this.transactionNumber = transactionNumber;
  }
  
  public Transaction(int transactionNumber) {
    this(LocalDateTime.now(), transactionNumber);
  }
  
  /**
   * Constructor that uses a String as the date
   * @param date with the format "MM-dd-yyyy HH:mm"
   */
  public Transaction(String date, int transactionNumber) {
    
    this.date = LocalDateTime.parse(date, formatter);
    debitAccounts = new ArrayList<>();
    debitAmounts = new ArrayList<>();
    creditAccounts = new ArrayList<>();
    creditAmounts = new ArrayList<>();
    this.transactionNumber = transactionNumber;
  }

  /**
   * @return the transactionNumber
   */
  public int getTransactionNumber() {
    return transactionNumber;
  }

  /**
   * @param transactionNumber the transactionNumber to set
   */
  public void setTransactionNumber(int transactionNumber) {
    this.transactionNumber = transactionNumber;
  }

  /**
   * @return the date
   */
  public LocalDateTime getDate() {
    return date;
  }

  /**
   * Return the string representing the date and time
   * @return MM-dd-yyyy HH:mm in String
   */
  public String getDateString() {
    return date.format(formatter);
  }
  
  /**
   * Set the date and time of this transaction
   * @param date the date to set
   */
  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  /**
   * Set the date and time of this transaction using a string 
   * @param date a string with format "MM-dd-yyyy HH:mm"
   */
  public void setDate(String date) {
    this.date = LocalDateTime.parse(date, formatter);
  }
  
  /**
   * @return the debitAccounts
   */
  public ArrayList<Account> getDebitAccounts() {
    return debitAccounts;
  }

  /**
   * @param debitAccounts the debitAccounts to set
   */
  public void setDebitAccounts(ArrayList<Account> debitAccounts) {
    this.debitAccounts = debitAccounts;
  }

  /**
   * @return the debitAmounts
   */
  public ArrayList<Integer> getDebitAmounts() {
    return debitAmounts;
  }

  /**
   * @param debitAmounts the debitAmounts to set
   */
  public void setDebitAmounts(ArrayList<Integer> debitAmounts) {
    this.debitAmounts = debitAmounts;
  }

  /**
   * @return the creditAccounts
   */
  public ArrayList<Account> getCreditAccounts() {
    return creditAccounts;
  }

  /**
   * @param creditAccounts the creditAccounts to set
   */
  public void setCreditAccounts(ArrayList<Account> creditAccounts) {
    this.creditAccounts = creditAccounts;
  }

  /**
   * @return the creditAmounts
   */
  public ArrayList<Integer> getCreditAmounts() {
    return creditAmounts;
  }

  /**
   * @param creditAmounts the creditAmounts to set
   */
  public void setCreditAmounts(ArrayList<Integer> creditAmounts) {
    this.creditAmounts = creditAmounts;
  }
  
  /**
   * Add a debit transaction
   * @param acct
   * @param amount
   */
  public void addDebitTransaction(Account acct, int amount) {
    this.debitAccounts.add(acct);
    this.debitAmounts.add(amount);
  }
  
  /**
   * Add a credit transaction
   * @param acct
   * @param amount
   */
  public void addCreditTransaction(Account acct, int amount) {
    this.creditAccounts.add(acct);
    this.creditAmounts.add(amount);
  }
  
  /**
   * Removes a debit account 
   * @param acct
   * @return true if successfully removed
   */
  public boolean removeDebitAccount(Account acct) {
    int acctInd = debitAccounts.indexOf(acct);
    //remove the amount
    if (acctInd>=0)
      debitAmounts.remove(acctInd);
    else
      return false;
    //remove the account
    return debitAccounts.remove(acct);
  }
  
  /**
   * Removes a credit account 
   * @param acct
   * @return true if successfully removed
   */
  public boolean removeCreditAccount(Account acct) {
    int acctInd = creditAccounts.indexOf(acct);
    //remove the amount
    if (acctInd>=0)
      creditAmounts.remove(acctInd);
    else
      return false;
    //remove the account
    return creditAccounts.remove(acct);
  }
  
  /**
   * Get the amount debited or credited of an account in this transaction
   * @param acct
   * @param type "DEBIT" or "CREDIT"
   * @return the amount debited or credited of an account
   */
  public int getCurrentAmount(Account acct, String type) {
    if(type.equalsIgnoreCase("DEBIT")) {
      int acctInd = debitAccounts.indexOf(acct);
      return debitAmounts.get(acctInd);
    } else if (type.equalsIgnoreCase("CREDIT")) {
      int acctInd = creditAccounts.indexOf(acct);
      return creditAmounts.get(acctInd);
    } else
      throw new IllegalArgumentException();
  }
  
  /**
   * Return the total amount of a type of accounts in this transaction
   * @param type "DEBIT" or "CREDIT"
   * @return the total transaction amount of a type of accounts
   */
  public int getCurrentAmount(String type) {
    if(type.equalsIgnoreCase("DEBIT")) {
      int debitAmt = 0;
      for(int i: debitAmounts)
        debitAmt += i;
      return debitAmt;
    } else if (type.equalsIgnoreCase("CREDIT")) {
      int creditAmt = 0;
      for(int i: creditAmounts)
        creditAmt += i;
      return creditAmt;
    }else
      throw new IllegalArgumentException();
  }
  
  /**
   * Checks whether this Transaction is correct or not
   * @return false if the amounts dont match
   */
  public boolean errorCheck() {
    int debitAmt = getCurrentAmount("DEBIT");
    int creditAmt = getCurrentAmount("CREDIT");
    
    return debitAmt==creditAmt;
  }
  
  /**
   * Returns a string in the format:
   * transaction id, date, debit accounts, debit amounts, credit accounts, credit amounts
   */
  @Override
  public String toString() {
    String transaction = "";
    transaction += (Integer.toString(transactionNumber) + ",");
    transaction += (date.format(formatter)+",");
    transaction += (debitAccounts.toString()+",");
    transaction += (debitAmounts.toString()+",");
    transaction += (creditAccounts.toString()+",");
    transaction += (creditAmounts.toString());
    
    return transaction; 
  }

  /**
   * Compares based on the transaction ID
   * @param o the transaction to compare to
   * @return -1 if lesser, 0 if equal, 1 if greater
   */
  @Override
  public int compareTo(Transaction o) {
    return (this.getTransactionNumber() < o.getTransactionNumber() ? -1 : 
      (this.getTransactionNumber() == o.getTransactionNumber() ? 0 : 1));
  }
}
