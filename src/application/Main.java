//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Main.java
// Files: (a list of all source files used by that program)
// Course: CS400 Lec001, Spring, 2020
//
// Author: Qingqi Wu, Benjamin Tarmann, Alexander Hertadi
// Email: qwu86@wisc.edu
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: https://stackoverflow.com/questions/47995936/javafx-tables-inside-row-table
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Main - TODO Description
 * 
 * Bugs: none known
 * 
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class Main extends Application {

  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 720;
  private static final String APP_TITLE = "BooKeeper v0.1";
  private static Font font = new Font("Arial", 15);// TODO:add settings to change the font

  @Override
  public void start(Stage primaryStage) throws Exception {
    // save args example
    args = this.getParameters().getRaw();

    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane root = new BorderPane();

    // add elements here
    //
    // according to the GUI shown in the design
    initializeTop(root);

    // initalize the main window

    initializeMain(root);

    // the left part

    initializeLeft(root);

    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);



    // Add the stuff and set the primary stage
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  /**
   * Initializes the main transaction journal window
   * 
   * @param root
   */
  private void initializeMain(BorderPane root) {
    BorderPane main = new BorderPane();
    HBox mainTop = new HBox();



    // create top tabs
    TabPane tabpane = new TabPane();
    // TODO: Figure out a way to add tabs and modify their content on demand. Perhaps create a
    // method
    // that adds tabs as the user opens a new balance sheet. Removing a tab is already implemented
    // by the tabpane class
    // Below loop adds tabs as a demonstration of how tabpane looks
    for (int i = 0; i < 3; i++) {
      tabpane.getTabs().add(new Tab("Tab " + i));
    }

    // create save button
    Button save = new Button("Save");

    HBox tabs = new HBox(tabpane, save);
    tabs.setSpacing(20);



    // TODO: add function to change the title
    Label title = new Label("Journal Entries");
    title.setFont(font);
    mainTop.getChildren().add(title);


    // Search bar

    ChoiceBox<String> searchChoice = new ChoiceBox();
    searchChoice.getItems().addAll("Transaction Number", "Date", "Account Name", "Amount");
    searchChoice.setValue("Transaction Number");

    TextField searchText = new TextField();
    searchText.setPromptText("Search this document...");

    HBox searchBar = new HBox(searchChoice, searchText);
    mainTop.setSpacing(5);
    mainTop.getChildren().add(searchBar);

    VBox topBar = new VBox(tabs, mainTop);

    main.setTop(topBar);



    TableView table = new TableView<>();
    table.setEditable(true);

    // transaction number column
    TableColumn<Integer, Transaction> transNumCol = new TableColumn<>("Transaction Number");
    transNumCol.setMinWidth(125);
    transNumCol
        .setCellValueFactory(new PropertyValueFactory<Integer, Transaction>("transactionNumber"));
    table.getColumns().add(transNumCol);

    // transaction date column
    TableColumn<LocalDateTime, Transaction> dateCol = new TableColumn<>("Time");
    dateCol.setMinWidth(100);
    dateCol.setCellValueFactory(new PropertyValueFactory<LocalDateTime, Transaction>("dateString"));
    table.getColumns().add(dateCol);

    // Transaction Details column
    TableColumn detailsCol = new TableColumn("Transaction Details");
    table.getColumns().add(detailsCol);


    // TODO: try not hard coding the items
    Transaction t1 = new Transaction(1);
    Account cash = new Account("Cash");
    t1.addDebitTransaction(cash, 99);
    Account inventory = new Account("Inventory");
    t1.addCreditTransaction(inventory, 99);
    table.getItems().add(t1);

    Transaction t2 = new Transaction(2);
    t2.addDebitTransaction(inventory, 100);
    t2.addCreditTransaction(cash, 100);
    table.getItems().add(t2);

    table.setRowFactory(tr -> new TableRow<Transaction>() {
      Node transactionDetails;
      {
        this.selectedProperty().addListener((i, wasSelected, isSelected) -> {
          if (isSelected) {
            transactionDetails = constructSubTable(getItem());
            this.getChildren().add(transactionDetails);
          } else {
            this.getChildren().remove(transactionDetails);
          }
          this.requestLayout();
        });

      }

      @Override
      protected double computePrefHeight(double width) {
        if (isSelected()) {
          return super.computePrefHeight(width) + transactionDetails.prefHeight(20);
        } else {
          return super.computePrefHeight(width);
        }
      }

      @Override
      protected void layoutChildren() {
        super.layoutChildren();
        if (isSelected()) {
          double width = getWidth();
          double paneHeight = transactionDetails.prefHeight(width);
          transactionDetails.resizeRelocate(transNumCol.getWidth() + dateCol.getWidth(),
              getHeight() - paneHeight, width, paneHeight);
        }
      }
    });


    main.setCenter(table);

    root.setCenter(main);
  }

  /**
   * Construct a sub table(vbox) containing details of transaction
   * 
   * @param t
   * @return
   */
  private VBox constructSubTable(Transaction t) {

    // create the debit part
    Label debLabel = new Label("Debit");
    debLabel.setFont(font);
    ListView debAcct =
        new ListView(FXCollections.observableArrayList(getAccountNames(t.getDebitAccounts())));
    ListView debAmt = new ListView(FXCollections.observableArrayList(t.getDebitAmounts()));
    HBox deb = new HBox(debLabel, debAcct, debAmt);
    deb.setPrefHeight((t.getDebitAmounts().size() * 25));
    deb.setSpacing(5);

    // create the credit part
    Label credLabel = new Label("Credit");
    credLabel.setFont(font);
    ListView credAcct =
        new ListView(FXCollections.observableArrayList(getAccountNames(t.getCreditAccounts())));
    ListView credAmt = new ListView(FXCollections.observableArrayList(t.getCreditAmounts()));
    HBox cred = new HBox(credLabel, credAcct, credAmt);
    cred.setPrefHeight((t.getCreditAmounts().size() * 25));
    cred.setSpacing(5);

    // combine these two
    VBox vb = new VBox(deb, cred);
    vb.setSpacing(5);

    // create a background fill
    // BackgroundFill bf =
    // new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY);

    // create Background
    // Background bg = new Background(bf);

    // vb.setBackground(bg);

    return vb;
  }

  /**
   * return a list of account names when given a list of accounts
   * 
   * @param accts
   * @return a list of account names in string
   */
  private List<String> getAccountNames(List<Account> accts) {

    List<String> acctNames = new ArrayList<>();

    for (Account acct : accts)
      acctNames.add(acct.getAccountName());

    return acctNames;
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



    // add elements to top
    BorderPane top = new BorderPane();
    top.setTop(topMb);

    root.setTop(top);
  }

  /**
   * Initializes the elements at the left of the main BorderPane layout
   * 
   * @param root main BorderPane layout
   */
  public void initializeLeft(BorderPane root) {

    TreeItem<Path> treeItem = new TreeItem<Path>(Paths.get(System.getProperty("user.dir")));
    treeItem.setExpanded(false);

    // create tree structure
    try {
      createTree(treeItem);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // sort tree structure by name
    treeItem.getChildren().sort(Comparator.comparing(new Function<TreeItem<Path>, String>() {
      @Override
      public String apply(TreeItem<Path> t) {
        String value = t.getValue().toString();
        int indexOf = value.lastIndexOf(File.separator);
        if (indexOf > 0) {
          return value.substring(indexOf + 1);
        } else {
          return value;
        }
        // return t.getValue().toString().toLowerCase();

      }
    }));

    // create components
    BorderPane left = new BorderPane();
    TreeView<Path> treeView = new TreeView<Path>(treeItem);
    SplitPane splitView = new SplitPane();
    splitView.getItems().add(treeView);

    FlowPane leftBot = new FlowPane();
    leftBot.setPadding(new Insets(20, 0, 0, 30));
    leftBot.setHgap(800000);
    leftBot.setVgap(10);
    Label style = new Label("Style Settings\n\nFont:");
    ComboBox fontSel = new ComboBox();
    fontSel.getItems().add("Arial");//TODO: add font selections
    fontSel.setValue("Arial");
    
    Label fontSize = new Label("Size: ");
    ComboBox fontSizer = new ComboBox();
    fontSizer.getItems().add("15");//TODO: add size settings
    fontSizer.setValue("15");
    
    Label line = new Label("Line Width:");
    ComboBox lineWidth = new ComboBox();
    lineWidth.getItems().add("1px");//TODO: add line width settings
    lineWidth.setValue("1px");
    
    leftBot.getChildren().addAll(style, fontSel, fontSize, fontSizer, line, lineWidth);


    left.setTop(splitView);
    left.setCenter(leftBot);
    root.setLeft(left);
  }


  /**
   * Recursively create the tree
   * 
   * @param rootItem
   * @throws IOException
   */
  public static void createTree(TreeItem<Path> rootItem) throws IOException {

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue())) {

      for (Path path : directoryStream) {

        TreeItem<Path> newItem = new TreeItem<Path>(path);
        newItem.setExpanded(true);

        rootItem.getChildren().add(newItem);

        if (Files.isDirectory(path)) {
          createTree(newItem);
        }
      }
    }
  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

}
