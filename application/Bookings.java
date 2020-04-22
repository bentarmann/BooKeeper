package application;
import java.util.Hashtable;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Bookings.java 
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
 * Bookings - hashtable that holds multiple Transactions, 
 * implements the HashTableADT. It uses chained buckets as 
 * a collision resolution scheme
 * 
 * Bugs: none known
 * @author Qingqi Wu
 * @version 1.0
 * @param <K> Transaction number
 * @param <V> Transaction
 * 
 */
public class Bookings extends Hashtable<String, Transaction> {

  public String title;
  
  /**
   * Initializes the hashtable
   * @param title
   */
  public Bookings(String title) {
    super();
    this.title = title;
  }
  
}
