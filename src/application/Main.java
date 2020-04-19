//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Main.java 
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
package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main - TODO Description
 * 
 * Bugs: none known
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class Main extends Application {

//store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 700;
  private static final String APP_TITLE = "BooKeeper";
  
  @Override
  public void start(Stage primaryStage) throws Exception {
      // save args example
      args = this.getParameters().getRaw();

      // Main layout is Border Pane example (top,left,center,right,bottom)
          BorderPane root = new BorderPane();
      
      //add elements here
          //
          //according to the GUI shown in the design
          initializeTop(root);
          
      Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
      
      

      // Add the stuff and set the primary stage
          primaryStage.setTitle(APP_TITLE);
          primaryStage.setScene(mainScene);
          primaryStage.show();
  }
  
  /**
   * Initializes the elements at the top of the main BorderPane layout
   * 
   * @param root main BorderPane layout
   */
  public void initializeTop(BorderPane root) {
      MenuBar topMb = new MenuBar();
      Menu file = new Menu("File");
      Menu edit = new Menu("Edit");
      Menu find = new Menu("Find");
      Menu window = new Menu("Window");
      Menu help = new Menu("Help");
      Menu about = new Menu("About");
      topMb.getMenus().add(file);
      topMb.getMenus().add(edit);
      topMb.getMenus().add(find);
      topMb.getMenus().add(window);
      topMb.getMenus().add(help);
      topMb.getMenus().add(about);
      
      // TODO: Add MenuItem objects and add them to menu options for desired implementations
      
      // create save button
      Button save = new Button("Save");
      
      // create top tabs
      TabPane tabpane = new TabPane();
      // TODO: Figure out a way to add tabs and modify their content on demand. Perhaps create a method 
      // that adds tabs as the user opens a new balance sheet. Removing a tab is already implemented 
      // by the tabpane class
      // Below loop adds tabs as a demonstration of how tabpane looks 	 
      for (int i = 0; i < 20; i++) {
	  tabpane.getTabs().add(new Tab("Tab " + i));
      }
      
      // add elements to top
      BorderPane top = new BorderPane();
      top.setTop(topMb);
      top.setRight(save);
      top.setCenter(tabpane);
         
      root.setTop(top);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
         launch(args);
  }

}
