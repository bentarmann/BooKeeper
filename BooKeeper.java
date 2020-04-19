//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           BooKeeper.java 
// Files:           (a list of all source files used by that program)
// Course:          CS400, Spring 2020
//
// Author:          Qingqi Wu, Alexander Hertadi, Benjamin Tarmann
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
    int[] currentAsset = {1,0,0};
    int[] nonCurrentAsset = {1,0,1};
    
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
    lInvest.addIdentifier(nonCurrentAsset);

    Account land = new Account("Land");
    land.addIdentifier(nonCurrentAsset);

    Account buildings = new Account("Buildings");
    buildings.addIdentifier(nonCurrentAsset);
    
    Account equipment = new Account("Equipment");
    equipment.addIdentifier(nonCurrentAsset);
    
    Account vehicles = new Account("Vehicles");
    vehicles.addIdentifier(nonCurrentAsset);
    
    Account furniture = new Account("Furniture & Fixtures");
    furniture.addIdentifier(nonCurrentAsset);
    
    Account depres = new Account("Accumulated Depreciation");
    depres.addIdentifier(nonCurrentAsset);
    
  }

}
