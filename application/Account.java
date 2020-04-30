package application;
import java.util.ArrayList;
import java.util.List;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Account.java 
// Files:           (a list of all source files used by that program)
// Course:          CS400 Lec001, Spring, 2020
//
// Author:          Qingqi Wu
// Email:           qwu86@wisc.edu
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
 * Account - Represents a single account including name 
 * and its current amount
 * 
 * Bugs: none known
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class Account implements Comparable<Account> {

  private String accountName;
  private int amount;
  private List<int[]> identifierList;
  
  /**
   * Constructor that initialized the account with given name and amount
   * @param name
   * @param amount
   */
  public Account(String name, int amount) {
    this.accountName = name;
    this.amount = amount;
    this.identifierList = new ArrayList<>();
  }
  
  /**
   * Initializes the account with 0 amount
   * @param name
   */
  public Account(String name) {
    this(name,0);
  }

  /**
   * @return the accountName
   */
  public String getAccountName() {
    return accountName;
  }

  /**
   * @param accountName the accountName to set
   */
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  /**
   * @return the amount
   */
  public int getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(int amount) {
    this.amount = amount;
  }
  
  /**
   * Add the specified amount to the account's amount
   * @param amountToAdd
   * @return the resulting amount
   */
  public int addAmount(int amountToAdd) {
    this.amount += amountToAdd;
    return this.amount;
  }

  /**
   * @return the identifier List
   */
  public List<int[]> getIdentifiers() {
    return identifierList;
  }

  /**
   * @param identifierList the identifierList to set
   */
  public void setIdentifierList(List<int[]> identifierList) {
    this.identifierList = identifierList;
  }
  
  /**
   * Add an identifier to the List in an int array of length 2, 
   *  i.e.[0,0]; First int means the financial statement [0 for 
   *  income statement, 1 for balance sheet, 2 for stockholder's equity,
   *  3 for statement of cashflows]
   *  The second int means the sub categories, for more information, check out
   *  the class diagram
   *  @param identifier - int array of length 2
   */
  public void addIdentifier(int[] identifier) {
    this.identifierList.add(identifier);
  }
  
  /**
   * Removes the specified identifier from this account
   * @param identifier - int array of length 2
   */
  public boolean removeIdentifier(int[] identifier) {
    return this.identifierList.remove(identifier);
  }
  
  /**
   * remove all identifiers of a given statement
   * @param statement - [0 for income statement, 1 for balance sheet, 
   * 2 for stockholder's equity, 3 for statement of cashflows]
   * @return true if removed successfully
   */
  public boolean removeIdentifier(int statement) {
    return this.identifierList.removeIf(tuple -> (tuple[0]==statement));
  }

  /**
   * Compares to other accounts based on name, if same name based on amount
   * @param o the other account
   * @return -1 if less, 0 if equal, 1 if greater
   */
  @Override
  public int compareTo(Account o) {
    if (!this.getAccountName().equals(o.getAccountName()))
      return this.getAccountName().compareTo(o.getAccountName());
    else
      return (this.getAmount() < o.getAmount() ? -1 : 
        (this.getAmount() == o.getAmount() ? 0 : 1));
  }
  
}
