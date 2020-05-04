/////////////////////////////////////////// FILE  HEADER ///////////////////////////////////////////
//
//Title: BooKeeper
//Files: Main.java, BooKeeper.java, Bookings.java, Financials.java, Transaction.java
//This File: Bookings.java
//
//Name: Alex Hertadi, Benjamin Tarmann, Qingqi Wu
//
///////////////////////////////////////// 100 COLUMNS WIDE /////////////////////////////////////////

package application;
import java.util.Hashtable;

/**
 * Bookings - hashtable that holds multiple Transactions, 
 * implements the HashTableADT. It uses chained buckets as 
 * a collision resolution scheme
 * 
 * Bugs: none known
 * @author Alex Hertadi, Benjamin Tarmann, Qingqi Wu
 * @version 1.0
 * @param <K> Transaction number
 * @param <V> Transaction
 * 
 */
public class Bookings extends Hashtable<String, Transaction> {

  public String title;
  private int latestTransactionID;
  private Transaction latestTransaction;
  
  /**
   * Initializes the hashtable
   * @param title
   */
  public Bookings(String title) {
    super();
    this.title = title;
  }
  
  /**
   * Add a method to track the latest transaction and its ID
   */
  @Override
  public synchronized Transaction put(String key, Transaction value) {
    this.latestTransactionID = Integer.parseInt(key);
    this.latestTransaction = value;
    return super.put(key, value);
  }
  
  /**
   * @return the latestTransaction
   */
  public Transaction getLatestTransaction() {
    return latestTransaction;
  }
  /**
   * @param latestTransaction the latestTransaction to set
   */
  public void setLatestTransaction(Transaction latestTransaction) {
    this.latestTransaction = latestTransaction;
  }

  /**
   * @return the latestTransactionID
   */
  public int getLatestTransactionID() {
    return latestTransactionID;
  }

  /**
   * @param latestTransactionID the latestTransactionID to set
   */
  public void setLatestTransactionID(int latestTransactionID) {
    this.latestTransactionID = latestTransactionID;
  }  
}
