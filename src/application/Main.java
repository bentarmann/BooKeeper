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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
      
      Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

      // Add the stuff and set the primary stage
          primaryStage.setTitle(APP_TITLE);
          primaryStage.setScene(mainScene);
          primaryStage.show();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
         launch(args);
  }

}
